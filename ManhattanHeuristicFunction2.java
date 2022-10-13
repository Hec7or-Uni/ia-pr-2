package aima.core.environment.eightpuzzle;

import aima.core.search.framework.HeuristicFunction;
import aima.core.util.datastructure.XYLocation;

/**
 * @author Ravi Mohan
 * 
 */
public class ManhattanHeuristicFunction2 implements HeuristicFunction {

	public EightPuzzleBoard obj;
	
	public ManhattanHeuristicFunction2(EightPuzzleBoard finalState) {
		obj = finalState;
	}
	
	public double h(Object state) {
		EightPuzzleBoard board = (EightPuzzleBoard) state;
		int retVal = 0;
		for (int i = 1; i < 9; i++) {
			XYLocation loc = board.getLocationOf(i);
			retVal += evaluateManhattanDistanceOf(i, loc);
		}
		return retVal;

	}

	public int evaluateManhattanDistanceOf(int i, XYLocation loc) {
		int xpos = loc.getXCoOrdinate();
		int ypos = loc.getYCoOrdinate();
		int xposf = obj.getLocationOf(i).getXCoOrdinate();
		int yposf = obj.getLocationOf(i).getYCoOrdinate();
		
		return Math.abs(xpos - xposf) + Math.abs(ypos - yposf);
	}
}