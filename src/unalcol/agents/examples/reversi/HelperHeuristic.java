/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.reversi;

/**
 *
 * @author Camilo
 */
public class HelperHeuristic {

    /* La heurística basada en esquinas. Propuesta por Peter Norvig
     '( ( 120 -20 20 5 5 20 -20 120 ) 
     ( -20 -40 -5 -5 -5 -5 -40 -20 ) 
     ( 20 -5 15 3 3 15 -5 20 ) 
     ( 5 -5 3 3 3 3 -5 5 ) 
     ( 5 -5 3 3 3 3 -5 5 ) 
     ( 20 -5 15 3 3 15 -5 20 ) 
     ( -20 -40 -5 -5 -5 -5 -40 -20 ) 
     ( 120 -20 20 5 5 20 -20 120 )))) 
     */
    int[][] weight = 
       {{120, -20, 20, 5, 5, 20, -20, 120},
        {-20, -40, -5, -5, -5, -5, -40, -20},
        {20, -5, 15, 3, 3, 15, -5, 20},
        {5, -5, 3, 3, 3, 3, -5, 5},
        {5, -5, 3, 3, 3, 3, -5, 5},
        {20, -5, 15, 3, 3, 15, -5, 20},
        {-20, -40, -5, -5, -5, -5, -40, -20},
        {120, -20, 20, 5, 5, 20, -20, 120}
    };

    public int getHeuristicCornner(Move move) {
        return weight[move.i][move.j];
    }
}
