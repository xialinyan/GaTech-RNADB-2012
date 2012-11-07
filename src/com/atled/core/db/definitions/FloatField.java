package com.atled.core.db.definitions;

public class FloatField extends DatabaseFieldDefinition {

	public FloatField(String name, String dbFieldName) {
		super(name, dbFieldName);
	}

	@Override
	public String getSqlCreateStatement() {
		return "";
	}
}
