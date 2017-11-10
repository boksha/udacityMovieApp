package com.example.milosevi.rxjavatest.details.mvp;

import com.example.milosevi.rxjavatest.details.model.Review;
import com.example.milosevi.rxjavatest.details.model.Reviews;
import com.example.milosevi.rxjavatest.details.model.Trailer;
import com.example.milosevi.rxjavatest.details.model.Trailers;
import com.example.milosevi.rxjavatest.model.Movie;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by milosevi on 10/27/17.
 */

public class DetailsContract {

    public interface View {
        void showTrailerList(List<Trailer> trailers);
        void showReviewList(List<Review> reviews);
//        void navigateToMovie(Movie movie);
        void navigateToTrailer(String key);
        void updateMarkButton(boolean marked);
    }

    public interface Presenter{

//
//
//        @Retention(SOURCE)
//        @IntDef({MENU_ITEM_TOP_RATED, MENU_ITEM_MOST_POPULAR, MENU_ITEM_FAVOURITES})
//        public @interface MenuMode {}
//        public static final int MENU_ITEM_TOP_RATED = 0;
//        public static final int MENU_ITEM_MOST_POPULAR = 1;
//        public static final int MENU_ITEM_FAVOURITES = 2;

//        void onMenuItemClicked(@GridContract.Presenter.MenuMode int menuMode);
        void onLoadTrailerList(Integer id);
        void onLoadReviewList(Integer id);
        void onLoadMovie(Integer id);
        void onMovieMarked(Movie movie, boolean mark);
        void onViewAttached(DetailsContract.View view);
        void onViewDetached(DetailsContract.View view);
        void onActivityDestroyed();

        void onTrailerSelected(String key);
    }

    public interface Repository{
        void markMovie(Movie movie);
        void unmarkMovie(Movie movie);
        boolean isMovieMarked(Integer id);
        Observable<Trailers> getTrailers(Integer id);
        Observable<Reviews> getReviews(Integer id);
//        Observable<Movies> getMoviesWithWord(String search);
    }
}
