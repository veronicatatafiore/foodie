package com.azienda.foodie.exception;

public class InvalidCredentialsException extends Exception{
	public InvalidCredentialsException (String messaggio) {
		super(messaggio);
	}
}
