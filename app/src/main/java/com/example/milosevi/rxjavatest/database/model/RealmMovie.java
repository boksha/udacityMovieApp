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
    private Double mUserRating;//vote_average in the api)
    private Integer mVoteCount;//vote_count in the api)
    private Double mPopularity;//popularity in the api)
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

    public void setUserRating(Double mUserRating) {
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

    public Integer getVoteCount() {
        return mVoteCount;
    }

    public void setVoteCount(Integer mVoteCount) {
        this.mVoteCount = mVoteCount;
    }

    public Double getPopularity() {
        return mPopularity;
    }

    public void setPopularity(Double mPopularity) {
        this.mPopularity = mPopularity;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getDescription() {
        return mDescription;
    }

    public Double getUserRating() {
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

    public void setTrailers(RealmList<RealmTrailer> mTrailers) {
        this.mTrailers = mTrailers;
    }

    public RealmList<RealmReview> getReviews() {
        return mReviews;
    }

    public void setReviews(RealmList<RealmReview> mReview) {
        this.mReviews = mReview;
    }

    public RealmMovie(Integer mId, String mTitle, String mImageUrl, String mDescription, Double mUserRating, Integer mVoteCount, Double mPopularity, String mReleaseDate) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mImageUrl = mImageUrl;
        this.mDescription = mDescription;
        this.mUserRating = mUserRating;
        this.mVoteCount = mVoteCount;
        this.mPopularity = mPopularity;
        this.mReleaseDate = mReleaseDate;
    }

    public RealmMovie() {
    }

    @Override
    public String toString() {
        return "RealmMovie{" +
                "mId=" + mId +
                ", mTitle='" + mTitle + '\'' +
                ", mImageUrl='" + mImageUrl + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mUserRating='" + mUserRating + '\'' +
                ", mVoteCount='" + mVoteCount + '\'' +
                ", mPopularity='" + mPopularity + '\'' +
                ", mReleaseDate='" + mReleaseDate + '\'' +
                ", mIsFavourite=" + mIsFavourite +
                ", mIsTopRated=" + mIsTopRated +
                ", mIsMostPopular=" + mIsMostPopular +
                ", mTrailers=" + mTrailers +
                ", mReviews=" + mReviews +
                '}';
    }
}