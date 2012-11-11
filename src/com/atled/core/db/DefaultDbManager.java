package com.atled.core.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.atled.core.db.fields.definitions.DatabaseDefinition;
import com.atled.core.db.fields.definitions.DatabaseTableDefinition;
import com.atled.core.exceptions.ExceptionHandler;
import com.atled.core.logging.Log;
import com.atled.core.validation.ParameterChecker;



public abstract class DefaultDbManager implements DbManager {
	
	private final static Log log = Log.getInstance(DefaultDbManager.class);

	protected final DatabaseDefinition databaseDefinition; 
	//String DB_NAME = "rnaparameditordb";
	private Connection con;
	private Statement dbStatement;
	

	public DefaultDbManager(DatabaseDefinition databaseDefinition) {
		this.databaseDefinition = databaseDefinition;
	}
	
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
