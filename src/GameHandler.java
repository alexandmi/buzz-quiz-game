/**
 * This class controls the flow of the game. It contains the rounds of the game and the user interfaces.
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GameHandler {
    
    private GeneralUserInterface generalInterface;
    private Player player;
    private ArrayList<Round> rounds;
    private int numberOfRounds;
    private int numberOfQuestions;

    /**
     * The constructor calls the GeneralUserInterface and creates the Round objects.
     * @throws IOException  for the BufferedReader that is used.
     */
    public GameHandler() throws IOException {
        
        rounds = new ArrayList<>();
        generalInterface = new GeneralUserInterface();

        numberOfQuestions = generalInterface.getNumberOfQuestions();
        numberOfRounds = generalInterface.getNumberOfRounds();
        player = new Player(generalInterface.getNameOfPlayer());
        createRounds();
    }


    /**
     * This method creates the Round objects.
     * @throws IOException  for the BufferedReader that is used.
     */
    private void createRounds() throws IOException {
        
        ArrayList<Integer> unavailableQuestions = new ArrayList<>();
        Random r;
        int typeOfRound;

        for(int i=0;i<generalInterface.getNumberOfRounds();i++) {
            
            r = new Random();
            typeOfRound = r.nextInt(2);
            if(typeOfRound == 0) {
                Round tempRound = new RoundCorrectAnswer(unavailableQuestions, numberOfQuestions);
                rounds.add(tempRound);
            }
            else if(typeOfRound == 1) {
                Round tempRound = new RoundBetting(unavailableQuestions, numberOfQuestions);
                rounds.add(tempRound);
            }
        }
    }


    /**
     * In this method the main body of the game is being run. It uses 2 for loops, one for the rounds and one for the
     * questions of each round.
     */
    public void start() {
        
        for(int i=0; i<numberOfRounds; i++) {
            String playersAnswer;
            
            if(rounds.get(i) instanceof RoundCorrectAnswer) {
                
                UserInterfaceCorrectAnswer userInterface;
                userInterface = new UserInterfaceCorrectAnswer();
                userInterface.printTitle(i);

                for (int j = 0; j < numberOfQuestions; j++) {
                    
                    String category = rounds.get(i).getCategory(j);
                    String question = rounds.get(i).getQuestion(j);
                    ArrayList<String> answers = rounds.get(i).getAnswers(j);

                    playersAnswer = userInterface.printQuestion(j, category, question, answers);
                    boolean isCorrect = rounds.get(i).isPlayersAnswerRight(j, playersAnswer);
                    if (isCorrect)
                        player.addPoints(1000);
                    userInterface.printResult(isCorrect, player.getName(), 1000, player.getPoints());
                }
            }
            else {
                
                UserInterfaceBetting userInterface;
                userInterface = new UserInterfaceBetting();
                userInterface.printTitle(i);

                for (int j = 0; j < numberOfQuestions; j++) {
                    
                    String category = rounds.get(i).getCategory(j);
                    String question = rounds.get(i).getQuestion(j);
                    ArrayList<String> answers = rounds.get(i).getAnswers(j);

                    int bettingPoints = userInterface.printBet(j, category);
                    playersAnswer = userInterface.printQuestion(j, category, question, answers);
                    boolean isCorrect = rounds.get(i).isPlayersAnswerRight(j, playersAnswer);
                    if(isCorrect)
                        player.addPoints(bettingPoints);
                    else
                        player.addPoints(bettingPoints*-1);

                    userInterface.printResult(isCorrect, player.getName(), bettingPoints, player.getPoints());
                }
            }
        }
    }


    /**
     * This method is called when the game has ended.
     */
    public void end() {
        
        String playersName = player.getName();
        int playersPoints = player.getPoints();
        generalInterface.printGameOver(playersName,playersPoints);
    }
}
