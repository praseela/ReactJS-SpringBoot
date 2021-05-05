package com.demo.in.controller;

/**
 * @author p.radhakrishnan
 * @version 1.0
 * Date 10-07-2020
 *
 */
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.in.model.Person;
import com.demo.in.service.PersonService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class PersonController {

	// Instance fields	
	@Autowired
	PersonService personService;
	
	/**
	 * Method to display all the Person details using GET API method
	 * @return List of Person Details
	 */
	
	@ApiOperation(httpMethod = "GET", value = "Get All Person Details ", response = Person.class)
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Success|OK"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@GetMapping("/Person")
	
	public ResponseEntity<List<Person>> getAll() {
		
		List<Person> list = personService.findAll();
		
		if (list.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	
	/**
	 * Save the Person Details using the POST API method
	 * @param person
	 * @return Person data
	 */
	
	@ApiOperation(httpMethod = "POST", value = "Create a Person Details", response = Person.class)
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Created"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@PostMapping("/Person")
	
	public ResponseEntity<Person> saveData(@RequestBody Person person) {
		
		if (person == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			Person data = personService.save(person);
			return new ResponseEntity<>(data, HttpStatus.CREATED);

		}
	}

	/**
	 * Update the particular person details using PUT Api method
	 * @param id
	 * @param person
	 * @return updated Person data
	 */
	
	@ApiOperation(httpMethod = "PUT", value = "Update a Person Detail", response = Person.class)
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Created"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@PutMapping("/Person/{id}")
	
	public ResponseEntity<Person> updateById(@PathVariable("id") int id, @RequestBody Person person) {
		Person personData = personService.findById(id);
		if (personData == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		personData.setName(person.getName());
		personData.setAge(person.getAge());
		personData.setAddress(person.getAddress());
		personData.setEmail(person.getEmail());

		Person data = personService.update(personData);
		return new ResponseEntity<Person>(data, HttpStatus.CREATED);
	}

	
	/**
	 * Display one a specific person detail using GET API method
	 * @param id
	 * @return single Person data
	 */
	
	@ApiOperation(httpMethod = "GET", value = "Get a Person Detail ", response = Person.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success|OK"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@GetMapping("/Person/{id}")
	
	public ResponseEntity<Person> getById(@PathVariable("id") int id) {
		Person updateData = personService.findById(id);
		if (updateData == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(updateData, HttpStatus.OK);
	}

	
	/**
	 * Delete a particular Person detail using DELETE API method
	 * @param id
	 * @return no content
	 */
	
	@ApiOperation(httpMethod = "DELETE", value = "Delete a Person Detail", response = Person.class)
	@ApiResponses(value = { 
			@ApiResponse(code = 204, message = "No Content"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@DeleteMapping("/Person/{id}")
	
	public ResponseEntity<?> deleteById(@PathVariable("id") int id) {
		Person personData = personService.findById(id);
		if (personData == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		personService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
