package com.example.course.services.exceptions;

public class DuplicateCPFException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DuplicateCPFException(String msg) {
		super("Already existing cpf");
	}
}
