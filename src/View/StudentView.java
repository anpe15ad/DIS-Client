package View;

import Logic.Controller;
import sdk.Service.AccessService;
import sdk.shared.LectureDTO;
import sdk.shared.ReviewDTO;
import sdk.shared.UserDTO;



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
                    controller.showCourses();
                    courseView(currentUser);
                    lectureView(currentUser);
                    presentView(currentUser);
                    break;
                case 2:

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
        System.out.println("-------------------vælg fag-----------------------");
        System.out.println("Indtast koden for et fag eks. BINTO1035U_XB_E16");
        LectureDTO lectureDTO = new LectureDTO();
        Scanner input = new Scanner(System.in);
        String binto = input.nextLine();
        lectureDTO.setCourseId(binto);

        Controller  controller = new Controller();
        System.out.println("-------------------------------------------------------");
        System.out.println("Der er tilknyttet følgende undervisningsgange til faget");
        controller.showLectures();
    }

    public void  lectureView(int currentUser) {
        System.out.println("indtast id for den undervisningsgang du vil evaluere");
        System.out.println("");
        System.out.println("indtast 2 for at komme tilbage til hovedmenu");

        Scanner input = new Scanner(System.in);
        int choise = input.nextInt();
        LectureDTO lectureDTO1 = new LectureDTO();
        lectureDTO1.setId(choise);

        if (choise != 0) {
            Controller controller = new Controller();
            controller.showReviews(choise);
           reviewMenu(lectureDTO1, currentUser);
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
    public void reviewMenu (LectureDTO lectureId, int currentUser){
        try {
            System.out.println("tast 1 - for oprette et personligt review");
            System.out.println("tast 2 - for at komme tilbage til hovedmenuen");
            Scanner inputChoise = new Scanner(System.in);
            int choise = inputChoise.nextInt();

            switch (choise) {
                case 1:
                    reviewCreateView(lectureId, currentUser);
                    break;
                case 2:
                    presentView(currentUser);
                    break;
                default:
                    System.out.println("Det var ikke en mulighed");
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Der blev fundet fejlen:" + e);
        }
    }


    public void reviewCreateView (LectureDTO lectureDTO, int currentUser) {
       // System.out.println("Du evaluere på faget: "+ lectureDTO.getDescription()+ "for undervisningsgang: " + lectureDTO.getStartDate());
        AccessService accessService = new AccessService();
        ReviewDTO reviewDTO = new ReviewDTO();

        System.out.println("Indtast din kommentar for undervisningsgangen: (tryk enter for at gå videre");
        Scanner inputComment = new Scanner(System.in);

        String comment = inputComment.nextLine();

        System.out.println("På en skala 1-5 hvor 5 er det bedste, hvor god var undervisningen?");
        Scanner inputRating = new Scanner(System.in);

        int rating = inputRating.nextInt();


        reviewDTO.setUserId(currentUser); //(accessService.getAccessToken().getId());
        reviewDTO.setLectureId(lectureDTO.getId());
        reviewDTO.setComment(comment);
        reviewDTO.setRating(rating);
        reviewDTO.setDeleted(false);

        Controller controller = new Controller();
        controller.createReview(reviewDTO);

    }

public void showMyReviews (int currentUser){

    Controller controller = new Controller();
    controller.showUserReviews(currentUser);

    System.out.println("Vælg et hvilket review du vil slette ved at taste dets id");
    System.out.println("For at gå tilbage til hoved menu tast 0");

    Scanner inputChoise = new Scanner(System.in);
    int choise = inputChoise.nextInt();
    if (choise == 0){
        presentView(currentUser);
    }
    if (choise != 0){
        //slet review kald controller til at slette review
    }

}


}
