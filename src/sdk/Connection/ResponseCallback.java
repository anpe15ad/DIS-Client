package sdk.Connection;
import sdk.shared.ReviewDTO;

public interface ResponseCallback<T> {

    void success(T data);
    void error(int status);

}