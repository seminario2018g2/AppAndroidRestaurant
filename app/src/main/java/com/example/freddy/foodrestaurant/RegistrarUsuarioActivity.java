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

public class RegistrarUsuarioActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Email;
    private EditText Phone;
    private EditText Ci;
    private EditText Password;
    private Button Register;

    private int confir=0;

    private host HOST =new host();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        Name = findViewById(R.id.etName);
        Email = findViewById(R.id.etEmail);
        Phone = findViewById(R.id.etPhone);
        Ci = findViewById(R.id.etci);
        Password = findViewById(R.id.etPassword);
        Register = findViewById(R.id.btnRegister);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataRegister(Name.getText().toString(), Email.getText().toString(),  Phone.getText().toString(),
                        Ci.getText().toString(), Password.getText().toString());

                if(confir==1) {

//                    Intent intent = new Intent(ThirdActivity.this, SecondActivity.class);
//                    startActivity(intent);

                    Name.setText("");
                    Email.setText("");
                    Phone.setText("");
                    Ci.setText("");
                    Password.setText("");

                }
            }
        });


    }

    private void sendDataRegister(final String name, String Email, String Phone, String Ci, String Password) {

        //Toast.makeText(getApplicationContext(),name, Toast.LENGTH_SHORT).show();


        if(!name.isEmpty()&& !Email.isEmpty()&& !Phone.isEmpty()&& !Ci.isEmpty()&& !Password.isEmpty()) {

            confir=1;

            RequestParams params = new RequestParams();
            params.put("name", name);
            params.put("email", Email);
            params.put("phone", Phone);
            params.put("ci", Ci);
            params.put("password", Password);



            AsyncHttpClient Client = new AsyncHttpClient();
            Client.post(HOST.getIp()+":4030/api/v1.0/client", params, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {

                        Toast.makeText(getApplicationContext(),"Registro realizado", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), response.getString("name"), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegistrarUsuarioActivity.this, MainActivity.class);
                        startActivity(intent);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                    Toast.makeText(getApplicationContext(),"Usuario ya existente",Toast.LENGTH_SHORT);
                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(),"fill in the empty spaces", Toast.LENGTH_SHORT).show();
            confir=0;

        }
    }
}
