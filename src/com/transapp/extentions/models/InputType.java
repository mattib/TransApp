package com.transapp.extentions.models;

public enum InputType {

	TaskStatusChange(1),
	
	ImageId(2),
	
	SignatureId(3),
	
	UserStatusChange(4),
	
	UserComment(5);
	
	private int value; 
	
	private InputType(int value) {
		this.value = value;
	}
	
	public static InputType fromValue(int value) { 
		for (InputType my: InputType.values()) {
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
