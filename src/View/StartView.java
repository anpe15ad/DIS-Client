package View;

import Logic.Controller;

import javax.naming.ldap.Control;
import java.util.EmptyStackException;
import java.util.Scanner;

/**
 * Created by andershoumann on 20/11/2016.
 */
public class StartView {

    public void mainMenu() {
        Controller controller = new Controller();

        System.out.println("------ Velkommen til undervisningsevaluering ------");
        System.out.println("tast 1 for at logge ind");
        System.out.println("tast 0 for at slukke programmet");

        try{


        Scanner inputChoise = new Scanner(System.in);

        int choise = inputChoise.nextInt();

        if (choise == 1 ){
            controller.showlogin(0);

        }
        if (choise == 0){
            System.exit(0);
        }
        if (choise != 0 && choise != 1){
            System.out.println("Du har kun 2 muligheder kammerat...");
                mainMenu();
        }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Det lykkedes ikke at finde en menu");
            mainMenu();
        }

    }
}
