package edu.fje.proyectofinaldam;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class EquiposFragment extends Fragment {

    public TextView pruebaEquipos;
    public EditText buscadorEquipos;
    public Button buscarEquipo;

    String jugadorBuscado;
    String nombre;
    String apellido;

    RequestQueue queue;

    //String url = "https://nba-stats4.p.rapidapi.com/players/";
    //String url = "https://free-nba.p.rapidapi.com/players/";
    String url = "https://data.nba.net/10s/prod/v1/2020/teams.json";
    //String league;
    //JSONArray leagueArray;

    String equipoBuscado;
    String city;
    String fullName;
    String urlName;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_equipos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pruebaEquipos = view.findViewById(R.id.textViewEquipos);
        buscadorEquipos = view.findViewById(R.id.editTextBuscarEquipo);
        buscarEquipo = view.findViewById(R.id.btnBuscarEquipo);


        buscarEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                equipoBuscado = buscadorEquipos.getText().toString().toLowerCase();
                //separarContenidoBuscador(jugadorBuscado);



                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    JSONObject league = response.getJSONObject("league");
                                    JSONArray dataArray = league.getJSONArray("standard");
                                    //pruebaJugadores.setText(dataArray.toString());

                                    for(int i=0; i<dataArray.length();i++){
                                        JSONObject equipo = dataArray.getJSONObject(i);

                                        if(equipo.getString("urlName").toLowerCase().equals(equipoBuscado) || equipo.getString("city").toLowerCase().equals(equipoBuscado) || equipo.getString("fullName").toLowerCase().equals(equipoBuscado)){
                                            pruebaEquipos.setText(dataArray.getString(i));
                                        }

                                        //pruebaJugadores.setText(jugador.toString());
                                    }



                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    pruebaEquipos.setText("error json");

                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //error.printStackTrace();
                        pruebaEquipos.setText("error volley");
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