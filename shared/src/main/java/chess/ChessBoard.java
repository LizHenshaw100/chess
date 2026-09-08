package chess;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;

import static chess.ChessGame.TeamColor.WHITE;
import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessPiece.PieceType.PAWN;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    ChessPiece[][] board;
    public ChessBoard() {
        board = new ChessPiece[8][8];
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        board[position.getRow()][position.getColumn()] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return board[position.getRow()][position.getColumn()];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        ChessPiece.PieceType[] pieceOrder = {
                ChessPiece.PieceType.ROOK,
                ChessPiece.PieceType.KNIGHT,
                ChessPiece.PieceType.BISHOP,
                ChessPiece.PieceType.QUEEN,
                ChessPiece.PieceType.KING,
                ChessPiece.PieceType.BISHOP,
                ChessPiece.PieceType.KNIGHT,
                ChessPiece.PieceType.ROOK
        };

        ChessPiece piece;
        ChessPosition position;
        for (int row=0; row<9; row++) {
            // white pawns
            if (row == 0) {
                piece = new ChessPiece(WHITE, PAWN);
                for (int col = 0; col < 9; col++) {
                    position = new ChessPosition(row, col);
                    addPiece(position, piece);
                }
            }
            // white row
            if (row == 1) {
                for (int col = 0; col < 9; col++) {
                    position = new ChessPosition(row, col);
                    piece = new ChessPiece(WHITE, pieceOrder[col]);
                    addPiece(position, piece);
                }
            }
            // null rows
            if (row > 1 && row < 6) {
                for (int col=0; col<8; col++) {
                    position = new ChessPosition(row, col);
                    addPiece(position, null);
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(board, that.board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }

    @Override
    public String toString() {
        String returnValue = "";
        for (int row=1; row<9; row++) {
            for (int col=1; col<9; col++) {
                returnValue += getPiece(new ChessPosition(row, col));
            }
        }
        return returnValue;
    }
}
