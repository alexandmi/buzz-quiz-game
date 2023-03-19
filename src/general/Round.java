/**
 * This abstract class represents the general logic of a round. It contains in an ArrayList its questions.
 */

package general;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public abstract class Round {

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
    protected Round(ArrayList<Integer> unavailableQuestions, int numberOfQuestions) throws IOException {
        
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
        for(int i=0;i<numberOfQuestions;i++) {
            do {
                currentRandomQuestion=r.nextInt(100);
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
    private void fillQuestions() throws IOException {

        questions = new ArrayList<>();
        String category;
        String question;
        ArrayList<String> answers;
        String correctAnswer;
        Question tempQuestion;
        String imageName;
        Image image;

        try (BufferedReader in = new BufferedReader(new FileReader("questions.txt"))) {
            
            int currentQuestion;
            int previousQuestion=0;
            //System.out.println(chosenQuestions);

            for(int i = 0; i < numberOfQuestions; i++) {
                
                currentQuestion = chosenQuestions.get(i);
                if(currentQuestion<61) {
                    for (int j = 0; j < (currentQuestion - previousQuestion - 1) * 8; j++)
                        in.readLine();
                }
                else if(previousQuestion<61) {
                    for(int j = 0; j< (60-previousQuestion) * 8; j++)
                        in.readLine();
                    for(int j = 0; j< (currentQuestion-61)*9; j++)
                        in.readLine();
                }
                else {
                    for(int j = 0; j< (currentQuestion-previousQuestion-1)*9; j++)
                        in.readLine();
                }

                category = in.readLine();
                question = in.readLine();
                answers = new ArrayList<>();
                for (int j = 0; j < 4; j++)
                    answers.add(in.readLine());
                correctAnswer = in.readLine();

                tempQuestion = new Question(category,question,answers,correctAnswer);

                String tempString = in.readLine();
                if(!(tempString.equals(""))) {
                    imageName = tempString;
                    image = ImageIO.read(new File("images/"+imageName+".jpg"));
                    tempQuestion.setImage(image);
                    in.readLine();
                }
                questions.add(tempQuestion);
                previousQuestion = currentQuestion;
            }
        }
        Collections.shuffle(questions);
    }


    /**
     * This method returns if the letter of the correct answer of a question.
     * @param indexOfQuestion the index of the current question in this round.
     * @return whether the answer is correct or not.
     */
    protected String getCorrectAnswer(int indexOfQuestion) {
        return questions.get(indexOfQuestion).getCorrectAnswer();
    }

    protected String getCategory(int indexOfQuestion)  { return questions.get(indexOfQuestion).getCategory();  }
    
    protected String getQuestion(int indexOfQuestion)  { return questions.get(indexOfQuestion).getQuestion();  }
    
    protected ArrayList<String> getAnswers(int indexOfQuestion) { return questions.get(indexOfQuestion).getAnswers(); }
    
    protected Image getImage(int indexOfQuestion) { return questions.get(indexOfQuestion).getImage(); }
    
    protected boolean hasImage(int indexOfQuestion) { return questions.get(indexOfQuestion).hasImage(); }
}
