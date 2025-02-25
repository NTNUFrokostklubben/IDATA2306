package no.ntnu.learniverseconnect.model.repos;

import no.ntnu.learniverseconnect.model.entities.Favorites;
import org.springframework.data.repository.CrudRepository;

public interface FavoritesRepo extends CrudRepository<Favorites, Integer> {
}
