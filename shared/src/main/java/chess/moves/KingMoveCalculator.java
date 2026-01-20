package chess.moves;

import chess.*;
import chess.ChessInterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;




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

    public void singleSpaceChecker(ChessBoard board, ChessPosition end, List<ChessMove> legalMoves) {
        if (isValidPosition(end)) {
            if (noOtherPiece(board, end)) {
                ChessMove legalMove = new ChessMove(start, end, null);
                legalMoves.add(legalMove);
            } else if (!isFriendly(board, end, team)) {
                ChessMove legalMove = new ChessMove(start, end, null);
                legalMoves.add(legalMove);
            }
        }
    }

    @Override
    public Collection<ChessMove> GetLegalMoves() {
        List<ChessMove> legalMoves = new ArrayList<>();
        //Check front
        ChessPosition end = new ChessPosition(row+1, col);
        singleSpaceChecker(board, end, legalMoves);

        //Check front right diag
        end = new ChessPosition(row+1, col+1);
        singleSpaceChecker(board, end, legalMoves);

        //Check right
        end = new ChessPosition(row, col+1);
        singleSpaceChecker(board, end, legalMoves);

        //Check back right diag
        end = new ChessPosition(row-1, col+1);
        singleSpaceChecker(board, end, legalMoves);

        //Check back
        end = new ChessPosition(row-1, col);
        singleSpaceChecker(board, end, legalMoves);

        //Check back left diag
        end = new ChessPosition(row-1, col-1);
        singleSpaceChecker(board, end, legalMoves);

        //Check left
        end = new ChessPosition(row, col-1);
        singleSpaceChecker(board, end, legalMoves);

        //Check front left diag
        end = new ChessPosition(row+1, col-1);
        singleSpaceChecker(board, end, legalMoves);

        return legalMoves;
    }
}

