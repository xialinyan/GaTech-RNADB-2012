import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.logging.*;

public class DangleDBManager extends ReadFile implements DBManager{
	List data;
	ReadFile reader = new ReadFile();
	Connection con = null;
	Statement st = null;
	ResultSet rs = null;
	
	public void init(String fileName) throws IOException{
		if(fileName == "dangle.DAT"){
			data = reader.read(fileName);
		}
	}
	/**
	 * This will insert ONE value into the database
	 * @throws SQLException 
	 */
	public void insert(ArrayList array) throws SQLException{
		st.executeUpdate("INSERT INTO DANGLE VALUES(array[0], array[1], array[2], array[3], array[4], array[5]");
	}
	/**
	 * This will update ONE value into the database base on the input in an arrayList
	 * Which are the attributes in order
	 */
	public void update(ArrayList array){
		
	}
	/**
	 * @param array We will want to take in an array with params in the order of the table
	 */
	public ResultSet search(ArrayList array){
		rs = st.executeQuery("SELECT * FROM DANGLE WHERE ID = array[0], LOWERBP = array[1], UPPERBP = array[2], DANGLEB = array[3], PRIMESTAR = array[4], VALUE = array[5]");
	}
	
	public static void main(String[] args) {
		
		String url = "jdbc:derby:dangle;user=XIALIN";
		try{
			st.executeUpdate("CREATE TABLE DANGLE(
				'ID'	INT	PRIMARY KEY,
				'LOWERBP'	VARCHAR(1)	NOT NULL,
				'UPPERBP'	VARCHAR(1)	NOT NULL,
				'DANGLEB'	VARCHAR(1)	NOT NULL,
				'PRIMESTART'	VARCHAR(1) NOT NULL,
				'VALUE'	FLOAT,)
				
			DriverManager.getConnection("jdbc:derby:;shutdown=true");
			
		}
		
catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CreateCars.class.getName());
            if (((ex.getErrorCode() == 50000)
                    && ("XJ015".equals(ex.getSQLState())))) {
                lgr.log(Level.INFO, "Derby shut down normally", ex);
            } 
            else {
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
            }
        } 
		finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } 
            catch (SQLException ex) {
                Logger lgr = Logger.getLogger(CreateCars.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }

	}
}
