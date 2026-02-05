package chess.moves;

import chess.*;
import chess.ChessInterface;

import java.util.HashSet;
import java.util.Collection;

import static chess.ChessGame.TeamColor.WHITE;
import static chess.ChessPiece.PieceType.ROOK;



public class KingMoveCalculator implements ChessInterface {
    private ChessBoard board;
    private ChessPosition start;
    private ChessGame.TeamColor team;
    private int row;
    private int col;

    public KingMoveCalculator(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor team) {
        this.board = board;
        this.start = myPosition;
        this.team = team;
        this.row = start.getRow();
        this.col = start.getColumn();
    }

    @Override
    public Collection<ChessMove> getLegalMoves() {
        HashSet<ChessMove> legalMoves = new HashSet<>();
        //Check front
        ChessPosition end = new ChessPosition(row+1, col);
        singleSpaceChecker(board, end, legalMoves, start, team);

        //Check front right diag
        end = new ChessPosition(row+1, col+1);
        singleSpaceChecker(board, end, legalMoves, start, team);

        //Check right
        end = new ChessPosition(row, col+1);
        singleSpaceChecker(board, end, legalMoves, start, team);

        //Check back right diag
        end = new ChessPosition(row-1, col+1);
        singleSpaceChecker(board, end, legalMoves, start, team);

        //Check back
        end = new ChessPosition(row-1, col);
        singleSpaceChecker(board, end, legalMoves, start, team);

        //Check back left diag
        end = new ChessPosition(row-1, col-1);
        singleSpaceChecker(board, end, legalMoves, start, team);

        //Check left
        end = new ChessPosition(row, col-1);
        singleSpaceChecker(board, end, legalMoves, start, team);

        //Check front left diag
        end = new ChessPosition(row+1, col-1);
        singleSpaceChecker(board, end, legalMoves, start, team);

        //Check for Queen's side castling
        ChessPiece king = board.getPiece(start);
        if (king.getMoveCount() == 0)
        {
            //Get color
            if (king.getTeamColor() == WHITE) {
                ChessPosition qCastlePosition = new ChessPosition(1, 1);
                //Check if there is a piece in Queen's side castle spot
                if (board.getPiece(qCastlePosition) != null) {
                    //Check if piece in Queen's side castle spot is a Rook and hasn't moved
                    if (board.getPiece(qCastlePosition).getPieceType() == ROOK && board.getPiece(qCastlePosition).getMoveCount() == 0){
                        //Check if all of the pieces inbetween are empty
                        ChessPosition position;
                        boolean canQueensideCastle = true;
                        for(int i = 2; i<5; i++) {
                            position = new ChessPosition(1, i);
                            if (!noOtherPiece(board, position)) {
                                canQueensideCastle = false;
                            }
                        }
                        if (canQueensideCastle) {
                            end = new ChessPosition(1, 3);
                            ChessMove legalMove = new ChessMove(start, end, null);
                            legalMoves.add(legalMove);
                        }
                    }
                }
            }
            else {
                ChessPosition qCastlePosition = new ChessPosition(8, 1);
                //Check if there is a piece in Queen's side castle spot
                if (board.getPiece(qCastlePosition) != null) {
                    //Check if piece in Queen's side castle spot is a Rook and hasn't moved
                    if (board.getPiece(qCastlePosition).getPieceType() == ROOK && board.getPiece(qCastlePosition).getMoveCount() == 0){
                        //Check if all of the pieces inbetween are empty
                        ChessPosition position;
                        boolean canQueensideCastle = true;
                        for(int i = 2; i<5; i++) {
                            position = new ChessPosition(8, i);
                            if (!noOtherPiece(board, position)) {
                                canQueensideCastle = false;
                            }
                        }
                        if (canQueensideCastle) {
                            end = new ChessPosition(8, 3);
                            ChessMove legalMove = new ChessMove(start, end, null);
                            legalMoves.add(legalMove);
                        }
                    }
                }

            }
        }



        //Check for King's side castling
        if (king.getMoveCount() == 0) {
            //Get color
            if (king.getTeamColor() == WHITE) {
                ChessPosition kCastlePosition = new ChessPosition(1, 8);
                //Check if there is a piece in Queen's side castle spot
                if (board.getPiece(kCastlePosition) != null) {
                    //Check if piece in Queen's side castle spot is a Rook and hasn't moved
                    if (board.getPiece(kCastlePosition).getPieceType() == ROOK && board.getPiece(kCastlePosition).getMoveCount() == 0){
                        //Check if all of the pieces inbetween are empty
                        ChessPosition position;
                        boolean canKingsideCastle = true;
                        for(int i = 6; i<8; i++) {
                            position = new ChessPosition(1, i);
                            if (!noOtherPiece(board, position)) {
                                canKingsideCastle = false;
                            }
                        }
                        if (canKingsideCastle) {
                            end = new ChessPosition(1, 7);
                            ChessMove legalMove = new ChessMove(start, end, null);
                            legalMoves.add(legalMove);
                        }
                    }
                }
            }
            else {
                ChessPosition kCastlePosition = new ChessPosition(8, 8);
                //Check if there is a piece in Queen's side castle spot
                if (board.getPiece(kCastlePosition) != null) {
                    //Check if piece in Queen's side castle spot is a Rook and hasn't moved
                    if (board.getPiece(kCastlePosition).getPieceType() == ROOK && board.getPiece(kCastlePosition).getMoveCount() == 0){
                        //Check if all of the pieces inbetween are empty
                        ChessPosition position;
                        boolean canKingsideCastle = true;
                        for(int i = 6; i<8; i++) {
                            position = new ChessPosition(8, i);
                            if (!noOtherPiece(board, position)) {
                                canKingsideCastle = false;
                            }
                        }
                        if (canKingsideCastle) {
                            end = new ChessPosition(8, 7);
                            ChessMove legalMove = new ChessMove(start, end, null);
                            legalMoves.add(legalMove);
                        }
                    }
                }

            }
        }

        return legalMoves;
    }
}

