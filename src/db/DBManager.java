package db;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
public interface DBManager {
	
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
	 * @retun 
	 * @throws IOException
	 */
	public boolean init(String fileName) throws IOException;
	
	/**
	 * @step ensure db is connected and initialized
	 * @step convert <code>List</code> of <code>String</code> to update SQL query.
	 * @step execute query
	 * @step return success of insertion
	 * 
	 * @param array
	 * @return
	 * @throws SQLException 
	 */
	public boolean insert(List<String> array) throws SQLException;
	
	/**
	 * @step ensure db is connected and initialized
	 * @step convert <code>List</code> of <code>String</code> to insert SQL query.
	 * @step execute query
	 * @step return success of update
	 * 
	 * @param array
	 * @return
	 */
	public boolean update(List<String> array) throws SQLException;
	
	/**
	 * @step ensure db is connected and initialized
	 * @step convert <code>List</code> of <code>String</code> to insert SQL query.
	 * @step execute query
	 * @step return result set
	 * 
	 * @param array We will want to take in an array with params in the order of the table
	 * @return
	 */
	public ResultSet search(List<String> array) throws SQLException;
	
	/**
	 * @step If connection is currently open, close the connection.
	 * @step set initialized to false 
	 * @step start garbage collector
	 * @step Return the close connection status
	 * 
	 * @return True if connection is not currently open.
	 */
	public boolean closeConnection();
}
