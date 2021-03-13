package com.example.womensskill;

public class OrderAttr {

    String id , userId , providerId , title , date , price , userName , providerName , status , img , providerImg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getProviderImg() {
        return providerImg;
    }

    public void setProviderImg(String providerImg) {
        this.providerImg = providerImg;
    }

    public OrderAttr(String id, String userId, String providerId, String title, String date, String price, String userName, String providerName, String status, String img, String providerImg) {
        this.id = id;
        this.userId = userId;
        this.providerId = providerId;
        this.title = title;
        this.date = date;
        this.price = price;
        this.userName = userName;
        this.providerName = providerName;
        this.status = status;
        this.img = img;
        this.providerImg = providerImg;
    }

    public OrderAttr() {
    }
}
