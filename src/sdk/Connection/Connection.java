package sdk.Connection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Denne metode er udarbejdet sammen med Jesper til øvelser og ligger på DIS16
 * https://github.com/Distribuerede-Systemer-2016/java-client/tree/master/src
 */

public class Connection {

    public static String serverURL = "http://localhost:5000/api";
    private CloseableHttpClient httpClient;

    public Connection(){
        this.httpClient = HttpClients.createDefault();
    }


    public void execute(HttpUriRequest uriRequest, final ResponseParser methods){

        // Create a custom response handler
        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

            public String handleResponse(final HttpResponse response) throws IOException {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    methods.error(status);
                }
                return null;
            }

        };

        try {
            String json = this.httpClient.execute(uriRequest, responseHandler);
            methods.payload(json);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}