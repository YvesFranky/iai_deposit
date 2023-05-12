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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ramseys.iaicideposit.MainActivity;
import com.ramseys.iaicideposit.R;
import com.ramseys.iaicideposit.Users;

import java.text.DateFormat;
import java.util.Calendar;

public class RegisterPage extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    EditText uName,uLogin ,uPass, uDate, uLieu, uTel;
    Button register;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

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
                register();
            }
        });

        
    }

    private void register() {
        FirebaseUser user = firebaseAuth.getCurrentUser();

        Users users = new Users();
        users.setId(user.getUid());
        users.setName(uName.getText().toString());
        users.setLogin(uLogin.getText().toString());
        users.setPassWord(uPass.getText().toString());
        users.setTel(uTel.getText().toString());
        users.setLieuNaiss(uLieu.getText().toString());
        users.setDateNais(uDate.getText().toString());
        users.setAdmin(false);

        firestore.collection("users").document(user.getUid()).update(users.fromJson()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(RegisterPage.this, "Update succes", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterPage.this, MainActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterPage.this, "Update fail", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

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