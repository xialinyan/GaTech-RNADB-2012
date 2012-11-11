package turner99parsing;


public class StackFileIO extends StandardGridFileIO {

	public final static int NUM_ROWS = 16;
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
}
