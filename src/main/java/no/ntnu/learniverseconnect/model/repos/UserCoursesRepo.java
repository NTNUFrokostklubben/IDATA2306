package no.ntnu.learniverseconnect.model.repos;

import java.util.List;
import no.ntnu.learniverseconnect.model.entities.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCoursesRepo extends JpaRepository<UserCourse, Integer> {
  List<UserCourse> getAllByUser_Id(Long userId);
}
