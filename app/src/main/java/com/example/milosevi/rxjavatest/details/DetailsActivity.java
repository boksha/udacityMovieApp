package com.example.milosevi.rxjavatest.details;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.milosevi.rxjavatest.ImageLoader;
import com.example.milosevi.rxjavatest.R;
import com.example.milosevi.rxjavatest.details.adapters.ReviewsAdapter;
import com.example.milosevi.rxjavatest.details.adapters.TrailerAdapter;
import com.example.milosevi.rxjavatest.details.model.Review;
import com.example.milosevi.rxjavatest.details.model.Trailer;
import com.example.milosevi.rxjavatest.details.mvp.DetailsContract;
import com.example.milosevi.rxjavatest.details.mvp.DetailsPresenter;
import com.example.milosevi.rxjavatest.details.mvp.DetailsRepository;
import com.example.milosevi.rxjavatest.model.Movie;

import java.util.List;

/**
 * Created by milosevi on 10/10/17.
 */

public class DetailsActivity extends AppCompatActivity implements DetailsContract.View{
    private static final String TAG = "Miki";
    private Movie mDetailMovie;
    private TextView mtitleTextView;
    private TextView mDescTextView;
    private ImageView mImageView;
    private TextView mReleaseDateTextView;
    private TextView mRatingsTextView;
    private Button mAddTofavoritesBtn;
    public static final String EXTRA_MOVIE = "om.example.milosevi.rxjavatest.EXTRA_MOVIE";
//    private CompositeDisposable disposableList = new CompositeDisposable();
    private RecyclerView mTrailersRecyclerView;
    private RecyclerView mReviewsRecyclerView;
    private DetailsContract.Presenter mPresenter;

    private TrailerAdapter trailerAdapter;
    private ReviewsAdapter mReviewsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mDetailMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        mPresenter = new DetailsPresenter(new DetailsRepository());
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
        mAddTofavoritesBtn = findViewById(R.id.button_add_to_favorites);
        mAddTofavoritesBtn.setOnClickListener(view -> {
            if (mAddTofavoritesBtn.getText().equals("UNMARK")) {
                //add this info in mdetailMovie; you should restore this from DB if exist!!!
                mPresenter.onMovieMarked(mDetailMovie, false);
            } else {
                mPresenter.onMovieMarked(mDetailMovie, true);
            }
            });
        initTrailerRecyclerView();
        initReviewsRecyclerView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onViewAttached(this);
        mPresenter.onLoadTrailerList(mDetailMovie.getId());
        mPresenter.onLoadReviewList(mDetailMovie.getId());
        mPresenter.onLoadMovie(mDetailMovie.getId());
    }


    @Override
    protected void onPause() {
        mPresenter.onViewDetached(this);
        super.onPause();
    }

    private void initReviewsRecyclerView() {
        LinearLayoutManager layoutManager= new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        mReviewsAdapter = new ReviewsAdapter();
        mReviewsRecyclerView =  findViewById(R.id.recycler_reviews);
        mReviewsRecyclerView.setLayoutManager(layoutManager);
        mReviewsRecyclerView.setAdapter(mReviewsAdapter);
    }

    private void initTrailerRecyclerView() {
        LinearLayoutManager layoutManager= new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        trailerAdapter = new TrailerAdapter();
        mTrailersRecyclerView = (RecyclerView) findViewById(R.id.recycler_trailers);
        mTrailersRecyclerView.setLayoutManager(layoutManager);
        trailerAdapter.setOnItemClickListener(new TrailerAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(Trailer t) {
                mPresenter.onTrailerSelected(t.getKey());
            }
        });
        mTrailersRecyclerView.setAdapter(trailerAdapter);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onActivityDestroyed();
        super.onDestroy();
    }

    public void watchYoutubeVideo( String key){
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
    public void showTrailerList(List<Trailer> trailers) {
        trailerAdapter.setTrailers(trailers);

    }

    @Override
    public void showReviewList(List<Review> reviews) {
        mReviewsAdapter.setReviews(reviews);

    }

    @Override
    public void navigateToTrailer(String key) {
        watchYoutubeVideo(key);
    }

    @Override
    public void updateMarkButton(boolean marked) {
        if (marked) {
            mAddTofavoritesBtn.setText("UNMARK");
        }
        else {
            mAddTofavoritesBtn.setText("MARK");
        }
    }
}
