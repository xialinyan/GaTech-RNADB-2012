package parameditor.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import turner99parsing.StackFileIO;

import com.atled.core.db.fields.definitions.DatabaseDefinition;
import com.atled.core.db.fields.definitions.DatabaseFieldDefinition;
import com.atled.core.db.fields.definitions.DatabaseFieldDefinition.FieldCharacteristics;
import com.atled.core.db.fields.definitions.DatabaseTableDefinition;
import com.atled.core.db.fields.definitions.VarcharFieldDefinition;
import com.atled.core.db.query.SqlTableSelectQuery;
import com.atled.core.db.query.constraints.SqlQueryConstraintCollection;
import com.atled.core.db.query.constraints.SqlVarcharQueryConstraint;
import com.atled.core.db.query.constraints.SqlVarcharQueryConstraint.VarcharQueryOperation;

public class StackModule extends DatabaseGridModule {

public final static DatabaseDefinition DATABASE_DEFINITION;
	
	public final static DatabaseTableDefinition DATABASE_TABLE_DEFINITION;
	
	
	/*
	 * FIELD DEFINITIONS
	 */
	public final static VarcharFieldDefinition EXTERNAL_TYPE_FIELD = 
			new VarcharFieldDefinition("Pair Type Field", "PAIRTYPE", 4); // TODO enum field definition?
	
	public final static VarcharFieldDefinition INTERNAL_TYPE_FIELD = 
			new VarcharFieldDefinition("Dangle Type Field", "DANGLETYPE", 4); // TODO enum field definition?
	
	public final static VarcharFieldDefinition NUCLEOTIDE_INTERNAL_5_FIELD = 
			new VarcharFieldDefinition("5' Internal Nucleotide", 
			"NUCEOTIDEINTERNAL5", 1); // TODO enum field definition?
	
	public final static VarcharFieldDefinition NUCLEOTIDE_INTERNAL_3_FIELD  = 
			new VarcharFieldDefinition("3' Internal Nucleotide", 
			"NUCLEOTIDEINTERNAL3", 1); // TODO enum field definition?
	
	public final static VarcharFieldDefinition NUCLEOTIDE_EXTERNAL_5_FIELD = 
			new VarcharFieldDefinition("5' External Nucleotide", 
			"NUCEOTIDEEXTERNAL5", 1); // TODO enum field definition?
	
	public final static VarcharFieldDefinition NUCLEOTIDE_EXTERNAL_3_FIELD  = 
			new VarcharFieldDefinition("3' External Nucleotide", 
			"NUCLEOTIDEEXTERNAL3", 1); // TODO enum field definition?
	
	public final static VarcharFieldDefinition ARRAY_INDEX_FIELD;
	
	public final static VarcharFieldDefinition PARAM_VALUE_FIELD  = 
			new VarcharFieldDefinition("Parameter Value", "PARAMVALUE", 10);
	
	public final static StackFileIO stackFileIO = new StackFileIO();
	
	
	
	static {
		List<FieldCharacteristics> fieldChars = 
				new ArrayList<DatabaseFieldDefinition.FieldCharacteristics>();
		fieldChars.add(FieldCharacteristics.PRIMARY_KEY);
		fieldChars.add(FieldCharacteristics.NOT_NULL);
		ARRAY_INDEX_FIELD = new VarcharFieldDefinition("Array Index", 
				"ARRAYINDEX", fieldChars, 5);
		{
			List<DatabaseFieldDefinition> fields = 
					new ArrayList<DatabaseFieldDefinition>();
			fields.add(EXTERNAL_TYPE_FIELD);
			fields.add(INTERNAL_TYPE_FIELD);
			fields.add(NUCLEOTIDE_INTERNAL_5_FIELD);
			fields.add(NUCLEOTIDE_INTERNAL_3_FIELD);
			fields.add(NUCLEOTIDE_EXTERNAL_5_FIELD);
			fields.add(NUCLEOTIDE_EXTERNAL_3_FIELD);
			fields.add(ARRAY_INDEX_FIELD);
			fields.add(PARAM_VALUE_FIELD);
			DATABASE_TABLE_DEFINITION = new DatabaseTableDefinition("Stack", "STACK", 
					Collections.unmodifiableList(fields));
		}
		List<DatabaseTableDefinition> tables = new ArrayList<DatabaseTableDefinition>();
		tables.add(DATABASE_TABLE_DEFINITION);
		DATABASE_DEFINITION = new DatabaseDefinition("Param Editor GUI - Stack", 
				"parameditorgui_stack", tables);
	}
	
	
	/*
	 * STATIC HELPER METHODS
	 */
	public static String getBasePairTypeExternal(int row, int column) {
		return getBasePairType(getExternal5Nucletide(row), 
				getExternal3Nucletide(column));
	}
	
