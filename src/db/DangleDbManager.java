package db;

//import DangleDbManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import reader.DangleFileIO;

public class DangleDbManager extends DefaultDbManager implements DBManager{
	
	@Override
	public boolean init(String fileName) throws IOException {
		// TODO 
		Connection con = null;
		Statement st = null;
		List<List<String>> value = new ArrayList<List<String>>();
		DangleFileIO dangle;
		dangle = new DangleFileIO();
		value = dangle.read("dangle.DAT");
		
		

		String url = "jdbc:derby:dangleDb;create=true";
		
		try {
			System.setProperty("derby.system.home", 
		                    "/home/xialin/Documents/eclipse/Research/Derby");
			con = DriverManager.getConnection(url);
			st = con.createStatement();
			st.executeUpdate("CREATE TABLE DANGLE( " +
			"'ID'	VARCHAR(4)	PRIMARY KEY, " +
			"'LOWERBP'	VARCHAR(1)	NOT NULL, " +
			"'UPPERBP'	VARCHAR(1)	NOT NULL, " +
			"'DANGLEB'	VARCHAR(1)	NOT NULL, " +
			"'PRIMESTART'	VARCHAR(1) NOT NULL, " +
			"'VALUE'	FLOAT");
			
			for(int i=0; i<8; i++){
				for(int j=0; j<16; j++){
					String val = value.get(i).get(j);
					String dangleBP;
					String pKey = "";
					String primeEnd;
					if (i<5){
						primeEnd = "5";
					}
					else{
						primeEnd = "3";
					}
					if(j<10){
						pKey += "0" + i + "0" + j;
					}
					else{
						pKey += "0" + i + j;
					}
					if((j+1)%4==1){
						dangleBP = "A";	
					}
					else if((j+1)%4 == 2){
						dangleBP = "C";
					}
					else if((j+1)%4 == 3){
						dangleBP = "G";
					}
					else{
						dangleBP = "U";
					}
				}
			}
//			st.executeUpdate("INSERT INTO CARS VALUES(15, 'Hummer', 41400)");
			
			System.gc();
			DriverManager.getConnection("jdbc:derby:dangleDb;shutdown=true");
			}
		catch (SQLException ex) {
			Logger lgr = Logger.getLogger(DangleDbManager.class.getName());
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
				Logger lgr = Logger.getLogger(DangleDbManager.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
				}
			}
		    
		
		/**
		 * @step connect to db. If connection failed, return false
		 */

		/**
		 * @step If the table does not exist in the database, this method creates it else, if 
		 * it does exist, this method truncates the table.
		 * 
		 * WHAT DOES TRUNCATE MEAN
		 * 
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
		 * @step set initialized to true
		 */
		isInitialized = true;
		/**
		 * @step return sucess of initialization
		 */
		return false;
	}

	@Override
	/**
	 * You would never insert into the database? 0_o
	 */
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
