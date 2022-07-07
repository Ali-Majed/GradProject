package com.example.gradproject.modle;

public class UsersCompany {
    private String id;
    private String nameCompany,distributorName,email,password,place,imageurl;
    private String number;
    private int usertypePos;

    public UsersCompany(String id,String nameCompany, String distributorName, String email, String password, String place, String number,int usertypePos ,String imageurl) {
        this.id=id;
        this.nameCompany = nameCompany;
        this.distributorName = distributorName;
        this.email = email;
        this.password = password;
        this.place = place;

        this.number = number;
        this.usertypePos=usertypePos;
        this.imageurl = imageurl;
    }
    public int getUsertypePos() {
        return usertypePos;
    }

    public void setUsertypePos(int usertypePos) {
        this.usertypePos = usertypePos;
    }

    public UsersCompany() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameCompany() {
        return nameCompany;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
