package com.azienda.foodie.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.azienda.foodie.dto.PostDataDto;
import com.azienda.foodie.dto.PostLikeDto;
import com.azienda.foodie.dto.PostTestoDto;
import com.azienda.foodie.dto.UtenteLoginDto;
import com.azienda.foodie.exception.InvalidCredentialsException;
import com.azienda.foodie.exception.InvalidFieldsException;
import com.azienda.foodie.exception.NoPostFoundException;
import com.azienda.foodie.model.Post;
import com.azienda.foodie.model.Utente;
import com.azienda.foodie.repository.PostRepository;
import com.azienda.foodie.repository.UtenteRepository;

@Service
@Transactional //le query vengono fatte come transaction
public class ServiceManager {
	
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private UtenteRepository utenteRepository;
	
	@Bean("BeanAdmin")
	public void admin () throws InvalidCredentialsException {
		if(utenteRepository.findUtenteByUsernameAndPassword("admin", "admin") == null) {
			Utente u = new Utente("admin", "admin", true);
			utenteRepository.save(u);
		}
	}

	//registrazione
	public void registrazione (Utente u) throws InvalidCredentialsException, InvalidFieldsException {
		String messaggioErrore = "";
		if(u.getEmail() == null) {
			messaggioErrore += "email non inserita\n";
		}
		if(u.getPassword() == null) {
			messaggioErrore += "password non inserita\n";
		}
		if(u.getUsername() == null) {
			messaggioErrore += "username non inserito\n";
		}
		if(u.getNome() == null) {
			messaggioErrore += "nome non inserito\n";
		}
		if(u.getCognome() == null) {
			messaggioErrore += "cognome non inserito\n";
		}
		
		if(utenteRepository.findUtenteByUsernameOrEmail(u.getUsername(), u.getEmail()) != null) {
			throw new InvalidCredentialsException("Credenziali non valide");
		}
		
		if (messaggioErrore == "") {
			//salvo l'utente nel db
			utenteRepository.save(u);
		} else throw new InvalidFieldsException(messaggioErrore);
	}
	
	//esiste utente
	public Utente esisteUtente(String username, String password) throws InvalidCredentialsException {
		Utente user = utenteRepository.findUtenteByUsernameAndPassword(username, password);
		if(user == null) {
			throw new InvalidCredentialsException("credenziali errate");
		}
		
		return user;
	}
	
	//login
	public void login(UtenteLoginDto u) throws InvalidCredentialsException, InvalidFieldsException {
		if(u.getPassword() == null || u.getUsername() == null) {
			throw new InvalidFieldsException("credenziali non inserite");
		}else {
			esisteUtente(u.getUsername(), u.getPassword());
		}
	}
	
	//creazione post
	public void createPost(PostTestoDto p, Utente u) {
		Post post = new Post(LocalDateTime.now(), p.getDescrizione(), p.getTitolo(), LocalDateTime.now(),0,0, p.getUrlImmagine());
		post.setUtente(u);
		post.setVisibile(true);
		postRepository.save(post);
	}
	
	//visualizza tutti i post
	public List<Post> selectAllPost(UtenteLoginDto u) throws NoPostFoundException {
		List<Post> posts = postRepository.findAllByVisibileTrue();
		if(posts.size() == 0) {
			throw new NoPostFoundException("non ci sono post");
		}
		return posts;
	}
	
	//visualizza tutti i post di un proprietario
	public List<Post> selectAllPostByUtente(Utente u) throws NoPostFoundException, InvalidCredentialsException {
		List<Post> posts = postRepository.findAllByVisibileTrueAndUtente(u);
		if(posts.size() == 0) {
			throw new NoPostFoundException("utente senza post");
		}
		return posts;
	}
	
	//ricerca di tutti i post la cui data di ultimo aggiornamento sia compresa in un intervallo passato in input
	public List<Post> selectAllPostByIntervallo(PostDataDto p) throws NoPostFoundException, InvalidCredentialsException{
		List<Post> posts = postRepository.findAllByVisibileTrueAndDataEOraBetween(p.getDataInizio(),p.getDataFine());
		if(posts.size() == 0) {
			throw new NoPostFoundException("nell'intervallo non ci sono post");
		}
		return posts;
	}
	
	//ricerca di tutti i post  di un determiinato proprietario la cui data di ultimo aggiornamento sia compresa in un intervallo passato in input
	public List<Post> selectAllPostProprietarioByIntervallo(PostDataDto p,Utente user) throws NoPostFoundException, InvalidCredentialsException{
		List<Post> posts = postRepository.findAllByVisibileTrueAndDataEOraBetweenAndUtenteEquals(p.getDataInizio(),p.getDataFine(),user);
		if(posts.size() == 0) {
			throw new NoPostFoundException("nell'intervallo non ci sono post del proprietario");
		}
		return posts;
	}
	
