package no.ntnu.learniverseconnect.model.repos;

import java.util.List;
import no.ntnu.learniverseconnect.model.entities.User;
import no.ntnu.learniverseconnect.model.entities.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The user courses repository.
 */
@Repository
public interface UserCoursesRepo extends JpaRepository<UserCourse, Integer> {
  List<UserCourse> getAllByUser_Id(Long userId);

  List<UserCourse> getAllByCourse_Id(long courseId);

  void deleteAllByCourse_Id(long courseId);

  User getById(Long id);
}
