package com.example.milosevi.rxjavatest.database.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.milosevi.rxjavatest.model.Movie;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by miodrag.milosevic on 11/13/2017.
 */

public class FavouriteMovieRealm extends RealmObject {

    @Index
    @PrimaryKey
    private Integer mId;//id
    private String mTitle;//title
    private String mImageUrl;//poster_path
    private String mDescription;//overview in the api
    private String mUserRating;//vote_average in the api)
    private String mReleaseDate;//release_date


    public void setId(Integer id) {
        this.mId = id;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public void setUserRating(String mUserRating) {
        this.mUserRating = mUserRating;
    }

    public void setReleaseDate(String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    public Integer getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getUserRating() {
        return mUserRating;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public FavouriteMovieRealm() {
    }

    public FavouriteMovieRealm(Integer id, String mTitle, String mImageUrl, String mDescription, String mUserRating, String mReleaseDate) {
        this.mId = id;
        this.mTitle = mTitle;
        this.mImageUrl = mImageUrl;
        this.mDescription = mDescription;
        this.mUserRating = mUserRating;
        this.mReleaseDate = mReleaseDate;
    }


        public FavouriteMovieRealm(Movie m) {
            this.mId = m.getId();
            this.mTitle = m.getTitle();
            this.mImageUrl = m.getImageUrl();
            this.mDescription = m.getDescription();
            this.mUserRating = m.getUserRating();
            this.mReleaseDate = m.getReleaseDate();
        }


    @Override
    public String toString() {
        return "FavouriteMovieRealm{" +
                "id=" + mId +
                ", mTitle='" + mTitle + '\'' +
                ", mImageUrl='" + mImageUrl + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mUserRating='" + mUserRating + '\'' +
                ", mReleaseDate='" + mReleaseDate + '\'' +
                '}';
    }
}
