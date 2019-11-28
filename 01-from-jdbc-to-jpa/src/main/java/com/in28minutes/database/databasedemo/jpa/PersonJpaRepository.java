package com.in28minutes.database.databasedemo.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.in28minutes.database.databasedemo.entity.Person;

@Repository
@Transactional // transaction managment ...all opertaion either successfull or all operations
				// will fail together
public class PersonJpaRepository {

	// connect to the database
	@PersistenceContext
	EntityManager entityManager;

	public List<Person> findAll() {
		TypedQuery<Person> namedQuery = entityManager.createNamedQuery("find_all_persons", Person.class);
		return namedQuery.getResultList();
	}

	public Person findById(int id) {
		return entityManager.find(Person.class, id);// JPA
	}

	public Person update(Person person) {
		// be it insertion or updation we will use merge method to do these operation,
		// merge() will check whether id has been generated for the row which is to be
		// updated then it will try and update the row, if no id is set merge() will
		// insert it automatically
		//so there is no any difference between update and insert here
		return entityManager.merge(person);
	}

	public Person insert(Person person) {
		return entityManager.merge(person);
	}

	public void deleteById(int id) {
		Person person = findById(id); //finding the perosn with id
		entityManager.remove(person);  //remove() to delete
	}

}
