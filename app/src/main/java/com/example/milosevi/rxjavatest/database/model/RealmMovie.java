package com.example.milosevi.rxjavatest.database.model;

import android.support.annotation.StringDef;

import com.example.milosevi.rxjavatest.model.Movie;

import java.lang.annotation.Retention;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by miodrag.milosevic on 11/14/2017.
 */

public class RealmMovie extends RealmObject {

    @Index
    @PrimaryKey
    private Integer mId;//id
    private String mTitle;//title
    private String mImageUrl;//poster_path
    private String mDescription;//overview in the api
    private String mUserRating;//vote_average in the api)
    private String mReleaseDate;//release_date
    private boolean mIsFavourite;
    private boolean mIsTopRated;
    private boolean mIsMostPopular;

    private RealmList<RealmTrailer> mTrailers;
    private RealmList<RealmReview> mReviews;


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


    public boolean isFavourite() {
        return mIsFavourite;
    }

    public void setIsFavourite(boolean mIsFavourite) {
        this.mIsFavourite = mIsFavourite;
    }

    public boolean isTopRated() {
        return mIsTopRated;
    }

    public void setIsTopRated(boolean mIsTopRated) {
        this.mIsTopRated = mIsTopRated;
    }

    public boolean isMostPopular() {
        return mIsMostPopular;
    }

    public void setIsMostPopular(boolean mIsMostPopular) {
        this.mIsMostPopular = mIsMostPopular;
    }
    public RealmList<RealmTrailer> getTrailers() {
        return mTrailers;
    }

    public void setTraiilers(RealmList<RealmTrailer> mTrailers) {
        this.mTrailers = mTrailers;
    }

    public RealmList<RealmReview> getReviews() {
        return mReviews;
    }

    public void setReviews(RealmList<RealmReview> mReview) {
        this.mReviews = mReview;
    }

    @Override
    public String toString() {
        return "RealmMovie{" +
                "mId=" + mId +
                ", mTitle='" + mTitle + '\'' +
                ", mImageUrl='" + mImageUrl + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mUserRating='" + mUserRating + '\'' +
                ", mReleaseDate='" + mReleaseDate + '\'' +
                ", mTrailers=" + mTrailers +
                ", mReviews=" + mReviews +
                '}';
    }

    public RealmMovie() {
    }

    public RealmMovie(Integer id, String mTitle, String mImageUrl, String mDescription, String mUserRating, String mReleaseDate) {
        this.mId = id;
        this.mTitle = mTitle;
        this.mImageUrl = mImageUrl;
        this.mDescription = mDescription;
        this.mUserRating = mUserRating;
        this.mReleaseDate = mReleaseDate;
    }


    public RealmMovie(Movie m) {
        this.mId = m.getId();
        this.mTitle = m.getTitle();
        this.mImageUrl = m.getImageUrl();
        this.mDescription = m.getDescription();
        this.mUserRating = m.getUserRating();
        this.mReleaseDate = m.getReleaseDate();
    }

}