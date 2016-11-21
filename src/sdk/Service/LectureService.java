package sdk.Service;

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
     * Denne metode henter alle lectures ved et asynkront kald igennem responsecallback.
     * @param responseCallback
     */
    //ArrayList<Book> = T, nu er pladsen T taget, derfor er den ikke en placeholder mere.
        public void getAllLectures(final ResponseCallback<ArrayList<LectureDTO>> responseCallback){

            // UserDTO user = accesService.getAccessToken();
            LectureDTO Binto = new LectureDTO();

            String BintoString =  "BINTO1051U_LA_E16";// Binto.getCourseId();

            //der er http også hvilken metode du skal bruge get fx.
            HttpGet getRequest = new HttpGet(Connection.serverURL + "/lecture/" +  BintoString);

            //i javascript skal this altid defineres, her behøves den ikke
            connection.execute(getRequest, new ResponseParser() {
                public void payload(String json) {

                    //String jsonDecrypt = Digester.decrypt(json);
                    //Her bliver det modtagede json gemt i en arrayliste
                    ArrayList<LectureDTO> data;
                    data = gson.fromJson(json, new TypeToken<ArrayList<LectureDTO>>(){}.getType());
                    responseCallback.success(data);
                }

                public void error(int status) {
                    responseCallback.error(status);
                }
            });

        }


}
