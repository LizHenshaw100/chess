package chess.moves;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BishopMoveCalculator implements ChessInterface {
    private ChessBoard board;
    private ChessPosition start;
    private ChessGame.TeamColor team;
    private int row;
    private int col;



    public BishopMoveCalculator(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor team) {
        this.board = board;
        this.start = myPosition;
        this.team = team;
        this.row = start.getRow();
        this.col = start.getColumn();
    }

    public boolean goThroughDiag(int tempRow, int tempCol, List<ChessMove> legalMoves, ChessPosition end) {
            end = new ChessPosition(tempRow, tempCol);
            if (noOtherPiece(board, end)) {
                ChessMove legalMove = new ChessMove(start, end, null);
                legalMoves.add(legalMove);
                return false;
            }
            else if (isEnemy(board, end, team)) {
                ChessMove legalMove = new ChessMove(start, end, null);
                legalMoves.add(legalMove);
                return true;
            }
            else {
                return true;
            }
        }


    @Override
    public Collection<ChessMove> getLegalMoves() {
        List<ChessMove> legalMoves = new ArrayList<>();
        ChessPosition end = null;
        int tempRow = row;
        int tempCol = col;
        boolean isDone = false;
        //Check diagonal right
        while ((tempRow < 8) && (tempCol < 8) && !isDone) {
            tempRow++;
            tempCol++;
            isDone = goThroughDiag(tempRow, tempCol, legalMoves, end);
        }

        //Check diagonal left
        tempRow = row;
        tempCol = col;
        isDone = false;
        while ((tempRow < 8) && (tempCol > 1) && !isDone) {
            tempRow++;
            tempCol--;
            isDone = goThroughDiag(tempRow, tempCol, legalMoves, end);
        }

        //Check back diagonal left
        tempRow = row;
        tempCol = col;
        isDone = false;
        while ((tempRow > 1) && (tempCol > 1) && !isDone) {
            tempRow--;
            tempCol--;
            isDone = goThroughDiag(tempRow, tempCol, legalMoves, end);
        }

        //Check back diagonal right
        tempRow = row;
        tempCol = col;
        isDone = false;
        while ((tempRow > 1) && (tempCol < 8) && !isDone) {
            tempRow--;
            tempCol++;
            isDone = goThroughDiag(tempRow, tempCol, legalMoves, end);
        }

        return legalMoves;
    }


}
