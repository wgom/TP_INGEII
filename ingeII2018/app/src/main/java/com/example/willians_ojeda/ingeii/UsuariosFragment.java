package com.example.willians_ojeda.ingeii;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import entidades.Usuarios;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsuariosFragment extends Fragment {
    private Button btnAgregar;
    private ProgressDialog dialogo;

    private final String urlHttp = Constante.ipService+"/WSSGP/webresources/inge2_sgp.entities.usuarios/";

    private RecyclerView recycler;

    String respStr = "";
    UsuariosFragment usuariosFragment;


    public UsuariosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_usuarios, container, false);

        recycler = view.findViewById(R.id.recyclerUsuario);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(linearLayoutManager);

        btnAgregar = view.findViewById(R.id.btn_agregar_usuario);
        dialogo = new ProgressDialog(view.getContext());


        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity()
                        , UsuarioActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        usuariosFragment = this;
        Consultar consultar = new Consultar();
        consultar.execute();
    }

    public void setListUsuarios(List<Usuarios> usuariosList) {
        UsuarioAdapter usuarioAdapter = new UsuarioAdapter(
                usuariosList, getContext(), usuariosFragment);

        recycler.setAdapter(usuarioAdapter);
    }

    private class Consultar extends AsyncTask<Void , Void, Boolean> {
        AlertDialog alertDialog;
        Throwable throwable;
        ArrayList<Usuarios> usuariosList = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialogo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialogo.setMessage("Cargan...");
            dialogo.setCancelable(false);
            dialogo.setMax(50);
            dialogo.show();

            Log.d("prueba", "usuariofragment: onPreExecute...");
            alertDialog = new AlertDialog.Builder(getContext()).create();
        }


        @Override
        protected Boolean doInBackground(Void... voids) {
            Log.d("prueba", "usuariofragment: doInBackground...");
            Log.d("prueba", "usuarioFragment: conectarRest" + urlHttp );
            HttpClient httpClient = new DefaultHttpClient();

            HttpGet del = new HttpGet(urlHttp);

            del.setHeader("content-type","apllication/json");

            try {
                Log.d("prueba", "execute(del)");
                HttpResponse resp = httpClient.execute(del);

                if(resp.getEntity() != null) {
                    respStr = EntityUtils.toString(resp.getEntity());
                    Log.d("prueba", "respStr:" + respStr);


                    JSONArray jsonArray = new JSONArray(respStr);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        Gson gson = new GsonBuilder().create();
                        Usuarios u = gson.fromJson(object.toString(), Usuarios.class);

                        usuariosList.add(u);
                    }
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
            Log.d("prueba", "usuariofragment: onPostExecute..." + respStr);

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

            setListUsuarios(usuariosList);
        }

    }


}
