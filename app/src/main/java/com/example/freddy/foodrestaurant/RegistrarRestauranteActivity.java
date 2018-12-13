package com.example.freddy.foodrestaurant;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.freddy.foodrestaurant.Host.host;
import com.example.freddy.foodrestaurant.utils.Data;
import com.example.freddy.foodrestaurant.utils.UserData;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class RegistrarRestauranteActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView map;
    private GoogleMap mMap;



    private Button EnviarRegistro;
    private EditText name;
    private EditText nit;
    private EditText property;
    private EditText phone;
    private EditText street;
    private EditText logo;
    private Context root;
    private int confir=0;

    private host HOST =new host();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_restaurante);

        map = findViewById(R.id.mapView3);
        map.onCreate(savedInstanceState);
        map.onResume();
        MapsInitializer.initialize(this);
        map.getMapAsync(this);

        root=this;

        name=findViewById(R.id.Name);
        nit=findViewById(R.id.Nit);
        property =findViewById(R.id.Property);
        phone=findViewById(R.id.Phone);
        street =findViewById(R.id.Street);
        EnviarRegistro=findViewById(R.id.btnSend);
        EnviarRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendDataRegister(name.getText().toString(),
                        nit.getText().toString(), property.getText().toString(),phone.getText().toString(),
                        street.getText().toString());

                if(confir==1) {

                    name.setText("");
                    nit.setText("");
                    property.setText("");
                    phone.setText("");
                    street.setText("");


                }
            }

        });
    }
    private void sendDataRegister(String name,String nit, String property, String phone, String street) {
        if(!name.isEmpty()&&!nit.isEmpty()&&!property.isEmpty()&&!phone.isEmpty()&& !street.isEmpty()) {
            confir=1;
            RequestParams params = new RequestParams();
            params.put("name", name);
            params.put("nit", nit);
            params.put("property", property);
            params.put("phone", phone);
            params.put("street", street);
            params.put("lat","");
            params.put("lon","");
            params.put("logo","");

            AsyncHttpClient Client = new AsyncHttpClient();
            Client.post(HOST.getIp()+":4030/api/v1.0/restaurante", params, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        String msn = response.getString("msn");
                        String id = response.getString("id");
                        UserData.ID = id;

                        Intent intent = new Intent(root, loadingImg.class);
                        root.startActivity(intent);
                        Toast.makeText(getApplicationContext(), response.getString("estado"), Toast.LENGTH_SHORT).show();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                    Toast.makeText(getApplicationContext(),"fail",Toast.LENGTH_SHORT);
                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(),"llene todos los espacios en blanco", Toast.LENGTH_SHORT).show();
            confir=0;

        }
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng potosi = new LatLng( -19.5830, -65.7561);
        mMap.addMarker(new MarkerOptions().position(potosi).title("Marker in Potosi").zIndex(17).draggable(true));
        mMap.setMaxZoomPreference(17);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(potosi,14));
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {

            }
        });
    }
}
