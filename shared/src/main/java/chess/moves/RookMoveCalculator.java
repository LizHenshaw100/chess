package chess.moves;

import chess.*;
import chess.ChessInterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;




public class RookMoveCalculator implements ChessInterface {
    private ChessBoard board;
    private ChessPosition start;
    private ChessGame.TeamColor team;
    private int row;
    private int col;

    public RookMoveCalculator(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor team) {
        this.board = board;
        this.start = myPosition;
        this.team = team;
        this.row = start.getRow();
        this.col = start.getColumn();
    }

    @Override
    public Collection<ChessMove> GetLegalMoves() {
        List<ChessMove> legalMoves = new ArrayList<>();
        int temp_row = row;
        int temp_col = col;
        //Check front
        while (temp_row < 8) {
            temp_row++;
            ChessPosition end = new ChessPosition(temp_row, temp_col);
            if (noOtherPiece(board, end)) {
                ChessMove legalMove = new ChessMove(start, end, null);
                legalMoves.add(legalMove);
            }
            else if (isFriendly(board, end, team)) {
                break;
            }
            else {
                ChessMove legalMove = new ChessMove(start, end, null);
                legalMoves.add(legalMove);
                break;
            }
        }

        //Check right
        temp_row = row;
        while (temp_col < 8) {
            temp_col++;
            ChessPosition end = new ChessPosition(temp_row, temp_col);
            if (noOtherPiece(board, end)) {
                ChessMove legalMove = new ChessMove(start, end, null);
                legalMoves.add(legalMove);
            }
            else if (isFriendly(board, end, team)) {
                break;
            }
            else {
                ChessMove legalMove = new ChessMove(start, end, null);
                legalMoves.add(legalMove);
                break;
            }
        }

        //Check back
        temp_col = col;
        while (temp_row > 1) {
            temp_row--;
            ChessPosition end = new ChessPosition(temp_row, temp_col);
            if (noOtherPiece(board, end)) {
                ChessMove legalMove = new ChessMove(start, end, null);
                legalMoves.add(legalMove);
            }
            else if (isFriendly(board, end, team)) {
                break;
            }
            else {
                ChessMove legalMove = new ChessMove(start, end, null);
                legalMoves.add(legalMove);
                break;
            }
        }

        //Check left
        temp_row = row;
        while (temp_col > 1) {
            temp_col--;
            ChessPosition end = new ChessPosition(temp_row, temp_col);
            if (noOtherPiece(board, end)) {
                ChessMove legalMove = new ChessMove(start, end, null);
                legalMoves.add(legalMove);
            }
            else if (isFriendly(board, end, team)) {
                break;
            }
            else {
                ChessMove legalMove = new ChessMove(start, end, null);
                legalMoves.add(legalMove);
                break;
            }
        }

        return legalMoves;
    }
}

