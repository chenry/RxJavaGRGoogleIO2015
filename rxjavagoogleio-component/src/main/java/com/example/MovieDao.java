package com.example;

import com.example.model.MovieSearchResult;

import java.util.Map;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by carlushenry on 5/25/15.
 */
public interface MovieDao {

    @GET("/")
    Observable<MovieSearchResult> findMoviesBySearchString(@Query("s") String searchString);

}
