import java.util.Scanner;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Set;

public class Shell {

	private HashMap<String, Labirynth> list;
	private boolean run;
	private Scanner scan;
	private String command;
	private String[] cmdSplit;

	public Shell() {
		list = new HashMap<String, Labirynth>();
		scan = new Scanner(System.in);
	}
	/**
    * Launch shell
    * @throws IncorrectCoordsException if x or y isnt valid
    */
	public void launch() 
	throws IncorrectCoordsException {
		run = true;
		this.drawLogo();
		while(this.isRunning()) {
			
			this.getNextCommand();

			if (this.getCmdSplit(0).equals("help")){
				this.printHelp();
			}
			else if(this.getCmdSplit(0).equals("exit") || this.getCmdSplit(0).equals("quit") ) {
				this.stop();

			}
			else if (this.getCmdSplit(0).equals("new")) {
				if(this.getCmdLength() == 2) {
					this.addLabirynth(this.getCmdSplit(1), new Labirynth());
						this.printInfo("Succesfully created Labirynth object " + this.getCmdSplit(1));
				} else if(this.getCmdLength() == 4) {
					this.addLabirynth(this.getCmdSplit(1), new Labirynth(Integer.parseInt(this.getCmdSplit(2)), Integer.parseInt(this.getCmdSplit(3))));
						this.printInfo("Succesfully created Labirynth object " + this.getCmdSplit(1) + " " + this.getCmdSplit(2) + "x" + this.getCmdSplit(3));
				}
			}
			else if (this.getCmdSplit(0).equals("list")) {
				this.printInfo("List of all labirynths:");
			  	System.out.println(this.getKeys());
			}
			else if (this.getKeys().contains(this.getCmdSplit(0))) {
				if(this.getCmdSplit(1).equals("generate")){
					if(this.getCmdLength() == 2) {
						if(this.getLabirynth(this.getCmdSplit(0)).generateLabirynth())
							this.printInfo("Succesfully generated labirynth");
					} else if(this.getCmdLength() == 3) {
						try {
							if(this.getLabirynth(this.getCmdSplit(0)).generateLabirynth(Integer.parseInt(this.getCmdSplit(2))))
								this.printInfo("Succesfully generated labirynth from seed: " + this.getCmdSplit(2));
						} catch (NumberFormatException e) {
							this.printError("seed has to be an integer");
						}
					}

				}
				else if(this.getCmdSplit(1).equals("set") && this.getCmdSplit(2).equals("begin"))
				{
					try {
						this.getLabirynth(this.getCmdSplit(0)).setBegin(Integer.parseInt(this.getCmdSplit(3)), Integer.parseInt(this.getCmdSplit(4)));
						this.printInfo("Succesfully set labirynth begining point to (" + this.getCmdSplit(3) + ", " + this.getCmdSplit(4) + ")");
					} 
					catch (NumberFormatException e) {
						this.printError("cordinates have to be an integer");
						
					}
				}
				else if(this.getCmdSplit(1).equals("set") && this.getCmdSplit(2).equals("end")){
					try {
						this.getLabirynth(this.getCmdSplit(0)).setEnd(Integer.parseInt(this.getCmdSplit(3)), Integer.parseInt(this.getCmdSplit(4)));
						this.printInfo("Succesfully set labirynth ending point to (" + this.getCmdSplit(3) + ", " + this.getCmdSplit(4) + ")");
					} 
					catch (NumberFormatException e)  {
						this.printError("cordinates have to be an integer");
					}
					
				}
				else if(this.getCmdSplit(1).equals("solve")){
					if(this.getLabirynth(this.getCmdSplit(0)).solveLabirynth())
						this.printInfo("Succesfully solved labirynth");
				
				}
				else if(this.getCmdSplit(1).equals("write")){
					if(this.getCmdLength() != 4) {
						System.out.println("Invalid number of arguments, expected 4, got " + this.getCmdLength() );
					}
					else if(this.getCmdSplit(2).equals("bitmap")){
						if(this.getLabirynth(this.getCmdSplit(0)).writeToBitmap(this.getCmdSplit(3)))
							this.printInfo("Succesfully wrote labirynth bitmap file to " + this.getCmdSplit(3));
					} 
					else if(this.getCmdSplit(2).equals("text")){
						if(this.getLabirynth(this.getCmdSplit(0)).writeToText(this.getCmdSplit(3)))
							this.printInfo("Succesfully wrote labirynth text file to " + this.getCmdSplit(3));
					} 
					else if(this.getCmdSplit(2).equals("binary")){
						if(this.getLabirynth(this.getCmdSplit(0)).writeToBinary(this.getCmdSplit(3)))
							this.printInfo("Succesfully wrote labirynth binary file to " + this.getCmdSplit(3));
					}
					else {
						this.printError("Unknown file type " + this.getCmdSplit(2));
					}
				}
				else if(this.getCmdSplit(1).equals("read")){
					if(this.getCmdLength() != 4) {
						this.printError("Invalid number of arguments, expected 4, got " + this.getCmdLength() );
					}
					else if(this.getCmdSplit(2).equals("bitmap")){
						if(this.getLabirynth(this.getCmdSplit(0)).readFromBitmap(this.getCmdSplit(3)))
							this.printInfo("Succesfully read labirynth bitmap file from " + this.getCmdSplit(3));
					} 
					else if(this.getCmdSplit(2).equals("text")){
						if(this.getLabirynth(this.getCmdSplit(0)).readFromText(this.getCmdSplit(3)))
							this.printInfo("Succesfully read labirynth text file from " + this.getCmdSplit(3));
					} 
					else if(this.getCmdSplit(2).equals("binary")){
						if(this.getLabirynth(this.getCmdSplit(0)).readFromBinary(this.getCmdSplit(3)))
							this.printInfo("Succesfully read labirynth binary file from " + this.getCmdSplit(3));
					} 
					else {
						this.printError("Unknown file type " + this.getCmdSplit(2));
					}
				}
				else if(this.getCmdSplit(1).equals("print")) {
					Labirynth lab = this.getLabirynth(this.getCmdSplit(0));
					for(int y = 0; y < lab.getHeight(); y++) {
						for (int x = 0; x < lab.getWidth(); x++) {
							System.out.print(lab.getCell(x,y));
						} System.out.println();
					}
				}
				else {
					this.printError("Unknown command " + this.getCmdSplit(1));
				}
			}
			else {
				this.printError("Unknown command " + this.getCmdSplit(0));
			}
		}
	}
	/**
    * Get next command from user
    */
	public void getNextCommand() {
		System.out.print("> ");
		this.command = scan.nextLine();
		this.cmdSplit = command.split(" ");
	}

