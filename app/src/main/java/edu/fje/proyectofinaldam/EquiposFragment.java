package edu.fje.proyectofinaldam;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.List;


public class EquiposFragment extends Fragment {

    public TextView pruebaEquipos;
    public TextView rosterEquipo;
    public EditText buscadorEquipos;
    public Button buscarEquipo;
    public ImageView fotoEquipo;
    public ArrayList<String> arrayJugadoresRoster = new ArrayList<String>();


    String teamId;
    String city;
    String fullName;
    String confName;
    String divName;

    RequestQueue queue;

    //String url = "https://nba-stats4.p.rapidapi.com/players/";
    //String url = "https://free-nba.p.rapidapi.com/players/";
    String url = "https://data.nba.net/10s/prod/v1/2020/teams.json";
    String urlJug = "https://data.nba.net/10s/prod/v1/2020/players.json";
    //String league;
    //JSONArray leagueArray;

    String equipoBuscado;
    String urlName;
    String tricode;

    String temporaryDisplayName, jersey , pos, nbaDebutYear, country;
    List<JugadoresList> players;
    RecyclerView recycler;


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
        //rosterEquipo = view.findViewById(R.id.textViewRosterEquipo);
        fotoEquipo = view.findViewById(R.id.imageViewFotoEquipo);

        recycler = view.findViewById(R.id.recyclerViewEquipoRoster);
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));

        players = new ArrayList<>();

        buscarEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                equipoBuscado = buscadorEquipos.getText().toString().toLowerCase();
                //separarContenidoBuscador(jugadorBuscado);

                players.clear();

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
                                            teamId = equipo.getString("teamId");
                                            tricode = equipo.getString("tricode");
                                            rosterEquipoBuscado(teamId);
                                        }

                                        //pruebaJugadores.setText(jugador.toString());
                                    }
                                    if(tricode.equals("BKN")){
                                        tricode = "NJN";
                                    }else if(tricode.equals("NOP")){
                                        tricode = "NOH";
                                    }else if(tricode.equals("PHX")){
                                        tricode = "PHO";
                                    }
                                    new DownloadImageTask(fotoEquipo).execute("https://d2p3bygnnzw9w3.cloudfront.net/req/202105061/tlogo/bbr/"+tricode+".png");


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

    public void rosterEquipoBuscado(String id){
        String urlRosterEquipo = "https://data.nba.net/prod/v1/2020/teams/"+id+"/roster.json" ;
        JsonObjectRequest requestRosterEquipo = new JsonObjectRequest(Request.Method.GET, urlRosterEquipo, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject league = response.getJSONObject("league");
                            JSONObject dataArray = league.getJSONObject("standard");
                            JSONArray rosterArray = dataArray.getJSONArray("players");

                            for(int i=0; i<rosterArray.length();i++){
                                JSONObject jugador = rosterArray.getJSONObject(i);
                                String jugadorIdRoster = jugador.getString("personId");
                                nombresRoster(jugadorIdRoster);

                                //pruebaJugadores.setText(jugador.toString());
                            }
                            //rosterEquipo.setText(rosterArray.toString());
                            //String firstName = standard.getString("standard");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            rosterEquipo.setText("error json");

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //error.printStackTrace();
                rosterEquipo.setText("error volley");
            }
        }
        );
        queue.add(requestRosterEquipo);
    }

    public void nombresRoster(String id){

        JsonObjectRequest requestNombresRoster = new JsonObjectRequest(Request.Method.GET, urlJug, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject league = response.getJSONObject("league");
                            JSONArray dataArray = league.getJSONArray("standard");
                            //pruebaJugadores.setText(dataArray.toString());

                            for(int i=0; i<dataArray.length();i++){
                                JSONObject jugador = dataArray.getJSONObject(i);

                                if(jugador.getString("personId").toLowerCase().equals(id)){
                                    temporaryDisplayName = jugador.getString("temporaryDisplayName");
                                    jersey = jugador.getString("jersey");
                                    pos = jugador.getString("pos");
                                    nbaDebutYear = jugador.getString("nbaDebutYear");
                                    country = jugador.getString("country");

                                    players.add(new JugadoresList(temporaryDisplayName, jersey, pos, nbaDebutYear, country));
                                    //arrayJugadoresRoster.add(temporaryDisplayName);
                                }

                            }
                            //rosterEquipo.setText(arrayJugadoresRoster.toString());
                            JugadoresAdapter playersAdapter = new JugadoresAdapter(players);
                            recycler.setAdapter(playersAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            rosterEquipo.setText("error json");

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //error.printStackTrace();
                rosterEquipo.setText("error volley");
            }
        }
        );
        queue.add(requestNombresRoster);
    }

}