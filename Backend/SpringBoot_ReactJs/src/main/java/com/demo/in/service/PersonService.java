package com.demo.in.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.in.model.Person;

@Service
@Transactional
public interface PersonService {

	public List<Person> findAll();

	public Person save(Person Person);

	public Person findById(int id);

	public void deleteById(int id);

	public Person update(Person Person);

}
