package com.example.ilyapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;

import com.example.ilyapp.R;
import com.example.ilyapp.api.ApiInterface;
import com.example.ilyapp.api.ApiServer;
import com.example.ilyapp.model.ResponseLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity {

    String textUser, textPass;
    EditText user, pass;
    AppCompatButton btnlogin, btnRegis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        btnlogin = findViewById(R.id.login);
        btnRegis = findViewById(R.id.btnRegis);

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegistrasiActivity.class));
            }
        });


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textUser = user.getText().toString();
                textPass = pass.getText().toString();

                if (textUser.equals("")){
                    user.setError("Mohon Diisi");
                }else if(textPass.equals("")){
                    pass.setError("Mohon Diisi");
                }else {
                    moveToLogin(textUser, textPass);
                }
            }
        });
    }

    private void moveToLogin(String textUser, String textPass) {
        ApiInterface apiInterface = ApiServer.getClient().create(ApiInterface.class);
        Call<ResponseLogin> responseLoginCall = apiInterface.login(textUser, textPass);
        responseLoginCall.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if (response.isSuccessful()){
                    startActivity(new Intent(getApplicationContext(), RegistrasiActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {

            }
        });
    }

}