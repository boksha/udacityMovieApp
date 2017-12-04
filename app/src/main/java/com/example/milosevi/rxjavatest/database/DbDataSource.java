package com.example.milosevi.rxjavatest.database;

import com.example.milosevi.rxjavatest.database.model.RealmMovie;
import com.example.milosevi.rxjavatest.model.Movie;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by miodrag.milosevic on 11/17/2017.
 */

public interface DbDataSource {

    void addMovie(Movie movie, @Movie.Type int type);

    void addMovieList(List<Movie> movies, @Movie.Type int type);

    void removeMovie(Movie movie, @Movie.Type int type);

    void deleteAllMovies(@Movie.Type int type);

    boolean isMovieInDb(int id, @Movie.Type int type);

    Observable<Movie> getMovie(int id);

    Observable<List<Movie>> queryMovies(@Movie.Type int type);

    void updateMovie(Movie movie);
}
