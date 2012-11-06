import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateCars {
    
    public static void main(String[] args) {

        Connection con = null;
        Statement st = null;

        String url = "jdbc:derby:firstdb;create=true";

        try {
            
            System.setProperty("derby.system.home", 
                    "/home/xialin/Documents/eclipse/Research/Derby");
            
            con = DriverManager.getConnection(url);
            st = con.createStatement();
//            st.executeUpdate("CREATE TABLE CARS(ID INT PRIMARY KEY,"
//                    + "NAME VARCHAR(30), PRICE INT)");
//            st.executeUpdate("INSERT INTO CARS VALUES(9, 'Audi', 52642)");
//            st.executeUpdate("INSERT INTO CARS VALUES(10, 'Mercedes', 57127)");
//            st.executeUpdate("INSERT INTO CARS VALUES(11, 'Skoda', 9000)");
//            st.executeUpdate("INSERT INTO CARS VALUES(12, 'Volvo', 29000)");
//            st.executeUpdate("INSERT INTO CARS VALUES(13, 'Bentley', 350000)");
//            st.executeUpdate("INSERT INTO CARS VALUES(14, 'Citroen', 21000)");
//            st.executeUpdate("INSERT INTO CARS VALUES(15, 'Hummer', 41400)");
            st.executeUpdate("INSERT INTO CARS VALUES(18, 'Volkswagen', 21600)");
            System.gc();
            DriverManager.getConnection("jdbc:derby:firstdb;shutdown=true");

        } catch (SQLException ex) {
            
            Logger lgr = Logger.getLogger(CreateCars.class.getName());

            if (((ex.getErrorCode() == 50000)
                    && ("XJ015".equals(ex.getSQLState())))) {

                lgr.log(Level.INFO, "Derby shut down normally", ex);

            } else {

                lgr.log(Level.SEVERE, ex.getMessage(), ex);
            }

        } finally {

            try {

                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(CreateCars.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }
}