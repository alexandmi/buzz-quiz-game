import java.io.IOException;
import java.util.ArrayList;

/**
 * This class represents the Correct Answer Round type.
 */
public class RoundCorrectAnswer extends Round {

    public RoundCorrectAnswer(ArrayList<Integer> unavailableQuestions, int numberOfQuestions) throws IOException {
     
        super(unavailableQuestions,numberOfQuestions);
    }
}
