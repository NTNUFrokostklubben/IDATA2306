package no.ntnu.learniverseconnect.model.repos;

import java.util.Date;
import java.util.List;
import no.ntnu.learniverseconnect.model.dto.CourseWithMinPriceDto;
import no.ntnu.learniverseconnect.model.entities.Course;
import no.ntnu.learniverseconnect.model.entities.OfferableCourses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferableCoursesRepo extends JpaRepository<OfferableCourses, Integer> {
  List<OfferableCourses> getAllByCourse_Id(long cid);

  List<OfferableCourses> getAllByProvider_Id(long pid);

  void deleteAllByCourse_Id(long courseId);

  OfferableCourses getOfferableCoursesById(long id);

  @Query("""
        SELECT NEW no.ntnu.learniverseconnect.model.dto.CourseWithMinPriceDto(
            o.course,
            MIN(o.price * (1 - o.discount)),  
            MIN(o.date)                       
        )
        FROM OfferableCourses o
        WHERE o.visibility = true
        AND (:title IS NULL OR o.course.title LIKE %:title%)
        AND o.date >= CURRENT_DATE            
        GROUP BY o.course
        """)
  List<CourseWithMinPriceDto> findVisibleCoursesWithMinPriceAndClosestDate(
      @Param("title") String title
  );

  @Query("""
        SELECT NEW no.ntnu.learniverseconnect.model.dto.CourseWithMinPriceDto(
            o.course,
            MIN(o.price * (1 - o.discount)),  
            MIN(o.date)                       
        )
        FROM OfferableCourses o
        WHERE o.visibility = true
        AND (:title IS NULL OR o.course.title LIKE %:title%)
        AND o.date between :dateFrom AND :dateTo      
        GROUP BY o.course
        """)
  List<CourseWithMinPriceDto> findVisibleCoursesWithMinPriceWhereClosestDateBetween(
      @Param("title") String title,
      @Param("dateFrom")Date dateFrom,
      @Param("dateTo")Date dateTo
      );

  @Query("""
        SELECT NEW no.ntnu.learniverseconnect.model.dto.CourseWithMinPriceDto(
            o.course,
            MIN(o.price * (1 - o.discount)),  
            MIN(o.date)                       
        )
        FROM OfferableCourses o
        WHERE o.visibility = true
        AND (:title IS NULL OR o.course.title LIKE %:title%)
        AND o.date between :dateFrom AND :dateTo
        AND (:categories IS NULL OR o.course.category IN :categories)
        AND (:diffLevels IS NULL OR o.course.diffLevel IN :diffLevels)
        AND (:creditsMin IS NULL OR o.course.credits >= :creditsMin)
        AND (:creditsMax IS NULL OR o.course.credits <= :creditsMax)
        GROUP BY o.course
""")
  List<CourseWithMinPriceDto> findVisibleCoursesWithMinPriceWhereClosestDateBetweenContainingCategoryContainingDifficultyLevelWithinCreditsWithinPriceRange(
      @Param("title") String title,
      @Param("dateFrom") Date dateFrom,
      @Param("dateTo") Date dateTo,
      @Param("categories") List<String> categories,
      @Param("diffLevels") List<Integer> diffLevels,
      @Param("creditsMin") Float creditsMin,
      @Param("creditsMax") Float creditsMax
  );
}
