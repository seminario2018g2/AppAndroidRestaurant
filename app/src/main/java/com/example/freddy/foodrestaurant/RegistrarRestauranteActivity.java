package com.example.freddy.foodrestaurant;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.freddy.foodrestaurant.utils.Data;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class RegistrarRestauranteActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView map;
    private GoogleMap mMap;
    private Geocoder geocoder;
    private TextView street;
    private Button next;
    private LatLng mainposition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_restaurante);

        map = findViewById(R.id.mapView);
        map.onCreate(savedInstanceState);
        map.onResume();
        MapsInitializer.initialize(this);
        map.getMapAsync(this);
        geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
        street = findViewById(R.id.street);
        next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendData();
            }
        });
    }
    public void sendData () {
        TextView name = findViewById(R.id.name);
        TextView nit = findViewById(R.id.nit);
        TextView street = findViewById(R.id.street);
        TextView property = findViewById(R.id.property);
        TextView phone = findViewById(R.id.telf);

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("authorization", Data.TOKEN);

        RequestParams params = new RequestParams();
        params.add("name", name.getText().toString());
        params.add("nit", nit.getText().toString());
        params.add("street", street.getText().toString());
        params.add("property", property.getText().toString());
        params.add("phone", phone.getText().toString());
        params.add("Lat", String.valueOf(""));
        params.add("Log", String.valueOf(""));

        client.post(Data.REGISTER_RESTORANT, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                /*AlertDialog alertDialog = new AlertDialog.Builder(RegisterRestaurant.this).create();
                try {
                    String msn = response.getString("msn");
                    alertDialog.setTitle("RESPONSE SERVER");
                    alertDialog.setMessage(msn);
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
                Intent camera = new Intent(RegistrarRestauranteActivity.this, loadingImg.class);
                RegistrarRestauranteActivity.this.startActivity(camera);

                //AsyncHttpClient.log.w(LOG_TAG, "onSuccess(int, Header[], JSONObject) was not overriden, but callback was received");
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //-19.5783329,-65.7563853
        LatLng potosi = new LatLng(-19.5783329, -65.7563853);
        mainposition = potosi;
        mMap.addMarker(new MarkerOptions().position(potosi).title("Lugar").zIndex(17).draggable(true));
        mMap.setMinZoomPreference(16);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(potosi));

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                mainposition = marker.getPosition();
                String street_string = getStreet(marker.getPosition().latitude, marker.getPosition().longitude);
                street.setText(street_string);
            }
        });

    }

    public String getStreet (Double lat, Double lon) {
        List<Address> address;
        String result = "";
        try {
            address = geocoder.getFromLocation(lat, lon, 1);
            result += address.get(0).getThoroughfare();


        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

}
