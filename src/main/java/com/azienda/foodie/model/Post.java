package com.azienda.foodie.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;


@Entity
public class Post {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private LocalDateTime dataEOra;
	private LocalDateTime dataEOraUpdate;
	private String descrizione;
	private String titolo;
	private Integer numeroLike;
	private Integer numeroUnlike;
	private Boolean visibile;
	
	@ManyToOne
	@JoinColumn(name="posts")
	private Utente utente;
	
	@ManyToMany
	@JoinTable(name="PostPerCategoria", joinColumns =
	@JoinColumn(name="id_post"), inverseJoinColumns = 
	@JoinColumn(name="id_categoria"))
	private List<Categoria> categorie;
	
	@ManyToMany(mappedBy = "post")
	private List<Hashtag> hashtag;
	
	@ManyToMany
	@JoinTable(name="Unlike", joinColumns =
	@JoinColumn(name="id_post2"), inverseJoinColumns = 
	@JoinColumn(name="id_utente2"))
	private List<Utente> unlikers;

	@ManyToMany(mappedBy = "postLike")
	private List<Utente> likers;
	
	public Post() {
		super();
	}
	
	public Post(LocalDateTime dataEOra, String descrizione, String titolo, LocalDateTime dataEOraUpdate, 
			Integer numeroLike, Integer numeroUnlike) {
		super();
		this.dataEOra = dataEOra;
		this.descrizione = descrizione;
		this.titolo = titolo;
		this.dataEOraUpdate = dataEOraUpdate;
		this.numeroLike = numeroLike;
		this.numeroUnlike = numeroUnlike;
	}

	public Integer getId() {
		return id;
	}
	public LocalDateTime getDataEOra() {
		return dataEOra;
	}
	public void setDataEOra(LocalDateTime dataEOra) {
		this.dataEOra = dataEOra;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public Utente getUtente() {
		return utente;
	}
	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public LocalDateTime getDataEOraUpdate() {
		return dataEOraUpdate;
	}
	public void setDataEOraUpdate(LocalDateTime dataEOraUpdate) {
		this.dataEOraUpdate = dataEOraUpdate;
	}
	public Integer getNumeroLike() {
		return numeroLike;
	}
	public void setNumeroLike(Integer numeroLike) {
		this.numeroLike = numeroLike;
	}
	public Integer getNumeroUnlike() {
		return numeroUnlike;
	}
	public void setNumeroUnlike(Integer numeroUnlike) {
		this.numeroUnlike = numeroUnlike;
	}
	public List<Utente> getUnlikers() {
		return unlikers;
	}
	public List<Utente> getLikers() {
		return likers;
	}

	public Boolean isVisibile() {
		return visibile;
	}

	public void setVisibile(Boolean visibile) {
		this.visibile = visibile;
	}
	
}
