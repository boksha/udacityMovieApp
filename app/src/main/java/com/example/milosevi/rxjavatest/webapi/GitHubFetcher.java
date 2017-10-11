package com.example.milosevi.rxjavatest.webapi;

import com.example.milosevi.rxjavatest.model.GitHubRepo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by milosevi on 9/28/17.
 */

public class GitHubFetcher {
    GitHubService service;
    static GitHubFetcher sInstance;

    private GitHubFetcher(){
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.github.com/")
                .build();
        service = retrofit.create(GitHubService.class);
    }
    public static GitHubFetcher getInstance(){
        if (sInstance == null){
            sInstance= new GitHubFetcher();
        }
        return sInstance;
    }

    public Observable<List<GitHubRepo>> getStarredRepos(String username){
        return service.getGithubRepos(username);
    }
}
