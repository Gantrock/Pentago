package pentago;
/**
 * A Board object which contains the data for the board
 * @author Gantrock
 *
 */
public class Board {
	/**
	 * A Default player token
	 */
	String player = "W";
	/**
	 * A boolean indicating that playerOne has won
	 */
	boolean whiteWin;
	/**
	 * A boolean indicating that playerTwo has won
	 */
	boolean blackWin;
	/**
	 * A 2d array containing the token for each slot. This is used to print the board.
	 */
	String[][] printBoard = new String[6][6];

	/**
	 * The default constructor. Sets both win conditions to false and adds the empty token.
	 * to each slot on the board.
	 */
	public Board() {
		whiteWin = false;
		blackWin = false;
		String baseToken = "-";
		for(int i = 0; i < printBoard.length; i++) {
			for(int j = 0; j < printBoard.length; j++) {
				printBoard[i][j] = baseToken;
			}
		}	
	}
	
	/**
	 * A method that places a new token on the board.
	 * @param token the input defining where the token should be place
	 */
	public void placeToken(String token) {
		String placement = "";
		String rotation = "";
		int space = 0;
		int block = 0;
		int spot = 0;
		int rotBlock = 0;
		char dir = ' ';
		space = token.indexOf(" ");
		placement = token.substring(0, space);
		rotation = token.substring(space+1);
		System.out.println("Place: " + placement + " rotation: " + rotation);
		//space = placement.indexOf("/");
		block = parseQuad(token);//Integer.parseInt(placement.substring(0, space));
		spot = parseSpot(token);//Integer.parseInt(placement.substring(space+1));
		System.out.println("block " + block + " spot " + spot);
		rotBlock = blockToRotate(token); //Character.getNumericValue(rotation.charAt(0));
		dir = direction(token); //rotation.charAt(1);
		System.out.println("rotation " + rotBlock + " R or L: " + dir);
		spot--;
		if(block == 1) {
			if(spot >  5) {
				printBoard[2][spot-6] = player;
				//quad1[2][spot-6] = player;
			} else if(spot > 2) {
				printBoard[1][spot-3] = player;
				//quad1[1][spot-3] = player;
			} else {
				printBoard[0][spot] = player;
				//quad1[0][spot] = player;
			}
		}else if(block == 2) {
			if(spot >  5) {
				printBoard[2][spot-3] = player;
				//quad2[2][spot-6] = player;
			} else if(spot > 2) {
				printBoard[1][spot] = player;
				//quad2[1][spot-3] = player;
			} else {
				printBoard[0][spot+3] = player;
				//quad2[0][spot] = player;
			}
		}else if(block == 3) {
			if(spot >  5) {
				printBoard[5][spot-6] = player;
				//quad3[2][spot-6] = player;
			} else if(spot > 2) {
				printBoard[4][spot-3] = player;
				//quad3[1][spot-3] = player;
			} else {
				printBoard[3][spot] = player;
				//quad3[0][spot] = player;
			}
		}else if(block == 4) {
			if(spot >  5) {
				printBoard[5][spot-3] = player;
				//quad4[2][spot-6] = player;
			} else if(spot > 2) {
				printBoard[4][spot] = player;
				//quad4[1][spot-3] = player;
			} else {
				printBoard[3][spot+3] = player;
				//quad4[0][spot] = player;
			}
		}

		if(dir == 'R') {
			RotateR(rotBlock);
		} else if(dir == 'L') {
			RotateL(rotBlock);
		}
		
		if(player.equals("W")) {
			player = "B";
		} else {
			player = "W";
		}
		
	}
	
