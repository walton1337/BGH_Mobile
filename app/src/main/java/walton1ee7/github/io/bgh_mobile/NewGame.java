
package walton1ee7.github.io.bgh_mobile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewGame {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("gametype_id")
    @Expose
    private String gametypeId;
    @SerializedName("gamename")
    @Expose
    private String gamename;
    @SerializedName("need_count")
    @Expose
    private String needCount;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("user_id")
    @Expose
    private String userId;

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The gametypeId
     */
    public String getGametypeId() {
        return gametypeId;
    }

    /**
     * 
     * @param gametypeId
     *     The gametype_id
     */
    public void setGametypeId(String gametypeId) {
        this.gametypeId = gametypeId;
    }

    /**
     * 
     * @return
     *     The gamename
     */
    public String getGamename() {
        return gamename;
    }

    /**
     * 
     * @param gamename
     *     The gamename
     */
    public void setGamename(String gamename) {
        this.gamename = gamename;
    }

    /**
     * 
     * @return
     *     The needCount
     */
    public String getNeedCount() {
        return needCount;
    }

    /**
     * 
     * @param needCount
     *     The need_count
     */
    public void setNeedCount(String needCount) {
        this.needCount = needCount;
    }

    /**
     * 
     * @return
     *     The startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * 
     * @param startTime
     *     The start_time
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * 
     * @return
     *     The endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * 
     * @param endTime
     *     The end_time
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * 
     * @return
     *     The latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * 
     * @param latitude
     *     The latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * 
     * @return
     *     The longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * 
     * @param longitude
     *     The longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return
     *     The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 
     * @param userId
     *     The user_id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

}
