/**
 * This class represents the general graphics of each round.It has a panel for instructions of the round and the buttons.It
 * has panels for the numbers of rounds and questions,for user names and points for the category of the question, the question
 * itself,the possible answers.It also has a panel which indicates whether the player has answered or not, and a panel
 * announcing the points he lost or won.It also includes a vacant pane called wildCardPanel,for the specific use of certain
 * types of rounds.In general it has everything that's common in every graphics of round.
 */

package graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.Map;


public class GraphicsRounds extends JPanel {
   
    protected JPanel instructionsPanel;
    protected JButton startRoundButton;
    protected JLabel titleLabel;
    protected JPanel instructions;
    protected JLabel inTheCaseOfSecondPlayer;

    protected int numberOfPlayers;
    private String username1;
    private String username2;

    private JLabel numberOfQuestion;
    private JLabel labelPoints1;
    private JLabel labelPoints2;
    private JPanel wildcardPanel;
    protected JLabel labelCategory;
    protected JLabel labelQuestion;
    protected JLabel labelAnswerA;
    protected JLabel labelAnswerB;
    protected JLabel labelAnswerC;
    protected JLabel labelAnswerD;

    private JPanel imagePanel;
    private JLabel imageLabel;

    private JLabel labelCorrectAnswer;
    protected JLabel player1NewPoints;
    protected JLabel player2NewPoints;

    private JLabel player1State;
    private JLabel player1Answer;
    private JLabel player2State;
    private JLabel player2Answer;

    private JButton nextButton;

    private boolean player1Locked = false;
    private boolean player2Locked = false;

