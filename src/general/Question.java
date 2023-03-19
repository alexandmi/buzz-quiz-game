/**
 * This class represents a question of the game. It contains the category of the question, the question itself, possible answers and
 * the correct answer.
 */

package general;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

class Question {

    private String category;
    private String question;
    private ArrayList<String> answers;
    private String correctAnswer;
    private Image image;
    private String letterOfCorrectAnswer;

    Question (String category, String question, ArrayList<String> answers,String correctAnswer) {
        this.category=category;
        this.question=question;
        this.answers=answers;
        this.correctAnswer=correctAnswer;

        Collections.shuffle(answers);
        findLetterOfCorrectAnswer();
    }

    String getQuestion() { return question; }

    ArrayList<String> getAnswers() { return answers; }

    String getCategory() { return category; }

    String getCorrectAnswer() {
        return letterOfCorrectAnswer;
    }

    Image getImage() { return image; }

    boolean hasImage() { return !(image==null); }

    void setImage(Image image) {
        this.image = image;
    }

    private void findLetterOfCorrectAnswer() {
        if(answers.get(0).equals(correctAnswer))
            letterOfCorrectAnswer = "A";
        else if(answers.get(1).equals(correctAnswer))
            letterOfCorrectAnswer = "B";
        else if(answers.get(2).equals(correctAnswer))
            letterOfCorrectAnswer = "C";
        else letterOfCorrectAnswer = "D";
    }
}
