package pentago;

import java.util.PriorityQueue;
/*Legion is currently being revamped an represents a work in progress*/
public class Legion {
	/**
	 * An array containing every possible move
	 */
	String[] moves = {"1/1 1R", "1/2 1R", "1/3 1R", "1/4 1R", "1/5 1R", "1/6 1R", "1/7 1R", "1/8 1R", "1/9 1R" 
+ "1/1 1L", "1/2 1L", "1/3 1L", "1/4 1L", "1/5 1L", "1/6 1L", "1/7 1L", "1/8 1L", "1/9 1L"
+ "2/1 1R", "2/2 1R", "2/3 1R", "2/4 1R", "2/5 1R", "2/6 1R", "2/7 1R", "2/8 1R", "2/9 1R"
+ "2/1 1L", "2/2 1L", "2/3 1L", "2/4 1L", "2/5 1L", "2/6 1L", "2/7 1L", "2/8 1L", "2/9 1L"
+ "3/1 1R", "3/2 1R", "3/3 1R", "3/4 1R", "3/5 1R", "3/6 1R", "3/7 1R", "3/8 1R", "3/9 1R"
+ "3/1 1L", "3/2 1L", "3/3 1L", "3/4 1L", "3/5 1L", "3/6 1L", "3/7 1L", "3/8 1L", "3/9 1L"
+ "4/1 1R", "4/2 1R", "4/3 1R", "4/4 1R", "4/5 1R", "4/6 1R", "4/7 1R", "4/8 1R", "4/9 1R"
+ "4/1 1L", "4/2 1L", "4/3 1L", "4/4 1L", "4/5 1L", "4/6 1L", "4/7 1L", "4/8 1L", "4/9 1L"};
	/**
	 * The token belonging to the AI
	 */
	String token = "";
	/**
	 * A copy of the board for Legion to manipulate in oder to make decisions
	 */
	Board state;
	/**
	 * The root node for the minmax tree
	 */
	Root myRoot;
	
	/**
	 * A two parameter constructor for the AI
	 * @param color the token Legion wil place on the board
	 * @param theState the board for Legion to manipulate
	 */
	public Legion(String color, Board theState) {
		token = color;
		state = theState;
	}
	
	/**
	 * Begins the process of making a minmax tree and finds the best possible move
	 * @return
	 */
	public String findBest() {
		String bestMove = "";
		myRoot = new Root(0, 2, moves, state);
		bestMove = myRoot.findBest();
		return bestMove;
	}
	/*
	 * Algorithm plan:
	 * If not at depth create children
	 * If at depth return score
	 */
	/**
	 * The generic root for the minmax tree
	 * @author Gantrock
	 *
	 */
	private class Root{
		/**
		 * The "current" depth
		 */
		int depth;
		/**
		 * The score of a node
		 */
		int score;
		/**
		 * The maximum depth to create children nodes to.
		 */
		int level;
		/**
		 * A copy of the board for manipulation
		 */
		Board myState;
		/** 
		 * An array containing all of the possible moves.
		 */
		String[] moves;
		String theMove;
		/**
		 * A priority queue of Maxnodes used to determine the highest scoring node
		 */
		PriorityQueue<MaxNode> branching = new PriorityQueue<MaxNode>();
		
		/**
		 * A four parameter constructor for a generic minmax root
		 */
		private Root(int theScore, int howDeep, String[] theMoves, Board theState) {
			depth = 0;
			score= theScore;
			level = howDeep;
			myState = theState;
			moves = theMoves.clone();
		}
		
		/**
		 * Recursive function that creates new nodes for every possible move until we reach
		 * the depth. When the depth is reached the nodes are scored and the best nodes move is chosen.
		 * @return A string representing the best move to make.
		 */
		private String findBest() {
			String bestMove = "";
			//For each possible move create a new branch node
			for(String motion: moves) {
				if(myState.validMove(motion)){
					addBranch(motion);
				}
			} 
			bestMove = branching.poll().theMove;
			return bestMove;
		}
		
		/**
		 * Creates all of the children.
		 * @param move
		 */
		private void addBranch(String move) {
			branching.add(new MaxNode(score, depth, level, myState, moves, move));
		}
		

	}
	
