package db;



public abstract class DefaultDbManager implements DBManager {

	protected final String DB_NAME = "rnaparameditordb";
	protected boolean isInitialized = false;
	
	/**
	 * @step Connect to correct database and create if necessary.
	 * @step Return success status of connection.
	 * 
	 * @return True if connection was opened sucessfully.
	 */
	protected final boolean openConnection() {
		// TODO
		/**
		 * @step Connect to correct database and create if necessary.
		 */
//		DriverManager.getConnection("jdbc:derby:"+DB_NAME+";create=true;");
		/**
		 * @step Return success status of connection.
		 */
		return false;
	}
	
	@Override
	public final boolean closeConnection() {
		// TODO
		/**
		 * @step If connection is currently open, close the connection.
		 */
//		DriverManager.getConnection("jdbc:derby:"+DB_NAME+";shutdown=true");
		/**
		 * @step set initialized to false
		 */
		isInitialized = false;
		/**
		 * @step start garbage collector
		 */
		System.gc();
		/**
		 * @step Return the close connection status
		 */
		return false;
	}

}
