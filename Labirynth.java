//import java.util.Stack;
//import java.util.Random;

public class Labirynth {
	private int     width     = 0;
	private int     height    = 0;
	private Cell    begin     = null;
	private Cell    end       = null;
	private Cell    grid[][]  = null;
	private boolean completed = false; // is solved?
	
	public Labirynth() {
		
	}
	
	public void generate(int width, int height) {
		this.width  = width;
		this.height = height;
		this.grid   = new Cell[width][height];
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++)
				this.grid[i][j] = new Cell(i, j, CellType.WALL);
		}
		
		this.begin  = this.grid[0][0]; // have to be changed
		this.end    = this.grid[width - 1][height - 1]; // have to be changed
		
		this.begin.setType(CellType.ROUTE);
		this.end.setType(CellType.CORRECT);

		// Maze generation happens here:
		/*Stack <IntPair> stack = new Stack<IntPair>();
		boolean[][] visited = new boolean[width][height];
		Random rand = new Random();

		stack.push(start);
		while(stack.size() != 0) {
			IntPair v = stack.pop();
			visited[v.first()][v.second()] = true;
			int random = rand.nextInt(5);

			for(int direction = 0; direction < 4; direction++) {
				direction = (random + direction) % 4;
				//System.out.println(Directions.getString(direction));
				IntPair u = Directions.moveCell(v, direction);
				if (onGrid(u) && !visited[u.first()][u.second()]) {
					stack.push(v);
					stack.push(u);
					System.out.println(v + " -> " + u + " = " + Directions.getString(direction));
					visited[u.first()][u.second()] = true;
					grid[v.first()][v.second()][direction] = true;
					grid[u.first()][u.second()][Directions.invertDirection(direction)] = true;
					break;
				}
			}
		}
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				System.out.println("(" + x + ", " + y + "):");
				for(int d = 0; d < 4; d++) {
					if(grid[x][y][d] == true)
						System.out.println(Directions.getString(d));
				}
			}
		}*/
	}
	
	/*
	public void solve() {
		int newWidth = 2 * width + 1;
		int newHeight = 2 * height + 1;
		char[][] maze = new char[newWidth][newHeight];
		
		for(int y = 0; y < newHeight; y++) {
			if(y % 2 == 0) {
				for(int x = 0; x < newHeight; x++) {
					maze[x][y] = '▒';
				}	
			} else {
				for(int x = 0; x < newHeight; x++) {
					if(x % 2 == 0)
						maze[x][y] = '▒';
					else
						maze[x][y] = ' ';
				}
			}
		}

		for(int x = 1; x < newHeight; x += 2) {
			for(int y = 1; y < newWidth; y += 2) {
				for(int d = 0; d < 4	; d++) {
					if(grid[x/2][y/2][d] == true) {
						IntPair cell = new IntPair(x, y);
						cell = Directions.moveCell(cell, d);
						maze[cell.first()][cell.second()] = ' ';	
					}
				}
				
			}
		}

		String s = "";
		for (char[] row: maze) {
			for(char cell: row) {
				s += cell;
			}
			s += "\n";
		}

		return s;
	}
	*/

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				str.append(this.grid[i][j]);
			}
			str.append("\n");
		}
		return str.toString();
	}

	public static void main(String[] args) {
		Labirynth lab = new Labirynth();
		lab.generate(10, 10);
		System.out.println(lab);
	}
}