package com.in28minutes.jpa.hibernate.demo.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.in28minutes.jpa.hibernate.demo.entity.Course;
import com.in28minutes.jpa.hibernate.demo.entity.Review;
import com.in28minutes.jpa.hibernate.demo.entity.ReviewRating;

@Repository
@Transactional
public class CourseRepository {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private EntityManager em;

	public Course findById(Long id) {
		Course course = em.find(Course.class, id);
		logger.info("Course -> {}", course);
		return course;
	}

	public Course save(Course course) {

		if (course.getId() == null) {
			em.persist(course); // for insertion
		} else {
			em.merge(course); // for update
		}

		return course;
	}

	public void deleteById(Long id) {
		Course course = findById(id);
		em.remove(course);
	}

	public void playWithEntityManager() {
		Course course1 = new Course("Web Services in 100 Steps");
		em.persist(course1);
//		em.flush(); // sends the changes to database to save
//		// em.detach(course1); //the changes to course 1 are not tracked by entity
//		// manager
//
//		// em.flush();
//
//		em.clear(); // it clears all the unsaved/not flushed changes from the database
//
	Course course2 = findById(10001L);
//
//		// as we are in @Transactional annotation so even though we have not called the
//		// merge() to update the course but still update happens this because Entity
//		// Manager keeps the track of all the operations and @Transactional saves all
//		// the operation related to entity manager by default therefore no need of
//		// em.merge(course)
//
		course2.setName("JPA in 50 Steps - Updated");
//		em.persist(course2);
//		em.flush();
//		// em.detach(course2); //the changes to course 2 are not tracked by entity
//		// manager
//		course1.setName("Web Services in 100 Steps - updated");
//		course2.setName("angular js in 100 Steps - updated"); // these changes are not reflected
//
//		em.refresh(course1); // course1 updated contents will not be saved as it refreshed so evnthough
//								// em.flush() is invoked the current course1 changes whicj=h are refresh wont be
//								// saved

//		Course course1 = new Course("Web Services in 100 Steps");
//		course1.setName(null); // this will give you error because nullable=false is set in @column()
//								// annotation for the field name in course
//		em.persist(course1);
//		em.flush();

	}

	public void addHardcodedReviewsForCourse() {
		// get the course 10003
		Course course = findById(10003L);
		logger.info("course.getReviews() -> {}", course.getReviews());

		// add 2 reviews to it
		Review review1 = new Review(ReviewRating.FIVE, "Great Hands-on Stuff.");
		Review review2 = new Review(ReviewRating.FIVE, "Hatsoff.");

		// setting the relationship
		course.addReview(review1);
		review1.setCourse(course);

		course.addReview(review2);
		review2.setCourse(course);

		// save it to the database
		
		//saving the owning entity only i.e review 
		em.persist(review1);
		em.persist(review2);
	}

	public void addReviewsForCourse(Long courseId, List<Review> reviews) {
		Course course = findById(courseId);
		logger.info("course.getReviews() -> {}", course.getReviews());
		for (Review review : reviews) {
			// setting the relationship
			course.addReview(review);
			review.setCourse(course);
			em.persist(review);
		}
	}
}