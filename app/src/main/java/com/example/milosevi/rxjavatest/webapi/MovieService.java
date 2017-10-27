package com.example.milosevi.rxjavatest.webapi;

import com.example.milosevi.rxjavatest.model.Genres;
import com.example.milosevi.rxjavatest.model.Movies;
import com.example.milosevi.rxjavatest.details.model.Reviews;
import com.example.milosevi.rxjavatest.details.model.Trailers;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by milosevi on 9/29/17.
 */

public interface MovieService {
// /discover/movie?sort_by=popularity.desc
    //: /discover/movie/?certification_country=US&certification=R&sort_by=vote_average.desc
    @GET("search/movie")
    Observable<Movies> findMoviesWithWord(@Query("api_key") String apiKey,@Query("query") String word);
    @GET("genre/movie/list")
    Observable<Genres> getGenres(@Query("api_key") String apiKey);

    @GET("movie/popular")
    Observable<Movies> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Observable<Movies> getTopRatedMovies(@Query("api_key") String apiKey);

//    /discover/movie?certification_country=US&certification.lte=G&sort_by=popularity.desc
    @GET("discover/movie")
    Observable<Movies> getMovies(@Query("api_key") String apiKey,
                                      @Query("page") String page,
                                      @Query("sort_by") String sortBy);

    @GET("movie/{id}/videos")
    Observable<Trailers> getTrailers(@Path("id") Integer id,@Query("api_key") String apiKey );

    @GET("movie/{id}/reviews")
    Observable<Reviews> getReviews(@Path("id") Integer id, @Query("api_key") String apiKey );

}
