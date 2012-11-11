package com.atled.core.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import turner99parsing.MfeParameterFileIO;

import com.atled.core.db.fields.DatabaseRow;
import com.atled.core.db.fields.definitions.DatabaseDefinition;
import com.atled.core.db.fields.definitions.DatabaseFieldDefinition;
import com.atled.core.db.fields.definitions.DatabaseTableDefinition;
import com.atled.core.exceptions.ExceptionHandler;
import com.atled.core.logging.Log;
import com.atled.core.validation.ParameterChecker;



public abstract class DefaultDbManager implements DbManager {
	
	private final static Log log = Log.getInstance(DefaultDbManager.class);

	protected final DatabaseDefinition databaseDefinition;
	private Connection con;
	private Statement dbStatement;

	public DefaultDbManager(DatabaseDefinition databaseDefinition) {
		this.databaseDefinition = databaseDefinition;
	}
	
	public abstract DatabaseTableDefinition getTableDefinition();
	
	public abstract MfeParameterFileIO getFileIO();
	
	protected abstract DatabaseRow getDatabaseRow(int row, int col, Object value);
	
	/**
	 * @step Connect to correct database and create if necessary.
	 * 
	 * @return True if connection was opened sucessfully.
	 */
	@Override
	public 	final void openConnection() {
		try {
			if (con != null && !con.isClosed()) {
				return;
			}
			/**
			 * @step Connect to correct database and create if necessary.
			 */
			String connectionString = "jdbc:derby:"+ databaseDefinition.getDbName() + 
					";create=true;";
			con = DriverManager.getConnection(connectionString);
		} catch (SQLException e) {
			ExceptionHandler.handle(e);
		}
	}
	
	@Override
	public final boolean isConnected() {
		try {
			return con != null && !con.isClosed();
		} catch (SQLException e) {	}
		return false;
	}
	
	@Override
	public final void closeConnection() {
		/**
		 * @step If connection is currently open, close the connection.
		 */
		try {
			if (con == null) {
				return;
			}
			con.close();
			con = null;
		} catch (SQLException e) {
			ExceptionHandler.handle(e);
		}
		/**
		 * @step start garbage collector
		 */
		System.gc();
	}

	@Override
	public boolean init(String fileName) {
		/**
		 * @step connect to db. If connection failed, return false
		 */
		openConnection();
		/**
		 * it does exist, this method truncates the table.
		 */
		if (tableExists(getTableDefinition())) {
			String dropSql = getTableDefinition().getDropStatement();
			if (!executeSql(dropSql)) {
				return false;
			}
		}
		String createSql = getTableDefinition().getSqlCreateStatement();
		if (!executeSql(createSql)) {
			return false;
		}
		/**
		 * @step This method should use a class which implements the ReadFile interface 
		 * to get the parameters to insert.
		 */
		List<List<String>> paramArray = getFileIO().read(fileName);
		List<DatabaseRow> entryList = new ArrayList<DatabaseRow>();
		for (int i=0;i<paramArray.size();i++) {
			for (int j=0;j<paramArray.get(i).size();j++) {
				entryList.add(getDatabaseRow(i, j, paramArray.get(i).get(j)));
			}
		}
		/**
		 * @step Once this method has the results from ReadFile, it should call 
		 * this.insert(...) for each parameter. Ensure that each entry was inserted 
		 * successfully.
		 */
		boolean insertResult = insert(entryList);
		/**
		 * @step return sucess of initialization
		 */
		return insertResult;
	}

	@Override
	public boolean update(DatabaseRow entry, DatabaseFieldDefinition updateField) {
		ParameterChecker.notNull("entry", entry);
		/**
		 * @step execute query
		 */
		String updateSql = entry.getUpdateString(updateField);
		boolean updateResult = executeSql(updateSql);
		/**
		 * @step return success of update
		 */ 
		return updateResult;
	}

	protected boolean insert(List<DatabaseRow> array) {
		
		for (DatabaseRow entry : array) {
			/**
			 * @step convert <code>List</code> of <code>String</code> to update SQL 
			 * query.
			 */
			String insertSql = entry.getInsertString();
			/**
			 * @step execute query
			 */
			if (!executeSql(insertSql)) {
				return false;
			}
		}
		/**
		 * @step return success of insertion
		 */
		return true;
	}

	protected boolean executeSql(String sql) {
		if (!isConnected()) {
			return false;
		}
		try {
			if (dbStatement == null) {
				dbStatement = con.createStatement();
			}
			log.debug(sql);
			dbStatement.execute(sql);
			return true;
		} catch (SQLException e) {
			ExceptionHandler.handle(e);
		}
		return false;
	}

	protected ResultSet executeSqlQuery(String sql) {
		if (!isConnected()) {
			return null;
		}
		try {
			if (dbStatement == null) {
				dbStatement = con.createStatement();
			}
			log.debug(sql);
			return dbStatement.executeQuery(sql);
		} catch (SQLException e) {
			ExceptionHandler.handle(e);
		}
		return null;
	}
	
	protected boolean tableExists(DatabaseTableDefinition tableDefinition) {
		ParameterChecker.notNull("tableDefinition", tableDefinition);
		try {
			DatabaseMetaData metadata = null;
			metadata = con.getMetaData();
//			String[] names = { "TABLE"};
			ResultSet tableNames = metadata.getTables( null, null, null, null);
			while( tableNames.next())
			{
				String tab = tableNames.getString( "TABLE_NAME");
				if (tab.equals(tableDefinition.getDbName())) {
					return true;
				}
			}
		} catch (SQLException e) {
			ExceptionHandler.handle(e);
		}
		return false;
	}
}
