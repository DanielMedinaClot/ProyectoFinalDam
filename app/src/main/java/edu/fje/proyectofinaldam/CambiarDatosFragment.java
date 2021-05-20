package edu.fje.proyectofinaldam;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class CambiarDatosFragment extends Fragment {

    public EditText antiguaPassword;
    public EditText nuevaPassword;
    public EditText nuevoJugadorFavorito;
    public EditText nuevoEquipoFavorito;
    public Button btnCambiarDatos;


    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    public String antiguoJugadorFav;
    public String antiguoEquipoFav;
    public String nuevoJugadorFav;
    public String nuevoEquipoFav;

    public String id;




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

        //antiguaPassword = view.findViewById(R.id.editTextPasswordAntiguo);
        //nuevaPassword = view.findViewById(R.id.editTextPasswordNueva);
        nuevoJugadorFavorito = view.findViewById(R.id.editTextNuevoJugadorFavorito);
        nuevoEquipoFavorito = view.findViewById(R.id.editTextNuevoEquipoFavorito);
        btnCambiarDatos = view.findViewById(R.id.btnCambiarDatos);


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        id = mAuth.getCurrentUser().getUid();
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

        btnCambiarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nuevoJugadorFav = nuevoJugadorFavorito.getText().toString();
                nuevoEquipoFav = nuevoEquipoFavorito.getText().toString();

                if(TextUtils.isEmpty(nuevoEquipoFav)){
                    nuevoEquipoFavorito.setError("Equipo favorito necesario");
                    return;
                }
                if(TextUtils.isEmpty(nuevoJugadorFav)){
                    nuevoJugadorFavorito.setError("Juagador Favorito necesario");
                    return;
                }

                cambiarDatos();
            }
        });



    }
    public void cambiarDatos(){
        Map<String, Object> datosMap = new HashMap<>();
        datosMap.put("equipoFavorito",nuevoEquipoFav);
        datosMap.put("jugadorFavorito",nuevoJugadorFav);

        mDatabase.child("Usuarios").child(id).updateChildren(datosMap).addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task2) {
                if (task2.isSuccessful()) {
                    Toast.makeText(getActivity(), "Datos Cambiados.",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "Error database.",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });;
    }

    /*

*/
}