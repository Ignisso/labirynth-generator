import java.util.Stack;
import java.util.Random;
import java.util.Queue;
import java.util.LinkedList;

public class Labirynth {
	private int     width 	  = 0;
	private int     height 	  = 0;
	private Cell    begin 	  = null;
	private Cell    end 	  = null;
	private Cell    grid[][]  = null;
	private boolean completed = false; // is solved?
	
	public Labirynth(int width, int height) {
		this.width = 2 * width - 1;	
		this.height = 2 * height - 1;

		this.grid = new Cell[this.width][this.height];

		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++)
				this.grid[i][j] = new Cell(i, j, CellType.WALL);
		}

		this.begin  = this.grid[0][0]; // have to be changed
		this.begin.setType(CellType.ROUTE);

		this.end 	= this.grid[this.width - 1][this.height - 1];
		this.end.setType(CellType.ROUTE);
	}
	
	public Labirynth() {

	}

	public void generate() {

		Stack <Cell> stack = new Stack<Cell>();
		Random rand = new Random(); // We can pass seed here
		
		int maxStackSize = 2;

		stack.push(this.begin);
		while(stack.size() != 0) {
			//System.out.println(stack);
			Cell v = stack.pop();
			
			if(stack.size() > maxStackSize && (v.x == 0 || v.y == 0 || v.y == height - 1 || v.x == width - 1)){
				this.end = v;
				maxStackSize = stack.size();
			}

			grid[v.x][v.y].setType(CellType.ROUTE);
			v.visit();
			int offset = rand.nextInt(Direction.LENGTH + 1);
			for(int direction = 0; direction < Direction.LENGTH; direction++) {		
				Cell u = null;
				switch((offset + direction) % Direction.LENGTH) {
					case Direction.UP:
						if(v.y > 1){
							u = grid[  v.x  ][v.y - 2];
						}
					break;

					case Direction.RIGHT:
						if(width - 2 > v.x){
							u = grid[v.x + 2][  v.y  ];
						}
					break;

					case Direction.DOWN:
						if(height - 2 > v.y){
							u = grid[  v.x  ][v.y + 2];
						}
					break;

					case Direction.LEFT:
						if(v.x > 1){
							u = grid[v.x - 2][  v.y  ];
						}
					break;
				}
				if (u != null && !u.isVisited()) {
					grid[  (v.x + u.x)/2  ][(v.y + u.y)/2].setType(CellType.ROUTE);
					u.setParent(v);
					//System.out.println("(" + v.x + ", " + v.y + ") -> (" + u.x + ", " + u.y + ")");			
					stack.push(v);
					stack.push(u);
					break;
				}
			}
		}

	}
	
	
	public void solve() {
		Cell cellNow = this.end;

		while(cellNow != this.begin && cellNow != null) {
			cellNow.setType(CellType.CORRECT);
			cellNow = cellNow.getParent();
		}
		this.begin.setType(CellType.CORRECT);
	}
	

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for(int i = 0; i < this.width + 2; i++) {
			str.append("#");
		}
		str.append("\n");
		for (int i = 0; i < this.height; i++) {
			str.append("#");
			for (int j = 0; j < this.width; j++) {
				str.append(this.grid[j][i]);
			}
			str.append("#\n");
		}
		for(int i = 0; i < this.width + 2; i++) {
			str.append("#");
		}
		str.append("\nBEGIN: (");
		str.append(this.begin.x); str.append(", "); str.append(this.begin.y); str.append(")\n");
		str.append("\nEND: (");
		str.append(this.end.x); str.append(", "); str.append(this.end.y); str.append(")\n");
		return str.toString();
	}

	public static void main(String[] args) {
		while(true) {
			Labirynth lab = new Labirynth(10, 10);
			lab.generate();
			lab.solve();
			System.out.println(lab);
		}
	}
}