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

    public void showCourses(int currentUser) {

       StudentView studentView = new StudentView();
        studentView.courseView(currentUser);

    }


    public void createReview (int currentUser, int lectureId ) {

     StudentView studentView = new StudentView();
        studentView.reviewCreateView(currentUser,lectureId);
    }

    public void reviewMenu (int currentUser, int lectureid){
        StudentView studentView = new StudentView();
        studentView.reviewMenu(currentUser, lectureid);
    }


    public  void showLectures (int currentUser, String binto) {
    StudentView studentView = new StudentView();
        studentView.lectureView(currentUser, binto);
    }

    public  void showReviews (int currentuser) {
        StudentView studentView = new StudentView();
        studentView.showMyReviews(currentuser);

    }

    public void showUserReviews (int currentUser) {
      StudentView studentView = new StudentView();
        studentView.showMyReviews(currentUser);
    }

}
