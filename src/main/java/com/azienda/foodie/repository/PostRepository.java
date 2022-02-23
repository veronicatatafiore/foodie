package com.azienda.foodie.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.azienda.foodie.model.Post;
import com.azienda.foodie.model.Utente;

public interface PostRepository extends CrudRepository<Post, Integer>{
	public List<Post> findAllByVisibileTrue(); 
	public List<Post> findAllByVisibileTrueAndUtente(Utente u); 
	public List<Post> findAllByVisibileTrueAndDataEOraBetween(LocalDateTime from,LocalDateTime to);
	public List<Post> findAllByVisibileTrueAndDataEOraBetweenAndUtenteEquals(LocalDateTime from,LocalDateTime to,Utente u);
	public List<Post> findAllByVisibileTrueAndTitoloContainsOrDescrizioneContains(String titolo,String descrizione);
	public List<Post> findAllByVisibileTrueAndUtenteEqualsAndTitoloContainsOrDescrizioneContains(Utente u, String titolo,String descrizione);


}
