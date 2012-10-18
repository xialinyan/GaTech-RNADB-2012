package reader;

import java.io.IOException;
import java.util.List;

public class DangleFileIO implements MfeParameterFileIO {

	@Override
	public List<List<String>> read(String filename) throws IllegalArgumentException {
		// TODO
		/**
		 * @step Ensure that filename is not null and file exists
		 */
		/**
		 * @step parse the file and convert to <code>List</code> of <code>List</code> of <code>Strings</code>
		 */
		/**
		 * @step return the parsed results
		 */
		return null;
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
