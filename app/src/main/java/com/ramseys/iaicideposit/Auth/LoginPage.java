package com.ramseys.iaicideposit.Auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ramseys.iaicideposit.MainActivity;
import com.ramseys.iaicideposit.R;
import com.ramseys.iaicideposit.UserScreen.EnrolPage;
import com.ramseys.iaicideposit.Users;

public class LoginPage extends AppCompatActivity implements View.OnClickListener{
    Button login;
    TextView register;
    private static  final int RC_SIGN_IN = 40;

     FirebaseAuth firebaseAuth;
     FirebaseDatabase firebaseDatabase;
     FirebaseFirestore firestore;

    GoogleSignInClient mGoogleSIgnInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        login = findViewById(R.id.login);
        register = findViewById(R.id.signUp);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                                .build();
        mGoogleSIgnInClient = GoogleSignIn.getClient(this, gso);


        login.setOnClickListener(this);
        register.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                    //signIn();
                break;
            case R.id.signUp:
                startActivity(new Intent(this, RegisterPage.class));
                break;
            default:
                startActivity(new Intent(this, MainActivity.class));
        }
    }

    private void signIn() {

        Intent intent = mGoogleSIgnInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuth(account.getIdToken());
            } catch (ApiException e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(LoginPage.this, RegisterPage.class));
            Toast.makeText(this, "User exist", Toast.LENGTH_SHORT).show();
        }else signIn();
    }

    private void firebaseAuth(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            Users users = new Users();

                            users.setId(user.getUid());
                            users.setName(user.getDisplayName());
                            users.setImage(user.getPhotoUrl().toString());
                            users.setTel(user.getPhoneNumber());
                            users.setLogin(user.getEmail());
                            users.setPassWord(user.getEmail());


                            firestore.collection("users").document(user.getUid()).set(users.fromJson()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(LoginPage.this, "Enregistrement reussi", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }else Toast.makeText(LoginPage.this, "Erreur d'enregistrement", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}