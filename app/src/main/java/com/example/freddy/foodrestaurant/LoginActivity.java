package com.example.freddy.foodrestaurant;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.freddy.foodrestaurant.Host.host;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private int GOOGLE_CODE=11267;
    private EditText Name;
    private  EditText Password;
    private TextView Info;
    private Button Login;
    //thirActivity
    private Button Register;
    private String eemail2;
    private String pw;
    private Button property;
    private GoogleApiClient client;

    private Button verAuncios;
    private Button Binmuebles;

    //HOST
    private host HOST = new host();


    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //google client
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        client = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, options)
                .build();
        loadcomponents();

        Name = findViewById(R.id.etemail);
        Password = findViewById(R.id.etPassword);
        Login = findViewById(R.id.btnLogin);
        Register = findViewById(R.id.btnRegister); //button activity thirdActivity

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadInitialRestData(Name.getText().toString(), Password.getText().toString());

            }

        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, RegistrarUsuarioActivity.class);
                startActivity(intent);

            }
        });




        //call



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();




            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==GOOGLE_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
            //if(result.isSuccess()){
            //   Toast.makeText(this,"ok",Toast.LENGTH_LONG).show();
            //}else {
            //    Toast.makeText(this,"error",Toast.LENGTH_LONG).show();

        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            CheckInformation();

        } else {
            Toast.makeText(this," Binvenido",Toast.LENGTH_LONG).show();

            Intent intent = new Intent(LoginActivity.this, RegistrarRestauranteActivity.class);
            startActivity(intent);
        }
    }
    private void CheckInformation(){
        Intent intent= new Intent(this, CheckInformationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
    private void loadcomponents() {
        SignInButton googlebtn = (SignInButton) this.findViewById(R.id.googlebutton);
        googlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(client);
                startActivityForResult(intent,GOOGLE_CODE);

            }
        });

    }


    private void loadInitialRestData(String email, String pass) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(HOST.getIp()+":4030/api/v1.0/login/"+email+"="+pass, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {

                    JSONArray list =  (JSONArray)response.get("email");
                    for (int i = 0; i < list.length();i++){
                        JSONObject itenJosn =list.getJSONObject(i);
                        String email1 = itenJosn.getString("email");
                        String pssw = itenJosn.getString("password");

                        Toast.makeText(getApplicationContext(),"Welcome", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;

                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse)  {
                Toast toast1 = Toast.makeText(getApplicationContext(), "verifique su email o password", Toast.LENGTH_SHORT);
                toast1.show();
            }


        });

        Password.setText("");
    }


    //login google
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


}
