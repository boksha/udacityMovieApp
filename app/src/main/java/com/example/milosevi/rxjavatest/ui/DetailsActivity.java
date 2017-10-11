package com.example.milosevi.rxjavatest.ui;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.milosevi.rxjavatest.ImageLoader;
import com.example.milosevi.rxjavatest.R;
import com.example.milosevi.rxjavatest.model.Movie;
import com.example.milosevi.rxjavatest.model.Reviews;
import com.example.milosevi.rxjavatest.model.Trailers;
import com.example.milosevi.rxjavatest.webapi.WebApiFetcher;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by milosevi on 10/10/17.
 */

public class DetailsActivity extends AppCompatActivity {
    private static final String TAG = "Miki";
    private Movie mDetailMovie;
    private TextView mtitleTextView;
    private TextView mDescTextView;
    private ImageView mImageView;
    private TextView mReleaseDateTextView;
    private TextView mRatingsTextView;
    private Button mAddTofavoritesBtn;
    public static final String EXTRA_MOVIE = "om.example.milosevi.rxjavatest.EXTRA_MOVIE";
    private CompositeDisposable disposableList = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mDetailMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        Log.i(TAG, "onCreate: " + mDetailMovie);
        mtitleTextView = findViewById(R.id.details_title);
        mReleaseDateTextView = findViewById(R.id.details_date);
        mReleaseDateTextView.setText(mDetailMovie.getReleaseDate());
        mRatingsTextView = findViewById(R.id.details_rating);
        mRatingsTextView.setText(mDetailMovie.getUserRating());
        mImageView = findViewById(R.id.details_image);
        mtitleTextView.setText(mDetailMovie.getTitle());
        mDescTextView = findViewById(R.id.details_description);
        mDescTextView.setText(mDetailMovie.getDescription());
        ImageLoader.loadImageintoView(this,mDetailMovie.getImageUrl(),mImageView);
        fetchTrailers();
        fetchReviews();
    }

    @Override
    protected void onDestroy() {
        if (disposableList != null) {
            Log.i(TAG, "onDestroy: dispose");
            disposableList.clear();
        }
        super.onDestroy();
    }

    private void fetchTrailers() {
        disposableList.add(WebApiFetcher.getInstance().getTrailers(mDetailMovie.getId()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<Trailers>() {

                    @Override
                    public void onNext(Trailers trailers) {
                        Log.i(TAG, "onNext: " + trailers);
//                        watchYoutubeVideo(getApplicationContext(),trailers.getTrailers().get(0).getKey());
//                        adapter.setData(trailers.getTrailers());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: ");
                    }
                }));

    }

    private void fetchReviews() {
        disposableList.add(WebApiFetcher.getInstance().getReviews(mDetailMovie.getId()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<Reviews>() {

                    @Override
                    public void onNext(Reviews reviews) {
                        Log.i(TAG, "onNext: " + reviews);
//                        adapter.setData(trailers.getTrailers());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: ");
                    }
                }));

    }

    public void watchYoutubeVideo(Context context, String key){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + key));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }
}
