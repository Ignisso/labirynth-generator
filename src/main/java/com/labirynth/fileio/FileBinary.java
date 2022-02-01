package com.labirynth.fileio;

import com.labirynth.core.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.ArrayIndexOutOfBoundsException;

public class FileBinary
implements FileIO {
	Labirynth maze;
	byte[] data;
	
	public static final byte WALL_B    = 0x00;
	public static final byte ROUTE_B   = 0x01;
	public static final byte CORRECT_B = 0x02;
	public static final byte END_B     = 0x03;
	
	public FileBinary(Labirynth maze) {
		this.maze = maze;
	}
	
	/**
    * Load labirynth from binary file
    * @return true if everything went succesfully
    * @throws IncorrectCoordsException if bitmap coordinates are invalid
    */
	public boolean load() {
		int change = 1;
		if (maze.isCompleted())
			change = 2;
		int size = 40 + change * (maze.getWidth() * maze.getHeight());
		this.data = new byte[size / 8 + 1];
		this.data[0] = (byte)(maze.getWidth() >> 8);
		this.data[1] = (byte)maze.getWidth();
		this.data[2] = (byte)(maze.getHeight() >> 8);
		this.data[3] = (byte)maze.getHeight();
		
		if (maze.isCompleted())
			this.data[4] = 0x01;
		int offset = 40;
		for (int i = 0; i < maze.getHeight(); i++) {
			for (int j = 0; j < maze.getWidth(); j++) {
				int shift = 8 - change - offset % 8;
				byte d = 0x00;
				Cell c;
				try {
					c = this.maze.getCell(i, j);
				} catch (IncorrectCoordsException e) {
					System.err.println("Bitmap coords is not valid");
					return false;
				}
				switch(c.getType()) {
					case WALL:
						d = WALL_B;
						break;
					case ROUTE:
						d = ROUTE_B;
						break;
					case CORRECT:
						d = CORRECT_B;
						break;
					case END:
						d = END_B;
						break;
				}
				this.data[offset / 8] |= (d << shift);
				offset += change;
			}
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
				streamout.write(this.data);
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
    * Read from binary file
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
		this.data = new byte[1024 * 1024 * 64]; // 64 MB
		int len = 0;
		try (FileInputStream streamin = new FileInputStream(filein)) {
			try {
				len = streamin.read(this.data);
			} catch (IOException e) {
				System.out.println("Error occured while reading from file " + path);
				return false;
			}
		} catch (IOException e) {
			System.err.println("Could not read from file " + path);
			return false;
		}
		
		if (len <= 6) {
			System.err.println("Tried to load empty or invalid labirynth");
			return false;
		}
		
		int width = 0;
		width |= this.data[0];
		width <<= 8;
		width |= (this.data[1] & 0xff);
		int height = 0;
		height |= this.data[2];
		height <<= 8;
		height |= (this.data[3] & 0xff);
		try {
			this.maze.setSize(width, height);
		} catch (IncorrectSizeException e) {
			System.err.println("Width and height can not be larger than 2048");
			return false;
		}
		int change = 1;
		if (this.data[4] != 0x00) {
			this.maze.setCompleted();
			change = 2;
		}
		
		int offset = 40;
		Cell grid[][] = new Cell[width][height];
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				int shift = 8 - change - offset % 8;
				byte b = (byte)(this.data[offset / 8] >> shift);
				b &= 0x03;
				if (this.data[4] == 0x00)
					b &= 0x01;
				switch(b) {
					case WALL_B:
						grid[i][j] = new Cell(i, j, CellType.WALL);
						break;
					case ROUTE_B:
						grid[i][j] = new Cell(i, j, CellType.ROUTE);
						break;
					case CORRECT_B:
						grid[i][j] = new Cell(i, j, CellType.CORRECT);
						break;
					case END_B:
						grid[i][j] = new Cell(i, j, CellType.END);
						this.maze.setCompleted();
						break;
				}
				offset += change;
			}
		}
		try {
			this.maze.setGrid(grid);
		} catch (NullPointerException e) {
			System.err.println("No data to read/write");
				return false;
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Grid is inconsistent");
				return false;
		}
		return true;
	}
}
