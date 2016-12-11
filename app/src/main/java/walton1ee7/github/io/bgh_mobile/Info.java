
package walton1ee7.github.io.bgh_mobile;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Info {

    @SerializedName("password")
    @Expose
    private List<String> password = null;

    @SerializedName("message")
    @Expose
    private String message = null;

    /**
     * 
     * @return
     *     The password
     */
    public List<String> getPassword() {
        return password;
    }

    /**
     * 
     * @param password
     *     The password
     */
    public void setPassword(List<String> password) {
        this.password = password;
    }

    /**
     *
     * @return
     *     The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     *     The message
     */
    public void setPassword(String message) {
        this.message = message;
    }
}
