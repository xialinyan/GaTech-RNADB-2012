package com.atled.core.db.fields.definitions;

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
	
	public String getDbName() {
		return dbName;
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
		sb.append("CREATE TABLE ").append(dbName).append(" (");
		DatabaseFieldDefinition primaryKey = null;
		List<DatabaseFieldDefinition> uniqueFields = new ArrayList<DatabaseFieldDefinition>();
		for (DatabaseFieldDefinition dbFieldDef : fields) {
			if (dbFieldDef.isPrimaryKey() || dbFieldDef.isUnique()) {
				if (dbFieldDef.isPrimaryKey()) {
					primaryKey = dbFieldDef;
				}
				if (dbFieldDef.isUnique()) {
					uniqueFields.add(dbFieldDef);
				}				
			}
			sb.append(dbFieldDef.getSqlCreateStatement()).append(", ");
		}
		for (DatabaseFieldDefinition dbFieldDef : uniqueFields) {
			sb.append("UNIQUE ").append(dbFieldDef.getDbFieldName()).append(", ");
		}
		if (primaryKey != null) {
			sb.append(" PRIMARY KEY (").append(primaryKey.getDbFieldName()).append(")");
		}
		if (fields.size() > 0) {
			sb.delete(sb.lastIndexOf(", "), sb.length());
		}
		sb.append(")");
		return sb.toString();
	}
	
	public String getDropStatement() {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("DROP TABLE ").append(this.dbName);
		return sqlBuilder.toString();
	}
}
