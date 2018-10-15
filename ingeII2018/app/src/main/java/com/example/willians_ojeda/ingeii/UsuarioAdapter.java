package com.example.willians_ojeda.ingeii;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import entidades.Usuarios;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.Item> {

    private List<Usuarios> list;
    private Context context;
    private final String urlHttp = "http://192.168.0.12:8080/WSSGP/webresources/inge2_sgp.entities.usuarios/";
    String respStr = "";
    private ProgressDialog dialogo;

    public UsuarioAdapter(List<Usuarios> list, Context context) {
        this.list = list;
        this.context = context;
        dialogo = new ProgressDialog(context);
    }

    @NonNull
    @Override
    public Item onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_usuarios ,viewGroup,false);
        return new UsuarioAdapter.Item(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Item item, final int i) {
        item.usuario.setText(list.get(i).getUsuario());
        item.editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        item.eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Eliminar eliminar = new Eliminar(list.get(i));
                eliminar.execute();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }else {
            return 0;
        }
    }

    public class Item extends RecyclerView.ViewHolder{

        TextView usuario;
        ImageButton editar;
        ImageButton eliminar;

        public Item(@NonNull View itemView) {
            super(itemView);
            usuario = itemView.findViewById(R.id.usuario_item);
            editar = itemView.findViewById(R.id.editar_item);
            eliminar = itemView.findViewById(R.id.eliminar_item);
        }
    }

    private class Eliminar extends AsyncTask<Void , Void, Boolean> {
        AlertDialog alertDialog;
        Throwable throwable;
        Usuarios u;

        public Eliminar(Usuarios u) {
            this.u = u;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialogo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialogo.setMessage("Cargan...");
            dialogo.setCancelable(false);
            dialogo.setMax(50);
            dialogo.show();

            Log.d("prueba", "usuarioAdaper: onPreExecute...");
            alertDialog = new AlertDialog.Builder(context).create();
        }


        @Override
        protected Boolean doInBackground(Void... voids) {
            Log.d("prueba", "usuarioAdaper: doInBackground...");
            Log.d("prueba", "usuarioAdapter: conectarRest" + urlHttp+u.getUsuario() );
            HttpClient httpClient = new DefaultHttpClient();

            HttpDelete del = new HttpDelete(urlHttp+u.getUsuario());

            del.setHeader("content-type","apllication/json");

            try {
                Log.d("prueba", "execute(del)");
                HttpResponse resp = httpClient.execute(del);


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
            Log.d("prueba", "usuarioAdaper: onPostExecute..." + respStr);

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
            }else{
                Toast.makeText(context, "Eliminado", Toast.LENGTH_SHORT).show();
            }
           
        }

    }
}
