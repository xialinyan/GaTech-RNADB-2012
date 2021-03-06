package com.atled.core.db.query.constraints;

import com.atled.core.db.fields.definitions.DatabaseFieldDefinition;

public class SqlRangeQueryConstraint extends SqlFieldQueryConstraint {

	public static enum RangeQueryOperation {
		LESS_THAN("<"),
		GREATER_THAN(">"),
		LESS_THAN_EQULAL("<="),
		GREATER_THAN_EQUAL(">="),
		INCLUSIVE_RANGE("<=", ">="),
		EXCLUSIVE_RANGE("<", ">");
		
		public final String sqlText1, sqlText2;
		
		private RangeQueryOperation(String sqlText) {
			this(sqlText, null);
		}
		
		private RangeQueryOperation(String sqlText1, String sqlText2) {
			this.sqlText1 = sqlText1;
			this.sqlText2 = sqlText2;
		}
	}
	
	private final RangeQueryOperation operation;
	private final Object queryMinValue;
	private final Object queryMaxValue;

	public RangeQueryOperation getOperation() {
		return operation;
	}

	public Object getQueryMinValue() {
		return queryMinValue;
	}

	public Object getQueryMaxValue() {
		return queryMaxValue;
	}
	
	public SqlRangeQueryConstraint(DatabaseFieldDefinition field, RangeQueryOperation operation, 
			Object queryValue) {
		this(field, operation, queryValue, null);
	}
	
	public SqlRangeQueryConstraint(DatabaseFieldDefinition field, RangeQueryOperation operation, 
			Object queryMinValue, Object queryMaxValue) {
		super(field);
		this.operation = operation;
		this.queryMinValue = queryMinValue;
		this.queryMaxValue = queryMaxValue;
		checkValidState(operation, queryMinValue, queryMaxValue);
	}
	
	private void checkValidState(RangeQueryOperation op, Object min, Object max) {
		if (min == null) {
			throw new IllegalArgumentException("At least one number must be provided to a " +
					"range query.");
		}
		switch(op) {
		case LESS_THAN:
		case LESS_THAN_EQULAL:
		case GREATER_THAN:
		case GREATER_THAN_EQUAL:
			if (max != null) {
				throw new IllegalArgumentException("Two arguments cannot be provided to a " +
						"single bouded range.");
			}
			break;
		case INCLUSIVE_RANGE:
		case EXCLUSIVE_RANGE:
			if (max == null) {
				throw new IllegalArgumentException("Two arguments must be provided to a " +
						"double bounded range.");
			}
			break;
		default:
			throw new IllegalArgumentException("Invalid Number range operation");
		}
	}

	@Override
	public String generateSql() {
		StringBuilder sqlBuilder = new StringBuilder();
		switch(operation) {
		case LESS_THAN:
		case LESS_THAN_EQULAL:
		case GREATER_THAN:
		case GREATER_THAN_EQUAL: 
			sqlBuilder.append(getSingleParamQueryString(operation.sqlText1, queryMinValue.toString()));
			break;
		case INCLUSIVE_RANGE:
		case EXCLUSIVE_RANGE:
			sqlBuilder.append("(");
			sqlBuilder.append(getSingleParamQueryString(operation.sqlText1, queryMinValue.toString()));
			sqlBuilder.append(" AND ");
			sqlBuilder.append(getSingleParamQueryString(operation.sqlText2, queryMaxValue.toString()));
			sqlBuilder.append(")");
		}
		return sqlBuilder.toString();
	}

	private String getSingleParamQueryString(String text, String value) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(super.getField().getDbFieldName());
		sqlBuilder.append(text).append("'").append(queryMinValue.toString());
		return sqlBuilder.append("'").toString();	
	}

}
