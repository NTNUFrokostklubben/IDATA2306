package no.ntnu.learniverseconnect.model.repos;

import no.ntnu.learniverseconnect.model.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Integer> {
}
