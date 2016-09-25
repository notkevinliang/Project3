/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Git URL:
 * Fall 2016
 */


package assignment3;
import java.util.*;
import java.io.*;

public class Main {
	
	// static variables and constants only here.
	static Set<String> inputDictionary;
	
	public static void main(String[] args) throws Exception {
		
		Scanner kb;	// input Scanner for commands
		PrintStream ps;	// output file
		// If arguments are specified, read/write from/to files instead of Std IO.
		if (args.length != 0) {
			kb = new Scanner(new File(args[0]));
			ps = new PrintStream(new File(args[1]));
			System.setOut(ps);			// redirect output to ps
		} else {
			kb = new Scanner(System.in);// default from Stdin
			ps = System.out;			// default to Stdout
		}
		initialize();
		ArrayList<String> userInput = parse(kb);
		ArrayList<String> ladder = getWordLadderDFS(userInput.get(0), userInput.get(1));
		printLadder(ladder);
		// TODO methods to read in words, output ladder
	}
	
	public static void initialize() {
		// initialize your static variables or constants here.
		// We will call this method before running our JUNIT tests.  So call it 
		// only once at the start of main.
		 inputDictionary = makeDictionary();
	}
	
	/**
	 * @param keyboard Scanner connected to System.in
	 * @return ArrayList of 2 Strings containing start word and end word. 
	 * If command is /quit, return empty ArrayList. 
	 */
	public static ArrayList<String> parse(Scanner keyboard) {
		String inputLine;
		ArrayList<String> keyboardInput = new ArrayList<String>();
		
		inputLine = keyboard.nextLine(); // read user's input
		
		if (inputLine.contains("/quit")){ // if user enters /quit, return empty ArrayList
			return keyboardInput;
		}
		
		String [] inputList = inputLine.split("\\s"); // split the user inputs
		
		for(int index = 0; index < inputList.length; index++){ // add them to the array list
			keyboardInput.add(inputList[index].toUpperCase());
		}
		
		return keyboardInput;
	}
	
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		
		// Return empty list if no ladder.
		// TODO some code
		ArrayList<String> ladderResult =  new ArrayList<String>();
		if (start.equals(end)){
			ladderResult.add(end);
			return ladderResult;
		}
		
		ladderResult.add(start);
		inputDictionary.remove(start);
		
		char[] charArray = start.toCharArray();
		
		int i = 0;
		while (i < charArray.length){
			for(char ch = 'A'; ch <= 'Z'; ch++){
				char tempChar = charArray[i];
				if(charArray[i] != ch){
					charArray[i] = ch;
				}
				
				String potentialWord = new String(charArray);
				if (inputDictionary.contains(potentialWord)){
					inputDictionary.remove(potentialWord); 
					ArrayList<String> newLadder = getWordLadderDFS(potentialWord, end);
					if (newLadder.contains(end)){
						ladderResult.addAll(newLadder);
						return ladderResult;
					}
				}
				
				charArray[i] = tempChar;
			}
			i += 1;
		}
		
		// TODO more code
		
		return ladderResult; // return just a simple ladder with just start if no path is found
	}
	
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
		
		// TODO some code
		Set<String> dict = makeDictionary(); // define a dictionary
		ArrayList<String> ladderResult =  new ArrayList<String>();
		
		LinkedList<ArrayList<String>> wordQueue = new LinkedList<ArrayList<String>>(); // FIFO queue 
		ArrayList<String> initialLadder = new ArrayList<String>();
		int i = 0;
		
		dict.remove(start); // remove the starting word to prevent loop in solution
		initialLadder.add(start); // start with the starting word
		wordQueue.add(initialLadder);
		
		while(!wordQueue.isEmpty()){
			ArrayList<String> topLadder = (ArrayList<String>)wordQueue.remove();
			
			String lastString = topLadder.get(topLadder.size() - 1); // get the latest string
			
			if (lastString.equals(end)){
				return topLadder;
			}
			
			char[] charArray = lastString.toCharArray();
			i = 0;
			
			while(i < charArray.length){
				for(char ch = 'A'; ch <= 'Z'; ch++){
					char tempChar = charArray[i];
					
					if(charArray[i] != ch){
						charArray[i] = ch;
					}
					
					String potentialWord = new String(charArray);
					if (dict.contains(potentialWord)){
						ArrayList<String> newLadder = new ArrayList<String>();
						newLadder.addAll(topLadder);
						newLadder.add(potentialWord);
						wordQueue.add(newLadder);
						
						dict.remove(potentialWord); // remove the word from dictionary to 'indicate' that they have been reached
					}
					
					charArray[i] = tempChar; // revert back to the original char array
				}
				i += 1;
			}
			
		}
		return ladderResult; // return an empty list if there is no path from start to end
	}
    
	public static Set<String>  makeDictionary () {
		Set<String> words = new HashSet<String>();
		Scanner infile = null;
		try {
			infile = new Scanner (new File("dict.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Dictionary File not Found!");
			e.printStackTrace();
			System.exit(1);
		}
		while (infile.hasNext()) {
			words.add(infile.next().toUpperCase());
		}
		return words;
	}
	
	public static void printLadder(ArrayList<String> ladder) {
		
	}
	// TODO
	// Other private static methods here
}
