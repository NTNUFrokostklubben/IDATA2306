package no.ntnu.learniverseconnect.model.repos;

import java.util.List;
import no.ntnu.learniverseconnect.model.entities.OfferableCourses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferableCoursesRepo extends JpaRepository<OfferableCourses, Integer> {
  List<OfferableCourses> getAllByCourse_Id(long cid);

  List<OfferableCourses> getAllByProvider_Id(long pid);
}
