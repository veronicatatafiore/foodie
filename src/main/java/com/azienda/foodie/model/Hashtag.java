package com.azienda.foodie.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Hashtag {
	
	@Id
	private String nome;
	@ManyToMany
	@JoinTable(name="HashtagPerPost", joinColumns =
	@JoinColumn(name="nome_hashtag"), inverseJoinColumns = 
	@JoinColumn(name="id_post"))
	private List<Post> post;

}
