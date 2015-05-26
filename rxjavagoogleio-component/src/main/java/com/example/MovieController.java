package com.example;

import com.example.model.MovieDetail;
import com.example.model.MovieSearchResult;
import com.example.model.SearchResultItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import retrofit.RestAdapter;
import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

public class MovieController {
    private MovieDao movieDao;

    public MovieController() {
        this.movieDao = createMovieDao();
    }

    private MovieDao createMovieDao() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://www.omdbapi.com")
//                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLogLevel(RestAdapter.LogLevel.NONE)
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

    public Observable<MovieDetail> findMovieDetailByImdbId(String imdbId) {
        return movieDao.findMovieDetailById(imdbId, "full");
    }


    // ZIP
    public rx.Observable<List<String>> findAllMovieTitlesUsingTheTwoSearchStrings(String searchString1, String searchString2) {
        Observable<MovieSearchResult> firstSearchResultObservable = findMoviesBySearchString(searchString1);
        Observable<MovieSearchResult> secondSearchResultObservable = findMoviesBySearchString(searchString2);

        return Observable.zip(firstSearchResultObservable, secondSearchResultObservable, new Func2<MovieSearchResult, MovieSearchResult, List<String>>() {
            @Override
            public List<String> call(MovieSearchResult movieSearchResult, MovieSearchResult movieSearchResult2) {
                List<String> movieTitles = new ArrayList<>();

                movieTitles.addAll(getMovieTitles(movieSearchResult));
                movieTitles.addAll(getMovieTitles(movieSearchResult2));

                return movieTitles;
            }

        });
    }

    // MERGE
    public Observable<MovieDetail> findAllMovieDetails(String searchString1, String searchString2) {
        Observable<MovieDetail> searchString1ObservableDetail = createObservableForMovieDetailsBySearchString(searchString1);
        Observable<MovieDetail> searchString2ObservableDetail = createObservableForMovieDetailsBySearchString(searchString2);;
        return Observable.merge(searchString1ObservableDetail, searchString2ObservableDetail);
    }

    private Observable<MovieDetail> createObservableForMovieDetailsBySearchString(String searchString1) {
        return findMoviesBySearchString(searchString1)
                .flatMap(new Func1<MovieSearchResult, Observable<SearchResultItem>>() {
                    @Override
                    public Observable<SearchResultItem> call(MovieSearchResult movieSearchResult) {
                        return Observable.from(movieSearchResult.getSearchResultItems());
                    }
                })
                .flatMap(new Func1<SearchResultItem, Observable<MovieDetail>>() {
                    @Override
                    public Observable<MovieDetail> call(SearchResultItem searchResultItem) {
                        return findMovieDetailByImdbId(searchResultItem.getImdbId());
                    }
                });
    }


    /* ======================================================================== */

    private Collection<? extends String> getMovieTitles(MovieSearchResult movieSearchResult) {
        List<String> movieTitles = new ArrayList<String>();
        for (SearchResultItem curr : movieSearchResult.getSearchResultItems()) {
            movieTitles.add(curr.getTitle());
        }

        return movieTitles;
    }

}
