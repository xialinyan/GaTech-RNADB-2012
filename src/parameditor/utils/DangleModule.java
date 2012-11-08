package parameditor.utils;

public class DangleModule {

	public static String generateKey(int row, int column) {
		return row + ":" + column;
	}
	
	public static String getBasePairType(int row, int column) {
		return getBasePairType(getLeadNucletide(row), getTailNucletide(column));
	}
	
	public static String getBasePairType(String lead, String tail) {
		if (lead.equals("A")) {
			return tail.equals("U") ? "WC" : "";
		} else if (lead.equals("C")) {
			return tail.equals("G") ? "WC" : "";
		} else if (lead.equals("G")) {
			return tail.equals("C") ? "WC" : tail.equals("U") ? "W" : "";
		} else if (lead.equals("U")) {
			return tail.equals("A") ? "WC" : tail.equals("G") ? "W" : "";
		}
		return null;
	}
	
	public static String getLeadNucletide(int row) {
		switch (row) {
		case 0: 
		case 4: return "A";
		case 1: 
		case 5: return "C";
		case 2:
		case 6: return "G";
		case 3:
		case 7: return "U";
		}
		return null;
	}
	
	public static String getTailNucletide(int column) {
		switch (column / 4) {
		case 0: return "A";
		case 1: return "C";
		case 2: return "G";
		case 3: return "U";
		}
		return null;
	}
	
	public static String getDangleNucletide(int column) {
		switch (column % 4) {
		case 0: return "A";
		case 1: return "C";
		case 2: return "G";
		case 3: return "U";
		}
		return null;
	}
	
}