	/**
    * Check whether shell is running
    * @return boolean 
    */
	public boolean isRunning() {
		return this.run;
	}

	/**
    * Stop shell
    */
	public void stop() {
		this.run = false;
		System.out.println("Bye!");
	}

	/**
    * Print info
    */
	public void printInfo(String message) {
		System.out.println("[INFO]:" + message);
	}

	/**
    * Print error
    */
	public void printError(String message) {
		System.out.println("[ERR]: " + message);
	}

	/**
    * get labirynth based on its name
    * @param key name
    * @return asked labirynth
    */
	public Labirynth getLabirynth(String key) {
		return list.get(key);
	}

	/**
    * add labirynth
    * @param key labirynth name
    * @param labirynth labirynth object
    */
	public void addLabirynth(String key, Labirynth lab) {
		list.put(key, lab);
	}

	/**
    * Get ith command parameter
    * @param index ith
    * @return string containing parameter
    */
	public String getCmdSplit(int index) {
		if(index >= this.getCmdLength())
			return null;
		return this.cmdSplit[index];
	}

	/**
    * get command parameter length
    * @return length
    */
	public int getCmdLength() {
		return this.cmdSplit.length;
	}

	/**
    * get all labirynths from set
    * @return list containging labirynths
    */
	public Set<String> getKeys() {
		return this.list.keySet();
	}

	/**
    * draw logo
    */
	public void drawLogo() {
		System.out.println("___________________________________________________________________");
		System.out.println("|  |___   ___________            |   __________________________   |");
		System.out.println("|  |     |   _____   |  |  |  |  |  |   ____________________   |  |");
		System.out.println("|_____|__|  |     |_____|  |  |_____|  |   _________________   |  |");
		System.out.println("|  |   __   ______|______________|________ __|__|___________|     |");
		System.out.println("|  |  |  |  |     |     |  |     |  |  |  |  |     |  |  |  |  |  |");
		System.out.println("|  |  |  |  |  |  |  |  |  |  |  |  |  |  |  |__ __|  |  |  |  |  |");
		System.out.println("|  |  |  |  |  |  |  |  |  |  |  |__   |     |  |  |  |  |  |  |  |");
		System.out.println("|  |  |  |  |     |     |  |     |  |  |  |  |  |  |     |  |  |  |");
		System.out.println("|  |  |  |  |  |  |  |  |  |  |  |  |  |  |  |  |  |  |  |  |  |  |");
		System.out.println("|  |  |  |__|  |  |  |  |  |  |  |__|  |  |  |  |  |  |  |  |  |  |");
		System.out.println("|  |  |_____|__|__|_____|__|__|__|_____|__|__|__|__|__|__|  |  |  |");
		System.out.println("|  |  |  |  |     |  |     |  |  ______   |   __    ___________|  |");
		System.out.println("|  |  |___  |  |__    __|  |  |___  |  |  |  |  |  |  |  |  ___|  |");
		System.out.println("|  |_____   |  |  |__|  |  |______  |  |  |     |__|     |______  |");
		System.out.println("|_________________|_________________|________|________|______v.1.3|");
	}

	/**
	 * print help
	 */
	public void printHelp() {

		System.out.println("== GENERAL ==")
		System.out.println("List all Labirynths: list");
		System.out.println("Create Labirynth object: new <name> [width] [height]");
		System.out.println("Print this help: help");
		System.out.println("Quit: exit/quit");
		
		System.out.println("== LABIRYNTH ==")
		System.out.println("Set Begin/End point: <name> set begin/end <x> <y>");
		System.out.println("Generate Labirynth: <name> generate [seed]");
		System.out.println("Solve Labirynth: <name> solve");
		System.out.println("Print Labirynth: <name> print");
		System.out.println("Write/Read to/from file: <name> write/read <bitmap/text/binary> <path>")
			
	}

	public static void main(String[] args) 
	throws IncorrectCoordsException {
		Shell shell = new Shell();
		shell.launch();
	}
}