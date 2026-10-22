package chess.movecalculator;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;

public class KnightMove implements MoveCalculatorInterface {
    public ArrayList<ChessMove> getMoves(ChessBoard board, ChessPosition pos) {
        int[][] coords = {{2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {1, 2}, {1, -2}, {-1, 2}, {-1, -2}};
        return calculateSteps(board, pos, coords);
    }
}