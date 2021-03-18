package com.example.womensskill;

/**
 * Created by Hanzalah on 3/2/2019.
 */

public class Rating_Attr {
    private Float Rating;
    private String Comment;


    public Rating_Attr() {
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public Float getRating() {
        return Rating;
    }

    public void setRating(Float rating) {
        Rating = rating;
    }

    public Rating_Attr(Float rating, String comment) {
        Rating = rating;
        Comment = comment;
    }
}