	//ricerca di tutti i post che contengono detrminate parole nel titolo e/o nella descrizione
	public List<Post> selectPostByWords(PostTestoDto p) throws NoPostFoundException{
		List<Post> posts = postRepository.findAllByVisibileTrueAndTitoloContainsOrDescrizioneContains(p.getTitolo(),p.getDescrizione());
		if(posts.size() == 0) {
			throw new NoPostFoundException("non ci sono post che contengono titolo o descrizione richiesti");
		}
		return posts;
	}
	
	//ricerca di tutti i post di un determinato proprietario che contengono detrminate parole nel titolo e/o nella descrizione
	public List<Post> selectPostProprietarioByWords(PostTestoDto p,Utente u) throws NoPostFoundException{
		List<Post> posts = postRepository.findAllByVisibileTrueAndUtenteEqualsAndTitoloContainsOrDescrizioneContains(u, p.getTitolo(),p.getDescrizione());
		if(posts.size() == 0) {
			throw new NoPostFoundException("non ci sono post che contengono titolo o descrizione richiesti");
		}
		return posts;
	}
	
	//aggiornamento post
	public Post updatePost(PostTestoDto p,Utente u,Integer id_P) throws InvalidCredentialsException, NoPostFoundException {
		Post post=getPostById(id_P);
		if(post==null) {
			throw new NoPostFoundException("post non trovato ");
		}
		if(u.getId()!=post.getUtente().getId()) {
			throw new InvalidCredentialsException("non sei il proprietario di questo post");
		}
		if(p.getTitolo()!=null) post.setTitolo(p.getTitolo());
		if(p.getDescrizione()!=null) post.setDescrizione(p.getDescrizione());
		post.setDataEOraUpdate(LocalDateTime.now());
		
		return postRepository.save(post);
	}
	
	//ricerca post tramite id
	public Post getPostById(Integer id_p) {
		Optional<Post> p= postRepository.findById(id_p);
		if(p.isPresent()) return p.get();
		else return null;
		
	}
	
	//aggiungi like
	public void aggiungiLike(PostLikeDto p, Integer idPost, Utente u) throws NoPostFoundException, InvalidCredentialsException {
		Post post = getPostById(idPost);
		if(post == null) {
			throw new NoPostFoundException("post non trovato");
		}
		if(u.getId() == post.getUtente().getId()) {
			throw new InvalidCredentialsException("non puoi metterti like da solo");
		}
		if(post.getLikers().contains(u)) {
			throw new InvalidCredentialsException("non puoi mettere like più di una volta");
		}
		post.setNumeroLike(post.getNumeroLike()+1);
		post.getLikers().add(u);
		u.getPostLike().add(post);
		if(post.getUnlikers().contains(u)) {
			post.setNumeroUnlike(post.getNumeroUnlike()-1);
			post.getUnlikers().remove(u);
			u.getPostUnlike().remove(post);
		}
	}
	
	//aggiungi unlike
	public void aggiungiUnlike(PostLikeDto p, Integer idPost, Utente u) throws NoPostFoundException, InvalidCredentialsException {
		Post post = getPostById(idPost);
		if(post == null) {
			throw new NoPostFoundException("post non trovato");
		}
		if(u.getId() == post.getUtente().getId()) {
			throw new InvalidCredentialsException("non puoi metterti unlike da solo");
		}
		if(post.getUnlikers().contains(u)) {
			throw new InvalidCredentialsException("non puoi mettere unlike più di una volta");
		}
		System.out.println(post.getNumeroUnlike());
		post.setNumeroUnlike(post.getNumeroUnlike()+1);
		post.getUnlikers().add(u);
		u.getPostUnlike().add(post);
		if(post.getLikers().contains(u)) {
			post.setNumeroLike(post.getNumeroLike()-1);
			post.getLikers().remove(u);
			u.getPostLike().remove(post);
		}
	}
	
	//eliminazione logica dal db di un post
	public void eliminaPost(PostLikeDto p) throws InvalidCredentialsException, NoPostFoundException {
		Post post = getPostById(p.getIdPost());
		if(post == null) {
			throw new NoPostFoundException("post non trovato");
		}
		if(!post.getUtente().getUsername().equals(p.getUsername()) && !p.getUsername().equals("admin")) {
			throw new InvalidCredentialsException("non hai i permessi per eliminare il post");
		}
		post.setVisibile(false);
		postRepository.save(post);
	}
	
}
