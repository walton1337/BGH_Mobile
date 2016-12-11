
package walton1ee7.github.io.bgh_mobile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import walton1ee7.github.io.bgh_mobile.User;

public class AuthToken {

    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("auth_token")
    @Expose
    private String authToken;

    /**
     * 
     * @return
     *     The user
     */
    public User getUser() {
        return user;
    }

    /**
     * 
     * @param user
     *     The user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * 
     * @return
     *     The authToken
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     * 
     * @param authToken
     *     The auth_token
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

}
