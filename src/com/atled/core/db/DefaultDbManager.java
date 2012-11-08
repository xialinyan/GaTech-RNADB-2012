package com.atled.core.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.atled.core.db.fields.definitions.DatabaseDefinition;
import com.atled.core.exceptions.ExceptionHandler;



public abstract class DefaultDbManager implements DbManager {

	protected final DatabaseDefinition databaseDefinition; 
	//String DB_NAME = "rnaparameditordb";
	private Connection con;
	private Statement dbStatement;
	

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
			String connectionString = "jdbc:derby:"+ databaseDefinition.getDbName() + 
					";create=true;";
			con = DriverManager.getConnection(connectionString);
		} catch (SQLException e) {
			ExceptionHandler.handle(e);
		}
	}
	
	@Override
	public final boolean isConnected() {
		try {
			return con != null && !con.isClosed();
		} catch (SQLException e) {	}
		return false;
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

	protected boolean executeSql(String sql) {
		if (!isConnected()) {
			return false;
		}
		try {
			if (dbStatement == null) {
				dbStatement = con.createStatement();
			}
			return dbStatement.execute(sql);
		} catch (SQLException e) {
			ExceptionHandler.handle(e);
		}
		return false;
	}
	
}
