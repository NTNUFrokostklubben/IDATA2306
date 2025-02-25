package no.ntnu.learniverseconnect.model.repos;

import no.ntnu.learniverseconnect.model.entities.Ratings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingsRepo extends JpaRepository<Ratings, Integer> {
}
