package com.example.milosevi.rxjavatest.details.mvp;

import android.util.Log;

import com.example.milosevi.rxjavatest.details.model.Reviews;
import com.example.milosevi.rxjavatest.details.model.Trailers;
import com.example.milosevi.rxjavatest.model.Movie;

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
    public void onLoadTrailerList(Integer id) {
        fetchTrailers(id);
    }

    @Override
    public void onLoadReviewList(Integer id) {
        fetchReviews(id);
    }

    @Override
    public void onLoadMovie(Integer id) {
        mView.updateMarkButton(mRepository.isMovieMarked(id));
    }

    @Override
    public void onMovieMarked(Movie movie) {
        boolean mark = ! mRepository.isMovieMarked(movie.getId());
//        movie.setMarked(!movie.isMarked());
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

    private void fetchTrailers(Integer id) {
        disposableList.add(mRepository.getTrailers(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<Trailers>() {

                    @Override
                    public void onNext(Trailers trailers) {
                        Log.i(TAG, "onNext: " + trailers);
//                        watchYoutubeVideo(getApplicationContext(),trailers.getTrailers().get(0).getKey());
                        mView.showTrailerList(trailers.getTrailers());
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

    private void fetchReviews(Integer id) {
        disposableList.add(mRepository.getReviews(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<Reviews>() {

                    @Override
                    public void onNext(Reviews reviews) {
                        Log.i(TAG, "onNext: " + reviews);
                        mView.showReviewList(reviews.getReviewList());
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
