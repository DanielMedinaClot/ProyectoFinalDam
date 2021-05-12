package edu.fje.proyectofinaldam;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class DraftFragment extends Fragment {

    public TextView pruebaDraft;

    String urlDraft = "https://data.nba.net/prod/draft/2020/draft_pick.json";

    RequestQueue queue;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_draft, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pruebaDraft = view.findViewById(R.id.textViewDraft);


        JsonArrayRequest requestDraft = new JsonArrayRequest(urlDraft,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {


                            /*
                        for(int i = 0; i<response.length();i++){
                            JSONObject drafteado = response.getJSONObject(i);

                        }


                            for(int i=0; i<response.length();i++){
                                JSONObject drafteado = response.getJSONObject(i);
                                String prospect = drafteado.getString("field_draft_prospect");
                                //nombresRoster(jugadorIdRoster);
                                pruebaDraft.setText(prospect);

                                //pruebaJugadores.setText(jugador.toString());
                            }

                             */
                        pruebaDraft.setText("hola");
                            //pruebaDraft.setText(response.toString());

                            //String firstName = standard.getString("standard");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //error.printStackTrace();
                pruebaDraft.setText("error volley");
            }
        }
        );
        queue.add(requestDraft);



    }
}