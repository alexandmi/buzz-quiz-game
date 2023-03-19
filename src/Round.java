/**
 * This class represents a round of the game. It contains an ArrayList its questions.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Round {

    private int numberOfQuestions;
    private ArrayList<Integer> unavailableQuestions;
    private ArrayList<Integer> chosenQuestions;
    private ArrayList<Question> questions;

    /**
     * The constructor calls the methods for the creation of the Question objects.
     * @param unavailableQuestions is the ArrayList containing all the question that have already been taken from other rounds.
     * @param numberOfQuestions is the number of question each round has.
     * @throws IOException  for the BufferedReader that is used.
     */
    public Round(ArrayList<Integer> unavailableQuestions, int numberOfQuestions) throws IOException {
        
        this.unavailableQuestions=unavailableQuestions;
        this.numberOfQuestions=numberOfQuestions;
        choseQuestions();
        fillQuestions();
    }


    /**
     * This method chooses randomly the indexes of the questions contained in the 'questions.txt' file. It utilizes the
     * unavailableQuestions ArrayList to not pick the same questions that the previous rounds picked.
     */
    private void choseQuestions() {
        
        chosenQuestions = new ArrayList<>();
        Random r = new Random();
        int currentRandomQuestion;
        
        for(int i=0;i<numberOfQuestions;i++){
            
            do{
                currentRandomQuestion=r.nextInt(60);
                currentRandomQuestion+=1;
            }
            while(unavailableQuestions.contains(currentRandomQuestion));

            unavailableQuestions.add(currentRandomQuestion);
            chosenQuestions.add(currentRandomQuestion);
        }
        Collections.sort(chosenQuestions);
    }

    /**
     * This method takes the indexes of the chosen questions from the chosenQuestions ArrayList and it goes to the 'questions.txt' file
     * and it reads them from there.
     * @throws IOException for the BufferedReader that is used.
     */
    private void fillQuestions() throws IOException{
        
        questions = new ArrayList<>();
        String category;
        String question;
        ArrayList<String> answers;
        String correctAnswer;
        Question tempQuestion;

        try (BufferedReader in = new BufferedReader(new FileReader("questions.txt"))) {
            
            int currentQuestion;
            int previousQuestion=0;

            for(int i = 0; i < numberOfQuestions; i++) {
           
                currentQuestion = chosenQuestions.get(i);
                for (int j = 0; j < (currentQuestion-previousQuestion-1) * 8; j++)
                    in.readLine();
                
                category = in.readLine();
                question = in.readLine();
                answers = new ArrayList<>();
                for (int j = 0; j < 4; j++)
                    answers.add(in.readLine());
                correctAnswer = in.readLine();

                tempQuestion = new Question(category,question,answers,correctAnswer);
                questions.add(tempQuestion);

                in.readLine();
                previousQuestion = currentQuestion;
            }
        }
        Collections.shuffle(questions);
    }


    /**
     * This method checks if the players answer is correct.
     * @param indexOfQuestion the index of the current question in this round.
     * @param playersAnswer the answer of the player.
     * @return whether the answer is correct or not.
     */
    public boolean isPlayersAnswerRight(int indexOfQuestion,String playersAnswer) {
        
        switch (playersAnswer) {
            case "A":  return questions.get(indexOfQuestion).isPlayersAnswerRight(0);
            case "a":  return questions.get(indexOfQuestion).isPlayersAnswerRight(0);
            case "B":  return questions.get(indexOfQuestion).isPlayersAnswerRight(1);
            case "b":  return questions.get(indexOfQuestion).isPlayersAnswerRight(1);
            case "C":  return questions.get(indexOfQuestion).isPlayersAnswerRight(2);
            case "c":  return questions.get(indexOfQuestion).isPlayersAnswerRight(2);
            default :  return questions.get(indexOfQuestion).isPlayersAnswerRight(3);
        }
    }

    public String getCategory(int indexOfQuestion)  { return questions.get(indexOfQuestion).getCategory();  }
    
    public String getQuestion(int indexOfQuestion)  { return questions.get(indexOfQuestion).getQuestion();  }
    
    public ArrayList<String> getAnswers(int indexOfQuestion) { return questions.get(indexOfQuestion).getAnswers(); }
}
