package no.ntnu.learniverseconnect.model.repos;

import java.util.List;
import no.ntnu.learniverseconnect.model.entities.Keywords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The keywords repository.
 */
@Repository
public interface KeywordsRepo extends JpaRepository<Keywords, Integer> {

  Keywords getKeywordsById(long id);

  List<Keywords> getAllByCourse_Id(long courseId);

  void deleteAllByCourse_Id(long courseId);

  boolean existsByCourse_Id(long courseId);
}
