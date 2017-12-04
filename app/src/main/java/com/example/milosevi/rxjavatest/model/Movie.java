package com.example.milosevi.rxjavatest.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import com.example.milosevi.rxjavatest.database.model.RealmMovie;
import com.example.milosevi.rxjavatest.details.model.Review;
import com.example.milosevi.rxjavatest.details.model.Trailer;
import com.google.gson.annotations.SerializedName;

import java.lang.annotation.Retention;
import java.util.List;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by milosevi on 9/29/17.
 */

public class Movie implements Parcelable {

    @Retention(SOURCE)
    @IntDef({
            FAVOURITE,
            TOP_RATED,
            MOST_POPULAR
    })
    public @interface Type {}
    public static final int FAVOURITE = 0;
    public static final int TOP_RATED = 1;
    public static final int MOST_POPULAR = 2;

    @SerializedName("id")
    private Integer id;//id
    @SerializedName("title")
    private String mTitle;//title
    @SerializedName("poster_path")
    private String mImageUrl;//poster_path
    @SerializedName("overview")
    private String mDescription;//overview in the api
    @SerializedName("vote_average")
    private Double mUserRating ;//vote_average in the api)
    @SerializedName("vote_count")
    private Integer mVoteCount ;//vote_count in the api)

    @SerializedName("popularity")
    private String mPopularity ;//popularity in the api)
    @SerializedName("release_date")
    private String mReleaseDate;//release_date

    private List<Review> mReviews;

    private List<Trailer> mTrailers;

    public Movie(){};

    public void setId(Integer id) {
        this.id = id;
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
        return id;
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

    public Double getUserRating() {
        return mUserRating;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public Integer getVoteCount() {
        return mVoteCount;
    }

    public void setVoteCount(Integer mVoteCount) {
        this.mVoteCount = mVoteCount;
    }

    public String getPopularity() {
        return mPopularity;
    }

    public void setPopularity(String mPopularity) {
        this.mPopularity = mPopularity;
    }

    public List<Review> getReviews() {
        return mReviews;
    }

    public void setReviews(List<Review> mReviews) {
        this.mReviews = mReviews;
    }

    public List<Trailer> getTrailers() {
        return mTrailers;
    }

    public void setTrailers(List<Trailer> mTrailers) {
        this.mTrailers = mTrailers;
    }


    public Movie(Integer id, String mTitle, String mImageUrl, String mDescription, Double mUserRating,
                 Integer mVoteCount,String mPopularity, String mReleaseDate) {
        this.id = id;
        this.mTitle = mTitle;
        this.mImageUrl = mImageUrl;
        this.mDescription = mDescription;
        this.mUserRating = mUserRating;
        this.mPopularity = mPopularity;
        this.mVoteCount = mVoteCount;
        this.mReleaseDate = mReleaseDate;
    }

    public Movie(Movie m) {
        this.id = m.id;
        this.mTitle = m.mTitle;
        this.mImageUrl = m.mImageUrl;
        this.mDescription = m.mDescription;
        this.mUserRating = m.mUserRating;
        this.mPopularity = m.mPopularity;
        this.mVoteCount = m.mVoteCount;
        this.mReleaseDate = m.mReleaseDate;
        this.mReviews = m.mReviews;
        this.mTrailers = m.mTrailers;
    }


    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", mTitle='" + mTitle + '\'' +
                ", mImageUrl='" + mImageUrl + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mUserRating=" + mUserRating +
                ", mVoteCount=" + mVoteCount +
                ", mPopularity='" + mPopularity + '\'' +
                ", mReleaseDate='" + mReleaseDate + '\'' +
                ", mReviews=" + mReviews +
                ", mTrailers=" + mTrailers +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.mTitle);
        dest.writeString(this.mImageUrl);
        dest.writeString(this.mDescription);
        dest.writeValue(this.mUserRating);
        dest.writeValue(this.mVoteCount);
        dest.writeString(this.mPopularity);
        dest.writeString(this.mReleaseDate);
    }

    protected Movie(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.mTitle = in.readString();
        this.mImageUrl = in.readString();
        this.mDescription = in.readString();
        this.mUserRating = (Double) in.readValue(Double.class.getClassLoader());
        this.mVoteCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.mPopularity = in.readString();
        this.mReleaseDate = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