	/**
	 * Tests to see if the requested move is valid.
	 * @param token A string denoting a move
	 * @return A boolean indicating whether a boolean is valid
	 */
	public boolean validMove(String token) {
		boolean valid = false;
		int space = 0;
		String placement = "";
		String rotation = "";
		int block = 0;
		int spot = 0;
		int rotBlock = 0;
		char dir = ' ';
		space = token.indexOf(" ");
		placement = token.substring(0, space);
		rotation = token.substring(space+1);
		space = placement.indexOf("/");
		block = Integer.parseInt(placement.substring(0, space));
		spot = Integer.parseInt(placement.substring(space+1));
		rotBlock = Character.getNumericValue(rotation.charAt(0));
		dir = rotation.charAt(1);
		spot--;
		//Update for efficiency after testing!!!!
		//
		//
		//
		if(block == 1) {
			if(spot >  5) {
				if(printBoard[2][spot-6].equals("-")) {
					valid = true;
				}
			} else if(spot > 2) {
				if(printBoard[1][spot-3].equals("-")) {
					valid = true;
				}
			} else {
				if(printBoard[0][spot].equals("-")) {
					valid = true;
				}
			}		
		} else if (block == 2) {
			if(spot >  5) {
				if(printBoard[2][spot-3].equals("-")) {
					valid = true;
				}
			} else if(spot > 2) {
				if(printBoard[1][spot].equals("-")) {
					valid = true;
				}
			} else {
				if(printBoard[0][spot + 3].equals("-")) {
					valid = true;
				}
			}				
		} else if (block == 3) {
			if(spot >  5) {
				if(printBoard[5][spot-6].equals("-")) {
					valid = true;
				}
			} else if(spot > 2) {
				if(printBoard[4][spot-3].equals("-")) {
					valid = true;
				}
			} else {
				if(printBoard[3][spot].equals("-")) {
					valid = true;
				}
			}	
		} else if (block ==4) {
			if(spot >  5) {
				if(printBoard[5][spot-3].equals("-")) {
					valid = true;
				}
			} else if(spot > 2) {
				if(printBoard[4][spot].equals("-")) {
					valid = true;
				}
			} else {
				if(printBoard[3][spot + 3].equals("-")) {
					valid = true;
				}
			}	
		}
		//if the rotation specifies a non existant quad it is not valid
		if(rotBlock < 1 || rotBlock > 4) {
			valid = false;
		}
		//if the direction is neither right or left it is not valid
		if(dir != 'R' && dir != 'L') {
			valid = false;
		}
		return valid;
	}
	
	/**
	 * Tests to see if any player has won the game.
	 * @return A boolean indicating whether the game is over
	 */
	public boolean isWin() {
		boolean isWin = false;
		String tester = "";
		for(int i = 0; i < printBoard.length; i++) {
			for(int j = 0; j < printBoard[0].length; j++) {
				tester = printBoard[i][j];
				if(!tester.equals("-")) {
					if(checkNeighbors(tester, i, j)>4) {
						isWin = true;
						if(tester.equals("W")) {
							whiteWin = true;
						}
						if(tester.equals("B")) {
							blackWin = true;
						}
					}
				}
			}
		}
		return isWin;
	}
	
	/**
	 * Checks the neighbors of the indicated cell
	 * @param token
	 * @param x
	 * @param y
	 * @return The number of like neighbors
	 */
	public int checkNeighbors(String token, int x, int y) {
		int neighbors = 0;
		//check vertical
		for(int h = x; h < printBoard.length; h++) {
			if(printBoard[h][y].equals(token)) {
				neighbors++;
			} else if(neighbors < 5){
				h = printBoard.length + 1;
				neighbors = 0;
			}else {
				h = printBoard.length + 1;
			}
		}
		//check horizontal
		for(int v = y; v < printBoard.length; v++) {
			if(printBoard[x][v].equals(token)) {
				neighbors++;
			} else if(neighbors < 5){
				v = printBoard.length + 1;
				neighbors = 0;
			} else {
				v = printBoard.length + 1;
			}
		}
		//check diagonal
		int aD = y;
		int d = x;
		while(d < printBoard.length && aD < printBoard.length) {
			if(printBoard[d][aD].equals(token)) {
				neighbors++;
			} else {
				d = printBoard.length + 1;
			}
			aD++;
			d++;
		}

		return neighbors;
	}
	
	/**
	 * Creates a text based version of the board for UI purposes
	 */
	/*
	public void fillBig() {
		for(int i = 0; i < printBoard.length; i++) {
			for(int j = 0; j < printBoard[0].length; j++) {
				if(i > 2 && j > 2) {
					printBoard[i][j] = quad4[i-3][j-3];
				} else if(i > 2 && j < 3) {
					printBoard[i][j] = quad3[i-3][j];
				} else if(j > 2) {
					printBoard[i][j] = quad2[i][j-3];
				} else {
					printBoard[i][j] = quad1[i][j];
				}
			}
		}
	}*/
	
