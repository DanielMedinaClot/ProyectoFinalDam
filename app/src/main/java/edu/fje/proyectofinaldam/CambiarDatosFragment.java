package edu.fje.proyectofinaldam;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CambiarDatosFragment extends Fragment {

    public EditText antiguaPassword;
    public EditText nuevaPassword;
    public EditText nuevoJugadorFavorito;
    public EditText nuevoEquipoFavorito;


    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    public String antiguoJugadorFav;
    public String antiguoEquipoFav;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cambiar_datos, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        antiguaPassword = view.findViewById(R.id.editTextPasswordAntiguo);
        nuevaPassword = view.findViewById(R.id.editTextPasswordNueva);
        nuevoJugadorFavorito = view.findViewById(R.id.editTextNuevoJugadorFavorito);
        nuevoEquipoFavorito = view.findViewById(R.id.editTextNuevoEquipoFavorito);


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Usuarios").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                antiguoJugadorFav = snapshot.child("jugadorFavorito").getValue().toString();
                antiguoEquipoFav = snapshot.child("equipoFavorito").getValue().toString();
                //emailUsuario.setText(user);
                nuevoJugadorFavorito.setText(antiguoJugadorFav);
                nuevoEquipoFavorito.setText(antiguoEquipoFav);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}