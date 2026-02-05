package chess.moves;

import chess.*;
import chess.ChessInterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static chess.ChessPiece.PieceType.*;


public class PawnMoveCalculator implements ChessInterface {
    private ChessBoard board;
    private ChessPosition start;
    private ChessGame.TeamColor team;
    private int row;
    private int col;

    public PawnMoveCalculator(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor team) {
        this.board = board;
        this.start = myPosition;
        this.team = team;
        this.row = start.getRow();
        this.col = start.getColumn();
    }

    public void checkForPromotion(int row, ChessPosition end, Collection<ChessMove> legalMoves, ChessGame.TeamColor team) {
        ChessMove legalMove;
        ArrayList<ChessPiece.PieceType> pieceList = new ArrayList<>();
        pieceList.add(KNIGHT);
        pieceList.add(QUEEN);
        pieceList.add(BISHOP);
        pieceList.add(ROOK);
        int rowForPromo;
        if (team == ChessGame.TeamColor.WHITE) {
            rowForPromo = 7;
        }
        else {
            rowForPromo = 2;
        }

        if (row != rowForPromo) {
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
        HashSet<ChessMove> legalMoves = new HashSet<>();
        ChessMove legalMove;
        ChessPosition end;
        //Check moves for white
        if (team == ChessGame.TeamColor.WHITE) {
            //Check move forward 1
            end = new ChessPosition(row + 1, col);
            if (isValidPosition(end) && (noOtherPiece(board, end))) {
                checkForPromotion(row, end, legalMoves, team);
                //Check move forward 2
                end = new ChessPosition(row + 2, col);
                if (row == 2 && isValidPosition(end) && (noOtherPiece(board, end))) {
                    board.getPiece(start).setEnPassant();
                    checkForPromotion(row, end, legalMoves, team);
                }

            }


            //Check left diag take piece
            end = new ChessPosition(row + 1, col - 1);
            if ((isValidPosition(end)) && (isEnemy(board, end, team))) {
                checkForPromotion(row, end, legalMoves, team);
            }

            //Check right diag take piece
            end = new ChessPosition(row + 1, col + 1);
            if ((isValidPosition(end)) && (isEnemy(board, end, team))) {
                checkForPromotion(row, end, legalMoves, team);
            }

            ChessPosition enPassant;
            //Check en Passant left
            end = new ChessPosition(row + 1, col - 1);
            enPassant = new ChessPosition(row, col - 1);
            if (row == 5 && isValidPosition(end) && isPawn(enPassant)) {
                checkForPromotion(row, end, legalMoves, team);
            }

            //Check en Passant right
            end = new ChessPosition(row + 1, col + 1);
            enPassant = new ChessPosition(row, col + 1);
            if (row == 5 && isValidPosition(end) && isPawn(enPassant)) {
                checkForPromotion(row, end, legalMoves, team);
            }
        }

        //Check moves for black
        else {
            //Check move forward 1
            end = new ChessPosition(row - 1, col);
            if (isValidPosition(end) && (noOtherPiece(board, end))) {
                checkForPromotion(row, end, legalMoves, team);
                //Check move forward 2
                end = new ChessPosition(row - 2, col);
                if (row == 7 && isValidPosition(end) && (noOtherPiece(board, end))) {
                    board.getPiece(start).setEnPassant();
                    checkForPromotion(row, end, legalMoves, team);
                }

            }


            //Check left diag take piece
            end = new ChessPosition(row - 1, col - 1);
            if ((isValidPosition(end)) && (isEnemy(board, end, team))) {
                checkForPromotion(row, end, legalMoves, team);
            }

            //Check right diag take piece
            end = new ChessPosition(row - 1, col + 1);
            if ((isValidPosition(end)) && (isEnemy(board, end, team))) {
                checkForPromotion(row, end, legalMoves, team);
            }

            ChessPosition enPassant;
            //Check en Passant left
            end = new ChessPosition(row - 1, col - 1);
            enPassant = new ChessPosition(row, col - 1);
            if (row == 4 && isValidPosition(end) && isPawn(enPassant)) {
                checkForPromotion(row, end, legalMoves, team);
            }

            //Check en Passant right
            end = new ChessPosition(row - 1, col + 1);
            enPassant = new ChessPosition(row, col + 1);
            if (row == 4 && isValidPosition(end) && isPawn(enPassant)) {
                checkForPromotion(row, end, legalMoves, team);
            }
        }


        return legalMoves;
    }

    public boolean isPawn(ChessPosition position) {
        if (isValidPosition(position) && isEnemy(board, position, team)) {
            if (board.getPiece(position).getPieceType() == ChessPiece.PieceType.PAWN) {
                return board.getPiece(position).canEnPassant();
            }
        }
        return false;
    }
}

