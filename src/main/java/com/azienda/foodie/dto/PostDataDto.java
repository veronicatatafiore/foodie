package com.azienda.foodie.dto;

import java.time.LocalDateTime;

public class PostDataDto {
	private String username;
	private String password;
	private LocalDateTime dataInizio;
	private LocalDateTime dataFine;
	public PostDataDto(String username, String password, LocalDateTime dataInizio, LocalDateTime dataFine) {
		super();
		this.username = username;
		this.password = password;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
	}
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
	public LocalDateTime getDataInizio() {
		return dataInizio;
	}
	public void setDataInizio(LocalDateTime dataInizio) {
		this.dataInizio = dataInizio;
	}
	public LocalDateTime getDataFine() {
		return dataFine;
	}
	public void setDataFine(LocalDateTime dataFine) {
		this.dataFine = dataFine;
	}
	
}
