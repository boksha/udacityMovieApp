package com.example.milosevi.rxjavatest.details.mvp;

import android.util.Log;

import com.example.milosevi.rxjavatest.database.DataBaseManager;
import com.example.milosevi.rxjavatest.database.DbDataSource;
import com.example.milosevi.rxjavatest.details.model.Review;
import com.example.milosevi.rxjavatest.details.model.Reviews;
import com.example.milosevi.rxjavatest.details.model.Trailer;
import com.example.milosevi.rxjavatest.details.model.Trailers;
import com.example.milosevi.rxjavatest.model.Movie;
import com.example.milosevi.rxjavatest.webapi.WebApiFetcher;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;

/**
 * Created by milosevi on 10/27/17.
 */

public class DetailsRepository implements DetailsContract.Repository {

    private WebApiFetcher mWebApiSource;
    private DbDataSource mDatabaseSource;

    public DetailsRepository() {
        mWebApiSource = WebApiFetcher.getInstance();
        mDatabaseSource = DataBaseManager.getInstance();
    }

    @Override
    public void markMovie(Movie movie) {
        mDatabaseSource.addMovie(movie, Movie.FAVOURITE);
    }

    @Override
    public void unmarkMovie(Movie movie) {
        mDatabaseSource.removeMovie(movie, Movie.FAVOURITE);
    }

    @Override
    public boolean isMovieMarked(Integer id) {
        return mDatabaseSource.isMovieInDb(id, Movie.FAVOURITE);
    }

    @Override
    public Observable<Movie> getMovieById(Integer id) {
        Observable<Movie> movieDB = mDatabaseSource.getMovie(id);//.publish().autoConnect(2);
        Observable<Movie> movieCloud = movieDB
                .flatMap(m -> Observable.zip(mWebApiSource.getTrailers(id)
                        .retryWhen(errors ->
                        errors.zipWith(Observable.range(1, 3), (n, i) -> i)
                                .flatMap(retryCount -> {
                                    Log.i("Miki", "getMovieById: retry tra" + retryCount);
                                    return Observable.timer((long) Math.pow(5, retryCount), TimeUnit.SECONDS);
                                })
                ), mWebApiSource.getReviews(id) .retryWhen(errors ->
                        errors.zipWith(Observable.range(1, 3), (n, i) -> i)
                                .flatMap(retryCount -> {
                                    Log.i("Miki", "getMovieById: retry rev" + retryCount);
                                    return Observable.timer((long) Math.pow(5, retryCount), TimeUnit.SECONDS);
                                })
                ), (trailers, reviews) -> {
                    Log.i("Miki", "getMovieById: " + m + " " + trailers + " " + reviews);
                    m.setReviews(reviews);
                    m.setTrailers(trailers);
                    mDatabaseSource.updateMovie(m);
                    return (m);
                }));
//                .retryWhen(errors ->
//                        errors.zipWith(Observable.range(1, 3), (n, i) -> i)
//                                .flatMap(retryCount -> {
//                                    Log.i("Miki", "getMovieById: retry" + retryCount);
//                                    return Observable.timer((long) Math.pow(5, retryCount), TimeUnit.SECONDS);
//                                })
//                );
//        return  movieCloud;
        return Observable.concat(movieDB, movieCloud);
    }
}
