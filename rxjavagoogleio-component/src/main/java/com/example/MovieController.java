package com.example;

import com.example.model.MovieSearchResult;

import java.util.Map;

import retrofit.RestAdapter;
import rx.functions.Action1;

public class MovieController {
    private MovieDao movieDao;

    public MovieController() {
        this.movieDao = createMovieDao();
    }

    private MovieDao createMovieDao() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://www.omdbapi.com")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new RestAdapter.Log() {

                    @Override
                    public void log(String message) {
                        System.out.println(message);
                    }
                })
                .build();

        return restAdapter.create(MovieDao.class);
    }

    public rx.Observable<MovieSearchResult> findMoviesBySearchString(String searchString) {
        return movieDao.findMoviesBySearchString(searchString);
    }
}
