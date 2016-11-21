package Logic;

import View.LoginView;
import View.StartView;
import View.StudentView;
import sdk.Connection.ResponseCallback;
import sdk.Service.CourseService;
import sdk.Service.LectureService;
import sdk.Service.ReviewService;
import sdk.shared.CourseDTO;
import sdk.shared.LectureDTO;
import sdk.shared.ReviewDTO;

import java.util.ArrayList;

/**
 * Created by andershoumann on 17/11/2016.
 */
public class Controller {

    public  void showMainMenu(){

        StartView startView = new StartView();
        startView.mainMenu();

    }

    public  void presentView (int currentUser) {
        StudentView studentView = new StudentView();
        studentView.presentView(currentUser);
    }


    public void showlogin (int currentUser) {

        LoginView loginView = new LoginView();

        loginView.presentLogin(currentUser);

    }

    public void showCourses() {

        CourseService courseService = new CourseService();

        courseService.getAllCourses(new ResponseCallback<ArrayList<CourseDTO>>() {
            public void success(ArrayList<CourseDTO> data) {
                for (CourseDTO c: data) {
                    System.out.println(c.getDisplaytext() + "   " + c.getCode());
                    System.out.println();

                }
            }

            public void error(int status) {

            }
        });

    }


    public void createReview (ReviewDTO reviewDTO) {

        ReviewService reviewService = new ReviewService();

        reviewService.create(reviewDTO, new ResponseCallback<Boolean>() {
            public void success(Boolean data) {
                System.out.println("Create review gik i Succes i Responsecallback");
            }

            public void error(int status) {
                System.out.println("fik fejl: " + status );
            }
        } );
    }



    public  void showLectures () {
        LectureService lectureService = new LectureService();
        lectureService.getAllLectures(new ResponseCallback<ArrayList<LectureDTO>>() {

            public void success(ArrayList<LectureDTO> data) {
                for (LectureDTO l:data) {
                    System.out.println(l.getId()+ ": " + l.getDescription()+ "  " +l.getStartDate() +"  " + l.getType());
                    System.out.println();
                }
            }

            public void error(int status) {

            }
        });
    }

    public  void showReviews (int lectureId) {
        ReviewService reviewService = new ReviewService();

        reviewService.getAllReviews(lectureId, new ResponseCallback<ArrayList<ReviewDTO>>() {
            public void success(ArrayList<ReviewDTO> reviews) {
                for (ReviewDTO r: reviews){
                   System.out.println("Id: "+ r.getId()+ " Kommentar:" + r.getComment()+ "  " + "Rating: " + r.getRating() );
                }
            }

            public void error(int status) {

            }
        });
    }

    public void showUserReviews (int currentUser) {
        ReviewService reviewService = new ReviewService();
        reviewService.getUserReviews(currentUser, new ResponseCallback<ArrayList<ReviewDTO>>() {
            public void success(ArrayList<ReviewDTO> data) {
                for (ReviewDTO r : data) {
                    System.out.println("Id: " + r.getId() + " Kommentar:" + r.getComment() + "  " + "Rating: " + r.getRating());
                }
            }
            public void error(int status) {
                System.out.println("Fejlede kaldet til server om brugers reviews fik fejl koden: " + status);
            }
        });

    }
}
