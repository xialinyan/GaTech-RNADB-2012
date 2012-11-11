package db.turner99manager;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import parameditor.utils.StackModule;
import turner99parsing.MfeParameterFileIO;
import turner99parsing.StackFileIO;

import com.atled.core.db.DefaultDbManager;
import com.atled.core.db.fields.DatabaseRow;
import com.atled.core.db.fields.definitions.DatabaseTableDefinition;
import com.atled.core.db.query.SqlTableSelectQuery;
import com.atled.core.exceptions.ExceptionHandler;
import com.atled.core.logging.Log;
import com.atled.core.validation.ParameterChecker;

import db.entry.StackRow;

public class StackDatabaseManager extends DefaultDbManager {

	@SuppressWarnings("unused")
	private final static Log log = Log.getInstance(DangleDatabaseManager.class);
	
	public StackDatabaseManager() {
		super(StackModule.DATABASE_DEFINITION);
	}

	@Override
	public DatabaseTableDefinition getTableDefinition() {
		return StackModule.DATABASE_TABLE_DEFINITION;
	}

	@Override
	public MfeParameterFileIO getFileIO() {
		return StackModule.stackFileIO;
	}
	
	@Override
	protected DatabaseRow getDatabaseRow(int row, int col, Object value) {
		ParameterChecker.matchClass("value", value, String.class);
		return new StackRow(row, col, (String)value);
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
						StackModule.ARRAY_INDEX_FIELD.getDbFieldName());
				String value = results.getString(
						StackModule.PARAM_VALUE_FIELD.getDbFieldName());
				DatabaseRow entry = new StackRow(key, value);
				entries.add(entry);
			}
		} catch (SQLException e) {
			ExceptionHandler.handle(e);
		}
		return entries;
	}
	
	@Override
	public boolean export(String filename) {
		String[][] arr = new String[StackFileIO.NUM_ROWS][StackFileIO.NUM_COLS];
		List<DatabaseRow> rows = search(StackModule.getStarTableQuery());
		for (DatabaseRow row : rows) {
			StackRow dRow = (StackRow) row;
			int rowIndex = StackModule.getRowFromKey(dRow.getArrayIndex());
			int colIndex = StackModule.getColumnFromKey(dRow.getArrayIndex());
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
			StackModule.stackFileIO.export(filename, params);
		} catch (IOException e) {
			ExceptionHandler.handle(e);
			return false;
		}
		return true;
	}
}
