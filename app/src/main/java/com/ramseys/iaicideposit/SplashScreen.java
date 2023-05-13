package com.ramseys.iaicideposit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ramseys.iaicideposit.Auth.LoginPage;
import com.ramseys.iaicideposit.Auth.RegisterPage;
import com.ramseys.iaicideposit.UserScreen.CandidatHome;
import com.ramseys.iaicideposit.UserScreen.EnrolPage;

public class SplashScreen extends AppCompatActivity {

    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    TextView chargement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        chargement = findViewById(R.id.chargement);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        if (firebaseAuth.getCurrentUser() != null){

            chargement.setText("Chargement des données utilisateurs...");

            FirebaseUser user = firebaseAuth.getCurrentUser();
            DocumentReference doc = firestore.collection("users").document(user.getUid());
            doc.get().addOnSuccessListener(documentSnapshot -> {
                Users mUsers = documentSnapshot.toObject(Users.class);

                chargement.setText("Verification des informations de connexions");

                if (mUsers.isRegister()){

                    chargement.setText("Verification du status de l'utilisateur");

                    if (mUsers.isAdmin()){
                        Toast.makeText(SplashScreen.this, "Bienvenue à toi "+mUsers.getUname(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SplashScreen.this, LoginPage.class));
                        finish();
                    }else {
                        Toast.makeText(SplashScreen.this, "Bienvenue à toi "+mUsers.getUname(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SplashScreen.this, CandidatHome.class);
                        intent.putExtra("gest", false);
                        startActivity(intent);
                        finish();
                    }

                }else {
                    Toast.makeText(SplashScreen.this, "Enregistrement", Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(SplashScreen.this, RegisterPage.class);
                    intent.putExtra("idCandidat", (Bundle) null);
                    startActivity(intent);
                    finish();
                }
            });
        }else{
            startActivity(new Intent(SplashScreen.this, LoginPage.class));
            finish();
        }
    }
}