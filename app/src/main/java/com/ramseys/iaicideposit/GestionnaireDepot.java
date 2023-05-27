package com.ramseys.iaicideposit;

public class GestionnaireDepot{

    private String matricule;
    private String post;
    private String dateEmp;
    private String lieuEmp;

    public GestionnaireDepot( String post,String dateEmp, String lieuEmp,String matricule) {
        this.matricule = matricule;
        this.post = post;
        this.dateEmp = dateEmp;
        this.lieuEmp = lieuEmp;
    }

    public GestionnaireDepot() {

    }

    public String getDateEmp() {
        return dateEmp;
    }

    public void setDateEmp(String dateEmp) {
        this.dateEmp = dateEmp;
    }

    public String getLieuEmp() {
        return lieuEmp;
    }

    public void setLieuEmp(String lieuEmp) {
        this.lieuEmp = lieuEmp;
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
