package db.entry;

import parameditor.utils.DangleModule;

import com.atled.core.db.fields.DatabaseRow;
import com.atled.core.db.fields.definitions.DatabaseFieldDefinition;
import com.atled.core.db.fields.definitions.DatabaseTableDefinition;
import com.atled.core.validation.ParameterChecker;

public class DangleRow extends DatabaseRow {
	
	public DangleRow(String key) {
		this(DangleModule.getRowFromKey(key), DangleModule.getColumnFromKey(key), "inf");
	}
	
	public DangleRow(String key, String value) {
		this(DangleModule.getRowFromKey(key), DangleModule.getColumnFromKey(key), value);
	}
	
	public DangleRow(int row, int column) {
		this(row, column, "inf");
	}
	
	public DangleRow(int row, int column, String value){
		super(DangleModule.generateKey(row, column));
		ParameterChecker.notNull("value", value);
		
		DatabaseFieldDefinition field = DangleModule.ARRAY_INDEX_FIELD;
		fieldMap.put(field, DangleModule.generateKey(row, column));
		
		field = DangleModule.DANGLE_NUCLEOTIDE_FIELD;
		fieldMap.put(field, DangleModule.getDangleNucletide(column));
		
		field = DangleModule.LEAD_NUCLEOTIDE_FIELD;
		fieldMap.put(field, DangleModule.getLeadNucletide(row));
		
		field = DangleModule.TAIL_NUCLEOTIDE_FIELD;
		fieldMap.put(field, DangleModule.getTailNucletide(column));

		field = DangleModule.PAIR_TYPE_FIELD;
		fieldMap.put(field, DangleModule.getBasePairType(row, column));
		
		field = DangleModule.PARAM_VALUE_FIELD;
		fieldMap.put(field, value);
	}
	
	@Override
	public DatabaseTableDefinition getTableDefinition() {
		return DangleModule.DATABASE_TABLE_DEFINITION;
	}
	
	@Override
	public DatabaseFieldDefinition getPrimaryKeyFieldDefinition() {
		return DangleModule.ARRAY_INDEX_FIELD;
	}
	
	public String getArrayIndex() {
		Object obj = fieldMap.get(DangleModule.ARRAY_INDEX_FIELD);
		return obj == null ? null : (String) obj;
	}
	
	public String getDangleNucleotide() {
		Object obj = fieldMap.get(DangleModule.DANGLE_NUCLEOTIDE_FIELD);
		return obj == null ? null : (String) obj;
	}
	
	public String getLeadNucleotide() {
		Object obj = fieldMap.get(DangleModule.LEAD_NUCLEOTIDE_FIELD);
		return obj == null ? null : (String) obj;
	}
	
	public String getPairType() {
		Object obj = fieldMap.get(DangleModule.PAIR_TYPE_FIELD);
		return obj == null ? null : (String) obj;
	}
	
	public String getParamValue() {
		Object obj = fieldMap.get(DangleModule.PARAM_VALUE_FIELD);
		return obj == null ? null : (String) obj;
	}
	
	public String getTailNucleotide() {
		Object obj = fieldMap.get(DangleModule.TAIL_NUCLEOTIDE_FIELD);
		return obj == null ? null : (String) obj;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("DangleEntry{key:").append(getKey()).append("\tfieldMap:");
		sb.append(fieldMap).append("}");
		return sb.toString();
	}
	
	public static void main(String[] args) {
		DatabaseRow entry = new DangleRow(0, 0);
		System.out.println(entry.getInsertString());
		entry.setFieldValue(DangleModule.PARAM_VALUE_FIELD, "-1.52");
		System.out.println(entry.getUpdateString(DangleModule.PARAM_VALUE_FIELD));
		entry.setFieldValue(DangleModule.PARAM_VALUE_FIELD, "inf");
		System.out.println(entry.getUpdateString(DangleModule.PARAM_VALUE_FIELD));
	}
}
