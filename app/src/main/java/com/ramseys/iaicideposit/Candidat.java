package com.ramseys.iaicideposit;

public class Candidat extends Users{

    private String tutorName;
    private int tutorTel;
    private String lieuConcours;
    private String lieuFormation;

    public Candidat(String id, String name, String fisrtName, String dateNais, String lieuNaiss, int tel, String tutorName, int tutorTel, String lieuConcours, String lieuFormation ) {
        super(id, name, fisrtName, dateNais, lieuNaiss, tel);
        this.tutorName = tutorName;
        this.tutorTel = tutorTel;
        this.lieuConcours = lieuConcours;
        this.lieuFormation = lieuFormation;
    }

    public String getTutorName() {
        return tutorName;
    }

    public void setTutorName(String tutorName) {
        this.tutorName = tutorName;
    }

    public int getTutorTel() {
        return tutorTel;
    }

    public void setTutorTel(int tutorTel) {
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
