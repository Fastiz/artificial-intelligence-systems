package back;

import back.game.Action;
import back.game.Game;

import java.util.ArrayList;
import java.util.List;

public class ResultPrinter {

    public static void printResult(int expandedNodes, int borderNodes, Game solution, long time) {
        System.out.println("Solution found!");

        List<Action> allActions = new ArrayList<>(solution.getActionList());
        System.out.println("Solution depth: " + allActions.size());

        int solutionCost = 0;
        for(Action action : allActions)
            solutionCost += action.getActionCost();
        System.out.println("Solution cost: " + solutionCost);

        System.out.println("Expanded nodes: " + expandedNodes);

        System.out.println("Border nodes: " + borderNodes);

        System.out.println("Time: " + time);

        List<String> states = new ArrayList<>();
        do {
            states.add(solution.toString());
        } while (solution.revertAction());

        for(String state : states)
            System.out.println(states);
    }

    public static void printNoSolutionFound(int expandedNodes, int borderNodes, long time) {
        System.out.println("No solution found");

        System.out.println("Expanded nodes: " + expandedNodes);

        System.out.println("Border nodes: " + borderNodes);

        System.out.println("Time: " + time);
    }
}
