package no.ntnu.learniverseconnect.model.repos;

import no.ntnu.learniverseconnect.model.entities.CourseProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The course provider repository.
 */
@Repository
public interface CourseProviderRepo extends JpaRepository<CourseProvider, Integer> {
  CourseProvider getCourseProviderById(long id);
}