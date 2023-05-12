package com.ramseys.iaicideposit;

import java.util.HashMap;
import java.util.Map;

public class Users {

    private String id;
    private String name;
    private String image;
    private String dateNais;
    private String lieuNaiss;
    private String tel;
    private String login;
    private  String passWord;
    private boolean isAdmin;

    public Users(String id, String name,  String dateNais, String lieuNaiss, String tel) {
        this.id = id;
        this.name = name;
        this.dateNais = dateNais;
        this.lieuNaiss = lieuNaiss;
        this.tel = tel;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Users() {
    }

    public Map<String, Object> fromJson(){
        Map<String, Object> userFromJson = new HashMap<>();
        userFromJson.put("uid", this.id);
        userFromJson.put("uname", this.name);
        userFromJson.put("login", this.login);
        userFromJson.put("password", this.passWord);
        userFromJson.put("dateNaiss", this.dateNais);
        userFromJson.put("lieuNais", this.lieuNaiss);
        userFromJson.put("Tel", this.tel);

        return userFromJson;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
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
