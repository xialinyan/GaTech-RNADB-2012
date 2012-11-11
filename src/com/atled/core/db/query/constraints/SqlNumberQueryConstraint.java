package com.atled.core.db.query.constraints;

import com.atled.core.db.fields.definitions.DatabaseFieldDefinition;

public abstract class SqlNumberQueryConstraint<T extends Number> extends SqlFieldQueryConstraint {

	public static enum NumberQueryOperation {
		EQUALS("=", "'"),
		NOT_EQUALS("!=", "'");
		
		public final String sqlText;
		public final String querySurroundingString;
		
		private NumberQueryOperation(String sqlText, String querySurroundingString) {
			this.sqlText = sqlText;
			this.querySurroundingString = querySurroundingString;
		}
	}
	
	private final NumberQueryOperation operation;
	private final T queryValue;

	public NumberQueryOperation getOperation() {
		return operation;
	}

	public Number getQueryValue() {
		return queryValue;
	}
	
	public SqlNumberQueryConstraint(DatabaseFieldDefinition field, NumberQueryOperation operation, T queryValue) {
		super(field);
		this.operation = operation;
		this.queryValue = queryValue;
	}

	@Override
	public String generateSql() {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(super.getField().getDbFieldName());
		sqlBuilder.append(operation.sqlText).append(operation.querySurroundingString);
		sqlBuilder.append(queryValue).append(operation.querySurroundingString);
		return sqlBuilder.toString();
	}

}
