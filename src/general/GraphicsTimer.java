/**
 * This class represents the additional graphics required for the Timer graphic round.It sets the instructions for
 * the  round.It uses the wildCardPanel of the GraphicsRounds to create the Timer panel.
 */

package general;

import javax.swing.*;
import java.awt.*;


public class GraphicsTimer extends GraphicsRounds {

    private JPanel timerPanel;
    private JLabel timerLabel;

    private JLabel readyLabel;
    private JLabel player1Name;
    private JLabel player1Time;
    private JLabel player2Name;
    private JLabel player2Time;

    private JPanel beginPanel;
    private JButton beginButton;

    public GraphicsTimer(int index,int number,String name1,String name2) {
   
        super(index,number,name1,name2);

        /***** INSTRUCTIONS PANEL *****/

        titleLabel.setText("ROUND: TIMER");

        JLabel phrase1 = new JLabel("You will have 5 seconds to answer a question.If you answer correctly within");
        phrase1.setAlignmentX(Component.CENTER_ALIGNMENT);
        phrase1.setFont(new Font("SERIF", Font.PLAIN, 20));
        instructions.add(phrase1);

        JLabel phrase2 = new JLabel("these 5 seconds,you gain points equal to the product of the milliseconds left");
        phrase2.setAlignmentX(Component.CENTER_ALIGNMENT);
        phrase2.setFont(new Font("SERIF", Font.PLAIN, 20));
        instructions.add(phrase2);

        JLabel phrase3 = new JLabel(" on the clock the moment you answered.");
        phrase3.setAlignmentX(Component.CENTER_ALIGNMENT);
        phrase3.setFont(new Font("SERIF", Font.PLAIN, 20));
        instructions.add(phrase3);

        if(!(name2 == null))
            inTheCaseOfSecondPlayer.setText("For the second player,the controls are:");

        /***** TIMER PANEL *****/

        timerPanel = new JPanel(new FlowLayout());
        timerPanel.setBackground(Color.orange);
        getWildcardPanel().add(timerPanel);

        timerLabel = new JLabel();
        timerLabel.setFont(new Font("SERIF", Font.PLAIN, 20));
        timerPanel.add(timerLabel);

        /***** READY PANEL *****/

        JPanel readyPanel = new JPanel(new FlowLayout());
        readyPanel.setBackground(Color.orange);
        getWildcardPanel().add(readyPanel);

        readyLabel = new JLabel("Ready?");
        readyLabel.setFont(new Font("SERIF", Font.PLAIN, 20));
        readyPanel.add(readyLabel);

        player1Name = new JLabel(name1+" time:");
        player1Name.setFont(new Font("SERIF", Font.PLAIN, 20));
        readyPanel.add(player1Name);

        player1Time = new JLabel("-");
        player1Time.setFont(new Font("SERIF", Font.PLAIN, 20));
        readyPanel.add(player1Time);

        player2Name = new JLabel("    "+name2+" time:");
        player2Name.setFont(new Font("SERIF", Font.PLAIN, 20));
        if(numberOfPlayers==2) readyPanel.add(player2Name);

        player2Time = new JLabel("-");
        player2Time.setFont(new Font("SERIF", Font.PLAIN, 20));
        if(numberOfPlayers==2) readyPanel.add(player2Time);

        /***** BEGIN BUTTON PANEL *****/

        beginPanel = new JPanel(new FlowLayout());
        beginPanel.setBackground(Color.orange);
        getWildcardPanel().add(beginPanel);

        beginButton = new JButton("Begin!");
        beginButton.setBackground(Color.RED);
        beginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        beginPanel.add(beginButton);
    }


    /**
     * This method locks or unlocks certain components of the timer graphics round when they should be hidden.
     * @param unlock boolean which states whether to lock or unlock
     */
    public void unlockTimer(boolean unlock) {
      
        timerPanel.setVisible(unlock);
        player1Name.setVisible(unlock);
        player1Time.setVisible(unlock);
        player2Name.setVisible(unlock);
        player2Time.setVisible(unlock);
        readyLabel.setVisible(!unlock);
        beginPanel.setVisible(!unlock);

        if(!unlock) {
            player1Time.setText("-");
            player2Time.setText("-");
            labelCategory.setText("Category: -");
            labelQuestion.setText("");
            labelAnswerA.setText("                       A.-");
            labelAnswerB.setText("                       B.-");
            labelAnswerC.setText("                       C.-");
            labelAnswerD.setText("                       D.-");
        }
    }


    public void setTimerLabel(String timeLeft){ timerLabel.setText("Time left: "+timeLeft);}


    public void setPlayerTime(int indexOfPlayer,String time) {
        if(indexOfPlayer==0)
            player1Time.setText(time);
        else
            player2Time.setText(time);
    }

    public JButton getBeginButton() { 
        return beginButton; 
    }
}
