package com.ramseys.iaicideposit.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ramseys.iaicideposit.MainActivity;
import com.ramseys.iaicideposit.R;

public class LoginPage extends AppCompatActivity implements View.OnClickListener{
    Button login;
    TextView register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        login = findViewById(R.id.login);
        register = findViewById(R.id.signUp);


        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.signUp:
                startActivity(new Intent(this, RegisterPage.class));
                finish();
                break;
            default:
                startActivity(new Intent(this, MainActivity.class));
        }
    }
}