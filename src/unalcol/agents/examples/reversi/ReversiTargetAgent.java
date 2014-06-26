/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.reversi;

import unalcol.agents.Action;
import unalcol.agents.AgentProgram;
import unalcol.agents.Percept;

/**
 *
 * @author Jonatan
 */
public class ReversiTargetAgent implements AgentProgram {

    protected String color;
    ReversiBoard reversiBoard;
    int size;
    Move move = new Move();
    TKind player = TKind.nil;

    public ReversiTargetAgent(String color) {
        this.color = color;
        reversiBoard = null;
    }

    @Override
    public Action compute(Percept p) {
        if (reversiBoard == null) {
            size = (int) p.getAttribute(Reversi.SIZE);
            reversiBoard = new ReversiBoard(size);
            if (color == Reversi.BLACK) {
                player = TKind.black;
            }
            if (color == Reversi.WHITE) {
                player = TKind.white;
            }
        }
        reversiBoard.printBoard();
        if (p.getAttribute(Reversi.TURN).equals(color)) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    String valor = (String) p.getAttribute(i + ":" + j);
//                    if (valor.equals(Reversi.BLACK) && reversiBoard.get(i, j) == TKind.nil) {
                    if (valor.equals(Reversi.BLACK) ) {
//                        reversiBoard.move(new Move(i, j), TKind.black);
                        reversiBoard.set(new Move(i, j), TKind.black);
                    } else if (valor.equals(Reversi.WHITE) ) {
//                        reversiBoard.move(new Move(i, j), TKind.white);
                        reversiBoard.set(new Move(i, j), TKind.white);
                    }
                    
                }
            }
            if (!reversiBoard.userCanMove(player)) {
                System.out.println("Cant move " + color);
                return new Action(Reversi.PASS);
            }
            if (size == 8) {
                if (reversiBoard.findMoveMinmax(player, 4, move)) {
                    reversiBoard.move(move, player);
                    return new Action(move.i + ":" + move.j + ":" + color);
                }
            } else {
                if (reversiBoard.findMove(player, 4, move)) {
                    reversiBoard.move(move, player);
                    return new Action(move.i + ":" + move.j + ":" + color);
                }
            }
        }
        System.out.println("Stealing turn " + color);
        return new Action(Reversi.PASS);
    }

    @Override
    public void init() {
    }
}
