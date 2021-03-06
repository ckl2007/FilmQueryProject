package com.skilldistillery.filmquery.database;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";
	private static final String user = "student";
	private static final String pass = "student";

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {

		}

	}

	@Override
	public Film findFilmById(int filmId) {
		Film film = null;
		String sqltxt = "select film.id, film.title, film.description, film.release_year, language.name"
				+ ", film.rental_duration, film.rental_rate, film.length, film.replacement_cost, film.rating"
				+ ", film.special_features, category.name from film JOIN film_category on film.id = film_category.film_id "
				+ "JOIN language on film.language_id = language.id JOIN category on film_category.category_id = category.id "
				+ "where film.id = ?";
		try (Connection conn = DriverManager.getConnection(URL, user, pass);
				PreparedStatement stmt = conn.prepareStatement(sqltxt);) {
			stmt.setInt(1, filmId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				film = new Film(); // Create the object
				// Here is our mapping of query columns to our object fields:
				film.setId(rs.getInt("id"));
				film.setTitle(rs.getString("title"));
				film.setDescription(rs.getString("description"));
				film.setRelease_year(rs.getInt("release_year"));
				film.setRental_duration(rs.getInt("rental_duration"));
				film.setRental_rate(rs.getDouble("rental_rate"));
				film.setLength(rs.getInt("length"));
				film.setReplacement_cost(rs.getDouble("replacement_cost"));
				film.setRating(rs.getString("rating"));
				film.setSpecial_features(rs.getString("special_features"));
				film.setLanguage(rs.getString(5));
				film.setCategory(rs.getString("category.name"));
				film.setActors(findActorsByFilmId(filmId));
				film.setLocationsWithCondition(inventoryMaps(film.getId()));
			}

		} catch (SQLException e) {
			System.err.println(e);
		}
		return film;
	}

	public List<Film> findFilmsByWord(String keyword) {
		Film film = null;
		List<Film> films = new ArrayList<>();
		String sqltxt = "select film.id, film.title, film.description, film.release_year, language.name"
				+ ", film.rental_duration, film.rental_rate, film.length, film.replacement_cost, film.rating"
				+ ", film.special_features, category.name from film JOIN film_category on film.id = film_category.film_id "
				+ "JOIN language on film.language_id = language.id JOIN category on film_category.category_id = category.id "
				+ "where title like ? or description like ?";
		try (Connection conn = DriverManager.getConnection(URL, user, pass);
				PreparedStatement stmt = conn.prepareStatement(sqltxt);) {
			stmt.setString(1, "%" + keyword + "%");
			stmt.setString(2, "%" + keyword + "%");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				film = new Film(); // Create the object
				// Here is our mapping of query columns to our object fields:
				film.setId(rs.getInt("id"));
				film.setTitle(rs.getString("title"));
				film.setDescription(rs.getString("description"));
				film.setRelease_year(rs.getInt("release_year"));
				film.setRental_duration(rs.getInt("rental_duration"));
				film.setRental_rate(rs.getDouble("rental_rate"));
				film.setLength(rs.getInt("length"));
				film.setReplacement_cost(rs.getDouble("replacement_cost"));
				film.setRating(rs.getString("rating"));
				film.setSpecial_features(rs.getString("special_features"));
				film.setLanguage(rs.getString("language.name"));
				film.setCategory(rs.getString("category.name"));
				film.setLocationsWithCondition(inventoryMaps(film.getId()));
				films.add(film);

			}

		} catch (SQLException e) {
			System.err.println(e);
		}
		return films;
	}

	@Override
	public Actor findActorById(int actorId) {
		Actor actor = null;
		String sqltxt = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
		try (Connection conn = DriverManager.getConnection(URL, user, pass);
				PreparedStatement stmt = conn.prepareStatement(sqltxt);) {
			stmt.setInt(1, actorId);
			ResultSet actorResult = stmt.executeQuery();
			if (actorResult.next()) {
				actor = new Actor(); // Create the object
				// Here is our mapping of query columns to our object fields:
				actor.setId(actorResult.getInt(1));
				actor.setFirstName(actorResult.getString(2));
				actor.setLastName(actorResult.getString(3));
			}

		} catch (SQLException e) {
			System.err.println(e);
		}
		return actor;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		List<Actor> actors = new ArrayList<>();
		Actor actor = null;
		String sqltxt = "SELECT id, first_name, last_name from actor"
				+ " JOIN film_actor on actor.id = film_actor.actor_id where film_actor.film_id = ?";
		try (Connection conn = DriverManager.getConnection(URL, user, pass);
				PreparedStatement stmt = conn.prepareStatement(sqltxt);) {
			stmt.setInt(1, filmId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				actor = new Actor(); // Create the object
				actor.setId(rs.getInt("id"));
				actor.setFirstName(rs.getString("first_name"));
				actor.setLastName(rs.getString("last_name"));
				actors.add(actor);
			}
		} catch (SQLException e) {
			System.err.println(e);
		}
		return actors;
	}

	private Map<Integer, Map<Integer, String>> inventoryMaps(int filmId) {
		Map<Integer, Map<Integer, String>> locsInventory = new HashMap<>();
		Map<Integer, String> invIdMedia = new HashMap<>();
		Set<Integer> storeSet = new HashSet<>();
		int oldStore = 0;
		String sqltxt = "SELECT * from inventory_item where film_id = ?";
		try (Connection conn = DriverManager.getConnection(URL, user, pass);
				PreparedStatement stmt = conn.prepareStatement(sqltxt);) {
			stmt.setInt(1, filmId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int invID = rs.getInt(1);
				String mediaCond = rs.getString(4);
				int storeID = rs.getInt(3);
				if (storeSet.contains(storeID)) {
					invIdMedia.put(invID, mediaCond);
					oldStore = storeID;

				} else {
					locsInventory.put(oldStore, invIdMedia);
					storeSet.add(storeID);
					invIdMedia = new HashMap<>();
					invIdMedia.put(invID, mediaCond);

				}

			}
			locsInventory.put(oldStore, invIdMedia);

		} catch (SQLException e) {
			System.err.println(e);
		}
		locsInventory.remove(0);
		return locsInventory;
	}

}
