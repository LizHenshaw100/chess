package chess;

import java.util.Collection;
import java.util.Objects;

public class ChessMovesCalculator {
    private ChessPiece.PieceType type;
    private 
    private ChessBoard board;
    private ChessPosition myPosition;
    private ChessInterface SpecializedCalculator;

    public ChessMovesCalculator(ChessPiece.PieceType type, ChessBoard newBoard, ChessPosition newPosition) {
        this.type = type;
        this.board = newBoard;
        this.myPosition = newPosition;
        //if (type == ChessPiece.PieceType.PAWN) {
            //this.SpecializedCalculator = new
        //}
        if (type == ChessPiece.PieceType.BISHOP) {
            this.SpecializedCalculator = new BishopMoveCalculator(board, myPosition, );
        }
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        return SpecializedCalculator.GetLegalMoves();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessMovesCalculator that = (ChessMovesCalculator) o;
        return type == that.type && Objects.equals(board, that.board) && Objects.equals(myPosition, that.myPosition) && Objects.equals(SpecializedCalculator, that.SpecializedCalculator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, board, myPosition, SpecializedCalculator);
    }
}