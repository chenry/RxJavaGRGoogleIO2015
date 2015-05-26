package com.example;

import com.example.model.MovieDetail;
import com.example.model.MovieSearchResult;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by carlushenry on 5/25/15.
 */
public interface MovieDao {

    @GET("/")
    Observable<MovieSearchResult> findMoviesBySearchString(@Query("s") String searchString);

    @GET("/")
    Observable<MovieDetail> findMovieDetailById(@Query("i") String imdbId, @Query("plot") String plot);

}
