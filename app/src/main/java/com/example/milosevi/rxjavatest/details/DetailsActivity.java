package com.example.milosevi.rxjavatest.details;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.milosevi.rxjavatest.ImageLoader;
import com.example.milosevi.rxjavatest.R;
import com.example.milosevi.rxjavatest.details.adapters.ReviewsAdapter;
import com.example.milosevi.rxjavatest.details.adapters.TrailerAdapter;
import com.example.milosevi.rxjavatest.details.mvp.DetailsContract;
import com.example.milosevi.rxjavatest.details.mvp.DetailsPresenter;
import com.example.milosevi.rxjavatest.details.mvp.DetailsRepository;
import com.example.milosevi.rxjavatest.model.Movie;

/**
 * Created by milosevi on 10/10/17.
 */

public class DetailsActivity extends AppCompatActivity implements DetailsContract.View {
    private static final String TAG = "Miki";
    public static final String EXTRA_MOVIE = "om.example.milosevi.rxjavatest.EXTRA_MOVIE";
    private Movie mDetailMovie;
    private TextView mTitleTextView;
    private TextView mDescTextView;
    private ImageView mImageView;
    private TextView mReleaseDateTextView;
    private TextView mRatingsTextView;
    private Button mAddToFavoritesBtn;
    private DetailsContract.Presenter mPresenter;

    private TrailerAdapter mTrailerAdapter;
    private ReviewsAdapter mReviewsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mDetailMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        mPresenter = new DetailsPresenter(new DetailsRepository());
        Log.i(TAG, "onCreate: " + mDetailMovie);
        mTitleTextView = findViewById(R.id.details_title);
        mReleaseDateTextView = findViewById(R.id.details_date);
        mReleaseDateTextView.setText(mDetailMovie.getReleaseDate());
        mRatingsTextView = findViewById(R.id.details_rating);
        mRatingsTextView.setText(mDetailMovie.getUserRating().toString());
        mImageView = findViewById(R.id.details_image);
        mTitleTextView.setText(mDetailMovie.getTitle());
        mDescTextView = findViewById(R.id.details_description);
        mDescTextView.setText(mDetailMovie.getDescription());
        ImageLoader.loadImageintoView(this, mDetailMovie.getImageUrl(), mImageView);
        mAddToFavoritesBtn = findViewById(R.id.button_add_to_favorites);
        mAddToFavoritesBtn.setOnClickListener(view ->
            mPresenter.onMovieMarked(mDetailMovie));
        initTrailerRecyclerView();
        initReviewsRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onViewAttached(this);
        mPresenter.onLoadMovie(mDetailMovie.getId());
    }


    @Override
    protected void onPause() {
        mPresenter.onViewDetached(this);
        super.onPause();
    }

    private void initReviewsRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mReviewsAdapter = new ReviewsAdapter();
        RecyclerView reviewsRecyclerView = findViewById(R.id.recycler_reviews);
        reviewsRecyclerView.setLayoutManager(layoutManager);
        reviewsRecyclerView.setAdapter(mReviewsAdapter);
    }

    private void initTrailerRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mTrailerAdapter = new TrailerAdapter();
        RecyclerView trailersRecyclerView = findViewById(R.id.recycler_trailers);
        trailersRecyclerView.setLayoutManager(layoutManager);
        mTrailerAdapter.setOnItemClickListener(t ->
                mPresenter.onTrailerSelected(t.getKey())
        );
        trailersRecyclerView.setAdapter(mTrailerAdapter);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onActivityDestroyed();
        super.onDestroy();
    }

    private void watchYoutubeVideo(String key) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + key));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }

    @Override
    public void showMovie(Movie movie) {
        mTrailerAdapter.setTrailers(movie.getTrailers());
        mReviewsAdapter.setReviews(movie.getReviews());
        mReleaseDateTextView.setText(movie.getReleaseDate());
        mRatingsTextView.setText(movie.getUserRating().toString());
        mTitleTextView.setText(movie.getTitle());
        mDescTextView.setText(movie.getDescription());
        ImageLoader.loadImageintoView(this, movie.getImageUrl(), mImageView);
    }

    @Override
    public void navigateToTrailer(String key) {
        watchYoutubeVideo(key);
    }

    @Override
    public void updateMarkButton(boolean marked) {
        if (marked) {
            mAddToFavoritesBtn.setText("UNMARK");
        } else {
            mAddToFavoritesBtn.setText("MARK");
        }
    }
}
