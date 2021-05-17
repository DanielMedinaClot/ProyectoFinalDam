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
import android.widget.ListAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class DraftFragment extends Fragment {

    public TextView pruebaDraft;

    String urlDraft = "https://data.nba.net/prod/draft/2020/draft_pick.json";

    RequestQueue queue;

    List<DraftList> drafteados;
    RecyclerView recycler;


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

        //pruebaDraft = view.findViewById(R.id.textViewDraft);
        recycler = view.findViewById(R.id.recyclerViewDraft);
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));

        queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonArrayRequest requestDraft = new JsonArrayRequest(Request.Method.GET, urlDraft, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        drafteados = new ArrayList<>();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject drafteado = response.getJSONObject(i);
                                String prospect = drafteado.getString("field_draft_prospect");
                                int number = drafteado.getInt("field_pick_number");
                                String team = drafteado.getString("field_team");
                                String position = drafteado.getString("prospect_position");
                                String college = drafteado.getString("prospect_college");
                                String country = drafteado.getString("prospect_country");
                                String height = drafteado.getString("prospect_height");
                                String weight = drafteado.getString("prospect_weight");
                                String headshot = drafteado.getString("prospect_headshot");
                                //new DownloadImageTask(fotoJugadorBuscado).execute(headshot);
                                drafteados.add(new DraftList(prospect, number,team,position, college, country, height, weight, headshot));
                            }

                            DraftAdapter draftAdapter = new DraftAdapter(drafteados);
                            recycler.setAdapter(draftAdapter);
                           /* RecyclerView recyclerView = view.findViewById(R.id.listMovieRV);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            recyclerView.setAdapter(draftAdapter);*/
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //pruebaDraft.setText(response.toString());


                        //pruebaDraft.setText("hola");
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