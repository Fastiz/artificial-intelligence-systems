package back.heuristics;

import back.game.CellTypeEnum;
import back.interfaces.Game;
import back.interfaces.Heuristic;

import java.util.*;

public class SimpleAssignationConsideringWalls implements Heuristic {
    class PosDepth{
        private int[] pos;
        private int depth;

        public PosDepth(int[] pos, int depth){
            this.pos = pos;
            this.depth = depth;
        }
    }

    @Override
    public int evaluate(Game game) {
        int[][] boxes = game.getBoxesPositions();
        CellTypeEnum[][] map = game.getMap();
        int value = 0;
        for(int[] box : boxes){
            value += bfsFromBox(box, map);
        }
        return value;
    }

    private int bfsFromBox(int[] box, CellTypeEnum[][] map){
        boolean[][] visited = new boolean[map.length][map[0].length];
        Queue<PosDepth> border = new LinkedList<>();
        border.add(new PosDepth(box, 0));

        while(!border.isEmpty()){
            PosDepth posDepth = border.poll();
            int[] pos = posDepth.pos;
            int depth = posDepth.depth;

            visited[pos[0]][pos[1]] = true;

            if(map[pos[0]][pos[1]] == CellTypeEnum.GOAL){
                return depth;
            }

            for(int i = Math.max(pos[0] - 1, 0); i < Math.min(pos[0] + 1, map.length); i++){
                for(int j = Math.max(pos[1] -1, 0); j < Math.min(pos[1] + 1, map[0].length); j++){
                    if(i!=pos[0] || j!=pos[1]){
                        if(map[i][j] != CellTypeEnum.WALL && !visited[i][j]){
                            border.add(new PosDepth(new int[]{i, j}, depth));
                        }
                    }
                }
            }

        }

        return 0;
    }

    @Override
    public String getName() {
        return "Simple assignation considering walls";
    }
}
