package com.example.freddy.foodrestaurant;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.freddy.foodrestaurant.Host.host;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class CheckInformationActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private TextView emailCheck;
    private TextView nameCheck;
    private GoogleApiClient client;
    private Button btn;
    private Context root; private host HOST =new host();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_information);

        root=this;
        emailCheck = this.findViewById(R.id.txtMailCkeck);
        nameCheck = this.findViewById(R.id.txtNameCheck);
        btn = this.findViewById(R.id.btnrederict);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( root, MainActivity.class);
                startActivity(intent);
            }
        });
        GoogleSignInOptions opt = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        client = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,opt).build(); {
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> res= Auth.GoogleSignInApi.silentSignIn(client);
        if(res.isDone()){
            GoogleSignInResult result= res.get();
            handleSignResult(result);
        }else{
            res.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignResult(googleSignInResult);
                }
            });

        }
    }

    private void handleSignResult(GoogleSignInResult result) {
        if(result.isSuccess()){
            GoogleSignInAccount account= result.getSignInAccount();
            nameCheck.setText(account.getDisplayName());
            emailCheck.setText(account.getEmail());

            RequestParams params = new RequestParams();
            params.put("name", account.getDisplayName());
            params.put("email", account.getEmail());

            Toast.makeText(root,account.getDisplayName(),Toast.LENGTH_LONG).show();
            AsyncHttpClient Client = new AsyncHttpClient();
            Client.post(HOST.getIp()+":4030/api/v1.0/registro", params, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {

                        Toast.makeText(getApplicationContext(),"Registro realizado", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), response.getString("name"), Toast.LENGTH_SHORT).show();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                    Toast.makeText(getApplicationContext(),"Usuario ya existente",Toast.LENGTH_SHORT);
                }
            });

        }else{
            goLogInScreen();

        }
    }

    private void goLogInScreen() {

        Intent intent = new Intent(root, MainActivity.class);
        startActivity(intent);
    }
    //private void logOut() {
    //}

    // private void revoke() {
    // }



    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
