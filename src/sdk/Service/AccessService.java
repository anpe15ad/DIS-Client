package sdk.Service;


import sdk.shared.UserDTO;

/**
 * Created by andershoumann on 17/11/2016.
 */
public class AccessService {


        private UserDTO accessToken;

        public void setAccessToken(UserDTO token){
            accessToken = token;
        }
        public UserDTO getAccessToken(){
            return accessToken;
        }
        public void clear(){
            accessToken = null;
        }

}

