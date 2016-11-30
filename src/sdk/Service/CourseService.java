package sdk.Service;


import Security.Digester;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;

import java.awt.print.Book;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


import sdk.Connection.Connection;
import sdk.Connection.ResponseCallback;
import sdk.Connection.ResponseParser;
import sdk.shared.CourseDTO;
import sdk.shared.ReviewDTO;
import sdk.shared.UserDTO;

public class CourseService {
    private Connection connection;
    private Gson gson;


    //en constructor, når der initieres en Course service kaldes denne også, og her laves en forbindelse.
    public CourseService(){
        this.connection = new Connection();
        this.gson = new Gson();
    }

    //ArrayList<CourseDTO> = T, nu er pladsen T taget, derfor er den ikke en placeholder mere.
    public void getAllCourses(int currentUser, final ResponseCallback<ArrayList<CourseDTO>> responseCallback){
        String encryptedUserId = Digester.encrypt(String.valueOf(currentUser));

        //der er http også hvilken metode du skal bruge get fx.
        HttpGet getRequest = new HttpGet(Connection.serverURL + "/course/" +  encryptedUserId);

        connection.execute(getRequest, new ResponseParser() {
            public void payload(String json) {
                String jsonDecrypt = Digester.decrypt(json);
                //Her bliver det modtagede json gemt i en arrayliste
                ArrayList<CourseDTO> courses = gson.fromJson(jsonDecrypt, new TypeToken<ArrayList<CourseDTO>>(){}.getType());
                responseCallback.success(courses);
            }

            public void error(int status) {
                responseCallback.error(status);
            }
        });

    }

}
