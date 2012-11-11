package com.atled.core.db;
import java.io.IOException;
import java.util.List;

import com.atled.core.db.fields.DatabaseRow;
import com.atled.core.db.fields.definitions.DatabaseFieldDefinition;
import com.atled.core.db.query.SqlTableSelectQuery;
public interface DbManager {
	
	/**
	 * @step connect to db. If connection failed, return false
	 * @step If the table does not exist in the database, this method creates it else, if 
	 * it does exist, this method truncates the table.
	 * @step This method should use a class which implements the ReadFile interface to get 
	 * the parameters to insert.
	 * @step Once this method has the results from ReadFile, it should call this.insert(...) for
	 * each parameter. Ensure that each entry was inserted sucessfully.
	 * @step set initialized to true
	 * @step return sucess of initialization
	 * 
	 * @param fileName
	 * @return 
	 * @throws IOException
	 */
	public boolean init(String fileName);
	
	/**
	 * @step ensure db is connected and initialized
	 * @step convert <code>List</code> of <code>String</code> to insert SQL query.
	 * @step execute query
	 * @step return success of update
	 * 
	 * @param array
	 * @return
	 */
	public boolean update(DatabaseRow entry, DatabaseFieldDefinition updateField);
	
	/**
	 * @step ensure db is connected and initialized
	 * @step convert <code>List</code> of <code>String</code> to insert SQL query.
	 * @step execute query
	 * @step return result set
	 * 
	 * @param array We will want to take in an array with params in the order of the table
	 * @return
	 */
	public List<DatabaseRow> search(SqlTableSelectQuery query);
	
	/**
	 * @step Connect to correct database and create if necessary.
	 */
	public void openConnection();
	
	public boolean isConnected();
	
	/**
	 * @step If connection is currently open, close the connection. 
	 * @step start garbage collector
	 */
	public void closeConnection();
	
	public boolean export(String filename);
}
