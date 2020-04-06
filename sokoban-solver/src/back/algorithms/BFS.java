package back.algorithms;

import back.AlgorithmSolution;
import back.algorithms.util.QueueSearch;
import back.interfaces.Algorithm;
import back.interfaces.Game;

import java.util.LinkedList;
import java.util.Queue;

public class BFS implements Algorithm {

    public AlgorithmSolution run(Game game) {
        Queue<Game> gamesQueue = new LinkedList<>();
        gamesQueue.add(game);

        QueueSearch queueSearch = new QueueSearch(this.getName());
        return queueSearch.run(gamesQueue);
    }

    @Override
    public String getName(){
        return "BFS";
    }

}
