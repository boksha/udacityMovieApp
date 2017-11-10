package com.example.milosevi.rxjavatest.ui.mvp;

import com.example.milosevi.rxjavatest.database.DataBaseManager;
import com.example.milosevi.rxjavatest.model.Movie;
import com.example.milosevi.rxjavatest.model.Movies;
import com.example.milosevi.rxjavatest.webapi.WebApiFetcher;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.realm.RealmResults;

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
    public Observable<Movies> getMostPopular() {
        return mWebApiSource.getPopularMovies();
    }

    @Override
    public Observable<List<Movie>> getFavourites() {
        return mDatabaseSource.getFavouriteMovies();
    }

    @Override
    public Observable<Movies> getTopRated() {
        return mWebApiSource.getTopRatedMovies();

    }

    @Override
    public Observable<Movies> getMoviesWithWord(String search) {
        return mWebApiSource.findMoviesWithWord(search);
    }
}