    public GraphicsRounds(int index,int number,String name1,String name2) {
       
        numberOfPlayers = number;
        username1 = name1;
        username2 = name2;

        CardLayout cardLayoutRound = new CardLayout();
        this.setLayout(cardLayoutRound);
        this.setBackground(Color.orange);

        /***** INSTRUCTIONS PANEL *****/
        
        instructionsPanel = new JPanel();
        BoxLayout boxLayoutInstructions = new BoxLayout(instructionsPanel, BoxLayout.Y_AXIS);
        instructionsPanel.setLayout(boxLayoutInstructions);
        instructionsPanel.setBackground(Color.orange);

        JPanel titlePanel = new JPanel(new FlowLayout());
        titlePanel.setBackground(Color.red);
        instructionsPanel.add(titlePanel);

        titleLabel = createLabel("", 20, false, true);
        titlePanel.add(titleLabel);

        JLabel instructionsLabel = createLabel("Instructions", 20, true, true);
        instructionsPanel.add(Box.createRigidArea(new Dimension(0, 70)));
        instructionsPanel.add(instructionsLabel);

        instructions = new JPanel();
        BoxLayout boxLayoutInstructionsBox = new BoxLayout(instructions, BoxLayout.Y_AXIS);
        instructions.setLayout(boxLayoutInstructionsBox);
        instructions.setBackground(Color.orange);
        instructionsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        instructionsPanel.add(instructions);

        JPanel controlsPanel = new JPanel();
        BoxLayout boxLayoutControls = new BoxLayout(controlsPanel, BoxLayout.Y_AXIS);
        controlsPanel.setLayout(boxLayoutControls);
        controlsPanel.setBackground(Color.orange);
        instructionsPanel.add(Box.createRigidArea(new Dimension(0, 60)));
        instructionsPanel.add(controlsPanel);
        
        JLabel controlsLabel = createLabel("Controls", 20, true, true);
        controlsPanel.add(controlsLabel);

        JLabel player1Controls1Label = createLabel("A ==>Q    B==>W    C==>E    D==>R    ENTER==>S", 20, true, false);
        controlsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        controlsPanel.add(player1Controls1Label);

        inTheCaseOfSecondPlayer = createLabel("In the case of a second player,his controls are:", 20, true, false);
        controlsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        controlsPanel.add(inTheCaseOfSecondPlayer);

        JLabel player2ControlsLabel = createLabel("A ==>U    B==>I    C==>O    D==>P    ENTER==>K", 20, true,false);
        controlsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        controlsPanel.add(player2ControlsLabel);

        startRoundButton = new JButton("Start Round");
        startRoundButton.setBackground(Color.RED);
        startRoundButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        startRoundButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startRoundButton.addActionListener(e ->  cardLayoutRound.show(graphics.GraphicsRounds.this,"ROUND"));

        instructionsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        instructionsPanel.add(startRoundButton);
        instructionsPanel.add(Box.createRigidArea(new Dimension(0, 230)));

        this.add(instructionsPanel,"INSTRUCTIONS");

        /***** ROUND PANEL *****/

        JPanel roundPanel = new JPanel();
        BoxLayout boxLayoutRound = new BoxLayout(roundPanel, BoxLayout.Y_AXIS);
        roundPanel.setLayout(boxLayoutRound);
        roundPanel.setBackground(Color.orange);

        this.add(roundPanel,"ROUND");

        setKeys();

        /***** PANEL FOR INDEX OF ROUND AND QUESTION *****/

        JPanel indexOfRoundAndQuestion;

        indexOfRoundAndQuestion = new JPanel(new FlowLayout());
        indexOfRoundAndQuestion.setBackground(Color.RED);
        roundPanel.add(indexOfRoundAndQuestion);

        JLabel indexOfRound = createLabel("ROUND "+(index+1)+"               ", 22, false, false);
        numberOfQuestion = createLabel("", 22, false, false);

        indexOfRoundAndQuestion.add(indexOfRound);
        indexOfRoundAndQuestion.add(numberOfQuestion,RIGHT_ALIGNMENT);

        /***** PANEL FOR USER NAMES AND POINTS *****/

        JPanel userNamesAndPoints;

        userNamesAndPoints = new JPanel(new FlowLayout());
        userNamesAndPoints.setBackground(Color.orange);
        roundPanel.add(userNamesAndPoints);
        roundPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel labelUsername1 = createLabel(username1+": ", 22, false, false);
        labelPoints1 = createLabel("", 22, false, false);
        JLabel labelUsername2 = createLabel("               "+username2+": ", 22, false, false);        
        labelPoints2 = createLabel("", 22, false, false);

        userNamesAndPoints.add(labelUsername1);
        userNamesAndPoints.add(labelPoints1);
        userNamesAndPoints.add(labelUsername2);
        userNamesAndPoints.add(labelPoints2);

        if(numberOfPlayers==1){
            labelUsername2.setVisible(false);
            labelPoints2.setVisible(false);
        }

        /***** PANEL FOR USE OF SPECIFIC ROUNDS *****/

        wildcardPanel = new JPanel();
        BoxLayout boxLayoutWildCard = new BoxLayout(wildcardPanel, BoxLayout.Y_AXIS);
        wildcardPanel.setBackground(Color.orange);
        wildcardPanel.setLayout(boxLayoutWildCard);
        roundPanel.add(wildcardPanel);

        /***** PANEL FOR CATEGORY *****/

        JPanel panelCategory;

        panelCategory = new JPanel(new FlowLayout());
        panelCategory.setBackground(Color.orange);
        roundPanel.add(panelCategory);

        labelCategory = createLabel("", 20, false, false);
        panelCategory.add(labelCategory);

        /***** PANEL FOR IMAGE *****/

        imagePanel = new JPanel(new FlowLayout());
        imagePanel.setBackground(Color.orange);
        imagePanel.setPreferredSize(new Dimension(300,200));
        imagePanel.setVisible(false);
        roundPanel.add(imagePanel);

        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(300,200));
        imagePanel.add(imageLabel);

        /***** PANEL FOR QUESTION *****/

        JPanel panelQuestion;

        panelQuestion = new JPanel(new FlowLayout());
        panelQuestion.setBackground(Color.orange);
        roundPanel.add(panelQuestion);
        roundPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        labelQuestion = createLabel("", 20, false, true);
        panelQuestion.add(labelQuestion);

        /***** PANEL FOR ANSWERS *****/

        JPanel answers = new JPanel(new GridLayout(2,2));
        answers.setBackground(Color.orange);
        roundPanel.add(answers);
        roundPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        labelAnswerA = createLabel("", 20, false, false);
        labelAnswerB = createLabel("", 20, false, false);
        labelAnswerC = createLabel("", 20, false, false);
        labelAnswerD = createLabel("", 20, false, false);

        answers.add(labelAnswerA);
        answers.add(labelAnswerB);
        answers.add(labelAnswerC);
        answers.add(labelAnswerD);

