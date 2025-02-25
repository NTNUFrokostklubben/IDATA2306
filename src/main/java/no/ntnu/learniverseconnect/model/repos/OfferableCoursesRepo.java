package no.ntnu.learniverseconnect.model.repos;

import no.ntnu.learniverseconnect.model.entities.OfferableCourses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferableCoursesRepo extends JpaRepository<OfferableCourses, Integer> {
}
