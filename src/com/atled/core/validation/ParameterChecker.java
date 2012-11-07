package com.atled.core.validation;

import java.util.Collection;

public final class ParameterChecker {

	public final static void notNull(String name, Object obj) {
		if (obj == null) {
			throw new IllegalArgumentException(name + " cannot be null.");
		}
	}
	
	public final static void stringNotEmpty(String name, String param) {
		notNull(name, param);
		if (param.isEmpty()) {
			throw new IllegalArgumentException(name + " cannot be empty.");
		}
	}

	public final static void collectionNotEmpty(String name, Collection<?> param) {
		notNull(name, param);
		if (param.isEmpty()) {
			throw new IllegalArgumentException(name + " cannot be empty.");
		}
	}
	
	public final static void notNegative(String name, Number num) {
		notNull(name, num);
		if (num.toString().charAt(0) == '-') {
			throw new IllegalArgumentException(name + " cannot be negative");
		}
	}
}
