package chess;

import java.util.Collection;
import java.util.HashSet;

import chess.ChessGame.TeamColor;

public interface ChessInterface {
    Collection<ChessMove> getLegalMoves();

    default boolean noOtherPiece(ChessBoard board, ChessPosition position) {
        if (board.getPiece(position) == null) {
            return true;
        }
        return false;
    }

    default boolean isFriendly(ChessBoard board, ChessPosition position, ChessGame.TeamColor color) {
        if (board.getPiece(position).getTeamColor() == color) {
            return true;
        }
        else {
            return false;
        }
    }

    default boolean isEnemy(ChessBoard board, ChessPosition position, ChessGame.TeamColor color) {
        if (noOtherPiece(board, position)){
            return false;
        }

        if (board.getPiece(position).getTeamColor() != color) {
            return true;
        }
        else {
            return false;
        }
    }

    default boolean isValidPosition(ChessPosition position) {
        return (position.getRow() <= 8) && (position.getRow() >= 1) && (position.getColumn() <= 8) && (position.getColumn() >= 1);
    }

    default void singleSpaceChecker(ChessBoard board, ChessPosition end, HashSet<ChessMove> legalMoves, ChessPosition start, TeamColor team) {
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
}
