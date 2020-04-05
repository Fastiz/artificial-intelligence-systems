package back.heuristics;

import back.interfaces.Game;
import back.interfaces.Heuristic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WalkableDistanceHeuristic implements Heuristic {
    @Override
    public int evaluate(Game game) {
        int[][] goals = game.getGoalsPositions();
        int[] player = game.getPlayerPosition();

        List<int[]> remainingGoals = new ArrayList<>(Arrays.asList(goals));
        int val = minWalkingDistance(remainingGoals, player);
        return val >= goals.length ? val - goals.length : val;
    }

    private int minWalkingDistance(List<int[]> remainingGoals, int[] currentPosition){
        if(remainingGoals.isEmpty())
            return 0;

        if(remainingGoals.size() == 1){
            return Math.abs(remainingGoals.get(0)[0] - currentPosition[0]) + Math.abs(remainingGoals.get(0)[1] - currentPosition[1]);
        }

        int min = 0;
        List<int[]> remainingCopy = new ArrayList<>(remainingGoals);
        for(int[] remaining : remainingGoals){
            remainingCopy.remove(remaining);
            int newVal = minWalkingDistance(remainingCopy, remaining);
            if(newVal < min)
                min = newVal + Math.abs(remaining[0] - currentPosition[0]) + Math.abs(remaining[1] - currentPosition[1]);
            remainingCopy.add(remaining);
        }

        return min;
    }
}
