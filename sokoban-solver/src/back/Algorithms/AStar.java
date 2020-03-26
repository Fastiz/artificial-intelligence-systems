package back.Algorithms;

import back.Interfaces.Heuristic;
import back.game.Game;

import java.util.Comparator;
import java.util.PriorityQueue;

public class AStar {

    public void runAlgorithm(Game game, Heuristic heuristic) {
        Comparator<Game> gameComparator = (g1, g2) -> {
            int f1 = g1.getEstimatedCost() + g1.getAcumulatedCost();
            int f2 = g2.getEstimatedCost() + g2.getAcumulatedCost();
            return f1 - f2;
        };

        PriorityQueue<Game> gamesPriorityQueue = new PriorityQueue<>(gameComparator);
        game.setEstimatedCost(heuristic.evaluate(game));
        gamesPriorityQueue.add(game);

        QueueSearch queueSearch = new QueueSearch();
        queueSearch.runAlgorithm(gamesPriorityQueue, heuristic);
    }
}
