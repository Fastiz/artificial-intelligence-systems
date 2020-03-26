package back.Algorithms;

import back.Interfaces.Heuristic;
import game.Game;

import java.util.Comparator;
import java.util.PriorityQueue;

public class AStar {

    public void runAlgorithm(Game game, Heuristic heuristic) {
        Comparator<Game> gameComparator = (g1, g2) -> {
            int f1 = heuristic.evaluate(g1) + g1.getAcumulatedCost();
            int f2 = heuristic.evaluate(g2) + g2.getAcumulatedCost();
            return f1 - f2;
        };

        PriorityQueue<Game> gamesPriorityQueue = new PriorityQueue<>(gameComparator);

        QueueSearch queueSearch = new QueueSearch();
        queueSearch.runAlgorithm(gamesPriorityQueue);
    }
}
