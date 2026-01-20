package chess;

import java.util.Collection;
import java.util.Objects;
import chess.*;
import chess.moves.*;

public class ChessMovesCalculator {
    private ChessPiece.PieceType type;
    private ChessGame.TeamColor team;
    private ChessBoard board;
    private ChessPosition myPosition;
    private ChessPiece.PieceType promotionPiece;
    private ChessInterface SpecializedCalculator;

    public ChessMovesCalculator(ChessPiece.PieceType type, ChessGame.TeamColor team, ChessBoard newBoard, ChessPosition newPosition, ChessPiece.PieceType promotionPiece) {
        this.type = type;
        this.team = team;
        this.board = newBoard;
        this.myPosition = newPosition;
        this.promotionPiece = promotionPiece;
        if (type == ChessPiece.PieceType.PAWN) {
            this.SpecializedCalculator = new PawnMoveCalculator(board, myPosition, team, promotionPiece);
        }
        if (type == ChessPiece.PieceType.BISHOP) {
            this.SpecializedCalculator = new BishopMoveCalculator(board, myPosition, team);
        }
        else if (type == ChessPiece.PieceType.ROOK) {
            this.SpecializedCalculator = new RookMoveCalculator(board, myPosition, team);
        }
        else if (type == ChessPiece.PieceType.QUEEN) {
            this.SpecializedCalculator = new QueenMoveCalculator(board, myPosition, team);
        }
        else if (type == ChessPiece.PieceType.KING) {
            this.SpecializedCalculator = new KingMoveCalculator(board, myPosition, team);
        }
        else if (type == ChessPiece.PieceType.KNIGHT) {
            this.SpecializedCalculator = new KnightMoveCalculator(board, myPosition, team);
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