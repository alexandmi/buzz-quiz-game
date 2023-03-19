/**
 * This class represents the logic of the high scores.It contains two Maps,for saving in and reading out of two files the
 * results of players games are saved.The map has an integer for a kay, which shows the position of the player in the
 * scoreboard,and an UserInformation object.
 */

package general;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class HighScores {
    
    private Map<Integer,UserInformation> scoresOnePlayer;
    private Map<Integer,UserInformation> scoresTwoPlayers;


    public HighScores() throws IOException,ClassNotFoundException {
        
        scoresOnePlayer = new HashMap<>();
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("scoresOnePlayer"));
        scoresOnePlayer = (Map<Integer, UserInformation>) inputStream.readObject();

        scoresTwoPlayers = new HashMap<>();
        ObjectInputStream inputStream2 = new ObjectInputStream(new FileInputStream("scoresTwoPlayers"));
        scoresTwoPlayers = (Map<Integer, UserInformation>) inputStream2.readObject();
    }


    private void createScoreOnePlayer(int index,UserInformation info) throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("scoresOnePlayer"));
        scoresOnePlayer.put(index,info);
        outputStream.writeObject(scoresOnePlayer);
        outputStream.flush();
        outputStream.close();
    }


    private void createScoreTwoPlayers(int index,UserInformation info) throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("scoresTwoPlayers"));
        scoresTwoPlayers.put(index,info);
        outputStream.writeObject(scoresTwoPlayers);
        outputStream.flush();
        outputStream.close();
    }


    /**
     * This method is called when a player has finished a game meant for one player,so his name and points,if they surpass
     * another player in the scoreboard,submit to the scoresOnePlayer Map.If he is to be submitted,all the players below him
     * go one position down.
     * @param name the name of the player
     * @param points the number of points he had
     * @throws IOException because we are using the createScoreOnePlayer method
     */
    public void addScoreOnePlayer(String name,int points)  throws IOException {
        
        UserInformation player = new UserInformation(name);
        player.setPoints(points);
        int size = scoresOnePlayer.size();

        for(int i=0; i<size; i++) {
            UserInformation tempUserInfo  =  scoresOnePlayer.get(i);

            if(player.getPoints() >= tempUserInfo.getPoints()) {
                for(int j=size-1; j>= i; j--) {
                    UserInformation tempLoserInfo = scoresOnePlayer.get(j);
                    if(j+1<10) createScoreOnePlayer(j+1,tempLoserInfo);
                }
                createScoreOnePlayer(i,player);
                return;
            }
        }
        if(size<10)
            createScoreOnePlayer(size,player);
    }


    /**
     * This method is called when a player has finished a game meant for two players,so his name,if he surpasses another
     * player in the scoreboard by total wins,submits to the scoresTwoPlayers Map.If he is to be submitted,all the players
     * below him go one position down. Because we need to count the wins of a specific username,which may have appeared
     * before,all user names with their wins are committed to the Map,but only the first 10 appear.
     * @param name the name of the player
     * @throws IOException because we are using the createScoreOnePlayer method
     */
    public void addScoreTwoPlayers(String name) throws IOException {
        
        UserInformation player = new UserInformation(name);
        int size = scoresTwoPlayers.size();

        for(int i=0; i<size; i++) {
            
            UserInformation tempUserInfo = scoresTwoPlayers.get(i);

            if(tempUserInfo.getName().equals(player.getName())) {
                tempUserInfo.addWin();
                int wins = tempUserInfo.getWins();
                if(i<10) {
                    for(int j=0; j<i; j++) {
                        if(wins>=scoresTwoPlayers.get(j).getWins()) {
                            for(int w=i-1; w>=j; w--)
                                scoresTwoPlayers.put(w+1,scoresTwoPlayers.get(w));
                            scoresTwoPlayers.put(j,tempUserInfo);
                            return;
                        }
                    }
                    return;
                }
                else {
                    for (int j = 0; j < 10; j++) {
                        if (wins >= scoresTwoPlayers.get(j).getWins()) {
                            createScoreTwoPlayers(i, scoresTwoPlayers.get(9));
                            for (int w = 8; w >= j; w--)
                                createScoreTwoPlayers(w + 1, scoresTwoPlayers.get(w));
                            createScoreTwoPlayers(j, tempUserInfo);
                            return;
                        }
                    }
                    return;
                }
            }
        }
        player.addWin();
        createScoreTwoPlayers(scoresTwoPlayers.size(),player);
    }

    public Map<Integer,UserInformation> getHighScoresOnePlayer() { return scoresOnePlayer;}

    public Map<Integer,UserInformation> getHighScoresTwoPlayers() { return scoresTwoPlayers;}
}
