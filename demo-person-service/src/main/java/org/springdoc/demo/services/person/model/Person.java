package org.springdoc.demo.services.person.model;

import javax.money.MonetaryAmount;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Person {

	private long id;

	@Size(min = 2, max = 50)
	@NotBlank
	private String firstName;

	@Size(min = 2, max = 50)
	@NotBlank
	private String lastName;

	@Pattern(regexp = ".+@.+\\..+", message = "Please provide a valid email address")
	private String email1;

	@Email
	private String email2;

	@Min(18)
	@Max(30)
	private int age;

	@JsonProperty
	private MonetaryAmount worth;

	public MonetaryAmount getWorth() {
		return worth;
	}

	public void setWorth(MonetaryAmount worth) {
		this.worth = worth;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}