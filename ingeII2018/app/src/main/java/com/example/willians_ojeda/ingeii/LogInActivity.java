package com.example.willians_ojeda.ingeii;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import entidades.Usuarios;


public class LogInActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient googleApiClient;

    private SignInButton signInButton;

    private String mail;
    private String rest_mail;

    private final String urlHttp = "http://192.168.0.12:8080/WSSGP/webresources/inge2_sgp.entities.usuarios/";

    private ProgressDialog dialogo;

    public static final int SIGN_IN_CODE = 777;
    String respStr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        dialogo = new ProgressDialog(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signInButton = (SignInButton) findViewById(R.id.signInButton);

        signInButton.setSize(SignInButton.SIZE_WIDE);

        signInButton.setColorScheme(SignInButton.COLOR_DARK);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("prueba", "logueo...");
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, SIGN_IN_CODE);
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("prueba", "onActivityResult: "+requestCode+ " - "+SIGN_IN_CODE);
        if (requestCode == SIGN_IN_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            mail = account.getEmail();
/*
            conectarRest();
*/

            Log.d("prueba", "handleSignInResult");
            Consultar consultar = new Consultar();
            consultar.execute();

            /*goMainScreen();*/
        } else {
            Log.d("prueba", "result != success");
            Toast.makeText(this, R.string.not_log_in, Toast.LENGTH_SHORT).show();
        }
    }

    private void conectarRest() {


        /*
        HttpClient client = new HttpClient(new OnHttpRequestComplete() {

            @Override
            public void onComplete(Response status) {
                //wgom
                Log.d("prueba", "status: "+status.getResult());
                if (status.isSuccess()) {

                    Gson gson = new GsonBuilder().create();
                    //Usuarios u = gson.fromJson(status.getResult(), Usuarios.class);
                    Toast.makeText(LogInActivity.this, status.getResult(), Toast.LENGTH_SHORT).show();

                    rest_mail = "";
                    if(status.getResult().trim().isEmpty() || status.getResult() == null){
                        rest_mail = "";
                        Toast.makeText(LogInActivity.this, R.string.not_account, Toast.LENGTH_SHORT).show();
                    }else {
                        //rest_mail = u.getUsuario();
                    }

                };
            }
        });
        Log.d("prueba","URL: " +urlHttp+mail);
        client.excecute(urlHttp+mail);

        */
    }

    private void goMaindeneg() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()) {

                } else {
                    Toast.makeText(getApplicationContext(), R.string.not_close_session, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void goMainScreen() {
        Intent intent = new Intent(this, PrincipalActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private class Consultar extends AsyncTask <Void , Void, Boolean>{
        AlertDialog alertDialog;
        Throwable throwable;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialogo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialogo.setMessage("Cargan...");
            dialogo.setCancelable(false);
            dialogo.setMax(50);
            dialogo.show();

            Log.d("prueba", "onPreExecute...");
            alertDialog = new AlertDialog.Builder(LogInActivity.this).create();
        }


        @Override
        protected Boolean doInBackground(Void... voids) {
            Log.d("prueba", "doInBackground...");
            Log.d("prueba", "conectarRest" + urlHttp+mail );
            HttpClient httpClient = new DefaultHttpClient();

            HttpGet del = new HttpGet(urlHttp+mail);

            del.setHeader("content-type","apllication/json");

            try {
                Log.d("prueba", "execute(del)");
                HttpResponse resp = httpClient.execute(del);
            if (resp.getEntity() != null) {
                Log.d("prueba", "restpnotnull:");
                respStr = EntityUtils.toString(resp.getEntity());
                Log.d("prueba", "respStr:" + respStr);
                Gson gson = new GsonBuilder().create();
                Usuarios u = gson.fromJson(respStr, Usuarios.class);
            }else{
                throw new Exception("No se encuentra registrado en el Sistema");
            }



            } catch (Exception e) {
                throwable = e;
                e.printStackTrace();
                Log.d("prueba", "Error:" + e.getMessage() );
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Log.d("prueba", "onPostExecute..." + respStr);

            dialogo.dismiss();
            if (throwable != null) {
                alertDialog.setTitle("Información");
                alertDialog.setMessage(throwable.getMessage());
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int wich){
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }

            if (respStr != null && !respStr.isEmpty()){
                goMainScreen();
            }else {
                goMaindeneg();
            }

        }

    }
}


