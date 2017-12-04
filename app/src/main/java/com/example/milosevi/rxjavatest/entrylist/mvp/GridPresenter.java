package com.example.milosevi.rxjavatest.entrylist.mvp;

import android.util.Log;

import com.example.milosevi.rxjavatest.model.Movie;

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
    private static final int STARTING_PAGE = 1;
    private CompositeDisposable disposableList = new CompositeDisposable();
    private GridContract.Repository mRepository;
    private GridContract.View mView;
    private boolean mIsLoading = false;

    @ListMode
    private int mCurrentListMode = LIST_MOST_POPULAR;


    public GridPresenter(GridContract.View view, GridContract.Repository repository) {
        mRepository = repository;
        mView = view;
    }

    @Override
    public void onMovieClicked(Movie movie) {
        disposableList.clear();
        mIsLoading = false;
        mView.navigateToMovie(movie);
    }

    @Override
    public void onActivityDestroyed() {
        if (disposableList != null) {
            Log.i(TAG, "onDestroy: dispose");
            disposableList.clear();
            mIsLoading = false;
        }
    }

    @Override
    public boolean isLoading() {
        return mIsLoading;
    }

    @Override
    public void onMenuItemClicked(@ListMode int listMode) {
        if (listMode != mCurrentListMode) {
            mIsLoading = false;
            mView.clearMovieList();
            mView.resetScrollPosition();
            mCurrentListMode = listMode;
            loadPage(STARTING_PAGE);
        }
    }

    @Override
    public void onLoadMovieList() {
        loadPage(STARTING_PAGE);
    }

    @Override
    public void onLoadMovieListByPage(int page) {
        loadPage(page);
    }


    @Override
    public void onSearch(String searchWord) {
        searchMoviesWithWord(searchWord);
    }

    @Override
    public void onViewAttached(GridContract.View view) {
//        mView = view;
    }

    @Override
    public void onViewDetached(GridContract.View view) {
//        mView = null;
    }

    private void getMostPopularMovies(int page) {
        disposableList.clear();
        mIsLoading = true;
        disposableList.add(mRepository.getMostPopular(page).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<List<Movie>>() {

                    @Override
                    public void onNext(List<Movie> movies) {
                        Log.i(TAG, "getMostPopularMovies onNext: " + movies);
                        if (page == STARTING_PAGE){
                            mView.showMovieList(movies);
                        } else {
                            mView.addMoviesToList(movies);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "getMostPopularMovies onError: " + e.getMessage());
                       mIsLoading = false;
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "getMostPopularMovies onComplete: ");
                        mIsLoading = false;
                    }
                }));
    }

    private void getFavourites() {
        disposableList.clear();
        mIsLoading = false;
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

    private void getTopRated(int page) {
        disposableList.clear();
        mIsLoading = true;
        disposableList.add(mRepository.getTopRated(page).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<List<Movie>>() {

                    @Override
                    public void onNext(List<Movie> movies) {
                        Log.i(TAG, "getTopRated onNext: " + movies);
                        if (page == STARTING_PAGE){
                            mView.showMovieList(movies);
                        } else {
                            mView.addMoviesToList(movies);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "getTopRated onError: " + e.getMessage());
                        mIsLoading = false;
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "getTopRated onComplete: ");
                        mIsLoading = false;
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

    private void loadList(@ListMode int listMode, int page) {
        if (listMode == LIST_MOST_POPULAR) {
            getMostPopularMovies(page);
        } else if (listMode == LIST_TOP_RATED) {
            getTopRated(page);
        } else if (listMode == LIST_FAVOURITES) {
            getFavourites();
        }
    }

    private void loadPage(int page) {
        loadList(mCurrentListMode,page);
    }
}
