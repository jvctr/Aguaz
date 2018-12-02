package com.example.victor.agrvai.Activities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.victor.agrvai.Conf_Firebase.ConfigFirebase;
import com.example.victor.agrvai.Entidades.Usuarios;
import com.example.victor.agrvai.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    private EditText edtEmail;
    private EditText edtSenha;
    private Button btnLogar;
    private Button btnCadastr;
    private FirebaseAuth autenticacao;
    private Usuarios usuarios;

    ConstraintLayout myLayout;
    AnimationDrawable animationDrawable;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConstraintLayout constraintLayout = findViewById(R.id.myLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        myLayout = (ConstraintLayout) findViewById(R.id.myLayout);
        animationDrawable = (AnimationDrawable) myLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtSenha = (EditText)findViewById(R.id.edtSenha);
        btnLogar = (Button) findViewById(R.id.btnLogar);
        btnCadastr = (Button)findViewById(R.id.btnCadastr);


        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtEmail.getText().toString().equals("") && !edtSenha.getText().toString().equals("")){

                    usuarios = new Usuarios();
                    usuarios.setEmail(edtEmail.getText().toString());
                    usuarios.setSenha(edtSenha.getText().toString());

                    validLogin();
                } else {
                    Toast.makeText(MainActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCadastr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreCadastroUsuario();
            }
        });


        }
        private void validLogin() {

        autenticacao = ConfigFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(usuarios.getEmail(), usuarios.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                    abrirTelaPrincipal();
                   }
        });

    }

    public void abrirTelaPrincipal() {
        Intent intentAbrirTelaPrincipal = new Intent(MainActivity.this, tela01.class);
        startActivity(intentAbrirTelaPrincipal);

    }

    public void abreCadastroUsuario() {
        Intent intent = new Intent(MainActivity.this, TelaCadastro.class);
        startActivity(intent);
        }
}
