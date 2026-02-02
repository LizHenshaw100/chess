package chess;


import java.util.Arrays;
import java.util.Objects;
import java.util.HashMap;

import static chess.ChessGame.TeamColor.WHITE;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    public ChessPiece[][] board;
    HashMap<ChessPosition, ChessPiece> whitePieces;
    HashMap<ChessPosition, ChessPiece> blackPieces;
    ChessPosition whiteKing;
    ChessPosition blackKing;

    public ChessBoard() {
        board = new ChessPiece[8][8];
        whitePieces = new HashMap<>();
        blackPieces = new HashMap<>();
    }

    public ChessBoard(ChessBoard chessBoard) {
        setBoard(chessBoard);
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        board[Math.abs(position.getRow()-8)][position.getColumn()-1] = piece;
        if (piece != null){
            if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                whitePieces.put(position, piece);
            }
            else {
                blackPieces.put(position, piece);
            }
        }
    }

    public void removePiece(ChessPosition position) {
        ChessPiece piece = getPiece(position);
        ChessGame.TeamColor color = piece.getTeamColor();
        board[Math.abs(position.getRow()-8)][position.getColumn()-1] = null;
        if (color==WHITE) {
            whitePieces.remove(position);
        }
        else {
            blackPieces.remove(position);
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
        return board[Math.abs(position.getRow()-8)][position.getColumn()-1];
    }

    public HashMap<ChessPosition, ChessPiece> getBlackPieces() {
        return blackPieces;
    }

    public HashMap getWhitePieces() {
        return whitePieces;
    }

    public HashMap getPieces(ChessGame.TeamColor color) {
        if (color == WHITE) {
            return whitePieces;
        }
        else {
            return blackPieces;
        }
    }

    public void setBoard(ChessBoard board) {
        board = board;
        ChessPosition position;
        ChessPiece piece;
        whitePieces = new HashMap<>();
        blackPieces = new HashMap<>();
        for (int i=1; i<9; i++) {
            for (int j=1; j<9; j++) {
                position = new ChessPosition(i, j);
                piece = board.getPiece(position);
                if (piece != null) {
                    if (piece.getTeamColor() == WHITE) {
                        whitePieces.put(position, piece);
                    }
                    else {
                        blackPieces.put(position, piece);
                    }
                }
            }
        }
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
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                // white pawns
                if (i==1) {
                    piece = new ChessPiece(WHITE, ChessPiece.PieceType.PAWN);
                    position = new ChessPosition(i+1, j+1);
                    addPiece(position, piece);
                }
                // black pawns
                else if (i==6) {
                    piece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
                    position = new ChessPosition(i+1, j+1);
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
                        position = new ChessPosition(i+1, j+1);
                        piece = new ChessPiece(WHITE, type);
                        addPiece(position, piece);
                        j++;
                    }
                    break;
                }
                // black first row
                else {
                    j = 0;
                    for (ChessPiece.PieceType type : pieceOrder) {
                        position = new ChessPosition(i+1, j+1);
                        piece = new ChessPiece(ChessGame.TeamColor.BLACK, type);
                        addPiece(position, piece);
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(board, that.board) && Objects.equals(whiteKing, that.whiteKing) && Objects.equals(blackKing, that.blackKing) && Objects.equals(whitePieces, that.whitePieces) && Objects.equals(blackPieces, that.blackPieces);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.deepHashCode(board), whitePieces, blackPieces, whiteKing, blackKing);
    }
}
