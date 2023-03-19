/**
 * This class represents the interface of the type of round Betting.
 */

import java.util.ArrayList;
import java.util.Scanner;

public class UserInterfaceBetting extends UserInterfaceRounds {

    public UserInterfaceBetting(){}

    /**
     * This method prints the header of the round and its rules.
     * @param indexOfRound the index of the current round.
     */
    public void printTitle(int indexOfRound) {
        
        System.out.println("\n\n-------");
        System.out.println("ROUND "+(indexOfRound+1));
        System.out.println("-------");
        System.out.println("\nType of round : BETTING");
        System.out.println("\nIn this type of round, you are shown initially only the category of the question you have to answer.");
        System.out.println("You bet a number of your points on whether you'll got it right or wrong.You can bet 250,500,750 and 1000 points.");
    }


    /**
     * This method prints the category of the question to the user and asks him to bet a number of points. It checks if the
     * input of the user is correct.
     * @param indexOfQuestion the index of the current question.
     * @param category the category of the question.
     * @return the players bet.
     */
    public int printBet(int indexOfQuestion,String category) {
        
        System.out.println("\nQUESTION "+(indexOfQuestion+1));
        System.out.println("``````````");
        System.out.println("The category of the question is "+category+". How many points do you want to bet?");

        Scanner systemScanner;
        Scanner inputScanner;
        int playersBet=0;
        boolean isInputFalse = true;

        do {
            systemScanner = new Scanner(System.in);

            inputScanner = new Scanner(systemScanner.nextLine());

            if (inputScanner.hasNextInt()) {
                
                playersBet = inputScanner.nextInt();

                if (!inputScanner.hasNext() && (playersBet == 250 || playersBet == 500 || playersBet == 750 || playersBet == 1000)) {
                    isInputFalse = false;
                    System.out.println("Your bet is "+playersBet+" points");
                }
                else if(inputScanner.hasNext())
                    System.out.println("You did not commit an integer.Please bet 250,500,750 or 1000 points: ");
                else
                    System.out.println("You can not bet this number. Please bet 250,500,750 or 1000 points: ");
            }
            else
                System.out.println("You did not commit an integer.Please bet 250,500,750 or 1000 points: ");
        }
        while (isInputFalse);

        return playersBet;
    }

    /**
     * This method prints the question with its answer.It utilizes the printAnswers method.
     * @param indexOfQuestion is the index of the current question.
     * @param category is the String that has category of the question.
     * @param question is the String that has the question itself.
     * @param answers is the ArrayList that contains the answers.
     * @return is the return of the printAnswers method, which is the answer of the player.
     */
    public String printQuestion(int indexOfQuestion, String category, String question, ArrayList<String> answers) {
        return printAnswers(question,answers);
    }

    /**
     * This method prints the result of the players answer to the question.
     * @param isCorrect is the boolean of whether the answer was correct or not.
     * @param playersName is the username of the player.
     * @param addingPoints are the number of points that are added to the player(his betting points).
     * @param currentPoints are the points of the player after the addition of the addingPoints.
     */
    public void printResult(boolean isCorrect, String playersName, int addingPoints, int currentPoints) {
        if(isCorrect)
            System.out.println("\nCongratulations "+playersName+", your answer is correct! You get "+addingPoints+" points! You " +
                    "currently have "+currentPoints+" points.");
        else
            System.out.println("\nUnfortunately "+playersName+", your answer is wrong. You lose "+addingPoints+". You " +
                    "currently have "+currentPoints+" points.");
    }
}
