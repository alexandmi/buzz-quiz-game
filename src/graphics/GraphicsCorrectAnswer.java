/**
 * This class represents the additional graphics required for the Correct Answer graphic round.It sets the instructions
 * for the round.
 */

package graphics;

import javax.swing.*;
import java.awt.*;

public class GraphicsCorrectAnswer extends GraphicsRounds {
    
    public GraphicsCorrectAnswer(int index,int number,String name1,String name2) {
        
        super(index,number,name1,name2);

        titleLabel.setText("ROUND: CORRECT ANSWER");
        JLabel phrase = new JLabel("Every player that answers correctly gains 1000 points.");
        phrase.setAlignmentX(Component.CENTER_ALIGNMENT);
        phrase.setFont(new Font("SERIF", Font.PLAIN, 20));
        instructions.add(phrase);

        if(!(name2 == null)) {
            inTheCaseOfSecondPlayer.setText("For the second player,the controls are:");
        }
    }
}
