package com.ramseys.iaicideposit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CandidatDetailInfo extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener{

    private TextView candidatName, candidatTele, candidatNaiss, filiere, centreForm, centreConcours;
    private EditText nomTuteur, telTuteur, doc;
    private Button valide, invalide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidat_detail_ifon);
        CandidatItem candidatItem = (CandidatItem) getIntent().getSerializableExtra("candidatItem");

        candidatName = findViewById(R.id.name);
        candidatNaiss = findViewById(R.id.dateNaiss);
        candidatTele = findViewById(R.id.telephone);
        filiere = findViewById(R.id.filiere);
        centreConcours = findViewById(R.id.centreExamen);
        centreForm = findViewById(R.id.centreForm);
        nomTuteur = findViewById(R.id.tutorName);
        telTuteur = findViewById(R.id.numTuteur);
        doc = findViewById(R.id.document);

        candidatName.setText(candidatItem.getNomCadidat());
        filiere.setText(candidatItem.getFiliere());

        valide = findViewById(R.id.valide);
        invalide = findViewById(R.id.invalide);


        valide.setOnClickListener(this);
        invalide.setOnClickListener(this);
        doc.setOnTouchListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.valide:
                Toast.makeText(this, "Dossier validé", Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(this)
                        .setMessage(
                            getResources().getString(R.string.ConfirmAccept)
                        )
                        .setIcon(
                                getResources().getDrawable(R.drawable.ic_baseline_warning_24)
                        ).setPositiveButton(
                                getResources().getString(R.string.Confirmyes),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(CandidatDetailInfo.this, "Confirmé", Toast.LENGTH_SHORT).show();
                                    }
                                }
                        ).setNegativeButton(
                                getResources().getString(R.string.Confirmno),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(CandidatDetailInfo.this, "Rejetté", Toast.LENGTH_SHORT).show();
                                    }
                                }
                        ).show();
                break;
            case R.id.invalide:
                Toast.makeText(this, "Dossier invalidé", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "Oups", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.document){
            Toast.makeText(this, "Downloading.....", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}