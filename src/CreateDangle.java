import java.sql.*;
import java.util.logging.*;

public class CreateDangle {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Connection con = null;
		Statement st = null;
		String url = "jdbc:derby:dangle;user=XIALIN";
		try{
			st.executeUpdate("CREATE TABLE DANGLE(
				'ID'	INT	PRIMARY KEY,
				'LOWERBP'	VARCHAR(1)	NOT NULL,
				'UPPERBP'	VARCHAR(1)	NOT NULL,
				'DANGLEB'	VARCHAR(1)	NOT NULL,
				'PRIMESTART'	VARCHAR(1) NOT NULL,)
		}

	}

}
