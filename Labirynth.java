import java.util.Random;

public class Labirynth {
    
    private int     width 	  = 0;
	private int     height 	  = 0;
	private Cell    begin 	  = null;
	private Cell    end 	  = null;
	private Cell    grid[][]  = null;
	private boolean completed = false;
	
	/**
	 * Constructor for class Labirynth
	 * @param width - width of the Labirynth in vertices
	 * @param height - height of the Labirynth in vertices
	 */ 
	
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
	}

	/**
	 *  Generates Labirytnth
	 *  @param seed [optional] - generates Labirynth using gives seed
	 */

    public void generateLabirynth(long seed) {
    	Generator mazeGenerator = new Generator(this, seed);
        mazeGenerator.generate();
    }

    public void generateLabirynth() {
    	Generator mazeGenerator = new Generator(this, new Random().nextLong());
        mazeGenerator.generate();
    }

    /**
     * Solves Labirynth
     */
    public void solveLabirynth() {
        Solver mazeSolver = new Solver(this);
        mazeSolver.solve();
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
		Labirynth lab = new Labirynth(10, 10);
		lab.generateLabirynth();
		lab.solveLabirynth();
		System.out.println(lab);	
	}
}
