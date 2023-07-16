/**
 * This class represents the additional logic required for the Timer round.It saves the time left on the timer when some
 * player answered.
 */

package rounds;

import java.io.IOException;
import java.util.ArrayList;

public class RoundTimer extends Round {

    private int timeLeft1;
    private int timeLeft2;

    public RoundTimer(ArrayList<Integer> unavailableQuestions, int numberOfQuestions) throws IOException {
        super(unavailableQuestions, numberOfQuestions);
    }

    public void setTimeLeft(int indexOfPlayer,int time) {
        if(indexOfPlayer==0)
            timeLeft1 = time;
        else timeLeft2 = time;
    }

    public int getTimeLeft(int indexOfPlayer) {
        if(indexOfPlayer==0)
            return timeLeft1;
        else
            return timeLeft2;
    }
}
