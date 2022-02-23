package com.azienda.foodie.repository;

import org.springframework.data.repository.CrudRepository;

import com.azienda.foodie.model.Utente;

public interface UtenteRepository extends CrudRepository<Utente, Integer>{
	public Utente findUtenteByUsernameAndPassword (String username, String password);
	public Utente findUtenteByUsernameOrEmail (String username, String email);
}
