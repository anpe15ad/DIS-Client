
package sdk.Connection;

public interface ResponseParser {

    void payload(String json);
    void error(int status);

}