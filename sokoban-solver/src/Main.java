import java.io.IOException;

import back.game.Game;
import back.game.exception.InvalidMapException;
import reader.MapReader;

public class Main {
	public static void main(String[] args){
		MapReader mapReader;
		try {
			mapReader = new MapReader("map");
		}catch(IOException e) {
			System.err.println(e);
			return;
		}
		

		
		Game game;
		try {
			game = new Game(mapReader.getMap());
		}catch(InvalidMapException e) {
			System.err.print(e);
			return;
		}
		
		System.out.println(game);
		
		game.applyAction(game.getAvailableActions().get(0));
		
		System.out.println(game);
		
		game.applyAction(game.getAvailableActions().get(0));

		System.out.println(game);

	}
}
