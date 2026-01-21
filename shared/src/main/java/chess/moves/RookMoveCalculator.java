package chess.moves;

import chess.*;
import chess.ChessInterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;




public class RookMoveCalculator implements ChessInterface {
    private ChessBoard board;
    private ChessPosition start;
    private ChessGame.TeamColor team;
    private int row;
    private int col;

    public RookMoveCalculator(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor team) {
        this.board = board;
        this.start = myPosition;
        this.team = team;
        this.row = start.getRow();
        this.col = start.getColumn();
    }

    @Override
    public Collection<ChessMove> getLegalMoves() {
        List<ChessMove> legalMoves = new ArrayList<>();
        int tempRow = row;
        int tempCol = col;
        //Check front
        while (tempRow < 8) {
            tempRow++;
            ChessPosition end = new ChessPosition(tempRow, tempCol);
            if (noOtherPiece(board, end)) {
                ChessMove legalMove = new ChessMove(start, end, null);
                legalMoves.add(legalMove);
            }
            else if (isFriendly(board, end, team)) {
                break;
            }
            else {
                ChessMove legalMove = new ChessMove(start, end, null);
                legalMoves.add(legalMove);
                break;
            }
        }

        //Check right
        tempRow = row;
        while (tempCol < 8) {
            tempCol++;
            ChessPosition end = new ChessPosition(tempRow, tempCol);
            if (noOtherPiece(board, end)) {
                ChessMove legalMove = new ChessMove(start, end, null);
                legalMoves.add(legalMove);
            }
            else if (isFriendly(board, end, team)) {
                break;
            }
            else {
                ChessMove legalMove = new ChessMove(start, end, null);
                legalMoves.add(legalMove);
                break;
            }
        }

        //Check back
        tempCol = col;
        while (tempRow > 1) {
            tempRow--;
            ChessPosition end = new ChessPosition(tempRow, tempCol);
            if (noOtherPiece(board, end)) {
                ChessMove legalMove = new ChessMove(start, end, null);
                legalMoves.add(legalMove);
            }
            else if (isFriendly(board, end, team)) {
                break;
            }
            else {
                ChessMove legalMove = new ChessMove(start, end, null);
                legalMoves.add(legalMove);
                break;
            }
        }

        //Check left
        tempRow = row;
        while (tempCol > 1) {
            tempCol--;
            ChessPosition end = new ChessPosition(tempRow, tempCol);
            if (noOtherPiece(board, end)) {
                ChessMove legalMove = new ChessMove(start, end, null);
                legalMoves.add(legalMove);
            }
            else if (isFriendly(board, end, team)) {
                break;
            }
            else {
                ChessMove legalMove = new ChessMove(start, end, null);
                legalMoves.add(legalMove);
                break;
            }
        }

        return legalMoves;
    }
}

