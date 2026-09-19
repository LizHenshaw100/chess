package chess.MoveCalculator;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;

public class QueenMove implements MoveCalculatorInterface{
    @Override
    public ArrayList<ChessMove> getMoves(ChessBoard board, ChessPosition pos) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        //get diagonal movements from bishop
        MoveCalculatorInterface diags = new BishopMove();
        //get diagonal movements from rook
        MoveCalculatorInterface straights = new RookMove();
        ArrayList<ChessMove> diagMoves = diags.getMoves(board, pos);
        ArrayList<ChessMove> straightMoves = straights.getMoves(board, pos);
        moves.addAll(diagMoves);
        moves.addAll(straightMoves);
        return moves;
    }
}
