package com.example.milosevi.rxjavatest.ui.mvp;

import com.example.milosevi.rxjavatest.model.Movies;
import com.example.milosevi.rxjavatest.webapi.WebApiFetcher;

import io.reactivex.Observable;

/**
 * Created by milosevi on 10/10/17.
 */

public class GridRepository implements GridContract.Repository {

    private static final String TAG = "GridRepository";
    private WebApiFetcher mWebApiSource;

    public GridRepository() {
        mWebApiSource = WebApiFetcher.getInstance();
    }

    @Override
    public Observable<Movies> getMostPopular() {
        return mWebApiSource.getPopularMovies();
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
