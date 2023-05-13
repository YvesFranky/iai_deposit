package com.ramseys.iaicideposit.Admin;

public class GestionnaireItem {
    private  String name;
    private  String post;
    private String matricule;
    private  int image;

    public GestionnaireItem(String name, String post, String matricule, int image) {
        this.name = name;
        this.post = post;
        this.matricule = matricule;
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }
}
