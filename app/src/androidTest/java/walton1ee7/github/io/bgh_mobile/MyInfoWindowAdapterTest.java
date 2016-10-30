package walton1ee7.github.io.bgh_mobile;

import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.vision.text.Text;

import org.joda.time.Instant;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by Derek on 10/30/2016.
 */

@RunWith(AndroidJUnit4.class)
public class MyInfoWindowAdapterTest {

    final String testName = "testName";
    final String testType = "testType";
    final String testStart = "2016-11-01T16:02:00.000-04:00";
    final String testEnd = "2016-11-01T16:02:00.000-04:00";

    @Test
    public void getInfoWindow() throws Exception {
        Game game = new Game();
        game.setName(testName);
        game.setGametype(testType);
        game.setStartTime(testStart);
        game.setEndTime(testEnd);

        MarkerOptions opts = new MarkerOptions();
        opts.position(new LatLng(42.4062206562065, -71.1189115047455));

        mapView map = new mapView();
        mapView.MyInfoWindowAdapter testAdapter = map.new MyInfoWindowAdapter();

        View infoWindow = testAdapter.getInfoWindow(game);


        TextView view = (TextView) infoWindow.findViewById(R.id.InfoWindow_Title);
        view.findViewById(R.id.Game);
        view.findViewById(R.id.Start);
        view.findViewById(R.id.End);

        DateTimeFormatter fmt = DateTimeFormat.shortDateTime();
        TextView title = (TextView)view.findViewById(R.id.InfoWindow_Title);
        assertEquals(title.getText(), testName);
        TextView gameText = (TextView)view.findViewById(R.id.Game);
        assertEquals(gameText.getText(), testType);
        TextView startText = (TextView)view.findViewById(R.id.Start);
        assertEquals(startText.getText(), new Instant(testStart).toString(fmt));
        TextView endText = (TextView)view.findViewById(R.id.End);
        assertEquals(endText.getText(), new Instant(testEnd).toString(fmt));
    }

}