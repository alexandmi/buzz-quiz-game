/**
 * This class represents the interface of the type of round Correct Answer.
 */

import java.util.ArrayList;

public class UserInterfaceCorrectAnswer extends UserInterfaceRounds {

    public UserInterfaceCorrectAnswer() {}

    /**
     * This method prints the header of the round and its rules.
     * @param indexOfRound the index of the current round.
     */
    public void printTitle(int indexOfRound) {
        
        System.out.println("\n\n-------");
        System.out.println("ROUND "+(indexOfRound+1));
        System.out.println("-------");
        System.out.println("\nType of round : CORRECT ANSWER");
        System.out.println("\nIn this type of round, you are given a question with 4 possible answers," +
                "and if you pick the correct one,you get 1000 points.");
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
        
        System.out.println("\nQUESTION "+(indexOfQuestion+1));
        System.out.println("``````````");
        System.out.println("Category: "+category);
        return printAnswers(question, answers);
    }

    /**
     * This method prints the result of the players answer to the question.
     * @param isCorrect is the boolean of whether the answer was correct or not.
     * @param playersName is the username of the player.
     * @param addingPoints are the number of points that are added to the player.
     * @param currentPoints are the points of the player after the addition of the addingPoints.
     */
    public void printResult(boolean isCorrect, String playersName, int addingPoints, int currentPoints) {
        if(isCorrect)
            System.out.println("\nCongratulations "+playersName+", your answer is correct! You get "+addingPoints+" points! You currently have " +
                    currentPoints+" points.");
        else
            System.out.println("\nUnfortunately "+playersName+", your answer is wrong. You get 0 points. You currently have " +
                    currentPoints+" points.");
    }
}
