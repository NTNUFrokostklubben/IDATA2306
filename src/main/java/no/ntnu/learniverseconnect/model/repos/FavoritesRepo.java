package no.ntnu.learniverseconnect.model.repos;

import java.util.List;
import no.ntnu.learniverseconnect.model.entities.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoritesRepo extends JpaRepository<Favorite, Integer> {
  List<Favorite> getAllByUser_Id(Long userId);

  List<Favorite> getByCourse_Id(long courseId);

  List<Favorite> findAllByUser_Name(String userName);

  List<Favorite> findFavoritesByUser_Id(Long userId);

  boolean existsByCourse_IdAndUser_Id(long courseId, Long userId);

  void deleteFavoriteByCourse_IdAndUser_Id(long courseId, Long userId);
}
