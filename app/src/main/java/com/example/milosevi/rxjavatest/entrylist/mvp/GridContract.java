package com.example.milosevi.rxjavatest.entrylist.mvp;

import android.support.annotation.IntDef;

import com.example.milosevi.rxjavatest.model.Movie;
import com.example.milosevi.rxjavatest.model.Movies;

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
        void navigateToMovie(Movie movie);
    }

    public interface Presenter{
        @Retention(SOURCE)
        @IntDef({MENU_ITEM_TOP_RATED, MENU_ITEM_MOST_POPULAR, MENU_ITEM_FAVOURITES})
        public @interface MenuMode {}
        public static final int MENU_ITEM_TOP_RATED = 0;
        public static final int MENU_ITEM_MOST_POPULAR = 1;
        public static final int MENU_ITEM_FAVOURITES = 2;

        void onMenuItemClicked(@MenuMode int menuMode);
        void onLoadMovieList();
        void onMovieClicked(Movie movie);
        void onSearch(String searchWord);
        void onViewAttached(View view);
        void onViewDetached(View view);
        void onActivityDestroyed();

    }

    public interface Repository{
        Observable<List<Movie>> getMostPopular();
        Observable<List<Movie>> getFavourites();
        Observable<List<Movie>> getTopRated();
        Observable<List<Movie>> getMoviesWithWord(String search);
    }
}
