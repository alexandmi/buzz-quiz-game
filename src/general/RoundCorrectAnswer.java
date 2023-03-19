/**
 * This class represents the Correct Answer type of round.
 */

package general;
import java.io.IOException;
import java.util.ArrayList;

public class RoundCorrectAnswer extends Round {

    public RoundCorrectAnswer(ArrayList<Integer> unavailableQuestions, int numberOfQuestions) throws IOException {
        super(unavailableQuestions, numberOfQuestions);
    }
}
