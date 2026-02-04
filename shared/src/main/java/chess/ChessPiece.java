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
    private static int moveCount;
    private Map<ChessPiece.PieceType, String> nameAbbreviations;


    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
        this.moveCount = 0;
        //The abbreviations for chess pieces
        this.nameAbbreviations = new HashMap<>();
        nameAbbreviations.put(PieceType.KING, "K");
        nameAbbreviations.put(PieceType.QUEEN, "Q");
        nameAbbreviations.put(PieceType.BISHOP, "B");
        nameAbbreviations.put(PieceType.KNIGHT, "k");
        nameAbbreviations.put(PieceType.ROOK, "R");
        nameAbbreviations.put(PieceType.PAWN, "p");
    }

    public ChessPiece(ChessPiece piece) {
        this.pieceColor = piece.getTeamColor();
        this.type = piece.getPieceType();
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

    public static void move() {
        moveCount += 1;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void setType(PieceType type) {
        this.type = type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessMovesCalculator calculator = new ChessMovesCalculator(type, pieceColor, board, myPosition);
        return calculator.pieceMoves(board, myPosition);
    }

    @Override
    public String toString() {
        return nameAbbreviations.get(type);
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
