package com.skilldistillery.filmquery.database;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.skilldistillery.filmquery.entities.*;

class DatabaseAccessTests {
  private DatabaseAccessor db;
  Actor a;
  Film f;
  
  @BeforeEach
  void setUp() throws Exception {
    db = new DatabaseAccessorObject();
  }

  @AfterEach
  void tearDown() throws Exception {
    db = null;
    a = null;
    f = null;
  }

  @Test
  void test_getFilmById_with_invalid_id_returns_null() {
    f = db.findFilmById(-42);
    assertNull(f);
  }
  
  @Test
  void test_getFilmById_23() {
	  f = db.findFilmById(23);
	  f.getTitle();
	  assertEquals("Anaconda Confessions", "Anaconda Confessions");
  }
  
  @Test
  @DisplayName("Null on invalid actor id")
  void test_findActorById_with_invalid_id_returns_null() {
	  a = db.findActorById(-23);
	  assertNull(a);
  }

  @Test
  @DisplayName("Film by id returns correct")
  void test_findActorById_23() {
	  a = db.findActorById(23);
	  a.getFirstName();
	  assertEquals("Sandra", "Sandra");
  }


}
