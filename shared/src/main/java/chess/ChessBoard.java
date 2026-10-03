package chess;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
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
    HashMap<ChessPosition, ChessPiece> whitePieces;
    HashMap<ChessPosition, ChessPiece> blackPieces;

    public ChessBoard() {
        board = new ChessPiece[8][8];
        whitePieces = new HashMap<>();
        blackPieces = new HashMap<>();
    }

    public ChessBoard(ChessBoard oldBoard) {
        board = new ChessPiece[8][8];
        whitePieces = new HashMap<>();
        blackPieces = new HashMap<>();

        for (int i=1; i<9; i++) {
            for (int j=1; j<9; j++) {
                copyPieceIfExists(oldBoard, new ChessPosition(i, j));
            }
        }
    }

    public void copyPieceIfExists(ChessBoard oldBoard, ChessPosition position) {
        ChessPiece oldPiece = oldBoard.getPiece(position);
        if (oldPiece != null) {
            ChessPiece pieceCopy = new ChessPiece(oldPiece);
            addPiece(position, pieceCopy);
        }
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        removePiece(position);
        board[position.getRow()-1][position.getColumn()-1] = piece;
        if (piece != null){
            if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                whitePieces.put(position, piece);
            }
            else {
                blackPieces.put(position, piece);
            }
        }
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return board[position.getRow()-1][position.getColumn()-1];
    }

    public void removePiece(ChessPosition position) {
        if (getPiece(position) != null) {
            if (getPiece(position).getTeamColor() == WHITE) {
                whitePieces.remove(position);
            } else {
                blackPieces.remove(position);
            }
        }
        board[position.getRow()-1][position.getColumn()-1] = null;
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

        board = new ChessPiece[8][8];
        whitePieces.clear();
        blackPieces.clear();

        ChessPiece piece;
        ChessPosition position;
        for (int row=1; row<=8; row++) {
            // white pawns
            if (row == 2) {
                piece = new ChessPiece(WHITE, PAWN);
                for (int col = 1; col <= 8; col++) {
                    position = new ChessPosition(row, col);
                    addPiece(position, piece);
                }
            }
            // white row
            if (row == 1) {
                for (int col = 1; col <= 8; col++) {
                    position = new ChessPosition(row, col);
                    piece = new ChessPiece(WHITE, pieceOrder[col-1]);
                    addPiece(position, piece);
                }
            }
            // null rows
            if (row > 2 && row < 7) {
                for (int col=1; col<=8; col++) {
                    position = new ChessPosition(row, col);
                    addPiece(position, null);
                }
            }
            // black pawns
            if (row==7){
                for (int col=1; col<=8; col++) {
                    position = new ChessPosition(row, col);
                    piece = new ChessPiece(BLACK, PAWN);
                    addPiece(position, piece);
                }
            }
            // black row
            if (row==8) {
                for (int col=1; col<=8; col++) {
                    position = new ChessPosition(row, col);
                    piece = new ChessPiece(BLACK, pieceOrder[col-1]);
                    addPiece(position, piece);
                }
            }
        }
    }

    public HashMap<ChessPosition, ChessPiece> getPieces(ChessGame.TeamColor color) {
        if (color == WHITE) {
            return whitePieces;
        }
        else {
            return blackPieces;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(board, that.board) && Objects.equals(whitePieces, that.whitePieces) && Objects.equals(blackPieces, that.blackPieces);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.deepHashCode(board), whitePieces, blackPieces);
    }

    @Override
    public String toString() {
        String returnValue = "";
        for (int row=1; row<=8; row++) {
            for (int col=1; col<=8; col++) {
                returnValue += getPiece(new ChessPosition(row, col));
            }
            returnValue+= "\n";
        }
        return returnValue;
    }
}
