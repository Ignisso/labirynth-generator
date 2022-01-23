public class Main {
	public static void main(String[] args) {
		Labirynth lab = new Labirynth(10, 10);
		lab.generateLabirynth();
		lab.setBegin(1, 1);
		lab.setEnd(5, 5);
		lab.solveLabirynth();
		lab.writeToBitmap("../maze.bmp");
		Labirynth rlab = new Labirynth();
		rlab.readFromBitmap("../maze.bmp");
		rlab.writeToBitmap("../maze2.bmp");
		System.out.println(lab);	
	}
}