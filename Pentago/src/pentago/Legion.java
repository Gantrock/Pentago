package pentago;

import java.util.PriorityQueue;
/*Legion is currently being revamped an represents a work in progress*/
public class Legion {
	String[] moves = {"1/1 1R", "1/2 1R", "1/3 1R", "1/4 1R", "1/5 1R", "1/6 1R", "1/7 1R", "1/8 1R", "1/9 1R" 
+ "1/1 1L", "1/2 1L", "1/3 1L", "1/4 1L", "1/5 1L", "1/6 1L", "1/7 1L", "1/8 1L", "1/9 1L"
+ "2/1 1R", "2/2 1R", "2/3 1R", "2/4 1R", "2/5 1R", "2/6 1R", "2/7 1R", "2/8 1R", "2/9 1R"
+ "2/1 1L", "2/2 1L", "2/3 1L", "2/4 1L", "2/5 1L", "2/6 1L", "2/7 1L", "2/8 1L", "2/9 1L"
+ "3/1 1R", "3/2 1R", "3/3 1R", "3/4 1R", "3/5 1R", "3/6 1R", "3/7 1R", "3/8 1R", "3/9 1R"
+ "3/1 1L", "3/2 1L", "3/3 1L", "3/4 1L", "3/5 1L", "3/6 1L", "3/7 1L", "3/8 1L", "3/9 1L"
+ "4/1 1R", "4/2 1R", "4/3 1R", "4/4 1R", "4/5 1R", "4/6 1R", "4/7 1R", "4/8 1R", "4/9 1R"
+ "4/1 1L", "4/2 1L", "4/3 1L", "4/4 1L", "4/5 1L", "4/6 1L", "4/7 1L", "4/8 1L", "4/9 1L"};
	String token = "";
	Board state;
	Root myRoot;
	public Legion(String color, Board theState) {
		token = color;
		state = theState;
	}
	
	public String findBest() {
		String bestMove = "";
		myRoot = new Root(0, 2, moves, state);
		bestMove = myRoot.findBest();
		return bestMove;
	}
	
	private class Root{
		int depth;
		int score;
		int level;
		Board state;
		String[] moves;
		String theMove;
		PriorityQueue<MaxNode> branching = new PriorityQueue<MaxNode>();
		
		private Root(int theScore, int howDeep, String[] theMoves, Board theState) {
			depth = 0;
			score= theScore;
			level = howDeep;
			state = theState;
			moves = theMoves.clone();
		}
		
		private String findBest() {
			String bestMove = "";
			//For each possible move create a new ranch node
			for(String motion: moves) {
				addBranch(motion);
			} 
			bestMove = branching.poll().theMove;
			return bestMove;
		}
		
		private void addBranch(String move) {
			branching.add(new MaxNode(score, depth, level, state, moves, move));
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
		Board state;
		String[] moves;
		String theMove;
		PriorityQueue<MaxNode> branch = new PriorityQueue<MaxNode>();
		
		private MinNode(int theScore, int theDepth, int howDeep, String[] theMoves, Board theState, String myMove) {
			depth = theDepth + 1;
			level = howDeep;
			state = theState;
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
					addMax(motion);
				}
				max = branch.poll().score;
			} else {
				//find score
				score = checkScore(state);
			}
			return max;
		}
		
		private void addMax(String move) {
			branch.add(new MaxNode(score, depth, level, state, moves, move));
		}
		
		public int checkScore(Board state) {
			String tester = "";
			int nums = 0;
			int score = 0;
			for(int i = 0; i < state.printBoard.length; i++) {
				nums = 0;
				for(int j = 0; j < state.printBoard[0].length; j++) {
					/*if(i > 2 && j > 2) {
						tester = state.quad4[i-3][j-3];
					} else if(i > 2 && j < 3) {
						tester = state.quad3[i-3][j];
					} else if(j > 2) {
						tester = state.quad2[i][j-3];
					} else {
						tester = state.quad1[i][j];
					}*/
					nums = state.checkNeighbors(tester, i, j);
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
		Board state;
		String[] moves;
		String theMove;

		
		PriorityQueue<MinNode> branch = new PriorityQueue<MinNode>();
		
		private MaxNode(int theScore, int theDepth, int howDeep, Board theState, String[]theMoves, String myMove) {
			depth = theDepth + 1;
			level = howDeep;
			state = theState;
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
					addMin(motion);
				}
				min = branch.poll().score;
			} else {
				//find score
				score = checkScore(state);
			}
			return min;
		}
		
		private void addMin(String move) {
			branch.add(new MinNode(score, depth, level, moves, state, move));
		}
		
		public int checkScore(Board state) {
			String tester = "";
			int nums = 0;
			int score = 0;
			for(int i = 0; i < state.printBoard.length; i++) {
				nums = 0;
				for(int j = 0; j < state.printBoard[0].length; j++) {
					/*if(i > 2 && j > 2) {
						tester = state.quad4[i-3][j-3];
					} else if(i > 2 && j < 3) {
						tester = state.quad3[i-3][j];
					} else if(j > 2) {
						tester = state.quad2[i][j-3];
					} else {
						tester = state.quad1[i][j];
					}*/
					nums = state.checkNeighbors(tester, i, j);
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
