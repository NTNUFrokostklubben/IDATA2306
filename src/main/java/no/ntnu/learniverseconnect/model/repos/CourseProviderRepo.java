package no.ntnu.learniverseconnect.model.repos;

import no.ntnu.learniverseconnect.model.entities.CourseProvider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseProviderRepo extends JpaRepository<CourseProvider, Integer> {
}
