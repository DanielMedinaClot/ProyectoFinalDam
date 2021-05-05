package edu.fje.proyectofinaldam;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthMultiFactorException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.MultiFactorResolver;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private EditText editTextUsuario;
    private EditText editTextContrasena;
    private EditText editTextEquipoFavorito;
    private EditText editTextJugadorFavorito;
    private Button btnRegister;
    private TextView textViewLogin;

    private String usuario = "";
    private String contrasena = "";
    private String equipoFavorito = "";
    private String jugadorFavorito = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        editTextUsuario = (EditText) findViewById(R.id.editTextUsuario);
        editTextContrasena = (EditText) findViewById(R.id.editTextContraseña);
        editTextEquipoFavorito = (EditText) findViewById(R.id.editTextEquipoFavorito);
        editTextJugadorFavorito = (EditText) findViewById(R.id.editTextJugadorFavorito);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        textViewLogin = (TextView) findViewById(R.id.textViewLogin);
    }

    public void registro(View v){
        usuario = editTextUsuario.getText().toString();
        contrasena = editTextContrasena.getText().toString();
        equipoFavorito = editTextEquipoFavorito.getText().toString();
        jugadorFavorito = editTextJugadorFavorito.getText().toString();
        if(TextUtils.isEmpty(usuario)){
            editTextUsuario.setError("Usuario necesario");
            return;
        }
        if(TextUtils.isEmpty(contrasena)){
            editTextContrasena.setError("Contraseña necesaria");
            return;
        }
        if(TextUtils.isEmpty(equipoFavorito)){
            editTextEquipoFavorito.setError("Equipo favorito necesario");
            return;
        }
        if(TextUtils.isEmpty(jugadorFavorito)){
            editTextJugadorFavorito.setError("Juagador Favorito necesario");
            return;
        }
        if(contrasena.length() < 6){
            editTextContrasena.setError("Contraseña mínimo de 6 carácteres");
            return;
        }

        registrarUsuario();

    }

    public void registrarUsuario(){

        mAuth.createUserWithEmailAndPassword(usuario, contrasena).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("email", usuario);
                    map.put("contrasena", contrasena);
                    map.put("equipoFavorito", equipoFavorito);
                    map.put("jugadorFavorito", jugadorFavorito);

                    String id = mAuth.getCurrentUser().getUid();

                    mDatabase.child("Usuarios").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {

                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()) {
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(RegisterActivity.this, "Error failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


                    //updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(RegisterActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    //updateUI(null);
                };
            };
        });
    }

    public void login(View v){
        Intent intentLogin = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intentLogin);
    }



    public void jugar(View v){
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void goLogin(View v){
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}