package com.azienda.foodie.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Categoria {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String tipo;
	@ManyToMany(mappedBy = "categorie")
	private List<Post> post;
	
	public Categoria(String tipo) {
		super();
		this.tipo = tipo;
	}
	public Categoria() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
