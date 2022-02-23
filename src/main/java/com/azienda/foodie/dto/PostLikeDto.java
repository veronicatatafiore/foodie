package com.azienda.foodie.dto;

public class PostLikeDto {
	private String username;
	private String password;
	private Integer idPost;
	
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
	public Integer getIdPost() {
		return idPost;
	}
	public PostLikeDto(String username, String password, Integer idPost) {
		super();
		this.username = username;
		this.password = password;
		this.idPost = idPost;
	}
}
