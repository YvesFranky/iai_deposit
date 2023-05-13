package com.ramseys.iaicideposit.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ramseys.iaicideposit.Auth.RegisterPage;
import com.ramseys.iaicideposit.GestionnaireDepot;
import com.ramseys.iaicideposit.R;
import com.ramseys.iaicideposit.UserScreen.CandidatHome;
import com.ramseys.iaicideposit.Users;

import java.text.DateFormat;
import java.util.Calendar;

public class AdminRegisterPage extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
        EditText nomG, loginG, password, dateEmp, lieuEmp, matriculeG, numeroG, posteG;
        Button register;

        FirebaseAuth firebaseAuth;
        FirebaseFirestore firestore;
        ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register_page);

        nomG = findViewById(R.id.userNameG);
        loginG = findViewById(R.id.loginG);
        password = findViewById(R.id.passWordG);
        dateEmp = findViewById(R.id.dateG);
        lieuEmp = findViewById(R.id.lieuG);
        matriculeG = findViewById(R.id.matriculeG);
        numeroG = findViewById(R.id.telephoneG);
        posteG = findViewById(R.id.poste);

        register = findViewById(R.id.registerG);
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        dateEmp.setOnClickListener(this);
        register.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.registerG:
                checkElements();
                break;
            case R.id.dateG:
                com.ramseys.iaicideposit.DatePicker mDatePicker;
                mDatePicker = new com.ramseys.iaicideposit.DatePicker();
                mDatePicker.show(getSupportFragmentManager(), "Choisize une date");
                break;
        }

    }

    private void checkElements() {

        if (nomG.getText().toString().isEmpty() && loginG.getText().toString().isEmpty() && password.getText().toString().isEmpty() && dateEmp.getText().toString().isEmpty()
        && lieuEmp.getText().toString().isEmpty() && matriculeG.getText().toString().isEmpty() && numeroG.getText().toString().isEmpty() && posteG.getText().toString().isEmpty()){
            Toast.makeText(this, "Veuillez remplir tous les chapms", Toast.LENGTH_SHORT).show();
        }else {
            if (password.getText().length() <= 6){
                Toast.makeText(this, "le mot de passe doit avoir plus de 06 caractère", Toast.LENGTH_SHORT).show();
            }else {
                adminRegister();
            }
        }

    }

    private void adminRegister() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String uid = "candidat"+ System.currentTimeMillis();
        Users users = new Users();
        users.setUid(matriculeG.getText().toString());
        users.setUname(nomG.getText().toString());
        users.setLogin(loginG.getText().toString());
        users.setPassword(password.getText().toString());
        users.setTel(numeroG.getText().toString());
        users.setRegister(true);
        users.setAdmin(true);
        progressDialog.setTitle("Enregistrement Gestionnaire");
        progressDialog.setMessage("Enregistrement du gestionnaire en tant que utilisateur");
        firestore.collection("users").document(matriculeG.getText().toString()).set(users).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressDialog.setMessage("Enregistrement nouvel utilisateur réussi");
                GestionnaireDepot gestionnaireDepot = new GestionnaireDepot();

                gestionnaireDepot.setMatricule(matriculeG.getText().toString());
                gestionnaireDepot.setDateEmp(dateEmp.getText().toString());
                gestionnaireDepot.setLieuEmp(lieuEmp.getText().toString());
                gestionnaireDepot.setPost(posteG.getText().toString());

                progressDialog.setMessage("Enregistrement des données du Gestionnaire");
                firestore.collection("gestionnaires").document(matriculeG.getText().toString()).set(gestionnaireDepot).addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                progressDialog.setMessage("Enregistrement réussi");
                                progressDialog.dismiss();
                                Toast.makeText(AdminRegisterPage.this, "Enregistrement réussi", Toast.LENGTH_SHORT).show();
                            }
                        }
                ).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.setMessage("Enregistrement interrompu");
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.setMessage("Enregistrement interrompu");
                        progressDialog.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(mCalendar.getTime());
        dateEmp.setText(selectedDate);
    }
}