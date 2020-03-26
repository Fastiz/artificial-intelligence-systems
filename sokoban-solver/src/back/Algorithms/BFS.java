package back.Algorithms;
import back.game.Game;

import java.util.LinkedList;
import java.util.Queue;

public class BFS {

    public void runAlgorithm(Game game) {
        Queue<Game> gamesQueue = new LinkedList<>();
        gamesQueue.add(game);

        QueueSearch queueSearch = new QueueSearch();
        queueSearch.runAlgorithm(gamesQueue);
    }

}
