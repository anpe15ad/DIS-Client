package sdk.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import sdk.Connection.Connection;
import sdk.Connection.ResponseCallback;
import sdk.Connection.ResponseParser;
import sdk.shared.LectureDTO;
import sdk.shared.ReviewDTO;

import java.util.ArrayList;

/**
 * Created by Anders Houmann on 14/11/2016.
 */
public class TeacherService {
    private  Connection connection = new Connection();
    private Gson gson = new Gson();

    public TeacherService(){
        this.connection = new Connection();
        this.gson = new Gson();
    }
    public void deleteReviewTeacher(ReviewDTO reviewDTO, final ResponseCallback<Boolean> responseCallback) {
        try {
            final Gson gson = new Gson();

            int reviewId =reviewDTO.getId();


            HttpDelete deleteRequest = new HttpDelete(Connection.serverURL + "/teacher/review/" + reviewId);
            deleteRequest.addHeader("Content-Type", "application/json");

            connection.execute(deleteRequest, new ResponseParser() {
                public void payload(String json) {

                    responseCallback.success(true);
                }

                public void error(int status) {
                    responseCallback.error(status);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * Denne metode henter alle lectures ved et asynkront kald igennem responsecallback.
     * @param responseCallback
     */
    //ArrayList<Book> = T, nu er pladsen T taget, derfor er den ikke en placeholder mere.
    public void averageOnCourse(String code ,final ResponseCallback<Double> responseCallback){

        //der er http også hvilken metode du skal bruge get fx.
        HttpGet getRequest = new HttpGet(Connection.serverURL + "/teacher/average/" +  code);

        connection.execute(getRequest, new ResponseParser() {
            public void payload(String json) {

                // String jsonDecrypt = Digester.decrypt(json);

                double data = Integer.valueOf(json);
                responseCallback.success(data);
            }

            public void error(int status) {
                responseCallback.error(status);
            }
        });

    }
}
