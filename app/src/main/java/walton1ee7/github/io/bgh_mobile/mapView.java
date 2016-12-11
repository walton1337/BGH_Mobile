package walton1ee7.github.io.bgh_mobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import io.fabric.sdk.android.Fabric;
import org.joda.time.Instant;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class mapView extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mTitle, mDrawerTitle;
    private static int LOGIN_CODE = 1337;


    private final List<String> menuChoices = new ArrayList<String>();
    private ArrayAdapter<String> arrayAdapter;

    private void loggedoutMenu() {
        menuChoices.clear();
        menuChoices.add("Login");
        menuChoices.add("Register");
        arrayAdapter.notifyDataSetChanged();
    }

    private void loggedinMenu() {
        menuChoices.clear();
        SharedPreferences sharedPref = mapView.this.getPreferences(Context.MODE_PRIVATE);
        menuChoices.add("New Event");
        menuChoices.add("Logout");
        menuChoices.add(sharedPref.getString("Email", ""));
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());


        setContentView(R.layout.activity_map_view);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mTitle = mDrawerTitle = getString(R.string.title_activity_map_view);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                menuChoices );
        SharedPreferences sharedPref = mapView.this.getPreferences(Context.MODE_PRIVATE);

        if(sharedPref.contains("Email") && sharedPref.contains("AuthToken")){
            loggedinMenu();
        } else {
            loggedoutMenu();
        }




        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.

        mDrawerList.setAdapter(arrayAdapter);
        class DrawerItemClickListener implements ListView.OnItemClickListener {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("BGH", "menuChoice " + menuChoices.get(position));
                SharedPreferences sharedPref = mapView.this.getPreferences(Context.MODE_PRIVATE);
                switch (menuChoices.get(position)) {
                    case "New Event":
                        break;
                    case "Login":
                        Intent i = new Intent(mapView.this, LoginActivity.class);
                        startActivityForResult(i, LOGIN_CODE);
                        break;
                    case "Logout":
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.clear();
                        editor.commit();
                        loggedoutMenu();
                        break;
                    case "Register":
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://biggamehunter.herokuapp.com/users/sign_up"));
                        startActivity(browserIntent);
                    default:
                        ;
                }


            }
        }

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request it is that we're responding to
        if (requestCode == LOGIN_CODE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                String authtoken = data.getStringExtra("AuthToken");
                String email = data.getStringExtra("Email");
                Log.d("mapView", "AuthToken: " + authtoken);
                Log.d("mapView", "Email: " + email);
                SharedPreferences sharedPref = mapView.this.getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("AuthToken", authtoken);
                editor.putString("Email", email);
                editor.commit();
                loggedinMenu();
            }
        }
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

        mMap.setInfoWindowAdapter(new MyInfoWindowAdapter());


        // Move and zoom the camera to Tufts
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(42.4075, -71.119)));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(16));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://biggamehunter.herokuapp.com")
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
                        switch (game.getImage()) {
                            case "ea":
                                opts.icon(BitmapDescriptorFactory.fromResource(R.drawable.ea_pin_green));
                                break;
                            case "basketball":
                                opts.icon(BitmapDescriptorFactory.fromResource(R.drawable.basketball_pin_green));
                                break;
                            case "boardgame":
                                opts.icon(BitmapDescriptorFactory.fromResource(R.drawable.boardgame_pin_green));
                                break;
                            case "cardgame":
                                opts.icon(BitmapDescriptorFactory.fromResource(R.drawable.cardgame_pin_green));
                                break;
                            case "catan":
                                opts.icon(BitmapDescriptorFactory.fromResource(R.drawable.catan_pin_green));
                                break;
                            case "csgo":
                                opts.icon(BitmapDescriptorFactory.fromResource(R.drawable.csgo_pin_green));
                                break;
                            case "dnd":
                                opts.icon(BitmapDescriptorFactory.fromResource(R.drawable.dnd_pin_green));
                                break;
                            case "football":
                                opts.icon(BitmapDescriptorFactory.fromResource(R.drawable.football_pin_green));
                                break;
                            case "frisbee":
                                opts.icon(BitmapDescriptorFactory.fromResource(R.drawable.frisbee_pin_green));
                                break;
                            case "heroesofthestorm":
                                opts.icon(BitmapDescriptorFactory.fromResource(R.drawable.heroesofthestorm_pin_green));
                                break;
                            case "smash":
                                opts.icon(BitmapDescriptorFactory.fromResource(R.drawable.smash_pin_green));
                                break;
                            case "soccer":
                                opts.icon(BitmapDescriptorFactory.fromResource(R.drawable.soccer_pin_green));
                                break;
                            case "sport":
                                opts.icon(BitmapDescriptorFactory.fromResource(R.drawable.sport_pin_green));
                                break;
                            case "tennis":
                                opts.icon(BitmapDescriptorFactory.fromResource(R.drawable.tennis_pin_green));
                                break;
                            case "videogame":
                                opts.icon(BitmapDescriptorFactory.fromResource(R.drawable.videogame_pin_green));
                                break;
                        }

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

    public class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
        @Override
        public View getInfoWindow(Marker marker) {
            Game game = (Game)marker.getTag();
            return getInfoWindow(game);
        }

        public View getInfoWindow (Game game) {
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
    }
}
