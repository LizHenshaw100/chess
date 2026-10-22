package chess.movecalculator;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;

public class KingMove implements MoveCalculatorInterface {
    public ArrayList<ChessMove> getMoves(ChessBoard board, ChessPosition pos) {
        int[][] coords = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        return calculateSteps(board, pos, coords);
    }
}
