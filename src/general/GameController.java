/**
 * This class is the main class of the game.It merges the logic and the graphics of the game.
 */

package general;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.util.Map;


public class GameController {
    
    private GameLogic game;
    private HighScores scores;
    private GraphicsHighScores graphScores;

    private JPanel generalPanel;
    private CardLayout cardLayout;

    private JPanel introPanel;

    private JPanel customizationPanel;
    private JTextField textFieldNumberOfRounds;
    private JTextField textFieldNumberOfQuestions;
    private JSpinner spinnerNumberOfPlayers;
    private JTextField textFieldPlayer1;
    private JTextField textFieldPlayer2;
    private JPanel panelPlayer2;

    private JPanel endGamePanel;

    private GraphicsRounds randomRound;
    private int numberOfRounds;
    private int numberOfQuestions;
    private int numberOfPlayers;
    private String userName1;
    private String userName2;
    private int currentRound = -1;
    private int currentQuestion = -1;

    private boolean player1Answered = false;
    private boolean player2Answered = false;
    private boolean isPlayer1Correct = false;
    private boolean isPlayer2Correct = false;

    private String correctAnswer;
    private JButton nextButton;
    private Timer timer;
    private int countTime;
    private int hasAnswered5QuestionsFirst = -1;

    public GameController() throws IOException, ClassNotFoundException {
        
        JFrame generalFrame = new JFrame();
        cardLayout = new CardLayout();
        generalPanel = new JPanel(cardLayout);

        createIntroPanel();
        generalPanel.add(introPanel,"INTRO");

        createCustomizationPanel();
        generalPanel.add(customizationPanel,"CUSTOM");

        scores = new HighScores();
        graphScores = new GraphicsHighScores();
        graphScores.getScoreOneMap(scores.getHighScoresOnePlayer());
        graphScores.getScoreTwoMap(scores.getHighScoresTwoPlayers());
        graphScores.getGoBackButton().addActionListener(e -> cardLayout.show(generalPanel, "INTRO"));
        generalPanel.add(graphScores,"SCORES");

        generalFrame.add(generalPanel);

        generalFrame.setTitle("BUZZ GAME");
        generalFrame.setSize(780, 720);
        generalFrame.setVisible(true);
        generalFrame.setLocation(300,5);
        generalFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * This method checks if player's inputs are valid.If not,it creates a pop up message to inform the player.
     * @return whether the inputs are correct or not.
     */
    private boolean checkUserInput() {
        
        String tempRounds = textFieldNumberOfRounds.getText();
        String tempQuest = textFieldNumberOfQuestions.getText();
        String tempUsername1 = textFieldPlayer1.getText().trim();
        double tempNumberPlayers = (Double)spinnerNumberOfPlayers.getValue();

        if(tempRounds==null || !(tempRounds.equals("3") || tempRounds.equals("4") || tempRounds.equals("5") || tempRounds.equals("6"))) {
            PopUpMessage.infoBox("You did not commit a valid number of rounds.Please commit a number between" +
                    " 3 and 6.", "Invalid number of rounds");
            return false;
        }
        else if(tempQuest==null || !(tempQuest.equals("3") || tempQuest.equals("4") || tempQuest.equals("5"))) {
            PopUpMessage.infoBox("You did not commit a valid number of questions.Please commit a number between" +
                    " 3 and 5.", "Invalid number of questions");
            return false;
        }
        else if(tempUsername1.length()<3 || tempUsername1.length()>13) {
            PopUpMessage.infoBox("Your username must be 3 to 13 characters long","Invalid username");
            return false;
        }
        else if(tempNumberPlayers==2) {
            String tempUsername2 = textFieldPlayer2.getText().trim();
            if(tempUsername2.length()<3 || tempUsername2.length()>13) {
                PopUpMessage.infoBox("Your username must be 3 to 13 characters long","Invalid username");
                return false;
            }
            userName2 = tempUsername2;
        }

        numberOfRounds = Integer.parseInt(tempRounds);
        numberOfQuestions = Integer.parseInt(tempQuest);
        userName1 = tempUsername1;
        numberOfPlayers = (int)tempNumberPlayers;
        return true;
    }


    /**
     * This method creates a new graphic round.It also includes the Listeners for the S and K keys,as well as for the Next
     * button.If the rounds are over,we go to the endgame panel.At the end it calls the nextQuestion method.
     */
    private void nextRound() {
        
        currentRound++;
        currentQuestion = -1;
        if(currentRound >= numberOfRounds) {
            createEndgamePanel();
            generalPanel.add(endGamePanel,"ENDGAME");
            cardLayout.show(generalPanel,"ENDGAME");
            return;
        }

        if(game.getRound(currentRound) instanceof RoundBetting)
            randomRound = new GraphicsBetting(currentRound,numberOfPlayers,userName1,userName2);
        else if(game.getRound(currentRound) instanceof RoundCorrectAnswer)
            randomRound = new GraphicsCorrectAnswer(currentRound,numberOfPlayers,userName1,userName2);
        else if(game.getRound(currentRound) instanceof RoundTimer) {
            randomRound = new GraphicsTimer(currentRound,numberOfPlayers,userName1,userName2);
            timer = new Timer(100, e -> {
                if (countTime <= 0)
                    ((Timer) e.getSource()).stop();
                else
                    countTime -= 100;
                ((GraphicsTimer) randomRound).setTimerLabel(String.valueOf(countTime/1000.0));

            });
        }
        else if(game.getRound(currentRound) instanceof RoundThermometer)
            randomRound = new GraphicsThermometer(currentRound,numberOfPlayers,userName1,userName2);
        else if(game.getRound(currentRound) instanceof RoundFastAnswer)
            randomRound =  new GraphicsFastAnswer(currentRound,numberOfPlayers,userName1,userName2);

        String S_KEY = "S PRESSED";
        Action SKey;
        SKey = new AbstractAction(S_KEY) {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!player1Answered) {
                    
                    String answer = randomRound.getPlayer1Answer().getText();
                    if (answer.equals("-"))
                        return;
                    randomRound.changeLockStatus(0, true);
                    player1Answered = true;
                    isPlayer1Correct = answer.equals(correctAnswer);
                    if(randomRound instanceof GraphicsTimer) {
                        ((GraphicsTimer) randomRound).setPlayerTime(0,String.valueOf(countTime/1000.0));
                        ((RoundTimer)game.getRound(currentRound)).setTimeLeft(0,countTime);
                    }
                    else if(randomRound instanceof GraphicsThermometer && isPlayer1Correct && hasAnswered5QuestionsFirst == -1) {
                        int correctAnswers = ((RoundThermometer)game.getRound(currentRound)).getCorrectAnswersOfPlayer(0);
                        if(correctAnswers+1==5)
                            hasAnswered5QuestionsFirst = 0;
                    }
                    else if(randomRound instanceof GraphicsFastAnswer) {
                        int answeredFirst = ((RoundFastAnswer)game.getRound(currentRound)).getWhoAnsweredFirst();
                        if(answeredFirst == -1 && isPlayer1Correct)
                            ((RoundFastAnswer)game.getRound(currentRound)).setWhoAnsweredFirst(0);
                    }
                    playersAnswered();
                }
            }
        };

