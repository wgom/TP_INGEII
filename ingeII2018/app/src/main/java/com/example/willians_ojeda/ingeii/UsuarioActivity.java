package com.example.willians_ojeda.ingeii;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entidades.Roles;
import entidades.Usuarios;

public class UsuarioActivity extends AppCompatActivity {

    private TextInputEditText correo;
    private Spinner spRoles;
    private Button btnGuardar;

    private String urlHttp = Constante.ipService + "/WSSGP/webresources/";
    private ProgressDialog dialogo;
    private String respStr = "";
    private List<Roles> rolesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        dialogo = new ProgressDialog(this);
        correo = findViewById(R.id.usuario_correo);
        spRoles = findViewById(R.id.sp_rol);
        btnGuardar = findViewById(R.id.btn_guardar_usuario);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ws de guardar usuario
                GuardarTask guardarTask = new GuardarTask();
                guardarTask.execute();
            }
        });


        RolesTask rolesTask = new RolesTask();
        rolesTask.execute();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(rolesList == null) {

        }
    }

    private class RolesTask extends  AsyncTask<Void, Void, Boolean> {
        AlertDialog alertDialog;
        Throwable throwable;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialogo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialogo.setMessage("Cargando roles...");
            dialogo.setCancelable(false);
            dialogo.setMax(50);
            dialogo.show();

            Log.d("prueba", "usuarioActivity: onPreExecute...");
            alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
        }


        @Override
        protected Boolean doInBackground(Void... voids) {
            HttpClient httpClient = new DefaultHttpClient();

            HttpGet del = new HttpGet(urlHttp + "inge2_sgp.entities.roles/");

            del.setHeader("content-type", "apllication/json");

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://www.yoursite.com/script.php");

            try {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("id", "12345"));
                nameValuePairs.add(new BasicNameValuePair("stringdata", "Hi"));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);

            } catch (Exception e) {
                throwable = e;
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Log.d("prueba", "usuarioActivity: onPostExecute..." + respStr);

            dialogo.dismiss();
            if (throwable != null) {
                alertDialog.setTitle("Error");
                alertDialog.setMessage(throwable.getMessage());
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int wich){
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }

            ArrayAdapter spinnerAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, rolesList);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spRoles.setAdapter(spinnerAdapter);


        }


    }



    private class GuardarTask extends AsyncTask<Void, Void, Boolean> {
        AlertDialog alertDialog;
        Throwable throwable;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogo = new ProgressDialog(getApplicationContext());
            dialogo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialogo.setMessage("Procesando...");
            dialogo.setCancelable(false);
            dialogo.setMax(50);
            dialogo.show();

            Log.d("prueba", "usuarioActivity: onPreExecute...");
            alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
        }


        @Override
        protected Boolean doInBackground(Void... voids) {
            HttpClient httpClient = new DefaultHttpClient();

            HttpGet del = new HttpGet(urlHttp+"inge2_sgp.entities.usuarios/"); //verificar

            del.setHeader("content-type", "apllication/json");

            try {
                Log.d("prueba", "execute(del)");
                HttpResponse resp = httpClient.execute(del);
            } catch (Exception e) {
                throwable = e;
                e.printStackTrace();
                Log.d("prueba", "Error:" + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Log.d("prueba", "usuarioActivity: onPostExecute..." + respStr);

            dialogo.dismiss();
            if (throwable != null) {
                alertDialog.setTitle("Error");
                alertDialog.setMessage(throwable.getMessage());
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int wich){
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        }
    }
}
