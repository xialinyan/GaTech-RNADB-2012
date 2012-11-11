package com.atled.core.db.query;

import java.util.List;

import com.atled.core.db.fields.definitions.DatabaseFieldDefinition;
import com.atled.core.db.fields.definitions.DatabaseTableDefinition;

public class SqlTableCreateQuery {

	private final DatabaseTableDefinition tableDefinition;
	
	public SqlTableCreateQuery(DatabaseTableDefinition tableDefinition) {
		this.tableDefinition = tableDefinition;
	}
	
	public String generateSql() {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("CREATE TABLE ").append(tableDefinition.getDbName()).append(" (");
		List<DatabaseFieldDefinition> fieldDefinitions = tableDefinition.getFieldDefinitions();
		if (!fieldDefinitions.isEmpty()) {
			sqlBuilder.append(fieldDefinitions.get(0).getSqlCreateStatement());
			if (fieldDefinitions.size() > 1) {
				for (int i=1;i<fieldDefinitions.size();i++) {
					sqlBuilder.append(", ").append(fieldDefinitions.get(i).getSqlCreateStatement());
				}
			}
		}
		sqlBuilder.append(");");
		return sqlBuilder.toString();
	}
}
