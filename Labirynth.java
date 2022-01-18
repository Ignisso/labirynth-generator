import java.util.Random;

public class Labirynth {
	private int     width = 0;
	private int     height = 0;
	private Cell    grid[][] = null;
	private Cell    begin = null;
	private Cell    end = null;
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
	
	public Labirynth() {
	
	}
	
	public void reset() {
		if (this.completed == false)
			return;
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				if (this.grid[i][j].getType() == CellType.CORRECT ||
					this.grid[i][j].getType() == CellType.END)
					this.grid[i][j].setType(CellType.ROUTE);
			}
		}
		this.completed = false;
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
	
	public void writeToBitmap(String path) {
		FileBMP bmp = new FileBMP(this);
		bmp.load();
		bmp.write(path);
	}
	
	public void readFromBitmap(String path) {
		FileBMP bmp = new FileBMP(this);
		bmp.read(path);
	}
	
	/**
	 * Getters and Setters
	 */
	public void setCompleted() {
		this.completed = true;
	}
	
	public void setBegin(Cell cell) {
		reset();
		this.begin = cell;
	}
	
	public void setBegin(int x, int y) {
		reset();
		this.begin = this.grid[2 * x - 1][2 * y - 1];
	}
	
	public Cell getBegin() {
		return this.begin;
	}
	
	public void setEnd(Cell cell) {
		reset();
		this.end = cell;
	}

	public void setEnd(int x, int y) {
		reset();
		this.end = this.grid[2 * x - 1][2 * y - 1];
	}
	
	public Cell getEnd() {
		return this.end;
	}
	
	public Cell getCell(int x, int y) {
		return this.grid[x][y];
	}
	
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
		this.grid = new Cell[this.width][this.height];
	
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++)
				this.grid[i][j] = new Cell(i, j, CellType.WALL);
		}
	}
	
	public void setGrid(Cell arr[][]) {
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++)
				this.grid[i][j] = arr[i][j];
		}
	}
	
	public int getWidth() {
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
		Labirynth lab = new Labirynth(100, 100);
		lab.generateLabirynth();
		lab.setBegin(93, 54);
		lab.setEnd(79, 20);
		lab.solveLabirynth();
		lab.writeToBitmap("../maze.bmp");
		Labirynth rlab = new Labirynth();
		rlab.readFromBitmap("../maze.bmp");
		rlab.writeToBitmap("../maze2.bmp");
		//System.out.println(lab);	
	}
}
