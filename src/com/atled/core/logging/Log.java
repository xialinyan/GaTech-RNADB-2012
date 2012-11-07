package com.atled.core.logging;

import java.util.Calendar;

import com.atled.core.validation.ParameterChecker;

public class Log {
	
	public final static LogLevel DEFUALT_LOG_LEVEL = LogLevel.DEBUG;

	private final Class<?> instanceClass;
	private LogLevel logLevel;
	
	private Log(Class<?> instanceClass) {
		this.instanceClass = instanceClass;
		this.logLevel = LogLevel.DEBUG;
	}
	
	public final static Log getInstance(Class<?> instanceClass) {
		ParameterChecker.notNull("instanceClass", instanceClass);
		return new Log(instanceClass);
	}
	
	public void debug(Object messageObj) {
		ParameterChecker.notNull("messageObj", messageObj);
		if (logLevel.greaterThanOrEqual(LogLevel.DEBUG)) {
			print(messageObj.toString(), LogLevel.DEBUG);
		}
	}
	
	public void info(Object messageObj) {
		ParameterChecker.notNull("messageObj", messageObj);
		if (logLevel.greaterThanOrEqual(LogLevel.INFO)) {
			print(messageObj.toString(), LogLevel.INFO);
		}
	}
	
	public void error(Object messageObj) {
		ParameterChecker.notNull("messageObj", messageObj);
		if (logLevel.greaterThanOrEqual(LogLevel.ERROR)) {
			print(messageObj.toString(), LogLevel.ERROR);
		}
	}
	
	public void error(Exception exceptionObj) {
		ParameterChecker.notNull("exceptionObj", exceptionObj);
		if (logLevel.greaterThanOrEqual(LogLevel.ERROR)) {
			printError(exceptionObj.getMessage(), LogLevel.ERROR);
			printError(exceptionObj.getStackTrace().toString(), LogLevel.ERROR);
		}
	}
	
	public void production(Object messageObj) {
		ParameterChecker.notNull("messageObj", messageObj);
		if (logLevel.greaterThanOrEqual(LogLevel.PRODUCTION)) {
			print(messageObj.toString(), LogLevel.PRODUCTION);
		}
	}
	
	private void print(String message, LogLevel logLevel) {
		StringBuilder sb = new StringBuilder(getMessageHeader(logLevel));
		sb.append(message);
		System.out.println(sb.toString());
	}
	
	private void printError(String message, LogLevel logLevel) {
		StringBuilder sb = new StringBuilder(getMessageHeader(logLevel));
		sb.append(message);
		System.err.println(sb.toString());
	}
	
	private final static String HEADER_SEPERATOR = "\t";
	
	private String getMessageHeader(LogLevel logLevel) {
		StringBuilder sb = new StringBuilder(instanceClass.getSimpleName());
		sb.append(HEADER_SEPERATOR).append(Calendar.getInstance().toString());
		sb.append(HEADER_SEPERATOR).append(logLevel.name());
		return sb.append(HEADER_SEPERATOR).toString();
	}
}
