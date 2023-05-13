package com.ramseys.iaicideposit;

import com.google.firebase.firestore.PropertyName;

import java.util.HashMap;
import java.util.Map;

public class Users {

    private String uid;
    private String uname;
    private String image;
    private String dateNaiss;
    private String lieuNaiss;
    private String Tel;
    private String login;
    private  String password;
    private boolean isAdmin;
    private boolean isRegister;

    public Users(String uid, String uname, String dateNaiss, String lieuNaiss, String tel) {
        this.uid = uid;
        this.uname = uname;
        this.dateNaiss = dateNaiss;
        this.lieuNaiss = lieuNaiss;
        this.Tel = tel;
    }



    public Users() {
    }

    public Map<String, Object> fromJson(){
        Map<String, Object> userFromJson = new HashMap<>();
        userFromJson.put("uid", this.uid);
        userFromJson.put("uname", this.uname);
        userFromJson.put("login", this.login);
        userFromJson.put("password", this.password);
        userFromJson.put("dateNaiss", this.dateNaiss);
        userFromJson.put("lieuNais", this.lieuNaiss);
        userFromJson.put("Tel", this.Tel);
        userFromJson.put("isAdmin", this.isAdmin);
        userFromJson.put("isRegister", this.isRegister);

        return userFromJson;
    }


    public void UserLogin(String login, String passWord){
        this.login = login;
        this.password = passWord;
    }
    @PropertyName("isRegister")
    public boolean isRegister() {
        return isRegister;
    }
    @PropertyName("isRegister")
    public void setRegister(boolean register) {
        isRegister = register;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDateNaiss() {
        return dateNaiss;
    }

    public void setDateNaiss(String dateNaiss) {
        this.dateNaiss = dateNaiss;
    }

    public String getLieuNaiss() {
        return lieuNaiss;
    }

    public void setLieuNaiss(String lieuNaiss) {
        this.lieuNaiss = lieuNaiss;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        Tel = tel;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @PropertyName("isAdmin")
    public boolean isAdmin() {
        return isAdmin;
    }
    @PropertyName("isAmdin")
    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
