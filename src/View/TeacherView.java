package View;

import Logic.Controller;
import sdk.Connection.ResponseCallback;
import sdk.Service.CourseService;
import sdk.Service.LectureService;
import sdk.Service.ReviewService;
import sdk.Service.TeacherService;
import sdk.shared.CourseDTO;
import sdk.shared.LectureDTO;
import sdk.shared.ReviewDTO;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by andershoumann on 26/11/2016.
 */
public class TeacherView {
    private Controller controller = new Controller();
    private ReviewService reviewService = new ReviewService();
    private TeacherService teacherService = new TeacherService();


    public TeacherView() {
        this.controller = new Controller();
        this.reviewService = new ReviewService();
        this.teacherService = new TeacherService();


    }

    public void presentView(int currentuser) {
        Controller controller = new Controller();

        System.out.println("Som underviser kan du slette kommentarer til alle reviews");
        System.out.println("Tast 1 for at slette et review.");
        System.out.println("Tast 2 for se gennemsnittet for et af dine kurser.");
        System.out.println("Tast 3 for at logge ud.");

        Scanner inout = new Scanner(System.in);
        int choise = inout.nextInt();

        switch (choise) {
            case 0:
                System.exit(0);
            case 1:
                controller.showDeleteTeacher(currentuser);
                break;
            case 2:
                controller.showAverageOnCourse(currentuser);
            case 3:
                controller.showMainMenu();
                break;
            default:
                System.out.println("Det var ikke en mulighed");
                controller.showTeacherMenu(currentuser);
                break;
        }
    }

    /**
     * Denne metode henter alle kurser for en teacher og alle lectures til et kursus.
     * Hvorefter den henter alle reviews for et lecture, og du tilsidst kan taste id'et for det review der skal slettes.
     *
     * @param currentUser
     */
    public void deleteComment(int currentUser) {

        CourseService courseService = new CourseService();

        courseService.getAllCourses(currentUser, new ResponseCallback<ArrayList<CourseDTO>>() {
            public void success(ArrayList<CourseDTO> data) {
                for (CourseDTO c : data) {
                    System.out.println(c.getDisplaytext() + "   " + c.getCode());
                    System.out.println();
                }
            }

            public void error(int status) {
                System.out.println("Fik fejlen: " + status);
            }
        });
        System.out.println("-------------------vælg fag-----------------------");
        System.out.println("Indtast koden for et fag eks. BINTO1035U_XB_E16");

        Scanner input = new Scanner(System.in);
        String binto = input.nextLine();

        System.out.println("-------------------------------------------------------");
        System.out.println("Der er tilknyttet følgende undervisningsgange til faget");

        //henter alle lectures
        LectureService lectureService = new LectureService();
        lectureService.getAllLectures(binto, new ResponseCallback<ArrayList<LectureDTO>>() {
            public void success(ArrayList<LectureDTO> data) {
                for (LectureDTO l : data) {
                    System.out.println("Id: " + l.getId() + " " + l.getDescription() + " " + l.getStartDate() + " " + l.getType());
                }
            }

            public void error(int status) {

            }
        });

        System.out.println("indtast id for den undervisningsgang du vil slette et review");
        System.out.println("indtast 0 for at gå til hovedmenu");
        System.out.println("");

        Scanner input1 = new Scanner(System.in);
        int choise = input1.nextInt();

        if (choise == 0) {
            controller.showTeacherMenu(currentUser);
        }
        if (choise != 0) {

            /**
             * Her hentes alle reviews
             */
            reviewService.getAllReviews(choise, new ResponseCallback<ArrayList<ReviewDTO>>() {
                public void success(ArrayList<ReviewDTO> data) {
                    for (ReviewDTO r : data) {
                        System.out.println("Id: " + r.getId() + " Rating: " + r.getRating() + " kommentar: " + r.getComment());
                    }
                }

                public void error(int status) {

                }
            });

        }

        Scanner input2 = new Scanner(System.in);

        int reviewid = input2.nextInt();

        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(reviewid);

        /**
         * her kaldes på selve slet kaldet til serveren for en teacher
         */
        TeacherService teacherService = new TeacherService();
        teacherService.deleteReviewTeacher(reviewDTO, new ResponseCallback<Boolean>() {
            public void success(Boolean data) {
                System.out.println("Reviewet er blevet slettet.");
            }

            public void error(int status) {
                System.out.println("Fik fejlen ved slet et deleteReviewTeacher: " + status);

            }
        });

        controller.showTeacherMenu(currentUser);


    }

    public void averageOncourse(int currentUser) {
        CourseService courseService = new CourseService();

        courseService.getAllCourses(currentUser, new ResponseCallback<ArrayList<CourseDTO>>() {
            public void success(ArrayList<CourseDTO> data) {
                for (CourseDTO c : data) {
                    System.out.println(c.getDisplaytext() + "   " + c.getCode());
                }
                System.out.println();
            }

            public void error(int status) {

            }
        });


        System.out.println("-------------------vælg fag-----------------------");
        System.out.println("Indtast koden for et fag eks. BINTO1035U_XB_E16");

        Scanner input = new Scanner(System.in);
        String binto = input.nextLine();


        teacherService.averageOnCourse(binto, new ResponseCallback<Double>() {
            public void success(Double data) {
                System.out.println("Gennemsnittet for kurset: "+data);
            }

            public void error(int status) {
                System.out.println("Fik fejlen koden: " + status + ", ved at se gennemsnittet for ratings for et kursus.");

            }
        });

        controller.showTeacherMenu(currentUser);
    }
}

