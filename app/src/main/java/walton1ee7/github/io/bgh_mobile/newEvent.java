package walton1ee7.github.io.bgh_mobile;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.vision.text.Text;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.io.IOException;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class newEvent extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String[] gametypes = {
        "CS:GO",
        "D&D",
        "Hearthstone",
        "Heroes of the Storm",
        "Other Board Games",
        "Other Video Games",
        "Settlers of Catan",
        "Soccer",
        "Super Smash Brothers Melee"
    };
    private DateTimeFormatter datefmt = DateTimeFormat.forPattern("MM/dd/yyyy");
    private DateTimeFormatter timefmt = DateTimeFormat.forPattern("HH:mm");
    private DateTimeFormatter datetimefmt = ISODateTimeFormat.dateTime();
    public DateTime start = new DateTime();
    public DateTime end = start.plusHours(1);
    public Marker eventMarker;
    private String email;
    private String authToken;
    private BGHService bghService = new Retrofit.Builder()
            .baseUrl("https://biggamehunter.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(BGHService.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_event);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Spinner spinner = (Spinner)  newEvent.this.findViewById(R.id.gametypeSelect);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(newEvent.this,
                android.R.layout.simple_spinner_dropdown_item, gametypes);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        refreshTimes();

        Button button = (Button) findViewById(R.id.submitButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nameText = (EditText) findViewById(R.id.name);
                EditText countText = (EditText) findViewById(R.id.playerCounter);
                Spinner gametypeSpinner = (Spinner) findViewById(R.id.gametypeSelect);
                EditText descriptionText = (EditText) findViewById(R.id.locationDescription);
                String startTime = datetimefmt.print(start);
                String endTime = datetimefmt.print(end);

                NewGame game = new NewGame();
                game.setName(nameText.getText().toString());
                game.setNeedCount(countText.getText().toString());
                game.setGamename(gametypeSpinner.getSelectedItem().toString());
                game.setGametypeId("0");
                game.setDescription(descriptionText.getText().toString());
                game.setStartTime(startTime);
                game.setEndTime(endTime);
                game.setLatitude(Double.toString(eventMarker.getPosition().latitude));
                game.setLongitude(Double.toString(eventMarker.getPosition().longitude));

                NewGameRequest req = new NewGameRequest();
                req.setGame(game);
                Call<NewGameResponse> call = bghService.addGame(req, authToken, email);
                call.enqueue(new Callback<NewGameResponse>() {
                    @Override
                    public void onResponse(Call<NewGameResponse> call, Response<NewGameResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Your event was succesfully added!", Toast.LENGTH_LONG);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "There was an error adding your event.", Toast.LENGTH_LONG);
                        }
                    }

                    @Override
                    public void onFailure(Call<NewGameResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "There was an error adding your event.", Toast.LENGTH_LONG);
                    }
                });
            }
        });
    }



    public void refreshTimes() {
        TextView startDate = (TextView) findViewById(R.id.startDate);
        TextView startTime = (TextView) findViewById(R.id.startTime);
        startDate.setText(datefmt.print(start));
        startTime.setText(timefmt.print(start));

        TextView endDate = (TextView) findViewById(R.id.endDate);
        TextView endTime = (TextView) findViewById(R.id.endTime);
        endDate.setText(datefmt.print(end));
        endTime.setText(timefmt.print(end));

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
        Intent intent = getIntent();

        email = intent.getStringExtra("email");
        authToken = intent.getStringExtra("authtoken");

        mMap = googleMap;

        // Add a marker and move the camera
        LatLng curr = new LatLng(intent.getDoubleExtra("lat", 42.4075), intent.getDoubleExtra("lon", -71.119));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(curr));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(intent.getFloatExtra("zoom", 16)));
        eventMarker = mMap.addMarker(new MarkerOptions().position(curr));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                eventMarker = mMap.addMarker(new MarkerOptions().position(latLng));
            }
        });
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        private String type;
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            Bundle args = getArguments();
            final Calendar c = Calendar.getInstance();
            int hour = args.getInt("hour", c.get(Calendar.HOUR_OF_DAY));
            int minute = args.getInt("minute", c.get(Calendar.MINUTE));
            type = args.getString("type");

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            newEvent activity = (newEvent)getActivity();
            if (type == "start") {
                DateTime newStart = activity.start.withMinuteOfHour(minute).withHourOfDay(hourOfDay);
                Period p = new Period(activity.start, activity.end);
                activity.end = newStart.plus(p);
                activity.start = newStart;
            } else if (type == "end"){
                DateTime newEnd = activity.end.withMinuteOfHour(minute).withHourOfDay(hourOfDay);
                if (activity.start.isBefore(newEnd)) {
                    activity.end = newEnd;
                }
            }
            activity.refreshTimes();

        }
    }

    public void showTimePicker (View v) {
        Bundle b = new Bundle();
        if(v.getId() == R.id.startTime) {
            Log.d("TimePicker", "Clicked startTime");
            b.putInt("hour", start.getHourOfDay());
            b.putInt("minute", start.getMinuteOfHour());
            b.putString("type", "start");
        }
        if(v.getId() == R.id.endTime) {
            Log.d("TimePicker", "Clicked endTime");
            b.putInt("hour", end.getHourOfDay());
            b.putInt("minute", end.getMinuteOfHour());
            b.putString("type", "end");
        }

        DialogFragment newFragment = new TimePickerFragment();
        newFragment.setArguments(b);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        private String type;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            Bundle args = getArguments();
            final Calendar c = Calendar.getInstance();
            int year = args.getInt("year", c.get(Calendar.YEAR));
            int month = args.getInt("month", c.get(Calendar.MONTH));
            int day = args.getInt("day", c.get(Calendar.DAY_OF_MONTH));
            type = args.getString("type");

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month-1, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            newEvent activity = (newEvent)getActivity();
            month = month + 1;
            if (type == "start") {
                DateTime newStart = activity.start.withMonthOfYear(month).withDayOfMonth(day).withYear(year);
                Period p = new Period(activity.start, activity.end);
                activity.end = newStart.plus(p);
                activity.start = newStart;
            } else if (type == "end"){
                DateTime newEnd = activity.end.withMonthOfYear(month).withDayOfMonth(day).withYear(year);
                if (activity.start.isBefore(newEnd)) {
                    activity.end = newEnd;
                }
            }
            activity.refreshTimes();
        }
    }

    public void showDatePickerDialog(View v) {
        Bundle b = new Bundle();
        if(v.getId() == R.id.startDate) {
            Log.d("DatePicker", "Clicked startDate");
            b.putInt("day", start.getDayOfMonth());
            b.putInt("month", start.getMonthOfYear());
            b.putInt("year", start.getYear());
            b.putString("type", "start");
        }
        if(v.getId() == R.id.endDate) {
            Log.d("DatePicker", "Clicked endDate");
            b.putInt("day", end.getDayOfMonth());
            b.putInt("month", end.getMonthOfYear());
            b.putInt("year", end.getYear());
            b.putString("type", "end");
        }
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.setArguments(b);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}
