package com.example.milosevi.rxjavatest.database.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by miodrag.milosevic on 11/17/2017.
 */

public class RealmTrailer extends RealmObject {

    @PrimaryKey
    private String id;//id

    private String mName;//name
    private Integer mSize;//size
    private String mKey;//key
    private String mSite;//
    private String mType;//

    public String getId() {
        return id;
    }

    public String getName() {
        return mName;
    }

    public Integer getSize() {
        return mSize;
    }

    public String getKey() {
        return mKey;
    }

    public String getSite() {
        return mSite;
    }

    public String getType() {
        return mType;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public void setSize(Integer mSize) {
        this.mSize = mSize;
    }

    public void setKey(String mKey) {
        this.mKey = mKey;
    }

    public void setSite(String mSite) {
        this.mSite = mSite;
    }

    public void setType(String mType) {
        this.mType = mType;
    }

    public RealmTrailer() {
    }

    public RealmTrailer(String id, String name, Integer mSize, String mKey, String mSite, String mType) {
        this.id = id;
        this.mName = name;
        this.mSize = mSize;
        this.mKey = mKey;
        this.mSite = mSite;
        this.mType = mType;
    }

    @Override
    public String toString() {
        return "RealmTrailer{" +
                "id='" + id + '\'' +
                ", mName='" + mName + '\'' +
                ", mSize=" + mSize +
                ", mKey='" + mKey + '\'' +
                ", mSite='" + mSite + '\'' +
                ", mType='" + mType + '\'' +
                '}';
    }

}
