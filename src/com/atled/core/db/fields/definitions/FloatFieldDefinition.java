package com.atled.core.db.fields.definitions;

public class FloatFieldDefinition extends DatabaseFieldDefinition {

	public FloatFieldDefinition(String name, String dbFieldName) {
		super(name, dbFieldName);
	}

	@Override
	public String getSqlCreateStatement() {
		return "";
	}
}
