package back;

import back.game.Action;
import back.interfaces.Game;

import java.util.ArrayList;
import java.util.List;

public class ResultPrinter {

    public static void printResult(AlgorithmSolution solution) {
        System.out.println("Solution found!");

        List<Game> allGames = new ArrayList<>(solution.getSolutionStates());
        System.out.println("Solution depth: " + solution.getSolutionDepth());

        System.out.println("Solution cost: " + solution.getSolutionCost());

        System.out.println("Expanded nodes: " + solution.getNumberOfExpandedNodes());

        System.out.println("Border nodes: " + solution.getNumberOfBorderNodes());

        System.out.println("Time: " + solution.getProcessingTime());

        List<String> states = new ArrayList<>();
        for(int i = allGames.size() - 1; i >= 0; i--) {
            System.out.println(allGames.get(i).toString());
        }
    }

    public static void printNoSolutionFound(int expandedNodes, int borderNodes, long time) {
        System.out.println("No solution found");

        System.out.println("Expanded nodes: " + expandedNodes);

        System.out.println("Border nodes: " + borderNodes);

        System.out.println("Time: " + time);
    }
}
