import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Main {
	public static void main(String[] args){
//		MapReader mapReader;
//		try {
//			mapReader = new MapReader("map2.txt");
//		}catch(IOException e) {
//			System.err.println(e);
//			return;
//		}
//
//
//
//		Game game = new GameImplementation(mapReader.getMap(), mapReader.getPlayerPosition(), mapReader.getBoxesPositions());
//
//
//		Algorithm dfs = new DFS();
//
//		AlgorithmSolution solution = dfs.run(game);
//
//		if(solution.isGoalFound()) {
//			for(Game state : solution.getSolutionStates()) {
//				System.out.print(state);
//			}
//		}else {
//			System.out.println("THERE ARE NO SOLUTIONS TO THIS PROBLEM.");
//		}
//

		Stack<Integer> stack = new Stack<>();
		stack.push(4);
		stack.push(3);
		stack.push(2);
		stack.push(1);

		List<Integer> list = new ArrayList<>(stack);

			System.out.println(stack.pop());
	}
}
