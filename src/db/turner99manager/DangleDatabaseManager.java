package db.turner99manager;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import parameditor.utils.DangleModule;
import turner99parsing.DangleFileIO;
import turner99parsing.MfeParameterFileIO;

import com.atled.core.db.DefaultDbManager;
import com.atled.core.db.fields.DatabaseRow;
import com.atled.core.db.fields.definitions.DatabaseTableDefinition;
import com.atled.core.db.query.SqlTableSelectQuery;
import com.atled.core.exceptions.ExceptionHandler;
import com.atled.core.logging.Log;
import com.atled.core.validation.ParameterChecker;

import db.entry.DangleRow;


public class DangleDatabaseManager extends DefaultDbManager {
	
	@SuppressWarnings("unused")
	private final static Log log = Log.getInstance(DangleDatabaseManager.class);
	
	public DangleDatabaseManager() {
		super(DangleModule.DATABASE_DEFINITION);
	}

	@Override
	public DatabaseTableDefinition getTableDefinition() {
		return DangleModule.DATABASE_TABLE_DEFINITION;
	}

	@Override
	public MfeParameterFileIO getFileIO() {
		return DangleModule.dangleFileIO;
	}
	
	@Override
	protected DatabaseRow getDatabaseRow(int row, int col, Object value) {
		ParameterChecker.matchClass("value", value, String.class);
		return new DangleRow(row, col, (String)value);
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
