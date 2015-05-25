import com.example.MovieController;
import com.example.model.MovieSearchResult;
import com.example.model.SearchResultItem;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import rx.functions.Action1;

/**
 * Created by carlushenry on 5/25/15.
 */
public class MovieControllerTest {

    MovieController movieController;
    CountDownLatch latch;
    @Before
    public void setup() {
        movieController = new MovieController();
        latch = new CountDownLatch(1);
    }

    @Test
    public void testFindMoviesBySearchString() throws Exception {
        System.out.println("Starting now!!!");
        movieController.findMoviesBySearchString("Avengers").
                subscribe(new Action1<MovieSearchResult>() {
            @Override
            public void call(MovieSearchResult movieSearchResult) {
                System.out.println("Found " + movieSearchResult.getSearchResultItems().size());
                for (SearchResultItem currSearchResultItem : movieSearchResult.getSearchResultItems()) {
                    System.out.println(String.format("%s, %s, %s, %s", currSearchResultItem.getImdbId(), currSearchResultItem.getTitle(), currSearchResultItem.getType(), currSearchResultItem.getYear()));
                }
                latch.countDown();
            }
        });

        latch.await();
    }


    @Test
    public void testFindTotalMoviesUsingMultipleSearchStrings() throws Exception {
        System.out.println("Starting now!!!");
        movieController.findAllMovieTitlesUsingTheTwoSearchStrings("Avengers", "Batman").
                subscribe(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> allTitles) {
                        for (String currTitle : allTitles) {
                            System.out.println("Movie Title: " + currTitle);
                        }
                        latch.countDown();
                    }
                });

        latch.await();
    }

}
