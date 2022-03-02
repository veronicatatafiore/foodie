package com.azienda.foodie.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Utente {
	
	@Id //chiave primaria
	@GeneratedValue(strategy=GenerationType.IDENTITY) //genera id in automatico e autoincrementante
	private Integer id;
	private String nome;
	private String cognome;
	private boolean admin;
	private String email;
	private String password;
	private String username;
	private String descrizione;
	
	@OneToMany(mappedBy="utente")
	@JsonIgnore
	private List<Post> posts;
	
	
	@ManyToMany(mappedBy = "unlikers")
	@JsonIgnore
	private List<Post> postUnlike;
	

	@ManyToMany
	@JoinTable(name="TabellaLike", joinColumns =
	@JoinColumn(name="id_utente3"), inverseJoinColumns = 
	@JoinColumn(name="id_post3"))
	@JsonIgnore
	private List<Post> postLike;
	
	
	public Utente() {
		super();
	}
	
	
	public Utente(String password, String username, boolean admin) {
		super();
		this.password = password;
		this.username = username;
		this.admin = admin;
	}
	public Utente(String username, String password) {
		super();
		this.password = password;
		this.username = username;
	}


	public Utente(String nome, String cognome, String email, String password, String username, String descrizione, boolean admin) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.password = password;
		this.username = username;
		this.descrizione = descrizione;
		this.admin = admin;
	}

	public Integer getId() {
		return id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
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
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public List<Post> getPosts() {
		return posts;
	}
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}


	public List<Post> getPostUnlike() {
		return postUnlike;
	}


	public void setPostUnlike(List<Post> postUnlike) {
		this.postUnlike = postUnlike;
	}


	public List<Post> getPostLike() {
		return postLike;
	}


	public void setPostLike(List<Post> postLike) {
		this.postLike = postLike;
	}
	
	
}
