import com.example.MovieController;
import com.example.model.MovieSearchResult;
import com.example.model.SearchResultItem;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import rx.functions.Action1;

/**
 * Created by carlushenry on 5/25/15.
 */
public class MovieControllerTest {

    MovieController movieController;
    @Before
    public void setup() {
        movieController = new MovieController();
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
            }
        });

        Thread.sleep(5000);
    }
}
