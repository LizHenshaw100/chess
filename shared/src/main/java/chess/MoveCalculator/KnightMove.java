package chess.MoveCalculator;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class KnightMove implements MoveCalculatorInterface {
    public ArrayList<ChessMove> getMoves(ChessBoard board, ChessPosition pos) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        int row = pos.getRow();
        int col = pos.getColumn();
        ChessGame.TeamColor team = board.getPiece(pos).getTeamColor();
        //1 o'clock
        if (isValidPosition(row + 2, col + 1)) {
            if (!isFriend(row+2, col+1, board, team)) {
                moves.add(new ChessMove(pos, new ChessPosition(row + 2, col + 1), null));
            }
        }
        //2 o'clock
        if (isValidPosition(row + 1, col + 2)) {
            if (!isFriend(row+1, col+2, board, team)) {
                moves.add(new ChessMove(pos, new ChessPosition(row + 1, col + 2), null));
            }
        }
        //4 o'clock
        if (isValidPosition(row - 1, col + 2)) {
            if (!isFriend(row-1, col+2, board, team)) {
                moves.add(new ChessMove(pos, new ChessPosition(row - 1, col + 2), null));
            }
        }
        //5 o'clock
        if (isValidPosition(row - 2, col + 1)) {
            if (!isFriend(row-2, col+1, board, team)) {
                moves.add(new ChessMove(pos, new ChessPosition(row - 2, col + 1), null));
            }
        }
        //7 o'clock
        if (isValidPosition(row - 2, col - 1)) {
            if (!isFriend(row-2, col-1, board, team)) {
                moves.add(new ChessMove(pos, new ChessPosition(row - 2, col - 1), null));
            }
        }
        //8 o'clock
        if (isValidPosition(row - 1, col - 2)) {
            if (!isFriend(row-1, col-2, board, team)) {
                moves.add(new ChessMove(pos, new ChessPosition(row - 1, col - 2), null));
            }
        }
        //10 o'clock
        if (isValidPosition(row + 1, col - 2)) {
            if (!isFriend(row+1, col-2, board, team)) {
                moves.add(new ChessMove(pos, new ChessPosition(row + 1, col - 2), null));
            }
        }
        //11 o'clock
        if (isValidPosition(row + 2, col - 1)) {
            if (!isFriend(row+2, col-1, board, team)) {
                moves.add(new ChessMove(pos, new ChessPosition(row + 2, col - 1), null));
            }
        }
        return moves;
    }

    boolean isValidPosition(int row, int col) {
        return (row >= 1 && row <= 8 && col >= 1 && col <= 8);
    }

    boolean isFriend(int row, int col, ChessBoard board, ChessGame.TeamColor team) {
        if (isEmpty(row, col, board)) {
            return false;
        }
        return (board.getPiece(new ChessPosition(row, col)).getTeamColor() == team);
    }

    boolean isEmpty(int row, int col, ChessBoard board) {
        return board.getPiece(new ChessPosition(row, col))==null;
    }
}
