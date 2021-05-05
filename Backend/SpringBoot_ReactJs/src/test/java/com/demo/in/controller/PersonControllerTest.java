package com.demo.in.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.demo.in.model.Person;
import com.demo.in.service.PersonService;

public class PersonControllerTest {

	@Autowired
	@InjectMocks
	public PersonController personController;

	@Mock
	public PersonService personService;

	private static final int personId = 1;

	@Spy
	List<Person> persons = new ArrayList<>();

	@Spy
	ModelMap model;

	@Mock
	BindingResult result;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		persons = getPersonDetails();
	}

	private List<Person> getPersonDetails() {
		Person person1 = new Person(1, "Aadhi", 10, "Chennai", "aadhi@gmail.com");
		Person person2 = new Person(2, "Akil", 12, "Chennai", "akil@gmail.com");
		Person person3 = new Person(3, "Krithick", 20, "Chennai", "krithick@gmail.com");
		persons.add(person1);
		persons.add(person2);
		persons.add(person3);
		return persons;
	}

	@Test
	public void testGetAllPerson() throws Exception {

		when(personService.findAll()).thenReturn(persons);
		
		ResponseEntity<List<Person>> result = personController.getAll();
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals("Aadhi", result.getBody().get(0).getName());
	}

	@Test
	public void testGetPersonById() throws Exception {
		Person person = new Person(2, "Aadhi", 10, "Chennai", "aadhi@gmail.com");
		when(personService.findById(personId)).thenReturn(person);
		
		ResponseEntity<Person> result = personController.getById(personId);
		assertEquals("Aadhi", result.getBody().getName());
	}

	@Test
	public void testCreatePerson() {
		Person person = new Person("Aadhi", 10, "Chennai", "aadhi@gmail.com");
		when(personService.save(Mockito.any())).thenReturn(person);
	
		ResponseEntity<Person> result = personController.saveData(person);
		assertEquals("Aadhi", result.getBody().getName());
		assertEquals(HttpStatus.CREATED, result.getStatusCode());

	}

	@Test
	public void testUpdatePerson() {
		Person person = new Person(2, "Aadhi", 10, "Chennai", "aadhi@gmail.com");
		when(personService.findById(personId)).thenReturn(person);
		
		Person updatePerson = new Person("Akil", 10, "Chennai", "aadhi@gmail.com");
		when(personService.update(Mockito.any())).thenReturn(updatePerson);
		
		ResponseEntity<?> result = personController.updateById(personId, updatePerson);
		assertEquals(HttpStatus.CREATED, result.getStatusCode());
		assertEquals("Akil", ((Person) result.getBody()).getName());
	}

	@Test
	public void testDeletePerson() {
		Person person = new Person(2, "Aadhi", 10, "Chennai", "aadhi@gmail.com");
		when(personService.findById(personId)).thenReturn(person);
		
		ResponseEntity<?> result = personController.deleteById(personId);
		assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
	}

}
