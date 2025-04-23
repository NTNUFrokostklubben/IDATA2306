package no.ntnu.learniverseconnect.model.repos;

import java.util.List;
import no.ntnu.learniverseconnect.model.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepo extends JpaRepository<Course, Integer> {

  Course getById(long id);

  Course getCoursesById(long id);

  List<Course> getCoursesByTitleContaining(String title);
  List<Course> getCoursesByDescriptionContaining(String description);
  List<Course> getCoursesByTitleContainingOrDescriptionContaining(String title, String description);
  //  Within should be a to-from limit
  List<Course> findByTitleContainingOrDescriptionContainingAndCategoryInAndDiffLevelInAndCreditsBetween(
      String title,
      String description,
      List<String> categories,
      List<Integer> diffLevels,
      Integer creditsMin,
      Integer creditsMax
  );

}
