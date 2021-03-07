package com.example.womensskill;

public class ProductAttr {
    String Id;
    String UserId;
    String Title;
    String Price;
    String Category;
    String Subcategory;
    String Image1;
    String Image2;
    String Image3;
    String Quantity;
    String Video;
    String Description;
    String Status;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getSubcategory() {
        return Subcategory;
    }

    public void setSubcategory(String subcategory) {
        Subcategory = subcategory;
    }

    public String getImage1() {
        return Image1;
    }

    public void setImage1(String image1) {
        Image1 = image1;
    }

    public String getImage2() {
        return Image2;
    }

    public void setImage2(String image2) {
        Image2 = image2;
    }

    public String getImage3() {
        return Image3;
    }

    public void setImage3(String image3) {
        Image3 = image3;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getVideo() {
        return Video;
    }

    public void setVideo(String video) {
        Video = video;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public ProductAttr(String id, String userId, String title, String price, String category, String subcategory, String image1, String image2, String image3, String quantity, String video, String description, String status) {
        Id = id;
        UserId = userId;
        Title = title;
        Price = price;
        Category = category;
        Subcategory = subcategory;
        Image1 = image1;
        Image2 = image2;
        Image3 = image3;
        Quantity = quantity;
        Video = video;
        Description = description;
        Status = status;
    }

    public ProductAttr() {
    }
}
