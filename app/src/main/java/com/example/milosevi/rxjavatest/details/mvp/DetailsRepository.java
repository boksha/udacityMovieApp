package com.example.milosevi.rxjavatest.details.mvp;

import com.example.milosevi.rxjavatest.database.DataBaseManager;
import com.example.milosevi.rxjavatest.database.DbDataSource;
import com.example.milosevi.rxjavatest.details.model.Review;
import com.example.milosevi.rxjavatest.details.model.Reviews;
import com.example.milosevi.rxjavatest.details.model.Trailer;
import com.example.milosevi.rxjavatest.details.model.Trailers;
import com.example.milosevi.rxjavatest.model.Movie;
import com.example.milosevi.rxjavatest.webapi.WebApiFetcher;

import java.util.List;

import io.reactivex.Observable;

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
    public Observable<List<Trailer>> getTrailers(Integer id) {
        return mWebApiSource.getTrailers(id);
    }

    @Override
    public Observable<List<Review>> getReviews(Integer id) {
        return mWebApiSource.getReviews(id);
    }


}
