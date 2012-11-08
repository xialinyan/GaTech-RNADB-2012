package db;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import turner99parsing.DangleFileIO;
import turner99parsing.Turner99Module;

import com.atled.core.db.DbManager;
import com.atled.core.db.DefaultDbManager;
import com.atled.core.db.fields.DatabaseEntry;
import com.atled.core.db.fields.definitions.DatabaseDefinition;
import com.atled.core.db.fields.definitions.DatabaseFieldDefinition;
import com.atled.core.db.fields.definitions.DatabaseFieldDefinition.FieldCharacteristics;
import com.atled.core.db.fields.definitions.DatabaseTableDefinition;
import com.atled.core.db.fields.definitions.VarcharFieldDefinition;
import com.atled.core.validation.ParameterChecker;

import db.entry.DangleEntry;


public class DangleDbManager extends DefaultDbManager {
	
	public final static DatabaseDefinition DATABASE_DEFINITION;
	public final static DatabaseTableDefinition DATABASE_TABLE_DEFINITION;
	public final static VarcharFieldDefinition PAIR_TYPE_FIELD;
	public final static VarcharFieldDefinition LEAD_NUCLEOTIDE_FIELD;
	public final static VarcharFieldDefinition TAIL_NUCLEOTIDE_FIELD;
	public final static VarcharFieldDefinition DANGLE_NUCLEOTIDE_FIELD;
	public final static VarcharFieldDefinition ARRAY_INDEX_FIELD;
	public final static VarcharFieldDefinition PARAM_VALUE_FIELD;
	
	public final static DangleFileIO dangleFileIO;
	
	static {
		PAIR_TYPE_FIELD = new VarcharFieldDefinition("pairTypeField", 
				"pairTypeField", 4); // TODO enum field definition?
		LEAD_NUCLEOTIDE_FIELD = new VarcharFieldDefinition("leadNucleotideField", 
				"leadNucleotideField", 1); // TODO enum field definition?
		TAIL_NUCLEOTIDE_FIELD = new VarcharFieldDefinition("tailNucleotideField", 
				"tailNucleotideField", 1); // TODO enum field definition?
		DANGLE_NUCLEOTIDE_FIELD = new VarcharFieldDefinition("dangleNucleotideField", 
				"dangleNucleotideField", 1); // TODO enum field definition?
		List<FieldCharacteristics> fieldChars = 
				new ArrayList<DatabaseFieldDefinition.FieldCharacteristics>();
		fieldChars.add(FieldCharacteristics.PRIMARY_KEY);
		fieldChars.add(FieldCharacteristics.NOT_NULL);
		ARRAY_INDEX_FIELD = new VarcharFieldDefinition("arrayIndexField", 
				"arrayIndexField", fieldChars, 5);
		PARAM_VALUE_FIELD = new VarcharFieldDefinition("paramValueField", 
				"paramValueField", 10);
		{
			List<DatabaseFieldDefinition> fields = 
					new ArrayList<DatabaseFieldDefinition>();
			fields.add(PAIR_TYPE_FIELD);
			fields.add(LEAD_NUCLEOTIDE_FIELD);
			fields.add(TAIL_NUCLEOTIDE_FIELD);
			fields.add(DANGLE_NUCLEOTIDE_FIELD);
			fields.add(ARRAY_INDEX_FIELD);
			fields.add(PARAM_VALUE_FIELD);
			DATABASE_TABLE_DEFINITION = new DatabaseTableDefinition("Dangle", "dangle", 
					Collections.unmodifiableList(fields));
		}
		List<DatabaseTableDefinition> tables = new ArrayList<DatabaseTableDefinition>();
		tables.add(DATABASE_TABLE_DEFINITION);
		DATABASE_DEFINITION = new DatabaseDefinition("Param Editor GUI - Dangle", 
				"parameditorgui_dangle", tables);
		
		dangleFileIO = new DangleFileIO();
	}
	
	public DangleDbManager() {
		super(DangleDbManager.DATABASE_DEFINITION);
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
		String dropSql = DATABASE_TABLE_DEFINITION.getDropStatement();
		if (!executeSql(dropSql)) {
			return false;
		}
		String createSql = DATABASE_TABLE_DEFINITION.getSqlCreateStatement();
		if (!executeSql(createSql)) {
			return false;
		}
		/**
		 * @step This method should use a class which implements the ReadFile interface 
		 * to get the parameters to insert.
		 */
		List<List<String>> paramArray = dangleFileIO.read(fileName);
		List<DatabaseEntry> entryList = new ArrayList<DatabaseEntry>();
		for (int i=0;i<paramArray.size();i++) {
			for (int j=0;j<paramArray.get(i).size();j++) {
				entryList.add(new DangleEntry(i, j, paramArray.get(i).get(j)));
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

	private boolean insert(List<DatabaseEntry> array) {
		
		for (DatabaseEntry entry : array) {
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
	public boolean update(DatabaseEntry entry, DatabaseFieldDefinition updateField) {
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
	public ResultSet search(List<String> array) {
		// TODO 
		/**
		 * @step ensure db is connected and initialized
		 */
		/**
		 * @step convert <code>List</code> of <code>String</code> to update SQL query.
		 */
		/**
		 * @step execute query
		 */
		/**
		 * @step return results set
		 */
		return null;
	}
	
	public static void main(String[] args) {
		// Test Sql Create Statements
		System.out.println(DangleDbManager.DATABASE_TABLE_DEFINITION
				.getSqlCreateStatement());
		
		// Test Dangle Db Manager Functionality
		DbManager manager = new DangleDbManager();
		manager.init(Turner99Module.DANGLE_TEST_FILENAME);
	}
}
