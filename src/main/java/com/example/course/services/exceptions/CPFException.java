package com.example.course.services.exceptions;

public class CPFException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CPFException(String cpf) {
		super("INVALID EMAIL, CPF OR NAME"+ cpf);
	}
}
