package com.example.milosevi.rxjavatest.ui.mvp;

import android.util.Log;

import com.example.milosevi.rxjavatest.webapi.GitHubFetcher;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by milosevi on 10/10/17.
 */

public class GridRepository implements GridContract.Repository {

    private static final String TAG = "GridRepository";

    public GridRepository(){

    }

    public void getStarredRepos(String username) {
       /* disposable = */

        GitHubFetcher.getInstance().getStarredRepos(username).subscribeOn(Schedulers.io())
                .filter(result->result !=null)
                .flatMap((gitHubRepos) -> Observable.fromIterable(gitHubRepos))
                .take(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( (gitHubRepos) -> {
//                    Observable.fromIterable(gitHubRepos)
//                            .subscribe(gitHub ->    Log.i(TAG, "onNext: " + gitHub +"\n"));
                    Log.i(TAG, "onNext: " + gitHubRepos);
//                        adapter.setGitHubRepos(gitHubRepos);
                }, (e)-> {
//                    e.printStackTrace();
                    Log.e(TAG, "onError: " ,e);}, ()-> {Log.i(TAG, "onComplete: "  );});

    }

    @Override
    public void getMostPopular() {

    }

    @Override
    public void getTopRated() {

    }

    @Override
    public void getMoviesWithWord(String search) {

    }
}
