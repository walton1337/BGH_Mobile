
package walton1ee7.github.io.bgh_mobile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewGameRequest {

    @SerializedName("game")
    @Expose
    private NewGame game;

    /**
     * 
     * @return
     *     The game
     */
    public NewGame getGame() {
        return game;
    }

    /**
     * 
     * @param game
     *     The game
     */
    public void setGame(NewGame game) {
        this.game = game;
    }

}
