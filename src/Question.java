/**
 * This class represents a question of the game. It contains the category of the question, the question itself, possible answers and
 * the correct answer.
 */

import java.util.ArrayList;
import java.util.Collections;

public class Question {

    private String category;
    private String question;
    private ArrayList<String> answers;
    private String correctAnswer;

    public Question (String category, String question, ArrayList<String> answers,String correctAnswer) {
        this.category=category;
        this.question=question;
        this.answers=answers;
        this.correctAnswer=correctAnswer;

        Collections.shuffle(answers);
    }

    public String getQuestion() { return question; }

    public ArrayList<String> getAnswers() { return answers; }

    public String getCategory() { return category; }

    public boolean isPlayersAnswerRight(int indexOfPlayersAnswer) {
        return answers.get(indexOfPlayersAnswer).equals(correctAnswer);
    }
}
