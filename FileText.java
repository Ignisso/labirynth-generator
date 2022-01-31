import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class FileText
implements FileIO {
	Labirynth maze;
	StringBuilder data;
	
	public static final byte WALL_ASCII = '#';
	public static final byte ROUTE_ASCII = ' ';
	public static final byte CORRECT_ASCII = '.';
	public static final byte END_ASCII = 'X';
	
	public FileText(Labirynth maze) {
		this.maze = maze;
		this.data = new StringBuilder();
	}
	
	/**
    * Load bitmap
    * @return true if everything went succesfully
    * @throws IncorrectCoordsException if bitmap cords are invalid
    * @throws UninitializedDataException if bitmap header wasn't created
    * @throws ArrayIndexOutOfBoundsException if ammount of initialized memory isn't sufficient
    */
	public boolean load() {
		this.maze = maze;
		for (int j = 0; j < this.maze.getHeight(); j++) {
			for (int i = 0; i < this.maze.getWidth(); i++) {
				Cell c;
				try {
					c = this.maze.getCell(i, j);
				} catch (IncorrectCoordsException e) {
					System.err.println("Bitmap coords is not valid");
					return false;
				}
				switch (c.getType()) {
					case WALL:
						this.data.append((char)WALL_ASCII);
						break;
					case ROUTE:
						this.data.append((char)ROUTE_ASCII);
						break;
					case CORRECT:
						this.data.append((char)CORRECT_ASCII);
						break;
					case END:
						this.data.append((char)END_ASCII);
						break;
				}
			}
			this.data.append('\n');
		}
		return true;
	}

	/**
    * Write labirynth object to file 
    * @param path path to output file
    * @return true if everything went succesfully
    * @throws NullPointerException if path to file is invalid
    * @throws IOException if error occured during writing to file
    */
	public boolean write(String path) {
		File fileout;
		try {
			fileout = new File(path);
		} catch (NullPointerException e) {
			System.err.println("Invalid path to file");
			return false;
		}
		try (FileOutputStream streamout = new FileOutputStream(fileout)){
			try {
				streamout.write(this.data.toString().getBytes());
			}
			catch (IOException e) {
				System.err.println("Error occured while writing to file " + path);
				return false;
			}
		} catch (IOException e) {
			System.err.println("Could not open or create file " + path);
			return false;
		}
		return true;
	}
	
	/**
    * Read from text file
    * @param path path to input file
    * @return true if everything went succesfully
    * @throws NullPointerException if path to file is invalid
    * @throws IOException if error occured during reading a file
    * @throws IncorrectSizeException if labirynth width or height exceeds 2048
    * @throws ArrayIndexOutOfBoundsException if grid is inconsistent
    */
	public boolean read(String path) {
		this.maze.clear();
		File filein;
		try {
			filein = new File(path);
		} catch (NullPointerException e) {
			System.err.println("Invalid path to file");
			return false;
		}
		byte arr[] = new byte[1024 * 1024 * 256]; // 256 MB
		int size;
		try (FileInputStream streamin = new FileInputStream(filein)) {
			try {
				size = streamin.read(arr);
			} catch (IOException e) {
				System.err.println("Error occured while reading from file " + path);
				return false;
			}
		} catch (IOException e) {
			System.err.println("Could not read from file " + path);
			return true;
		}
		
		int width = 0;
		int height = 0;
		while (arr[width] != '\n' && width <= 2048)
			width ++;
		height = size / width - 1;
		if (width > 2048 || size == 0) {
			System.err.println("Not a correct labirynth format");
			return false;
		}
		try {
			this.maze.setSize(width, height);
		} catch (IncorrectSizeException e) {
			System.err.println("Width and height can not be larger than 2048");
			return false;
		}

		Cell grid[][] = new Cell[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				switch (arr[i + j * width + j]) {
					case WALL_ASCII:
						grid[i][j] = new Cell(i, j, CellType.WALL);
						break;
					case ROUTE_ASCII:
						grid[i][j] = new Cell(i, j, CellType.ROUTE);
						break;
					case CORRECT_ASCII:
						grid[i][j] = new Cell(i, j, CellType.CORRECT);
						break;
					case END_ASCII:
						grid[i][j] = new Cell(i, j, CellType.END);
						this.maze.setCompleted();
						break;
				}
			}
		}
		try {
			this.maze.setGrid(grid);
		} catch (NullPointerException e) {
			System.err.println("No data to read/write");
				return false;
		}
		return true;
	}
}
