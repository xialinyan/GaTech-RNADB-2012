package com.atled.core.db.fields.definitions;

public interface DatabaseEntity {

	/**
	 * @return Sql code to define this field in a CREATE statement.
	 */
	public String getSqlCreateStatement();
	
}
