package com.example.course.services.exceptions;

public class DifferentPassword extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public DifferentPassword(Object id) {
		super("Different Password");
	}
	
	public DifferentPassword(String msg, Throwable cause){
        super("Different Password", cause);
    }
}
