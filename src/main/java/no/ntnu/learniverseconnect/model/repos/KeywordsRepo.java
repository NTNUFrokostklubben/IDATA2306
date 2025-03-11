package no.ntnu.learniverseconnect.model.repos;

import no.ntnu.learniverseconnect.model.entities.Keywords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeywordsRepo extends JpaRepository<Keywords, Integer> {

  Keywords getKeywordsById(long id);
}
