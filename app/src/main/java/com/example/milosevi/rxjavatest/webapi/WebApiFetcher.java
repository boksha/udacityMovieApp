package com.example.milosevi.rxjavatest.webapi;

import com.example.milosevi.rxjavatest.details.model.Review;
import com.example.milosevi.rxjavatest.details.model.Trailer;
import com.example.milosevi.rxjavatest.model.Genres;
import com.example.milosevi.rxjavatest.model.Movie;
import com.example.milosevi.rxjavatest.model.Movies;
import com.example.milosevi.rxjavatest.details.model.Reviews;
import com.example.milosevi.rxjavatest.details.model.Trailers;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by milosevi on 9/29/17.
 */

public class WebApiFetcher implements NetworkDataSource {
    private static final String API_KEY = "63c792551dfae04485cc8cc06de29fe1";
    private static final String SORT_BY_POPULAR = "popularity.desc";
    private static final String SORT_BY_TOP_RATED = "vote_average.desc";
    private static final String BASE_URL_THEMOVIEDB_ORG_3 = "https://api.themoviedb.org/3/";

    private MovieService service;
    private static WebApiFetcher sInstance;

    private WebApiFetcher() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient)
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

    @Override
    public Observable<List<Movie>> getMovies(int type, int page) {

        //TODO pagination!
        if (type == Movie.TOP_RATED) {
        return  service.getMovies(API_KEY, page, SORT_BY_TOP_RATED).flatMap(movies ->
             Observable.fromArray(movies.getMovies()));
        } else if (type == Movie.MOST_POPULAR) {
            return service.getMovies(API_KEY, page, SORT_BY_POPULAR).flatMap(movies ->
                    Observable.fromArray(movies.getMovies()));
        }
        return Observable.empty();
    }

    @Override
    public Observable<List<Trailer>> getTrailers(Integer id) {
        return service.getTrailers(id, API_KEY).flatMap(trailers ->
                Observable.fromArray(trailers.getTrailers()));
    }

    @Override
    public Observable<List<Review>> getReviews(Integer id) {
        return service.getReviews(id, API_KEY).flatMap(reviews ->
                Observable.fromArray(reviews.getReviewList()));
    }

    public Observable<List<Movie>> findMoviesWithWord(String word) {
        return service.findMoviesWithWord(API_KEY, word).flatMap(movies ->
                Observable.fromArray(movies.getMovies()));
    }

}
