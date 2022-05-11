package com.devsuperior.dslearnbds.services.exceptions;

public class UnauthorizedExcpetion extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public UnauthorizedExcpetion(String msg) {
		super(msg);
	}

}
