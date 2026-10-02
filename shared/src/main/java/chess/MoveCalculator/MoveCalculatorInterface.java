package chess.MoveCalculator;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;

public interface MoveCalculatorInterface {
    ArrayList<ChessMove> getMoves(ChessBoard board, ChessPosition pos);
}
