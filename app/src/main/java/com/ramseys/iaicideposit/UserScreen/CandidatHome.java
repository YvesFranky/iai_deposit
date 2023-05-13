package com.ramseys.iaicideposit.UserScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ramseys.iaicideposit.Admin.GestionnaireItem;
import com.ramseys.iaicideposit.Auth.LoginPage;
import com.ramseys.iaicideposit.Auth.RegisterPage;
import com.ramseys.iaicideposit.Candidat;
import com.ramseys.iaicideposit.CandidatDetailInfo;
import com.ramseys.iaicideposit.CandidatItem;
import com.ramseys.iaicideposit.Candidature;
import com.ramseys.iaicideposit.GestionnaireDepot;
import com.ramseys.iaicideposit.MyAdapter;
import com.ramseys.iaicideposit.MyAdapter2;
import com.ramseys.iaicideposit.R;
import com.ramseys.iaicideposit.SplashScreen;
import com.ramseys.iaicideposit.Users;

import java.util.ArrayList;
import java.util.List;

public class CandidatHome extends AppCompatActivity {
    private RecyclerView recyclerView;
     List<CandidatItem> candidatItemList;
     List<Candidat> candidatList;


    List<GestionnaireItem> gestionnaireItemsList;
    List<GestionnaireDepot> gestionnaireDepotList;
    private SearchView searchView;
    private MyAdapter myAdapter;
    private MyAdapter2 myAdapter2;
    ProgressDialog progressDialog;

    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidat_home);
        bundle = getIntent().getExtras();
        searchView = findViewById(R.id.searchBar);
        searchView.clearFocus();
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(CandidatHome.this);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });
        candidatItemList = new ArrayList<>();
        candidatList = new ArrayList<>();
        gestionnaireItemsList = new ArrayList<>();
        gestionnaireDepotList = new ArrayList<>();
        if (bundle.getBoolean("gest")){
            FirebaseUser user = firebaseAuth.getCurrentUser();
            firestore.collection("users").whereEqualTo("isAdmin", true).get()
                    .addOnCompleteListener(task -> {

                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot snapshot: task.getResult()){
                                progressDialog.setTitle("Chargement de la liste des gestionnaires");
                                progressDialog.setMessage("Veuillez patienter, chargement de la liste en cours");
                                progressDialog.show();
                                Users users = snapshot.toObject(Users.class);

                                firestore.collection("gestionnaires").whereEqualTo("matricule", users.getUid()).get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            private void onItemClick(GestionnaireItem gestionnaireItem) {
                                                Toast.makeText(CandidatHome.this, users.getUid(), Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()){
                                                    for (QueryDocumentSnapshot snapshot1: task.getResult()){
                                                        GestionnaireDepot gestionnaire = snapshot1.toObject(GestionnaireDepot.class);
                                                        gestionnaireItemsList.add(new GestionnaireItem(users.getUname(),gestionnaire.getPost(),gestionnaire.getMatricule(),R.drawable.mainbkg));
                                                        myAdapter2 = new MyAdapter2(getApplicationContext(), gestionnaireItemsList, this::onItemClick);

                                                        recyclerView = findViewById(R.id.recyclerView);
                                                        recyclerView.setLayoutManager(new LinearLayoutManager(CandidatHome.this));
                                                        recyclerView.setHasFixedSize(true);
                                                        recyclerView.setAdapter(myAdapter2);
                                                        fecthData2(gestionnaireDepotList);
                                                        progressDialog.dismiss();
                                                    }
                                                }


                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(CandidatHome.this, "Erreur de chargement"+users.getUid(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }
                    });

        }else if (!bundle.getBoolean("gest")){
            FirebaseUser user = firebaseAuth.getCurrentUser();
            firestore.collection("users").whereEqualTo("isAdmin", false).get()
                    .addOnCompleteListener(task -> {

                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot snapshot: task.getResult()){
                                progressDialog.setTitle("Chargement de la liste des candidats");
                                progressDialog.setMessage("Veuillez patienter, chargement de la liste en cours");
                                progressDialog.show();
                                Users users = snapshot.toObject(Users.class);

                                firestore.collection("candidats").whereEqualTo("uid", users.getUid()).get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            private void onItemClick(CandidatItem candidatItem) {
                                                Intent intent = new Intent(CandidatHome.this, CandidatDetailInfo.class);
                                                intent.putExtra("candidatItem", candidatItem);
                                                startActivity(intent);
                                            }

                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()){
                                                    for (QueryDocumentSnapshot snapshot1: task.getResult()){
                                                        Candidat candidat = snapshot1.toObject(Candidat.class);

                                                        if (candidat.isCandidat()){
                                                            candidatItemList.add(new CandidatItem(candidat.getUid(), users.getUname(), R.drawable.mainbkg, candidat.getFiliere(), true));
                                                        }else candidatItemList.add(new CandidatItem(candidat.getUid(), users.getUname(), R.drawable.mainbkg, candidat.getFiliere(), false));
                                                        myAdapter = new MyAdapter(getApplicationContext(), candidatItemList, this::onItemClick);

                                                        recyclerView = findViewById(R.id.recyclerView);
                                                        recyclerView.setLayoutManager(new LinearLayoutManager(CandidatHome.this));
                                                        recyclerView.setHasFixedSize(true);
                                                        recyclerView.setAdapter(myAdapter);
                                                        fecthData(candidatList);
                                                        Toast.makeText(CandidatHome.this, candidat.getTutorName(), Toast.LENGTH_SHORT).show();
                                                        progressDialog.dismiss();
                                                    }
                                                }


                                            }
                                        });
                            }
                        }
                    });
        }

    }

    private List<Candidat> fecthData(List<Candidat> candidatList) {
        return  candidatList;
    }
    private List<GestionnaireDepot> fecthData2(List<GestionnaireDepot> candidatList) {
        return  candidatList;
    }

    private void filterList(String newText) {
        List<CandidatItem> filteredList = new ArrayList<>();
        List<GestionnaireItem> filteredGestionnaire = new ArrayList<>();

        if (bundle.getBoolean("gest")){
            for (GestionnaireItem candidatItem : gestionnaireItemsList) {
                if (candidatItem.getName().toLowerCase().contains(newText.toLowerCase())){
                    filteredGestionnaire.add(candidatItem);
                }
            }

            if (filteredGestionnaire.isEmpty()){
                Toast.makeText(this, "Pas de correspondance", Toast.LENGTH_SHORT).show();
            }else {
                myAdapter2.setFilteredList(filteredGestionnaire);
            }

        }else {
            for (CandidatItem candidatItem : candidatItemList) {
                if (candidatItem.getNomCadidat().toLowerCase().contains(newText.toLowerCase())){
                    filteredList.add(candidatItem);
                }
            }

            if (filteredList.isEmpty()){
                Toast.makeText(this, "Pas de correspondance", Toast.LENGTH_SHORT).show();
            }else {
                myAdapter.setFilteredList(filteredList);
            }
        }

    }
    //continuer la modification avec le trie de la liste des gestionnaires

}