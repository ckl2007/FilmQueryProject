package com.skilldistillery.filmquery.entities;

public class Actor {
	
	private int id;
	private String firstName;
	private String lastName;

	//Film class with attributes that map to the columns of the film table. 
	//Use appropriate datatypes and Java naming conventions. 
	//Provide getters and setters, and appropriate constructors. 
	//Also add a good toString, and equals and hashCode methods.
	
	//Define an Actor class with id, first name, and last name fields.
	// Include the usual getters, setters, toString, etc (of course!), 
	//and appropriate constructor(s).
	
	
	public void setLastName(String string) {
		lastName = string;
	}
	
	public void setFirstName(String string) {
		firstName = string;
	}
	
	public void setId(int int1) {
		id = int1;
	}
	
	public Actor() {}



	public Actor(int id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(id + " ").append(firstName).append(" ").append(lastName);
		return builder.toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + id;
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Actor other = (Actor) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id != other.id)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		return true;
	}
}
