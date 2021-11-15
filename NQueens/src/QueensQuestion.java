import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class QueensQuestion {

	public static void main(String[] args) {
		System.out.print("Enter the size of chessboard:");
		Scanner sc = new Scanner(System.in);
		int chessboardsize = Integer.parseInt(sc.nextLine());
		sc.close();
        
		
		//steepestAscentHillClimbingMain(chessboardsize);
		stochasticHillClimbing(chessboardsize);
		
	}

	private static void stochasticHillClimbing(int chessboardsize) {
		
		ChessBoard state = new ChessBoard();
		int[] chessboardarray = new int[chessboardsize];
		state.setNoOfAttempts(0.0f);
		state.setGoalReached(false);
		
		
		for(int i=0;i<1000;i++) {
			chessboardarray=generateArray(chessboardsize);
			
			ComputeStochasticSteps(chessboardarray, state,i);
			
			if (state.isfinalSolution()) { // if solution is reached in the first try since we are checking average success rate and failure rates				
				state.setNoOfAttempts(state.getnoofAttempts() + 1);
				state.setGoalReached(false);
			}
			
		}
		float successRate = (state.getnoofAttempts() / 1000) * 100;
		System.out.println("\nStochastic Hill Climbing Results: ");
		System.out.println("Success Rate:: " + successRate + " %");
		System.out.println("Failure Rate:: " + (100.0f - successRate) + " %");
		System.out.println("Success Average: "
				+ String.format("%.00f", state.getSuccessfulAttempts().stream().mapToDouble(a -> a).average().getAsDouble()));
		System.out.println("Failure Average: "
				+ String.format("%.00f", state.getFailureAttempts().stream().mapToDouble(a -> a).average().getAsDouble()));
		
		System.out.print("The no of steps succesfull:");
		var list= state.getSuccessfulAttempts();
		System.out.println(Arrays.toString(list.toArray()));
		System.out.print("The no of steps failed:");
		var list1= state.getFailureAttempts();
		System.out.println(Arrays.toString(list1.toArray()));
		
		
		
		
	}

	private static int[] ComputeStochasticSteps(int[] chessboardarray, ChessBoard state, int i) {
		// TODO Auto-generated method stub
int noofThreats = calculateThreats(chessboardarray);
		
		int minThrealLevel = 0; //All queens are placed with zero threats.(It is assumed)
		Integer attempts = 0;
		
		if(i<4) {
			showChessBoard(chessboardarray);
		}

		while (chessboardarray != null && minThrealLevel < noofThreats) {

			int[][] chessBoardMoves = calculatePotentialMoves(chessboardarray);
			minThrealLevel = Arrays.stream(chessBoardMoves).flatMapToInt(Arrays::stream).min().getAsInt();
			if (minThrealLevel < noofThreats) {

				chessboardarray = traverseToStochasticNeighbor(minThrealLevel, chessboardarray, chessBoardMoves,i);
				
				if(chessboardarray != null) {
				attempts++;
				noofThreats = calculateThreats(chessboardarray);
				chessBoardMoves = calculatePotentialMoves(chessboardarray);
				minThrealLevel = Arrays.stream(chessBoardMoves).flatMapToInt(Arrays::stream).min().getAsInt();
				}
			}
		}
		if (noofThreats == 0) {
			state.setGoalReached(true);
			state.getSuccessfulAttempts().add(attempts);
		} else {
			state.getFailureAttempts().add(attempts);
		}
		return chessboardarray;
	}

	@SuppressWarnings("unused")
	private static int[] traverseToStochasticNeighbor(int minThrealLevel, int[] chessboardarray,
			int[][] chessBoardMoves, int count) {
        
		List<int[]> traversals = new ArrayList<>();
		
		int[] cb = new int[chessboardarray.length];
		cb = chessboardarray.clone();
		
		for(int i=0;i<chessBoardMoves.length;i++) {
		
			int id_x = new Random().nextInt(chessBoardMoves.length);
			int id_y = new Random().nextInt(chessBoardMoves[id_x].length);
			int pos = chessBoardMoves[id_x][id_y];
		    
			if(minThrealLevel == pos) {
				int[] position = {id_x,id_y};
				traversals.add(position);
				
			}
		}
		    
		if(traversals.isEmpty()) {
			return cb;
		}
		
		 
			int[] position = traversals.get(new Random().nextInt(traversals.size()));
		    cb[position[0]] = position[1];
		
		
		//checking for duplicate current state in the new chessboard and previous one
		while (isArrayEqual(cb, chessboardarray)) {
			if (traversals.size() == 1) {
				return null;
			} else {
				traversals.remove(traversals.indexOf(position));
				position = traversals.get(new Random().nextInt(traversals.size()));
				cb[position[0]] = position[1];
			}
		}
		
		if(count<4) {
			showChessBoard(cb);
		}
		
		return cb;
	}

	private static void steepestAscentHillClimbingMain(int chessboardsize) {
		
		ChessBoard state = new ChessBoard();
		int[] chessboardarray = new int[chessboardsize];
		state.setNoOfAttempts(0.0f);
		state.setGoalReached(false);
		
		
		for(int i=0;i<1000;i++) {
			chessboardarray=generateArray(chessboardsize);
			
			ComputeSteps(chessboardarray, state,i);
			
			if (state.isfinalSolution()) { // if solution is reached in the first try since we are checking average success rate and failure rates
				state.setNoOfAttempts(state.getnoofAttempts() + 1);
				state.setGoalReached(false);
			}
			
		}
		float successRate = (state.getnoofAttempts() / 1000) * 100;
		System.out.println("\nSteepest-ascent Hill Climbing Results: ");
		System.out.println("Success Rate:: " + successRate + " %");
		System.out.println("Failure Rate:: " + (100.0f - successRate) + " %");
		System.out.println("Success Average: "
				+ String.format("%.00f", state.getSuccessfulAttempts().stream().mapToDouble(a -> a).average().getAsDouble()));
		System.out.println("Failure Average: "
				+ String.format("%.00f", state.getFailureAttempts().stream().mapToDouble(a -> a).average().getAsDouble()));
		System.out.print("The no of steps succesfull:");
		var list= state.getSuccessfulAttempts();
		System.out.println(Arrays.toString(list.toArray()));
		System.out.print("The no of steps failed:");
		var list1= state.getFailureAttempts();
		System.out.println(Arrays.toString(list1.toArray()));
		
		
		
		
		
		
		
	}
	
	
	
	private static void showChessBoard(int[] chessboardarray) {
		System.out.println(" ");
		
		for (int i = 0; i < chessboardarray.length; i++) {
			System.out.println(" ");
			for (int j = 0; j < chessboardarray.length; j++) {
				if (i == chessboardarray[j]) {
					System.out.print("Q ");
				} else {
					System.out.print("- ");
				}
			}
		}
	}

	private static int[] ComputeSteps(int[] chessboardarray, ChessBoard state, int count) {
		int noofThreats = calculateThreats(chessboardarray);
		
		int minThrealLevel = 0; //All queens are placed with zero threats.(It is assumed)
		Integer attempts = 0;
		
		if(count<4) {
			showChessBoard(chessboardarray);
		}

		while (chessboardarray != null && minThrealLevel < noofThreats) {

			int[][] chessBoardMoves = calculatePotentialMoves(chessboardarray);
			minThrealLevel = Arrays.stream(chessBoardMoves).flatMapToInt(Arrays::stream).min().getAsInt();
			if (minThrealLevel < noofThreats) {

				chessboardarray = traverseToNeighbor(minThrealLevel, chessboardarray, chessBoardMoves,count);
				
				if(chessboardarray != null) {
				attempts++;
				noofThreats = calculateThreats(chessboardarray);
				chessBoardMoves = calculatePotentialMoves(chessboardarray);
				minThrealLevel = Arrays.stream(chessBoardMoves).flatMapToInt(Arrays::stream).min().getAsInt();
				}
			}
		}
		if (noofThreats == 0) {
			state.setGoalReached(true);
			state.getSuccessfulAttempts().add(attempts);
		} else {
			state.getFailureAttempts().add(attempts);
		}
		return chessboardarray;
		
	}

	private static int[] traverseToNeighbor(int minThrealLevel, int[] chessboardarray, int[][] chessBoardMoves, int count) {
       
		List<int[]> traversals = new ArrayList<>();
		
		int[] cb = new int[chessboardarray.length];
		cb = chessboardarray.clone();
		
		for (int i = 0; i < chessBoardMoves.length; i++) {
			for (int j = 0; j < chessBoardMoves.length; j++) {
				if (minThrealLevel == chessBoardMoves[i][j] && chessboardarray[j]!=i) {
					int[] position = {i, j};
					traversals.add(position);
				}
			}
		}

		// Randomly choosing value for neighbor from best heuristic
		int[] position = traversals.get(new Random().nextInt(traversals.size()));
		cb[position[0]] = position[1];
		
		//checking for duplicate current state in the new chessboard and previous one
		while (isArrayEqual(cb, chessboardarray)) {
			if (traversals.size() == 1) {
				return null;
			} else {
				traversals.remove(traversals.indexOf(position));
				position = traversals.get(new Random().nextInt(traversals.size()));
				cb[position[0]] = position[1];
			}
		}
		
		if(count<4) {
			showChessBoard(cb);
		}
		
		return cb;
	}
	
	
	private static boolean isArrayEqual(int[] cb, int[] currentBoard) {
		boolean equal = true;
		for (int i = 0; i < currentBoard.length; i++) {
			if(cb[i] != currentBoard[i]) {
				equal = false;
			}
		}
		return equal;
	}


	private static int[][] calculatePotentialMoves(int[] chessboardarray) {
		
		int[][] heuristicBoard = new int[chessboardarray.length][chessboardarray.length];
		for (int i = 0; i < chessboardarray.length; i++) {
			int[] newBoard = chessboardarray.clone();
			for (int j = 0; j < chessboardarray.length; j++) {
				
				newBoard[i] = j;
				heuristicBoard[i][j] = calculateThreats(newBoard);
				
				//assigning maximum value to current queen positions, to avoid repeated state generation
				if(i == chessboardarray[j]) {
					heuristicBoard[i][j] = 1000;
				}
			}
		}
		return heuristicBoard;
	}

	private static int calculateThreats(int[] chessboardqueenposition) {
		int threatCount = 0; // Should be zero for considering no queen to be attacking each other
		
		
		for(int i=0;i<chessboardqueenposition.length;i++) {
			int currentQueenPos=chessboardqueenposition[i];
			for(int j=i+1;j<chessboardqueenposition.length;j++) {
				//check for diagonal
				int x_dist=Math.abs(j-i);
				int y_dist=Math.abs(currentQueenPos-chessboardqueenposition[j]);
				
				if(currentQueenPos==chessboardqueenposition[j] || x_dist==y_dist) {
					threatCount++;
				}
			}
			
		}
		return threatCount;
		
		
	}

	/**Utility method for generating randomized array for placing queens in the state of n*n board size
	 * @param n
	 * @return
	 */
	private static int[] generateArray(int chessBoardSize) {
		return new Random().ints(chessBoardSize, 0, chessBoardSize - 1).toArray();
	}

}