        /***** PANEL FOR ANNOUNCEMENT OF CORRECT ANSWER *****/

        JPanel resultAnswerPanel = new JPanel(new FlowLayout());
        resultAnswerPanel.setBackground(Color.orange);
        roundPanel.add(resultAnswerPanel);

        labelCorrectAnswer = createLabel("", 20, false, false);
        resultAnswerPanel.add(labelCorrectAnswer);

        /***** PANEL FOR ANNOUNCEMENT OF NEW POINTS *****/

        JPanel resultPointsPanel = new JPanel(new FlowLayout());
        resultPointsPanel.setBackground(Color.orange);
        roundPanel.add(resultPointsPanel);

        player1NewPoints = createLabel("", 20, false, false);
        resultPointsPanel.add(player1NewPoints);

        player2NewPoints = createLabel("", 20, false, false);

        if(numberOfPlayers==2) resultPointsPanel.add(player2NewPoints);

        /***** PANEL FOR STATE OF PLAYERS ANSWER *****/

        JPanel currentStateOfAnswers;

        currentStateOfAnswers = new JPanel(new FlowLayout());
        currentStateOfAnswers.setBackground(Color.orange);
        roundPanel.add(currentStateOfAnswers);

        JLabel playerName1 = createLabel(username1+":", 20, false, false);
        player1State = createLabel("Current Answer", 20, false, false);
        player1Answer = createLabel("-", 20, false, false);
        JLabel playerName2 = createLabel("        "+username2+":", 20, false, false);
        player2State = createLabel("Current Answer", 20, false, false);
        player2Answer = createLabel("-", 20, false, false);

        currentStateOfAnswers.add(playerName1);
        currentStateOfAnswers.add(player1State);
        currentStateOfAnswers.add(player1Answer);
        currentStateOfAnswers.add(playerName2);
        currentStateOfAnswers.add(player2State);
        currentStateOfAnswers.add(player2Answer);


        if(numberOfPlayers==1) {
            playerName2.setVisible(false);
            player2State.setVisible(false);
            player2Answer.setVisible(false);
        }

        /***** PANEL FOR NEXT QUESTION OR ROUND BUTTON *****/

        JPanel panelForButton;

        panelForButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelForButton.setBackground(Color.orange);
        roundPanel.add(panelForButton);

