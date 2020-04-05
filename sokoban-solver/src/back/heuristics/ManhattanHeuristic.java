package back.heuristics;

import back.game.CellTypeEnum;
import back.interfaces.Game;
import back.interfaces.Heuristic;
import javafx.scene.control.Cell;

public class ManhattanHeuristic implements Heuristic {
    @Override
    public int evaluate(Game game) {
        int[][] goals = game.getGoalsPositions();
        int[][] boxes = game.getBoxesPositions();

        int totalDistance = 0;
        for(int[] goal : goals){
            int max = 0;
            for(int[] box : boxes){
                int newVal = Math.abs(goal[0]-box[0]) + Math.abs(goal[1]-box[1]);
                if(max < newVal)
                    max = newVal;
            }
            totalDistance += max;
        }

        return totalDistance;
    }
}
