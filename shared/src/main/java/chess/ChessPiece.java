package chess;

import java.util.Collection;
import java.util.Objects;
import java.util.Map;
import java.util.HashMap;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private ChessGame.TeamColor pieceColor;
    private ChessPiece.PieceType type;
    private Map<ChessPiece.PieceType, String> name_abbreviations;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
        //The abbreviations for chess pieces
        this.name_abbreviations = new HashMap<>();
        name_abbreviations.put(PieceType.KING, "K");
        name_abbreviations.put(PieceType.QUEEN, "Q");
        name_abbreviations.put(PieceType.BISHOP, "B");
        name_abbreviations.put(PieceType.KNIGHT, "k");
        name_abbreviations.put(PieceType.ROOK, "R");
        name_abbreviations.put(PieceType.PAWN, "p");
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }



    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }


    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessMovesCalculator calculator = new ChessMovesCalculator(type, board, myPosition);
        return calculator.pieceMoves(board, myPosition);
    }

    @Override
    public String toString() {
        return name_abbreviations.get(type);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }
}
