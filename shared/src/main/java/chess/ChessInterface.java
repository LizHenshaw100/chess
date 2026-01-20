package chess;

import java.util.Collection;

public interface ChessInterface {
    Collection<ChessMove> GetLegalMoves();

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

    default boolean isValidPosition(ChessPosition position) {
        return (position.getRow() <= 8) && (position.getRow() >= 1) && (position.getColumn() <= 8) && (position.getColumn() >= 1);
    }
}
