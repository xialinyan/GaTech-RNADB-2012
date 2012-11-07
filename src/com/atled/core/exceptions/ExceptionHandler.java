package com.atled.core.exceptions;

import com.atled.core.validation.ParameterChecker;

public class ExceptionHandler {

	public static final void handle(Exception exception) {
		ParameterChecker.notNull("exception", exception);
		exception.printStackTrace();
	}
	
}
