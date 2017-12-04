package com.example.milosevi.rxjavatest.entrylist.mvp;

import android.support.annotation.IntDef;

import com.example.milosevi.rxjavatest.model.Movie;

import java.lang.annotation.Retention;
import java.util.List;

import io.reactivex.Observable;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by milosevi on 10/10/17.
 */

public class GridContract {

    public interface View {
        void showMovieList(List<Movie> movies);
        void addMoviesToList(List<Movie> movies);
        void clearMovieList();
        void navigateToMovie(Movie movie);
        void resetScrollPosition();
    }

    public interface Presenter{
        @Retention(SOURCE)
        @IntDef({LIST_TOP_RATED, LIST_MOST_POPULAR, LIST_FAVOURITES})
        @interface ListMode {}
        int LIST_TOP_RATED = 0;
        int LIST_MOST_POPULAR = 1;
        int LIST_FAVOURITES = 2;

        void onMenuItemClicked(@ListMode int listMode);
        void onLoadMovieList();
        void onLoadMovieListByPage(int page);
        void onMovieClicked(Movie movie);
        void onSearch(String searchWord);
        void onViewAttached(View view);
        void onViewDetached(View view);
        void onActivityDestroyed();
        boolean isLoading();
    }

    public interface Repository{
        Observable<List<Movie>> getMostPopular(int page);
        Observable<List<Movie>> getFavourites();
        Observable<List<Movie>> getTopRated(int page);
        Observable<List<Movie>> getMoviesWithWord(String search);
    }
}
