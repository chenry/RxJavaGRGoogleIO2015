import com.example.MovieController;
import com.example.model.MovieDetail;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by carlushenry on 5/27/15.
 */
public class MovieController_Merge_Test {
    MovieController movieController;
    CountDownLatch latch;

    @Before
    public void setup() {
        movieController = new MovieController();
        latch = new CountDownLatch(1);
    }

    //MERGE
    @Test
    public void testFindMovieDetailsByTwoSearchStrings() throws Exception {
        movieController
                .findAllMovieDetails("Avengers", "Batman")
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
                System.out.println("Merge: " + movieDetail.getTitle());
            }
        };
    }
}
