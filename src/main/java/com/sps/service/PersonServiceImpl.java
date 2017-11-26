package com.sps.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sps.entity.Person;
import com.sps.model.PersonDao;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {
	private PersonDao personDao;

	@Autowired
	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}

	@Override
	public List<Person> findAll() {
		return personDao.findAll();
	}

	@Override
	public Person findById(Long id) {
		return personDao.findById(id);
	}

	@Override
	public void create(Person person) {
		personDao.create(person);
	}

	@Override
	public void update(Person person) {
		personDao.update(person);
	}

	@Override
	public void delete(Long id) {
		personDao.delete(id);
	}
}
