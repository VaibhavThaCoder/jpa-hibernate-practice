package com.in28minutes.database.databasedemo.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.in28minutes.database.databasedemo.entity.Person;

@Repository
public class PersonJbdcDao {

	// similar to repository like jpa

	@Autowired
	JdbcTemplate jdbcTemplate; // as we are using spring so no databse connectivity is used instead we use jdbc
								// template to talk to our database

	// creating custom row mapper by implements the RowMapper interface
	class PersonRowMapper implements RowMapper<Person> {  
		@Override
		public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
			Person person = new Person();
			person.setId(rs.getInt("id"));
			person.setName(rs.getString("name"));
			person.setLocation(rs.getString("location"));
			person.setBirthDate(rs.getTimestamp("birth_date"));
			return person;
		}

	}

	public List<Person> findAll() {
		return jdbcTemplate.query("select * from person", new PersonRowMapper());
	}

//	public List<Person> findAll() {
//		// mapping all the fields in the person class to fields in the database for this
//		// there is an inbuilt row mapper associated with it i.e is
//		// BeanPropertyRowMapper()
//		return jdbcTemplate.query("select * from person", new BeanPropertyRowMapper<Person>(Person.class));
//	}

	public Person findById(int id) {

		// here queryForObject() when we are querying the results for single person
		// ? will be replaced by array of objects "Object[] { id }"
		// finally the BeanPropertyRowMapper() for mapping filelds of a person to row in
		// database
		return jdbcTemplate.queryForObject("select * from person where id=?", new Object[] { id },
				new BeanPropertyRowMapper<Person>(Person.class));
	}

	public int deleteById(int id) {

		// update method on jdbcTemplate is used when any operaation on database updates
		// the tables and its contents so we use upf=date() on jdbcTemplate while
		// insertion and deletion operation
		//no need of mapper becuase we are deleting the row here  
		// returns how many rows were affected by update()  
		return jdbcTemplate.update("delete from person where id=?", new Object[] { id });
	}

	public int insert(Person person) {
		return jdbcTemplate.update("insert into person (id, name, location, birth_date) " + "values(?,  ?, ?, ?)",
				new Object[] { person.getId(), person.getName(), person.getLocation(),
						new Timestamp(person.getBirthDate().getTime()) });
	}

	public int update(Person person) {
		return jdbcTemplate.update("update person " + " set name = ?, location = ?, birth_date = ? " + " where id = ?",
				new Object[] { person.getName(), person.getLocation(), new Timestamp(person.getBirthDate().getTime()),
						person.getId() });
	}

}