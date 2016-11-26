package sdk.Service;

import Security.Digester;
import com.google.gson.Gson;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import sdk.Connection.Connection;
import sdk.Connection.ResponseCallback;
import sdk.Connection.ResponseParser;

import sdk.shared.ReviewDTO;


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

    /**
     * Service der sender det asynkrone kald for at slette et review til en teachers kurser
     * @param reviewDTO indeholder det reviewid'et der skal slettes
     * @param responseCallback
     */
    public void deleteReviewTeacher(ReviewDTO reviewDTO, final ResponseCallback<Boolean> responseCallback) {
        try {
            int reviewId =reviewDTO.getId();
            String encryptReview = Digester.encrypt(String.valueOf(reviewId));

            HttpDelete deleteRequest = new HttpDelete(Connection.serverURL + "/teacher/review/" + encryptReview);
            deleteRequest.addHeader("Content-Type", "application/json");

            connection.execute(deleteRequest, new ResponseParser() {
                public void payload(String json) {
                    String decrypted = Digester.decrypt(json);
                 //   if (decrypted.equals("\"\\\"true\\\"\"")) {

                        responseCallback.success(true);
                 //   } else {
                        System.out.println("Returnerede ikke true 'rigtigt'");
                 //   }
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
    public void averageOnCourse(String code ,final ResponseCallback<String> responseCallback){

        String encrypted = Digester.encrypt(code);

        //der er http også hvilken metode du skal bruge get fx.
        HttpGet getRequest = new HttpGet(Connection.serverURL + "/teacher/average/" +  encrypted);

        connection.execute(getRequest, new ResponseParser() {
            public void payload(String json) {

                 String jsonDecrypt = Digester.decrypt(json);


                responseCallback.success(jsonDecrypt);
            }

            public void error(int status) {
                responseCallback.error(status);
            }
        });

    }
}
