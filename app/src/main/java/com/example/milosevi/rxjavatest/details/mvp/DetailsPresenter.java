package com.example.milosevi.rxjavatest.details.mvp;

import android.util.Log;

import com.example.milosevi.rxjavatest.details.model.Review;
import com.example.milosevi.rxjavatest.details.model.Reviews;
import com.example.milosevi.rxjavatest.details.model.Trailer;
import com.example.milosevi.rxjavatest.details.model.Trailers;
import com.example.milosevi.rxjavatest.model.Movie;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by milosevi on 10/27/17.
 */

public class DetailsPresenter implements DetailsContract.Presenter {

    private static final String TAG = "Miki";
    private CompositeDisposable disposableList = new CompositeDisposable();
    private DetailsContract.View mView;
    private DetailsContract.Repository mRepository;

    public DetailsPresenter(DetailsContract.Repository mRepository) {
        this.mRepository = mRepository;
    }

    @Override
    public void onLoadMovie(Integer id) {
        getMovie(id);
        mView.updateMarkButton(mRepository.isMovieMarked(id));
    }

    @Override
    public void onMovieMarked(Movie movie) {
        boolean mark = ! mRepository.isMovieMarked(movie.getId());
        if (mark) {
            mRepository.markMovie(movie);
        } else {
            mRepository.unmarkMovie(movie);
        }
        mView.updateMarkButton(mark);
        Log.i(TAG, "onMovieMarked: " + mark);
    }

    @Override
    public void onViewAttached(DetailsContract.View view) {
        mView = view;
    }

    @Override
    public void onViewDetached(DetailsContract.View view) {
        mView = null;
    }

    @Override
    public void onActivityDestroyed() {
        if (disposableList != null) {
            Log.i(TAG, "onDestroy: dispose");
            disposableList.clear();
        }
    }

    @Override
    public void onTrailerSelected(String key) {
        mView.navigateToTrailer(key);
    }

    private void getMovie(Integer id) {
        disposableList.add(mRepository.getMovieById(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<Movie>() {

                    @Override
                    public void onNext(Movie movie) {
                        Log.i(TAG, "getMovie onNext: " + movie);
                        mView.showMovie(movie);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "getMovie onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "getMovie onComplete: ");
                    }
                }));
    }
}
