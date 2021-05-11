package edu.fje.proyectofinaldam;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class JugadoresFragment extends Fragment {

    public TextView pruebaJugadores;
    public EditText buscadorJugadores;
    public Button buscarJugador;

    RequestQueue queue;

    //String url = "https://nba-stats4.p.rapidapi.com/players/";
    //String url = "https://free-nba.p.rapidapi.com/players/";
    String url = "https://data.nba.net/10s/prod/v1/2020/players.json";
    //String league;
    //JSONArray leagueArray;

    String jugadorBuscado;
    String nombre;
    String apellido;

    String temporaryDisplayName;
    String jersey;
    String pos;
    String heightMeters;
    String weightKilograms;
    String dateOfBirth;
    String country;
    String teamId, personId;
    String draftTeamId, pickNum, roundNum, seasonYear;
    String collegeName;
    String nbaDebutYear;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_jugadores, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pruebaJugadores = view.findViewById(R.id.textViewJugadores);
        buscadorJugadores = view.findViewById(R.id.editTextBuscarJugador);
        buscarJugador = view.findViewById(R.id.btnBuscarJugador);



        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        ArrayList<String> a1 = new ArrayList<String>();


/*
//create post data as JSONObject - if your are using JSONArrayRequest use obviously an JSONArray :)
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        JsonObjectRequest getRequest = new JsonObjectRequest( Request.Method.GET, url, json,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        a1.add(response.toString());

                            pruebaJugadores.setText(response.toString());



                        // pruebaJugadores.setText(a1.toArray()[iu].toString());

                    }

                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        pruebaJugadores.setText("error");
                    }
                }
        ){        //this is the part, that adds the header to the request
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("x-rapidapi-key", "b0030df614msh86958735e39a051p11a017jsn3f3b87480909");
                //params.put("x-rapidapi-host","nba-stats4.p.rapidapi.com");
                params.put("x-rapidapi-host","free-nba.p.rapidapi.com");
                return params;
            }
        };


// Add the request to the queue
        queue.add(getRequest);

*/

    buscarJugador.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            jugadorBuscado = buscadorJugadores.getText().toString().toLowerCase();
            separarContenidoBuscador(jugadorBuscado);



            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                JSONObject league = response.getJSONObject("league");
                                JSONArray dataArray = league.getJSONArray("standard");
                                //pruebaJugadores.setText(dataArray.toString());

                                for(int i=0; i<dataArray.length();i++){
                                    JSONObject jugador = dataArray.getJSONObject(i);

                                    if(jugador.getString("lastName").toLowerCase().equals(apellido) && jugador.getString("firstName").toLowerCase().equals(nombre) || jugador.getString("lastName").toLowerCase().equals(jugadorBuscado)){
                                        pruebaJugadores.setText(dataArray.getString(i));

                                        temporaryDisplayName = jugador.getString("temporaryDisplayName");
                                        jersey = jugador.getString("jersey");
                                        pos = jugador.getString("pos");
                                        heightMeters = jugador.getString("heightMeters");
                                        weightKilograms = jugador.getString("weightKilograms");
                                        dateOfBirth = jugador.getString("dateOfBirthUTC");
                                        country = jugador.getString("country");
                                        teamId = jugador.getString("teamId");
                                        personId = jugador.getString("personId");
                                        /*

                                        draftTeamId = jugador.getString("teamId");
                                        pickNum = jugador.getString("pickNum");
                                        roundNum = jugador.getString("roundNum");
                                        seasonYear = jugador.getString("seasonYear");

                                         */
                                        collegeName = jugador.getString("collegeName");
                                        nbaDebutYear = jugador.getString("nbaDebutYear");



                                    }

                                    //pruebaJugadores.setText(jugador.toString());
                                }



                            } catch (JSONException e) {
                                e.printStackTrace();
                                pruebaJugadores.setText("error json");

                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //error.printStackTrace();
                    pruebaJugadores.setText("error volley");
                }
            }
            );
            queue.add(request);
        }
    });

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
        }
    }




}