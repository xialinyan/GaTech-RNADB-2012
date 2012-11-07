package com.atled.core.db.definitions;

import java.util.Collection;
import java.util.Collections;

import com.atled.core.validation.ParameterChecker;

/**
 * 
 * @author David Esposito
 */
public abstract class DatabaseFieldDefinition implements DatabaseEntity {

	/**
	 * Characteristics of the field in the database definition.
	 * @author David Esposito
	 */
	public static enum FieldCharacteristics {
		NOT_NULL("NOT NULL"), 
		AUTO_INCREMENT("AUTO-INCREMENT"), 
		UNIQUE("UNIQUE"),
		PRIMARY_KEY("PRIMARY KEY");
		
		private final String text;
		
		public String getText() {
			return text;
		}
		
		private FieldCharacteristics(String text) {
			this.text = text;
		}
	}
	
	/**
	 * Parameterized characteristics of the field in the database definition.
	 * @author David Esposito
	 */
	public static enum FieldParameter {
		DEFAULT("DEFAULT", "NULL"), 
		CHECK("CHECK", "!= NULL");
		
		private final String text;
		private String argument;
		
		public String getText() {
			return text + " " + argument;
		}
		
		public void setArgument(String newArgument) {
			ParameterChecker.stringNotEmpty("newArgument", newArgument);
			this.argument = newArgument;
		}
		
		private FieldParameter(String text, String argument) {
			this.text = text;
			this.argument = argument;
		}
	}
	
	protected final String name;
	protected final String dbFieldName;
	protected final Collection<FieldCharacteristics> fieldCharacteristics;
	protected final Collection<FieldParameter> fieldParameters;
	
	public String getName() {
		return name;
	}
	
	public String getDbFieldName() {
		return dbFieldName;
	}
	
	public boolean isPrimaryKey() {
		return fieldCharacteristics.contains(FieldCharacteristics.PRIMARY_KEY);
	}
	
	public boolean isUnique() {
		return fieldCharacteristics.contains(FieldCharacteristics.UNIQUE);
	}
	
	/**
	 * @return The default value for this field. Note this could include the string:
	 * <code>"NULL"</code>. If there is no default set then the null object is returned.
	 */
	public String getDefualt() {
		if (fieldParameters.contains(FieldParameter.DEFAULT)) {
			for (FieldParameter fp : fieldParameters) {
				if (fp == FieldParameter.DEFAULT) {
					return fp.argument;
				}
			}
		}
		return null;
	}
	
	public Collection<FieldCharacteristics> getFieldCharacteristics() {
		return fieldCharacteristics;
	}
	
	public Collection<FieldParameter> getFieldParameters() {
		return fieldParameters;
	}
	
	/**
	 * @param name The human readable name for this field.
	 * @param dbFieldName The actual name as it appeans in the database table.
	 */
	public DatabaseFieldDefinition(String name, String dbFieldName) {
		this(name, dbFieldName, Collections.<FieldCharacteristics>emptyList(), 
				Collections.<FieldParameter>emptyList());
	}
	
	/**
	 * @param name The human readable name for this field.
	 * @param dbFieldName The actual name as it appeans in the database table.
	 * @param fieldCharacteristics This is an unchanging list of characteristics 
	 * which define the initialization and begavior of data in this field.
	 */
	public DatabaseFieldDefinition(String name, String dbFieldName, 
			Collection<FieldCharacteristics> fieldCharacteristics) {
		this(name, dbFieldName, fieldCharacteristics, 
				Collections.<FieldParameter> emptyList());
	}
	
	/**
	 * @param name The human readable name for this field.
	 * @param dbFieldName The actual name as it appeans in the database table.
	 * @param fieldCharacteristics This is an unchanging list of characteristics 
	 * which define the initialization and begavior of data in this field.
	 * @param fieldParameters This is an unchanging list of parameterized 
	 * characteristics which define the initialization and begavior of data in 
	 * this field.
	 */
	public DatabaseFieldDefinition(String name, String dbFieldName, 
			Collection<FieldCharacteristics> fieldCharacteristics,
			Collection<FieldParameter> fieldParameters) {
		ParameterChecker.stringNotEmpty("name", name);
		ParameterChecker.stringNotEmpty("dbFieldName", dbFieldName);
		this.name = name;
		this.dbFieldName = dbFieldName;
		this.fieldCharacteristics = 
				Collections.unmodifiableCollection(fieldCharacteristics);
		this.fieldParameters = Collections.unmodifiableCollection(fieldParameters);
		validateCharacteristics();
	}
	

	private void validateCharacteristics() {
		if (!fieldCharacteristics.isEmpty()) {
			{
				String defaultValue = getDefualt();
				if (fieldCharacteristics.contains(FieldCharacteristics.NOT_NULL) && 
						defaultValue != null &&	defaultValue.equals("NULL")) {
					throw new IllegalStateException("Cannot make field \"NOT NULL\" and " +
							"\"DEFAULT NULL\"");
				}
			}
			if (fieldCharacteristics.contains(FieldCharacteristics.UNIQUE) && 
					fieldCharacteristics.contains(FieldCharacteristics.PRIMARY_KEY)) {
				throw new IllegalStateException("Redundent to define field as " +
						"\"UNIQUE \" and \"DEFAULT NULL\"");
			}
			if (fieldCharacteristics.contains(FieldCharacteristics.PRIMARY_KEY) && 
					!fieldCharacteristics.contains(FieldCharacteristics.NOT_NULL)) {
				throw new IllegalStateException("PRIMARY KEY cannot be NULL. Please " +
						"add \"NOT NULL\" to this field. (" + name + ")");
			}
		}
	}
}
