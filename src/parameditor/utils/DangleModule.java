package parameditor.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import turner99parsing.DangleFileIO;

import com.atled.core.db.fields.definitions.DatabaseDefinition;
import com.atled.core.db.fields.definitions.DatabaseFieldDefinition;
import com.atled.core.db.fields.definitions.DatabaseFieldDefinition.FieldCharacteristics;
import com.atled.core.db.fields.definitions.DatabaseTableDefinition;
import com.atled.core.db.fields.definitions.VarcharFieldDefinition;
import com.atled.core.db.query.SqlTableSelectQuery;
import com.atled.core.db.query.constraints.SqlQueryConstraintCollection;
import com.atled.core.db.query.constraints.SqlVarcharQueryConstraint;
import com.atled.core.db.query.constraints.SqlVarcharQueryConstraint.VarcharQueryOperation;


public class DangleModule extends DatabaseModule {

	
	public final static DatabaseDefinition DATABASE_DEFINITION;
	
	public final static DatabaseTableDefinition DATABASE_TABLE_DEFINITION;
	
	
	/*
	 * FIELD DEFINITIONS
	 */
	public final static VarcharFieldDefinition PAIR_TYPE_FIELD = 
			new VarcharFieldDefinition("pairTypeField", "PAIRTYPE", 4); // TODO enum field definition?
	
	public final static VarcharFieldDefinition NUCLEOTIDE_5_FIELD = 
			new VarcharFieldDefinition("5' Nucleotide", 
			"NUCEOTIDE5", 1); // TODO enum field definition?
	
	public final static VarcharFieldDefinition NUCLEOTIDE_3_FIELD  = 
			new VarcharFieldDefinition("3' Nucleotide", 
			"NUCLEOTIDE3", 1); // TODO enum field definition?
	
	public final static VarcharFieldDefinition DANGLE_NUCLEOTIDE_FIELD 
			= new VarcharFieldDefinition("dangleNucleotideField", 
			"DANGLENUCLEOTIDE", 1); // TODO enum field definition?
	
	
	public final static VarcharFieldDefinition ARRAY_INDEX_FIELD;
	
	public final static VarcharFieldDefinition PARAM_VALUE_FIELD  = 
			new VarcharFieldDefinition("paramValueField", "PARAMVALUE", 10);
	
	public final static DangleFileIO dangleFileIO = new DangleFileIO();
	
	
	
	static {
		List<FieldCharacteristics> fieldChars = 
				new ArrayList<DatabaseFieldDefinition.FieldCharacteristics>();
		fieldChars.add(FieldCharacteristics.PRIMARY_KEY);
		fieldChars.add(FieldCharacteristics.NOT_NULL);
		ARRAY_INDEX_FIELD = new VarcharFieldDefinition("arrayIndexField", 
				"ARRAYINDEX", fieldChars, 5);
		{
			List<DatabaseFieldDefinition> fields = 
					new ArrayList<DatabaseFieldDefinition>();
			fields.add(PAIR_TYPE_FIELD);
			fields.add(NUCLEOTIDE_5_FIELD);
			fields.add(NUCLEOTIDE_3_FIELD);
			fields.add(DANGLE_NUCLEOTIDE_FIELD);
			fields.add(ARRAY_INDEX_FIELD);
			fields.add(PARAM_VALUE_FIELD);
			DATABASE_TABLE_DEFINITION = new DatabaseTableDefinition("Dangle", "DANGLE", 
					Collections.unmodifiableList(fields));
		}
		List<DatabaseTableDefinition> tables = new ArrayList<DatabaseTableDefinition>();
		tables.add(DATABASE_TABLE_DEFINITION);
		DATABASE_DEFINITION = new DatabaseDefinition("Param Editor GUI - Dangle", 
				"parameditorgui_dangle", tables);
	}
	
	
	/*
	 * STATIC HELPER METHODS
	 */
	public static String generateKey(int row, int column) {
		return row + ":" + column;
	}
	
	public static int getRowFromKey(String key) {
		String[] arr = key.split(":");
		return Integer.parseInt(arr[0]);
	}
	
	public static int getColumnFromKey(String key) {
		String[] arr = key.split(":");
		return Integer.parseInt(arr[1]);
	}
	
	public static String getBasePairType(int row, int column) {
		return getBasePairType(getLeadNucletide(row), getTailNucletide(column));
	}
	
	public static String getBasePairType(String lead, String tail) {
		if (lead.equals("A")) {
			return tail.equals("U") ? WATSON_CRICK_BP : "";
		} else if (lead.equals("C")) {
			return tail.equals("G") ? WATSON_CRICK_BP : "";
		} else if (lead.equals("G")) {
			return tail.equals("C") ? WATSON_CRICK_BP : tail.equals("U") ? WOBBLE_BP : "";
		} else if (lead.equals("U")) {
			return tail.equals("A") ? WATSON_CRICK_BP : tail.equals("G") ? WOBBLE_BP : "";
		}
		return null;
	}
	
	public static String getLeadNucletide(int row) {
		switch (row) {
		case 0: 
		case 4: return "A";
		case 1: 
		case 5: return "C";
		case 2:
		case 6: return "G";
		case 3:
		case 7: return "U";
		}
		return null;
	}
	
	public static String getTailNucletide(int column) {
		switch (column / 4) {
		case 0: return "A";
		case 1: return "C";
		case 2: return "G";
		case 3: return "U";
		}
		return null;
	}
	
	public static String getDangleNucletide(int column) {
		switch (column % 4) {
		case 0: return "A";
		case 1: return "C";
		case 2: return "G";
		case 3: return "U";
		}
		return null;
	}
	
	public static SqlTableSelectQuery getStarTableQuery() {
		return new SqlTableSelectQuery(DangleModule.DATABASE_TABLE_DEFINITION);
	}
	
	public static SqlTableSelectQuery getCannonicalBPTableQuery() {
		SqlQueryConstraintCollection constraints = getOrConstraintCollection();
		constraints.addQuery(new SqlVarcharQueryConstraint(
				DangleModule.PAIR_TYPE_FIELD, VarcharQueryOperation.EQUALS, 
				WATSON_CRICK_BP));
		constraints.addQuery(new SqlVarcharQueryConstraint(
				DangleModule.PAIR_TYPE_FIELD, VarcharQueryOperation.EQUALS, WOBBLE_BP));
		return new SqlTableSelectQuery(DangleModule.DATABASE_TABLE_DEFINITION,
				constraints);
	}
}