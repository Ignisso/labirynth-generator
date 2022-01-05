public class Directions {
	public final static int UP = 0;
	public final static int RIGHT = 1;
	public final static int DOWN = 2; 
	public final static int LEFT = 3;

	public static IntPair moveCell(IntPair cell, int dir) {
		if(dir == UP) {
			return new IntPair(cell.first(), cell.second() - 1);
		}
		else if (dir == RIGHT) {
			return new IntPair(cell.first() + 1, cell.second());
		}
		else if (dir == DOWN) {
			return new IntPair(cell.first(), cell.second() + 1);
		}
		else if (dir == LEFT) {
			return new IntPair(cell.first() - 1, cell.second());
		}
		return null;
	}

	public static int invertDirection(int dir) {
		return (dir + 2) % 4;
	}

	public static String getString(int dir) {
		if(dir == UP) {
			return "UP";
		}
		else if (dir == RIGHT) {
			return "RIGHT";
		}
		else if (dir == DOWN) {
			return "DOWN";
		}
		else if (dir == LEFT) {
			return "LEFT";
		}
		return "";
	}
}