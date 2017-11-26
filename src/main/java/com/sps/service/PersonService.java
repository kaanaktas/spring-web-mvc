package com.sps.service;

import java.util.List;

import com.sps.entity.Person;

public interface PersonService {
	List<Person> findAll();

	Person findById(Long id);

	void create(Person person);

	void update(Person person);

	void delete(Long id);
}