        nextButton = new JButton("Next");
        nextButton.setBackground(Color.RED);
        nextButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        nextButton.setEnabled(false);
        panelForButton.add(nextButton);
    }


    /**
     * This method sets certain keyboard keys to give possible answers of a question.For A,B,C,D we have Q,W,E,R for the
     * first player and U,I,O,P for the second.The enter is the S key for the first player and the K key for the second.
     */
    private void setKeys() {
        String Q_KEY = "Q PRESSED";
        Action QKey;
        QKey = new AbstractAction(Q_KEY) {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!player1Locked) player1Answer.setText("A");
            }
        };
        this.getInputMap(2).put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0), Q_KEY);
        this.getActionMap().put(Q_KEY, QKey);

        String W_KEY = "W PRESSED";
        Action WKey;
        WKey = new AbstractAction(W_KEY) {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!player1Locked) player1Answer.setText("B");
            }
        };
        this.getInputMap(2).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), W_KEY);
        this.getActionMap().put(W_KEY, WKey);

        String E_KEY = "E PRESSED";
        Action EKey;
        EKey = new AbstractAction(E_KEY) {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!player1Locked) player1Answer.setText("C");
            }
        };
        this.getInputMap(2).put(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0), E_KEY);
        this.getActionMap().put(E_KEY, EKey);

        String R_KEY = "R PRESSED";
        Action RKey;
        RKey = new AbstractAction(R_KEY) {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!player1Locked) player1Answer.setText("D");
            }
        };
        this.getInputMap(2).put(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0), R_KEY);
        this.getActionMap().put(R_KEY, RKey);

        String U_KEY = "U PRESSED";
        Action UKey;
        UKey = new AbstractAction(U_KEY) {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!player2Locked) player2Answer.setText("A");
            }
        };
        this.getInputMap(2).put(KeyStroke.getKeyStroke(KeyEvent.VK_U, 0), U_KEY);
        this.getActionMap().put(U_KEY, UKey);

        String I_KEY = "I PRESSED";
        Action IKey;
        IKey = new AbstractAction(I_KEY) {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!player2Locked) player2Answer.setText("B");
            }
        };
        this.getInputMap(2).put(KeyStroke.getKeyStroke(KeyEvent.VK_I, 0), I_KEY);
        this.getActionMap().put(I_KEY, IKey);

        String O_KEY = "O PRESSED";
        Action OKey;
        OKey = new AbstractAction(O_KEY) {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!player2Locked) player2Answer.setText("C");
            }
        };
        this.getInputMap(2).put(KeyStroke.getKeyStroke(KeyEvent.VK_O, 0), O_KEY);
        this.getActionMap().put(O_KEY, OKey);

        String P_KEY = "P PRESSED";
        Action PKey;
        PKey = new AbstractAction(P_KEY) {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!player2Locked) player2Answer.setText("D");
            }
        };
        this.getInputMap(2).put(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0), P_KEY);
        this.getActionMap().put(P_KEY, PKey);
    }

    public JLabel getPlayer1Answer() { return player1Answer; }

    public JLabel getPlayer2Answer() { return player2Answer; }

    public JPanel getWildcardPanel() { return wildcardPanel; }

    public JButton getNextButton() { return nextButton; }


    /**
     * This method locks and unlocks certain parts of the graphics depending on the phase of the round.
     * @param indexOfPlayer the index of the player
     * @param isLocked boolean that shows whether we should lock or not
     */
    public void changeLockStatus(int indexOfPlayer,boolean isLocked) {
        if(indexOfPlayer==0) {
            player1Locked = isLocked;
            if(isLocked)
                player1State.setText("Locked Answer");
            else
                player1State.setText("Current Answer");
        }
        else {
            player2Locked = isLocked;
            if(isLocked)
                player2State.setText("Locked Answer");
            else
                player2State.setText("Current Answer");
        }
    }

    public void setResults(String correctAnswer,String player1result,String player2result,int player1Points,int player2Points) {
        labelCorrectAnswer.setText("The correct answer is: "+correctAnswer);
        player1NewPoints.setText(username1+" "+player1result+" "+player1Points+" points.");
        player2NewPoints.setText("         "+username2+" "+player2result+" "+player2Points+" points.");
    }

    /**
     * This method locks and unlocks certain parts of the graphics depending on the phase of the round.
     */
    public void resetResultsLockStatusAnswers() {
        
        labelCorrectAnswer.setText("");
        player1NewPoints.setText("");
        player2NewPoints.setText("");
        changeLockStatus(0,false);
        changeLockStatus(0,false);
        changeLockStatus(1,false);
        changeLockStatus(1,false);
        player1Answer.setText("-");
        player2Answer.setText("-");
        imagePanel.setVisible(false);
    }

    public void setNumberOfQuestion(int number) {
        numberOfQuestion.setText("QUESTION "+number);
    }

    public void setPoints(int points,int indexOfPlayer) {
        if(indexOfPlayer==0)
            labelPoints1.setText(points+" points");
        else
            labelPoints2.setText(points+" points");
    }

    public void setCategory(String category) {
        labelCategory.setText("Category: "+ category);
    }

    public void setImage(Image image) {
        imagePanel.setVisible(true);
        Image tempImage = image.getScaledInstance(300,200,Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(tempImage));
    }

    public void setQuestion(String question) {
        labelQuestion.setText(question);
    }

    public void setAnswers(ArrayList<String> answers) {
        labelAnswerA.setText("                          A."+answers.get(0));
        labelAnswerB.setText("                          B."+answers.get(1));
        labelAnswerC.setText("                          C."+answers.get(2));
        labelAnswerD.setText("                          D."+answers.get(3));
    }

        
	protected JLabel createLabel(String label, int fontSize, boolean centerAlignment, boolean underlined){
	
		JLabel tempLabel;
	
		if(label.equals(""))
			tempLabel = new JLabel();
		else
			tempLabel = new JLabel(label);
	
		tempLabel.setFont(new Font("SERIF", Font.PLAIN, fontSize));
		
		if(centerAlignment)
			tempLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		if(underlined) {
			Font font;
        	Map attributes;
		
			font = tempLabel.getFont();
			attributes = font.getAttributes();
			attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
			tempLabel.setFont(font.deriveFont(attributes));
        }
        return tempLabel;
	}
}
