package com.ramseys.iaicideposit;

import java.io.Serializable;

public class CandidatItem implements Serializable {
    private String numero;
    private String nomCadidat;
    private boolean status;
    private int image;
    private String filiere;

    public CandidatItem(String numero, String nomCandidat, int image, String filiere, boolean status){

        this.numero = numero;
        this.nomCadidat = nomCandidat;
        this.status = status;
        this.image = image;
        this.filiere = filiere;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getFiliere() {
        return filiere;
    }

    public void setFiliere(String filiere) {
        this.filiere = filiere;
    }

    public String getNomCadidat() {
        return nomCadidat;
    }

    public void setNomCadidat(String nomCadidat) {
        this.nomCadidat = nomCadidat;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
