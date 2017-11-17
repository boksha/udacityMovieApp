package com.example.milosevi.rxjavatest.webapi;

import com.example.milosevi.rxjavatest.details.model.Reviews;
import com.example.milosevi.rxjavatest.details.model.Trailers;
import com.example.milosevi.rxjavatest.model.Genres;
import com.example.milosevi.rxjavatest.model.Movie;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by miodrag.milosevic on 11/17/2017.
 */

public interface NetworkDataSource {
    Observable<Genres> getGenres();

    Observable<List<Movie>> getMovies(@Movie.Type int type);

    Observable<Trailers> getTrailers(Integer id);

    Observable<Reviews> getReviews(Integer id);

    Observable<List<Movie>> findMoviesWithWord(String word);
}
