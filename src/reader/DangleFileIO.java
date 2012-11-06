package reader;

import java.io.*;
import java.util.*;

public class DangleFileIO implements MfeParameterFileIO {
	
	FileReader fileReader;
	List<List<String>> data;
	Scanner scan;
	@Override
	public List<List<String>> read(String filename) throws IllegalArgumentException, FileNotFoundException {
		fileReader = new FileReader(filename);
		scan = new Scanner(fileReader);
		data = new ArrayList<List<String>>();
		for(int i=0; i<8; i++){
			String[] arr = new String[16];
			arr = scan.nextLine().trim().split("\\s+");
			ArrayList<String> temp = new ArrayList<String>();
			for(int j=0; j<16; j++){
				temp.add(j,arr[j]);
			}
			data.add(i,temp);
		}
		
		/**
		 * @step Ensure that filename is not null and file exists
		 */
		/**
		 * @step parse the file and convert to <code>List</code> of <code>List</code> of <code>Strings</code>
		 */
		/**
		 * @step return the parsed results
		 */
		return data;
	}

	@Override
	public boolean export(String filename, List<List<String>> params) throws IOException {
		// TODO
		PrintStream ps;
		try{
			ps = new PrintStream(new FileOutputStream(filename));
			for(int row = 0; row < params.size(); row++){
				for(int col = 0; col < params.get(row).size();col++){
					String s= params.get(row).get(col);
					ps.println(s);
				}
			}
			ps.close();
		}
		catch(FileNotFoundException e){
			System.out.println(e.getMessage());
			return false;
		}
		/**
		 * @step Ensure that filename is not null
		 */
		/**
		 * @step get results as <code>List</code> of <code>List</code> of <code>Strings</code> from correct DbManager
		 */
		/**
		 * @step return the aggregated results
		 */
		return true;
	}
}
