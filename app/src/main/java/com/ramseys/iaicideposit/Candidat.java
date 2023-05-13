package com.ramseys.iaicideposit;

import com.google.firebase.firestore.PropertyName;

public class Candidat{

    private String tutorName;
    private String tutorTel;
    private String lieuConcours;
    private String lieuFormation;
    private String filiere;
    private String uid;
    private boolean isCandidat;


    public Candidat( String tutorName, String tutorTel, String lieuConcours, String lieuFormation, String filiere ) {
        this.tutorName = tutorName;
        this.tutorTel = tutorTel;
        this.lieuConcours = lieuConcours;
        this.lieuFormation = lieuFormation;
        this.filiere = filiere;
    }

    public Candidat() {
    }

    public String getFiliere() {
        return filiere;
    }

    public void setFiliere(String filiere) {
        this.filiere = filiere;
    }
    @PropertyName("isCandidat")
    public boolean isCandidat() {
        return isCandidat;
    }
    @PropertyName("isCandidat")
    public void setCandidat(boolean candidat) {
        isCandidat = candidat;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTutorName() {
        return tutorName;
    }

    public void setTutorName(String tutorName) {
        this.tutorName = tutorName;
    }

    public String getTutorTel() {
        return tutorTel;
    }

    public void setTutorTel(String tutorTel) {
        this.tutorTel = tutorTel;
    }

    public String getLieuConcours() {
        return lieuConcours;
    }

    public void setLieuConcours(String lieuConcours) {
        this.lieuConcours = lieuConcours;
    }

    public String getLieuFormation() {
        return lieuFormation;
    }

    public void setLieuFormation(String lieuFormation) {
        this.lieuFormation = lieuFormation;
    }


}
