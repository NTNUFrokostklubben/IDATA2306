package no.ntnu.learniverseconnect.model.repos;

import java.util.List;
import no.ntnu.learniverseconnect.model.entities.Course;
import no.ntnu.learniverseconnect.model.entities.CourseProvider;
import no.ntnu.learniverseconnect.model.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The transaction repository.
 */
@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Integer> {

  Transaction findById(long id);

  List<Transaction> findByUser_Id(Long userId);

  List<Transaction> findAllByUser_Id(Long userId);

  List<Transaction> findAllByOfferableCourses_Course(Course offerableCoursesCourse);

  List<Transaction> findAllByUser_IdAndOfferableCourses_Course(Long userId,
                                                               Course offerableCoursesCourse);

  List<Transaction> findAllByOfferableCourses_Provider(CourseProvider provider);
}
