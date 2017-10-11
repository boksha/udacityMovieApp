package com.example.milosevi.rxjavatest.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by milosevi on 10/11/17.
 */

public class Trailers {
    @Override
    public String toString() {
        return "Trailers{" +
                "mReviews=" + mTrailers +
                '}';
    }

    @SerializedName("results")
    List<Trailer> mTrailers;

    public List<Trailer> getTrailers() {
        return mTrailers;
    }

    public Trailers(List<Trailer> trailers) {
        this.mTrailers = trailers;
    }
}
