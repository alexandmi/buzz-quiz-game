import java.io.IOException;
import java.util.ArrayList;

/**
 * This class represents the Betting round type.
 */
public class RoundBetting extends Round {
    
    public RoundBetting(ArrayList<Integer> unavailableQuestions, int numberOfQuestions) throws IOException {
        
        super(unavailableQuestions,numberOfQuestions);
    }
}
