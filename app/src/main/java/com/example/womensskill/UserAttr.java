package com.example.womensskill;

public class UserAttr {

    String id, username, fullname, email, date, phone,province,distric,address, gender,summary , img  , currency , outofoffice , balance;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public UserAttr(String id, String username, String fullname, String email, String date, String phone, String province, String distric, String address, String gender, String summary, String img, String currency, String outofoffice, String balance) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.date = date;
        this.phone = phone;
        this.province = province;
        this.distric = distric;
        this.address = address;
        this.gender = gender;
        this.summary = summary;
        this.img = img;
        this.currency = currency;
        this.outofoffice = outofoffice;
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getOutofoffice() {
        return outofoffice;
    }

    public void setOutofoffice(String outofoffice) {
        this.outofoffice = outofoffice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistric() {
        return distric;
    }

    public void setDistric(String distric) {
        this.distric = distric;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public UserAttr() {
    }
}
