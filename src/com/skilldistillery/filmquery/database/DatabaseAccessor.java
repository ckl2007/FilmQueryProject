package com.skilldistillery.filmquery.database;

import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

//With the Actor class implemented, uncomment the 
//two commented methods in the DatabaseAccessor interface.

public interface DatabaseAccessor {
	public Film findFilmById(int filmId);
	public List<Film> findFilmsByWord(String keyword);
	public Actor findActorById(int actorId);
	public List<Actor> findActorsByFilmId(int filmId);

}
