package com.example.milosevi.rxjavatest.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by milosevi on 9/29/17.
 */

public class Movie  extends RealmObject implements Parcelable {

    @PrimaryKey
    @SerializedName("id")
    private Integer id;//id
    @SerializedName("title")
    private String mTitle;//title
    @SerializedName("poster_path")
    private String mImageUrl;//poster_path
    @SerializedName("overview")
    private String mDescription;//overview in the api
    @SerializedName("vote_average")
    private String mUserRating ;//vote_average in the api)
    @SerializedName("release_date")
    private String mReleaseDate;//release_date

    public Movie(){};

    public boolean isMarked() {
        return mIsMarked;
    }

    public void setMarked(boolean marked) {
        mIsMarked = marked;
    }

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

    public void setUserRating(String mUserRating) {
        this.mUserRating = mUserRating;
    }

    public void setReleaseDate(String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    //realm param TODO create separate objects
    private boolean mIsMarked;

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

    public String getUserRating() {
        return mUserRating;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }



    public Movie(Integer id, String mTitle, String mImageUrl, String mDescription, String mUserRating, String mReleaseDate) {
        this.id = id;
        this.mTitle = mTitle;
        this.mImageUrl = mImageUrl;
        this.mDescription = mDescription;
        this.mUserRating = mUserRating;
        this.mReleaseDate = mReleaseDate;
    }

    public Movie(Integer id, String mTitle, String mImageUrl, String mDescription, String mUserRating, String mReleaseDate, boolean isMarked) {
        this.id = id;
        this.mTitle = mTitle;
        this.mImageUrl = mImageUrl;
        this.mDescription = mDescription;
        this.mUserRating = mUserRating;
        this.mReleaseDate = mReleaseDate;
        this.mIsMarked = isMarked;
    }

    public Movie(Movie m) {
        this.id = m.id;
        this.mTitle = m.mTitle;
        this.mImageUrl = m.mImageUrl;
        this.mDescription = m.mDescription;
        this.mUserRating = m.mUserRating;
        this.mReleaseDate = m.mReleaseDate;
        this.mIsMarked = m.mIsMarked;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", mTitle='" + mTitle + '\'' +
                ", mImageUrl='" + mImageUrl + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mUserRating='" + mUserRating + '\'' +
                ", mReleaseDate='" + mReleaseDate + '\'' +
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
        dest.writeString(this.mUserRating);
        dest.writeString(this.mReleaseDate);
    }

    protected Movie(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.mTitle = in.readString();
        this.mImageUrl = in.readString();
        this.mDescription = in.readString();
        this.mUserRating = in.readString();
        this.mReleaseDate = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
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
