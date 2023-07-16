/**
 * This class represents the graphics of the High Scores.It utilizes 4 ArrayLists for the names of the players and their
 * points and wins.These results are shown on two columns for each type of game(single and double player).It uses the
 * getScoreOneMap and getScoreTwoMap to input the results from the High Scores logic.
 */

package highScores;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.Map;


public class GraphicsHighScores extends JPanel {

    private JButton goBackButton;
    private ArrayList<JLabel> namesOne = new ArrayList<>();
    private ArrayList<JLabel> namesTwo = new ArrayList<>();
    private ArrayList<JLabel> points = new ArrayList<>();
    private ArrayList<JLabel> wins = new ArrayList<>();

    public GraphicsHighScores() {
        
        BoxLayout boxLayoutScores = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(boxLayoutScores);
        this.setBackground(Color.orange);

        /***** TITLE PANEL *****/

        JPanel titlePanel = new JPanel(new FlowLayout());
        titlePanel.setBackground(Color.red);
        this.add(titlePanel);

        JLabel titleScores = new JLabel("HIGH SCORES");
        titleScores.setFont(new Font("SERIF", Font.PLAIN, 22));
        Font font;
        Map attributes;
        font = titleScores.getFont();
        attributes = font.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        titleScores.setFont(font.deriveFont(attributes));
        titlePanel.add(titleScores);
        this.add(Box.createRigidArea(new Dimension(0, 30)));

        /***** TYPE OF GAME PANEL *****/

        JPanel typeOfGamePanel = new JPanel(new FlowLayout());
        typeOfGamePanel.setBackground(Color.orange);
        this.add(typeOfGamePanel);

        JLabel onePlayerLabel = new JLabel("ONE PLAYER");
        onePlayerLabel.setFont(new Font("SERIF", Font.PLAIN, 20));
        typeOfGamePanel.add(onePlayerLabel);

        JLabel twoPlayersLabel = new JLabel("                                               TWO PLAYERS");
        twoPlayersLabel.setFont(new Font("SERIF", Font.PLAIN, 20));
        typeOfGamePanel.add(twoPlayersLabel);
        this.add(Box.createRigidArea(new Dimension(0, 30)));

        /***** PANEL NAME AND POINTS *****/

        JPanel nameAndPointsPanel = new JPanel(new FlowLayout());
        nameAndPointsPanel.setBackground(Color.orange);
        this.add(nameAndPointsPanel);

        JLabel nameAndPointsLabel = new JLabel("Name                  Points");
        nameAndPointsLabel.setFont(new Font("SERIF", Font.PLAIN, 20));
        nameAndPointsPanel.add(nameAndPointsLabel);

        JLabel nameAndWinsLabel = new JLabel("                                 Name                       Wins");
        nameAndWinsLabel.setFont(new Font("SERIF", Font.PLAIN, 20));
        nameAndPointsPanel.add(nameAndWinsLabel);

        /***** PANEL SCORES *****/

        JPanel scores = new JPanel();
        BoxLayout boxLayoutNamesPoints = new BoxLayout(scores, BoxLayout.X_AXIS);
        scores.setLayout(boxLayoutNamesPoints);

        scores.setBackground(Color.orange);
        this.add(scores);

        JPanel namesOnePanel = new JPanel();
        BoxLayout boxLayoutNamesOne = new BoxLayout(namesOnePanel, BoxLayout.Y_AXIS);
        namesOnePanel.setLayout(boxLayoutNamesOne);
        namesOnePanel.setBackground(Color.orange);
        scores.add(namesOnePanel);
        scores.add(Box.createRigidArea(new Dimension(100, 0)));

        for(int i=0;i<10;i++) {
            JLabel playerLabel = new JLabel((i + 1) + ". ----------");
            playerLabel.setFont(new Font("SERIF", Font.PLAIN, 20));
            namesOnePanel.add(playerLabel);
            namesOne.add(playerLabel);
        }

        JPanel pointsPanel = new JPanel();
        BoxLayout boxLayoutPoints = new BoxLayout(pointsPanel, BoxLayout.Y_AXIS);
        pointsPanel.setLayout(boxLayoutPoints);
        pointsPanel.setBackground(Color.orange);
        scores.add(pointsPanel);
        scores.add(Box.createRigidArea(new Dimension(150, 0)));

        for(int i=0;i<10;i++) {
            JLabel pointsLabel = new JLabel("----");
            pointsLabel.setFont(new Font("SERIF", Font.PLAIN, 20));
            pointsPanel.add(pointsLabel);
            points.add(pointsLabel);
        }

        JPanel namesTwoPanel = new JPanel();
        BoxLayout boxLayoutNamesTwo = new BoxLayout(namesTwoPanel, BoxLayout.Y_AXIS);
        namesTwoPanel.setLayout(boxLayoutNamesTwo);
        namesTwoPanel.setBackground(Color.orange);
        scores.add(namesTwoPanel);
        scores.add(Box.createRigidArea(new Dimension(100, 0)));

        for(int i=0;i<10;i++) {
            JLabel playerLabel = new JLabel((i + 1) + ". --------");
            playerLabel.setFont(new Font("SERIF", Font.PLAIN, 20));
            namesTwoPanel.add(playerLabel);
            namesTwo.add(playerLabel);
        }

        JPanel winsPanel = new JPanel();
        BoxLayout boxLayoutWins = new BoxLayout(winsPanel, BoxLayout.Y_AXIS);
        winsPanel.setLayout(boxLayoutWins);
        winsPanel.setBackground(Color.orange);
        scores.add(winsPanel);

        for(int i=0;i<10;i++) {
            JLabel winsLabel = new JLabel("--");
            winsLabel.setFont(new Font("SERIF", Font.PLAIN, 20));
            winsPanel.add(winsLabel);
            wins.add(winsLabel);
        }

        this.add(Box.createRigidArea(new Dimension(0, 50)));

        /***** PANEL BUTTON *****/

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.orange);
        this.add(buttonPanel);

        goBackButton = new JButton("Go Back");
        goBackButton.setBackground(Color.RED);
        goBackButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        goBackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(goBackButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 120)));
    }


    public JButton getGoBackButton() { return goBackButton; }


    public void getScoreOneMap(Map<Integer,UserInformation> highScores) {
       
        for(int i=0;i<highScores.size();i++) {
            String tempName = highScores.get(i).getName();
            namesOne.get(i).setText((i + 1) + ". "+tempName);
            int tempPoints = highScores.get(i).getPoints();
            points.get(i).setText(tempPoints+"");
        }
    }


    public void getScoreTwoMap(Map<Integer,UserInformation> highScores) {
   
        for(int i=0;i<highScores.size();i++) {
            String tempName = highScores.get(i).getName();
            namesTwo.get(i).setText((i + 1) + ". "+tempName);
            int tempWins = highScores.get(i).getWins();
            wins.get(i).setText(tempWins+"");
        }
    }
}
