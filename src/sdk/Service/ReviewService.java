package sdk.Service;

import Security.Digester;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import sdk.Connection.Connection;
import sdk.Connection.ResponseCallback;
import sdk.Connection.ResponseParser;
import sdk.shared.LectureDTO;
import sdk.shared.ReviewDTO;
import sdk.shared.UserDTO;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by Anders Houmann on 14/11/2016.
 */
public class ReviewService {

    private Connection connection;
    private Gson gson;

    public ReviewService() {
        this.connection = new Connection();
        this.gson = new Gson();

    }

    /**
     * Metode inspireret af metode lavet sammen til øvelses time:
     * https://github.com/Distribuerede-Systemer-2016/java-client/blob/master/src/sdk/services/BookService.java
     * Kald til serveren der opretter et review
     * @param review DTO af ReviewDTO klassen der bliver postet som en stringEntity
     * @param responseCallback
     */
    public void create(ReviewDTO review, final ResponseCallback<Boolean> responseCallback) {
        try {

            HttpPost postRequest = new HttpPost(Connection.serverURL + "/student/review/");
            postRequest.addHeader("Content-Type", "application/json");


            final StringEntity reviewString = new StringEntity(Digester.encrypt(gson.toJson(review)));
            postRequest.setEntity(reviewString);

            this.connection.execute(postRequest, new ResponseParser() {
                public void payload(String json) {

                    responseCallback.success(true);
                }

                public void error(int status) {
                    responseCallback.error(status);
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    /**
     * Metode inspireret af metode lavet sammen til øvelses time:
     * https://github.com/Distribuerede-Systemer-2016/java-client/blob/master/src/sdk/services/BookService.java
     *
     * Henter alle reviews for et lecureID
     * @param lectureId er en int i denne metode.
     * @param responseCallback modtager en Arrayliste af ReviewDTO.
     */
    public void getAllReviews(int lectureId, final ResponseCallback<ArrayList<ReviewDTO>> responseCallback) {
        String encryptedUserId = Digester.encrypt(String.valueOf(lectureId));


        HttpGet getRequest = new HttpGet(Connection.serverURL + "/review/" + encryptedUserId);

        //i javascript skal this altid defineres, her behøves den ikke
        connection.execute(getRequest, new ResponseParser() {
            public void payload(String json) {

                String jsonDecrypt = Digester.decrypt(json);
                //Her bliver det modtagede json gemt i en arrayliste
                ArrayList<ReviewDTO> data = gson.fromJson(jsonDecrypt, new TypeToken<ArrayList<ReviewDTO>>() {}.getType());
                responseCallback.success(data);
            }

            public void error(int status) {
                responseCallback.error(status);
            }
        });

    }

    /**
     *  Metode inspireret af metode lavet sammen til øvelses time:
     * https://github.com/Distribuerede-Systemer-2016/java-client/blob/master/src/sdk/services/BookService.java
     *
     * Henter alle reviews en user har oprettet.
     *
     * @param userId tager det forelæsnings id brugeren har valgt.
     * @param responseCallback
     */
    public void getUserReviews(int userId, final ResponseCallback<ArrayList<ReviewDTO>> responseCallback) {
        String encryptedUserId = Digester.encrypt(String.valueOf(userId));

        //der er http også hvilken metode du skal bruge get fx.
        HttpGet getRequest = new HttpGet(Connection.serverURL + "/student/review/" + encryptedUserId);

        //i javascript skal this altid defineres, her behøves den ikke
        connection.execute(getRequest, new ResponseParser() {
            public void payload(String json) {

                String jsonDecrypt = Digester.decrypt(json);
                //Her bliver det modtagede json gemt i en arrayliste
                ArrayList<ReviewDTO> data = gson.fromJson(jsonDecrypt, new TypeToken<ArrayList<ReviewDTO>>() {}.getType());
                responseCallback.success(data);
            }

            public void error(int status) {
                responseCallback.error(status);
            }
        });

    }

    /**
     *
     * Metode til at softdelete for en user
     *
     * @param reviewDTO'et er det id som brugeren ønsker at slette
     * @param responseCallback
     */
    public void deleteReviewUser(ReviewDTO reviewDTO, final ResponseCallback<Boolean> responseCallback) {
        try {
            final Gson gson = new Gson();

            int reviewId =reviewDTO.getId();
            int userId = reviewDTO.getUserId();

            String encryptReview = Digester.encrypt(String.valueOf(reviewId));
            String encryptUser = Digester.encrypt(String.valueOf(userId));


            HttpDelete deleteRequest = new HttpDelete(Connection.serverURL + "/student/review/" + encryptReview + "/"+ encryptUser);
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
}
