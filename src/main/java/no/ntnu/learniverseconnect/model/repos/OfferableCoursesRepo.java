package no.ntnu.learniverseconnect.model.repos;

import java.util.Date;
import java.util.List;
import no.ntnu.learniverseconnect.model.dto.CourseWithMinPriceAndRatingDto;
import no.ntnu.learniverseconnect.model.entities.OfferableCourses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * The offerable courses repository.
 */
@Repository
public interface OfferableCoursesRepo extends JpaRepository<OfferableCourses, Integer>,
    JpaSpecificationExecutor<OfferableCourses> {
  List<OfferableCourses> getAllByCourse_Id(long cid);

  List<OfferableCourses> getAllByProvider_Id(long pid);

  void deleteAllByCourse_Id(long courseId);

  OfferableCourses getOfferableCoursesById(long id);




}
