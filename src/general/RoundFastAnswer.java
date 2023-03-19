/**
 * This class represents the additional logic required for the FastAnswer round.It saves the index of the player who answered
 * first.
 */

package general;

import java.io.IOException;
import java.util.ArrayList;

public class RoundFastAnswer extends Round {
    
    private int hasAnsweredFirst = -1;

    public RoundFastAnswer(ArrayList<Integer> unavailableQuestions, int numberOfQuestions) throws IOException {
        super(unavailableQuestions,numberOfQuestions);
    }

    public int getWhoAnsweredFirst() {
        return hasAnsweredFirst;
    }

    public void setWhoAnsweredFirst(int indexOfPlayer) {
        hasAnsweredFirst = indexOfPlayer;
    }

    public void resetWhoAnsweredFirst() {
        hasAnsweredFirst = -1;
    }
}
