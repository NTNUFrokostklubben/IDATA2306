package no.ntnu.learniverseconnect.model.repos;

import no.ntnu.learniverseconnect.model.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The course repository.
 */
@Repository
public interface CourseRepo extends JpaRepository<Course, Integer> {

  Course getById(long id);

  Course getCoursesById(long id);
}
