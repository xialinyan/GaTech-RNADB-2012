package reader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TLoopFileIO implements MfeParameterFileIO {
	FileReader fileReader;
	List<List<String>> data;
	Scanner scan;
	@Override
	public List<List<String>> read(String filename) throws IllegalArgumentException, FileNotFoundException {
		// TODO
		fileReader = new FileReader(filename);
		scan = new Scanner(fileReader);
		data = new ArrayList<List<String>>();
		for(int i=0; i<30; i++){
			String[] arr = new String[2];
			arr = scan.nextLine().trim().split("\\s+");
			ArrayList<String> temp = new ArrayList<String>();
			for(int j=0; j<2; j++){
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
		/**
		 * @step Ensure that filename is not null
		 */
		/**
		 * @step get results as <code>List</code> of <code>List</code> of <code>Strings</code> from correct DbManager
		 */
		/**
		 * @step return the aggregated results
		 */
		return false;
	}
}
