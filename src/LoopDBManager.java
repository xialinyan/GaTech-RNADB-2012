import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.logging.*;

public class LoopDBManager extends ReadFile implements DBManager{
	
	String[][] data;
	ReadFile reader = new ReadFile();
	
	public void init(String fileName) throws IOException{
		if(fileName == "loop.DAT"){
			data = reader.read(fileName);
		}
	}
	/**
	 * This is the main method.
	 * The create table statements will go here.
	 * ID will be VARCHAR(3) SIZE+I(nternal)+B(ulge)+H(airpin)
	 * @param args
	 */
	public static void main(String[] args) {

			
		}
	
}
