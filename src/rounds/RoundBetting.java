/**
 * This class represents the additional logic required for the Betting round.It saves the betting points of both players.
 */

package rounds;

import java.io.IOException;
import java.util.ArrayList;


public class RoundBetting extends Round {
    
    private double bettingPoints1;
    private double bettingPoints2;


    public RoundBetting(ArrayList<Integer> unavailableQuestions, int numberOfQuestions) throws IOException {
        super(unavailableQuestions,numberOfQuestions);
    }


    public void setBettingPoints(int indexOfPlayer,double bettingPoints) {
        if(indexOfPlayer==0) this.bettingPoints1 = bettingPoints;
        else
            this.bettingPoints2 = bettingPoints;
    }

    public double getBettingPoints(int indexOfPlayer) {
        if(indexOfPlayer==0)
            return bettingPoints1;
        else
            return bettingPoints2;
    }
}
