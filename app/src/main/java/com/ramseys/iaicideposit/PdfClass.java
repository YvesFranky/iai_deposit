package com.ramseys.iaicideposit;

public class PdfClass {

    public  String name, url;

    public PdfClass(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public PdfClass() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
