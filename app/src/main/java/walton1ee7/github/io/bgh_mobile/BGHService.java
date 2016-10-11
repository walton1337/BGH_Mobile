package walton1ee7.github.io.bgh_mobile;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Derek on 10/9/2016.
 */

public interface BGHService {
    @GET("/games.json")
    Call<List<Game>> getGames();
}
