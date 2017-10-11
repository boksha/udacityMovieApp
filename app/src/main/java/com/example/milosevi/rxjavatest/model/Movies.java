package com.example.milosevi.rxjavatest.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by milosevi on 9/29/17.
 */

public class Movies {

    @Override
    public String toString() {
        return "Movies{" +
                "mReviews=" + mMovies +
                '}';
    }
    @SerializedName("results")
    List<Movie> mMovies;

    public List<Movie> getMovies() {
        return mMovies;
    }

    public Movies(List<Movie> mMovies) {
        this.mMovies = mMovies;
    }
}
