package chess.moves;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BishopMoveCalculator implements ChessInterface {
    private ChessBoard board;
    private ChessPosition start;
    private ChessGame.TeamColor team;
    private int row;
    private int col;



    public BishopMoveCalculator(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor team) {
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
        //Check diagonal right
        while ((temp_row < 8) && (temp_col < 8)) {
            temp_row++;
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

        //Check diagonal left
        temp_row = row;
        temp_col = col;
        while ((temp_row < 8) && (temp_col > 1)) {
            temp_row++;
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

        //Check back diagonal left
        temp_row = row;
        temp_col = col;
        while ((temp_row > 1) && (temp_col > 1)) {
            temp_row--;
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

        //Check back diagonal right
        temp_row = row;
        temp_col = col;
        while ((temp_row > 1) && (temp_col < 8)) {
            temp_row--;
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

        return legalMoves;
    }
}
