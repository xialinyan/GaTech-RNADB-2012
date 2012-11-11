package db;

import com.atled.core.db.query.constraints.SqlQueryConstraintCollection;
import com.atled.core.db.query.constraints.SqlQueryConstraintCollection.QueryCondition;

public class DatabaseModule {

	public static SqlQueryConstraintCollection getAndConstraintCollection() {
		return new SqlQueryConstraintCollection(QueryCondition.AND);
	}

	public static SqlQueryConstraintCollection getOrConstraintCollection() {
		return new SqlQueryConstraintCollection(QueryCondition.OR);
	}
}
