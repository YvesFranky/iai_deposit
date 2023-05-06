package com.ramseys.iaicideposit.UserScreen;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
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

import com.ramseys.iaicideposit.MainActivity;
import com.ramseys.iaicideposit.R;

public class EnrolPage extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {
    private Button next;
    private EditText tuteur, numTuteur, document, lieuDepot, lieuFormation;
    private ActivityResultLauncher<Intent> resultLauncher;
    public static Uri pdfData;
    private  final int REQ =1;
    String donwloadUri = "";
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrol_page);

        //Connexion des composant avec l'interface #Ramseys

        next = findViewById(R.id.upload);
        numTuteur = findViewById(R.id.numTuteur);
        tuteur = findViewById(R.id.tuteur);
        document = findViewById(R.id.document);
        lieuDepot = findViewById(R.id.lieuDepot);
        lieuFormation = findViewById(R.id.lieuForm);

        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            Intent data = result.getData();

            if (data!=null){
                pdfData = data.getData();
                document.setText(pdfData.toString());
            }
        });

        next.setOnClickListener(this);
        document.setOnTouchListener(this);
        lieuFormation.setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.upload:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.document:
                Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "Oups", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (v.getId()){
            case R.id.document:
                GetFile();
                break;
        }
        return false;
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
            selectPdf();
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