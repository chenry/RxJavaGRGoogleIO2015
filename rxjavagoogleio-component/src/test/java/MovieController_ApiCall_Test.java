import com.example.MovieController;
import com.example.model.MovieDetail;
import com.example.model.MovieSearchResult;
import com.example.model.SearchResultItem;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import rx.functions.Action1;

/**
 * Created by carlushenry on 5/27/15.
 */
public class MovieController_ApiCall_Test {
    MovieController movieController;
    CountDownLatch latch;

    @Before
    public void setup() {
        movieController = new MovieController();
        latch = new CountDownLatch(1);
    }

    /*
    Testing the api call.
     */
    @Test
    public void testFindMoviesBySearchString() throws Exception {
        movieController
                .findMoviesBySearchString("Avengers") // will only emit one item.
                .subscribe(printMovieInformation());

        latch.await();
    }

    /*
    Just testing the movie detail api
     */
    @Test
    public void testFindMovieDetailByImdbId() throws Exception {
        movieController
                .findMovieDetailByImdbId("tt0112462") // will only emit one item
                .subscribe(printMovieInfoFromDetail());

        latch.await();
    }

    /* ======================================================================== */

    private Action1<MovieDetail> printMovieInfoFromDetail() {
        return new Action1<MovieDetail>() {
            @Override
            public void call(MovieDetail movieDetail) {
                System.out.println(movieDetail);
                latch.countDown();
            }
        };
    }

    private Action1<MovieSearchResult> printMovieInformation() {
        return new Action1<MovieSearchResult>() {
            @Override
            public void call(MovieSearchResult movieSearchResult) {
                for (SearchResultItem currSearchResultItem : movieSearchResult.getSearchResultItems()) {
                    System.out.println(String.format("%s, %s, %s, %s", currSearchResultItem.getImdbId(), currSearchResultItem.getTitle(), currSearchResultItem.getType(), currSearchResultItem.getYear()));
                }
                latch.countDown();
            }
        };
    }









}
