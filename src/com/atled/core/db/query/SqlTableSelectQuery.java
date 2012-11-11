package com.atled.core.db.query;

import java.util.List;

import com.atled.core.db.fields.definitions.DatabaseFieldDefinition;
import com.atled.core.db.fields.definitions.DatabaseTableDefinition;
import com.atled.core.db.query.constraints.SqlQueryConstraintCollection;

public class SqlTableSelectQuery {

	private final DatabaseTableDefinition tableDefinition;
	private final SqlQueryConstraintCollection constraintCollection;
	
	public SqlTableSelectQuery(DatabaseTableDefinition tableDefinition) {
		this(tableDefinition, SqlQueryConstraintCollection.getEmptyCollection());
	}
	
	public SqlTableSelectQuery(DatabaseTableDefinition tableDefinition, 
			SqlQueryConstraintCollection constraintCollection) {
		this.tableDefinition = tableDefinition;
		this.constraintCollection = constraintCollection;
	}
	
	public String generateSql() {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT * FROM ").append(tableDefinition.getDbName()).append("");
		if (!constraintCollection.isEmpty()) {			
			sqlBuilder.append(" WHERE ").append(constraintCollection.generateSql());
		}
		return sqlBuilder.toString();
	}
	
	public String generateSql(List<DatabaseFieldDefinition> fieldDefinitions) {
		if (fieldDefinitions.isEmpty()) {
			throw new IllegalArgumentException("Must select at least one field");
		}
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT ").append(fieldDefinitions.get(0).getDbFieldName());
		for (int i=1;i<fieldDefinitions.size();i++) {
			sqlBuilder.append(", ").append(fieldDefinitions.get(i).getDbFieldName());
		}
		sqlBuilder.append(" FROM ").append(tableDefinition.getDbName());
		if (!constraintCollection.isEmpty()) {			
			sqlBuilder.append(" WHERE ").append(constraintCollection.generateSql());
		}
		return sqlBuilder.toString();
	}
}
