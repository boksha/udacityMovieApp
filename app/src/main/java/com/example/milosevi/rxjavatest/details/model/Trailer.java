package com.example.milosevi.rxjavatest.details.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by milosevi on 10/11/17.
 */

public class Trailer {
//
//    {"id":"5581bd68c3a3685df70000c6","iso_639_1":"en","iso_3166_1":"US","key":"c25GKl5VNeY","name":"Trailer 1","site":"YouTube","size":720,"type":"Trailer"},
//    {"id":"58e9bfb6925141351f02fde0","iso_639_1":"en","iso_3166_1":"US","key":"Y9JvS2TmSvA","name":"Mere Khwabon Mein - song by CinePlusPlus","site":"YouTube","size":720,"type":"Clip"},
//    {"id":"58e9bf11c3a36872ee070b9a","iso_639_1":"en","iso_3166_1":"US","key":"H74COj0UQ_Q","name":"Zara Sa Jhoom Loon Main - song by CinePlusPlus","site":"YouTube","size":720,"type":"Clip"},
//    {"id":"58e9c00792514152ac020a34","iso_639_1":"en","iso_3166_1":"US","key":"OkjXMqK1G0o","name":"Ho Gaya Hai Tujhko To Pyar Sajna - song by CinePlusPlus","site":"YouTube","size":720,"type":"Clip"},
//    {"id":"58e9c034c3a36872ee070c84","iso_639_1":"en","iso_3166_1":"US","key":"7NhoeyoR_XA","name":"Mehndi Laga Ke Rakhna - song by CinePlusPlus","site":"YouTube","size":720,"type":"Clip"}
//    {"id":"58e9c07f9251414b2802a16e","iso_639_1":"en","iso_3166_1":"US","key":"Ee-cCwP7VPQ","name":"Tujhe dekha to  Ye Jaana Sanam - song by CinePlus","site":"YouTube","size":480,"type":"Clip"}
//
    @SerializedName("id")
    private String id;//id

    @SerializedName("name")
    private String mName;//name
    @SerializedName("size")
    private Integer mSize;//size
    @SerializedName("key")
    private String mKey;//key
    @SerializedName("site")
    private String mSite;//
    @SerializedName("type")
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

    public Trailer(String id, String name, Integer mSize, String mKey, String mSite, String mType) {
        this.id = id;
        this.mName = name;
        this.mSize = mSize;
        this.mKey = mKey;
        this.mSite = mSite;
        this.mType = mType;
    }

    @Override
    public String toString() {
        return "Trailer{" +
                "id='" + id + '\'' +
                ", mName='" + mName + '\'' +
                ", mSize=" + mSize +
                ", mKey='" + mKey + '\'' +
                ", mSite='" + mSite + '\'' +
                ", mType='" + mType + '\'' +
                '}';
    }

}
