/**
 * This class represents the additional logic required for the Thermometer round.It saves the number of correct answers each
 * player has answered.
 */

package general;

import java.io.IOException;
import java.util.ArrayList;


public class RoundThermometer extends Round {

    private int correctAnswers1 = 0;
    private int correctAnswers2 = 0;

    public RoundThermometer(ArrayList<Integer> unavailableQuestions, int numberOfQuestions) throws IOException {
        super(unavailableQuestions, numberOfQuestions);

    }

    public void addCorrect(int indexOfPlayer) {
        if(indexOfPlayer==0)
            correctAnswers1++;
        else
            correctAnswers2++;
    }

    public int getCorrectAnswersOfPlayer(int indexOfPlayer) {
        if(indexOfPlayer==0)
            return correctAnswers1;
        else
            return correctAnswers2;
    }
}
