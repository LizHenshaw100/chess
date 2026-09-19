package chess.MoveCalculator;

import chess.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class PawnMove implements MoveCalculatorInterface {
    public ArrayList<ChessMove> getMoves(ChessBoard board, ChessPosition pos) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        int row = pos.getRow();
        int col = pos.getColumn();
        ChessGame.TeamColor team = board.getPiece(pos).getTeamColor();
        //check white
        if (team== ChessGame.TeamColor.WHITE) {
            if (row + 1 == 8) {
                //check promotion forward
                if (isEmpty(row + 1, col, board)) {
                    moves.addAll(promoMoves(row + 1, col, pos));
                }
                //check promotion attack left
                if (isEnemy(row + 1, col - 1, board, team)) {
                    moves.addAll(promoMoves(row + 1, col - 1, pos));
                }
                //check promotion attack right
                if (isEnemy(row + 1, col + 1, board, team)) {
                    moves.addAll(promoMoves(row + 1, col + 1, pos));
                }
                return moves;
            }
            //check front
            if (isEmpty(row + 1, col, board)) {
                moves.add(new ChessMove(pos, new ChessPosition(row + 1, col), null));
            }
            //check front right attack
            if (isEnemy(row + 1, col + 1, board, team)) {
                moves.add(new ChessMove(pos, new ChessPosition(row + 1, col + 1), null));
            }
            //check front left attack
            if (isEnemy(row + 1, col - 1, board, team)) {
                moves.add(new ChessMove(pos, new ChessPosition(row + 1, col - 1), null));
            }
            //check first move forward
            if (row==2 && isEmpty(row + 2, col, board) && isEmpty(row-1, col, board)) {
                moves.add(new ChessMove(pos, new ChessPosition(row + 2, col), null));
            }
        }
        //check black
        else {
            if (row - 1 == 1) {
                //check promotion forward
                if (isEmpty(row - 1, col, board)) {
                    moves.addAll(promoMoves(row - 1, col, pos));
                }
                //check promotion attack left
                if (isEnemy(row - 1, col - 1, board, team)) {
                    moves.addAll(promoMoves(row - 1, col - 1, pos));
                }
                //check promotion attack right
                if (isEnemy(row - 1, col + 1, board, team)) {
                    moves.addAll(promoMoves(row - 1, col + 1, pos));
                }
                return moves;
            }
            //check front
            if (isEmpty(row - 1, col, board)) {
                moves.add(new ChessMove(pos, new ChessPosition(row - 1, col), null));
            }
            //check front right attack
            if (isEnemy(row - 1, col + 1, board, team)) {
                moves.add(new ChessMove(pos, new ChessPosition(row - 1, col + 1), null));
            }
            //check front left attack
            if (isEnemy(row - 1, col - 1, board, team)) {
                moves.add(new ChessMove(pos, new ChessPosition(row - 1, col - 1), null));
            }
            //check first move forward
            if (row==7 && isEmpty(row - 2, col, board) && isEmpty(row-1, col, board)) {
                moves.add(new ChessMove(pos, new ChessPosition(row - 2, col), null));
            }
        }
        return moves;
    }


    ArrayList<ChessMove>promoMoves(int row, int col, ChessPosition pos) {
        ArrayList<ChessMove> move = new ArrayList<>();
        ChessPosition position = new ChessPosition(row, col);
        move.add(new ChessMove(pos, position, ChessPiece.PieceType.QUEEN));
        move.add(new ChessMove(pos, position, ChessPiece.PieceType.BISHOP));
        move.add(new ChessMove(pos, position, ChessPiece.PieceType.ROOK));
        move.add(new ChessMove(pos, position, ChessPiece.PieceType.KNIGHT));
        return move;
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
        if (isValidPosition(row, col)) {
            return board.getPiece(new ChessPosition(row, col)) == null;
        }
        return false;
    }

    boolean isEnemy(int row, int col, ChessBoard board, ChessGame.TeamColor team) {
        if (!isValidPosition(row, col)) {
            return false;
        }
        if (isEmpty(row, col, board)) {
            return false;
        }
        return (board.getPiece(new ChessPosition(row, col)).getTeamColor() != team);
    }
}
