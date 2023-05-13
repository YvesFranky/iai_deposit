package com.ramseys.iaicideposit;

import android.net.Uri;

public class Candidature {

    private Uri pdfUri;
    private int Image;
    private String idCandidat;
    private String idGestion;

    public Candidature(Uri pdfUri, int image, String idCandidat) {
        this.pdfUri = pdfUri;
        Image = image;
        this.idCandidat = idCandidat;
    }

    public Candidature() {

    }

    public Uri getPdfUri() {
        return pdfUri;
    }

    public void setPdfUri(Uri pdfUri) {
        this.pdfUri = pdfUri;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String getIdCandidat() {
        return idCandidat;
    }

    public void setIdCandidat(String idCandidat) {
        this.idCandidat = idCandidat;
    }

    public String getIdGestion() {
        return idGestion;
    }

    public void setIdGestion(String idGestion) {
        this.idGestion = idGestion;
    }
}
