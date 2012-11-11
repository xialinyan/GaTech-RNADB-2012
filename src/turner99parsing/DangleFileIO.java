package turner99parsing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.atled.core.exceptions.ExceptionHandler;

public class DangleFileIO implements MfeParameterFileIO {

	public final static int NUM_ROWS = 8;
	public final static int NUM_COLS = 16;
	
	public void validateFileName(String filename) throws Exception {
		// TODO
	}
	
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
			for (;i<NUM_ROWS && scan.hasNextLine();i++) {
				String[] sarr = scan.nextLine().split("\\s+");
				List<String> tempList = new ArrayList<String>();
				if (sarr.length == NUM_COLS) {
					for (int j=0;j<NUM_COLS;j++) {
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
	public boolean export(String filename, List<List<String>> params) throws IOException {
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
	
	public static void main(String[] args) throws IOException {
		DangleFileIO dangleIO = new DangleFileIO();
		System.out.println("----------\nIMPORT TEST\n----------");
		List<List<String>> importList = dangleIO.read("Turner99/dangle.DAT");
		for (int i=0;i<importList.size();i++) {
			for (int j=0;j<importList.get(i).size();j++) {
				System.out.print(importList.get(i).get(j) + "\t");
			}
			System.out.println();
		}
		
		System.out.println("----------\nEXPORT TEST\n----------");
		String exportFileName = "test/Turner99/dangle.DAT";
		dangleIO.export(exportFileName, importList);
		try {
			Scanner scan = new Scanner(new File(exportFileName));
			while (scan.hasNext()) {
				System.out.println(scan.nextLine());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
