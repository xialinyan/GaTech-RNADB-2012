import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.logging.*;

public class tLoopDBManager extends ReadFile implements DBManager{
	List data;
	ReadFile reader = new ReadFile();
	Connection con = null;
	Statement st = null;
	ResultSet rs = null;
	
	public void init(String fileName) throws IOException{
		if(fileName == "tloop.DAT"){
			data = reader.read(fileName);
		}
	}
	
	
	/**
	 * This will insert ONE value into the database
	 * @throws SQLException 
	 */
	public void insert(ArrayList array) throws SQLException{
		st.executeUpdate("INSERT INTO DANGLE VALUES(array[0], array[1]");
	}
	/**
	 * This will update ONE value into the database base on the input in an arrayList
	 * Which are the attributes in order
	 */
	public void update(ArrayList array){
		
	}
	/**
	 * @param array We will want to take in an array with params in the order of the table
	 * @throws SQLException 
	 */
	public ResultSet search(ArrayList array) throws SQLException{
		rs = st.executeQuery("SELECT * FROM DANGLE WHERE ID = array[0], VALUE = array[1]");
		return rs;
	}
	
	public static void main(String[] args) {
		
		String url = "jdbc:derby:tloop;user=XIALIN";
		try{
			st.executeUpdate("CREATE TABLE tLOOP(" +
				"'ID'	VARCHAR(6)	PRIMARY KEY," +
				+'VALUE'	FLOAT	NOT NULL,"");
				
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
