package com.example.milosevi.rxjavatest.database;

import android.util.Log;

import com.example.milosevi.rxjavatest.model.Movie;
import com.example.milosevi.rxjavatest.model.MovieFields;
import com.example.milosevi.rxjavatest.webapi.MovieService;
import com.example.milosevi.rxjavatest.webapi.WebApiFetcher;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by miodrag.milosevic on 11/3/2017.
 */

public class DataBaseManager {
    private static final String TAG = "Miki";

    private static DataBaseManager sInstance;

    private DataBaseManager() {
    }

    public static DataBaseManager getInstance() {
        if (sInstance == null) {
            sInstance = new DataBaseManager();
        }
        return sInstance;
    }

    public void addToFavourites(Movie movie) {
        try (Realm realmInstance = Realm.getDefaultInstance()) {
            Log.i(TAG, "addToFavourites: " + movie.isMarked());
            realmInstance.executeTransaction((realm) -> realm.insertOrUpdate(movie));
        }
    }

    public void removeFromFavourites(Movie movie) {
        try (Realm realmInstance = Realm.getDefaultInstance()) {
            Log.i(TAG, "removeFromFavourites: " + movie.isMarked());
            realmInstance.executeTransaction((realm) -> {
                RealmResults<Movie> result = realm.where(Movie.class).equalTo(MovieFields.ID, movie.getId()).findAll();
                result.deleteAllFromRealm();
            });
        }
    }

    public boolean isMovieInFavouritesById(int id) {
        final Movie resultRealm;
        try (Realm realmInstance = Realm.getDefaultInstance()) {
            resultRealm = realmInstance.where(Movie.class).equalTo(MovieFields.ID, id).findFirst();
            Log.i(TAG, "isMovieInFavouritesById: " + resultRealm);
        }
        return resultRealm != null;
    }

    public Movie getMovieFromFavouritesById(int id) {
        final Movie resultRealm;
        try (Realm realmInstance = Realm.getDefaultInstance()) {
            resultRealm = realmInstance.where(Movie.class).equalTo(MovieFields.ID, id).findFirst();
            Log.i(TAG, "getMovieFromFavouritesById: " + resultRealm);
        }
        return resultRealm ;
    }

    public Observable<List<Movie>> getFavouriteMovies() {
        try (Realm realmInstance = Realm.getDefaultInstance()) {
            Log.i(TAG, "getFavouriteMovies: ");

// Query in the background
            RealmResults<Movie> realmResults = realmInstance.where(Movie.class)
                    .findAllAsync();

// Use ChangeListeners to be notified about updates
            final List<Movie> movies = new ArrayList<>();
//TODO dont use it like this, you are losing realm zero copy feature - use raalm driven architecture!!!
            for (Movie realmMovie : realmResults) {
                movies.add(new Movie(realmMovie));
            }
// Or RxJava
            return Observable.just(movies);
        }
    }
}