        randomRound.getInputMap(2).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), S_KEY);
        randomRound.getActionMap().put(S_KEY, SKey);

        String K_KEY = "K PRESSED";
        Action KKey;
        KKey = new AbstractAction(K_KEY) {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if(!player2Answered) { 
                    String answer = randomRound.getPlayer2Answer().getText();
                    if (answer.equals("-"))
                        return;
                    randomRound.changeLockStatus(1, true);
                    player2Answered = true;
                    isPlayer2Correct = answer.equals(correctAnswer);
                    if(randomRound instanceof GraphicsTimer) {
                        ((GraphicsTimer) randomRound).setPlayerTime(1,String.valueOf(countTime/1000.0));
                        ((RoundTimer)game.getRound(currentRound)).setTimeLeft(1,countTime);
                    }
                    else if(randomRound instanceof GraphicsThermometer && isPlayer2Correct && hasAnswered5QuestionsFirst == -1) {
                        int correctAnswers = ((RoundThermometer)game.getRound(currentRound)).getCorrectAnswersOfPlayer(1);
                        if(correctAnswers+1==5)
                            hasAnswered5QuestionsFirst = 1;
                    }
                    else if(randomRound instanceof GraphicsFastAnswer) {
                        int answeredFirst = ((RoundFastAnswer)game.getRound(currentRound)).getWhoAnsweredFirst();
                        if(answeredFirst == -1 && isPlayer2Correct)
                            ((RoundFastAnswer)game.getRound(currentRound)).setWhoAnsweredFirst(1);
                    }
                    playersAnswered();
                }
            }
        };
        randomRound.getInputMap(2).put(KeyStroke.getKeyStroke(KeyEvent.VK_K, 0), K_KEY);
        randomRound.getActionMap().put(K_KEY, KKey);

        nextButton = randomRound.getNextButton();
        nextButton.addActionListener(e -> nextQuestion());

        nextQuestion();

        generalPanel.add(randomRound,"ROUND");
        cardLayout.show(generalPanel,"ROUND");
    }

    /**
     * This method is called when the answers of the player or players are closed,and we are ready to see if they answered
     * correctly or not.It also controls the logic of the game to set these points to the players.
     */
    private void playersAnswered() {
      
        String result1 = "gains";
        String result2 = "gains";
        int player1Points = 0;
        int player2Points = 0;

        if((numberOfPlayers==1 && player1Answered) || (player1Answered && player2Answered)) {
          
            if(!(randomRound instanceof GraphicsThermometer))
                game.setPlayersPoints(0,isPlayer1Correct,currentRound);
            if(randomRound instanceof GraphicsBetting) {
                if(!isPlayer1Correct)
                    result1 = "loses";
                player1Points = (int)((RoundBetting)game.getRound(currentRound)).getBettingPoints(0);
            }
            else if(randomRound instanceof GraphicsCorrectAnswer && isPlayer1Correct)
                player1Points = 1000;
            else if(randomRound instanceof GraphicsTimer) {
                if(isPlayer1Correct) {
                    double tempPoints =  ((RoundTimer)game.getRound(currentRound)).getTimeLeft(0)*0.2;
                    player1Points = (int)tempPoints;
                }
                timer.stop();
            }

            if(numberOfPlayers==2) {
               
                if(!(randomRound instanceof GraphicsThermometer))
                    game.setPlayersPoints(1,isPlayer2Correct,currentRound);
                if(randomRound instanceof GraphicsBetting) {
                    if(!isPlayer2Correct)
                        result2 = "loses";
                    player2Points = (int)((RoundBetting)game.getRound(currentRound)).getBettingPoints(1);
                }
                else if(randomRound instanceof GraphicsCorrectAnswer && isPlayer2Correct)
                    player2Points = 1000;
                else if(randomRound instanceof GraphicsTimer) {
                    if(isPlayer2Correct) {
                        double tempPoints =  ((RoundTimer)game.getRound(currentRound)).getTimeLeft(1)*0.2;
                        player2Points = (int)tempPoints;
                    }
                }
                else if(randomRound instanceof GraphicsThermometer) {
                    int numberOfCorrectAnswers;
                    if(isPlayer1Correct) {
                        ((RoundThermometer)game.getRound(currentRound)).addCorrect(0);
                        numberOfCorrectAnswers = ((RoundThermometer)game.getRound(currentRound)).getCorrectAnswersOfPlayer(0);
                        ((GraphicsThermometer)randomRound).setCorrectAnswersLabel(0,numberOfCorrectAnswers);
                    }
                    if(isPlayer2Correct) {
                        ((RoundThermometer)game.getRound(currentRound)).addCorrect(1);
                        numberOfCorrectAnswers = ((RoundThermometer)game.getRound(currentRound)).getCorrectAnswersOfPlayer(1);
                        ((GraphicsThermometer)randomRound).setCorrectAnswersLabel(1,numberOfCorrectAnswers);
                    }
                    if(hasAnswered5QuestionsFirst != -1) {
                        if(hasAnswered5QuestionsFirst == 0) {
                            ((GraphicsThermometer)randomRound).setTheWinner(userName1);
                            game.setPlayersPoints(0,isPlayer1Correct,currentRound);
                        }
                        else {
                            ((GraphicsThermometer)randomRound).setTheWinner(userName2);
                            game.setPlayersPoints(1,isPlayer1Correct,currentRound);
                        }
                        nextButton.setEnabled(true);
                        currentQuestion = 60;
                        hasAnswered5QuestionsFirst = -1;
                        randomRound.setResults(correctAnswer, result1, result2, player1Points, player2Points);
                        return;
                    }
                }
                else if(randomRound instanceof GraphicsFastAnswer) {
                    if(isPlayer1Correct && isPlayer2Correct) {
                        if(((RoundFastAnswer)game.getRound(currentRound)).getWhoAnsweredFirst() == 0) {
                            ((GraphicsFastAnswer)randomRound).setWhoAnsweredFaster(userName1,userName2);
                            player1Points =1000;
                            player2Points =500;
                        }
                        else {
                            ((GraphicsFastAnswer)randomRound).setWhoAnsweredFaster(userName2,userName1);
                            player1Points =500;
                            player2Points =1000;
                        }
                    }
                    else if(isPlayer1Correct)
                        player1Points = 1000;
                    else if(isPlayer2Correct)
                        player2Points = 1000;
                }
            }
            nextButton.setEnabled(true);
            randomRound.setResults(correctAnswer, result1, result2, player1Points, player2Points);
        }
    }

    /**
     * This method depending on round type it creates the GUI of the next and next question.
     */
    private void nextQuestion() {
        
        currentQuestion++;
        if(randomRound instanceof GraphicsThermometer) {
            if(currentQuestion>=60) {
                nextRound();
                return;
            }
        }
        else if(currentQuestion>=numberOfQuestions) {
            nextRound();
            return;
        }

        randomRound.setNumberOfQuestion(currentQuestion+1);
        randomRound.setPoints(game.getPlayerPoints(0),0);
        if(numberOfPlayers==2)
            randomRound.setPoints(game.getPlayerPoints(1),1);
        randomRound.resetResultsLockStatusAnswers();
        player1Answered = false;
        player2Answered = false;
        nextButton.setEnabled(false);
        correctAnswer = game.getRound(currentRound).getCorrectAnswer(currentQuestion);

        if(randomRound instanceof GraphicsBetting) {
            randomRound.changeLockStatus(0,true);
            randomRound.changeLockStatus(1,true);
            ((GraphicsBetting) randomRound).unlockBets(true);
            ((GraphicsBetting) randomRound).setCategoryBet(game.getRound(currentRound).getCategory(currentQuestion));
            ((GraphicsBetting) randomRound).getRevealQuestionButton().addActionListener(e ->  {
                double tempBetPoints;
                tempBetPoints = ((GraphicsBetting) randomRound).getBettingPoints(0);
                ((RoundBetting)game.getRound(currentRound)).setBettingPoints(0,tempBetPoints);
                if(numberOfPlayers==2)
                {
                    tempBetPoints = ((GraphicsBetting) randomRound).getBettingPoints(1);
                    ((RoundBetting)game.getRound(currentRound)).setBettingPoints(1,tempBetPoints);
                }
                ((GraphicsBetting) randomRound).unlockBets(false);
                randomRound.changeLockStatus(0,false);
                randomRound.changeLockStatus(1,false);
                nextQuestion2();
            });
        }
        else if(randomRound instanceof GraphicsTimer) {
            randomRound.changeLockStatus(0,true);
            randomRound.changeLockStatus(1,true);
            ((GraphicsTimer)randomRound).unlockTimer(false);
            ((GraphicsTimer)randomRound).getBeginButton().addActionListener(e ->  {
                ((GraphicsTimer) randomRound).setTimerLabel("5.0");
                countTime = 5000;

                timer.start();

                ((GraphicsTimer) randomRound).unlockTimer(true);
                randomRound.changeLockStatus(0,false);
                randomRound.changeLockStatus(1,false);
                nextQuestion2();
            });
        }
        else if(randomRound instanceof GraphicsFastAnswer) {
            ((GraphicsFastAnswer)randomRound).resetWhoAnsweredFaster();
            ((RoundFastAnswer)game.getRound(currentRound)).resetWhoAnsweredFirst();
            nextQuestion2();
        }
        else {
            nextQuestion2();
        }
    }

    /**
     * This method sets the next question.
     */
    private void nextQuestion2() {
       
        if(game.getRound(currentRound).hasImage(currentQuestion))
            randomRound.setImage(game.getRound(currentRound).getImage(currentQuestion));
        randomRound.setCategory(game.getRound(currentRound).getCategory(currentQuestion));
        randomRound.setQuestion(game.getRound(currentRound).getQuestion(currentQuestion));
        randomRound.setAnswers(game.getRound(currentRound).getAnswers(currentQuestion));
    }

    /**
     * This method creates the Intro Panel.
     */
    private void createIntroPanel() {
        /***** INTRO PANEL *****/

        introPanel = new JPanel();
        BoxLayout boxLayoutIntro = new BoxLayout(introPanel, BoxLayout.Y_AXIS);
        introPanel.setLayout(boxLayoutIntro);
        introPanel.setBackground(Color.orange);

        JPanel titlePanel = new JPanel(new FlowLayout());
        titlePanel.setBackground(Color.red);
        introPanel.add(titlePanel);

        JLabel titleIntro = new JLabel("BUZZ GAME");
        titleIntro.setFont(new Font("SERIF", Font.PLAIN, 22));
        titleIntro.setAlignmentX(Component.CENTER_ALIGNMENT);
        Font font;
        Map attributes;
        font = titleIntro.getFont();
        attributes = font.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        titleIntro.setFont(font.deriveFont(attributes));
        titlePanel.add(titleIntro);

        JButton playButton = new JButton("PLAY");
        playButton.setBackground(Color.RED);
        playButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playButton.addActionListener(e -> cardLayout.show(generalPanel,"CUSTOM"));
        introPanel.add(Box.createRigidArea(new Dimension(0, 150)));
        introPanel.add(playButton);

        JButton highScoresButton = new JButton("HIGH SCORES");
        highScoresButton.setBackground(Color.RED);
        highScoresButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        highScoresButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        highScoresButton.addActionListener(e -> cardLayout.show(generalPanel,"SCORES"));
        introPanel.add(Box.createRigidArea(new Dimension(0, 60)));
        introPanel.add(highScoresButton);
        introPanel.add(Box.createRigidArea(new Dimension(0, 400)));
    }

    /**
     * This method creates the panel which is responsible for each player's and game's info.
     */
    private void createCustomizationPanel() {
        /***** CUSTOMIZATION PANEL *****/

        customizationPanel = new JPanel();
        BoxLayout boxLayoutCustom = new BoxLayout(customizationPanel, BoxLayout.Y_AXIS);
        customizationPanel.setLayout(boxLayoutCustom);
        customizationPanel.setBackground(Color.orange);

        JPanel customPanel = new JPanel(new FlowLayout());
        customPanel.setBackground(Color.red);
        customizationPanel.add(customPanel);
        customizationPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        JLabel titleCustom = new JLabel("CUSTOMIZATION");
        titleCustom.setFont(new Font("SERIF", Font.PLAIN, 20));
        titleCustom.setAlignmentX(Component.CENTER_ALIGNMENT);
        Font font;
        Map attributes;
        font = titleCustom.getFont();
        attributes = font.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        titleCustom.setFont(font.deriveFont(attributes));
        customPanel.add(titleCustom);

        /***** PANEL FOR NUMBER OF ROUNDS *****/

        JPanel panelNumberOfRounds = new JPanel(new FlowLayout());
        panelNumberOfRounds.setBackground(Color.ORANGE);
        customizationPanel.add(panelNumberOfRounds);

        JLabel labelNumberOfRounds = new JLabel("Number of rounds: ");
        labelNumberOfRounds.setFont(new Font("SERIF", Font.PLAIN, 20));
        panelNumberOfRounds.add(labelNumberOfRounds);

        textFieldNumberOfRounds = new JTextField();
        Dimension dimensionForTextFields = new Dimension(40, 30);
        textFieldNumberOfRounds.setPreferredSize(dimensionForTextFields);
        panelNumberOfRounds.add(textFieldNumberOfRounds);

        /***** PANEL FOR NUMBER OF QUESTIONS *****/

        JPanel panelNumberOfQuestions = new JPanel(new FlowLayout());
        panelNumberOfQuestions.setBackground(Color.ORANGE);
        customizationPanel.add(panelNumberOfQuestions);

        JLabel labelNumberOfQuestions = new JLabel("Number of questions: ");
        labelNumberOfQuestions.setFont(new Font("SERIF", Font.PLAIN, 20));
        panelNumberOfQuestions.add(labelNumberOfQuestions);

        textFieldNumberOfQuestions = new JTextField();
        textFieldNumberOfQuestions.setPreferredSize(dimensionForTextFields);
        panelNumberOfQuestions.add(textFieldNumberOfQuestions);

        /***** PANEL FOR NUMBER OF PLAYERS *****/

        JPanel panelNumberOfPlayers = new JPanel(new FlowLayout());
        panelNumberOfPlayers.setBackground(Color.ORANGE);
        panelNumberOfPlayers.add(Box.createRigidArea(new Dimension(0, 10)));
        customizationPanel.add(panelNumberOfPlayers);

        JLabel labelNumberOfPlayers = new JLabel("Number of players: ");
        labelNumberOfPlayers.setFont(new Font("SERIF", Font.PLAIN, 20));
        panelNumberOfPlayers.add(labelNumberOfPlayers);

        SpinnerNumberModel bounds = new SpinnerNumberModel(1.0, 1.0, 2.0, 1.0);
        spinnerNumberOfPlayers = new JSpinner(bounds);
        spinnerNumberOfPlayers.setEditor(new JSpinner.DefaultEditor(spinnerNumberOfPlayers));
        Dimension dimensionForSpinner = new Dimension(40, 25);
        spinnerNumberOfPlayers.setPreferredSize(dimensionForSpinner);

        JPanel panelPlayer1 = new JPanel();
        panelPlayer2 = new JPanel();
        spinnerNumberOfPlayers.addChangeListener(e -> {
            double num = (Double) spinnerNumberOfPlayers.getValue();
            if(num==1.0)
                panelPlayer2.setVisible(false);
            else panelPlayer2.setVisible(true);
        });
        panelNumberOfPlayers.add(spinnerNumberOfPlayers);

        /***** PANEL FOR PLAYERS USERNAME *****/

        JPanel panelPlayersUsername = new JPanel(new FlowLayout());
        panelPlayersUsername.setBackground(Color.ORANGE);
        customizationPanel.add(panelPlayersUsername);

        BoxLayout boxLayoutPlayer1 = new BoxLayout(panelPlayer1,BoxLayout.Y_AXIS);
        panelPlayer1.setLayout(boxLayoutPlayer1);
        panelPlayer1.setBackground(Color.ORANGE);
        panelPlayersUsername.add(panelPlayer1);

        BoxLayout boxLayoutPlayer2 = new BoxLayout(panelPlayer2,BoxLayout.Y_AXIS);
        panelPlayer2.setLayout(boxLayoutPlayer2);
        panelPlayer2.setBackground(Color.ORANGE);
        panelPlayer2.setVisible(false);
        panelPlayersUsername.add(panelPlayer2);

        JLabel labelPlayer1 = new JLabel("Player username");
        labelPlayer1.setFont(new Font("SERIF", Font.PLAIN, 20));
        font = labelPlayer1.getFont();
        attributes = font.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        labelPlayer1.setFont(font.deriveFont(attributes));
        panelPlayer1.add(labelPlayer1);

        textFieldPlayer1 = new JTextField();
        Dimension dimensionForUsername = new Dimension(60, 30);
        textFieldPlayer1.setPreferredSize(dimensionForUsername);
        panelPlayer1.add(Box.createRigidArea(new Dimension(0, 10)));
        panelPlayer1.add(textFieldPlayer1);

        JLabel labelPlayer2 = new JLabel("Player Username");
        labelPlayer2.setFont(new Font("SERIF", Font.PLAIN, 20));
        font = labelPlayer2.getFont();
        attributes = font.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        labelPlayer2.setFont(font.deriveFont(attributes));
        panelPlayer2.add(labelPlayer2);

        textFieldPlayer2 = new JTextField();
        textFieldPlayer2.setPreferredSize(dimensionForUsername);
        panelPlayer2.add(Box.createRigidArea(new Dimension(0, 10)));
        panelPlayer2.add(textFieldPlayer2);

        /***** START BUTTON *****/

        JButton startButton = new JButton("Start");
        startButton.setBackground(Color.RED);
        startButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.addActionListener(e -> {
            if(checkUserInput()) {
                try {
                    game = new GameLogic(numberOfRounds,numberOfQuestions,numberOfPlayers);
                    game.setPlayersNames();
                    if(numberOfPlayers==2)
                        game.setPlayersNames();
                    currentRound = -1;
                }
                catch (IOException io) {
                    System.out.println("IO Exception :"+io);
                }
                nextRound();
                resetCustomization();
            }
        });
        customizationPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        customizationPanel.add(startButton);
        customizationPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        /***** GO BACK BUTTON *****/

        JButton goBackButton = new JButton("Go Back");
        goBackButton.setBackground(Color.RED);
        goBackButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        goBackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        goBackButton.addActionListener(e ->  {
            cardLayout.show(generalPanel,"INTRO");
            resetCustomization();
        });
        customizationPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        customizationPanel.add(goBackButton);
        customizationPanel.add(Box.createRigidArea(new Dimension(0, 270)));
    }

    /**
     * This method creates the ending panel.
     */
    private void createEndgamePanel() {
        endGamePanel = new JPanel();
        BoxLayout boxLayoutEndgame = new BoxLayout(endGamePanel, BoxLayout.Y_AXIS);
        endGamePanel.setLayout(boxLayoutEndgame);
        endGamePanel.setBackground(Color.orange);
        int winnerIndex = -2;

        try {
            if (numberOfPlayers == 1) {
                winnerIndex = 0;
                scores.addScoreOnePlayer(userName1, game.getPlayerPoints(0));
            }
            else {
                if (game.getPlayerPoints(0) > game.getPlayerPoints(1)) {
                    winnerIndex = 0;
                    scores.addScoreTwoPlayers(userName1);
                }
                else if (game.getPlayerPoints(1) > game.getPlayerPoints(0)) {
                    winnerIndex = 1;
                    scores.addScoreTwoPlayers(userName2);
                }
                else {
                    winnerIndex = -1;
                    scores.addScoreTwoPlayers(userName1);
                    scores.addScoreTwoPlayers(userName2);
                }
            }
        }
        catch (IOException io) {
            System.out.println("IO Exception here :" + io);
        }

        graphScores.getScoreOneMap(scores.getHighScoresOnePlayer());
        graphScores.getScoreTwoMap(scores.getHighScoresTwoPlayers());

        /***** GAME OVER PANEL *****/

        JPanel gameOverPanel = new JPanel(new FlowLayout());
        gameOverPanel.setBackground(Color.red);
        endGamePanel.add(gameOverPanel);
        endGamePanel.add(Box.createRigidArea(new Dimension(0, 100)));

        JLabel gameOverLabel = new JLabel("GAME OVER");
        gameOverLabel.setFont(new Font("SERIF", Font.PLAIN, 22));
        gameOverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        Font font;
        Map attributes;
        font = gameOverLabel.getFont();
        attributes = font.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        gameOverLabel.setFont(font.deriveFont(attributes));
        gameOverPanel.add(gameOverLabel);

        /***** CONGRATULATIONS LABEL *****/

        JLabel congratulationsLabel = new JLabel();
        congratulationsLabel.setFont(new Font("SERIF", Font.PLAIN, 20));
        congratulationsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        if(winnerIndex == 0)
            congratulationsLabel.setText("Congratulations "+userName1+"!");
        else if(winnerIndex == 1)
            congratulationsLabel.setText("Congratulations "+userName2+"!");
        else
            congratulationsLabel.setText("Congratulations to both of you!");
        endGamePanel.add(congratulationsLabel);
        endGamePanel.add(Box.createRigidArea(new Dimension(0, 30)));

        /***** RESULTS PANEL *****/

        JPanel resultsPanel = new JPanel();
        BoxLayout boxLayoutResults = new BoxLayout(resultsPanel, BoxLayout.Y_AXIS);
        resultsPanel.setLayout(boxLayoutResults);
        resultsPanel.setBackground(Color.orange);
        endGamePanel.add(resultsPanel);

        JLabel resultsLabel1 = new JLabel();
        resultsLabel1.setFont(new Font("SERIF", Font.PLAIN, 20));
        resultsLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultsPanel.add(resultsLabel1);

        JLabel resultsLabel2 = new JLabel();
        resultsLabel2.setFont(new Font("SERIF", Font.PLAIN, 20));
        resultsLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultsPanel.add(resultsLabel2);

        if(numberOfPlayers == 1) {
            resultsLabel1.setText("In "+numberOfRounds+" rounds and "+numberOfQuestions+" questions,");
            resultsLabel2.setText("you have gained "+game.getPlayerPoints(0)+" points!");
        }
        else {
            resultsLabel1.setText(userName1+" has gained "+game.getPlayerPoints(0)+" points in total.");
            resultsLabel2.setText(userName2+" has gained "+game.getPlayerPoints(1)+" points in total.");
        }

        /***** RETURN TO MENU BUTTON *****/

        JButton returnButton = new JButton("Return to main menu");
        returnButton.setBackground(Color.RED);
        returnButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        returnButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        returnButton.addActionListener(e -> cardLayout.show(generalPanel,"INTRO"));
        endGamePanel.add(Box.createRigidArea(new Dimension(0, 70)));
        endGamePanel.add(returnButton);
        endGamePanel.add(Box.createRigidArea(new Dimension(0, 330)));
    }

    private void resetCustomization()
    {
        textFieldNumberOfRounds.setText("");
        textFieldNumberOfQuestions.setText("");
        spinnerNumberOfPlayers.setValue(1.0);
        textFieldPlayer1.setText("");
        textFieldPlayer2.setText("");
        panelPlayer2.setVisible(false);
    }
}
