package chess.moves;

import chess.*;
import chess.ChessInterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static chess.ChessPiece.PieceType.*;


public class PawnMoveCalculator implements ChessInterface {
    private ChessBoard board;
    private ChessPosition start;
    private ChessGame.TeamColor team;
    private ChessPiece.PieceType promotionPiece;
    private int row;
    private int col;

    public PawnMoveCalculator(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor team, ChessPiece.PieceType promotionPiece) {
        this.board = board;
        this.start = myPosition;
        this.team = team;
        this.row = start.getRow();
        this.col = start.getColumn();
    }

    public void CheckForWhitePromotion(int row, ChessPosition end, Collection<ChessMove> legalMoves) {
        ChessMove legalMove;
        ArrayList<ChessPiece.PieceType> pieceList = new ArrayList<>();
        pieceList.add(KNIGHT);
        pieceList.add(QUEEN);
        pieceList.add(BISHOP);
        pieceList.add(ROOK);
        if (row + 1 < 8) {
            legalMove = new ChessMove(start, end, null);
            legalMoves.add(legalMove);
        }
        else {
            ChessPiece.PieceType promoPiece;
            for (int i=0; i<pieceList.size(); i++) {
                promoPiece = pieceList.get(i);
                legalMove = new ChessMove(start, end, promoPiece);
                legalMoves.add(legalMove);
            }
        }
    }

    public void CheckForBlackPromotion(int row, ChessPosition end, Collection<ChessMove> legalMoves) {
        ChessMove legalMove;
        ArrayList<ChessPiece.PieceType> pieceList = new ArrayList<>();
        pieceList.add(KNIGHT);
        pieceList.add(QUEEN);
        pieceList.add(BISHOP);
        pieceList.add(ROOK);
        if (row - 1 > 1) {
            legalMove = new ChessMove(start, end, null);
            legalMoves.add(legalMove);
        }
        else {
            ChessPiece.PieceType promoPiece;
            for (int i=0; i<pieceList.size(); i++) {
                promoPiece = pieceList.get(i);
                legalMove = new ChessMove(start, end, promoPiece);
                legalMoves.add(legalMove);
            }
        }
    }

    @Override
    public Collection<ChessMove> getLegalMoves() {
        List<ChessMove> legalMoves = new ArrayList<>();
        ChessMove legalMove;
        ChessPosition end;
        //Check forward moves for white
        if (team == ChessGame.TeamColor.WHITE) {
            //Check move forward 1
            end = new ChessPosition(row + 1, col);
            if (isValidPosition(end) && (noOtherPiece(board, end))) {
                CheckForWhitePromotion(row, end, legalMoves);
                //Check move forward 2
                end = new ChessPosition(row + 2, col);
                if (row == 2 && isValidPosition(end) && (noOtherPiece(board, end))) {
                    CheckForWhitePromotion(row, end, legalMoves);
                }

            }


            //Check left diag take piece
            end = new ChessPosition(row + 1, col - 1);
            if ((isValidPosition(end)) && (isEnemy(board, end, team))) {
                CheckForWhitePromotion(row, end, legalMoves);
            }

            //Check right diag take piece
            end = new ChessPosition(row + 1, col + 1);
            if ((isValidPosition(end)) && (isEnemy(board, end, team))) {
                CheckForWhitePromotion(row, end, legalMoves);
            }
        }

        //Check forward moves for black
        else {
            //Check move forward 1
            end = new ChessPosition(row - 1, col);
            if (isValidPosition(end) && (noOtherPiece(board, end))) {
                CheckForBlackPromotion(row, end, legalMoves);
                //Check move forward 2
                end = new ChessPosition(row - 2, col);
                if (row == 7 && isValidPosition(end) && (noOtherPiece(board, end))) {
                    CheckForBlackPromotion(row, end, legalMoves);
                }

            }


            //Check left diag take piece
            end = new ChessPosition(row - 1, col - 1);
            if ((isValidPosition(end)) && (isEnemy(board, end, team))) {
                CheckForBlackPromotion(row, end, legalMoves);
            }

            //Check right diag take piece
            end = new ChessPosition(row - 1, col + 1);
            if ((isValidPosition(end)) && (isEnemy(board, end, team))) {
                CheckForBlackPromotion(row, end, legalMoves);
            }
        }

        return legalMoves;
    }
}

