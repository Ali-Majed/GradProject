package com.example.gradproject.modle;

public class UserPOS {

    private String idPos,namePos,emailPos,passwordPos,numberPos,placePos,imagePos;
    private int usertypePos;



    public UserPOS(String idPos, String namePos, String emailPos, String passwordPos, String numberPos, String placePos,int usertypePos) {
        this.idPos = idPos;
        this.namePos = namePos;
        this.emailPos = emailPos;
        this.passwordPos = passwordPos;
        this.numberPos = numberPos;
        this.placePos = placePos;
        this.usertypePos=usertypePos;
    }

    public int getUsertypePos() {
        return usertypePos;
    }

    public void setUsertypePos(int usertypePos) {
        this.usertypePos = usertypePos;
    }

    public UserPOS() {
    }

    public String getImagePos() {
        return imagePos;
    }

    public void setImagePos(String imagePos) {
        this.imagePos = imagePos;
    }

    public String getIdPos() {
        return idPos;
    }

    public void setIdPos(String idPos) {
        this.idPos = idPos;
    }

    public String getNamePos() {
        return namePos;
    }

    public void setNamePos(String namePos) {
        this.namePos = namePos;
    }

    public String getEmailPos() {
        return emailPos;
    }

    public void setEmailPos(String emailPos) {
        this.emailPos = emailPos;
    }

    public String getPasswordPos() {
        return passwordPos;
    }

    public void setPasswordPos(String passwordPos) {
        this.passwordPos = passwordPos;
    }

    public String getNumberPos() {
        return numberPos;
    }

    public void setNumberPos(String numberPos) {
        this.numberPos = numberPos;
    }

    public String getPlacePos() {
        return placePos;
    }

    public void setPlacePos(String placePos) {
        this.placePos = placePos;
    }
}
