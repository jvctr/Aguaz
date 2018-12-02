package com.example.victor.agrvai.Activities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.victor.agrvai.Conf_Firebase.ConfigFirebase;
import com.example.victor.agrvai.Entidades.Base64Custom;
import com.example.victor.agrvai.Entidades.Usuarios;
import com.example.victor.agrvai.Helper.Preferencias;
import com.example.victor.agrvai.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class TelaCadastro extends AppCompatActivity {

    private EditText edtCadEmail;
    private EditText edtCadNome;
    private EditText edtCadSobrenome;
    private EditText edtCadSenha;
    private EditText edtCadConfirmarSenha;
    private EditText edtCaddataNascimento;
    private RadioButton rbMasculino;
    private RadioButton rbFeminino;
    private Button btnCadastro;
    private Usuarios usuarios;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);

        ConstraintLayout constraintLayout = findViewById(R.id.myLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        edtCadEmail = (EditText)findViewById(R.id.edtCadEmail);
        edtCadNome = (EditText)findViewById(R.id.edtNome);
        edtCadSobrenome = (EditText)findViewById(R.id.edtCadSobrenome);
        edtCadSenha = (EditText)findViewById(R.id.edtCadSenha);
        edtCadConfirmarSenha = (EditText)findViewById(R.id.edtCadConfirmarSenha);
        edtCaddataNascimento = (EditText)findViewById(R.id.edtCaddataNascimento);
        rbMasculino = (RadioButton)findViewById(R.id.rbMasculino);
        rbFeminino = (RadioButton)findViewById(R.id.rbFeminino);
        btnCadastro = (Button)findViewById((R.id.btnCadastro));

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtCadSenha.getText().toString().equals(edtCadConfirmarSenha.getText().toString())){
                    usuarios = new Usuarios();
                    usuarios.setNome(edtCadNome.getText().toString());
                    usuarios.setSobrenome(edtCadSobrenome.getText().toString());
                    usuarios.setEmail(edtCadEmail.getText().toString());
                    usuarios.setSenha(edtCadSenha.getText().toString());
                    usuarios.setDataNascimento(edtCaddataNascimento.getText().toString());

                    if (rbMasculino.isChecked()){
                        usuarios.setSexo("Masculino");
                    } else {
                        usuarios.setSexo("Feminino");
                    }
                    cadastrarUsuario();

                } else {
                    Toast.makeText(TelaCadastro.this, "Senhas não conferem", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
    private void cadastrarUsuario() {
        autenticacao = ConfigFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuarios.getEmail(),
                usuarios.getSenha()
        ).addOnCompleteListener(TelaCadastro.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(TelaCadastro.this, "Usuário cadastrado com sucesso", Toast.LENGTH_LONG).show();

                    String identificadorUsuario = Base64Custom.codificarBase64(usuarios.getEmail());
                    FirebaseUser usuarioFirebase = task.getResult().getUser();
                    usuarios.setId(identificadorUsuario);
                    usuarios.salvar();

                    Preferencias preferencias = new Preferencias(TelaCadastro.this);
                    preferencias.salvarUsuarioPreferencias(identificadorUsuario, usuarios.getNome());

                    abrirLoginUsuario();

                } else {

                    String erroExcecao = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        erroExcecao = "Digite uma senha mais forte (mínimo 8 caracteres)";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExcecao = "O e-mail digitado é inválido, digite um outro e-mail";
                    } catch (FirebaseAuthUserCollisionException e) {
                        erroExcecao = "E-mail já cadastrado no sistema";
                    } catch (Exception e) {
                        erroExcecao = "Erro ao efetuar o cadastro";
                        e.printStackTrace();
                    }
                    Toast.makeText(TelaCadastro.this, "Erro: " + erroExcecao, Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    public void abrirLoginUsuario() {
        Intent intent = new Intent(TelaCadastro.this, tela01.class);
        startActivity(intent);
        finish();
    }

}
