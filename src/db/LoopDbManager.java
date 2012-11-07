package db;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.atled.core.db.DefaultDbManager;
import com.atled.core.db.definitions.DatabaseDefinition;

public class LoopDbManager extends DefaultDbManager {
	
	public LoopDbManager(DatabaseDefinition databaseDefinition) {
		super(databaseDefinition);
		// TODO
	}

	@Override
	public boolean init(String fileName) throws IOException {
		// TODO 
		/**
		 * @step connect to db. If connection failed, return false
		 */
		openConnection();
		/**
		 * @step If the table does not exist in the database, this method creates it else, if 
		 * it does exist, this method truncates the table.
		 */
//		st.executeUpdate("CREATE TABLE DANGLE( " +
//		"'ID'	INT	PRIMARY KEY, " +
//		"'LOWERBP'	VARCHAR(1)	NOT NULL, " +
//		"'UPPERBP'	VARCHAR(1)	NOT NULL, " +
//		"'DANGLEB'	VARCHAR(1)	NOT NULL, " +
//		"'PRIMESTART'	VARCHAR(1) NOT NULL, " +
//		"'VALUE'	FLOAT");
		/**
		 * @step This method should use a class which implements the ReadFile interface to get 
		 * the parameters to insert.
		 */
		/**
		 * @step Once this method has the results from ReadFile, it should call this.insert(...) for
		 * each parameter. Ensure that each entry was inserted sucessfully.
		 */
		/**
		 * @step return sucess of initialization
		 */
		return false;
	}

	@Override
	public boolean insert(List<String> array) throws SQLException {
		// TODO
		/**
		 * @step ensure db is connected and initialized
		 */
		/**
		 * @step convert <code>List</code> of <code>String</code> to update SQL query.
		 */
		/**
		 * @step execute query
		 */
		/**
		 * @step return success of insertion
		 */
		return false;
	}

	@Override
	public boolean update(List<String> array) throws SQLException {
		// TODO
		/**
		 * @step ensure db is connected and initialized
		 */
		/**
		 * @step convert <code>List</code> of <code>String</code> to update SQL query.
		 */
		/**
		 * @step execute query
		 */
		/**
		 * @step return success of update
		 */ 
		return false;
	}

	@Override
	public ResultSet search(List<String> array) throws SQLException {
		// TODO 
		/**
		 * @step ensure db is connected and initialized
		 */
		/**
		 * @step convert <code>List</code> of <code>String</code> to update SQL query.
		 */
		/**
		 * @step execute query
		 */
		/**
		 * @step return results set
		 */
		return null;
	}
}
