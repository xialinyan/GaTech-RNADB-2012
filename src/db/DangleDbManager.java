package db;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import parameditor.utils.DangleModule;
import turner99parsing.DangleFileIO;

import com.atled.core.db.DefaultDbManager;
import com.atled.core.db.fields.DatabaseRow;
import com.atled.core.db.fields.definitions.DatabaseFieldDefinition;
import com.atled.core.db.query.SqlTableSelectQuery;
import com.atled.core.exceptions.ExceptionHandler;
import com.atled.core.logging.Log;
import com.atled.core.validation.ParameterChecker;

import db.entry.DangleRow;


public class DangleDbManager extends DefaultDbManager {
	
	@SuppressWarnings("unused")
	private final static Log log = Log.getInstance(DangleDbManager.class);
	
	public DangleDbManager() {
		super(DangleModule.DATABASE_DEFINITION);
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
		if (tableExists(DangleModule.DATABASE_TABLE_DEFINITION)) {
			String dropSql = DangleModule.DATABASE_TABLE_DEFINITION.getDropStatement();
			if (!executeSql(dropSql)) {
				return false;
			}
		}
		String createSql = DangleModule.DATABASE_TABLE_DEFINITION.getSqlCreateStatement();
		if (!executeSql(createSql)) {
			return false;
		}
		/**
		 * @step This method should use a class which implements the ReadFile interface 
		 * to get the parameters to insert.
		 */
		List<List<String>> paramArray = DangleModule.dangleFileIO.read(fileName);
		List<DatabaseRow> entryList = new ArrayList<DatabaseRow>();
		for (int i=0;i<paramArray.size();i++) {
			for (int j=0;j<paramArray.get(i).size();j++) {
				entryList.add(new DangleRow(i, j, paramArray.get(i).get(j)));
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

	private boolean insert(List<DatabaseRow> array) {
		
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

	@Override
	public List<DatabaseRow> search(SqlTableSelectQuery query) {
		/**
		 * @step ensure db is connected and initialized
		 */
		if (!isConnected()) {
			return null;
		}
		/**
		 * @step execute query
		 */
		ResultSet results = executeSqlQuery(query.generateSql());
		/**
		 * @step return results set
		 */
		List<DatabaseRow> entries = new ArrayList<DatabaseRow>();
		try {
			while (results.next()) {
				String key = results.getString(
						DangleModule.ARRAY_INDEX_FIELD.getDbFieldName());
				String value = results.getString(
						DangleModule.PARAM_VALUE_FIELD.getDbFieldName());
				DatabaseRow entry = new DangleRow(key, value);
				entries.add(entry);
			}
		} catch (SQLException e) {
			ExceptionHandler.handle(e);
		}
		return entries;
	}
	
	@Override
	public boolean export(String filename) {
		String[][] arr = new String[DangleFileIO.NUM_ROWS][DangleFileIO.NUM_COLS];
		List<DatabaseRow> rows = search(DangleModule.getStarTableQuery());
		for (DatabaseRow row : rows) {
			DangleRow dRow = (DangleRow) row;
			int rowIndex = DangleModule.getRowFromKey(dRow.getArrayIndex());
			int colIndex = DangleModule.getColumnFromKey(dRow.getArrayIndex());
			arr[rowIndex][colIndex] = dRow.getParamValue();
		}
		List<List<String>> params = new ArrayList<List<String>>();
		for (int i=0;i<arr.length;i++) {
			List<String> temp = new ArrayList<String>();
			for (int j=0;j<arr[i].length;j++) {
				temp.add(arr[i][j]);
			}
			params.add(temp);
		}
		
		try {
			DangleModule.dangleFileIO.export(filename, params);
		} catch (IOException e) {
			ExceptionHandler.handle(e);
			return false;
		}
		return true;
	}
}
