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
	private boolean completed = false;
	
	public Labirynth(int width, int height) {
		this.width = 2 * width - 1;	
		this.height = 2 * height - 1;

		this.grid = new Cell[this.width][this.height];

		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++)
				this.grid[i][j] = new Cell(i, j, CellType.WALL);
		}

		this.begin = this.grid[1][1];
		this.begin.setType(CellType.ROUTE);

		this.end = this.grid[this.width - 2][this.height - 2];
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

			Cell v = stack.pop();
			
			// Move end to the end of path
			if(stack.size() > maxStackSize && (v.x == 1 || v.y == 1 || v.y == height - 2 || v.x == width - 2)){
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
							u = grid[v.x][v.y - 2];
						}
					break;

					case Direction.RIGHT:
						if(width - 2 > v.x){
							u = grid[v.x + 2][v.y];
						}
					break;

					case Direction.DOWN:
						if(height - 2 > v.y){
							u = grid[v.x][v.y + 2];
						}
					break;

					case Direction.LEFT:
						if(v.x > 1){
							u = grid[v.x - 2][v.y];
						}
					break;
				}

				if(u == null || u.isVisited())
					continue;

				if(u.x != 0 && u.x != width - 1 && u.y != 0 && u.y != height - 1)
				{
					grid[(v.x + u.x)/2][(v.y + u.y)/2].setType(CellType.ROUTE);
					u.setParent(v);		
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
		completed = true;
	}
	
	public void writeToBitmap(String path) {
		FileBMP bmp = new FileBMP();
		bmp.load(this);
		bmp.write(path);
	}
	
	/**
     * Getters and Setters
     */
    public void setCompleted(){
    	this.completed = true;
    }
    
    public void setEnd(Cell cell) {
    	this.end = cell;
    }
    public Cell getEnd() {
    	return this.end;
    }

    public void setBegin(Cell cell) {
    	this.begin = cell;
    }
    public Cell getBegin() {
    	return this.begin;
    }

    public Cell getCell(int x, int y) {
    	return this.grid[x][y];
    }

    public int getWidth(){
    	return this.width;
    }
    public int getHeight() {
    	return this.height;
    }
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				str.append(this.grid[j][i]);
			}
			str.append("\n");
		}
		return str.toString();
	}

	public static void main(String[] args) {
		Labirynth lab = new Labirynth(3900, 3900);
		lab.generate();
		lab.solve();
		//System.out.println(lab);
		lab.writeToBitmap("../test.bmp");
	}
}
