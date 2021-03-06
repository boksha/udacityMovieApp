package com.example.milosevi.rxjavatest.details.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by milosevi on 10/11/17.
 */

public class Reviews {

    @Override
    public String toString() {
        return "Reviews{" +
                "mReviews=" + mReviews +
                '}';
    }

    @SerializedName("results")
    List<Review> mReviews;

    public List<Review> getReviewList() {
        return mReviews;
    }

    public Reviews(List<Review> reviews) {
        this.mReviews = reviews;
    }
}
