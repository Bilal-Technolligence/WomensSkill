package com.example.womensskill;

public class OrderPostAttr {

    String id ,postId, userId , providerId , title , date , price , userName , providerName , status , img , providerImg , productId , time,  category , criteria , userImg ;

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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public OrderPostAttr(String id, String postId, String userId, String providerId, String title, String date, String price, String userName, String providerName, String status, String img, String providerImg, String productId, String time, String category, String criteria, String userImg) {
        this.id = id;
        this.postId = postId;
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
        this.productId = productId;
        this.time = time;
        this.category = category;
        this.criteria = criteria;
        this.userImg = userImg;
    }

    public OrderPostAttr() {
    }
}
