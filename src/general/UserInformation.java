/**
 * This class represents a winning player of the game.It saves his points if its an one player game,and his wins if its
 * a two player game.It implements Serializable and it helps us with the HighScore files.
 */

package general;

import java.io.Serializable;

public class UserInformation implements Serializable {
    
    private String name;
    private int points;
    private int wins = 0;

    public UserInformation(String name) {
        this.name = name;
    }

    public void setPoints(int points) { this.points = points;}

    public void addWin() { wins++; }

    public int getWins() { return wins; }

    public int getPoints() { return points; }

    public String getName() { return name; }
}
