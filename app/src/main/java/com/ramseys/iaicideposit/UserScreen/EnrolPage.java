package com.ramseys.iaicideposit.UserScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ramseys.iaicideposit.R;

public class EnrolPage extends AppCompatActivity implements View.OnClickListener {
    private Button next;
    private EditText demande, diplome, acteNaiss, cni;

    private Uri pdfData;
    private  final int REQ =1;
    String donwloadUri = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrol_page);

        //Connexion des composant avec l'interface #Ramseys

        next = findViewById(R.id.next);
        demande = findViewById(R.id.demande);
        diplome = findViewById(R.id.diplome);
        acteNaiss = findViewById(R.id.acteNaissance);
        cni = findViewById(R.id.cni);

        next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.next:
                startActivity(new Intent(this, EnrolPage2.class));
                break;
            case R.id.demande:
                break;
            case R.id.diplome:
                break;
            case R.id.acteNaissance:
                break;
            case R.id.cni:
                break;
            default:
                Toast.makeText(this, "Oups", Toast.LENGTH_SHORT).show();
        }

    }
}