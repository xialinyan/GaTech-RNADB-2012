package db.entry;

import java.util.HashMap;
import java.util.Map;

import com.atled.core.db.fields.DatabaseEntry;
import com.atled.core.db.fields.definitions.DatabaseFieldDefinition;

import db.DangleDbManager;

public class DangleEntry extends DatabaseEntry {

	public static String generateKey(int row, int column) {
		return row + ":" + column;
	}
	
	public static String getBasePairType(int row, int column) {
		return getBasePairType(getLeadNucletide(row), getTailNucletide(column));
	}
	
	public static String getBasePairType(String lead, String tail) {
		if (lead.equals("A")) {
			return tail.equals("U") ? "WC" : "";
		} else if (lead.equals("C")) {
			return tail.equals("G") ? "WC" : "";
		} else if (lead.equals("G")) {
			return tail.equals("C") ? "WC" : tail.equals("U") ? "W" : "";
		} else if (lead.equals("U")) {
			return tail.equals("A") ? "WC" : tail.equals("G") ? "W" : "";
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
	
	private final String key;
	private final Map<String, String> fieldMap;
	
	public DangleEntry(int row, int column) {
		this(row, column, "inf");
	}
	
	public DangleEntry(int row, int column, String value){
		this.key = generateKey(row, column);
		fieldMap = new HashMap<String, String>();
		
		DatabaseFieldDefinition field = DangleDbManager.ARRAY_INDEX_FIELD;
		fieldMap.put(field.getName(), key);
		
		field = DangleDbManager.DANGLE_NUCLEOTIDE_FIELD;
		fieldMap.put(field.getName(), getDangleNucletide(column));
		
		
		field = DangleDbManager.LEAD_NUCLEOTIDE_FIELD;
		fieldMap.put(field.getName(), getLeadNucletide(row));
		
		field = DangleDbManager.TAIL_NUCLEOTIDE_FIELD;
		fieldMap.put(field.getName(), getTailNucletide(column));

		field = DangleDbManager.PAIR_TYPE_FIELD;
		fieldMap.put(field.getName(), getBasePairType(row, column));
		
		field = DangleDbManager.PARAM_VALUE_FIELD;
		fieldMap.put(field.getName(), value);
	}
	
	@Override
	public String getKey() {
		return key;
	}
}
