package com.atled.core.db.query.constraints;

import com.atled.core.db.definitions.DatabaseFieldDefinition;

public abstract class SqlFieldQueryConstraint implements SqlQueryConstraintGenerator {

	protected final DatabaseFieldDefinition field;
	
	public DatabaseFieldDefinition getField() {
		return field;
	}
	
	public SqlFieldQueryConstraint(DatabaseFieldDefinition field) {
		this.field = field;
	}
}
