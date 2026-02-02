package chess.moves;

import chess.*;
import chess.ChessInterface;

import java.util.HashSet;
import java.util.Collection;





public class KingMoveCalculator implements ChessInterface {
    private ChessBoard board;
    private ChessPosition start;
    private ChessGame.TeamColor team;
    private int row;
    private int col;

    public KingMoveCalculator(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor team) {
        this.board = board;
        this.start = myPosition;
        this.team = team;
        this.row = start.getRow();
        this.col = start.getColumn();
    }

    @Override
    public Collection<ChessMove> getLegalMoves() {
        HashSet<ChessMove> legalMoves = new HashSet<>();
        //Check front
        ChessPosition end = new ChessPosition(row+1, col);
        singleSpaceChecker(board, end, legalMoves, start, team);

        //Check front right diag
        end = new ChessPosition(row+1, col+1);
        singleSpaceChecker(board, end, legalMoves, start, team);

        //Check right
        end = new ChessPosition(row, col+1);
        singleSpaceChecker(board, end, legalMoves, start, team);

        //Check back right diag
        end = new ChessPosition(row-1, col+1);
        singleSpaceChecker(board, end, legalMoves, start, team);

        //Check back
        end = new ChessPosition(row-1, col);
        singleSpaceChecker(board, end, legalMoves, start, team);

        //Check back left diag
        end = new ChessPosition(row-1, col-1);
        singleSpaceChecker(board, end, legalMoves, start, team);

        //Check left
        end = new ChessPosition(row, col-1);
        singleSpaceChecker(board, end, legalMoves, start, team);

        //Check front left diag
        end = new ChessPosition(row+1, col-1);
        singleSpaceChecker(board, end, legalMoves, start, team);

        return legalMoves;
    }
}

