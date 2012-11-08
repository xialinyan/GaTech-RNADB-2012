package db.entry;

import parameditor.utils.DangleModule;

import com.atled.core.db.fields.DatabaseEntry;
import com.atled.core.db.fields.definitions.DatabaseFieldDefinition;
import com.atled.core.db.fields.definitions.DatabaseTableDefinition;
import com.atled.core.validation.ParameterChecker;

import db.DangleDbManager;

public class DangleEntry extends DatabaseEntry {
	
	public DangleEntry(int row, int column) {
		this(row, column, "inf");
	}
	
	public DangleEntry(int row, int column, String value){
		super(DangleModule.generateKey(row, column));
		ParameterChecker.notNull("value", value);
		
		DatabaseFieldDefinition field = DangleDbManager.ARRAY_INDEX_FIELD;
		fieldMap.put(field, DangleModule.generateKey(row, column));
		
		field = DangleDbManager.DANGLE_NUCLEOTIDE_FIELD;
		fieldMap.put(field, DangleModule.getDangleNucletide(column));
		
		field = DangleDbManager.LEAD_NUCLEOTIDE_FIELD;
		fieldMap.put(field, DangleModule.getLeadNucletide(row));
		
		field = DangleDbManager.TAIL_NUCLEOTIDE_FIELD;
		fieldMap.put(field, DangleModule.getTailNucletide(column));

		field = DangleDbManager.PAIR_TYPE_FIELD;
		fieldMap.put(field, DangleModule.getBasePairType(row, column));
		
		field = DangleDbManager.PARAM_VALUE_FIELD;
		fieldMap.put(field, value);
	}
	
	@Override
	public DatabaseTableDefinition getTableDefinition() {
		return DangleDbManager.DATABASE_TABLE_DEFINITION;
	}
	
	@Override
	public DatabaseFieldDefinition getPrimaryKeyFieldDefinition() {
		return DangleDbManager.ARRAY_INDEX_FIELD;
	}
	
	public static void main(String[] args) {
		DatabaseEntry entry = new DangleEntry(0, 0);
		System.out.println(entry.getInsertString());
		entry.setValue(DangleDbManager.PARAM_VALUE_FIELD, "-1.52");
		System.out.println(entry.getUpdateString(DangleDbManager.PARAM_VALUE_FIELD));
		entry.setValue(DangleDbManager.PARAM_VALUE_FIELD, "inf");
		System.out.println(entry.getUpdateString(DangleDbManager.PARAM_VALUE_FIELD));
	}
}
