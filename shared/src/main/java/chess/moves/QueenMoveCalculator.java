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
    private BishopMoveCalculator BishopMoves;
    private RookMoveCalculator RookMoves;

    public QueenMoveCalculator(ChessBoard board, ChessPosition position, ChessGame.TeamColor team) {
        this.board = board;
        this.start = position;
        this.team = team;
        this.BishopMoves = new BishopMoveCalculator(board, position, team);
        this.RookMoves = new RookMoveCalculator(board, position, team);
    }

    @Override
    public Collection<ChessMove> GetLegalMoves() {
        List<ChessMove> legalMoves = new ArrayList<>();
        legalMoves.addAll(BishopMoves.GetLegalMoves());
        legalMoves.addAll(RookMoves.GetLegalMoves());
        return legalMoves;
    }
}
