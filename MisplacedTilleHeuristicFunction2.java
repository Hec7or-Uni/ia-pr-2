package aima.core.environment.eightpuzzle;

import aima.core.search.framework.HeuristicFunction;
import aima.core.util.datastructure.XYLocation;

/**
 * @author Ravi Mohan
 * 
 */
public class MisplacedTilleHeuristicFunction2 implements HeuristicFunction {

	public EightPuzzleBoard obj;
	
	public MisplacedTilleHeuristicFunction2(EightPuzzleBoard finalState) {
		obj = finalState;
	}
	
	public double h(Object state) {
		EightPuzzleBoard board = (EightPuzzleBoard) state;
		return getNumberOfMisplacedTiles(board);
	}

	private int getNumberOfMisplacedTiles(EightPuzzleBoard board) {
		int numberOfMisplacedTiles = 0;
		for (int i = 0; i < 9; i++) {
			if (!(board.getLocationOf(i).equals(obj.getLocationOf(i)))) {
				numberOfMisplacedTiles++;
			}
		}
		// Subtract the gap position from the # of misplaced tiles
		// as its not actually a tile (see issue 73).
		if (numberOfMisplacedTiles > 0) {
			numberOfMisplacedTiles--;
		}
		return numberOfMisplacedTiles;
	}
}