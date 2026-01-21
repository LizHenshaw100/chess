package chess.moves;

import chess.*;
import chess.ChessInterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;




public class KnightMoveCalculator implements ChessInterface {
    private ChessBoard board;
    private ChessPosition start;
    private ChessGame.TeamColor team;
    private int row;
    private int col;

    public KnightMoveCalculator(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor team) {
        this.board = board;
        this.start = myPosition;
        this.team = team;
        this.row = start.getRow();
        this.col = start.getColumn();
    }


    @Override
    public Collection<ChessMove> getLegalMoves() {
        List<ChessMove> legalMoves = new ArrayList<>();
        //Check 1 o'clock
        ChessPosition end = new ChessPosition(row+2, col+1);
        singleSpaceChecker(board, end, legalMoves, start, team);

        //Check 2 o'clock
        end = new ChessPosition(row+1, col+2);
        singleSpaceChecker(board, end, legalMoves, start, team);

        //Check 4 o'clock
        end = new ChessPosition(row-1, col+2);
        singleSpaceChecker(board, end, legalMoves, start, team);

        //Check 5 o'clock
        end = new ChessPosition(row-2, col+1);
        singleSpaceChecker(board, end, legalMoves, start, team);

        //Check 7 o'clock
        end = new ChessPosition(row-2, col-1);
        singleSpaceChecker(board, end, legalMoves, start, team);

        //Check 8 o'clock
        end = new ChessPosition(row-1, col-2);
        singleSpaceChecker(board, end, legalMoves, start, team);

        //Check 10 o'clock
        end = new ChessPosition(row+1, col-2);
        singleSpaceChecker(board, end, legalMoves, start, team);

        //Check 11 o'clock
        end = new ChessPosition(row+2, col-1);
        singleSpaceChecker(board, end, legalMoves, start, team);

        return legalMoves;
    }
}

