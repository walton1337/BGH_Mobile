package walton1ee7.github.io.bgh_mobile;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Derek on 10/9/2016.
 */

public interface BGHService {
    @GET("/games.json")
    Call<List<Game>> getGames();

    @Headers({"Accept: application/json"})
    @POST("/sessions")
    Call<LoginResponse> login(@Body LoginRequest userEmail);


    @Headers({"Accept: application/json"})
    @POST("/games")
    Call<NewGameResponse> addGame(@Body NewGameRequest newGame, @Header("X-User-Token") String authToken, @Header("X-User-Email") String email);
}
