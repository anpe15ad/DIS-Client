package sdk.Connection;
import sdk.shared.ReviewDTO;

/**
 * Dette interface er udarbejdet sammen med Jesper til øvelser og ligger på DIS16
 * https://github.com/Distribuerede-Systemer-2016/java-client/tree/master/src
 */
public interface ResponseCallback<T> {

    void success(T data);
    void error(int status);

}