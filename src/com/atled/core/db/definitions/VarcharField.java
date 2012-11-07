package com.atled.core.db.definitions;

import java.util.List;

import com.atled.core.validation.ParameterChecker;

public class VarcharField extends DatabaseFieldDefinition {

	private final int fieldLength;
	
	public int getFieldLength() {
		return fieldLength;
	}
	
	public VarcharField(String name, String dbFieldName, int fieldLength) {
		super(name, dbFieldName);
		ParameterChecker.notNegative("fieldLength", fieldLength);
		this.fieldLength = fieldLength;
	}
	
	public VarcharField(String name, String dbFieldName, 
			List<FieldCharacteristics> characteristics, int fieldLength) {
		super(name, dbFieldName, characteristics);
		ParameterChecker.notNegative("fieldLength", fieldLength);
		this.fieldLength = fieldLength;
	}

	@Override
	public String getSqlCreateStatement() {
		StringBuilder sqlBuilder = new StringBuilder(this.dbFieldName);
		sqlBuilder.append(" VARCHAR(").append(fieldLength).append(")");
		if (!this.fieldCharacteristics.isEmpty() || !this.fieldParameters.isEmpty()) {
			for (FieldCharacteristics fc : fieldCharacteristics) {
				if (fc != FieldCharacteristics.PRIMARY_KEY || 
						fc != FieldCharacteristics.UNIQUE) {
					sqlBuilder.append(" ").append(fc.getText());
				}
			}
			for (FieldParameter fc : fieldParameters) {
				sqlBuilder.append(" ").append(fc.getText());
			}
		}
		return sqlBuilder.toString();
	}
	
}
