package com.example.milosevi.rxjavatest.ui.mvp;

import android.support.annotation.IntDef;

import com.example.milosevi.rxjavatest.model.Movie;

import java.lang.annotation.Retention;
import java.util.List;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by milosevi on 10/10/17.
 */

public class GridContract {

    public interface View {
        public void showMovieList(List<Movie> movies);
    }

    public interface Presenter{

        @Retention(SOURCE)
        @IntDef({MENU_ITEM_TOP_RATED, MENU_ITEM_MOST_POPULAR})
        public @interface MenuMode {}
        public static final int MENU_ITEM_TOP_RATED = 0;
        public static final int MENU_ITEM_MOST_POPULAR = 1;

        void onItemClicked(int position);
        void onMenuItemClicked(@MenuMode int menuMode);
        void onSearchButtonClicked(String searchWord);
    }

    public interface Repository{
         void getStarredRepos(String username);
         void getMostPopular();
         void getTopRated();
         void getMoviesWithWord(String search);
    }
}
