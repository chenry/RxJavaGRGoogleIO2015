import com.example.MovieController;
import com.example.model.MovieDetail;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;

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
                .subscribe(new Action1<MovieDetail>() {
                    @Override
                    public void call(MovieDetail movieDetail) {
                        System.out.println("Merge: " + movieDetail.getTitle());
                        latch.countDown();
                    }
                });

        latch.await();
    }
}
