package pentago;

import java.util.Random;
import java.util.Scanner;

public class PentagoMain {

	public static void main(String[] args) {
		/**
		 * choice represents the player input
		 */
		String choice = "";
		/**
		 * color represents which letter to set for the second player
		 */
		String color = "";
		Scanner key = new Scanner(System.in);
		Board theBoard = new Board();
		Random dice = new Random();
		/**
		 * playerTurn keeps track of whether it is player One or player Two/AI's turn
		 */
		boolean playerTurn = true;
		/**
		 * aiPlaying tells the program whether an AI is desired
		 */
		String aiPlaying = "F";
		//Determines whether player One is W or B
		if(dice.nextInt(2) == 0) {
			color = "W";
		} else {
			color = "B";
		}
		Legion bot = new Legion(color, theBoard);
		//Ask the user if they want to play against an AI
		System.out.println("Play with an Ai? (Y/N) ");
		aiPlaying = key.nextLine();
		//error checking
			while(!aiPlaying.equals("Y") && !aiPlaying.equals("N")) {
				System.out.println("Please select valid input? (Y/N) ");
				aiPlaying = key.nextLine();	
			}
			//Randomly determines if the player one goes first or second
		if(aiPlaying.equals("Y")) {
			if(dice.nextInt(2) == 0) {
				playerTurn = false;
			} else {
				playerTurn = true;
			}
		}
		System.out.println(theBoard);
		/*
		 * While the game isn't won this while will continue
		 */
		while(!theBoard.isWin()) {
			if(playerTurn){
				System.out.println("For input guide type H");
				System.out.print("Please type move: ");
				choice = key.nextLine();
				while(choice.length() < 6) {
					if(choice.equalsIgnoreCase("H")) {
						help();
					} else{
						System.out.print("Please make a valid move: ");	
					}

					choice = key.nextLine();
				}
				while(!theBoard.validMove(choice)) {
					System.out.print("Please make a valid move: ");
					choice = key.nextLine();					
				}
					theBoard.placeToken(choice);
					System.out.println(theBoard);
					if(aiPlaying.equals("Y")) {
						playerTurn = false;
					}	
			}else {
				//ai things here
				choice = bot.findBest();
				System.out.println("Legion thinks: " + choice);
				theBoard.placeToken(choice);
				System.out.println(theBoard);
				playerTurn = true;
		}
			
		}
		if(theBoard.whiteWin && theBoard.blackWin) {
			System.out.println("tie, noone wins :(");
		} else if (theBoard.blackWin) {
			System.out.println("Black wins!");
		} else {
			System.out.println("White wins!");
		}
		System.out.println(theBoard);
		key.close();
	}
	
	public static void help() {
			System.out.println("Quadrant(1-4)/Section(1-9) Quadrant(1-4)Rotation(R or L)");
			System.out.println("Example 1/1 1R would place a token at spot 1 in quad 1 and rotate quad 1 right");
			System.out.println("   Quad1      Quad2   ");
			System.out.println("  1  2  3    1  2  3  ");
			System.out.println("  4  5  6    4  5  6  ");
			System.out.println("  7  8  9    7  8  9  ");
			System.out.println("  1  2  3    1  2  3  ");
			System.out.println("  4  5  6    4  5  6  ");
			System.out.println("  7  8  9    7  8  9  ");
			System.out.println("   Quad3      Quad4   ");
			System.out.println("Enter valid move now: ");
	}

}
