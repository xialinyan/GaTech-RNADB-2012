package turner99parsing;
import java.io.*;
import java.util.*;

public interface MfeParameterFileIO {
	
	/**
	 * Ensure that the filename has the correct extension and matches the Parameter file type.
	 * This can be used for <code>import(String)</code> or <code>export(String)</code>
	 * 
	 * @param filename Name to validate.
	 * @throws Exception Only throw if invalid filename
	 */
	public void validateFileName(String filename) throws Exception;
	
	/**
	 * @step Ensure that filename is not null and file exists
	 * @step parse the file and convert to <code>List</code> of <code>List</code> of <code>Strings</code>
	 * @step return the parsed results
	 *  
	 * @param filename
	 * @return Never null. Returns empty list if there was an exception
	 * @throws IllegalArgumentException if filename is null or file does not exist
	 */
	public List<List<String>> read(String filename) throws IllegalArgumentException;
	
	/**
	 * @step Ensure that filename is not null
	 * @step get results as <code>List</code> of <code>List</code> of <code>Strings</code> from correct DbManager
	 * @step return the aggregated results
	 * 
	 * @param filename
	 * @param params
	 * @return
	 * @throws IOException
	 */
	public boolean export(String filename, List<List<String>> params) throws IOException;
}
