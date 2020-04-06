package back.heuristics;

import back.interfaces.Game;
import back.interfaces.Heuristic;

public class TrivialHeuristic implements Heuristic {
    @Override
    public String getName(){
        return "Trivial";
    }
    public int evaluate(Game game) {
        return 1;
    }

}
