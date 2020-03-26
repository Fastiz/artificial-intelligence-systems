package back.game;

public enum CellTypeEnum {
	WALL, BOX, EMPTY, PLAYER, GOAL, GOALANDBOX, PLAYERANDGOAL;
	
	public static boolean movableCell(CellTypeEnum cell) {
		if(cell == EMPTY || cell == GOAL || cell == PLAYER || cell == PLAYERANDGOAL) {
			return true;
		}else {
			return false;	
		}
	}
	
	public static CellTypeEnum moveBoxTo(CellTypeEnum to) {
		if(to == GOAL || to == PLAYERANDGOAL)
			return GOALANDBOX;
		return BOX;
	}
	
	public static CellTypeEnum movePlayerTo(CellTypeEnum to) {
		if(to == GOAL)
			return PLAYERANDGOAL;
		return PLAYER;
	}
	
	public static CellTypeEnum moveFrom(CellTypeEnum from) {
		if(from == PLAYERANDGOAL || from == GOALANDBOX)
			return GOAL;
		return EMPTY;
	}
}
