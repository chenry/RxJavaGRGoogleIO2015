import com.example.MovieController;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import rx.functions.Action1;

/**
 * Created by carlushenry on 5/27/15.
 */
public class MovieController_Zip_Test {
    MovieController movieController;
    CountDownLatch latch;

    @Before
    public void setup() {
        movieController = new MovieController();
        latch = new CountDownLatch(1);
    }

    // ZIP
    @Test
    public void testFindTotalMoviesUsingMultipleSearchStrings() throws Exception {
        movieController
                .findAllMovieTitlesUsingTheTwoSearchStrings("Avengers", "Batman")
                .subscribe(new Action1<List<String>>() {
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
