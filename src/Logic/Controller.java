package Logic;

import View.LoginView;
import View.StartView;
import View.StudentView;
import View.TeacherView;
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

    public void reviewCreate (int currentUser, int lectureId){
        StudentView studentView = new StudentView();
        studentView.reviewCreateView(currentUser,lectureId);
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

    public void showTeacherMenu(int currentUser){
        TeacherView teacherView = new TeacherView();
        teacherView.presentView(currentUser);
    }

    public void showDeleteTeacher(int currentUser){
        TeacherView teacherView = new TeacherView();
        teacherView.deleteComment(currentUser);
    }

    public void showAverageOnCourse (int currentUser){
        TeacherView teacherView = new TeacherView();
        teacherView.averageOncourse(currentUser);
    }

    public void showCourseTeacher (int currentUser){
        TeacherView teacherView = new TeacherView();
        teacherView.getcourses(currentUser);
    }

    public void showlectureTeacher (int currentUser, String binto) {
        TeacherView teacherView = new TeacherView();
        teacherView.getLectures(currentUser, binto);
    }
    public void showParticipents (int currentUser) {
        TeacherView teacherView = new TeacherView();
        teacherView.participents(currentUser);
    }
    public void showPresenViewTeacher (int currentUser){
        TeacherView teacherView = new TeacherView();
        teacherView.presentView(currentUser);
    }
}
