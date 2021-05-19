package org.springdoc.demo.services.person.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springdoc.demo.services.person.model.Person;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

	private Random ran = new Random();

	@PostMapping("/persons")
	public Person person(@Valid @RequestBody Person person) {
		int nxt = ran.nextInt(10);
		if (nxt >= 5) {
			throw new RuntimeException("Breaking logic");
		}
		return person;
	}

	@GetMapping(path = "/persons")
	public List<Person> findByLastName(@RequestParam @NotBlank @Size(max = 10) String lastName) {
		List<Person> hardCoded = new ArrayList<>();
		Person person = new Person();
		person.setAge(20);
		person.setEmail1("abc1@abc.com");
		person.setEmail2("abc@abc.com");
		person.setFirstName("Somefirstname");
		person.setLastName(lastName);
		person.setId(1);
		hardCoded.add(person);
		return hardCoded;

	}
}
