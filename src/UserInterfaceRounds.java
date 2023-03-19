/**
 * This abstract class represents the superclass of the rounds interfaces. It contains their common methods.
 */

import java.util.ArrayList;
import java.util.Scanner;

public abstract class UserInterfaceRounds {

    public UserInterfaceRounds() {}

    public abstract void printTitle(int indexOfRound);

    public abstract String printQuestion(int indexOfQuestion, String category, String question, ArrayList<String> answers);

    /**
     * This method prints the question with its answers and takes the users answer. It checks that this answer is
     * one of the a,b,c or d.
     * @param question the String of the question.
     * @param answers the ArrayList of the answers.
     * @return the players answer.
     */
    public String printAnswers(String question, ArrayList<String> answers) {
        
        String playersAnswer;
        Scanner scanner;
        boolean isInputFalse;

        System.out.println("\n"+question);
        System.out.println("\nA."+answers.get(0));
        System.out.println("B."+answers.get(1));
        System.out.println("C."+answers.get(2));
        System.out.println("D."+answers.get(3));
        System.out.println("\nEnter A,B,C or D according to which answer you think is the correct one: ");

        do {
            scanner = new Scanner(System.in);
            playersAnswer = scanner.nextLine();
            playersAnswer = playersAnswer.replaceAll("\\s+","");
            isInputFalse = true;

            if(playersAnswer.equals("A") || playersAnswer.equals("B") || playersAnswer.equals("C") || playersAnswer.equals("D") ||
                    playersAnswer.equals("a") || playersAnswer.equals("b") || playersAnswer.equals("c") || playersAnswer.equals("d"))
                isInputFalse = false;
            else
                System.out.println("You didn't commit A,B,C or D.Please commit one of these.");
        }
        while (isInputFalse);
        return playersAnswer;
    }

    public abstract void printResult(boolean isCorrect, String playersName, int addingPoints, int currentPoints);
}
