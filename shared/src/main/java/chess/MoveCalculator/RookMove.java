package chess.MoveCalculator;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class RookMove implements MoveCalculatorInterface {
    public ArrayList<ChessMove> getMoves(ChessBoard board, ChessPosition pos) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        int row = pos.getRow();
        int col = pos.getColumn();
        ChessGame.TeamColor team = board.getPiece(pos).getTeamColor();
        //check front
        while (isValidPosition(row + 1, col)) {
            row += 1;
            if (isEnemy(row, col, board, team)) {
                moves.add(new ChessMove(pos, new ChessPosition(row, col), null));
                break;
            } else if (isFriend(row, col, board, team)) {
                break;
            } else {
                moves.add(new ChessMove(pos, new ChessPosition(row, col), null));
            }
        }
        //check back
        row = pos.getRow();
        col = pos.getColumn();
        while (isValidPosition(row - 1, col)) {
            row -= 1;
            if (isEnemy(row, col, board, team)) {
                moves.add(new ChessMove(pos, new ChessPosition(row, col), null));
                break;
            } else if (isFriend(row, col, board, team)) {
                break;
            } else {
                moves.add(new ChessMove(pos, new ChessPosition(row, col), null));
            }
        }
        //check back left
        row = pos.getRow();
        col = pos.getColumn();
        while (isValidPosition(row, col - 1)) {
            col -= 1;
            if (isEnemy(row, col, board, team)) {
                moves.add(new ChessMove(pos, new ChessPosition(row, col), null));
                break;
            } else if (isFriend(row, col, board, team)) {
                break;
            } else {
                moves.add(new ChessMove(pos, new ChessPosition(row, col), null));
            }
        }
        //check right
        row = pos.getRow();
        col = pos.getColumn();
        while (isValidPosition(row, col + 1)) {
            col += 1;
            if (isEnemy(row, col, board, team)) {
                moves.add(new ChessMove(pos, new ChessPosition(row, col), null));
                break;
            } else if (isFriend(row, col, board, team)) {
                break;
            } else {
                moves.add(new ChessMove(pos, new ChessPosition(row, col), null));
            }
        }
        return moves;
    }

    boolean isValidPosition(int row, int col) {
        return (row >= 1 && row <= 8 && col >= 1 && col <= 8);
    }

    boolean isEnemy(int row, int col, ChessBoard board, ChessGame.TeamColor team) {
        if (isEmpty(row, col, board)) {
            return false;
        }
        return (board.getPiece(new ChessPosition(row, col)).getTeamColor() != team);
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
