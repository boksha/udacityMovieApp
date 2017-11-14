package com.example.milosevi.rxjavatest.entrylist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.example.milosevi.rxjavatest.R;
import com.example.milosevi.rxjavatest.details.DetailsActivity;
import com.example.milosevi.rxjavatest.model.Movie;
import com.example.milosevi.rxjavatest.entrylist.adapters.MovieGridAdapter;
import com.example.milosevi.rxjavatest.entrylist.mvp.GridContract;
import com.example.milosevi.rxjavatest.entrylist.mvp.GridPresenter;
import com.example.milosevi.rxjavatest.entrylist.mvp.GridRepository;

import java.util.List;

import static com.example.milosevi.rxjavatest.entrylist.mvp.GridContract.Presenter.MENU_ITEM_TOP_RATED;

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
    private MovieGridAdapter movieGridAdapter;
    private GridContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final GridView gridView = findViewById(R.id.gridView1);
        movieGridAdapter = new MovieGridAdapter(this);
        mPresenter = new GridPresenter(new GridRepository());
        gridView.setAdapter(movieGridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Movie movie = movieGridAdapter.getItem(i);
                mPresenter.onMovieClicked(movie);
                Log.i(TAG, "onItemClick: " + i + " movie" + movie);
            }
        });
        final EditText editTextUsername = (EditText) findViewById(R.id.edit_text_username);
        final Button buttonSearch = (Button) findViewById(R.id.button_search);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String searchWord = editTextUsername.getText().toString();
                if (!TextUtils.isEmpty(searchWord)) {
                    mPresenter.onSearch(searchWord);
                }
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
                mPresenter.onMenuItemClicked(MENU_ITEM_TOP_RATED);
                return true;
            case R.id.menu_most_popular:
                mPresenter.onMenuItemClicked(GridContract.Presenter.MENU_ITEM_MOST_POPULAR);
                return true;

                case R.id.menu_favourites:
                mPresenter.onMenuItemClicked(GridContract.Presenter.MENU_ITEM_FAVOURITES);
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
    }

    @Override
    public void navigateToMovie(Movie movie) {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.EXTRA_MOVIE, movie);
        startActivity(intent);
    }
}
