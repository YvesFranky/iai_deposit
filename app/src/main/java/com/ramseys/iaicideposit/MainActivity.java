
package com.ramseys.iaicideposit;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.ramseys.iaicideposit.Admin.AdminRegisterPage;
import com.ramseys.iaicideposit.Auth.RegisterPage;
import com.ramseys.iaicideposit.UserScreen.CandidatHome;
import com.ramseys.iaicideposit.UserScreen.EnrolPage;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
   CardView addGest, listGest, addCan, listCan, profil, settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addGest = findViewById(R.id.addGest);
        listGest = findViewById(R.id.listGest);
        addCan = findViewById(R.id.addCan);
        listCan = findViewById(R.id.listCan);
        profil = findViewById(R.id.profil);
        settings = findViewById(R.id.settings);


        addGest.setOnClickListener(this);
        listGest.setOnClickListener(this);
        addCan.setOnClickListener(this);
        listCan.setOnClickListener(this);
        profil.setOnClickListener(this);
        settings.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addGest:
                startActivity(new Intent(MainActivity.this, AdminRegisterPage.class));
                break;
            case R.id.listGest:
                Intent i = new Intent(MainActivity.this, CandidatHome.class);
                i.putExtra("gest", true);
                startActivity(i);
                break;
            case  R.id.addCan:
                Intent intent = new Intent(MainActivity.this, RegisterPage.class);
                intent.putExtra("idCandidat", "candidat"+ System.currentTimeMillis());
                startActivity(intent);
                break;
            case R.id.listCan:Intent i2 = new Intent(MainActivity.this, CandidatHome.class);
                i2.putExtra("gest", false);
                startActivity(i2);
                break;
            case R.id.profil:
                Toast.makeText(this, "NOn implementé", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(this, "NOn implementé", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "Non implementé", Toast.LENGTH_SHORT).show();
        }
    }
}