package com.ramseys.iaicideposit;


public class Candidature {

    private String pdfUri;
    private String image;
    private String idCandidat;
    private String idGestion;

    public Candidature(String pdfUri, String image, String idCandidat, String idGestion) {
        this.pdfUri = pdfUri;
        this.image = image;
        this.idCandidat = idCandidat;
        this.idGestion = idGestion;
    }

    public Candidature() {

    }

    public String getPdfUri() {
        return pdfUri;
    }

    public void setPdfUri(String pdfUri) {
        this.pdfUri = pdfUri;
    }

    public String getImage(String nothing) {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
