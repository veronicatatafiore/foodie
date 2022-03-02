package com.azienda.foodie.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azienda.foodie.dto.UtenteLoginDto;
import com.azienda.foodie.exception.InvalidCredentialsException;
import com.azienda.foodie.exception.InvalidFieldsException;
import com.azienda.foodie.model.Utente;
import com.azienda.foodie.service.ServiceManager;

@RestController
@RequestMapping(path="/utenti", produces = "application/json") //l'url di riferimento
@CrossOrigin(origins="*") //da quali indirizzi si possono ricevere le richieste
public class UtenteRest {
	
	@Autowired
	private ServiceManager serviceManager;
	
	@PostMapping("/registrazione")
	//tramite l'@RequestBody i parametri per il metodo post vengono passati tramite il body e non tramite l'url
	public ResponseEntity<String> registrazione(@RequestBody Utente u){
		try {
			serviceManager.registrazione(u);
			return new ResponseEntity<>(null, HttpStatus.CREATED);
		} catch (InvalidCredentialsException | InvalidFieldsException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<> (e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody UtenteLoginDto u){
		try {
			serviceManager.login(u);
			return new ResponseEntity<>(null, HttpStatus.OK);
			
		} catch (InvalidCredentialsException | InvalidFieldsException e) {
			 return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			 return new ResponseEntity<>("Errore nel server", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

}
