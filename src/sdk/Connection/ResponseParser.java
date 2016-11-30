
package sdk.Connection;

/**
 * Dette interface er udarbejdet sammen med Jesper til øvelser og ligger på DIS16
 * https://github.com/Distribuerede-Systemer-2016/java-client/tree/master/src
 */
public interface ResponseParser {

    void payload(String json);
    void error(int status);

}