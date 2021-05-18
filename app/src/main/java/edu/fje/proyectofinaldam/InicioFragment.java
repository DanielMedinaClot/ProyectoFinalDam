package edu.fje.proyectofinaldam;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.io.InputStream;
import java.net.URL;


public class InicioFragment extends Fragment {
    public TextView prueba;
    public TextView jugadorFavorito;
    public TextView equipoFavorito;
    public TextView statsJugadorFavorito;
    public ImageView fotoJugadorFavorito;
    public ImageView fotoEquipoFavorito;

    public TextView email;
    public String user;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    String urlJugadores = "https://data.nba.net/10s/prod/v1/2020/players.json";
    String urlEquipos = "https://data.nba.net/10s/prod/v1/2020/teams.json";

    String jugadorFav;
    String idJugadorFav;
    String equipoFav;
    String idEquipoFav;
    String nombre, apellido;
    String tricode;

    RequestQueue queue;
    ProgressDialog pd;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inicio, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        prueba = view.findViewById(R.id.textViewUsuario);
        jugadorFavorito = view.findViewById(R.id.textViewJugadorFavorito);
        equipoFavorito = view.findViewById(R.id.textViewEquipoFavorito);
        statsJugadorFavorito = view.findViewById(R.id.textViewStatsJugadorFav);
        fotoJugadorFavorito = view.findViewById(R.id.imageViewJugadorFav);
        fotoEquipoFavorito = view.findViewById(R.id.imageViewEquipoFav);
        email = view.findViewById(R.id.textViewEmail);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        
        cogerInfoUser();

        cogerDatosConFirebase();



    }
    public void cogerInfoUser(){
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Usuarios").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                jugadorFav = snapshot.child("jugadorFavorito").getValue().toString().toLowerCase();
                equipoFav = snapshot.child("equipoFavorito").getValue().toString().toLowerCase();
                user = snapshot.child("email").getValue().toString().toLowerCase();
                //emailUsuario.setText(user);
                jugadorFavorito.setText(jugadorFav);
                equipoFavorito.setText(equipoFav);
                separarContenidoBuscador(jugadorFav);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void cogerDatosConFirebase(){

        queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest requestJugadores = new JsonObjectRequest(Request.Method.GET, urlJugadores, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject league = response.getJSONObject("league");
                            JSONArray dataArray = league.getJSONArray("standard");

                            for(int i=0; i<dataArray.length();i++){
                                JSONObject jugador = dataArray.getJSONObject(i);
                                if(jugador.getString("lastName").toLowerCase().equals(jugadorFav) || jugador.getString("lastName").toLowerCase().equals(jugadorFav) || jugador.getString("lastName").toLowerCase().equals(apellido) && jugador.getString("firstName").toLowerCase().equals(nombre) || jugador.getString("firstName").toLowerCase().equals(jugadorFav)){     //igualar con la id luego
                                    jugadorFavorito.setText(dataArray.getString(i));
                                    idJugadorFav = jugador.getString("personId");

                                    //"https://cdn.nba.com/headshots/nba/latest/1040x760/"+idJugadorFav+".png"
                                }
                                //String firstName = standard.getString("standard");


                            }
                            //generarImagenJugador(idJugadorFav);
                            String urlFotoJug =  "https://cdn.nba.com/headshots/nba/latest/1040x760/"+idJugadorFav+".png";
                            new DownloadImageTask(fotoJugadorFavorito).execute(urlFotoJug);
                            statsJugador(idJugadorFav);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            jugadorFavorito.setText("error json");

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //error.printStackTrace();
                jugadorFavorito.setText("error volley");
            }
        }
        );
        JsonObjectRequest requestEquipos = new JsonObjectRequest(Request.Method.GET, urlEquipos, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject league = response.getJSONObject("league");
                            JSONArray dataArray = league.getJSONArray("standard");

                            for(int i=0; i<dataArray.length();i++){
                                JSONObject equipo = dataArray.getJSONObject(i);
                                if(equipo.getString("urlName").toLowerCase().equals(equipoFav) || equipo.getString("city").toLowerCase().equals(equipoFav) || equipo.getString("fullName").toLowerCase().equals(equipoFav)){
                                    equipoFavorito.setText(dataArray.getString(i));
                                    idEquipoFav = equipo.getString("teamId");
                                    tricode = equipo.getString("tricode");

                                }
                                //String firstName = standard.getString("standard");


                            }
                            String urlFotoEquipo =  "https://cdn.nba.com/logos/nba/"+idEquipoFav+"/global/L/logo.svg";
                            //Utils.fetchSvg(getContext(), urlFotoEquipo, fotoEquipoFavorito);
                            new DownloadImageTask(fotoEquipoFavorito).execute("https://d2p3bygnnzw9w3.cloudfront.net/req/202105061/tlogo/bbr/"+tricode+".png");
                            //new HttpImageRequestTask(fotoEquipoFavorito).execute(urlFotoEquipo);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            equipoFavorito.setText("error json");

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //error.printStackTrace();
                equipoFavorito.setText("error volley");
            }
        }
        );
        queue.add(requestJugadores);
        queue.add(requestEquipos);
    }

    public void statsJugador(String id) {
        String urlStatsJugador = "https://data.nba.net/10s/prod/v1/2020/players/"+id+"_profile.json";
        JsonObjectRequest requestStatsEquipos = new JsonObjectRequest(Request.Method.GET, urlStatsJugador, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject league = response.getJSONObject("league");
                            JSONObject dataArray = league.getJSONObject("standard");
                            JSONObject statsArray = dataArray.getJSONObject("stats");

                            statsJugadorFavorito.setText(statsArray.toString());
                                //String firstName = standard.getString("standard");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            statsJugadorFavorito.setText("error json");

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //error.printStackTrace();
                equipoFavorito.setText("error volley");
            }
        }
        );
        queue.add(requestStatsEquipos);

    }

    public void generarImagenJugador(String id){
        String urlFotoJug =  "https://cdn.nba.com/headshots/nba/latest/1040x760/"+id+".png";


        //fotoJugadorFavorito.;
    }




    private class HttpImageRequestTask extends AsyncTask<String, Void, Drawable> {
        ImageView imageView;

        public HttpImageRequestTask(ImageView imageView){
            this.imageView = imageView;
        }


        protected Drawable doInBackground(String...urls) {
            String urlOfImage = urls[0];
            try {

                InputStream inputStream = new URL(urlOfImage).openStream();
                SVG svg = SVGParser.getSVGFromInputStream(inputStream);
                Drawable drawable = svg.createPictureDrawable();
                return drawable;
            } catch (Exception e) {
                Log.e("InicioFragment", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Drawable drawable) {
            // Update the view
            imageView.setImageDrawable(drawable);
        }
    }

    public void separarContenidoBuscador(String contenido){
        int cantidadDeEspacios = 0;
        // Recorremos la cadena:
        for (int i = 0; i < contenido.length(); i++) {
            // Si el carácter en [i] es un espacio (' ') aumentamos el contador
            if (contenido.charAt(i) == ' ') cantidadDeEspacios++;
        }
        if(cantidadDeEspacios == 1){
            String[] arrayJugadorBuscado = contenido.split(" ");
            nombre = arrayJugadorBuscado[0];
            apellido = arrayJugadorBuscado[1];
        }else if(cantidadDeEspacios == 2){
            String[] arrayJugadorBuscado = contenido.split(" ");
            nombre = arrayJugadorBuscado[0];
            apellido = arrayJugadorBuscado[1] + " " + arrayJugadorBuscado[2];
        }
    }

}

