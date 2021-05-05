package edu.fje.proyectofinaldam;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class InicioFragment extends Fragment {
    public TextView prueba;
    public TextView jugadorFavorito;
    public TextView equipoFavorito;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;




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

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        
        cogerInfoUser();

        String url = "https://nba-stats4.p.rapidapi.com/players/";

//create post data as JSONObject - if your are using JSONArrayRequest use obviously an JSONArray :)
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        prueba.setText(response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        prueba.setText("error");
                    }
                }
        ) {        //this is the part, that adds the header to the request
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("x-rapidapi-key", "b0030df614msh86958735e39a051p11a017jsn3f3b87480909");
                params.put("x-rapidapi-host","nba-stats4.p.rapidapi.com");
                return params;
            }
        };

// Add the request to the queue
        queue.add(getRequest);

    }
    public void cogerInfoUser(){
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Usuarios").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String jugadorFav = snapshot.child("jugadorFavorito").getValue().toString();
                String equipoFav = snapshot.child("equipoFavorito").getValue().toString();
                //emailUsuario.setText(user);
                jugadorFavorito.setText(jugadorFav);
                equipoFavorito.setText(equipoFav);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}