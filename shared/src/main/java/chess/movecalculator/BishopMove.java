package chess.movecalculator;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;

public class BishopMove implements MoveCalculatorInterface {
    public ArrayList<ChessMove> getMoves(ChessBoard board, ChessPosition pos) {
        int[][] coords = {{1,1},{-1,-1},{-1,1},{1,-1}};
        return calculateSlides(board, pos, coords);
    }
}
