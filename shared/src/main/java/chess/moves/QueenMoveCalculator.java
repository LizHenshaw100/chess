package chess.moves;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPosition;
import chess.ChessMove;
import chess.ChessInterface;

import java.util.*;

public class QueenMoveCalculator implements ChessInterface{
    private ChessBoard board;
    private ChessPosition start;
    private ChessGame.TeamColor team;
    private BishopMoveCalculator bishopMoves;
    private RookMoveCalculator rookMoves;

    public QueenMoveCalculator(ChessBoard board, ChessPosition position, ChessGame.TeamColor team) {
        this.board = board;
        this.start = position;
        this.team = team;
        this.bishopMoves = new BishopMoveCalculator(board, position, team);
        this.rookMoves = new RookMoveCalculator(board, position, team);
    }

    @Override
    public Collection<ChessMove> getLegalMoves() {
        HashSet<ChessMove> legalMoves = new HashSet<>();
        legalMoves.addAll(bishopMoves.getLegalMoves());
        legalMoves.addAll(rookMoves.getLegalMoves());
        return legalMoves;
    }
}
