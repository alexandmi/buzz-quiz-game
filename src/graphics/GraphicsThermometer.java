/**
 * This class represents the additional graphics required for the Thermometer graphic round.It sets the instructions for
 * the round.It uses the wildCardPanel of the GraphicsRounds to create the number of correct answers panel.In that panel
 * the correct answers of each player are counted until one of them reaches 5.
 */

package graphics;

import javax.swing.*;
import java.awt.*;


public class GraphicsThermometer extends GraphicsRounds {
  
    private JLabel player1NumberLabel;
    private JLabel player2NumberLabel;
    private JLabel theWinnerIsLabel;

    public GraphicsThermometer(int index,int number,String name1,String name2) {
        
        super(index,number,name1,name2);

        player1NewPoints.setVisible(false);
        player2NewPoints.setVisible(false);

        /***** INSTRUCTIONS PANEL *****/

        titleLabel.setText("ROUND: THERMOMETER");

        JLabel phrase1 = new JLabel("The first player to answer 5 questions correctly,wins 5000 points.");
        phrase1.setAlignmentX(Component.CENTER_ALIGNMENT);
        phrase1.setFont(new Font("SERIF", Font.PLAIN, 20));
        instructions.add(phrase1);

        inTheCaseOfSecondPlayer.setText("For the second player,the controls are:");

        /***** NUMBER OF CORRECT ANSWERS PANEL *****/

        JPanel numbersOfCorrectAnswersPanel = new JPanel(new FlowLayout());
        numbersOfCorrectAnswersPanel.setBackground(Color.orange);
        getWildcardPanel().add(numbersOfCorrectAnswersPanel);

        JLabel player1NameLabel = new JLabel(name1+" correct answers:");
        player1NameLabel.setFont(new Font("SERIF", Font.PLAIN, 20));
        numbersOfCorrectAnswersPanel.add(player1NameLabel);

        player1NumberLabel = new JLabel("0");
        player1NumberLabel.setFont(new Font("SERIF", Font.PLAIN, 20));
        numbersOfCorrectAnswersPanel.add(player1NumberLabel);

        JLabel player2NameLabel = new JLabel("          "+name2+" correct answers:");
        player2NameLabel.setFont(new Font("SERIF", Font.PLAIN, 20));
        numbersOfCorrectAnswersPanel.add(player2NameLabel);

        player2NumberLabel = new JLabel("0");
        player2NumberLabel.setFont(new Font("SERIF", Font.PLAIN, 20));
        numbersOfCorrectAnswersPanel.add(player2NumberLabel);

        /***** WINNER ANNOUNCEMENT PANEL *****/

        JPanel theWinnerIsPanel = new JPanel(new FlowLayout());
        theWinnerIsPanel.setBackground(Color.orange);
        getWildcardPanel().add(theWinnerIsPanel);

        theWinnerIsLabel = new JLabel();
        theWinnerIsLabel.setFont(new Font("SERIF", Font.PLAIN, 20));
        theWinnerIsPanel.add(theWinnerIsLabel);
    }


    public void setCorrectAnswersLabel(int indexOfPlayer,int correctAnswers) {
        if(indexOfPlayer==0)
            player1NumberLabel.setText(String.valueOf(correctAnswers));
        else
            player2NumberLabel.setText(String.valueOf(correctAnswers));
    }


    public void setTheWinner(String name) {
        theWinnerIsLabel.setText(name+" has answered 5 questions correctly faster. "+name+" gains 5000 points");
    }
}
