package chess;

import java.util.Collection;
import java.util.HashMap;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    ChessGame.TeamColor pieceColor;
    ChessPiece.PieceType type;
    HashMap<ChessPiece.PieceType, String> nameAbbreviations;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;

        this.nameAbbreviations = new HashMap<>();
        nameAbbreviations.put(PieceType.KING, "K");
        nameAbbreviations.put(PieceType.QUEEN, "Q");
        nameAbbreviations.put(PieceType.BISHOP, "B");
        nameAbbreviations.put(PieceType.KNIGHT, "k");
        nameAbbreviations.put(PieceType.ROOK, "R");
        nameAbbreviations.put(PieceType.PAWN, "p");
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
        throw new RuntimeException("Not implemented");
    }

    @Override
    public String toString() {
        return nameAbbreviations.get(type);
    }
}
