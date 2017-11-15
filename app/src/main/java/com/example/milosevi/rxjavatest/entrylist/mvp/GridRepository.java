package com.example.milosevi.rxjavatest.entrylist.mvp;

import android.util.Log;

import com.example.milosevi.rxjavatest.database.DataBaseManager;
import com.example.milosevi.rxjavatest.model.Movie;
import com.example.milosevi.rxjavatest.model.Movies;
import com.example.milosevi.rxjavatest.webapi.WebApiFetcher;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by milosevi on 10/10/17.
 */

public class GridRepository implements GridContract.Repository {

    private static final String TAG = "GridRepository";
    private WebApiFetcher mWebApiSource;
    private DataBaseManager mDatabaseSource;

    public GridRepository() {
        mWebApiSource = WebApiFetcher.getInstance();
        mDatabaseSource = DataBaseManager.getInstance();
    }

    @Override
    public Observable<List<Movie>> getMostPopular() {
        Observable<List<Movie>> movieListDB =     mDatabaseSource.getMostPopularMovies()
                .filter(movies -> movies.size() > 0);
        Observable<List<Movie>> movieListCloud =  mWebApiSource.getPopularMovies()
                .doOnNext((movies) -> {
            mDatabaseSource.deleteMostPopularMovies();
            mDatabaseSource.saveMostPopularList(movies);
            Log.i("Miki", "getMostPopular: save finished");

        }) .onErrorResumeNext(Observable.empty());

        return Observable.concat(movieListDB, movieListCloud);
    }

    @Override
    public Observable<List<Movie>> getFavourites() {
        return mDatabaseSource.getFavouriteMovies();
    }

    @Override
    public Observable<List<Movie>> getTopRated() {
        Observable<List<Movie>> movieListDB =     mDatabaseSource.getTopRatedMovies()
                .filter(movies -> movies.size() > 0);

        Observable<List<Movie>> movieListCloud =  mWebApiSource.getTopRatedMovies().onErrorResumeNext(Observable.empty())
                .doOnNext((movies) -> {
            mDatabaseSource.deleteTopRatedMovies();
            mDatabaseSource.saveTopRatedList(movies);
            Log.i("Miki", "getTopRated: save finished");
        });
        return Observable.concat(movieListDB, movieListCloud);
    }

    @Override
    public Observable<List<Movie>> getMoviesWithWord(String search) {
        return mWebApiSource.findMoviesWithWord(search);
    }
}
