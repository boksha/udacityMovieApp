package com.example.milosevi.rxjavatest.ui.mvp;

import android.util.Log;

import com.example.milosevi.rxjavatest.model.Movie;
import com.example.milosevi.rxjavatest.model.Movies;

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
        disposableList.add(mRepository.getMostPopular().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<Movies>() {

                    @Override
                    public void onNext(Movies movies) {
                        Log.i(TAG, "onNext: " + movies);
                        mView.showMovieList(movies.getMovies());
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
        disposableList.add(mRepository.getTopRated().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<Movies>() {

                    @Override
                    public void onNext(Movies movies) {
                        Log.i(TAG, "onNext: " + movies);
                        mView.showMovieList(movies.getMovies());
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

    private void searchMoviesWithWord(String searchWord) {
        disposableList.add(mRepository.getMoviesWithWord(searchWord).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<Movies>() {

                    @Override
                    public void onNext(Movies movies) {
                        Log.i(TAG, "onNext: " + movies);
                        mView.showMovieList(movies.getMovies());
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


//    private void getGenreList() {
//        disposableList.add(WebApiFetcher.getInstance().getGenres().subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<Genres>() {
//
//
//                    @Override
//                    public void onNext(Genres gitHubRepos) {
//                        Log.i(TAG, "onNext: " + gitHubRepos);
////                movieGridAdapter.setGitHubRepos(gitHubRepos);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.i(TAG, "onError: " + e.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.i(TAG, "onComplete: ");
//                    }
//                }));
//
//    }


}
