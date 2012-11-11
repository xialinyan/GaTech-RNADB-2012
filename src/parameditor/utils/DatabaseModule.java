package parameditor.utils;

import com.atled.core.db.query.constraints.SqlQueryConstraintCollection;
import com.atled.core.db.query.constraints.SqlQueryConstraintCollection.QueryCondition;

public class DatabaseModule {
	
	public final static String WATSON_CRICK_BP = "WC";
	public final static String WOBBLE_BP = "W";

	public static SqlQueryConstraintCollection getAndConstraintCollection() {
		return new SqlQueryConstraintCollection(QueryCondition.AND);
	}

	public static SqlQueryConstraintCollection getOrConstraintCollection() {
		return new SqlQueryConstraintCollection(QueryCondition.OR);
	}

	public static String getBasePairType(String lead, String tail) {
		if (lead.equals("A")) {
			return tail.equals("U") ? WATSON_CRICK_BP : "";
		} else if (lead.equals("C")) {
			return tail.equals("G") ? WATSON_CRICK_BP : "";
		} else if (lead.equals("G")) {
			return tail.equals("C") ? WATSON_CRICK_BP : tail.equals("U") ? WOBBLE_BP : "";
		} else if (lead.equals("U")) {
			return tail.equals("A") ? WATSON_CRICK_BP : tail.equals("G") ? WOBBLE_BP : "";
		}
		return null;
	}
}
