// Person.java
package model;

public abstract class Person {
	protected String name;
	
	public Person() {
		
	}

	public Person(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
