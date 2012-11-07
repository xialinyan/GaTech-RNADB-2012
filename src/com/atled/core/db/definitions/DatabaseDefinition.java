package com.atled.core.db.definitions;

import java.util.Collections;
import java.util.List;

/**
 * This class defined a database.
 *
 * @author David Esposito
 */
public class DatabaseDefinition {

	private final String name;
	private final String dbName;
	private final List<DatabaseTableDefinition> tables;
	
	public String getName() {
		return name;
	}
	
	public String getDbName() {
		return dbName;
	}
	
	public List<DatabaseTableDefinition> getTableDefinitions() {
		return tables;
	}
	
	/**
	 * @param name Human readable name of this database.
	 * @param dbName Actual database name to be used in SQL statements.
	 * @param tables Tables to be included in this database.
	 */
	public DatabaseDefinition(String name, String dbName, List<DatabaseTableDefinition> tables) {
		this.name = name;
		this.dbName = dbName;
		this.tables = Collections.unmodifiableList(tables);
	}
	
}
