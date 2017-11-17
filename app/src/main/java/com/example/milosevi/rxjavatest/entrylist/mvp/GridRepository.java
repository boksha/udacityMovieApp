package com.example.milosevi.rxjavatest.entrylist.mvp;

import android.util.Log;

import com.example.milosevi.rxjavatest.database.DataBaseManager;
import com.example.milosevi.rxjavatest.model.Movie;
import com.example.milosevi.rxjavatest.model.Movies;
import com.example.milosevi.rxjavatest.webapi.WebApiFetcher;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by milosevi on 10/10/17.
 */

public class GridRepository implements GridContract.Repository {

    private static final String TAG = "Miki";
    private WebApiFetcher mWebApiSource;
    private DataBaseManager mDatabaseSource;

    public GridRepository() {
        mWebApiSource = WebApiFetcher.getInstance();
        mDatabaseSource = DataBaseManager.getInstance();
    }

    @Override
    public Observable<List<Movie>> getMostPopular() {
        Observable<List<Movie>> movieListDB = mDatabaseSource.getMostPopularMovies()
                .filter(movies -> movies.size() > 0);
        Observable<List<Movie>> movieListCloud = mWebApiSource.getPopularMovies()
                .retryWhen(errors ->
                        errors.zipWith(Observable.range(1, 3), (n, i) -> i)
                                .flatMap(retryCount -> {
                                    Log.i(TAG, "getMostPopular: retry" + retryCount);
                                    return Observable.timer((long) Math.pow(5, retryCount), TimeUnit.SECONDS);
                                })
//  for onError after retry?.flatMap(it -> it < count ? Observable.timer(it, TimeUnit.SECONDS) : t.flatMap(Observable::error));
                )
                .doOnNext((movies) -> {
                    mDatabaseSource.deleteMostPopularMovies();
                    mDatabaseSource.saveMostPopularList(movies);
                    Log.i(TAG, "getMostPopular: save finished");

                });
        return Observable.concat(movieListDB, movieListCloud);
    }

    @Override
    public Observable<List<Movie>> getFavourites() {
        return mDatabaseSource.getFavouriteMovies();
    }

    @Override
    public Observable<List<Movie>> getTopRated() {
        Observable<List<Movie>> movieListDB = mDatabaseSource.getTopRatedMovies()
                .filter(movies -> movies.size() > 0);

        Observable<List<Movie>> movieListCloud = mWebApiSource.getTopRatedMovies()
                .retryWhen(errors ->
                        errors.zipWith(Observable.range(1, 3), (n, i) -> i)
                                .flatMap(retryCount -> {
                                    Log.i(TAG, "getTopRated: retry" + retryCount);
                                    return Observable.timer((long) Math.pow(5, retryCount), TimeUnit.SECONDS);
                                })
                )
                .doOnNext((movies) -> {
                    mDatabaseSource.deleteTopRatedMovies();
                    mDatabaseSource.saveTopRatedList(movies);
                    Log.i(TAG, "getTopRated: save finished");
                });
        return Observable.concat(movieListDB, movieListCloud);
    }

    @Override
    public Observable<List<Movie>> getMoviesWithWord(String search) {
        return mWebApiSource.findMoviesWithWord(search);
    }
}
