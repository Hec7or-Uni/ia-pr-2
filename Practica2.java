package aima.gui.demo.search;

import java.text.DecimalFormat;

import aima.core.environment.eightpuzzle.EightPuzzleBoard;
import aima.core.environment.eightpuzzle.EightPuzzleFunctionFactory;
import aima.core.environment.eightpuzzle.EightPuzzleGoalTest;
import aima.core.environment.eightpuzzle.ManhattanHeuristicFunction;
import aima.core.environment.eightpuzzle.ManhattanHeuristicFunction2;
import aima.core.environment.eightpuzzle.MisplacedTilleHeuristicFunction2;
import aima.core.search.framework.GraphSearch;
import aima.core.search.framework.Problem;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.informed.AStarSearch;
import aima.core.search.uninformed.*;
import aima.core.util.math.Biseccion;

/*
 * @author Héctor Toral
 * 
 */

public class Practica2 {
	static EightPuzzleBoard boardIni, boardFin;
	
	static int    BFS_N = 0, IDS_N = 0, AH1_N = 0, AH2_N = 0;   // Nodos generados
	static double BFS_B = 0, IDS_B = 0, AH1_B = 0, AH2_B = 0;	// Factor de ramificación
	
	static int profundidad = 25;
	static int iteraciones = 100;

	public static void main(String[] args) {
		DecimalFormat dec = new DecimalFormat("0.00");
		
		cabecera();
		for (int i = 2; i < profundidad; i++) {
			BFS_N = 0; IDS_N = 0; AH1_N = 0; AH2_N = 0;	// Limpieza para la siguiente profundidad
			for (int j = 0; j < iteraciones; j++) {				// 100 iteraciones
				// creo un estado del tablero aleatorio
				boardIni = GenerateInitialEightPuzzleBoard.randomIni();
				Problem problem = null;
				// accept = true si la solucion esta en la profundidad i. Sino false
				boolean accept = false;
				while(!accept) {
					boardFin = GenerateInitialEightPuzzleBoard.random(i, boardIni);	// (profundidad, estadoInicial)
					problem = new Problem(boardIni, 
							EightPuzzleFunctionFactory.getActionsFunction(), 
							EightPuzzleFunctionFactory.getResultFunction(),
							new EightPuzzleGoalTest(boardFin));
					try {
						SearchAgent agent = new SearchAgent( problem, new AStarSearch(new GraphSearch(), new ManhattanHeuristicFunction2(boardFin)) );
						if (i == (int)Float.parseFloat(agent.getInstrumentation().getProperty("pathCost")))
							accept = true;
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
				resolvePuzzle(problem, new BreadthFirstSearch(new GraphSearch()), 1);
				if (i < 11)
					resolvePuzzle(problem, new IterativeDeepeningSearch(), 2);
				resolvePuzzle(problem, new AStarSearch(new GraphSearch(), new ManhattanHeuristicFunction2(boardFin)), 3);
				resolvePuzzle(problem, new AStarSearch(new GraphSearch(), new MisplacedTilleHeuristicFunction2(boardFin)), 4);
				
			}
			Biseccion b = new Biseccion();
			b.setDepth(i);
			// BFS
			b.setGeneratedNodes(BFS_N/iteraciones);
			BFS_B = b.metodoDeBiseccion(1.000000000001, 4, 1E-12);
			// IDS
			if (i < 11) {
				b.setGeneratedNodes(IDS_N/iteraciones);
				IDS_B = b.metodoDeBiseccion(1.000000000001, 4, 1E-12);
			} else {
				IDS_B = 0;
			}
			// AH1 -> AManhattan
			b.setGeneratedNodes(AH1_N/iteraciones);
			AH1_B = b.metodoDeBiseccion(1.000000000001, 4, 1E-12);
			// AH2 -> AMisplaced
			b.setGeneratedNodes(AH2_N/iteraciones);
			AH2_B = b.metodoDeBiseccion(1.000000000001, 4, 1E-12);
			
			// Mostrar fila 
			System.out.format("||%4s||%9s|%9s|%9s|%9s||%9s|%9s|%9s|%9s||\n", i, BFS_N/100, IDS_N/100, AH1_N/100, AH2_N/100, dec.format(BFS_B), dec.format(IDS_B), dec.format(AH1_B), dec.format(AH2_B));
		}
	}
	
	 private static void cabecera() {
		 System.out.println("------------------------------------------------------------------------------------------");
		 System.out.format("||%4s||%39s||%39s||\n", "", "Nodos Generados", "b*");
		 System.out.println("------------------------------------------------------------------------------------------");
		 System.out.format("||%4s||%9s|%9s|%9s|%9s||%9s|%9s|%9s|%9s||\n", "d", "BFS", "IDS", "A*h(1)", "A*h(2)","BFS", "IDS", "A*h(1)", "A*h(2)");
		 System.out.println("------------------------------------------------------------------------------------------");
		 System.out.println("------------------------------------------------------------------------------------------");
	 }
	 
	 private static void resolvePuzzle(Problem problem, Search search, int col) {
		 try {
			 SearchAgent agent = new SearchAgent(problem, search);
			 int nodes = (int)Float.parseFloat(agent.getInstrumentation().getProperty("nodesGenerated"));
			 if      (col == 1) BFS_N += nodes;
			 else if (col == 2)	IDS_N += nodes;
			 else if (col == 3) AH1_N += nodes;
			 else if (col == 4) AH2_N += nodes;
		 } catch(Exception e) {
			 e.printStackTrace();
		 }
	 }
}