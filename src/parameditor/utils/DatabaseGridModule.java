package parameditor.utils;


public class DatabaseGridModule extends DatabaseModule {

	public static String generateKey(int row, int column) {
		return row + ":" + column;
	}
	
	public static int getRowFromKey(String key) {
		String[] arr = key.split(":");
		return Integer.parseInt(arr[0]);
	}
	
	public static int getColumnFromKey(String key) {
		String[] arr = key.split(":");
		return Integer.parseInt(arr[1]);
	}
	
}
