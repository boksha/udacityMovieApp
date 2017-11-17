package com.example.milosevi.rxjavatest.database;

import android.support.annotation.StringDef;
import android.util.Log;

import com.example.milosevi.rxjavatest.database.model.RealmMovie;
import com.example.milosevi.rxjavatest.database.model.RealmMovieFields;
import com.example.milosevi.rxjavatest.model.Movie;

import java.lang.annotation.Retention;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;

import static java.lang.annotation.RetentionPolicy.SOURCE;


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
                RealmMovie realmMovie = realm.where(RealmMovie.class).equalTo(RealmMovieFields.ID, movie.getId()).findFirst();
                if (realmMovie == null) {
                    realmMovie = new RealmMovie(movie);
                    realmMovie.setIsFavourite(true);
                    realm.insertOrUpdate(realmMovie);
                } else {
                    realmMovie.setIsFavourite(true);
                }
            });
        }
    }

    public void removeFromFavourites(Movie movie) {
        try (Realm realmInstance = Realm.getDefaultInstance()) {
            Log.i(TAG, "removeFromFavourites: " + movie);
            realmInstance.executeTransaction((realm) -> {
                RealmMovie realmMovie = realm.where(RealmMovie.class)
                        .equalTo(RealmMovieFields.ID, movie.getId())
                        .equalTo(RealmMovieFields.IS_FAVOURITE, true)
                        .findFirst();

                if (realmMovie.isMostPopular() || realmMovie.isTopRated()) {
                    realmMovie.setIsFavourite(false);
                } else {
                    realmMovie.deleteFromRealm();
                }
            });
        }
    }


    public boolean isMovieInFavouritesById(int id) {
        return getMovieFromFavouritesById(id) != null;
    }

    public RealmMovie getMovieFromFavouritesById(int id) {
        final RealmMovie resultRealm;
        try (Realm realmInstance = Realm.getDefaultInstance()) {
            resultRealm = realmInstance.where(RealmMovie.class)
                    .equalTo(RealmMovieFields.ID, id)
                    .equalTo(RealmMovieFields.IS_FAVOURITE, true)
                    .findFirst();
            Log.i(TAG, "getMovieFromFavouritesById: " + resultRealm);
        }
        return resultRealm;
    }

    public Observable<List<Movie>> getFavouriteMovies() {
        return getMoviesByType(RealmMovieFields.IS_FAVOURITE);
    }

    public Observable<List<Movie>> getTopRatedMovies() {
        return getMoviesByType(RealmMovieFields.IS_TOP_RATED);
    }

    public void deleteTopRatedMovies() {
        try (Realm realmInstance = Realm.getDefaultInstance()) {
            Log.i(TAG, "deleteTopRated: ");
            realmInstance.executeTransaction((realm) -> {
                List<RealmMovie> realmMovies = realm.where(RealmMovie.class)
                        .equalTo(RealmMovieFields.IS_TOP_RATED, true)
                        .findAll();
                for (RealmMovie realmMovie : realmMovies) {
                    if (realmMovie.isMostPopular() || realmMovie.isFavourite()) {
                        realmMovie.setIsTopRated(false);
                    } else {
                        realmMovie.deleteFromRealm();
                    }
                }
            });
        }
    }

    public void addToTopRated(Movie movie) {
        try (Realm realmInstance = Realm.getDefaultInstance()) {
            realmInstance.executeTransaction((realm) -> {
                Log.i(TAG, "addToTopRated: " + movie);
                addTopRatedMovieToRealm(realm, movie);
            });
        }
    }

    public void saveTopRatedList(List<Movie> movies) {
        try (Realm realmInstance = Realm.getDefaultInstance()) {
            Log.i(TAG, "saveTopRatedList: " + movies);
            realmInstance.executeTransaction((realm) -> {
                for (Movie m : movies) {
                    addTopRatedMovieToRealm(realm, m);
                }
            });
        }
    }

    public Observable<List<Movie>> getMostPopularMovies() {
        return getMoviesByType(RealmMovieFields.IS_MOST_POPULAR);
    }

    public void deleteMostPopularMovies() {
        try (Realm realmInstance = Realm.getDefaultInstance()) {
            Log.i(TAG, "deleteMostPopular: ");
            realmInstance.executeTransaction((realm) -> {
                List<RealmMovie> realmMovies = realm.where(RealmMovie.class)
                        .equalTo(RealmMovieFields.IS_MOST_POPULAR, true)
                        .findAll();
                for (RealmMovie realmMovie : realmMovies) {
                    if (realmMovie.isTopRated() || realmMovie.isFavourite()) {
                        realmMovie.setIsMostPopular(false);
                    } else {
                        realmMovie.deleteFromRealm();
                    }
                }
            });
        }
    }

    public void addToMostPopular(Movie movie) {
        try (Realm realmInstance = Realm.getDefaultInstance()) {
            Log.i(TAG, "addToMostPopular: " + movie);
            realmInstance.executeTransaction((realm) -> {
                addMostPopularMovieToRealm(realm, movie);
            });
        }
    }


    public void saveMostPopularList(List<Movie> movies) {
        try (Realm realmInstance = Realm.getDefaultInstance()) {
            Log.i(TAG, "saveMostPopularList: " + movies);
            realmInstance.executeTransaction((realm) -> {
                for (Movie m : movies) {
                    addMostPopularMovieToRealm(realm, m);
                }
            });
        }
    }

    private Observable<List<Movie>> getMoviesByType(String field) {
        try (Realm realmInstance = Realm.getDefaultInstance()) {
            RealmResults<RealmMovie> realmResults = realmInstance.where(RealmMovie.class)
                    .equalTo(field, true)
                    .findAll();
            final List<Movie> movies = new ArrayList<>();
//TODO dont use it like this, you are losing realm zero copy feature - use raelm driven architecture!!!
            for (RealmMovie realmMovie : realmResults) {
                movies.add(new Movie(realmMovie));//TO DO change this to Mapper
            }
            return Observable.just(movies);
        }
    }

    private void addTopRatedMovieToRealm(Realm realm, Movie m) {
        RealmMovie realmMovie = realm.where(RealmMovie.class).equalTo(RealmMovieFields.ID, m.getId()).findFirst();
        if (realmMovie == null) {
            realmMovie = new RealmMovie(m);
            realmMovie.setIsTopRated(true);
            realm.insertOrUpdate(realmMovie);
        } else {
            realmMovie.setIsTopRated(true);
        }
    }

    private void addMostPopularMovieToRealm(Realm realm, Movie m) {
        RealmMovie realmMovie = realm.where(RealmMovie.class).equalTo(RealmMovieFields.ID, m.getId()).findFirst();
        if (realmMovie == null) {
            realmMovie = new RealmMovie(m);
            realmMovie.setIsMostPopular(true);
            realm.insertOrUpdate(realmMovie);
        } else {
            realmMovie.setIsMostPopular(true);
        }
    }
}
