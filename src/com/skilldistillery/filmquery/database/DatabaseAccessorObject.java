package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
		String sqltxt = "SELECT * FROM film where film.id= ?";
		try (Connection conn = DriverManager.getConnection(URL, user, pass);
				PreparedStatement stmt = conn.prepareStatement(sqltxt);
				ResultSet rs = stmt.executeQuery();) {
			stmt.setInt(1, filmId);
			ResultSet fR = stmt.executeQuery();
			if (fR.next()) {
				film = new Film(); // Create the object
				// Here is our mapping of query columns to our object fields:
				film.setId(fR.getInt(1));
				film.setTitle(fR.getString(2));
				film.setDescription(fR.getString(2));
				film.setRelease_year(fR.getInt(2));
				film.setRental_duration(fR.getInt(2));
				film.setRental_rate(fR.getDouble(2));
				film.setLength(fR.getInt(2));
				film.setReplacement_cost(fR.getDouble(2));
				film.setRating(fR.getString(2));
				film.setSpecial_features(fR.getString(2));
			}

		} catch (SQLException e) {
			System.err.println(e);
		}
		return film;
	}

	public Actor findActorById(int actorId) {
		Actor actor = null;
		String sqltxt = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
		try (Connection conn = DriverManager.getConnection(URL, user, pass);
				PreparedStatement stmt = conn.prepareStatement(sqltxt);
				ResultSet rs = stmt.executeQuery();) {
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
		String sqltxt = "SELECT actor.id, actor.first_name, actor.last_name from actor"
				+ " JOIN film_actor on actor.id = film_actor.actor_id where film_actor.film_id = ";
		try (Connection conn = DriverManager.getConnection(URL, user, pass);
				PreparedStatement stmt = conn.prepareStatement(sqltxt);
				ResultSet rs = stmt.executeQuery();) {
			if(rs.next()) {
				actor = new Actor(); // Create the object
				actor.setId(rs.getInt(1));
				actor.setFirstName(rs.getString(2));
				actor.setLastName(rs.getString(3));
				actors.add(actor);
			}
		} catch (SQLException e) {
			System.err.println(e);
		}
		return actors;
	}

}
