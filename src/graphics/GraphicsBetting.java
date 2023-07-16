/**
 * This class represents the additional graphics required for the Betting graphic round.It sets the instructions for
 * the round.It uses the wildCardPanel of the GraphicsRounds to create the bets panel.In that panel before the revealing
 * of the questions the player is shown only the category of the question,he chooses a bet and he presses the Reveal Button
 * to proceed with the question.
 */

package graphics;

import javax.swing.*;
import java.awt.*;


public class GraphicsBetting extends GraphicsRounds {

    private JPanel categoryPanel;
    private JLabel theCategoryOfQuestionIs;

    private JSpinner player1BetSpinner;
    private JLabel finalBet1Label;
    private JSpinner player2BetSpinner;
    private JLabel finalBet2Label;

    private JPanel revealQuestionPanel;
    private JButton revealQuestionButton;


    public GraphicsBetting(int index,int number,String name1,String name2) {

        super(index,number,name1,name2);

        /***** INSTRUCTIONS PANEL *****/

        titleLabel.setText("ROUND: BETTING");

        JLabel phrase1 = new JLabel("At the start of each round you will be shown only its category and you'll");
        phrase1.setAlignmentX(Component.CENTER_ALIGNMENT);
        phrase1.setFont(new Font("SERIF", Font.PLAIN, 20));
        instructions.add(phrase1);

        JLabel phrase2 = new JLabel("have to place a bet of 250, 500, 750 or 1000 points.If you answer correctly,");
        phrase2.setAlignmentX(Component.CENTER_ALIGNMENT);
        phrase2.setFont(new Font("SERIF", Font.PLAIN, 20));
        instructions.add(phrase2);

        JLabel phrase3 = new JLabel("you win your betting points,otherwise you lose them.");
        phrase3.setAlignmentX(Component.CENTER_ALIGNMENT);
        phrase3.setFont(new Font("SERIF", Font.PLAIN, 20));
        instructions.add(phrase3);

        if(!(name2 == null)) {
            inTheCaseOfSecondPlayer.setText("For the second player,the controls are:");
        }

        /***** CATEGORY PANEL *****/

        categoryPanel = new JPanel(new FlowLayout());
        categoryPanel.setBackground(Color.orange);
        getWildcardPanel().add(categoryPanel);

        theCategoryOfQuestionIs = new JLabel();
        theCategoryOfQuestionIs.setFont(new Font("SERIF", Font.PLAIN, 20));
        categoryPanel.add(theCategoryOfQuestionIs);

        /***** BETS PANEL *****/

        JPanel betsPanel = new JPanel(new FlowLayout());
        betsPanel.setBackground(Color.orange);
        getWildcardPanel().add(betsPanel);

        JLabel player1BetLabel = new JLabel(name1+" bet:");
        player1BetLabel.setFont(new Font("SERIF", Font.PLAIN, 20));
        betsPanel.add(player1BetLabel);

        SpinnerNumberModel bounds1 = new SpinnerNumberModel(250.0, 250.0, 1000.0, 250.0);
        player1BetSpinner = new JSpinner(bounds1);
        player1BetSpinner.setEditor(new JSpinner.DefaultEditor(player1BetSpinner));
        Dimension dimensionForSpinner = new Dimension(60, 25);
        player1BetSpinner.setPreferredSize(dimensionForSpinner);
        betsPanel.add(player1BetSpinner);

        finalBet1Label = new JLabel();
        finalBet1Label.setFont(new Font("SERIF", Font.PLAIN, 20));
        betsPanel.add(finalBet1Label);

        JLabel player2BetLabel = new JLabel("         "+name2+" bet:");
        player2BetLabel.setFont(new Font("SERIF", Font.PLAIN, 20));
        if(numberOfPlayers==2) betsPanel.add(player2BetLabel);

        SpinnerNumberModel bounds2 = new SpinnerNumberModel(250.0, 250.0, 1000.0, 250.0);
        player2BetSpinner = new JSpinner(bounds2);
        player2BetSpinner.setEditor(new JSpinner.DefaultEditor(player2BetSpinner));
        player2BetSpinner.setPreferredSize(dimensionForSpinner);
        if(numberOfPlayers==2) betsPanel.add(player2BetSpinner);

        finalBet2Label = new JLabel();
        finalBet2Label.setFont(new Font("SERIF", Font.PLAIN, 20));
        if(numberOfPlayers==2) betsPanel.add(finalBet2Label);

        /***** REVEAL QUESTION BUTTON *****/

        revealQuestionPanel = new JPanel(new FlowLayout());
        revealQuestionPanel.setBackground(Color.orange);
        getWildcardPanel().add(revealQuestionPanel);

        revealQuestionButton = new JButton("Reveal Question");
        revealQuestionButton.setBackground(Color.RED);
        revealQuestionButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        revealQuestionPanel.add(revealQuestionButton);
    }


    public void setCategoryBet(String category) {
        theCategoryOfQuestionIs.setText("The category of the question is: "+category);
    }


    /**
     * This method locks or unlocks certain components of the betting graphics round when they should be hidden.
     * @param unlock boolean which states whether to lock or unlock
     */
    public void unlockBets(boolean unlock) {
        
        revealQuestionPanel.setVisible(unlock);
        categoryPanel.setVisible(unlock);
        player1BetSpinner.setVisible(unlock);
        player2BetSpinner.setVisible(unlock);
       
        if(unlock) {
         
            finalBet1Label.setText("");
            finalBet2Label.setText("");
            labelCategory.setText("");
            labelQuestion.setText("");
            labelAnswerA.setText("                       A.-");
            labelAnswerB.setText("                       B.-");
            labelAnswerC.setText("                       C.-");
            labelAnswerD.setText("                       D.-");
        }
        else {
            double bet1 = (double)player1BetSpinner.getValue();
            double bet2 = (double)player2BetSpinner.getValue();
            finalBet1Label.setText(String.valueOf((int)bet1));
            finalBet2Label.setText(String.valueOf((int)bet2));
        }
    }

    public double getBettingPoints(int indexOfPlayer) {
        if(indexOfPlayer==0)
            return (Double)player1BetSpinner.getValue();
        else
            return (Double)player2BetSpinner.getValue();
    }

    public JButton getRevealQuestionButton() {
        return revealQuestionButton; 
    }
}
