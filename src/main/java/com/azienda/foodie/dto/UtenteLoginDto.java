package com.azienda.foodie.dto;

public class UtenteLoginDto {
	private String password;
	private String username;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public UtenteLoginDto(String password, String username) {
		super();
		this.password = password;
		this.username = username;
	}


}