	/**
	 * Checks the score of a board setting for the AI
	 * @param unit the AI
	 * @return the score
	 */
	public int checkScore(Legion unit) {
		String tester = "";
		int nums = 0;
		int score = 0;
		for(int i = 0; i < printBoard.length; i++) {
			nums = 0;
			for(int j = 0; j < printBoard[0].length; j++) {
				tester = printBoard[i][j];
				nums = checkNeighbors(tester, i, j);
				if(unit.token.equals(tester)) {
					if(nums == 3) {
						score += 5;
					} else if(nums == 4) {
						score += 20;
					} else if(nums == 5) {
						score += 100;
					}
				} else {
					if(nums == 3) {
						score -= 5;
					} else if(nums == 4) {
						score -= 20;
					} else if(nums == 5) {
						score -= 100;
					}
				}
			}
		}
		return score;
	}
	
	/**
	 * Takes a string and returns it's parsed quad (1-4).
	 * @param token The string presented by the user.
	 * @return the Integer representing the quad being selected.
	 */
	public int parseQuad(String token) {		
		return Integer.parseInt(token.substring(0, token.indexOf('/')));
	}
	/**
	 * Takes a string and returns it's parsed spot within a quad (1-9).
	 * @param token The string presented by the user.
	 * @return The Integer representing the spot being selected.
	 */
	public int parseSpot(String token) {
		int bSlash = token.indexOf('/') + 1;
		return Integer.parseInt(token.substring(bSlash, bSlash+1));
	}
	/**
	 * Takes a string and returns the quad to rotate.
	 * @param token The string presented by the user.
	 * @return The Integer representing the quad being selected.
	 */
	public int blockToRotate(String token) {
		int space = token.indexOf(' ') + 1;
		return Integer.parseInt(token.substring(space, space + 1));
	}
	/**
	 * Takes a string and returns which direction to rotate the chosen quad.
	 * @param token The string presented by the user.
	 * @return The Character representing which direction to rotate the form.
	 */
	public char direction(String token) {
		return token.charAt(token.indexOf(' ') + 2);
	}
	
	/**
	 * Repositions the elements of a quad to simulate being rotated Right once
	 * @param block
	 */
	public void RotateR(int block) {
		String temp = "";
		int startX = 0;
		int maxX = 2;
		int startY = 0;
		int maxY = 2;

		if (block == 2) {
			startY = 3;
			maxY = 5;
		} else if (block == 3) {
			startX = 3;
			maxX = 5;
		} else if (block == 4) {
			startX = 3;
			maxX = 5;
			startY = 3;
			maxY = 5;
		}

			for(int j = 0; j < 2; j++) {
				temp = printBoard[startX][maxY - j]; //1
				printBoard[startX][maxY - j] = printBoard[startX + j][startY]; //2
				printBoard[startX + j][startY] = printBoard[maxX][startY + j]; //3
				printBoard[maxX][startY + j] = printBoard[maxX-j][maxY]; //4
				printBoard[maxX-j][maxY] = temp; //5
			}
	}
	
	/**
	 * Repositions the elements of a quad to simulate being rotated Left once
	 * @param block
	 */
	public void RotateL(int block) {
		String temp = "";
		int startX = 0;
		int maxX = 2;
		int startY = 0;
		int maxY = 2;

		if (block == 2) {
			startY = 3;
			maxY = 5;
		} else if (block == 3) {
			startX = 3;
			maxX = 5;
		} else if (block == 4) {
			startX = 3;
			maxX = 5;
			startY = 3;
			maxY = 5;
		}

			for(int j = 0; j < 2; j++) {
				temp = printBoard[startX][startY + j];
				printBoard[startX][startY + j] = printBoard[startX + j][maxY];
				printBoard[startX + j][maxY] = printBoard[maxX][maxY-j];
				printBoard[maxX][maxY-j] = printBoard[maxX-j][startY];
				printBoard[maxX-j][startY] = temp;
			}
	}
	
	/**
	 * Prints the entire board
	 */
	public String toString() {
		String board = "";
		String border = "+---------+---------+\n";
		board += border;
		for(int i = 0; i < printBoard.length; i++) {
			for(int j = 0; j < printBoard[0].length; j++) {
				if(j == 0 || j == 3) {
					board += "|";
				} 
				board += " " + printBoard[i][j] + " ";
				if(j == 5) {
					board += "|\n";
				}
				if((i == 2 && j == 5) || (i == 5 && j == 5)) {
					board += border;
				}
			}
		}
		return board;
	}
}
