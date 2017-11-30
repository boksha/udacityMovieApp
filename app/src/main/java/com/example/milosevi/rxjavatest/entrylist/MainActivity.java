package com.example.milosevi.rxjavatest.entrylist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.example.milosevi.rxjavatest.EndlessScrollListener;
import com.example.milosevi.rxjavatest.R;
import com.example.milosevi.rxjavatest.details.DetailsActivity;
import com.example.milosevi.rxjavatest.entrylist.adapters.MovieGridRecyclerViewAdapter;
import com.example.milosevi.rxjavatest.model.Movie;
import com.example.milosevi.rxjavatest.entrylist.mvp.GridContract;
import com.example.milosevi.rxjavatest.entrylist.mvp.GridPresenter;
import com.example.milosevi.rxjavatest.entrylist.mvp.GridRepository;

import java.util.List;

import static com.example.milosevi.rxjavatest.entrylist.mvp.GridContract.Presenter.LIST_TOP_RATED;

/**
 * Greed screen
 */
public class MainActivity extends AppCompatActivity implements GridContract.View {
    //https://api.themoviedb.org/3/movie/550?api_key=63c792551dfae04485cc8cc06de29fe1
//    private static final String API_KEY = "63c792551dfae04485cc8cc06de29fe1";
//    private static final String API_READ_ACCESS_TOKEN =
//            "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2M2M3OTI1NTFkZmFlMDQ0ODVjYzhjYzA2ZGUyOWZlMSIsInN1YiI6IjU5Y2UwNjcyYzNhMzY4NmFiYzAwZWMzMSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.04bjvLkxpVmkABNlLjNMSMkVcFkqKF6DVBMQ--XXk5w";
    private static final String TAG = "Miki";
    //    private GitHubAdapter movieGridAdapter = new GitHubAdapter();
    private MovieGridRecyclerViewAdapter movieGridAdapter;
    private GridLayoutManager mGridLayoutManager;
    private GridContract.Presenter mPresenter;
    private RecyclerView mGridView;


    private EndlessScrollListener recyclerViewOnScrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGridView = findViewById(R.id.gridView1);
        movieGridAdapter = new MovieGridRecyclerViewAdapter(this);
        movieGridAdapter.setOnItemClickListener((movie -> {
            mPresenter.onMovieClicked(movie);
            Log.i(TAG, "onItemClick: movie" + movie);
        }));
        mPresenter = new GridPresenter(new GridRepository());
        mGridView.setAdapter(movieGridAdapter);
        mGridLayoutManager = new GridLayoutManager(this, 3);
        recyclerViewOnScrollListener = new EndlessScrollListener(mGridLayoutManager) {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                return  mPresenter.onLoadMovieListByPage(page);
            }
        };
        mGridView.setLayoutManager(mGridLayoutManager);
        mGridView.addOnScrollListener(recyclerViewOnScrollListener);
        final EditText editTextUsername = findViewById(R.id.edit_text_username);
        final Button buttonSearch = findViewById(R.id.button_search);
        buttonSearch.setOnClickListener(view -> {
            final String searchWord = editTextUsername.getText().toString();
            if (!TextUtils.isEmpty(searchWord)) {
                mPresenter.onSearch(searchWord);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onViewAttached(this);
        mPresenter.onLoadMovieList();
    }

    @Override
    protected void onPause() {
        mPresenter.onViewDetached(this);
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_top:
                mPresenter.onMenuItemClicked(LIST_TOP_RATED);
                return true;
            case R.id.menu_most_popular:
                mPresenter.onMenuItemClicked(GridContract.Presenter.LIST_MOST_POPULAR);
                return true;
            case R.id.menu_favourites:
                mPresenter.onMenuItemClicked(GridContract.Presenter.LIST_FAVOURITES);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onDestroy() {
        mPresenter.onActivityDestroyed();
        super.onDestroy();
    }


    @Override
    public void showMovieList(List<Movie> movies) {
        Log.i(TAG, "showMovieList: " + movies);
        movieGridAdapter.setData(movies);
//        movieGridAdapter.addData(movies);
    }

    @Override
    public void addMoviesToList(List<Movie> movies) {
        movieGridAdapter.addData(movies);
    }

    @Override
    public void clearMovieList() {
        movieGridAdapter.clearList();
        recyclerViewOnScrollListener.resetState();
    }

    @Override
    public void navigateToMovie(Movie movie) {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.EXTRA_MOVIE, movie);
        startActivity(intent);
    }

    @Override
    public void resetScrollPosition() {
        mGridView.scrollToPosition(0);
    }
}
