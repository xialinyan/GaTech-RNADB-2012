package com.atled.core.db.fields;

import com.atled.core.db.fields.definitions.VarcharFieldDefinition;
import com.atled.core.exceptions.ExceptionHandler;
import com.atled.core.validation.ParameterChecker;

public class VarcharField {

	private VarcharFieldDefinition definition;
	private String value;

	public VarcharField(VarcharFieldDefinition definition, String value) {
		ParameterChecker.notNull("definition", definition);
		ParameterChecker.notNull("value", value);
		this.definition = definition;
		this.value = value;
		validate();
	}
	
	private void validate() {
		if (this.definition.getFieldLength() < value.length()) {
			ExceptionHandler.handle(new IllegalArgumentException("Invalid length " +
					"for varchar value."));
		}
	}
	
}
