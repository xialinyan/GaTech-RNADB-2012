package turner99parsing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.atled.core.exceptions.ExceptionHandler;

public abstract class StandardGridFileIO implements MfeParameterFileIO {
	
	public abstract int getNumRows();
	
	public abstract int getNumCols();
	
	@Override
	public List<List<String>> read(String filename) throws IllegalArgumentException {
		/**
		 * @step Ensure that filename is not null and file exists
		 */
		try {
			validateFileName(filename);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
		/**
		 * @step parse the file and convert to <code>List</code> of <code>List</code> of <code>Strings</code>
		 */
		Scanner scan = null;
		List<List<String>> returnList = new ArrayList<List<String>>();
		try {
			scan = new Scanner(new File(filename));
			int i=0;
			for (;i<getNumRows() && scan.hasNextLine();i++) {
				String[] sarr = scan.nextLine().split("\\s+");
				List<String> tempList = new ArrayList<String>();
				if (sarr.length == getNumCols()) {
					for (int j=0;j<getNumCols();j++) {
						if (sarr[j].trim().equals("inf")) {
							tempList.add("inf");
						} else {
							Double.parseDouble(sarr[j].trim());
							tempList.add(sarr[j]);
						}
					}
				} else {
					ExceptionHandler.handle(new Turner99FormatException("Incorrect " +
							"number of columns (" + sarr.length + ") on row " + i));
					return null;
				}
				returnList.add(tempList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (scan != null) {
				scan.close();
			}
		}
		/**
		 * @step return the parsed results
		 */
		return returnList;
	}

	@Override
	public boolean export(String filename, List<List<String>> params)
			throws IOException {
		/**
		 * @step Ensure that filename is not null
		 */
		try {
			validateFileName(filename);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
		/**
		 * @step get results as <code>List</code> of <code>List</code> of <code>Strings</code> from 
		 * correct DbManager
		 */
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filename)));
		for(List<String> stringList : params) {
			for (String s : stringList) {
				writer.write(s);
				writer.write("\t");
			}
			writer.newLine();
		}
		writer.close();
		/**
		 * @step return the aggregated results
		 */
		return false;
	}

}
