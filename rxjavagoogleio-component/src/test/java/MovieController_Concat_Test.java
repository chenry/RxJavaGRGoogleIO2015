import com.example.MovieController;
import com.example.model.MovieDetail;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import rx.Subscriber;

/**
 * Created by carlushenry on 5/27/15.
 */
public class MovieController_Concat_Test {
    MovieController movieController;
    CountDownLatch latch;

    @Before
    public void setup() {
        movieController = new MovieController();
        latch = new CountDownLatch(1);
    }

    // CONCAT
    // I want the details to come out in the order of the search
    @Test
    public void testFindMovieDetailsByTwoSearchStrings() throws Exception {
        movieController
                .findAllMovieDetailsConcat("Batman", "Avengers")
                .subscribe(printMovieTitles());

        latch.await();
    }

    /* ============================================= */

    private Subscriber<MovieDetail> printMovieTitles() {
        return new Subscriber<MovieDetail>() {
            @Override
            public void onCompleted() {
                latch.countDown();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(MovieDetail movieDetail) {
                System.out.println("Concat: " + movieDetail.getTitle());
            }
        };
    }
}
