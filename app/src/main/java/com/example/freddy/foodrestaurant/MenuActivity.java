package com.example.freddy.foodrestaurant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.freddy.foodrestaurant.Host.host;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MenuActivity extends AppCompatActivity {

    private Button foto;
    private EditText nombre;
    private EditText Precio;
    private EditText descripcion;

    private int confir=0;

    private host HOST =new host();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        nombre = findViewById(R.id.etNamemenu);
        Precio = findViewById(R.id.etPreciomenu);
        descripcion = findViewById(R.id.etDescripcionmenu);
        foto = findViewById(R.id.capturar);
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataMenu(nombre.getText().toString(), Precio.getText().toString(), descripcion.getText().toString());

                if(confir==1) {

//                    Intent intent = new Intent(ThirdActivity.this, SecondActivity.class);
//                    startActivity(intent);

                    nombre.setText("");
                    Precio.setText("");
                    descripcion.setText("");


                }
            }
        });
    }
    private void sendDataMenu(final String name, String precio, String descripcion ) {

        //Toast.makeText(getApplicationContext(),name, Toast.LENGTH_SHORT).show();


        if(!name.isEmpty()&& !precio.isEmpty()&& !descripcion.isEmpty()) {

            confir=1;

            RequestParams params = new RequestParams();
            params.put("name", name);
            params.put("price", precio);
            params.put("description", descripcion);




            AsyncHttpClient Client = new AsyncHttpClient();
            Client.post(HOST.getIp()+":4030/api/v1.0/menus", params, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {

                        Toast.makeText(getApplicationContext(),"Menu agregado", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), response.getString("name"), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MenuActivity.this, loadingImg.class);
                        startActivity(intent);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                    Toast.makeText(getApplicationContext(),"Menu ya existe",Toast.LENGTH_SHORT);
                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(),"llene los espacios en blanco", Toast.LENGTH_SHORT).show();
            confir=0;

        }
    }
}

