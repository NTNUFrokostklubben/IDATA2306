package no.ntnu.learniverseconnect.controllers;

import java.util.List;
import no.ntnu.learniverseconnect.model.entities.User;
import no.ntnu.learniverseconnect.model.entities.UserCourse;
import no.ntnu.learniverseconnect.model.repos.CourseRepo;
import no.ntnu.learniverseconnect.model.repos.UserCoursesRepo;
import no.ntnu.learniverseconnect.model.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Represents the rest controller for the User_course table in the database.
 */

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
public class UserCoursesController {

  UserCoursesRepo userCoursesRepo;
  UserRepo userRepo;
  CourseRepo courseRepo;

  @Autowired
  public UserCoursesController(UserCoursesRepo userCoursesRepo1, UserRepo userRepo1, CourseRepo courseRepo1){
    this.userCoursesRepo = userCoursesRepo1;
    this.courseRepo = courseRepo1;
    this.userRepo = userRepo1;
  }

  /**
   * Get all courses associated with a user.
   * @param id the user to get by
   * @return the list of courses a user is associated with.
   */
  @GetMapping("/user-courses/{id}")
  public ResponseEntity<List<UserCourse>> getAll(@PathVariable long id){
    List<UserCourse> userCourseList = userCoursesRepo.getAllByUser_Id(id);
    int status = 404;
    if(!userCourseList.isEmpty()){
     status = 200;
    }
    return ResponseEntity.status(status).body(userCourseList);
  }

  /**
   *  Get the average rating of a given course based on the course id.
   * @param cid the course id for the course to find the average on.
   * @return the average.
   */
  @GetMapping("/getAvg/{cid}")
  public ResponseEntity<Float> getAverageByCourse(@PathVariable long cid){
     List<UserCourse> courses  = userCoursesRepo.getAllByCourse_Id(cid);
     float average = 0;
     int status = 0;
     for(UserCourse course : courses){
       average += course.getRating();
     }
     if(average > -1 && average < 6){
       status = 200;
     }else {
       status  = 204;
     }
     return ResponseEntity.status(status).body(average/courses.size());
  }
}
