/**
 * This class is responsible for the user interface at the start and the at end of the game. The interface of the initialization
 * of the game takes part here.
 */

import java.util.Scanner;

public class GeneralUserInterface {
    
    private int numberOfRounds;
    private int numberOfQuestions;
    private String nameOfPlayer;
    private Scanner systemScanner;
    private Scanner inputScanner;

    /**
     * In the constructor we have the intro of the game and the initialization of the users preferences and name.
     */
    public GeneralUserInterface(){

        intro();
        initializeNumberOfRounds();
        initializeNumberOfQuestions();
        initializeNameOfPlayer();
    }

    /**
     * This method prints the intro of the game and its basic rules.
     */
    private void intro() {
        
        System.out.println("\n - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
        System.out.println("| * * * * * * * * * * * * * * * * * * * * * * * * * * *  |");
        System.out.println("| *  - - - - - - - - - - - - - - - - - - - - - - - -   * |");
        System.out.println("| * | IT'S TIME FOR QUESTIONS, IT'S TIME FOR BUZZ!!! | * |");
        System.out.println("| *  - - - - - - - - - - - - - - - - - - - - - - - -   * |");
        System.out.println("| * * * * * * * * * * * * * * * * * * * * * * * * * * *  |");
        System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - -");

        System.out.println("\nWelcome to the Buzz Quiz! Buzz is a trivia game where fun and learning go together!");
        System.out.println("The rules of the game are pretty simple:");
        System.out.println("\n>The game consists of a number of rounds, and each round consists of a number of questions.");
        System.out.println(">There are different types of rounds with different rules, but don't worry, all the rules will " +
                "be explained each time a round starts.");
        System.out.println(">Also, there are four categories of questions : History, Geography, Sports and Art.");
        System.out.println(">Throughout the game you have a number of points. The more questions you answer right, the more" +
                " points you'll gain! Simple,right?");
    }


    /**
     * This method asks the user for the number of rounds he wants. He has to commit an integer between 2 and 4, and the method
     * checks if the input is correct.
     */
    private void initializeNumberOfRounds() {
       
        System.out.println("\nNow, you can chose the number of rounds you want your game to have.You can chose from 2 to 4 rounds.");
        System.out.println("Please insert the number of rounds you want: ");
        numberOfRounds = 0;
        boolean isInputFalse = true;

        do {
            systemScanner = new Scanner(System.in);
            inputScanner = new Scanner(systemScanner.nextLine());

            if (inputScanner.hasNextInt()) {
                
                numberOfRounds = inputScanner.nextInt();
                if (numberOfRounds >= 2 && numberOfRounds <= 4 && !inputScanner.hasNext())
                    isInputFalse = false;
                else if(inputScanner.hasNext())
                    System.out.println("You did not commit a number.Please give a number between 2 and 4: ");
                else
                    System.out.println("Sorry, the number of rounds must be between 2 and 4. Please give an appropriate number: ");
            }
            else
                System.out.println("You did not commit a number.Please give a number between 2 and 4: ");
        }
        while (isInputFalse);
    }


    /**
     * This method asks the user for the number of questions he wants. He has to commit an integer between 2 and 4, and the method
     * checks if the input is correct.
     */
    private void initializeNumberOfQuestions() {
    
        System.out.println("\nExcellent! Now you can also chose the number of the questions you want per round.It can be from 2 to 4 questions.");
        System.out.println("Please insert the number of questions you want: ");
        numberOfQuestions=0;
        boolean isInputFalse = true;

        do {
            systemScanner = new Scanner(System.in);
            inputScanner = new Scanner(systemScanner.nextLine());

            if (inputScanner.hasNextInt()) {
                
                numberOfQuestions = inputScanner.nextInt();
                if (!inputScanner.hasNext() && numberOfQuestions >= 2 && numberOfQuestions <= 4)
                    isInputFalse = false;
                else if(inputScanner.hasNext())
                    System.out.println("You did not commit a number.Please give a number between 2 and 4: ");
                else
                    System.out.println("Sorry, the number of questions must be between 2 and 4. Please give an appropriate number: ");
            }
            else
                System.out.println("You did not commit a number.Please give a number between 2 and 4: ");
        }
        while (isInputFalse);
    }


    /**
     * This method initializes the name of the player. It has to be 3 to 10 characters long. The method checks if the input is correct.
     */
    private void initializeNameOfPlayer() {
        
        System.out.println("\nGood!Now type in your username. It can be 3 to 10 characters long: ");
        boolean isInputFalse = true;

        do {
            systemScanner = new Scanner(System.in);
            nameOfPlayer = systemScanner.nextLine().trim();

            if(nameOfPlayer.length() > 2 && nameOfPlayer.length() < 11)
                isInputFalse = false;
            else
                System.out.println("Your username must be 3 to 10 characters long. Please insert another username: ");
        }
        while (isInputFalse);
        System.out.println("\nGood job "+nameOfPlayer+"! You are now ready to start the game!");
    }


    /**
     * This method prints the final message of the game, after its completion.
     * @param playersName is the players username.
     * @param playersPoints is the players total points.
     */
    public void printGameOver(String playersName,int playersPoints) {
        
        System.out.println("\n<<< GAME OVER >>>");
        System.out.println("Well done "+playersName+"! You finished the game. Your total number of points is " +
                playersPoints+".");
        System.out.println("Thank you for playing Buzz!");
    }


    public Integer getNumberOfRounds() { return numberOfRounds; }
    
    public Integer getNumberOfQuestions()  { return numberOfQuestions; }
    
    public String getNameOfPlayer() { return nameOfPlayer;}
}
