import java.util.ArrayList;
import java.util.List;

import com.atled.core.db.fields.definitions.DatabaseFieldDefinition;
import com.atled.core.db.query.SqlTableSelectQuery;
import com.atled.core.db.query.constraints.SqlQueryConstraintCollection;
import com.atled.core.db.query.constraints.SqlQueryConstraintCollection.QueryCondition;
import com.atled.core.db.query.constraints.SqlRangeQueryConstraint;
import com.atled.core.db.query.constraints.SqlRangeQueryConstraint.RangeQueryOperation;
import com.atled.core.db.query.constraints.SqlVarcharQueryConstraint;
import com.atled.core.db.query.constraints.SqlVarcharQueryConstraint.VarcharQueryOperation;

import db.DangleDbManager;


public class DatabaseExample {

	public static void exampleSelect() {
		/* 
		 * While building the tree we want to know what the relationship will be between 
		 * siblings.
		 * 
		 * ( x AND y AND z) vs. (x OR y OR z)
		 * 
		 * Note x, y and z can be QueryConstraintsCollections or Query Constraints. This 
		 * allows you to do the following
		 * 
		 *  ( x AND (y OR z))
		 */
		QueryCondition queryCondition = QueryCondition.AND;
		SqlQueryConstraintCollection constraints = 
				new SqlQueryConstraintCollection(queryCondition);
		
		/*
		 * Populate the collection with constraints using query constraints from the
		 * <code>com.atled.core.db.query.constraints</code> package.
		 * 
		 * Each class has *QueryOperation enums which define the constraints behavior.
		 */
		constraints.addQuery(new SqlVarcharQueryConstraint(
				DangleDbManager.ARRAY_INDEX_FIELD, 
				VarcharQueryOperation.EQUALS, 
				"1,4"));
		
		// range example
		constraints.addQuery(new SqlRangeQueryConstraint<String>(
				DangleDbManager.PARAM_VALUE_FIELD, 
				RangeQueryOperation.INCLUSIVE_RANGE, 
				"-0.5", // min
				"0.5")); // max
		
		/*
		 * Create the final select statement query object at the table level using the 
		 * constraints.
		 * 
		 * Note: it is not necessary to add constraints to the collection e.g. 
		 * 	<code>SqlTableSelectQuery query = new SqlTableSelectQuery(
		 * 		DangleDbManager.DATABASE_TABLE_DEFINITION);</code>
		 * also works.
		 */
		SqlTableSelectQuery query = new SqlTableSelectQuery(
				DangleDbManager.DATABASE_TABLE_DEFINITION, constraints);
		
		/*
		 * print out the statement with a "SELECT * FROM ..."
		 * 
		 * <code>
		  		SELECT * FROM `Dangle` WHERE (
					`arrayIndexField`='1,4' AND (
						`paramValueField`<='-0.5' AND 
						`paramValueField`>='-0.5'
					)
				);
		 * </code>
		 */
		System.out.println(query.generateSql());
		
		
		
		/*
		 *  print out the statement while selecting specific fields
		 *  
		 * <code>
		  		SELECT `pairTypeField`, `paramValueField` FROM `Dangle` WHERE (
					`arrayIndexField`='1,4' AND (
						`paramValueField`<='-0.5' AND 
						`paramValueField`>='-0.5'
					)
				);
		 * </code>
		 */
		List<DatabaseFieldDefinition> fieldsToSelect = 
				new ArrayList<DatabaseFieldDefinition>();
		fieldsToSelect.add(DangleDbManager.PAIR_TYPE_FIELD);
		fieldsToSelect.add(DangleDbManager.PARAM_VALUE_FIELD);
		System.out.println(query.generateSql(fieldsToSelect));
	}
	
	public static void main(String[] args) {
		exampleSelect();
	}
}
