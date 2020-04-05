package back.heuristics;

import back.interfaces.Game;
import back.interfaces.Heuristic;

public class StackHeuristic implements Heuristic {

    public int evaluate(Game game) {
        return game.getGameStack().size();
    }
}
