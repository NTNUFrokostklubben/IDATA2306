package no.ntnu.learniverseconnect.model.repos;

import java.util.List;
import no.ntnu.learniverseconnect.model.entities.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCoursesRepo extends JpaRepository<UserCourse, Integer> {
  List<UserCourse> getAllByUser_Id(Long userId);

  List<UserCourse> getAllByCourse_Id(long courseId);

  @Query("SELECT AVG(uc.rating) FROM UserCourse uc WHERE uc.course.id = :courseId")
  Float getAverageRatingByCourseId(@Param("courseId") Long courseId);

  void deleteAllByCourse_Id(long courseId);

  int countByCourseId(long id);
}
