package com.labirynth.core;

public class Main {
	public static void main(String[] args) {

		Labirynth lab = new Labirynth(20, 20);
		Labirynth rlab = new Labirynth();
		Labirynth slab = new Labirynth();
		lab.generateLabirynth();
		try {		
			lab.setBegin(3, 3);
			lab.setEnd(17, 17);
		}
		catch (IncorrectCoordsException e) {
			System.err.println("Begin/End wrong coords");	
		}
		try {
			lab.solveLabirynth();
		}
		catch (UninitializedDataException e) {
			System.err.println("Begin/End is null");
		}
		lab.writeToBitmap("../maze.bmp");
		lab.writeToBinary("../maze.bin");
		lab.writeToText("../maze.txt");
			
		rlab.readFromBitmap("../maze.bmp");
		rlab.writeToBitmap("../maze2.bmp");
		rlab.readFromBinary("../maze.bin");
		rlab.writeToBinary("../maze2.bin");
		rlab.readFromText("../maze.txt");
		rlab.writeToText("../maze2.txt");
		
		slab.readFromBitmap("../maze2.bmp");
		slab.writeToBitmap("../maze3.bmp");
		slab.readFromBinary("../maze2.bin");
		slab.writeToBinary("../maze3.bin");
		slab.readFromText("../maze2.txt");
		slab.writeToText("../maze3.txt");
	}
}

