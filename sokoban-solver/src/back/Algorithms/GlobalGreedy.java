package back.Algorithms;

import back.Interfaces.Heuristic;
import back.game.Game;

import java.util.Comparator;
import java.util.PriorityQueue;

public class GlobalGreedy {

    public void runAlgorithm(Game game, Heuristic heuristic) {
        Comparator<Game> gameComparator = Comparator.comparingInt(heuristic::evaluate);
        PriorityQueue<Game> gamesPriorityQueue = new PriorityQueue<>(gameComparator);

        QueueSearch queueSearch = new QueueSearch();
        queueSearch.runAlgorithm(gamesPriorityQueue);
    }
}
