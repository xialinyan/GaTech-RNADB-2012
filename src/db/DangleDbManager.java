package db;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.atled.core.db.DefaultDbManager;
import com.atled.core.db.definitions.DatabaseDefinition;
import com.atled.core.db.definitions.DatabaseFieldDefinition;
import com.atled.core.db.definitions.DatabaseFieldDefinition.FieldCharacteristics;
import com.atled.core.db.definitions.DatabaseTableDefinition;
import com.atled.core.db.definitions.VarcharField;
import com.atled.core.exceptions.ExceptionHandler;


public class DangleDbManager extends DefaultDbManager {
	
	public final static DatabaseDefinition DATABASE_DEFINITION;
	public final static DatabaseTableDefinition DATABASE_TABLE_DEFINITION;
	public final static VarcharField PAIR_TYPE_FIELD;
	public final static VarcharField LEAD_NUCLEOTIDE_FIELD;
	public final static VarcharField TAIL_NUCLEOTIDE_FIELD;
	public final static VarcharField DANGLE_NUCLEOTIDE_FIELD;
	public final static VarcharField ARRAY_INDEX_FIELD;
	public final static VarcharField PARAM_VALUE_FIELD;
	
	static {
		PAIR_TYPE_FIELD = new VarcharField("pairTypeField", "pairTypeField", 4); // TODO enum field definition?
		LEAD_NUCLEOTIDE_FIELD = new VarcharField("leadNucleotideField", 
				"leadNucleotideField", 1); // TODO enum field definition?
		TAIL_NUCLEOTIDE_FIELD = new VarcharField("tailNucleotideField", 
				"tailNucleotideField", 1); // TODO enum field definition?
		DANGLE_NUCLEOTIDE_FIELD = new VarcharField("dangleNucleotideField", 
				"dangleNucleotideField", 1); // TODO enum field definition?
		List<FieldCharacteristics> fieldChars = 
				new ArrayList<DatabaseFieldDefinition.FieldCharacteristics>();
		fieldChars.add(FieldCharacteristics.PRIMARY_KEY);
		fieldChars.add(FieldCharacteristics.NOT_NULL);
		ARRAY_INDEX_FIELD = new VarcharField("arrayIndexField", 
				"arrayIndexField", fieldChars, 5);
		PARAM_VALUE_FIELD = new VarcharField("paramValueField", 
				"paramValueField", 10);
		{
			List<DatabaseFieldDefinition> fields = new ArrayList<DatabaseFieldDefinition>();
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
		DATABASE_DEFINITION = new DatabaseDefinition("", "", tables);
	}
	
	public DangleDbManager() {
		super(DangleDbManager.DATABASE_DEFINITION);
	}
	
	@Override
	public boolean init(String fileName) throws IOException {
		/**
		 * @step connect to db. If connection failed, return false
		 */
		openConnection();
		/**
		 * @step If the table does not exist in the database, this method creates it else, if 
		 * it does exist, this method truncates the table.
		 */
		try {
			Statement st = con.createStatement();
			return st.execute("");
		} catch (SQLException e) {
			ExceptionHandler.handle(e);
		}
		/**
		 * @step This method should use a class which implements the ReadFile interface to get 
		 * the parameters to insert.
		 */
		
		/**
		 * @step Once this method has the results from ReadFile, it should call this.insert(...) for
		 * each parameter. Ensure that each entry was inserted sucessfully.
		 */
		/**
		 * @step return sucess of initialization
		 */
		return false;
	}

	@Override
	public boolean insert(List<String> array) throws SQLException {
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
		 * @step return success of insertion
		 */
		return false;
	}

	@Override
	public boolean update(List<String> array) throws SQLException {
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
		 * @step return success of update
		 */ 
		return false;
	}

	@Override
	public ResultSet search(List<String> array) throws SQLException {
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
		System.out.println(DangleDbManager.DATABASE_TABLE_DEFINITION.getSqlCreateStatement());
	}
}
