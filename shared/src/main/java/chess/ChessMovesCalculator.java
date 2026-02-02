package chess;

import java.util.Collection;
import java.util.Objects;
import chess.*;
import chess.moves.*;
import chess.ChessPiece.PieceType;
import chess.ChessGame.TeamColor;

public class ChessMovesCalculator {
    private ChessPiece.PieceType type;
    private ChessGame.TeamColor team;
    private ChessBoard board;
    private ChessPosition myPosition;
    private ChessPiece.PieceType promotionPiece;
    private ChessInterface specializedCalculator;

    public ChessMovesCalculator(PieceType type, TeamColor team, ChessBoard newBoard, ChessPosition newPosition) {
        this.type = type;
        this.team = team;
        this.board = newBoard;
        this.myPosition = newPosition;

        if (type == ChessPiece.PieceType.PAWN) {
            this.specializedCalculator = new PawnMoveCalculator(board, myPosition, team);
        }
        else if (type == ChessPiece.PieceType.BISHOP) {
            this.specializedCalculator = new BishopMoveCalculator(board, myPosition, team);
        }
        else if (type == ChessPiece.PieceType.ROOK) {
            this.specializedCalculator = new RookMoveCalculator(board, myPosition, team);
        }
        else if (type == ChessPiece.PieceType.QUEEN) {
            this.specializedCalculator = new QueenMoveCalculator(board, myPosition, team);
        }
        else if (type == ChessPiece.PieceType.KING) {
            this.specializedCalculator = new KingMoveCalculator(board, myPosition, team);
        }
        else {
            this.specializedCalculator = new KnightMoveCalculator(board, myPosition, team);
        }

    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        return specializedCalculator.getLegalMoves();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessMovesCalculator that = (ChessMovesCalculator) o;
        boolean bool1 = type == that.type && Objects.equals(board, that.board);
        return bool1 && Objects.equals(myPosition, that.myPosition) && Objects.equals(specializedCalculator, that.specializedCalculator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, board, myPosition, specializedCalculator);
    }
}