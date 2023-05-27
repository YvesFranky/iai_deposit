package com.ramseys.iaicideposit.Auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.ramseys.iaicideposit.Candidature;
import com.ramseys.iaicideposit.MainActivity;
import com.ramseys.iaicideposit.R;
import com.ramseys.iaicideposit.UserScreen.CandidatHome;
import com.ramseys.iaicideposit.UserScreen.EnrolPage;
import com.ramseys.iaicideposit.Users;

public class LoginPage extends AppCompatActivity implements View.OnClickListener{
    Button login;
    TextView register, notice;
    EditText uname, password;
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
        uname = findViewById(R.id.userName);
        password = findViewById(R.id.passWord);

        notice = findViewById(R.id.notice);
        notice.setVisibility(View.INVISIBLE);
        login.setClickable(false);

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
                    logIn();
                break;
            case R.id.signUp:
                Intent intent  = new Intent(this, RegisterPage.class);
                intent.putExtra("idCandidat", (Bundle) null);
                startActivity(intent);
                break;
            default:
                startActivity(new Intent(this, MainActivity.class));
        }
    }

    private void logIn() {
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (uname.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Nom d'utilisateur non définie", Toast.LENGTH_SHORT).show();
        }else {
            if (password.getText().toString().isEmpty()){
                Toast.makeText(this, "Mot de passe non définit", Toast.LENGTH_SHORT).show();
            }else {
                    if (firebaseAuth.getCurrentUser() != null){
                        DocumentReference doc = firestore.collection("users").document(user.getUid());
                        doc.get().addOnSuccessListener(documentSnapshot -> {
                            Users mUsers = documentSnapshot.toObject(Users.class);
                            String nom = uname.getText().toString();
                            if (mUsers.getLogin().equals(nom)){
                                if (mUsers.getPassword().equals(password.getText().toString())){
                                    startActivity(new Intent(this, MainActivity.class));
                                }else Toast.makeText(this, "Mot de passe incorrect", Toast.LENGTH_SHORT).show();
                            }else Toast.makeText(this, "Nom d'utilisateur incorrect"+ mUsers.getLogin()+" "+nom, Toast.LENGTH_SHORT).show();
                        });
                    }else {
                        signIn();
                    }

            }
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

        if (firebaseAuth.getCurrentUser() == null){
            signIn();
            Toast.makeText(this, "Se connecter/ s'enregistrer", Toast.LENGTH_SHORT).show();
        }else Toast.makeText(this, "Veuillez vous authentifier!!!", Toast.LENGTH_SHORT).show();

    }


    private void firebaseAuth(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            firestore.collection("users").whereEqualTo("uid", user.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()){
                                        for (DocumentSnapshot snapshot: task.getResult()){
                                            Users users1 = snapshot.toObject(Users.class);

                                            if (users1.isRegister() == true){
                                                if (users1.isAdmin()==true){
                                                    startActivity(new Intent(LoginPage.this, MainActivity.class));
                                                }{
                                                    Intent intent = new Intent(LoginPage.this, CandidatHome.class);
                                                    intent.putExtra("gest", false);
                                                    startActivity(intent);
                                                }
                                            }else {
                                                Users users = new Users();

                                                users.setUid(user.getUid());
                                                users.setUname(user.getDisplayName());
                                                users.setImage(user.getPhotoUrl().toString());
                                                users.setTel(user.getPhoneNumber());
                                                users.setLogin(user.getEmail());
                                                users.setPassword(user.getEmail());
                                                users.setAdmin(false);


                                                firestore.collection("users").document(user.getUid()).set(users.fromJson()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Toast.makeText(LoginPage.this, "Enregistrement reussi", Toast.LENGTH_SHORT).show();
                                                        Intent intent =new Intent(LoginPage.this, RegisterPage.class);
                                                        intent.putExtra("idCandidat", (Bundle) null);
                                                        startActivity(intent);
                                                    }
                                                });
                                            }
                                        }
                                    }
                                }
                            });


                        }else Toast.makeText(LoginPage.this, "Erreur d'enregistrement", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}