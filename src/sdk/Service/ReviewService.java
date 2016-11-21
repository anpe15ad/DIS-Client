package sdk.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import sdk.Connection.Connection;
import sdk.Connection.ResponseCallback;
import sdk.Connection.ResponseParser;
import sdk.shared.LectureDTO;
import sdk.shared.ReviewDTO;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by Anders Houmann on 14/11/2016.
 */
public class ReviewService {

    private Connection connection;
    private Gson gson;

    public ReviewService(){
        this.connection = new Connection();
        this.gson = new Gson();

    }

    public void create(ReviewDTO review, final ResponseCallback<Boolean> responseCallback){
        try {
            HttpPost postRequest = new HttpPost(Connection.serverURL + "/student/review/");
            postRequest.addHeader("Content-Type", "application/json");
            // postRequest.addHeader("authorization", "NTxX4aHJ974xlJY6N3xFJXBB1gG7w8G8u8C20IFwp5Qvd4v1kHWf9PjBb1bc5Ei8");

            final StringEntity reviewString = new StringEntity(gson.toJson(review));
            postRequest.setEntity(reviewString);

            this.connection.execute(postRequest, new ResponseParser() {
                public void payload(String json) {
                 //   ReviewDTO reviewDTO = gson.fromJson(json, ReviewDTO.class);
                    responseCallback.success(true);
                }

                public void error(int status) {
                    responseCallback.error(status);
                }
            });
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public void getAllReviews(int lectureId, final ResponseCallback<ArrayList<ReviewDTO>> responseCallback){

        // UserDTO user = accesService.getAccessToken();



        //der er http også hvilken metode du skal bruge get fx.
        HttpGet getRequest = new HttpGet(Connection.serverURL + "/review/" +  lectureId);

        //i javascript skal this altid defineres, her behøves den ikke
        connection.execute(getRequest, new ResponseParser() {
            public void payload(String json) {

                //String jsonDecrypt = Digester.decrypt(json);
                //Her bliver det modtagede json gemt i en arrayliste
                ArrayList<ReviewDTO> data = gson.fromJson(json, new TypeToken<ArrayList<ReviewDTO>>(){}.getType());
                responseCallback.success(data);
            }

            public void error(int status) {
                responseCallback.error(status);
            }
        });

    }

    public void getUserReviews(int lectureId, final ResponseCallback<ArrayList<ReviewDTO>> responseCallback){
        //der er http også hvilken metode du skal bruge get fx.
        HttpGet getRequest = new HttpGet(Connection.serverURL + "/review/" +  lectureId);

        //i javascript skal this altid defineres, her behøves den ikke
        connection.execute(getRequest, new ResponseParser() {
            public void payload(String json) {

                //String jsonDecrypt = Digester.decrypt(json);
                //Her bliver det modtagede json gemt i en arrayliste
                ArrayList<ReviewDTO> data = gson.fromJson(json, new TypeToken<ArrayList<ReviewDTO>>(){}.getType());
                responseCallback.success(data);
            }

            public void error(int status) {
                responseCallback.error(status);
            }
        });

    }
}
