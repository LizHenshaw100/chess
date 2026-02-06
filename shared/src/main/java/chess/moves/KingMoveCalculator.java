package chess.moves;

import chess.*;
import chess.ChessInterface;

import java.util.HashSet;
import java.util.Collection;

import static chess.ChessGame.TeamColor.WHITE;
import static chess.ChessPiece.PieceType.ROOK;



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
        ChessPosition end = new ChessPosition(row + 1, col);
        singleSpaceChecker(board, end, legalMoves, start, team);

        //Check front right diag
        end = new ChessPosition(row + 1, col + 1);
        singleSpaceChecker(board, end, legalMoves, start, team);

        //Check right
        end = new ChessPosition(row, col + 1);
        singleSpaceChecker(board, end, legalMoves, start, team);

        //Check back right diag
        end = new ChessPosition(row - 1, col + 1);
        singleSpaceChecker(board, end, legalMoves, start, team);

        //Check back
        end = new ChessPosition(row - 1, col);
        singleSpaceChecker(board, end, legalMoves, start, team);

        //Check back left diag
        end = new ChessPosition(row - 1, col - 1);
        singleSpaceChecker(board, end, legalMoves, start, team);

        //Check left
        end = new ChessPosition(row, col - 1);
        singleSpaceChecker(board, end, legalMoves, start, team);

        //Check front left diag
        end = new ChessPosition(row + 1, col - 1);
        singleSpaceChecker(board, end, legalMoves, start, team);

        ChessPiece king = board.getPiece(start);
        if (king.getMoveCount() == 0) {
            addCastlingMoves(legalMoves);
        }

        return legalMoves;
    }

    public void addCastlingMoves(Collection<ChessMove> legalMoves) {
        int row;
        if (team == WHITE) {
            row = 1;
        }
        else {
            row = 8;
        }
        tryAddCastlingMove(legalMoves, new ChessPosition(row, 1), 2, 4, new ChessPosition(row, 3));

        tryAddCastlingMove(legalMoves, new ChessPosition(row, 8), 6, 7, new ChessPosition(row, 7));
    }

    public void tryAddCastlingMove(Collection<ChessMove> legalMoves, ChessPosition rookPos, int startCol, int endCol, ChessPosition kingEnd) {
        ChessPiece rook = board.getPiece(rookPos);
        if (rook == null || rook.getPieceType() != ROOK || rook.getMoveCount() != 0) {
            return;
        }
        int row = rookPos.getRow();
        for (int col = startCol; col <= endCol; col++) {
            if (!noOtherPiece(board, new ChessPosition(row, col))) {
                return;
            }
        }
        legalMoves.add(new ChessMove(start, kingEnd, null));
    }


}

