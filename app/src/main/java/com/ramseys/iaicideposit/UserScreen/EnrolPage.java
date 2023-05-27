package com.ramseys.iaicideposit.UserScreen;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.Manifest;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ramseys.iaicideposit.Candidat;
import com.ramseys.iaicideposit.Candidature;
import com.ramseys.iaicideposit.MainActivity;
import com.ramseys.iaicideposit.PdfClass;
import com.ramseys.iaicideposit.R;
import com.ramseys.iaicideposit.Users;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class EnrolPage extends AppCompatActivity implements View.OnClickListener {
    private Button next;
    private EditText tuteur, numTuteur, document, lieuDepot, lieuFormation, filiere;
    private CircleImageView imageView;
    private ActivityResultLauncher<Intent> resultLauncher;
    public static Uri pdfData;
    private  final int REQ =1;
    String donwloadUri = "";

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    FirebaseStorage storage;
    StorageReference reference;
    Bundle bundle;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrol_page);

        bundle = getIntent().getExtras();
        //Connexion des composant avec l'interface #Ramseys

        next = findViewById(R.id.upload);
        numTuteur = findViewById(R.id.numTuteur);
        tuteur = findViewById(R.id.tuteur);
        document = findViewById(R.id.document);
        lieuDepot = findViewById(R.id.lieuDepot);
        lieuFormation = findViewById(R.id.lieuForm);
        imageView = findViewById(R.id.imageView);
        filiere  = findViewById(R.id.filiere);

        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();


        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            Intent data = result.getData();

            if (data!=null){
                pdfData = data.getData();
                document.setText(pdfData.getPath());
            }
        });

        next.setOnClickListener(this);
        document.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firestore.collection("users").document(firebaseAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Users users = documentSnapshot.toObject(Users.class);

                if (users.isRegister()){
                    Intent intent  =  new Intent(EnrolPage.this, CandidatHome.class);
                    intent.putExtra("gest", false);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.upload){

            depotCandidature();
                //startActivity(new Intent(this, CandidatHome.class));
        }else if (v.getId() == R.id.document){
            GetFile();
        }

    }

    private void depotCandidature() {

        if (tuteur.getText().toString().isEmpty() && numTuteur.getText().toString().isEmpty() && lieuFormation.getText().toString().isEmpty() && lieuDepot.getText().toString().isEmpty() && document.getText().toString().isEmpty()){
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
        }else {

            //candidature.setPdfUri();
            if (bundle.get("idCandidat")!=null){
                registerCandidature(bundle.get("idCandidat").toString());
            }else {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                registerCandidature(user.getUid());
            }


        }
    }

    private void registerCandidature(String idCandidat) {
        FirebaseUser  user = firebaseAuth.getCurrentUser();
        Candidat candidat = new Candidat();
        if (bundle.get("idCandidat")!=null){
            candidat.setUid(bundle.get("idCandidat").toString());
        }else candidat.setUid(user.getUid());
        candidat.setLieuConcours(lieuDepot.getText().toString());
        candidat.setLieuFormation(lieuFormation.getText().toString());
        candidat.setTutorName(tuteur.getText().toString());
        candidat.setTutorTel(numTuteur.getText().toString());
        candidat.setFiliere(filiere.getText().toString());
        candidat.setCandidat(false);

        reference = storage.getReference();

        DocumentReference doc = firestore.collection("candidats").document(idCandidat);
        doc.set(candidat).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                Toast.makeText(EnrolPage.this, "Crée avec succes"+ donwloadUri, Toast.LENGTH_SHORT).show();

                if (pdfData!=null){
                    final ProgressDialog progressDialog = new ProgressDialog(EnrolPage.this);
                    progressDialog.setTitle("Chargement du dossier");
                    progressDialog.show();

                    StorageReference dossierPdfRef = reference.child("dossiers/"+idCandidat+".pdf");
                    dossierPdfRef.putFile(pdfData)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                    while (!uriTask.isComplete());
                                    Uri url = uriTask.getResult();
                                    PdfClass pdfClass = new PdfClass(idCandidat, url.toString() );

                                    Candidature candidature = new Candidature();

                                    candidature.setIdCandidat(idCandidat);
                                    candidature.setPdfUri(url.toString());
                                    candidature.setImage("nothing");
                                    candidature.setIdGestion("");

                                    DocumentReference doc = firestore.collection("candidatures").document(idCandidat);
                                    doc.set(candidature).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(EnrolPage.this, "Candidature ajouté", Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                        }
                                    });
                                }
                            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                    double progress = (100.0*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                                    progressDialog.setMessage("Progression:"+ (int)progress+"%");
                                }
                            });

                }
            }
        });
    }


    private void selectpdf() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent ,"Selectionner votre dossier pdf"), 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 1 && resultCode == RESULT_OK && data!=null && data.getData()!=null ){
            document.setText(data.getData().getPath());
            uploadFiles(data.getData());
        }
    }

    private void uploadFiles(Uri data) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Chargement du dossier");
        progressDialog.show();
        String uid;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (bundle.get("idCandidat")!=null){
            uid = bundle.get("idCandidat").toString();
        }else  uid = user.getUid();
        StorageReference dossierPdfRef = reference.child("dossiers/"+uid+".pdf");
        dossierPdfRef.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete());
                        Uri url = uriTask.getResult();
                        PdfClass pdfClass = new PdfClass(uid, url.toString() );

                        Candidature candidature = new Candidature();

                        candidature.setIdCandidat(uid);
                        candidature.setPdfUri(url.toString());
                        candidature.setImage("nothing");
                        candidature.setIdGestion("");

                        DocumentReference doc = firestore.collection("candidatures").document(uid);
                        doc.set(candidature).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(EnrolPage.this, "Candidature ajouté", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        });
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress = (100.0*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                            progressDialog.setMessage("Progression:"+ (int)progress+"%");
                    }
                });

    }

    private void selectPdf(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

        intent.setType("application/pdf");

        resultLauncher.launch(intent);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQ  && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            selectpdf();
        }else Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
    }


    private boolean GetFile() {
        if (ActivityCompat.checkSelfPermission(
                EnrolPage.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager
                .PERMISSION_GRANTED) {
            // When permission is not granted
            // Result permission
            ActivityCompat.requestPermissions(
                    EnrolPage.this,
                    new String[] {
                            Manifest.permission
                                    .READ_EXTERNAL_STORAGE },
                    1);
            return false;
        }
        else {
            // When permission is granted
            // Create method
            selectPdf();
            return true;
        }
    }
}