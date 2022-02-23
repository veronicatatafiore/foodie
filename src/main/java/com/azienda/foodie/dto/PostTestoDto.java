package com.azienda.foodie.dto;

public class PostTestoDto {
	
	private String username;
	private String password;
	private String titolo;
	private String descrizione;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public PostTestoDto(String username, String password, String titolo, String descrizione) {
		super();
		this.username = username;
		this.password = password;
		this.titolo = titolo;
		this.descrizione = descrizione;
	}
}
