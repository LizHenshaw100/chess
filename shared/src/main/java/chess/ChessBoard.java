package chess;


import java.util.Arrays;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    public ChessPiece[][] board = new ChessPiece[8][8];

    public ChessBoard() {
        board = new ChessPiece[8][8];
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

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        board[Math.abs(position.getRow()-8)][position.getColumn()-1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return board[Math.abs(position.getRow()-8)][position.getColumn()-1];
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
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                // white pawns
                if (i==1) {
                    addPiece(new ChessPosition(i+1, j+1), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN));
                }
                // black pawns
                else if (i==6) {
                    addPiece(new ChessPosition(i+1, j+1), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN));
                }
                // null space
                else if ((i > 1) && (i < 6)) {
                    addPiece(new ChessPosition(i+1, j+1), null);
                }
                // white first row
                else if (i==0) {
                    j = 0;
                    for (ChessPiece.PieceType type : pieceOrder) {
                        addPiece(new ChessPosition(i+1, j+1), new ChessPiece(ChessGame.TeamColor.WHITE, type));
                        j++;
                    }
                    break;
                }
                // black first row
                else {
                    j = 0;
                    for (ChessPiece.PieceType type : pieceOrder) {
                        addPiece(new ChessPosition(i+1, j+1), new ChessPiece(ChessGame.TeamColor.BLACK, type));
                        j++;
                    }
                    break;
                }
            }
        }
        System.out.println(board);
    }

    @Override
    public String toString() {
        String chessboard = "Chess Board:\n";
        for (int i=0; i<8; i++)
        {
            for (ChessPiece piece : board[i]) {
                if (piece!=null) {
                    chessboard = chessboard + piece;
                }
                else {
                    chessboard = chessboard + ".";
                }
            }
            chessboard = chessboard + "\n";
        }
        return chessboard;
    }
}
