package com.example.milosevi.rxjavatest.entrylist.mvp;

import android.icu.text.UnicodeSetSpanner;
import android.util.Log;

import com.example.milosevi.rxjavatest.database.DataBaseManager;
import com.example.milosevi.rxjavatest.database.DbDataSource;
import com.example.milosevi.rxjavatest.model.Movie;
import com.example.milosevi.rxjavatest.webapi.NetworkDataSource;
import com.example.milosevi.rxjavatest.webapi.WebApiFetcher;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**
 * Created by milosevi on 10/10/17.
 */

public class GridRepository implements GridContract.Repository {

    private static final String TAG = "Miki";
    private NetworkDataSource mWebApiSource;
    private DbDataSource mDatabaseSource;

    public GridRepository() {
        mWebApiSource = WebApiFetcher.getInstance();
        mDatabaseSource = DataBaseManager.getInstance();
    }

    @Override
    public Observable<List<Movie>> getMostPopular() {
        Observable<List<Movie>> movieListDB = mDatabaseSource.queryMovies(Movie.MOST_POPULAR)
                .filter(movies -> movies.size() > 0);
        Observable<List<Movie>> movieListCloud = mWebApiSource.getMovies(Movie.MOST_POPULAR)
                .retryWhen(errors ->
                        errors.zipWith(Observable.range(1, 3), (n, i) -> i)
                                .flatMap(retryCount -> {
                                    Log.i(TAG, "getMostPopular: retry" + retryCount);
                                    return Observable.timer((long) Math.pow(5, retryCount), TimeUnit.SECONDS);
                                })
//  for onError after retry?.flatMap(it -> it < count ? Observable.timer(it, TimeUnit.SECONDS) : t.flatMap(Observable::error));
                )
                .doOnNext((movies) -> {
                    mDatabaseSource.deleteAllMovies(Movie.MOST_POPULAR);
                    mDatabaseSource.addMovieList(movies, Movie.MOST_POPULAR);
                    Log.i(TAG, "getMostPopular: save finished");

                });
        return Observable.concat(movieListDB, movieListCloud);
    }

    @Override
    public Observable<List<Movie>> getFavourites() {
        return mDatabaseSource.queryMovies(Movie.FAVOURITE);
    }

    @Override
    public Observable<List<Movie>> getTopRated() {
        Observable<List<Movie>> movieListDB = mDatabaseSource.queryMovies(Movie.TOP_RATED)
                .filter(movies -> movies.size() > 0);

        Observable<List<Movie>> movieListCloud = mWebApiSource.getMovies(Movie.TOP_RATED)
                .retryWhen(errors ->
                        errors.zipWith(Observable.range(1, 3), (n, i) -> i)
                                .flatMap(retryCount -> {
                                    Log.i(TAG, "getTopRated: retry" + retryCount);
                                    return Observable.timer((long) Math.pow(5, retryCount), TimeUnit.SECONDS);
                                })
                )
                .doOnNext((movies) -> {
                    mDatabaseSource.deleteAllMovies(Movie.TOP_RATED);
                    mDatabaseSource.addMovieList(movies, Movie.TOP_RATED);
                    Log.i(TAG, "getTopRated: save finished");
                });
        return Observable.concat(movieListDB, movieListCloud);
    }

    @Override
    public Observable<List<Movie>> getMoviesWithWord(String search) {
        return mWebApiSource.findMoviesWithWord(search);
    }
}
