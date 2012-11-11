package com.atled.core.logging;

public enum LogLevel {

	DEBUG(0),
	INFO(1),
	ERROR(2),
	PRODUCTION(3);
	
	private int level;
	
	private LogLevel(int level) {
		this.level = level;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public boolean shouldLog(LogLevel otherLevel) {
		return otherLevel.getLevel() >= this.getLevel();
	}
}
