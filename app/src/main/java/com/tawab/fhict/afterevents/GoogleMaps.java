package com.tawab.fhict.afterevents;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GoogleMaps extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mMap;


    //static String LOCATION_NAME_TEST = "Arnhem";

    private ListView listview;
    public static ArrayList<String> aftiesArray = new ArrayList<String>();

    public static String name;
    public static String location;
    public static String genre;


    public static String afterNaam;
    public static String afterSoort;
    public static double afterLat;
    public static double afterLon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);

        Intent intent2 = getIntent();
        Bundle extras = intent2.getExtras();
        name = extras.getString("EventName");
        location = extras.getString("EventPlace");
        genre = extras.getString("EventGenre");

        System.out.println("@@@@@@@@@@@@@@@@: " + name + location + genre);




        // Check of google play apk beschikbaar is, zoja map laten zien, en niet, dan gwn normale map layout laten zien
        // Als screen gedraaid wordt, wordt deze methode opnieuw geroepen
        if (isGooglePlayServicesAvailable()) {

            setContentView(R.layout.activity_map2);

            // --------->>>> sqllite database data eruit halen...
            SQLiteDatabaseHandler db = new SQLiteDatabaseHandler(this);


            // afterparties aantal
            int aantalAfters = db.getAftersCount();

            TextView aantalAfterss = (TextView) findViewById(R.id.aantalAfterparties);
            aantalAfterss.setText("Er zijn op dit moment " + aantalAfters + " Afters aanwezig!");







            // show all afterparties from SQlite db
            List<Afters> afterparties = db.getAllAfters();

            for (Afters cn : afterparties) {
                String afters = "Id: "+cn.get_afterid()+" ,Name: " + cn.get_afterNaam() + " ,Soort: " + cn.get_afterSoort() + " ,Lat: " + cn.get_lat() + " ,Lon: " + cn.get_lon();
                // Writing Contacts to log
                Log.d("Afters: ", afters);
            }


            listview = (ListView) findViewById(R.id.alleAfterParties);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, aftiesArray);

            listview.setAdapter(adapter);
            //adapter.clear();


            // Obtain the MapFragment and get notified when the map is ready to be used.
            MapFragment mapFragment = (MapFragment) getFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);


        } else {
            setContentView(R.layout.activity_main);
        }


    }



    // method om te kijken of googlePlayService beschikbaar is
    public boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();

        int status = googleApiAvailability.isGooglePlayServicesAvailable(this);

        if(status == ConnectionResult.SUCCESS) {
            return true;
        } else if (googleApiAvailability.isUserResolvableError(status)) {
            googleApiAvailability.getErrorDialog(this, status, 2404).show();
        } else {
            Toast.makeText(this, "Can't connect to mapping service", Toast.LENGTH_SHORT).show();
        }

        return false;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.getUiSettings().setTiltGesturesEnabled(false);
        googleMap.getUiSettings().setRotateGesturesEnabled(false);
        googleMap.getUiSettings().setScrollGesturesEnabled(true);
        googleMap.getUiSettings().setZoomGesturesEnabled(false);


        // geocoding gebruiken -> Stad ophalen en geocoder class helpt dan locatie vinden van de stad string
        // LETOP: 2500 requests per 24 uur max.

        mMap = googleMap;

        Geocoder gc = new Geocoder(GoogleMaps.this);
        try {
            List<android.location.Address> list = gc.getFromLocationName(location, 1);
            Address add = list.get(0);
            String locality = add.getLocality();
            System.out.println("TESTTTT locatie found->>>" + locality);
            double lat = add.getLatitude();
            double lng = add.getLongitude();

            gotoLocation(lat, lng, 15);


        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("INCORRECT EVENT NAME, LOCATION COULDN'T BE FOUND!");
        }

        // double EINDHOVEN_LAT = 123;
        // double EINDHOVEN_LNG = 123;
        // gotoLocation(EINDHOVEN_LAT, EINDHOVEN_LNG, 10);
    }

    public Bitmap resizeMapIcons(String iconName, int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(iconName, "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }

    private void gotoLocation(double lat, double lng, float zoom) {
        LatLng latLng = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, zoom);


        Random r = new Random();
        int ret = r.nextInt(10 + 1);
        double randomNumber = ret / 1000.0;
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@:" + randomNumber);

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lng))
                .title(location)
                .snippet(name)
                .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("foodmarker",125,125)))
        );

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lng-0.002))
                .title(location)
                .snippet(name)
                .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("foodmarker",125,125)))
        );

/*        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat+randomNumber, lng-0.003))
                .title(location)
                .snippet(name)
                .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("foodmarker",125,125)))
        );

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat-randomNumber, lng+0.003))
                .title(location)
                .snippet(name)
                .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("foodmarker",125,125)))
        );*/



        mMap.moveCamera(update);
    }


}
