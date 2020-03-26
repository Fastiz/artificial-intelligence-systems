package back.Heuristics;

import back.Interfaces.Heuristic;
import game.Game;

public class TrivialHeuristic implements Heuristic {

    public int evaluate(Game game) {
        return 1;
    }

}
