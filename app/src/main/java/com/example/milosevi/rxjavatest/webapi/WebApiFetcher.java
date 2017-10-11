package com.example.milosevi.rxjavatest.webapi;

import com.example.milosevi.rxjavatest.model.Genres;
import com.example.milosevi.rxjavatest.model.Movies;
import com.example.milosevi.rxjavatest.model.Reviews;
import com.example.milosevi.rxjavatest.model.Trailers;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by milosevi on 9/29/17.
 */

public class WebApiFetcher {
    private static final String API_KEY = "63c792551dfae04485cc8cc06de29fe1";
    private static final String BASE_URL_THEMOVIEDB_ORG_3 = "https://api.themoviedb.org/3/";


    private MovieService service;
    private static WebApiFetcher sInstance;

    private WebApiFetcher() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL_THEMOVIEDB_ORG_3)
                .build();
        service = retrofit.create(MovieService.class);
    }

    public static WebApiFetcher getInstance() {
        if (sInstance == null) {
            sInstance = new WebApiFetcher();
        }
        return sInstance;
    }

    public Observable<Genres> getGenres() {
        return service.getGenres(API_KEY);
    }

    public Observable<Movies> getPopularMovies() {
        return service.getPopularMovies(API_KEY);
    }

    public Observable<Movies> getTopRatedMovies() {
        return service.getTopRatedMovies(API_KEY);
    }

    public Observable<Trailers> getTrailers(Integer id) {
        return service.getTrailers(id, API_KEY);
    }

    public Observable<Reviews> getReviews(Integer id) {
        return service.getReviews(id, API_KEY);
    }

    public Observable<Movies> findMoviesWithWord(String word) {
        return service.findMoviesWithWord(API_KEY, word);
    }

}
