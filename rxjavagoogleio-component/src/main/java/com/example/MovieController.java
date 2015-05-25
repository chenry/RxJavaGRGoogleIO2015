package com.example;

import com.example.model.MovieSearchResult;
import com.example.model.SearchResultItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.naming.directory.SearchResult;

import retrofit.RestAdapter;
import rx.Observable;
import rx.functions.Func2;

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


    public rx.Observable<List<String>> findTotalMoviesUsingMutipleSearchStrings(String searchString1, String searchString2) {
        Observable<MovieSearchResult> firstSearchResultObservable = movieDao.findMoviesBySearchString(searchString1);
        Observable<MovieSearchResult> secondSearchResultObservable = movieDao.findMoviesBySearchString(searchString2);

        return Observable.zip(firstSearchResultObservable, secondSearchResultObservable, new Func2<MovieSearchResult, MovieSearchResult, List<String>>() {
            @Override
            public List<String> call(MovieSearchResult movieSearchResult, MovieSearchResult movieSearchResult2) {
                List<String> movieTitles = new ArrayList<>();

                movieTitles.addAll(getMovieTitles(movieSearchResult));
                movieTitles.addAll(getMovieTitles(movieSearchResult2));

                return movieTitles;
            }

            private Collection<? extends String> getMovieTitles(MovieSearchResult movieSearchResult) {
                List<String> movieTitles = new ArrayList<String>();
                for (SearchResultItem curr: movieSearchResult.getSearchResultItems()) {
                    movieTitles.add(curr.getTitle());
                }

                return movieTitles;
            }
        });
    }
}
