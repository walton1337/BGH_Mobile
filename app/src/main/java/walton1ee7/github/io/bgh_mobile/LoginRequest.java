
package walton1ee7.github.io.bgh_mobile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    @SerializedName("user")
    @Expose
    private UserEmail user;

    /**
     * 
     * @return
     *     The user
     */
    public UserEmail getUser() {
        return user;
    }

    /**
     * 
     * @param user
     *     The user
     */
    public void setUser(UserEmail user) {
        this.user = user;
    }

}
