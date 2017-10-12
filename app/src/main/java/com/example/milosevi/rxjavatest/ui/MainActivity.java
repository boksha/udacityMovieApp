package com.example.milosevi.rxjavatest.ui;

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
import com.example.milosevi.rxjavatest.model.Genres;
import com.example.milosevi.rxjavatest.model.Movie;
import com.example.milosevi.rxjavatest.model.Movies;
import com.example.milosevi.rxjavatest.ui.adapters.MovieGridAdapter;
import com.example.milosevi.rxjavatest.ui.mvp.GridContract;
import com.example.milosevi.rxjavatest.ui.mvp.GridPresenter;
import com.example.milosevi.rxjavatest.ui.mvp.GridRepository;
import com.example.milosevi.rxjavatest.webapi.WebApiFetcher;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 *  Greed screen
 */
public class MainActivity extends AppCompatActivity implements GridContract.View {
//https://api.themoviedb.org/3/movie/550?api_key=63c792551dfae04485cc8cc06de29fe1
    private static final String API_KEY = "63c792551dfae04485cc8cc06de29fe1";
    private static final String API_READ_ACCESS_TOKEN =
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2M2M3OTI1NTFkZmFlMDQ0ODVjYzhjYzA2ZGUyOWZlMSIsInN1YiI6IjU5Y2UwNjcyYzNhMzY4NmFiYzAwZWMzMSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.04bjvLkxpVmkABNlLjNMSMkVcFkqKF6DVBMQ--XXk5w";
    private static final String TAG = "Miki";
//    private GitHubAdapter adapter = new GitHubAdapter();
   private MovieGridAdapter adapter;
   private GridContract.Presenter mPresenter;
    private CompositeDisposable disposableList =new CompositeDisposable();
    ArrayList<Movie> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final GridView gridView = (GridView) findViewById(R.id.gridView1);
        adapter = new MovieGridAdapter(this);
        mPresenter = new GridPresenter(new GridRepository());
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Movie movie = adapter.getItem(i);
                Log.i(TAG, "onItemClick: " + i + " movie" + movie);
                Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
                intent.putExtra(DetailsActivity.EXTRA_MOVIE,movie);
                startActivity(intent);
            }
        });
        getMostPopularMovies();
        final EditText editTextUsername = (EditText) findViewById(R.id.edit_text_username);
        final Button buttonSearch = (Button) findViewById(R.id.button_search);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                final String searchWord = editTextUsername.getText().toString();
                if (!TextUtils.isEmpty(searchWord)) {
                    mPresenter.onSearchButtonClicked(searchWord);
                    getMoviesWithWord(searchWord);
                }
            }
        });
        testRxEmit();
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
                getTopRated();
                return true;
            case R.id.menu_most_popular:
                getMostPopularMovies();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getTopRated() {
        disposableList.add(WebApiFetcher.getInstance().getTopRatedMovies().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<Movies>() {

                    @Override
                    public void onNext(Movies movies) {
                        Log.i(TAG, "onNext: " + movies);
                        adapter.setData(movies.getMovies());
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

    private void getMostPopularMovies(){
        disposableList.add(WebApiFetcher.getInstance().getPopularMovies().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<Movies>() {

                    @Override
                    public void onNext(Movies movies) {
                        Log.i(TAG, "onNext: " + movies);
                        adapter.setData(movies.getMovies());
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

    private void getGenreList() {
        disposableList.add(WebApiFetcher.getInstance().getGenres().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<Genres>() {


            @Override
            public void onNext(Genres gitHubRepos) {
                Log.i(TAG, "onNext: " + gitHubRepos);
//                adapter.setGitHubRepos(gitHubRepos);
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
    private void getMoviesWithWord(String username) {
        disposableList.add(WebApiFetcher.getInstance().findMoviesWithWord(username).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<Movies>() {

            @Override
            public void onNext(Movies movies) {
                Log.i(TAG, "onNext: " + movies);
                adapter.setData(movies.getMovies());
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
    @Override
    protected void onDestroy() {
        if (disposableList != null) {
            Log.i(TAG, "onDestroy: dispose");
            disposableList.clear();
        }
        super.onDestroy();
    }



    private void testRxEmit() {
        Observable<Integer> ob = Observable.create((e)-> {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            });
        ob.map(i-> i*5)
                .map(i -> "Miki" + i)
              .subscribe((integer) ->
                Log.i(TAG, "onNext: " + integer),
                (i) -> Log.i(TAG, "onError: "),
                ()->Log.i(TAG, "onComplete: ")
            );


//        Observable.just(1,2,3,4).map(new Function<Integer, Integer>()  {
//            @Override
//            public Integer apply(Integer integer) throws Exception {
//                return integer*5;
//            }
//
//        })/*.filter(new Function<Integer,Boolean>(){
//
//                      @Override
//                      public Boolean apply(Integer integer) throws Exception {
//                          return integer>10;
//                      }
//                  }
//        )*/.subscribe(new DisposableObserver<Integer>() {
//
//            @Override
//            public void onNext(Integer integer) {
//                Log.i(TAG, "onNext: " + integer);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//                Log.i(TAG, "onComplete: ");
//            }
//        });
    }

    @Override
    public void showMovieList(List<Movie> movies) {
        Log.i(TAG, "showMovieList: " + movies);
        adapter.setData(movies);
    }
}
