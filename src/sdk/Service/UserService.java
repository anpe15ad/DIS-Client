package sdk.Service;

import Security.Digester;
import com.google.gson.Gson;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import sdk.Connection.Connection;
import sdk.Connection.ResponseCallback;
import sdk.Connection.ResponseParser;
import sdk.shared.UserDTO;

import java.io.UnsupportedEncodingException;

/**
 * Created by Anders Houmann on 14/11/2016.
 */
public class UserService {


    public void login(String mail, String password, final ResponseCallback<UserDTO> responseCallback){

        HttpPost postRequest = new HttpPost(Connection.serverURL + "/login");

        UserDTO login = new UserDTO();
        login.setCbsMail(mail);
        login.setPassword(password);

        try {
            Gson gson = new Gson();
            String readyEncrypt = gson.toJson(login);
            String encrypted = Digester.encrypt(readyEncrypt);
            StringEntity loginInfo = new StringEntity(readyEncrypt);

            postRequest.setEntity(loginInfo);
            postRequest.setHeader("Content-Type", "application/json");


            Connection connection = new Connection();
            connection.execute(postRequest, new ResponseParser() {
                public void payload(String json) {
                   // String decrypted = Digester.decrypt(json);
                   final Gson gson = new Gson();
                    UserDTO accessToken = gson.fromJson(json, UserDTO.class);
                    AccessService accessService = new AccessService();
                    accessService.setAccessToken(accessToken);
                    responseCallback.success(accessToken);
                }
                public void error(int status) {
                    responseCallback.error(status);
                }
            });


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
