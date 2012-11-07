package com.atled.core.db.query.constraints;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SqlQueryConstraintCollection implements SqlQueryConstraintGenerator {

	public static enum QueryCondition {
		AND("AND"),
		OR("OR");
		
		public final String sqlText;
		
		private QueryCondition(String sqlText) {
			this.sqlText = sqlText;
		}
	}
	
	public static final SqlQueryConstraintCollection getEmptyCollection() {
		return new SqlQueryConstraintCollection();
	}
	
	private final QueryCondition condition;
	private final List<SqlQueryConstraintGenerator> queries;
	
	protected SqlQueryConstraintCollection() {
		this.condition = QueryCondition.AND;
		this.queries = Collections.emptyList();
	}
	
	public SqlQueryConstraintCollection(QueryCondition condition) {
		this.condition = condition;
		this.queries = new ArrayList<SqlQueryConstraintGenerator>();
	}
	
	public void clear() {
		this.queries.clear();
	}
	
	public boolean isEmpty() {
		return queries.isEmpty();
	}
	
	public void addQuery(SqlQueryConstraintGenerator queryGenerator) {
		if (queryGenerator != null) {
			this.queries.add(queryGenerator);
		}
	}

	@Override
	public String generateSql() {
		StringBuilder sqlBuiler = new StringBuilder();
		if (queries.size() > 0) {
			if (queries.size() > 1) {
				sqlBuiler.append("(");
			}
			sqlBuiler.append(queries.get(0).generateSql());
			if (queries.size() > 1) {
				for (int i=1;i<queries.size();i++) {
					sqlBuiler.append(" ").append(condition.sqlText).append(" ");
					sqlBuiler.append(queries.get(i).generateSql());
				}
				sqlBuiler.append(")");
			}
		}
		return sqlBuiler.toString();
	}
	
	
	
}
