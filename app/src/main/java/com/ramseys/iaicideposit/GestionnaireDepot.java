package com.ramseys.iaicideposit;

public class GestionnaireDepot extends Users{

    private String matricule;
    private String post;

    public GestionnaireDepot(String id, String name,  String dateNais, String lieuNaiss, String tel, String matricule, String post) {
        super(id, name, dateNais, lieuNaiss, tel);
        this.matricule = matricule;
        this.post = post;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}