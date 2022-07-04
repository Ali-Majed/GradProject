package com.example.gradproject.modle;

public class Orders {

    private String idOrder,namePos,nameProduct,quantity,size,imageOrder,idPos,time,idCompany,nameCompany;


    public Orders(String namePos, String nameProduct, String quantity, String size, String imageOrder,
                  String idPos, String time,String idCompany,String nameCompany) {
        this.namePos = namePos;
        this.nameProduct = nameProduct;
        this.quantity = quantity;
        this.size = size;
        this.imageOrder = imageOrder;
        this.idPos = idPos;
        this.time = time;
        this.idCompany=idCompany;
        this.nameCompany=nameCompany;
    }

    public Orders() {
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getNamePos() {
        return namePos;
    }

    public void setNamePos(String namePos) {
        this.namePos = namePos;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getImageOrder() {
        return imageOrder;
    }

    public void setImageOrder(String imageOrder) {
        this.imageOrder = imageOrder;
    }

    public String getIdPos() {
        return idPos;
    }

    public void setIdPos(String idPos) {
        this.idPos = idPos;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(String idCompany) {
        this.idCompany = idCompany;
    }

    public String getNameCompany() {
        return nameCompany;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }
}
