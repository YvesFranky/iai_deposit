package com.ramseys.iaicideposit.Auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ramseys.iaicideposit.MainActivity;
import com.ramseys.iaicideposit.R;
import com.ramseys.iaicideposit.UserScreen.CandidatHome;
import com.ramseys.iaicideposit.UserScreen.EnrolPage;
import com.ramseys.iaicideposit.Users;

import java.text.DateFormat;
import java.util.Calendar;

public class RegisterPage extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    EditText uName,uLogin ,uPass, uDate, uLieu, uTel;
    Button register;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        bundle = getIntent().getExtras();

        uName = findViewById(R.id.userName);
        uPass = findViewById(R.id.passWord);
        uDate = findViewById(R.id.date);
        uLieu = findViewById(R.id.lieu);
        uTel = findViewById(R.id.tel);
        uLogin = findViewById(R.id.login);
        register = findViewById(R.id.register);

        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        uDate.setOnClickListener(v -> {
            com.ramseys.iaicideposit.DatePicker mDatePicker;
            mDatePicker = new com.ramseys.iaicideposit.DatePicker();
            mDatePicker.show(getSupportFragmentManager(), "Choisize une date");
        });
        
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkChamp();

            }
        });

        
    }

    private void checkChamp() {
        
        if (uName.getText()!=null && uPass.getText()!=null && uDate.getText()!=null && uLogin.getText()!=null && uTel.getText().toString() != null){
            if (uPass.getText().length() <= 6){
                Toast.makeText(this, "Le mot de passe doit dépasser 06 caractère", Toast.LENGTH_SHORT).show();
            }else {
                if (uTel.getText().length()!=9){
                    Toast.makeText(this, "Le numérou de téléphone contient 09 chiffres", Toast.LENGTH_SHORT).show();
                }else {
                    register();
                }
            }
        }
    }

    private void register() {

        FirebaseUser user = firebaseAuth.getCurrentUser();

        Users users = new Users();
        users.setUid(user.getUid());
        users.setUname(uName.getText().toString());
        users.setLogin(uLogin.getText().toString());
        users.setPassword(uPass.getText().toString());
        users.setTel(uTel.getText().toString());
        users.setLieuNaiss(uLieu.getText().toString());
        users.setDateNaiss(uDate.getText().toString());
        users.setRegister(true);
        users.setAdmin(false);

        if (bundle.get("idCandidat")!=null){
            users.setUid(bundle.get("idCandidat").toString());
            firestore.collection("users").document(bundle.get("idCandidat").toString()).set(users).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(RegisterPage.this, "Enregistrement réussi avec succes", Toast.LENGTH_SHORT).show();
                    Intent intent =  new Intent(RegisterPage.this, EnrolPage.class);
                    intent.putExtra("idCandidat", bundle.get("idCandidat").toString());
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RegisterPage.this, "Update fail", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            firestore.collection("users").document(user.getUid()).update(users.fromJson()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(RegisterPage.this, "Update succes", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterPage.this, CandidatHome.class);
                    intent.putExtra("isCandidat", (Bundle) null);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RegisterPage.this, "Update fail", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }


    @Override
    protected void onStart() {
        super.onStart();
        if (bundle.get("idCandidat") != null){
            Toast.makeText(this, "Création d'un nouveau candidat", Toast.LENGTH_SHORT).show();

        }else  checkRegisterStatus();


    }

    private void checkRegisterStatus() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        DocumentReference documentReference = firestore.collection("users").document(user.getUid());
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Users mUsers = documentSnapshot.toObject(Users.class);
                Toast.makeText(RegisterPage.this, mUsers.getUid(), Toast.LENGTH_SHORT).show();
                if (mUsers.isRegister()) {
                    Toast.makeText(RegisterPage.this, "Bienvenue "+mUsers.getUname(), Toast.LENGTH_SHORT).show();
                    Intent intent =  new Intent(RegisterPage.this, CandidatHome.class);
                    intent.putExtra("isCandidat", (Bundle) null);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(mCalendar.getTime());
        uDate.setText(selectedDate);
    }
}