	/**
	 *  MinNode is a Node object that finds the minimum scored child node
	 * @author Gantrock
	 *
	 */
	private class MinNode implements Comparable<MinNode> {
		int depth;
		int score;
		int level;
		Board minState;
		String[] moves;
		String theMove;
		PriorityQueue<MaxNode> branch = new PriorityQueue<MaxNode>();
		
		private MinNode(int theScore, int theDepth, int howDeep, String[] theMoves, Board theState, String myMove) {
			depth = theDepth + 1;
			level = howDeep;
			minState = theState;
			minState.placeToken(myMove);
			moves = theMoves.clone();
			theMove = myMove;
			score = findMax();
		}
		
		private int findMax() {
			int max = 0;
			//if the target depth is not reached add branches, if not find the score
			if(level > depth) {
				//for each possible move create a new MinNode
				for(String motion: moves) {
					if(minState.validMove(motion)) {
						addMax(motion);
					}
				}
				max = branch.poll().score;
			} else {
				//find score
				score = checkScore(minState);
			}
			return max;
		}
		
		private void addMax(String move) {
			branch.add(new MaxNode(score, depth, level, minState, moves, move));
		}
		
		//This should be rewritten to actually work.
		public int checkScore(Board theState) {
			String tester = "";
			int nums = 0;
			int score = 0;
			for(int i = 0; i < theState.printBoard.length; i++) {
				nums = 0;
				for(int j = 0; j < theState.printBoard[0].length; j++) {
					/*if(i > 2 && j > 2) {
						tester = state.quad4[i-3][j-3];
					} else if(i > 2 && j < 3) {
						tester = state.quad3[i-3][j];
					} else if(j > 2) {
						tester = state.quad2[i][j-3];
					} else {
						tester = state.quad1[i][j];
					}*/
					nums = theState.checkNeighbors(tester, i, j);
					if(token.equals(tester)) {
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
		
		@Override
		public int compareTo(MinNode theOther) {			
			return score - theOther.score;
		}
		
	}

	/**
	 * MaxNode is a Node Object that finds the maximum scored child node
	 * @author Gantrock
	 *
	 */
	private class MaxNode implements Comparable<MaxNode> {
		int depth;
		int score;
		int level;
		Board maxState;
		String[] moves;
		String theMove;

		
		PriorityQueue<MinNode> branch = new PriorityQueue<MinNode>();
		
		private MaxNode(int theScore, int theDepth, int howDeep, Board theState, String[]theMoves, String myMove) {
			depth = theDepth + 1;
			level = howDeep;
			maxState = theState;
			maxState.placeToken(myMove);
			moves = theMoves.clone();
			theMove = myMove;
			score = findMin();
		}
		
		private int findMin() {
			int min = 0;
			//if the target depth is not reached add branches, if not find the score
			if(level > depth) {
				//for each possible move create a new MinNode
				for(String motion: moves) {
					if(maxState.validMove(motion)){
						addMin(motion);
					}
				}
				min = branch.poll().score;
			} else {
				//find score
				score = checkScore(maxState);
			}
			return min;
		}
		
		private void addMin(String move) {
			branch.add(new MinNode(score, depth, level, moves, maxState, move));
		}
		
		//This should be rewritten so that it actually works.
		public int checkScore(Board theState) {
			String tester = "";
			int nums = 0;
			int score = 0;
			for(int i = 0; i < theState.printBoard.length; i++) {
				nums = 0;
				for(int j = 0; j < theState.printBoard[0].length; j++) {
					/*if(i > 2 && j > 2) {
						tester = state.quad4[i-3][j-3];
					} else if(i > 2 && j < 3) {
						tester = state.quad3[i-3][j];
					} else if(j > 2) {
						tester = state.quad2[i][j-3];
					} else {
						tester = state.quad1[i][j];
					}*/
					nums = maxState.checkNeighbors(tester, i, j);
					if(token.equals(tester)) {
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
		
		@Override
		public int compareTo(MaxNode theOther) {
			return theOther.score - score;
		}
		
	}
}
