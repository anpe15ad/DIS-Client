package View;

import Logic.Controller;
import sdk.Connection.ResponseCallback;
import sdk.Service.AccessService;
import sdk.Service.CourseService;
import sdk.Service.LectureService;
import sdk.Service.ReviewService;
import sdk.shared.CourseDTO;
import sdk.shared.LectureDTO;
import sdk.shared.ReviewDTO;
import sdk.shared.UserDTO;


import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by andershoumann on 19/11/2016.
 */
public class StudentView {

    public void presentView (int currentUser) {
        Controller controller = new Controller();

        while (true) {
            System.out.println("Velkommen til studerende");
            System.out.println("vælg et fag");
            System.out.println("vælg 0 for at slukke programmet");
            System.out.println("vælg 1 for at se dine fag");
            System.out.println("vælg 2 for at slette et af dine reviews");
            System.out.println("vælg 3 for at logge ud");

            System.out.println();

            Scanner input = new Scanner(System.in);
            int valg = input.nextInt();

            switch (valg){
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    controller.showCourses(currentUser);
                    break;
                case 2:
                    controller.showUserReviews(currentUser);
                    break;
                case 3:
                    StartView startView = new StartView();
                    startView.mainMenu();
                    break;
                default:
                    System.out.println("Du indtastede ikke en gyldig menu! ");
                    presentView(currentUser);
                    break;
            }
        }
    }

    public void courseView(int currentUser) {
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
        System.out.println("Indtast koden for et fag eks. BINTO1051U_LA_E16");
        Scanner input = new Scanner(System.in);
        String binto = input.nextLine();

        Controller  controller = new Controller();
        controller.showLectures(currentUser,binto);

    }

    public void  lectureView(int currentUser, String binto) {

        System.out.println("-------------------------------------------------------");
        System.out.println("Der er tilknyttet følgende undervisningsgange til faget");
        //henter alle lectures
        LectureService lectureService = new LectureService();
        lectureService.getAllLectures(binto, new ResponseCallback<ArrayList<LectureDTO>>() {
            public void success(ArrayList<LectureDTO> data) {
                for (LectureDTO l:data) {
                    System.out.println(l.getId()+ ": " + l.getDescription()+ "  " +l.getStartDate() +"  " + l.getType());
                    System.out.println();
                }
            }

            public void error(int status) {

            }
        });

        System.out.println("indtast id for den undervisningsgang du vil evaluere");
        System.out.println("");
        System.out.println("indtast 2 for at komme tilbage til hovedmenu");

        Scanner input = new Scanner(System.in);
        int choise = input.nextInt();

        if (choise != 0) {
            Controller controller = new Controller();
            controller.reviewMenu(currentUser,choise);

        }
        if (choise == 0) ;
        {
            presentView(currentUser);
        }
    }

    /**
     * denne mellem menu giver muligheden for tilføje et review
     * @param lectureId'et videre føres blot til reviewCreateView for man ved hvilket lectureId der tilføjes et review til.
     */
    public void reviewMenu (int currentUser, int lectureId){
        Controller controller = new Controller();
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
        try {
            System.out.println("tast 1 - for oprette et personligt review");
            System.out.println("tast 2 - for at komme tilbage til hovedmenuen");
            Scanner inputChoise = new Scanner(System.in);
            int choise = inputChoise.nextInt();

            switch (choise) {
                case 1:
                    controller.createReview(currentUser, lectureId);
                    break;
                case 2:
                    controller.presentView(currentUser);
                    break;
                default:
                    System.out.println("Det var ikke en mulighed");
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Der blev fundet fejlen:" + e);
        }
    }


    public void reviewCreateView (int currentUser, int lectureid) {
        ReviewDTO reviewDTO = new ReviewDTO();

        System.out.println("Indtast din kommentar for undervisningsgangen: (tryk enter for at gå videre");
        Scanner inputComment = new Scanner(System.in);

        String comment = inputComment.nextLine();

        System.out.println("På en skala 1-5 hvor 5 er det bedste, hvor god var undervisningen?");
        Scanner inputRating = new Scanner(System.in);

        int rating = inputRating.nextInt();


        reviewDTO.setUserId(currentUser); //(accessService.getAccessToken().getId());
        reviewDTO.setLectureId(lectureid);
        reviewDTO.setComment(comment);
        reviewDTO.setRating(rating);
        reviewDTO.setDeleted(false);


        ReviewService reviewService = new ReviewService();

        reviewService.create(reviewDTO, new ResponseCallback<Boolean>() {
            public void success(Boolean data) {
                System.out.println("Dit review er oprettet.");
            }

            public void error(int status) {
                System.out.println("fik fejl: " + status );
            }
        } );

    }

public void showMyReviews (int currentUser){

    ReviewService reviewService = new ReviewService();
    reviewService.getUserReviews(currentUser, new ResponseCallback<ArrayList<ReviewDTO>>() {
        public void success(ArrayList<ReviewDTO> reviewDTOArrayList) {
            for (ReviewDTO r : reviewDTOArrayList)
                System.out.println("Id: "+ r.getId()+ " Kommentar:" + r.getComment()+ "  " + "Rating: " + r.getRating() );
        }

        public void error(int status) {
            System.out.println("Fejlede kaldet til server om brugers reviews fik fejl koden: " + status);
        }
    });


    System.out.println("Vælg hvilket review du vil slette ved at taste dets id");
    System.out.println("For at gå tilbage til hoved menu tast 0");

    Scanner inputChoise = new Scanner(System.in);
    int choise = inputChoise.nextInt();
    if (choise == 0){
        Controller controller = new Controller();
        controller.presentView(currentUser);
    }
    if (choise != 0){
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(choise);
        reviewDTO.setUserId(currentUser);
        reviewService.deleteReviewUser(reviewDTO, new ResponseCallback<Boolean>() {
                    public void success(Boolean data) {
                        System.out.println("Dit Review er blevet slettet");
                        System.out.println(data);
                    }

                    public void error(int status) {

                    }
                });

                Controller controller = new Controller();
        controller.presentView(currentUser);


    }

}


}
