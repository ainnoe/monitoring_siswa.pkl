package com.example.ilyapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ilyapp.R;
import com.example.ilyapp.api.ApiInterface;
import com.example.ilyapp.api.ApiServer;
import com.example.ilyapp.model.ResponseLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrasiActivity extends AppCompatActivity {
    String user, pass, confirmpass;
    EditText username, password, confirmPassword;
    Button login, registrasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        username        = findViewById(R.id.edt_username);
        password        = findViewById(R.id.edt_psswd);
        confirmPassword = findViewById(R.id.edt_cnfrm);
        login           = findViewById(R.id.btn_lgn);
        registrasi      = findViewById(R.id.btn_msk);

        registrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = username.getText().toString();
                pass = password.getText().toString();
                confirmpass = confirmPassword.getText().toString();

                if (confirmpass.equals(pass)){
                    moveToRegister(user, pass);
                }else{
                    password.setError("Password tidak sama");
                }
            }
        });


        user            = username.getText().toString();
        pass            = password.getText().toString();
        confirmpass     = confirmPassword.getText().toString();

        if (confirmpass.equals(pass)){
            moveToRegister(user, pass);

        }else{
            password.setError("Password Tidak Sama");
        }

    }

    private void moveToRegister(String user, String pass) {
        ApiInterface apiInterface = ApiServer.getClient().create(ApiInterface.class);
        Call<ResponseLogin> call = apiInterface.register(user, pass);
        call.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Berhasil Mendaftar", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), login.class));
                }else{
                    Toast.makeText(getApplicationContext(), "Gagal Mendaftar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}