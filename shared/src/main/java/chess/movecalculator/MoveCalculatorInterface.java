package chess.movecalculator;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;

public interface MoveCalculatorInterface {
    ArrayList<ChessMove> getMoves(ChessBoard board, ChessPosition pos);
    default boolean isValidPosition(int row, int col) {
        return row >= 1 && row <= 8 && col >= 1 && col <= 8;
    }

    default boolean isEmpty(int row, int col, ChessBoard board) {
        return board.getPiece(new ChessPosition(row, col)) == null;
    }

    default boolean isFriend(int row, int col, ChessBoard board, ChessGame.TeamColor team) {
        return !isEmpty(row, col, board)
                && board.getPiece(new ChessPosition(row, col)).getTeamColor() == team;
    }

    default boolean isEnemy(int row, int col, ChessBoard board, ChessGame.TeamColor team) {
        if (!isValidPosition(row, col) || isEmpty(row, col, board)) {
            return false;
        }
        return (board.getPiece(new ChessPosition(row, col)).getTeamColor() != team);
    }

    default ArrayList<ChessMove> calculateSteps(ChessBoard board, ChessPosition pos, int[][] coords) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        ChessGame.TeamColor team = board.getPiece(pos).getTeamColor();
        for (int[] coord : coords) {
            int row = pos.getRow() + coord[0];
            int col = pos.getColumn() + coord[1];
            if (isValidPosition(row, col) && !isFriend(row, col, board, team)) {
                moves.add(new ChessMove(pos, new ChessPosition(row, col), null));
            }
        }
        return moves;
    }

    default ArrayList<ChessMove> calculateSlides(ChessBoard board, ChessPosition pos, int [][] coords) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        ChessGame.TeamColor team = board.getPiece(pos).getTeamColor();
        int row;
        int col;
        for (int[] coord : coords) {
            row = pos.getRow() + coord[0];
            col = pos.getColumn() + coord[1];
            while(isValidPosition(row, col) && !isFriend(row, col, board, team)) {
                moves.add(new ChessMove(pos, new ChessPosition(row, col), null));
                if (isEnemy(row, col, board, team)) {
                    break;
                }
                row += coord[0];
                col += coord[1];
            }
        }
        return moves;
    }
}
