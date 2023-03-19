/**
 * This class represents the user that plays the game. It contains his username and his total points.
 */

public class Player {

    private int points;
    private String name;

    public Player(String name){
        this.name = name;
        points = 0;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public int getPoints() {
        return points;
    }

    public String getName() {
        return name;
    }
}
