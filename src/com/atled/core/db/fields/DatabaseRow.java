package com.atled.core.db.fields;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import parameditor.utils.DangleModule;

import com.atled.core.db.fields.definitions.DatabaseFieldDefinition;
import com.atled.core.db.fields.definitions.DatabaseTableDefinition;
import com.atled.core.validation.ParameterChecker;

public abstract class DatabaseRow {

	private final String key;
	// TODO wrapper around SavableObject
	protected final Map<DatabaseFieldDefinition, Object> fieldMap;
	
	public DatabaseRow(String key) {
		fieldMap = new HashMap<DatabaseFieldDefinition, Object>();
		this.key = key;
	}
	
	protected String getKey() {
		return key;
	}
	
	public abstract DatabaseFieldDefinition getPrimaryKeyFieldDefinition();
	
	public abstract DatabaseTableDefinition getTableDefinition();

	public String getInsertString() {
		StringBuilder sqlBuilder = new StringBuilder();
		DatabaseTableDefinition tableDef = DangleModule.DATABASE_TABLE_DEFINITION;
		sqlBuilder.append("INSERT INTO ").append(tableDef.getDbName()).append(" ");
		StringBuilder fieldNames = new StringBuilder("(");
		StringBuilder fieldValues = new StringBuilder("(");
		{	// indented to group similar code
			for (Entry<DatabaseFieldDefinition, Object> e : fieldMap.entrySet()) {
				fieldNames.append("").append(e.getKey().getDbFieldName()).append(", ");
				fieldValues.append("'").append(e.getValue()).append("', ");
			}
			fieldNames.delete(fieldNames.lastIndexOf(", "), fieldNames.length());
			fieldValues.delete(fieldValues.lastIndexOf(", "), fieldValues.length());
			fieldNames.append(")");
			fieldValues.append(")");
		}
		sqlBuilder.append(fieldNames).append(" VALUES ").append(fieldValues);
		return sqlBuilder.toString();
	}

	public String getUpdateString(DatabaseFieldDefinition updateField) {
		ParameterChecker.notNull("updateField", updateField);
		StringBuilder sqlBuilder = new StringBuilder();
		DatabaseTableDefinition tableDef = getTableDefinition();
		DatabaseFieldDefinition keyDef = getPrimaryKeyFieldDefinition();
		sqlBuilder.append("UPDATE ").append(tableDef.getDbName()).append(" SET ");
		sqlBuilder.append(updateField.getDbFieldName()).append("='");
		sqlBuilder.append(fieldMap.get(updateField)).append("' WHERE ");
		sqlBuilder.append(keyDef.getDbFieldName()).append("='").append(getKey());
		sqlBuilder.append("'");
		return sqlBuilder.toString();
	}
	
	public void setFieldValue(DatabaseFieldDefinition fieldDefinition, Object fieldValue) {
		ParameterChecker.notNull("fieldDefinition", fieldDefinition);
		ParameterChecker.matchClass("fieldValue", fieldValue, String.class);
		// TODO: check that field already exists? check class?
		String value = fieldValue.toString(); // TODO: this is going to be wrong someday...
		fieldMap.put(fieldDefinition, value);
	}
	
	public Object getFieldValue(DatabaseFieldDefinition fieldDefinition) {
		ParameterChecker.notNull("fieldDefinition", fieldDefinition);
		return fieldMap.get(fieldDefinition);
	}
}
