package com.atled.core.validation;

import java.util.Collection;

import com.atled.core.exceptions.ExceptionHandler;

public final class ParameterChecker {

	public final static void notNull(String name, Object obj) {
		if (obj == null) {
			ExceptionHandler.handle(new IllegalArgumentException(name + 
					" cannot be null."));
		}
	}
	
	public final static void stringNotEmpty(String name, String param) {
		notNull(name, param);
		if (param.isEmpty()) {
			ExceptionHandler.handle(new IllegalArgumentException(name + 
					" cannot be empty."));
		}
	}

	public final static void collectionNotEmpty(String name, Collection<?> param) {
		notNull(name, param);
		if (param.isEmpty()) {
			ExceptionHandler.handle(new IllegalArgumentException(name + 
					" cannot be empty."));
		}
	}
	
	public final static void notNegative(String name, Number num) {
		notNull(name, num);
		if (num.toString().charAt(0) == '-') {
			ExceptionHandler.handle(new IllegalArgumentException(name + 
					" cannot be negative"));
		}
	}
	
	public final static void matchClass(String name, Object obj, Class<?> c) {
		notNull(name, obj);
		notNull(name + " (Class)", c);
		if (obj.getClass() != c) {
			ExceptionHandler.handle(new IllegalArgumentException(name + 
					" must have the following class: " + c));
		}
	}
}
