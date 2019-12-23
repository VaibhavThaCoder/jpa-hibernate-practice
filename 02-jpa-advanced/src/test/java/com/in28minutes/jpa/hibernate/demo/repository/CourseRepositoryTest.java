package com.in28minutes.jpa.hibernate.demo.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.Subgraph;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.in28minutes.jpa.hibernate.demo.DemoApplication;
import com.in28minutes.jpa.hibernate.demo.entity.Course;
import com.in28minutes.jpa.hibernate.demo.entity.Review;
import com.in28minutes.jpa.hibernate.demo.entity.Student;

@RunWith(SpringRunner.class) // for launching a spring boot context while running the test
@SpringBootTest(classes = DemoApplication.class) // DemoApplication.class is the context we want to load where there is
													// information of course
public class CourseRepositoryTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CourseRepository repository;

	@Autowired
	EntityManager em;

	@Test
	public void findById_basic() {
		Course course = repository.findById(10001L);
		assertEquals("JPA in 50 Steps", course.getName());
	}

	@Test
	public void findById_firstLevelCacheDemo() {

		Course course = repository.findById(10001L);
		logger.info("First Course Retrieved {}", course);

		Course course1 = repository.findById(10001L);
		logger.info("First Course Retrieved again {}", course1);

		assertEquals("JPA in 50 Steps", course.getName());

		assertEquals("JPA in 50 Steps", course1.getName());
	}

	 // as we are deleting the records in test but test should not modify the
					// production env database so we use @DirtiesContext to keep database as it
					// is,i.e resetting the data
	@Test
	@DirtiesContext
	public void deleteById_basic() {
		repository.deleteById(10002L);
		// to check whether a 10002 id is deleted or not
		// if it is deleted and when we retrieve it again by findById() method it will
		// be null
		// thats how we test deleteById()
		assertNull(repository.findById(10002L));
	}

	@Test
	@DirtiesContext
	public void save_basic() {
		// get a course
		Course course = repository.findById(10001L);
		assertEquals("JPA in 50 Steps", course.getName());

		// update details
		course.setName("JPA in 50 Steps - Updated");
		repository.save(course);

		// check the updated value
		Course course1 = repository.findById(10001L);
		assertEquals("JPA in 50 Steps - Updated", course1.getName());
	}

	@Test
	@DirtiesContext
	public void playWithEntityManager() {
		repository.playWithEntityManager();
	}
//
//	@Test
//	@Transactional
//	public void retrieveReviewsForCourse() {
//		Course course = repository.findById(10001L);
//		logger.info("{}", course.getReviews());
//	}
//
	@Test
	@Transactional
	public void retrieveCourseForReview() {
		Review review = em.find(Review.class, 50001L);
		logger.info("{}", review.getCourse());
	}
//
//	@Test
//	@Transactional
//	@DirtiesContext
//	public void performance() {
//		// for (int i = 0; i < 20; i++)
//		// em.persist(new Course("Something" + i));
//		// em.flush();
//
//		// EntityGraph graph = em.getEntityGraph("graph.CourseAndStudents");
//
//		EntityGraph<Course> graph = em.createEntityGraph(Course.class);
//		Subgraph<List<Student>> bookSubGraph = graph.addSubgraph("students");
//
//		List<Course> courses = em.createQuery("Select c from Course c", Course.class)
//				.setHint("javax.persistence.loadgraph", graph).getResultList();
//		for (Course course : courses) {
//			System.out.println(course + " " + course.getStudents());
//		}
//	}
//
//	@Test
//	@Transactional
//	@DirtiesContext
//	public void performance_without_hint() {
//		List<Course> courses = em.createQuery("Select c from Course c", Course.class)
//				// .setHint("javax.persistence.loadgraph", graph)
//				.getResultList();
//		for (Course course : courses) {
//			System.out.println(course + " " + course.getStudents());
//		}
//	}

}
