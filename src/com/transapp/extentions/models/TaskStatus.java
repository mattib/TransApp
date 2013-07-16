package com.transapp.extentions.models;

public enum TaskStatus {

	UnAssigned(0),
	
	NotStarted(1),
	
	Started(2),
	
	Accepted(3),
	
	Rejected(4),
	
	Finished(5),
	
	Canceled(6),
	
	Reassigned(7);
	
	private int value; 
	
	private TaskStatus(int value) {
		this.value = value;
	}
	
	public static TaskStatus fromValue(int value) { 
		for (TaskStatus my: TaskStatus.values()) {
			if (my.value == value) {
				return my;
			}
		}
		return null;
	}
	
	public int value() {
		return value;
	}
}
