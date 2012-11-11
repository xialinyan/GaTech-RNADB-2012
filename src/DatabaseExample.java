import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import parameditor.utils.DangleModule;
import parameditor.utils.Turner99Module;

import com.atled.core.db.DbManager;
import com.atled.core.db.fields.DatabaseRow;
import com.atled.core.db.fields.definitions.DatabaseFieldDefinition;
import com.atled.core.db.query.SqlTableSelectQuery;
import com.atled.core.db.query.constraints.SqlQueryConstraintCollection;
import com.atled.core.db.query.constraints.SqlQueryConstraintCollection.QueryCondition;
import com.atled.core.db.query.constraints.SqlRangeQueryConstraint;
import com.atled.core.db.query.constraints.SqlRangeQueryConstraint.RangeQueryOperation;
import com.atled.core.db.query.constraints.SqlVarcharQueryConstraint;
import com.atled.core.db.query.constraints.SqlVarcharQueryConstraint.VarcharQueryOperation;
import com.atled.core.exceptions.ExceptionHandler;
import com.atled.core.logging.Log;

import db.entry.DangleRow;
import db.turner99manager.DangleDatabaseManager;


public class DatabaseExample {
	
	private final static Log log = Log.getInstance(DatabaseExample.class);

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
				DangleModule.ARRAY_INDEX_FIELD, 
				VarcharQueryOperation.EQUALS, 
				"1,4"));
		
		// range example
		constraints.addQuery(new SqlRangeQueryConstraint(
				DangleModule.PARAM_VALUE_FIELD, 
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
				DangleModule.DATABASE_TABLE_DEFINITION, constraints);
		
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
		fieldsToSelect.add(DangleModule.PAIR_TYPE_FIELD);
		fieldsToSelect.add(DangleModule.PARAM_VALUE_FIELD);
		System.out.println(query.generateSql(fieldsToSelect));
	}
	
	private static void fullStackExample() {
		DbManager manager = exampleInit();
		
		exampleStarSearch(manager);
		
		exampleConstrainedSearch1(manager);

		exampleRangeSearch1(manager);
		
		exampleRangeSearch2(manager);
		
		exampleChangeAndUpdate(manager);
		
		exampleExport(manager);
	}
	
	private static DbManager exampleInit() {
		DbManager manager = new DangleDatabaseManager();
		if (!manager.init(Turner99Module.DANGLE_TEST_FILENAME)) {
			throw new RuntimeException("Failed to initalize database"); 
		}
		return manager;
	}

	private static void exampleStarSearch(DbManager manager) {
		SqlTableSelectQuery query = DangleModule.getStarTableQuery();
		log.info("---------------------------------------------");
		processSearch(manager, query);
	}
	
	private static void exampleConstrainedSearch1(DbManager manager) {
		SqlQueryConstraintCollection constraints = 
				DangleModule.getAndConstraintCollection();
		
		constraints.addQuery(new SqlVarcharQueryConstraint(
				DangleModule.PARAM_VALUE_FIELD, VarcharQueryOperation.NOT_EQUALS, 
				"inf"));
		SqlTableSelectQuery query = new SqlTableSelectQuery(
				DangleModule.DATABASE_TABLE_DEFINITION, constraints);
		log.info("---------------------------------------------");
		processSearch(manager, query);
	}
	
	private static void exampleRangeSearch1(DbManager manager) {
		SqlQueryConstraintCollection constraints = 
				DangleModule.getAndConstraintCollection();
		constraints.addQuery(new SqlRangeQueryConstraint(
				DangleModule.PARAM_VALUE_FIELD, 
				RangeQueryOperation.GREATER_THAN, "0.00"));
		SqlTableSelectQuery query = new SqlTableSelectQuery(
				DangleModule.DATABASE_TABLE_DEFINITION, constraints);
		log.info("---------------------------------------------");
		processSearch(manager, query);
	}
	
	private static void exampleRangeSearch2(DbManager manager) {
		SqlQueryConstraintCollection constraints = 
				DangleModule.getAndConstraintCollection();
		constraints.addQuery(new SqlRangeQueryConstraint(
				DangleModule.PARAM_VALUE_FIELD, 
				RangeQueryOperation.LESS_THAN, "0.00"));
		SqlTableSelectQuery query = new SqlTableSelectQuery(
				DangleModule.DATABASE_TABLE_DEFINITION, constraints);
		log.info("---------------------------------------------");
		processSearch(manager, query);
	}
	
	private static void processSearch(DbManager manager, SqlTableSelectQuery query) {
		List<DatabaseRow> rows = manager.search(query);
		for (DatabaseRow row : rows) {
			DangleRow dRow = (DangleRow)row;
			log.info(dRow.getParamValue());
		}
	}
	
	private static void exampleChangeAndUpdate(DbManager manager) {
		SqlQueryConstraintCollection constraints = 
				DangleModule.getAndConstraintCollection();
		
		constraints.addQuery(new SqlVarcharQueryConstraint(
				DangleModule.PARAM_VALUE_FIELD, VarcharQueryOperation.EQUALS, 
				"inf"));
		SqlTableSelectQuery query = new SqlTableSelectQuery(
				DangleModule.DATABASE_TABLE_DEFINITION, constraints);
		log.info("---------------------------------------------");
		List<DatabaseRow> rows = manager.search(query);
		for (DatabaseRow row : rows) {
			row.setFieldValue(DangleModule.PARAM_VALUE_FIELD, "100");
			manager.update(row, DangleModule.PARAM_VALUE_FIELD);
		}
	}
	
	private static void exampleExport(DbManager manager) {
		String filename = "dangle_exampleExport.DAT";
		manager.export(filename);
		try {
			Scanner scan = new Scanner(new File(filename));
			while (scan.hasNextLine()) {
				log.info(scan.nextLine());
			}
			scan.close();
		} catch (FileNotFoundException e) {
			ExceptionHandler.handle(e);
		}
	}
	
	public static void main(String[] args) {
//		exampleSelect();
		fullStackExample();
	}
}
