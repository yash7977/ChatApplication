package com.example.chatapplication;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.internal.maps.zzt;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    EditText SearchBox;
    Button SearchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SearchBox= findViewById(R.id.SearchBox);
        SearchButton = findViewById(R.id.SearchButton);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Cities().execute(SearchBox.getText().toString());

            }
        });


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

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(new LatLng(, arg0.getLongitude())).title("It's Me!"));
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));



    }


    public  class Cities extends AsyncTask<String,Void, ArrayList<LatLng>> {


        @Override
        protected ArrayList<LatLng> doInBackground(String... strings) {
            ArrayList<LatLng> cities_lat_long = new ArrayList<>();
            HttpURLConnection connection = null;
            BufferedReader bufferedReader = null;
            StringBuilder stringBuilder = new StringBuilder();

            try {
                URL url = new URL("https://maps.googleapis.com/maps/api/place/autocomplete/json?types=(cities)&key=AIzaSyAhzAG3qeDmHvbBq8nVuy-lM3I_P7aUvVk&input="+strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                if(connection.getResponseCode()==HttpURLConnection.HTTP_OK){
                    bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line="";
                    while((line = bufferedReader.readLine())!=null){
                        stringBuilder.append(line);
                    }

                    JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                    JSONArray jsonArray = jsonObject.getJSONArray("predictions");
                    for(int i=0;i<jsonArray.length();i++){

                        JSONObject citiesJson = jsonArray.getJSONObject(i);
                        System.out.println("CITIES ID: "+ citiesJson.getString("place_id"));
                        URL detailurl = new URL("https://maps.googleapis.com/maps/api/place/details/json?place_id="+citiesJson.getString("place_id")+"&fields=name,rating,formatted_phone_number,geometry/location&key=AIzaSyAhzAG3qeDmHvbBq8nVuy-lM3I_P7aUvVk");
                        HttpURLConnection connection1 = (HttpURLConnection) detailurl.openConnection();

                        //https://maps.googleapis.com/maps/api/place/details/json?place_id=ChIJN1t_tDeuEmsRUsoyG83frY4&fields=name,rating,formatted_phone_number,geometry/location&key=AIzaSyAhzAG3qeDmHvbBq8nVuy-lM3I_P7aUvVk
                        connection1.connect();
                        if(connection1.getResponseCode()== HttpURLConnection.HTTP_OK){
                            BufferedReader reader = new BufferedReader(new InputStreamReader((connection1.getInputStream())));
                            String line1="";
                            StringBuilder stringBuilder1 = new StringBuilder();
                            while ((line1 = reader.readLine()) != null) {
                                stringBuilder1.append(line1);
                            }
                            JSONObject latlongObject = new JSONObject(stringBuilder1.toString());
                            JSONObject resultObject = new JSONObject(latlongObject.getString("result"));
                            System.out.println("result Object: "+resultObject.toString());
                            JSONObject geometryObject = new JSONObject(resultObject.getString("geometry"));
                            JSONObject locationObject = new JSONObject(geometryObject.getString("location"));
                            cities_lat_long.add(new LatLng(locationObject.getDouble("lat"),locationObject.getDouble("lng")));

                            System.out.println("DETAIL URL: "+stringBuilder1.toString());

                            //https://maps.googleapis.com/maps/api/place/details/json?place_id=ChIJN1t_tDeuEmsRUsoyG83frY4&fields=name,rating,formatted_phone_number&key=YOUR_API_KEY
                        }
                    }


                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return cities_lat_long;
        }


        @Override
        protected void onPostExecute(ArrayList<LatLng> strings) {

            final ArrayList<Marker> markers = new ArrayList<>();
            for(LatLng latLng: strings) {
                markers.add(mMap.addMarker(new MarkerOptions()
                        .position(latLng)));

            }


            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (Marker marker : markers) {
                builder.include(marker.getPosition());
            }
            LatLngBounds bounds = builder.build();
            int padding = 0; // offset from edges of the map in pixels
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            mMap.animateCamera(cu);

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    System.out.println(marker.getPosition().latitude);
                    Intent intent = getIntent();
                    //intent.putExtra("latlong",marker.getPosition());

                    if (intent.getStringExtra("requestCode").equals("1")){
                        intent.putExtra("resultCode","1");
                        intent.putExtra("latlong",marker.getPosition());
                        setResult(1,intent);
                        finish();
                    }else{
                        intent.putExtra("resultCode","2");
                        intent.putExtra("latlong",marker.getPosition());
                        setResult(2,intent);
                        finish();
                    }

                    return true;

                }
            });
        }
    }

}
