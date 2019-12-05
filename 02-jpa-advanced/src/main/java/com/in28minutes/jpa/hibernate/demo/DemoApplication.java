package com.in28minutes.jpa.hibernate.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.in28minutes.jpa.hibernate.demo.entity.Course;
import com.in28minutes.jpa.hibernate.demo.repository.CourseRepository;
import com.in28minutes.jpa.hibernate.demo.repository.EmployeeRepository;
import com.in28minutes.jpa.hibernate.demo.repository.StudentRepository;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {

		Course course = courseRepository.findById(10001L);
		logger.info("course 1001 -->{}", course);
		courseRepository.save(new Course("microservices in 200 steps"));

		// courseRepository.deleteById(10001L);

		// studentRepository.saveStudentWithPassport();
		
		
		//kindly comment thid method while executing JPQLTest.java and NativeQueriesTest.java

		//courseRepository.playWithEntityManager();
		
		
		
		// courseRepository.addHardcodedReviewsForCourse();
		// List<Review> reviews = new ArrayList<>();

		// reviews.add(new Review("5", "Great Hands-on Stuff."));
		// reviews.add(new Review("5", "Hatsoff."));
 
		// courseRepository.addReviewsForCourse(10003L, reviews );
		// studentRepository.insertHardcodedStudentAndCourse();
		// studentRepository.insertStudentAndCourse(new Student("Jack"),
		// new Course("Microservices in 100 Steps"));

		// Jack FullTimeEmployee salary - 10000$
		// Jill PartTimeEmployee - 50$ per hour
		/*
		 * employeeRepository.insert(new PartTimeEmployee("Jill", new
		 * BigDecimal("50"))); employeeRepository.insert(new FullTimeEmployee("Jack",
		 * new BigDecimal("10000")));
		 * 
		 * logger.info("Full Time Employees -> {}",
		 * employeeRepository.retrieveAllFullTimeEmployees());
		 * 
		 * logger.info("Part Time Employees -> {}",
		 * employeeRepository.retrieveAllPartTimeEmployees());
		 */
	}
}
