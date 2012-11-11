package db.entry;

import parameditor.utils.StackModule;

import com.atled.core.db.fields.DatabaseRow;
import com.atled.core.db.fields.definitions.DatabaseFieldDefinition;
import com.atled.core.db.fields.definitions.DatabaseTableDefinition;
import com.atled.core.validation.ParameterChecker;

public class StackRow extends DatabaseRow {

	public StackRow(String key) {
		this(StackModule.getRowFromKey(key), StackModule.getColumnFromKey(key), "inf");
	}
	
	public StackRow(String key, String value) {
		this(StackModule.getRowFromKey(key), StackModule.getColumnFromKey(key), value);
	}
	
	public StackRow(int row, int column) {
		this(row, column, "inf");
	}
	
	public StackRow(int row, int column, String value){
		super(StackModule.generateKey(row, column));
		ParameterChecker.notNull("value", value);

		DatabaseFieldDefinition field = StackModule.ARRAY_INDEX_FIELD;
		fieldMap.put(field, StackModule.generateKey(row, column));

		field = StackModule.INTERNAL_TYPE_FIELD;
		fieldMap.put(field, StackModule.getBasePairTypeInternal(row, column));

		field = StackModule.EXTERNAL_TYPE_FIELD;
		fieldMap.put(field, StackModule.getBasePairTypeExternal(row, column));
		
		field = StackModule.NUCLEOTIDE_INTERNAL_5_FIELD;
		fieldMap.put(field, StackModule.getInternal5Nucletide(column));
		
		field = StackModule.NUCLEOTIDE_INTERNAL_3_FIELD;
		fieldMap.put(field, StackModule.getInternal3Nucletide(row));
		
		field = StackModule.NUCLEOTIDE_EXTERNAL_5_FIELD;
		fieldMap.put(field, StackModule.getExternal5Nucletide(column));
		
		field = StackModule.NUCLEOTIDE_EXTERNAL_3_FIELD;
		fieldMap.put(field, StackModule.getExternal3Nucletide(row));

		
		field = StackModule.PARAM_VALUE_FIELD;
		fieldMap.put(field, value);
	}
	
	@Override
	public DatabaseTableDefinition getTableDefinition() {
		return StackModule.DATABASE_TABLE_DEFINITION;
	}
	
	@Override
	public DatabaseFieldDefinition getPrimaryKeyFieldDefinition() {
		return StackModule.ARRAY_INDEX_FIELD;
	}
	
	public String getArrayIndex() {
		Object obj = fieldMap.get(StackModule.ARRAY_INDEX_FIELD);
		return obj == null ? null : (String) obj;
	}
	
	public String getParamValue() {
		Object obj = fieldMap.get(StackModule.PARAM_VALUE_FIELD);
		return obj == null ? null : (String) obj;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("DangleEntry{key:").append(getKey()).append("\tfieldMap:");
		sb.append(fieldMap).append("}");
		return sb.toString();
	}
}
