package com.atled.core.db.definitions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class DatabaseTableDefinition implements DatabaseEntity {

	private final String name;
	private final String dbName;
	private final List<DatabaseFieldDefinition> fields;
	
	public String getName() {
		return name;
	}
	
	public List<DatabaseFieldDefinition> getFieldDefinitions() {
		return fields;
	}
	
	public DatabaseTableDefinition(String name, String dbName, List<DatabaseFieldDefinition> fields) {
		this.name = name;
		this.dbName = dbName;
		this.fields = Collections.unmodifiableList(fields);
	}

	@Override
	public String getSqlCreateStatement() {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE ").append(dbName).append("(");
		DatabaseFieldDefinition primaryKey = null;
		List<DatabaseFieldDefinition> uniqueFields = new ArrayList<DatabaseFieldDefinition>();
		for (DatabaseFieldDefinition dbFieldDef : fields) {
			sb.append(dbFieldDef.getSqlCreateStatement()).append(", ");
			if (dbFieldDef.isPrimaryKey()) {
				primaryKey = dbFieldDef;
			}
			if (dbFieldDef.isUnique()) {
				uniqueFields.add(dbFieldDef);
			}
		}
		for (DatabaseFieldDefinition dbFieldDef : uniqueFields) {
			sb.append("UNIQUE ").append(dbFieldDef.getDbFieldName()).append(", ");
		}
		if (primaryKey != null) {
			sb.append(" PRIMARY KEY ").append(primaryKey.getDbFieldName());
		}
		if (fields.size() > 0) {
			sb.delete(sb.lastIndexOf(", "), sb.length());
		}
		sb.append(")");
		return sb.toString();
	}
}
