import java.io.*;
import java.util.*;

public class ReadFile { // this should be an interface i.e. public interface MfeParamFileReader {

	// all of these variables should be in the classes which implement this interface
	FileReader fileReader;
	BufferedReader bufferedReader;
	@SuppressWarnings("rawtypes")  // delete this
	List lines;   // you did not define generics
	
	@SuppressWarnings({ "unchecked", "rawtypes" }) // delete this
	/**
	 * 	
	 * @param filename This should take a string. Each class will extend this class.
	 * It will individually check for the correct file.
	 * This should make a 2D array of the values from which you can tell the type of data.
	 * @return this returns a 2D array of the  data
	 * @throws IOException
	 */
	public List read(String filename) throws IOException{  // do not implement the file here but make all classes
							       // implementing this interface implement it. Remember generics
		fileReader = new FileReader(filename);
		bufferedReader = new BufferedReader(fileReader);
		lines = new ArrayList();
		String line = null;
		int i=0;
		while((line=bufferedReader.readLine())!=null){
			String[] splitLine = line.split("\t");
			lines.add(i,splitLine);
			i++;
			
		}
		return lines;
	
		
	}

}
