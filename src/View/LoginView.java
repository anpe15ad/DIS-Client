package View;

import Logic.Controller;
import Security.Digester;
import sdk.Connection.ResponseCallback;
import sdk.Service.UserService;
import sdk.shared.UserDTO;

import java.util.Scanner;

/**
 * Created by andershoumann on 17/11/2016.
 */
public class LoginView {

    public void presentLogin (final int currentUser){

        System.out.println("Velkommen til loginet");

        System.out.println("Cbsmail: ");
        Scanner inputMail = new Scanner(System.in);

        String mail = inputMail.nextLine();
        System.out.println("Password: ");

        Scanner inputPw = new Scanner(System.in);
        String pw = inputPw.nextLine();

        String securePw = Digester.hashWithSalt(pw);

        UserService userService = new UserService();

        userService.login(mail, pw, new ResponseCallback<UserDTO>() {
            public void success(UserDTO data) {
                if( data == null){
                System.out.println("Server kunne ikke finde login.");
                    int currentUser = data.getId();
                presentLogin(currentUser);

            }else{
                if(data.getType().contentEquals("student")){
                    System.out.println("Velkommen " + data.getType());

                    Controller controller = new Controller();
                    // Her sendes videre til student courses
                    int currentUser = data.getId();
                    controller.presentView(currentUser);

                }

                }if(data.getType().contentEquals("teacher")){
                    System.out.println("Velkommen" + data.getType());

                    Controller controller = new Controller();
                    // her sendes videre til teacher menu
                    controller.showlogin(currentUser);

                    }if(data.getType().contentEquals("admin")){
                    System.out.println("admins kan ikke benytte klienten, log på serveren");
                    presentLogin(currentUser);

                    }

            }
            public void error(int status) {

            }
        });


    }
}
