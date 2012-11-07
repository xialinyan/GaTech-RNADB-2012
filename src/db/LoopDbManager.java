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

		Connection con = null;
		Statement st = null;
		List<List<String>> value = new ArrayList<List<String>>();
		DangleFileIO dangle;
		loop = new LoopFileIO();
		value = loop.read("loop.DAT");
		
		

		String url = "jdbc:derby:loopDb;create=true";
		
		try {
			System.setProperty("derby.system.home", 
		                    "/home/xialin/Documents/eclipse/Research/Derby");
			con = DriverManager.getConnection(url);
			st = con.createStatement();
			st.executeUpdate("CREATE TABLE LOOP( " +
			"'ID'	VARCHAR(3)	PRIMARY KEY, " +
			"'SIZE'	VARCHAR(2)	NOT NULL, " +
			"'TYPE'	VARCHAR(1)	NOT NULL, " +
			"'VALUE'	FLOAT");
			
			for(int i=0; i<30; i++){
				for(int j=0; j<4; j++){
					String val = value.get(i).get(j);
					String size = "" + (i+1);
					String pKey = "";
					String type;
					if (i<10){
						pKey+= "0" + i;
					}
					else{
						pKey += i;
					}
					
					if(j==1){
						pKey += "I";
						type = "I";	
					}
					else if(j == 2){
						pKey += "B";
						type = "B";	
					}
					else if(j == 3){
						pKey += "H";
						type = "H";	
					}
					st.executeUpdate("INSERT INTO LOOP VALUES(pKey, size, type, val");
				}
			}
			
			
			System.gc();
			DriverManager.getConnection("jdbc:derby:loopDb;shutdown=true");
			}
		catch (SQLException ex) {
			Logger lgr = Logger.getLogger(LoopDbManager.class.getName());
			if (((ex.getErrorCode() == 50000)
					&& ("XJ015".equals(ex.getSQLState())))) {
				lgr.log(Level.INFO, "Derby shut down normally", ex);
				}
			else {
				lgr.log(Level.SEVERE, ex.getMessage(), ex);
				}
			}
		finally{
			try{
				if (st != null){
					st.close();
					}
				if (con != null){
					con.close();
					}
				}
			catch (SQLException ex){
				Logger lgr = Logger.getLogger(LoopDbManager.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
				}
			}
		    
		/**
		 * @step connect to db. If connection failed, return false
		 */
		openConnection();
		/**
		 * @step If the table does not exist in the database, this method creates it else, if 
		 * it does exist, this method truncates the table.
		 */

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
