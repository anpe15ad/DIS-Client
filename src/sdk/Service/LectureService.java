package sdk.Service;

import Security.Digester;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.methods.HttpGet;
import sdk.Connection.Connection;
import sdk.Connection.ResponseCallback;
import sdk.Connection.ResponseParser;
import sdk.shared.LectureDTO;

import java.util.ArrayList;

/**
 * Created by Anders Houmann on 14/11/2016.
 */
public class LectureService {
        private Connection connection;
        private Gson gson;
        private AccessService accesService;

        //en constructor, når der initieres en bookservice kaldes denne også, og her laves en forbindelse.
        public LectureService(){
            this.connection = new Connection();
            this.gson = new Gson();
            this.accesService = new AccessService();
        }

    /**
     * Metode inspireret af metode lavet sammen til øvelses time:
     * https://github.com/Distribuerede-Systemer-2016/java-client/blob/master/src/sdk/services/BookService.java
     *
     * Denne metode henter alle lectures ved et asynkront kald igennem responsecallback.
     * @param code der er hvilket BINTO nr kurset har.
     * @param responseCallback
     */
    //ArrayList<Book> = T, nu er pladsen T taget, derfor er den ikke en placeholder mere.
        public void getAllLectures(String code ,final ResponseCallback<ArrayList<LectureDTO>> responseCallback){
            String encryptedUserId = Digester.encrypt(code);
            //der er http også hvilken metode du skal bruge get fx.
            HttpGet getRequest = new HttpGet(Connection.serverURL + "/lecture/" +  encryptedUserId);

            //i javascript skal this altid defineres, her behøves den ikke
            connection.execute(getRequest, new ResponseParser() {
                public void payload(String json) {

                    String jsonDecrypt = Digester.decrypt(json);
                    //Her bliver det modtagede json gemt i en arrayliste
                    ArrayList<LectureDTO> data;
                    data = gson.fromJson(jsonDecrypt, new TypeToken<ArrayList<LectureDTO>>(){}.getType());
                    responseCallback.success(data);
                }

                public void error(int status) {
                    responseCallback.error(status);
                }
            });

        }


}
