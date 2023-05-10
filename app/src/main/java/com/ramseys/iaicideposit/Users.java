package com.ramseys.iaicideposit;

public class Users {

    private String id;
    private String name;
    private  String fisrtName;
    private String dateNais;
    private String lieuNaiss;
    private int tel;
    private String login;
    private  String passWord;

    public Users(String id, String name, String fisrtName, String dateNais, String lieuNaiss, int tel) {
        this.id = id;
        this.name = name;
        this.fisrtName = fisrtName;
        this.dateNais = dateNais;
        this.lieuNaiss = lieuNaiss;
        this.tel = tel;
    }

    public void UserLogin(String login, String passWord){
        this.login = login;
        this.passWord = passWord;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFisrtName() {
        return fisrtName;
    }

    public void setFisrtName(String fisrtName) {
        this.fisrtName = fisrtName;
    }

    public String getDateNais() {
        return dateNais;
    }

    public void setDateNais(String dateNais) {
        this.dateNais = dateNais;
    }

    public String getLieuNaiss() {
        return lieuNaiss;
    }

    public void setLieuNaiss(String lieuNaiss) {
        this.lieuNaiss = lieuNaiss;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
