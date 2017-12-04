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
        void showMovie(Movie movie);
        void navigateToTrailer(String key);
        void updateMarkButton(boolean marked);
    }

    public interface Presenter{

        void onLoadMovie(Integer id);
        void onMovieMarked(Movie movie);
        void onTrailerSelected(String key);
        void onViewAttached(DetailsContract.View view);
        void onViewDetached(DetailsContract.View view);
        void onActivityDestroyed();

    }

    public interface Repository{
        void markMovie(Movie movie);
        void unmarkMovie(Movie movie);
        boolean isMovieMarked(Integer id);
        Observable<Movie> getMovieById(Integer id);
    }
}
