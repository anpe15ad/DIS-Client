import Logic.ConfigLoader;
import Logic.Controller;
import View.StudentView;
import sdk.Connection.ResponseCallback;

import sdk.Service.CourseService;
import sdk.shared.CourseDTO;
import sdk.shared.LectureDTO;

import java.util.ArrayList;

public class Main {


    public static void main(String[] args) {


        ConfigLoader.parseConfig();


/*
        StudentView studentView = new StudentView();
        LectureDTO lectureDTO = new LectureDTO();
        lectureDTO.setId(4895);
        studentView.reviewView(lectureDTO);
*/

        Controller controller = new Controller();
        controller.showMainMenu();
    }
}
