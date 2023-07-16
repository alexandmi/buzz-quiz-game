/**
 * This class represents the user that plays the game. It contains his username and his total points.
 */

package players;

public class Player {

    private int points;

    public Player() {
        points = 0;
    }

    public void addPoints(int points) { 
        this.points += points; 
    }

    public int getPoints() {
        return points;
    }
}
