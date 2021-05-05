package com.demo.in.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.in.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, String> {

	Person findById(int id);

	void deleteById(int id);

}