	public static String getBasePairTypeInternal(int row, int column) {
		return getBasePairType(getInternal5Nucletide(row), getInternal3Nucletide(row));
	}
	
	public static String getExternal5Nucletide(int row) {
		switch (row / 4) {
		case 0: return "A";
		case 1: return "C";
		case 2:return "G";
		case 3:return "U";
		}
		return null;
	}
	
	public static String getInternal5Nucletide(int column) {
		switch (column % 4) {
		case 0: return "A";
		case 1: return "C";
		case 2: return "G";
		case 3: return "U";
		}
		return null;
	}
	
	public static String getExternal3Nucletide(int column) {
		switch (column / 4) {
		case 0: return "A";
		case 1: return "C";
		case 2: return "G";
		case 3: return "U";
		}
		return null;
	}
	
	public static String getInternal3Nucletide(int row) {
		switch (row % 4) {
		case 0: return "A";
		case 1: return "C";
		case 2: return "G";
		case 3: return "U";
		}
		return null;
	}
	
	/*
	 * Convience methods
	 */
	
	public static SqlTableSelectQuery getStarTableQuery() {
		return new SqlTableSelectQuery(DATABASE_TABLE_DEFINITION);
	}
	
	public static SqlTableSelectQuery getExternalCannonicalBPTableQuery() {
		return new SqlTableSelectQuery(DATABASE_TABLE_DEFINITION,
				getExternalCannonicalBPConstraints());
	}
	
	public static SqlQueryConstraintCollection getExternalCannonicalBPConstraints() {
		SqlQueryConstraintCollection constraints = getOrConstraintCollection();
		constraints.addQuery(new SqlVarcharQueryConstraint(
				EXTERNAL_TYPE_FIELD, VarcharQueryOperation.EQUALS, 
				WATSON_CRICK_BP));
		constraints.addQuery(new SqlVarcharQueryConstraint(
				EXTERNAL_TYPE_FIELD, VarcharQueryOperation.EQUALS, WOBBLE_BP));
		return constraints;
	}
	
	public static SqlTableSelectQuery getInternalCannonicalBPTableQuery() {
		return new SqlTableSelectQuery(DATABASE_TABLE_DEFINITION,
				getInternalCannonicalBPConstraints());
	}
	
	public static SqlQueryConstraintCollection getInternalCannonicalBPConstraints() {
		SqlQueryConstraintCollection constraints = getOrConstraintCollection();
		constraints.addQuery(new SqlVarcharQueryConstraint(
				INTERNAL_TYPE_FIELD, VarcharQueryOperation.EQUALS, 
				WATSON_CRICK_BP));
		constraints.addQuery(new SqlVarcharQueryConstraint(
				INTERNAL_TYPE_FIELD, VarcharQueryOperation.EQUALS, WOBBLE_BP));
		return constraints;
	}
	
	public static SqlTableSelectQuery getBothCannonicalBPTableQuery() {
		SqlQueryConstraintCollection constraints = getAndConstraintCollection();
		constraints.addQuery(getExternalCannonicalBPConstraints());
		constraints.addQuery(getInternalCannonicalBPConstraints());
		return new SqlTableSelectQuery(DATABASE_TABLE_DEFINITION,
				constraints);
	}
	
	public static SqlTableSelectQuery getEitherCannonicalBPTableQuery() {
		SqlQueryConstraintCollection constraints = getOrConstraintCollection();
		constraints.addQuery(getExternalCannonicalBPConstraints());
		constraints.addQuery(getInternalCannonicalBPConstraints());
		return new SqlTableSelectQuery(DATABASE_TABLE_DEFINITION,
				constraints);
	}
}
