package turner99parsing;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class DangleFileIO extends StandardGridFileIO {

	public final static int NUM_ROWS = 8;
	public final static int NUM_COLS = 16;
	
	@Override
	public int getNumRows() {
		return NUM_ROWS;
	}
	
	@Override
	public int getNumCols() {
		return NUM_COLS;
	}
	
	public void validateFileName(String filename) throws Exception {
		// TODO
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
