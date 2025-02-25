package no.ntnu.learniverseconnect.model.repos;

import no.ntnu.learniverseconnect.model.entities.OfferableCourses;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OfferableCoursesRepo extends JpaRepository<OfferableCourses, Integer> {
}
