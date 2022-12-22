package com.example.course.entities.enums;

public enum CardStatus {
	
	APROVADO(1),
	RECUSADO(2);
	private int code;
	
	private CardStatus (int code) {
		this.code = code;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public static CardStatus valueOf(int code) {
		for(CardStatus value : CardStatus.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}
		throw new IllegalArgumentException("Invalid CartStatu code");
	}
}
