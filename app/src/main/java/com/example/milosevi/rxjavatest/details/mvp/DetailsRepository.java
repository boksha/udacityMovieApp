package com.example.milosevi.rxjavatest.details.mvp;

import com.example.milosevi.rxjavatest.details.model.Reviews;
import com.example.milosevi.rxjavatest.details.model.Trailers;
import com.example.milosevi.rxjavatest.webapi.WebApiFetcher;

import io.reactivex.Observable;

/**
 * Created by milosevi on 10/27/17.
 */

public class DetailsRepository implements DetailsContract.Repository {

    private WebApiFetcher mWebApiSource;

    public DetailsRepository() {
        mWebApiSource = WebApiFetcher.getInstance();
    }

    @Override
    public Observable<Trailers> getTrailers(Integer id) {
        return mWebApiSource.getTrailers(id);
    }

    @Override
    public Observable<Reviews> getReviews(Integer id) {
        return mWebApiSource.getReviews(id);
    }
}
