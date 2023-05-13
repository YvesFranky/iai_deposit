package com.ramseys.iaicideposit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class CandidatDetailInfo extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener{

    private TextView candidatName, candidatTele, candidatNaiss, filiere, centreForm, centreConcours, lieuNaiss;
    private EditText nomTuteur, telTuteur, doc;
    private Button valide, invalide;
    ProgressDialog progressDialog;
    FirebaseUser user;
    FirebaseFirestore firestore;
    CandidatItem candidatItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidat_detail_ifon);
        candidatItem = (CandidatItem) getIntent().getSerializableExtra("candidatItem");

        candidatName = findViewById(R.id.name);
        candidatNaiss = findViewById(R.id.dateNaiss);
        candidatTele = findViewById(R.id.telephone);
        filiere = findViewById(R.id.filiere);
        centreConcours = findViewById(R.id.centreExamen);
        centreForm = findViewById(R.id.centreForm);
        nomTuteur = findViewById(R.id.tutorName);
        telTuteur = findViewById(R.id.tutorNumber);
        lieuNaiss = findViewById(R.id.lieuNaiss);
        doc = findViewById(R.id.document);

        candidatName.setText(candidatItem.getNomCadidat());
        filiere.setText(candidatItem.getFiliere());

        valide = findViewById(R.id.valide);
        invalide = findViewById(R.id.invalide);

        firestore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        firestore.collection("users").document(candidatItem.getNumero()).get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()){
                                       Users users = task.getResult().toObject(Users.class);
                                       progressDialog = new ProgressDialog(CandidatDetailInfo.this);
                                        progressDialog.setTitle("Chargement des informations");
                                        progressDialog.setMessage("Veuillez patienter le temps que les informations du candidats "+users.getUname()+ " se chargent....");
                                        progressDialog.show();
                                       candidatName.setText(users.getUname());
                                       candidatNaiss.setText("Né le :"+users.getDateNaiss());
                                       candidatTele.setText(users.getTel());
                                       lieuNaiss.setText("Né à "+users.getLieuNaiss());
                                       firestore.collection("candidats").document(users.getUid()).get()
                                               .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                   @Override
                                                   public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()){
                                                            Candidat candidat = task.getResult().toObject(Candidat.class);

                                                            filiere.setText(candidat.getFiliere());
                                                            nomTuteur.setText(candidat.getTutorName());
                                                            telTuteur.setText(candidat.getTutorTel());
                                                            centreForm.setText(candidat.getLieuFormation());
                                                            centreConcours.setText(candidat.getLieuConcours());
                                                            firestore.collection("candidatures").document(users.getUid()).get()
                                                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                          if (task.isSuccessful()){
                                                                              Candidature candidature = task.getResult().toObject(Candidature.class);

                                                                                if (candidature.getPdfUri()!=null)doc.setText(candidature.getPdfUri());

                                                                              progressDialog.dismiss();
                                                                          }
                                                                        }
                                                                    });
                                                        }
                                                   }
                                               });
                                    }
                            }
                        });


        valide.setOnClickListener(this);
        invalide.setOnClickListener(this);
        doc.setOnTouchListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.valide:
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
                                            firestore.collection("candidats").document(candidatItem.getNumero()).update("isCandidat", true).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    Toast.makeText(CandidatDetailInfo.this, "Confirmé", Toast.LENGTH_SHORT).show();
                                                }
                                            });
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
                                    firestore.collection("candidats").document(candidatItem.getNumero()).update("isCandidat", false).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            Toast.makeText(CandidatDetailInfo.this, "Annulée", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                    ).setNegativeButton(
                            getResources().getString(R.string.Confirmno),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(CandidatDetailInfo.this, "Annulé", Toast.LENGTH_SHORT).show();
                                }
                            }
                    ).show();
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