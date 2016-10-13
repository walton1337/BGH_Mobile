package walton1ee7.github.io.bgh_mobile;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import org.joda.time.Instant;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class mapView extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add marker for each lat/long in JSON!!!

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                Game game = (Game)marker.getTag();
                DateTimeFormatter fmt = DateTimeFormat.shortDateTime();
                View infowindow = getLayoutInflater().inflate(R.layout.map_infowindow, null);
                TextView title = (TextView)infowindow.findViewById(R.id.InfoWindow_Title);
                title.setText(game.getName());
                TextView gameText = (TextView)infowindow.findViewById(R.id.Game);
                gameText.setText(game.getGametype());
                TextView startText = (TextView)infowindow.findViewById(R.id.Start);
                startText.setText(new Instant(game.getStartTime()).toString(fmt));
                TextView endText = (TextView)infowindow.findViewById(R.id.End);
                endText.setText(new Instant(game.getEndTime()).toString(fmt));
                return infowindow;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });


        // Move and zoom the camera to Tufts
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(42.4075, -71.119)));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(16));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://biggamehunter.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BGHService bghService = retrofit.create(BGHService.class);
        Call<List<Game>> call = bghService.getGames();
        call.enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                if (response.isSuccessful()) {
                    // tasks available
                    DateTimeFormatter fmt = DateTimeFormat.shortDateTime();
                    for (Game game: response.body()) {
                        MarkerOptions opts = new MarkerOptions();
                        opts.position(new LatLng(game.getLatitude(), game.getLongitude()));
                        mMap.addMarker(opts).setTag(game);
                    }
                } else {
                    // error response, no access to resource?
                }
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                // something went completely south (like no internet connection)
                Log.d("Error", t.getMessage());
            }
        });
    }
}
