package no.ntnu.learniverseconnect.model.repos;

import no.ntnu.learniverseconnect.model.entities.Ratings;
import org.springframework.data.repository.CrudRepository;

public interface RatingsRepo extends CrudRepository<Ratings, Integer> {
}
