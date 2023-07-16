/**
 * This class represents the logic of the game.It contains the rounds and the players.
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import players.Player;
import rounds.*;

public class GameLogic {
    
    private ArrayList<Player> players;
    private ArrayList<Round> rounds;
    private int numberOfRounds;
    private int numberOfQuestions;
    private int numberOfPlayers;

    public GameLogic(int numberOfRounds, int numberOfQuestions,int numberOfPlayers)   throws IOException {
        
        rounds = new ArrayList<>();
        players = new ArrayList<>();
        this.numberOfRounds = numberOfRounds;
        this.numberOfQuestions = numberOfQuestions;
        this.numberOfPlayers = numberOfPlayers;
        createRounds();
    }


    public void setPlayersNames() {
        players.add(new Player());
    }


    /**
     * This method creates the round objects.
     * @throws IOException  for the BufferedReader that is used.
     */
    private void createRounds()   throws IOException {
       
        ArrayList<Integer> unavailableQuestions = new ArrayList<>();
        Random r;
        int typeOfRound;
        boolean thermometerChosen =false;

        for(int i=0;i<numberOfRounds;i++) {
            r = new Random();
            if(numberOfPlayers==2 && !thermometerChosen)
                typeOfRound = r.nextInt(5);
            else if(numberOfPlayers==2)
                typeOfRound = r.nextInt(4);
            else
                typeOfRound = r.nextInt(3);

            if(typeOfRound == 0) {
                Round tempRound = new RoundCorrectAnswer(unavailableQuestions, numberOfQuestions);
                rounds.add(tempRound);
            }
            else if(typeOfRound == 1) {
                Round tempRound = new RoundBetting(unavailableQuestions, numberOfQuestions);
                rounds.add(tempRound);
            }
            else if(typeOfRound == 2) {
                Round tempRound = new RoundTimer(unavailableQuestions, numberOfQuestions);
                rounds.add(tempRound);
            }
            else if(typeOfRound == 3) {
                Round tempRound = new RoundFastAnswer(unavailableQuestions, numberOfQuestions);
                rounds.add(tempRound);
            }
            else if(typeOfRound == 4) {
                Round tempRound = new RoundThermometer(unavailableQuestions, 40);
                rounds.add(tempRound);
                thermometerChosen = true;
            }
        }
    }


    public Round getRound(int indexOfRound) {
        return rounds.get(indexOfRound);
    }


    public int getPlayerPoints(int indexOfPlayer) {
        return players.get(indexOfPlayer).getPoints();
    }


    /**
     * This methods sets the player points depending on whether they answered correctly or not and depending on the type
     * of round.
     * @param indexOfPlayer the index of the player
     * @param correct whether the player is correct or not
     * @param currentRound the index of the round we are currently in
     */
    public void setPlayersPoints(int indexOfPlayer,boolean correct,int currentRound) {
       
        if(rounds.get(currentRound) instanceof RoundCorrectAnswer) {
            if(correct)
                players.get(indexOfPlayer).addPoints(1000);
            else
                players.get(indexOfPlayer).addPoints(0);
        }
        else if(rounds.get(currentRound) instanceof RoundBetting) {
            double bet = ((RoundBetting)rounds.get(currentRound)).getBettingPoints(indexOfPlayer);
            if(correct)
                players.get(indexOfPlayer).addPoints((int)bet);
            else
                players.get(indexOfPlayer).addPoints((int)-bet);
        }
        else if(rounds.get(currentRound) instanceof RoundTimer) {
            double points = ((RoundTimer) rounds.get(currentRound)).getTimeLeft(indexOfPlayer)*0.2;
            if(correct) players.get(indexOfPlayer).addPoints((int)points);
        }
        else if(rounds.get(currentRound) instanceof RoundThermometer) {
            players.get(indexOfPlayer).addPoints(5000);
        }
        else if(rounds.get(currentRound) instanceof RoundFastAnswer) {
            if(correct) {
                if(((RoundFastAnswer)rounds.get(currentRound)).getWhoAnsweredFirst() == indexOfPlayer)
                    players.get(indexOfPlayer).addPoints(1000);
                else
                    players.get(indexOfPlayer).addPoints(500);
            }
            else
                players.get(indexOfPlayer).addPoints(0);
        }
    }
}
