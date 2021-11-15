import java.util.ArrayList;
import java.util.List;

public class ChessBoard {

	private float noofAttempts;
	
	private boolean finalSolution;
	
	private List<Integer> sucessfullAttempts;
	
	
	private List<Integer> failureAttempts;
	
	public float getnoofAttempts() {
		return noofAttempts;
	}
	
	public boolean isfinalSolution() {
		return finalSolution;
	}
	public void setGoalReached(boolean val) {
		this.finalSolution = val;
	}
	
	public void setNoOfAttempts(float val) {
		this.noofAttempts = val;
	}
	
	public List<Integer> getSuccessfulAttempts() {
		if(sucessfullAttempts == null || sucessfullAttempts.isEmpty()) {
			sucessfullAttempts = new ArrayList<>();
		}
		return sucessfullAttempts;
	}
	
	public void setSuccessSteps(List<Integer> sucessfullAttempts) {
		this.sucessfullAttempts = sucessfullAttempts;
	}
	public List<Integer> getFailureAttempts() {
		if(failureAttempts == null || failureAttempts.isEmpty()) {
			failureAttempts = new ArrayList<>();
		}
		return failureAttempts;
	}
	public void setFailureSteps(List<Integer> failureAttempts) {
		this.failureAttempts = failureAttempts;
	}
	

}
