package com.example.milosevi.rxjavatest.database.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by miodrag.milosevic on 11/17/2017.
 */

public class RealmReview extends RealmObject {

    @PrimaryKey
    private String id;//id

    private String mAuthor;//author
    private String mContent;//key
    private String mUrl;//release_date

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getContent() {
        return mContent;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public void setContent(String mContent) {
        this.mContent = mContent;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public RealmReview() {
    }

    public RealmReview(String id, String name, String mContent, String mUrl) {
        this.id = id;
        this.mAuthor = name;
        this.mContent = mContent;
        this.mUrl = mUrl;
    }

    @Override
    public String toString() {
        return "RealmReview{" +
                "id='" + id + '\'' +
                ", mAuthor='" + mAuthor + '\'' +
                ", mContent='" + mContent + '\'' +
                ", mUrl='" + mUrl + '\'' +
                '}';
    }
}
