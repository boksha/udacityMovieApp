package com.example.milosevi.rxjavatest.database;

import android.util.Log;

import com.example.milosevi.rxjavatest.database.model.FavouriteMovieRealm;
import com.example.milosevi.rxjavatest.database.model.FavouriteMovieRealmFields;
import com.example.milosevi.rxjavatest.database.model.MostPopularMovieRealm;
import com.example.milosevi.rxjavatest.database.model.TopRatedMovieRealm;
import com.example.milosevi.rxjavatest.model.Movie;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;


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
            Log.i(TAG, "addToFavourites: " + movie);
            realmInstance.executeTransaction((realm) -> {
                FavouriteMovieRealm result = new FavouriteMovieRealm(movie);
                    realm.insertOrUpdate(result);
            });
        }
    }

    public void removeFromFavourites(Movie movie) {
        try (Realm realmInstance = Realm.getDefaultInstance()) {
            Log.i(TAG, "removeFromFavourites: " + movie);
            realmInstance.executeTransaction((realm) -> {
                FavouriteMovieRealm result = realm.where(FavouriteMovieRealm.class)
                        .equalTo(FavouriteMovieRealmFields.ID, movie.getId())
                        .findFirst();
                result.deleteFromRealm();
            });
        }
    }



    public boolean isMovieInFavouritesById(int id) {
        final FavouriteMovieRealm resultRealm;
        try (Realm realmInstance = Realm.getDefaultInstance()) {
            resultRealm = realmInstance.where(FavouriteMovieRealm.class)
                    .equalTo(FavouriteMovieRealmFields.ID, id)
              .findFirst();
            Log.i(TAG, "isMovieInFavouritesById: " + resultRealm);
        }
        return resultRealm != null;
    }

    public FavouriteMovieRealm getMovieFromFavouritesById(int id) {
        final FavouriteMovieRealm resultRealm;
        try (Realm realmInstance = Realm.getDefaultInstance()) {
            resultRealm = realmInstance.where(FavouriteMovieRealm.class).equalTo(FavouriteMovieRealmFields.ID, id)
                  .findFirst();
            Log.i(TAG, "getMovieFromFavouritesById: " + resultRealm);
        }
        return resultRealm;
    }

    public Observable<List<Movie>> getFavouriteMovies() {
        try (Realm realmInstance = Realm.getDefaultInstance()) {
            Log.i(TAG, "getFavouriteMovies: ");
            RealmResults<FavouriteMovieRealm> realmResults = realmInstance.where(FavouriteMovieRealm.class)
                    .findAllAsync();
            final List<Movie> movies = new ArrayList<>();
//TODO dont use it like this, you are losing realm zero copy feature - use raelm driven architecture!!!
            for (FavouriteMovieRealm realmMovie : realmResults) {
                movies.add(new Movie(realmMovie));
            }
            return Observable.just(movies);
        }
    }

    public Observable<List<Movie>> getTopRatedMovies() {
        try (Realm realmInstance = Realm.getDefaultInstance()) {
            Log.i(TAG, "getTopRatedMovies: ");
            RealmResults<TopRatedMovieRealm> realmResults = realmInstance.where(TopRatedMovieRealm.class)
                    .findAllAsync();
            final List<Movie> movies = new ArrayList<>();
//TODO dont use it like this, you are losing realm zero copy feature - use raelm driven architecture!!!
            for (TopRatedMovieRealm realmMovie : realmResults) {
                movies.add(new Movie(realmMovie));//TO DO change this
            }
            return Observable.just(movies);
        }
    }

    public void deleteTopRatedMovies() {
        try (Realm realmInstance = Realm.getDefaultInstance()) {
            Log.i(TAG, "deleteTopRated: " );
            realmInstance.executeTransaction((realm) -> {
                realm.delete(TopRatedMovieRealm.class);
            });
        }
    }

    public void addToTopRated(Movie movie) {
        try (Realm realmInstance = Realm.getDefaultInstance()) {
            Log.i(TAG, "addToTopRated: " + movie);
            realmInstance.executeTransaction((realm) -> {
                TopRatedMovieRealm result = new TopRatedMovieRealm(movie);
                realm.insertOrUpdate(result);
            });
        }
    }

    public void saveTopRatedList(List<Movie> movies) {
        try (Realm realmInstance = Realm.getDefaultInstance()) {
            Log.i(TAG, "saveTopRatedList: " + movies);
            realmInstance.executeTransaction((realm) -> {
                for (Movie m : movies) {
                    TopRatedMovieRealm result = new TopRatedMovieRealm(m);
                    realm.insertOrUpdate(result);
                }
            });
        }
    }

    public Observable<List<Movie>> getMostPopularMovies() {
        try (Realm realmInstance = Realm.getDefaultInstance()) {
            Log.i(TAG, "getMostPopularMovies: ");
            RealmResults<MostPopularMovieRealm> realmResults = realmInstance.where(MostPopularMovieRealm.class)
                    .findAllAsync();
            final List<Movie> movies = new ArrayList<>();
//TODO dont use it like this, you are losing realm zero copy feature - use raelm driven architecture!!!
            for (MostPopularMovieRealm realmMovie : realmResults) {
                movies.add(new Movie(realmMovie));//TO DO change this
            }
// Or RxJava
            return Observable.just(movies);
        }
    }


    public void deleteMostPopularMovies() {
        try (Realm realmInstance = Realm.getDefaultInstance()) {
            Log.i(TAG, "deleteMostPopular: " );
            realmInstance.executeTransaction((realm) -> {
                realm.delete(MostPopularMovieRealm.class);
            });
        }
    }

    public void addToMostPopular(Movie movie) {
        try (Realm realmInstance = Realm.getDefaultInstance()) {
            Log.i(TAG, "addToMostPopular: " + movie);
            realmInstance.executeTransaction((realm) -> {
                MostPopularMovieRealm result = new MostPopularMovieRealm(movie);
                realm.insertOrUpdate(result);
            });
        }
    }

    public void saveMostPopularList(List<Movie> movies) {
        try (Realm realmInstance = Realm.getDefaultInstance()) {
            Log.i(TAG, "saveMostPopularList: " + movies);
            realmInstance.executeTransaction((realm) -> {
                for (Movie m : movies) {
                    MostPopularMovieRealm result = new MostPopularMovieRealm(m);
                    realm.insertOrUpdate(result);
                }
            });
        }
    }
}
