package walton1ee7.github.io.bgh_mobile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Derek on 12/9/2016.
 */

public class LoginResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("info")
    @Expose
    private Info info;
    @SerializedName("data")
    @Expose
    private AuthToken data;

    /**
     *
     * @return
     *     The success
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     *
     * @param success
     *     The success
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    /**
     *
     * @return
     *     The info
     */
    public Info getInfo() {
        return info;
    }

    /**
     *
     * @param info
     *     The info
     */
    public void setInfo(Info info) {
        this.info = info;
    }

    /**
     *
     * @return
     *     The data
     */
    public AuthToken getData() {
        return data;
    }

    /**
     *
     * @param data
     *     The data
     */
    public void setData(AuthToken data) {
        this.data = data;
    }

}


