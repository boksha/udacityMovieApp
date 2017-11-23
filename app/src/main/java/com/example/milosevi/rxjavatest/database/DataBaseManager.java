package com.example.milosevi.rxjavatest.database;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.milosevi.rxjavatest.database.model.RealmMovie;
import com.example.milosevi.rxjavatest.database.model.RealmMovieFields;
import com.example.milosevi.rxjavatest.database.model.RealmMovieMapper;
import com.example.milosevi.rxjavatest.database.model.RealmReview;
import com.example.milosevi.rxjavatest.database.model.RealmTrailer;
import com.example.milosevi.rxjavatest.model.Movie;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;


/**
 * Created by miodrag.milosevic on 11/3/2017.
 */

public class DataBaseManager implements DbDataSource {

    private static final String TAG = "Miki";

    private static DataBaseManager sInstance;
    private RealmMovieMapper mRealmMovieMapper;

    private DataBaseManager() {
        mRealmMovieMapper = new RealmMovieMapper();
    }

    public static DataBaseManager getInstance() {
        if (sInstance == null) {
            sInstance = new DataBaseManager();
        }
        return sInstance;
    }

    @Override
    public void addMovie(Movie movie, @Movie.Type int type) {
        try (Realm realmInstance = Realm.getDefaultInstance()) {
            Log.i(TAG, "addToFavourites: " + movie);
            realmInstance.executeTransaction((realm) -> {
                addMovieToRealmByType(realm, movie, convertTypeToField(type));
            });
        }
    }

    @Override
    public void removeMovie(Movie movie, @Movie.Type int type) {
        try (Realm realmInstance = Realm.getDefaultInstance()) {
            Log.i(TAG, "removeFromFavourites: " + movie);
            realmInstance.executeTransaction((realm) -> {
                RealmMovie realmMovie = realm.where(RealmMovie.class)
                        .equalTo(RealmMovieFields.ID, movie.getId())
                        .equalTo(convertTypeToField(type), true)
                        .findFirst();
                if (realmMovie != null) {
                    deleteMovieFromRealmByType(realmMovie, type);
                }
            });
        }
    }

    @Override
    public boolean isMovieInDb(int id,@Movie.Type int type) {
        return getMovie(id, type) != null;
    }

    @Override
    public RealmMovie getMovie(int id,@Movie.Type int type) {
        final RealmMovie resultRealm;
        try (Realm realmInstance = Realm.getDefaultInstance()) {
            resultRealm = realmInstance.where(RealmMovie.class)
                    .equalTo(RealmMovieFields.ID, id)
                    .equalTo(convertTypeToField(type), true)
                    .findFirst();
            Log.i(TAG, "getMovie:type " + type + " " + resultRealm);
        }
        return resultRealm;
    }

    @Override
    public Observable<List<Movie>> queryMovies(int type)  {

        //TODO implement same search as from webapi
        try (Realm realmInstance = Realm.getDefaultInstance()) {
            RealmResults<RealmMovie> realmResults = realmInstance.where(RealmMovie.class)
                    .equalTo(convertTypeToField(type), true)
                    .findAllSorted(convertTypeToSortField(type),Sort.DESCENDING);
            final List<Movie> movies = new ArrayList<>();
//TODO dont use it like this, you are losing realm zero copy feature - use raelm driven architecture!!!
            for (RealmMovie realmMovie : realmResults) {
                movies.add(mRealmMovieMapper.reverseMap(realmMovie));//TO DO change this to Mapper
            }
            return Observable.just(movies);
        }
    }

    @Override
    public void deleteAllMovies(@Movie.Type final int type) {
        try (Realm realmInstance = Realm.getDefaultInstance()) {
            Log.i(TAG, "deleteAll: " + type);
            realmInstance.executeTransaction((realm) -> {
                List<RealmMovie> realmMovies = realm.where(RealmMovie.class)
                        .equalTo(convertTypeToField(type), true)
                        .findAll();
                for (RealmMovie realmMovie : realmMovies) {
                    deleteMovieFromRealmByType(realmMovie, type);
                }
            });
        }
    }

    @Override
    public void addMovieList(List<Movie> movies, int type) {
        try (Realm realmInstance = Realm.getDefaultInstance()) {
            Log.i(TAG, "addMovieList: " + movies + " " + type);
            realmInstance.executeTransaction((realm) -> {
                for (Movie m : movies) {
                    addMovieToRealmByType(realm, m, convertTypeToField(type));
                }
            });
        }
    }

////////////////// help f-ions

    private void addMovieToRealmByType(Realm realm, Movie m, String fieldType) {
        RealmMovie realmMovie = realm.where(RealmMovie.class).equalTo(RealmMovieFields.ID, m.getId()).findFirst();
        if (realmMovie == null) {
            realmMovie = mRealmMovieMapper.map(m);
            setMovieFieldType(realmMovie, true, fieldType);
            realm.insertOrUpdate(realmMovie);
        } else {
            setMovieFieldType(realmMovie, true, fieldType);
        }
    }

    private void setMovieFieldType(RealmMovie realmMovie, boolean shouldAddToList, String fieldType) {
        if (fieldType == RealmMovieFields.IS_MOST_POPULAR) {
            realmMovie.setIsMostPopular(shouldAddToList);
        } else if (fieldType == RealmMovieFields.IS_TOP_RATED) {
            realmMovie.setIsTopRated(shouldAddToList);
        } else if (fieldType == RealmMovieFields.IS_FAVOURITE) {
            realmMovie.setIsFavourite(shouldAddToList);
        }
    }


    private void deleteMovieFromRealmByType(RealmMovie realmMovie, @Movie.Type int type) {
        if (getDeleteCondition(realmMovie, type)) {
            setMovieFieldType(realmMovie,false,convertTypeToField(type));
        } else {
            RealmList<RealmReview> reviews = realmMovie.getReviews();
            reviews.deleteAllFromRealm();
            RealmList<RealmTrailer> trailers = realmMovie.getTrailers();
            trailers.deleteAllFromRealm();
            realmMovie.deleteFromRealm();
        }
    }

    private boolean getDeleteCondition(RealmMovie realmMovie,@Movie.Type int type) {
        if (type == Movie.TOP_RATED ) {
            return realmMovie.isMostPopular() || realmMovie.isFavourite();
        } else if (type == Movie.MOST_POPULAR){
            return (realmMovie.isTopRated() || realmMovie.isFavourite());
        } else if (type == Movie.FAVOURITE){
            return realmMovie.isMostPopular() || realmMovie.isTopRated();
        }
        return false;//throw exception better!!!
    }

    @NonNull
    private String convertTypeToField(int type) {
        String typeField = "";//TO DO better throw exception!
        if (type == Movie.FAVOURITE){
            typeField = RealmMovieFields.IS_FAVOURITE;
        } else  if (type == Movie.TOP_RATED){
            typeField = RealmMovieFields.IS_TOP_RATED;
        } else if (type == Movie.MOST_POPULAR) {
            typeField = RealmMovieFields.IS_MOST_POPULAR;
        }
        return typeField;
    }

    private String convertTypeToSortField(int type) {
        String sortField = "";//TO DO better throw exception!
        if (type == Movie.FAVOURITE){
            sortField = RealmMovieFields.ID;
        } else  if (type == Movie.TOP_RATED){
            sortField = RealmMovieFields.USER_RATING;//this one is not working from realm when all values are the same??? find extra criteria?
        } else if (type == Movie.MOST_POPULAR) {
            sortField = RealmMovieFields.POPULARITY;//this one is ok!
        }
        return sortField;
     }
}
