/**
 * This class represents the user that plays the game. It contains his username and his total points.
 */

package general;

class Player {

    private int points;

    Player() {
        points = 0;
    }

    void addPoints(int points) { 
        this.points += points; 
    }

    int getPoints() {
        return points;
    }
}
