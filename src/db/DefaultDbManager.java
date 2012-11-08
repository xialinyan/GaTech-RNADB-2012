package db;

import com.atled.core.db.DbManager;



public abstract class DefaultDbManager implements DbManager {

	protected final String DB_NAME = "rnaparameditordb";
	
	/**
	 * @step Connect to correct database and create if necessary.
	 * @step Return success status of connection.
	 * 
	 * @return True if connection was opened successfully.
	 */
	@Override
	public final void openConnection() {
		// TODO
		/**
		 * @step Connect to correct database and create if necessary.
		 *
//		DriverManager.getConnection("jdbc:derby:"+DB_NAME+";create=true;");
		/**
		 * @step Return success status of connection.
		 */
	}
	
	@Override
	public final void closeConnection() {
		// TODO
		/**
		 * @step If connection is currently open, close the connection.
		 */
//		DriverManager.getConnection("jdbc:derby:"+DB_NAME+";shutdown=true");
		/**
		 * @step start garbage collector
		 */
		System.gc();
	}

}
