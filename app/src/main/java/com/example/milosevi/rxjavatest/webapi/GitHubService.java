package com.example.milosevi.rxjavatest.webapi;

import com.example.milosevi.rxjavatest.model.GitHubRepo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by milosevi on 9/28/17.
 */

public interface GitHubService {


    @GET("users/{user}/repos")
    Observable<List<GitHubRepo>> getGithubRepos(@Path("user") String user);

}
