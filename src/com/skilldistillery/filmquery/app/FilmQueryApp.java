package com.skilldistillery.filmquery.app;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
//		app.test();
		app.launch();
	}

//	private void test() {
//    Film film = db.findFilmById(23);
//    List<Film> films = db.findFilmsByWord("dino");
//    Actor act = db.findActorById(4);
//    List<Actor> list = db.findActorsByFilmId(23);
//    System.out.println(films);
//    System.out.println(film.getLocationsWithCondition());
//    System.out.println(act);
//    printActorList(list);
//	}

	private void launch() {
		Scanner kb = new Scanner(System.in);

		startUserInterface(kb);

	}

	private void startUserInterface(Scanner kb) {
		int choice = 0;
		printBb();

		printMenu1();
		choice = chooseCatch(kb);

		switch (choice) {
		case 1:
			searchFilmMenu(kb);
			break;
		case 2:
			searchActorMenu(kb);
			break;      
		case 3:
			kb.close();
			break;
		}

	}

	private int chooseCatch(Scanner kb) {
		int choice = 0;
		do {
			try {
				choice = kb.nextInt();
				if (choice > 3) {
					System.out.println("Please only input a correct choice");
					choice = kb.nextInt();
				}
				break;
			} catch (InputMismatchException e) {
				System.out.println("Please enter an integer.");
				kb.next();
			}
		} while (true);

		return choice;
	}

	private void printBb() {
		System.out.println("	            @-_________________-@");
		System.out.println("	      @-_____|                 |_____-@");
		System.out.println(" 	       |         BLOCKBUSTER         |");
		System.out.println("	_______|_____________________________|____________");
		System.out.println("	|_________________________________________________|");
		System.out.println("	|               -                -                |");
		System.out.println("	|   -       -             -                     - |");
		System.out.println("	|        ____                  -   ____           |");
		System.out.println("	| -  -  |    |                    |    | -        |");
		System.out.println("	|       |    |                    |    |          |");
		System.out.println("	|  --   |____| -                - |____|      -   |");
		System.out.println("	| -     |    |        --          |    |          |");
		System.out.println("	|    -  |    |-   -      -        |    | --       |");
		System.out.println("	|_______|====|____________________|====|__________|");
		System.out.println("	/ Welcome to Blockbuster. What are you looking for \\");
		System.out.println("	/___________________________________________________\\");

	}

	private void searchFilmMenu(Scanner kb) {
		System.out.println("+-----+-------------+");
		System.out.println("|   Find Film by    |");
		System.out.println("+-----+-------------+");
		System.out.println("| Num |    Desc     |");
		System.out.println("+-----+-------------+");
		System.out.println("|  1. | By Film ID  |");
		System.out.println("|  2. | By Keyword  |");
		System.out.println("+-----+-------------+");
		Film film = null;
		int choice = chooseCatch(kb);
		switch (choice) {
		case 1:
			System.out.print("Please enter the Film ID you'd like to search by: ");
			int filmID = kb.nextInt();
			film = db.findFilmById(filmID);
			if (film == null) {
				System.out.println("Your search returned nothing");
			} else {
				// If the film is found, its title, year, rating, and description are displayed
				System.out.println(film.methodString());
			}
			filmSubMenu(film, kb);
			break;
		case 2:
			System.out.print("Please enter the Keyword you'd like to search by: ");
			String keyword = kb.next();
			List<Film> films = db.findFilmsByWord(keyword);
			if (films.isEmpty()) {
				System.out.println("Your search returned nothing");
			} else {
				for (Film film2 : films) {
					System.out.println(film2.methodString());
				}
			}
			filmSubMenu(films, kb);
			break;
		}
	}

	private void searchActorMenu(Scanner kb) {
		System.out.println("+-----+-------------+");
		System.out.println("|   Find Actor by   |");
		System.out.println("+-----+-------------+");
		System.out.println("| Num |    Desc     |");
		System.out.println("+-----+-------------+");
		System.out.println("|  1. | By Film  ID |");
		System.out.println("|  2. | By Actor ID |");
		System.out.println("+-----+-------------+");
		int choice = chooseCatch(kb);
		switch (choice) {
		case 1:
			System.out.print("Please enter the Film ID you'd like to search by: ");
			Integer filmID = Integer.parseInt(((kb.next()).trim()));
			List<Actor> actors = db.findActorsByFilmId(filmID);
			if (actors == null) {
				System.out.println("Your search returned nothing");
			} else {
				printActorList(actors);
			}
			break;
		case 2:
			System.out.print("Please enter the Actor ID you'd like to search by: ");
			int actorID = kb.nextInt();
			Actor actor = db.findActorById(actorID);
			if (actor == null) {
				System.out.println("Your search returned nothing");
			} else {
				System.out.println(actor);
			}
			break;
		}
	}

	private void printMenu1() {
		System.out.println("                        +-----+-----------+");
		System.out.println("                        | Num |   Desc    |");
		System.out.println("                        +-----+-----------+");
		System.out.println("                        |  1. |   Film    |");
		System.out.println("                        |  2. |   Actor(s)|");
		System.out.println("                        |  3. |   Exit    |");
		System.out.println("                        +-----+-----------+");

	}

	private void printActorList(List<Actor> actors) {
		String alignFormat = "| %-4d| %-11s | %-12s |%n";
		System.out.println("+-----+-------------+--------------+");
		System.out.println("|              Actors              |");
		System.out.println("+-----+-------------+--------------+");
		System.out.println("| id  | first_name  | last_name    |");
		System.out.println("+-----+-------------+--------------+");
		for (Actor actor : actors) {
			int id = actor.getId();
			String fname = actor.getFirstName();
			String lname = actor.getLastName();
			System.out.printf(alignFormat, id, fname, lname);

		}
		System.out.println("+-----+-------------+--------------+");
	}

	private void filmSubMenu(Film film, Scanner kb) {
		System.out.println("+-----+-----------------------------+");
		System.out.println("| Num |             Desc            |");
		System.out.println("+-----+-----------------------------+");
		System.out.println("|  1. | View ALL the film details   |");
		System.out.println("|  2. | Return to Main              |");
		System.out.println("+-----+-----------------------------+");

		int choice = chooseCatch(kb);
		switch (choice) {
		case 1:
			System.out.println(film.toString());
			startUserInterface(kb);
			break;
		case 2:
			startUserInterface(kb);
			break;
		}
	}
	private void filmSubMenu(List<Film> films, Scanner kb) {
		System.out.println("+-----+-----------------------------+");
		System.out.println("| Num |             Desc            |");
		System.out.println("+-----+-----------------------------+");
		System.out.println("|  1. | View ALL the film details   |");
		System.out.println("|  2. | Return to Main              |");
		System.out.println("+-----+-----------------------------+");
		
		int choice = chooseCatch(kb);
		switch (choice) {
		case 1:
			for (Film film : films) {
				System.out.println(film.toString());
			}
			startUserInterface(kb);
			break;
		case 2:
			startUserInterface(kb);
			break;
		}
	}
}
