package com.example.milosevi.rxjavatest.entrylist.mvp;

import android.util.Log;

import com.example.milosevi.rxjavatest.model.Movie;
import com.example.milosevi.rxjavatest.model.Movies;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by milosevi on 10/10/17.
 */

public class GridPresenter implements GridContract.Presenter {

    private static final String TAG = "Miki";
    private CompositeDisposable disposableList = new CompositeDisposable();
    private GridContract.Repository mRepository;
    private GridContract.View mView;

    public GridPresenter(GridContract.Repository repository) {
        mRepository = repository;
    }

    @Override
    public void onMovieClicked(Movie movie) {
        mView.navigateToMovie(movie);
    }

    @Override
    public void onActivityDestroyed() {
        if (disposableList != null) {
            Log.i(TAG, "onDestroy: dispose");
            disposableList.clear();
        }
    }

    @Override
    public void onMenuItemClicked(@MenuMode int menuMode) {
        if (menuMode == MENU_ITEM_MOST_POPULAR) {
            getMostPopularMovies();
        } else if (menuMode == MENU_ITEM_TOP_RATED) {
            getTopRated();
        } else if (menuMode == MENU_ITEM_FAVOURITES){
            getFavourites();
        }
    }

    @Override
    public void onLoadMovieList() {
        getMostPopularMovies();
    }

    @Override
    public void onSearch(String searchWord) {
        searchMoviesWithWord(searchWord);
    }

    @Override
    public void onViewAttached(GridContract.View view) {
        mView = view;
    }

    @Override
    public void onViewDetached(GridContract.View view) {
        mView = null;
    }

    private void getMostPopularMovies() {
        disposableList.clear();
        disposableList.add(mRepository.getMostPopular().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<List<Movie>>() {

                    @Override
                    public void onNext(List<Movie> movies) {
                        Log.i(TAG, "getMostPopularMovies onNext: " + movies);
                        mView.showMovieList(movies);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "getMostPopularMovies onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "getMostPopularMovies onComplete: ");
                    }
                }));
    }
    private void getFavourites() {
        disposableList.clear();
        disposableList.add(mRepository.getFavourites().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<List<Movie>>() {

                    @Override
                    public void onNext(List<Movie> movies) {
                        Log.i(TAG, "getFavourites onNext: " + movies);
                        mView.showMovieList(movies);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: ");
                    }
                }));
    }

    private void getTopRated() {
        disposableList.clear();
        disposableList.add(mRepository.getTopRated().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<List<Movie>>() {

                    @Override
                    public void onNext(List<Movie> movies) {
                        Log.i(TAG, "getTopRated onNext: " + movies);
                        mView.showMovieList(movies);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "getTopRated onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "getTopRated onComplete: ");
                    }
                }));
    }

    private void searchMoviesWithWord(String searchWord) {
        disposableList.clear();
        disposableList.add(mRepository.getMoviesWithWord(searchWord).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<List<Movie>>() {

                    @Override
                    public void onNext(List<Movie> movies) {
                        Log.i(TAG, "searchMoviesWithWord onNext: " + movies);
                        mView.showMovieList(movies);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: ");
                    }
                }));

    }
}
