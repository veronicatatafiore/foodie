package com.azienda.foodie.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azienda.foodie.dto.PostDataDto;
import com.azienda.foodie.dto.PostLikeDto;
import com.azienda.foodie.dto.PostTestoDto;
import com.azienda.foodie.dto.UtenteLoginDto;
import com.azienda.foodie.exception.InvalidCredentialsException;
import com.azienda.foodie.exception.NoPostFoundException;
import com.azienda.foodie.model.Post;
import com.azienda.foodie.model.Utente;
import com.azienda.foodie.service.ServiceManager;

@RestController
@RequestMapping(path="/post", produces = "application/json") //l'url di riferimento
@CrossOrigin(origins="*") //da quali indirizzi si possono ricevere le richieste
public class PostRest {

	@Autowired
	private ServiceManager serviceManager;

	@PostMapping("/createPost")
	public ResponseEntity<?> createPost (@RequestBody PostTestoDto p){
		try {
			Utente user = null;
			try {
				user = serviceManager.esisteUtente(p.getUsername(), p.getPassword());
				serviceManager.createPost(p, user);
			} catch(InvalidCredentialsException e) {
				return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>("Post creato con successo", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("errore generico del server", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/visualizzaPost")
	public ResponseEntity<?> visualizzaPost (@RequestBody UtenteLoginDto u){
		try {
			List<Post> posts = null;
			serviceManager.esisteUtente(u.getUsername(), u.getPassword());
			posts = serviceManager.selectAllPost(u);
			return new ResponseEntity<>(posts, HttpStatus.CREATED);
		} catch(InvalidCredentialsException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NoPostFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("errore generico del server", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/visualizzaPostByUtente")
	public ResponseEntity<?> visualizzaPostByUtente (@RequestBody Utente u){
		try {
			List<Post> posts = null;
			serviceManager.esisteUtente(u.getUsername(), u.getPassword());
			posts = serviceManager.selectAllPostByUtente(u);
			return new ResponseEntity<>(posts, HttpStatus.CREATED);
		} catch(InvalidCredentialsException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NoPostFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("errore generico del server", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/visualizzaPostByIntervallo")
	public ResponseEntity<?> visualizzaPostByIntervallo(@RequestBody PostDataDto p){
		try {
			List<Post> posts = null;
			serviceManager.esisteUtente(p.getUsername(), p.getPassword());
			posts = serviceManager.selectAllPostByIntervallo(p);
			return new ResponseEntity<>(posts, HttpStatus.CREATED);
		} catch(InvalidCredentialsException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NoPostFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("errore generico del server", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/visualizzaPostProprietarioByIntervallo")
	public ResponseEntity<?> visualizzaPostProprietarioByIntervallo(@RequestBody PostDataDto p){
		try {
			List<Post> posts = null;
			Utente user=serviceManager.esisteUtente(p.getUsername(), p.getPassword());
			posts = serviceManager.selectAllPostProprietarioByIntervallo(p,user);
			return new ResponseEntity<>(posts, HttpStatus.CREATED);
		} catch(InvalidCredentialsException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NoPostFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("errore generico del server", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/visualizzaPostCheContieneParola")
	public ResponseEntity<?> visualizzaPostCheContieneParola(@RequestBody PostTestoDto p){
		try {
			List<Post> posts = null;
			serviceManager.esisteUtente(p.getUsername(), p.getPassword());
			posts = serviceManager.selectPostByWords(p);
			return new ResponseEntity<>(posts, HttpStatus.CREATED);
		} catch(InvalidCredentialsException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NoPostFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("errore generico del server", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/visualizzaPostProprietarioCheContieneParola")
	public ResponseEntity<?> visualizzaPostProprietarioCheContieneParola(@RequestBody PostTestoDto p){
		try {
			List<Post> posts = null;
			Utente u=serviceManager.esisteUtente(p.getUsername(), p.getPassword());
			posts = serviceManager.selectPostProprietarioByWords(p,u);
			return new ResponseEntity<>(posts, HttpStatus.CREATED);
		} catch(InvalidCredentialsException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NoPostFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("errore generico del server", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//patch perchè si devono aggiornare solo alcuni campi
	@PatchMapping("/updatePost/{id_post}")
	public ResponseEntity<?> updatePost(@PathVariable("id_post") Integer id_post, @RequestBody PostTestoDto p){
		try {
			Post post = null;
			Utente u = null;
			u = serviceManager.esisteUtente(p.getUsername(), p.getPassword());
			post = serviceManager.updatePost(p, u, id_post);
			return new ResponseEntity<>(post, HttpStatus.CREATED);
		} catch (InvalidCredentialsException | NoPostFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			return new ResponseEntity<>("errore generico del server", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/aggiungiLike")
	public ResponseEntity<?> aggiungiLike(@RequestBody PostLikeDto p){
		try {
			Utente u = null;
			u = serviceManager.esisteUtente(p.getUsername(), p.getPassword());
			serviceManager.aggiungiLike(p, p.getIdPost(), u);
			return new ResponseEntity<>("like inserito con successo!", HttpStatus.CREATED);
		} catch (InvalidCredentialsException | NoPostFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("errore generico del server", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/aggiungiUnlike")
	public ResponseEntity<?> aggiungiUnlike(@RequestBody PostLikeDto p){
		try {
			Utente u = null;
			u = serviceManager.esisteUtente(p.getUsername(), p.getPassword());
			serviceManager.aggiungiUnlike(p, p.getIdPost(), u);
			return new ResponseEntity<>("unlike inserito con successo!", HttpStatus.CREATED);
		} catch (InvalidCredentialsException | NoPostFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("errore generico del server", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/eliminaPost")
	public ResponseEntity<?> eliminaPost(@RequestBody PostLikeDto p){
		try {
			serviceManager.esisteUtente(p.getUsername(), p.getPassword());
			serviceManager.eliminaPost(p);
			return new ResponseEntity<>("post eliminato con successo!", HttpStatus.NO_CONTENT);
		} catch (InvalidCredentialsException | NoPostFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("errore generico del server", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}
