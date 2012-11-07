package com.atled.core.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.atled.core.db.definitions.DatabaseDefinition;
import com.atled.core.exceptions.ExceptionHandler;



public abstract class DefaultDbManager implements DBManager {

	protected final DatabaseDefinition databaseDefinition; 
	//String DB_NAME = "rnaparameditordb";
	protected Connection con;

	public DefaultDbManager(DatabaseDefinition databaseDefinition) {
		this.databaseDefinition = databaseDefinition;
	}
	
	/**
	 * @step Connect to correct database and create if necessary.
	 * 
	 * @return True if connection was opened sucessfully.
	 */
	@Override
	public 	final void openConnection() {
		try {
			if (con != null && !con.isClosed()) {
				return;
			}
			/**
			 * @step Connect to correct database and create if necessary.
			 */
			con = DriverManager.getConnection("jdbc:derby:"+
					databaseDefinition.getDbName() + ";create=true;");
		} catch (SQLException e) {
			ExceptionHandler.handle(e);
		}
	}
	
	@Override
	public final void closeConnection() {
		/**
		 * @step If connection is currently open, close the connection.
		 */
		try {
			if (con == null) {
				return;
			}
			con.close();
			con = null;
		} catch (SQLException e) {
			ExceptionHandler.handle(e);
		}
		/**
		 * @step start garbage collector
		 */
		System.gc();
	}

}
