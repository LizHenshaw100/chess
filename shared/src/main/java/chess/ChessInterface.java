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

    default boolean isFriendly(ChessBoard board, ChessPosition position) {
        if (board.getPiece(position) ==
    }
}
