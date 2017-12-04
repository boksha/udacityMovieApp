package com.example.milosevi.rxjavatest.database.model;

import android.util.Log;

import com.example.milosevi.rxjavatest.Mapper;
import com.example.milosevi.rxjavatest.model.Movie;

/**
 * Created by miodrag.milosevic on 11/14/2017.
 */

public class RealmMovieMapper extends Mapper<RealmMovie, Movie> {

    public RealmMovieMapper() {
    }

    @Override
    public RealmMovie map(Movie m) {
        RealmMovie realmMovie = new RealmMovie();
        realmMovie.setId(m.getId());
        realmMovie.setTitle(m.getTitle());
        realmMovie.setImageUrl(m.getImageUrl());
        realmMovie.setDescription(m.getDescription());
        realmMovie.setUserRating(m.getUserRating());
        realmMovie.setVoteCount(m.getVoteCount());
        realmMovie.setPopularity(Double.valueOf( m.getPopularity()));
        realmMovie.setReleaseDate(m.getReleaseDate());
        return realmMovie;
    }

    @Override
    public Movie reverseMap(RealmMovie m) {
        Movie movie = new Movie();
        movie.setId(m.getId());
        movie.setTitle(m.getTitle());
        movie.setImageUrl(m.getImageUrl());
        movie.setDescription(m.getDescription());
        movie.setUserRating(m.getUserRating());
        movie.setVoteCount(m.getVoteCount());
        movie.setPopularity(String.valueOf(m.getPopularity()));
        movie.setReleaseDate(m.getReleaseDate());
        return movie;
    }
}
