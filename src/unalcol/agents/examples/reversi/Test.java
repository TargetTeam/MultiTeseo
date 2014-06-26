/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.reversi;

/**
 *
 * @author Camilo
 */
public class Test {

    public static void main(String[] args) {
        ReversiBoard board = new ReversiBoard(10);
        Move move = new Move();
        int n = 1;
        int win = 0;
        int drawn = 0;
        for (int i = 0; i < n; i++) {
            board.clear();
            while (board.userCanMove(TKind.black) || board.userCanMove(TKind.white)) {
                if (board.findMove(TKind.black, 4, move)) {
                    board.move(move, TKind.black);
                    //System.out.println("Move Black");
                    //board.printBoard();
                }
                if (board.findMove(TKind.white, 4, move)) {
                    board.move(move, TKind.white);
                    //System.out.println("Move White");
                    //board.printBoard();
                }
            }
            board.printBoard();
            System.out.print("Iteration#=" + i + " Result:" + board.getCounter(TKind.black) + "-" + board.getCounter(TKind.white));
            if (board.getCounter(TKind.black) > board.getCounter(TKind.white)) {
                win++;
            }
            if (board.getCounter(TKind.black) == board.getCounter(TKind.white)) {
                drawn++;
            }
            System.out.println("  Win#=" + win + "  Drawn#=" + drawn);
        }
        System.out.println("Total#=" + n + "  Win#=" + win + "  Drawn#=" + drawn);
    }
}
