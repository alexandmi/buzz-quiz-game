/**
 * This class represents the additional graphics required for the Fast Answer graphic round.It sets the instructions for
 * the round.It uses the wildCardPanel of the GraphicsRounds to create the answered first panel.In that panel,in the case
 * that both players answer right it will show who answered first.
 */

package graphics;

import javax.swing.*;
import java.awt.*;


public class GraphicsFastAnswer extends GraphicsRounds {
    
    private JLabel answeredFirst;
    private JLabel distributionOfPoints;

    
    public GraphicsFastAnswer(int index,int number,String name1,String name2)
    {
        super(index,number,name1,name2);

        /***** INSTRUCTIONS PANEL *****/

        titleLabel.setText("ROUND: FAST ANSWER");

        JLabel phrase1 = new JLabel("Whoever player answers correctly first, gains 1000 points.If the other player");
        phrase1.setAlignmentX(Component.CENTER_ALIGNMENT);
        phrase1.setFont(new Font("SERIF", Font.PLAIN, 20));
        instructions.add(phrase1);

        JLabel phrase2 = new JLabel("also answered correctly,but later than the first one,gains 500 points.");
        phrase2.setAlignmentX(Component.CENTER_ALIGNMENT);
        phrase2.setFont(new Font("SERIF", Font.PLAIN, 20));
        instructions.add(phrase2);

        inTheCaseOfSecondPlayer.setText("For the second player,the controls are:");

        /***** ANSWERED FIRST PANEL *****/

        JPanel answeredFirstPanel = new JPanel(new FlowLayout());
        answeredFirstPanel.setBackground(Color.orange);
        getWildcardPanel().add(answeredFirstPanel);

        answeredFirst = new JLabel();
        answeredFirst.setFont(new Font("SERIF", Font.PLAIN, 20));
        answeredFirstPanel.add(answeredFirst);

        /***** DISTRIBUTION OF POINTS PANEL *****/

        JPanel distributionOfPointsPanel = new JPanel(new FlowLayout());
        distributionOfPointsPanel.setBackground(Color.orange);
        getWildcardPanel().add(distributionOfPointsPanel);

        distributionOfPoints = new JLabel();
        distributionOfPoints.setFont(new Font("SERIF", Font.PLAIN, 20));
        distributionOfPointsPanel.add(distributionOfPoints);
    }


    public void setWhoAnsweredFaster(String name1,String name2) {
        answeredFirst.setText("Both players answered correctly but "+name1+" answered first");
        distributionOfPoints.setText("As a result "+name1+" gets 1000 points and "+name2+" gets 500 points");
    }


    public void resetWhoAnsweredFaster() {
        answeredFirst.setText("");
        distributionOfPoints.setText("");
    }
}
