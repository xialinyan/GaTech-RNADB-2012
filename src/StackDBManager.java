

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.logging.*;

public class StackDBManager extends ReadFile implements DBManager{
	String[][] data;
	ReadFile reader = new ReadFile();
	
	public void init(String fileName) throws IOException{
		if(fileName == "stack.DAT"){
			data = reader.read(fileName);
		}
	}
	/**
	 * This is the main method.
	 * The create table statements will go here.
	 * ID will be VARCHAR(4)
	 * INTERNAL LOW
	 * INTERNAL HIGH
	 * EXTERNAL LOW
	 * EXTERNAL HIGH
	 * VALUE
	 * @param args
	 */
	public static void main(String[] args) {

			
		}
}
