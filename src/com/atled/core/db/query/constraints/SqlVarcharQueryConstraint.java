package com.atled.core.db.query.constraints;

import com.atled.core.db.definitions.DatabaseFieldDefinition;

public class SqlVarcharQueryConstraint extends SqlFieldQueryConstraint {

	public static enum VarcharQueryOperation { 
		LIKE(" LIKE ", "'%", "%'"),
		EQUALS("=", "'"),
		NOT_EQUALS("!=", "'");
		
		public final String sqlText;
		public final String queryPrefix;
		public final String queryPostfix;
		
		private VarcharQueryOperation(String sqlText, String querySurroundingString) {
			this (sqlText, querySurroundingString, querySurroundingString);
		}
		
		private VarcharQueryOperation(String sqlText, String queryPrefix, String queryPostfix) {
			this.sqlText = sqlText;
			this.queryPrefix = queryPrefix;
			this.queryPostfix = queryPostfix;
		}
	}
	
	private final VarcharQueryOperation operation;
	private final String queryValue;
	
	public VarcharQueryOperation getOperation() {
		return operation;
	}
	
	public String getQueryValue() {
		return queryValue;
	}
	
	public SqlVarcharQueryConstraint(DatabaseFieldDefinition field, VarcharQueryOperation operation, String queryValue) {
		super(field);
		this.operation = operation;
		this.queryValue = queryValue;
	}

	@Override
	public String generateSql() {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("`").append(super.getField().getName()).append("`");
		sqlBuilder.append(operation.sqlText).append(operation.queryPrefix);
		sqlBuilder.append(queryValue).append(operation.queryPostfix);
		return sqlBuilder.toString();
	}
}
