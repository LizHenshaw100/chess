package chess;

import java.util.*;

import chess.ChessPiece.PieceType;

import static chess.ChessPiece.PieceType.*;
import static java.lang.Math.abs;


/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    ChessBoard board;
    TeamColor turn;
    public ChessGame() {
        this.turn = TeamColor.WHITE;
        this.board = new ChessBoard();
        board.resetBoard();
    }

    public ChessGame(ChessGame oldGame) {
        this.turn = oldGame.getTeamTurn();
        this.board = new ChessBoard(oldGame.getBoard());
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return turn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        turn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece piece;
        TeamColor team;
        piece = board.getPiece(startPosition);
        if (piece == null) {
            return null;
        }
        team = piece.getTeamColor();
        Collection<ChessMove> moves = piece.pieceMoves(board, startPosition);
        ArrayList<ChessMove> validMoves = new ArrayList<>();
        ChessGame testGame;
        for (ChessMove move : moves) {
            testGame = new ChessGame(this);
            testGame.makeTestMove(move);
            if (!testGame.isInCheck(team)) {
                if (isCastling(move) ) {
                    if (!isInCheck(team)) {
                        ChessMove kingPath = getCastlingPath(move);
                        testGame = new ChessGame(this);
                        testGame.makeTestMove(kingPath);
                        if (!testGame.isInCheck(team)) {
                            validMoves.add(move);
                        }
                    }
                }
                else {
                    validMoves.add(move);
                }
            }
        }
        return validMoves;
    }

    public boolean testMove(ChessMove move, TeamColor color) {
        ChessGame testGame = new ChessGame(this);
        testGame.makeTestMove(move);
        return !testGame.isInCheck(color);
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition start = move.getStartPosition();
        ChessPosition end = move.getEndPosition();
        ChessPiece piece = board.getPiece(start);
        if (piece == null) {
            throw new InvalidMoveException();
        }
        TeamColor color = piece.getTeamColor();

        if (!isTurn(color)) {
            throw new InvalidMoveException();
        }

        HashSet<ChessMove> moveList = (HashSet<ChessMove>) piece.pieceMoves(board, start);

        for (ChessMove currentMove : moveList) {
            if (currentMove.getEndPosition().equals(end)) {
                //Test Move
                if (!testMove(move, color)) {
                    throw new InvalidMoveException();
                }
                if (board.getPiece(end) != null) {
                    board.removePiece(end);
                }
                PieceType promoPieceType = move.getPromotionPiece();
                if (promoPieceType != null) {
                    ChessPiece promoPiece = new ChessPiece(color, promoPieceType);
                    board.addPiece(end, promoPiece);
                    board.removePiece(start);
                } else {
                    //Check for en Passant
                    if (isEnPassant(move)) {
                        board.removePiece(getEnPassantPos(move, color));
                    }
                    if (isCastling(move)) {
                        if (isQueensideCastling(move)) {
                            ChessPosition rookPos;
                            rookPos = getQCastlingPos(color);
                            ChessPiece rook;
                            rook = board.getPiece(rookPos);
                            board.removePiece(rookPos);
                            ChessPosition newRookPos = getNewQCastlingPos(color);
                            board.addPiece(newRookPos, rook);
                        }
                        else {
                            ChessPosition rookPos;
                            rookPos = getKCastlingPos(color);
                            ChessPiece rook;
                            rook = board.getPiece(rookPos);
                            board.removePiece(rookPos);
                            ChessPosition newRookPos = getNewKCastlingPos(color);
                            board.addPiece(newRookPos, rook);
                        }
                    }
                    piece.move();
                    board.addPiece(end, piece);
                    board.removePiece(start);
                }
                swapTeamTurn();
                return;
            }
        }
        throw new InvalidMoveException();
    }

    public ChessPosition getNewQCastlingPos(TeamColor color) {
        ChessPosition pos;
        if (color == TeamColor.WHITE) {
            pos =  new ChessPosition(1, 4);
        }
        else {
            pos = new ChessPosition(8, 4);
        }
        return pos;
    }

    public ChessPosition getQCastlingPos(TeamColor color) {
        ChessPosition pos;
        if (color == TeamColor.WHITE) {
            pos = new ChessPosition(1, 1);
        }
        else {
            pos = new ChessPosition(8, 1);
        }
        return pos;
    }

    public boolean isQueensideCastling(ChessMove move) {
        ChessPosition start = move.getStartPosition();
        if (board.getPiece(start).getPieceType() != KING) {
            return false;
        }
        int sCol = start.getColumn();
        int eCol = move.getEndPosition().getColumn();
        return sCol - eCol > 1;
    }

    public ChessPosition getNewKCastlingPos(TeamColor color) {
        ChessPosition pos;
        if (color == TeamColor.WHITE) {
            pos =  new ChessPosition(1, 6);
        }
        else {
            pos = new ChessPosition(8, 6);
        }
        return pos;
    }

    public ChessPosition getKCastlingPos(TeamColor color) {
        ChessPosition pos;
        if (color == TeamColor.WHITE) {
            pos = new ChessPosition(1, 8);
        }
        else {
            pos = new ChessPosition(8, 8);
        }
        return pos;
    }

    public boolean isKingsideCastling(ChessMove move) {
        ChessPosition start = move.getStartPosition();
        if (board.getPiece(start).getPieceType() != KING) {
            return false;
        }
        int sCol = start.getColumn();
        int eCol = move.getEndPosition().getColumn();
        return eCol - sCol > 1;
    }

    public ChessMove getCastlingPath(ChessMove move) {
        ChessPosition start = move.getStartPosition();
        int sCol = start.getColumn();
        int eCol = move.getEndPosition().getColumn();
        int newCol = (sCol + eCol) / 2;
        ChessPosition end = new ChessPosition(start.getRow(), newCol);
        ChessMove newMove = new ChessMove(start, end, null);
        return newMove;
    }

    public boolean isCastling(ChessMove move) {
        if (board.getPiece(move.getStartPosition()).getPieceType() == KING) {
            return (abs(move.getStartPosition().getColumn()-move.getEndPosition().getColumn()) > 1);
        }
        return false;
    }

    public ChessPosition getEnPassantPos(ChessMove move, TeamColor color) {
        ChessPosition pawn;
        ChessPosition start = move.getStartPosition();
        ChessPosition end = move.getEndPosition();
        //end column and end row-1
        if (color == TeamColor.WHITE) {
            pawn = new ChessPosition(end.getRow()-1, end.getColumn());
        }
        else{
            pawn = new ChessPosition(end.getRow()+1, end.getColumn());
        }
        return pawn;
    }

    public boolean isEnPassant(ChessMove move) {
        ChessPosition start;
        ChessPosition end;
        start = move.getStartPosition();
        end = move.getEndPosition();
        if (board.getPiece(start).getPieceType() != PAWN) {
            return false;
        }
        int sRow = start.getRow();
        int sCol = start.getColumn();
        int eRow = end.getRow();
        int eCol = end.getColumn();
        int drow = (sRow - eRow);
        int dcol = (sCol - eCol);
        if (abs(dcol) != abs(drow)) {
            return false;
        }
        if (board.getPiece(end) != null) {
            return false;
        }
        ChessPosition position;
        if (board.getPiece(start).getTeamColor() == TeamColor.WHITE) {
            position = new ChessPosition(end.getRow()-1, end.getColumn());
        }
        else {
            position = new ChessPosition(end.getRow()+1, end.getColumn());
        }
        if (board.getPiece(position).canEnPassant()) {
            return true;
        }
        return false;

    }

    public void makeTestMove(ChessMove move) {
        ChessPosition start = move.getStartPosition();
        ChessPosition end = move.getEndPosition();
        TeamColor color = board.getPiece(start).getTeamColor();
        ChessPiece piece;
        if (move.getPromotionPiece() == null) {
            piece = board.getPiece(start);
        }
        else {
            piece = new ChessPiece(color, move.getPromotionPiece());
        }
        if (isEnPassant(move)) {
            board.removePiece(getEnPassantPos(move, color));
        }
        if (isQueensideCastling(move)) {
            ChessPosition CastlingPos = getQCastlingPos(color);
            ChessPiece rook = board.getPiece(CastlingPos);
            board.removePiece(CastlingPos);
            board.addPiece(getNewQCastlingPos(color), rook);
        }
        if (isKingsideCastling(move)) {
            ChessPosition CastlingPos = getKCastlingPos(color);
            ChessPiece rook = board.getPiece(CastlingPos);
            board.removePiece(CastlingPos);
            board.addPiece(getNewKCastlingPos(color), rook);
        }
        board.removePiece(start);
        if (board.getPiece(end) != null) {
            board.removePiece(end);
        }
        board.addPiece(end, piece);
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        if (myIsInCheck(teamColor) == null) {
            return false;
        }
        else {
            return true;
        }
    }

    public ChessPosition findKingPosition(HashMap<ChessPosition, ChessPiece> myPieces) {
        for (HashMap.Entry<ChessPosition, ChessPiece> entry : myPieces.entrySet()) {
            ChessPosition start = entry.getKey();
            ChessPiece piece = entry.getValue();
            if (piece.getPieceType().equals(PieceType.KING)) {
                return start;
            }
        }
        return null;
    }


    public ChessPosition myIsInCheck(TeamColor teamColor) {
        Collection<ChessMove> moveSet;
        ChessPosition kingPosition;
        TeamColor opponent;
        if (teamColor == TeamColor.WHITE) {
            opponent = TeamColor.BLACK;
        } else {
            opponent = TeamColor.WHITE;
        }
        HashMap<ChessPosition, ChessPiece> opponentPieces;
        HashMap<ChessPosition, ChessPiece> myPieces;
        opponentPieces = board.getPieces(opponent);
        myPieces = board.getPieces(teamColor);

        //Find kingPosition

        kingPosition = findKingPosition(myPieces);

        for (HashMap.Entry<ChessPosition, ChessPiece> entry : opponentPieces.entrySet()) {
            ChessPosition start = entry.getKey();
            ChessPiece piece = entry.getValue();
            moveSet = piece.pieceMoves(board, start);
            for (ChessMove move : moveSet) {
                if (move.getEndPosition().equals(kingPosition)) {
                    return start;
                }
            }
        }
        return null;
    }
    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        ChessPosition attackerPos;
        attackerPos = myIsInCheck(teamColor);
        if (attackerPos == null) {
            return false;
        }
        HashMap<ChessPosition, ChessPiece> myPieces = board.getPieces(teamColor);

        //Check King Moves
        ChessPosition kingPosition = findKingPosition(myPieces);
        ChessPiece king = board.getPiece(kingPosition);
        Collection<ChessMove> kingMoves = king.pieceMoves(board, kingPosition);
        for (ChessMove move : kingMoves) {
            if (testMove(move, teamColor)) {
                return false;
            }
        }
        //Get list of interception positions
        ArrayList<ChessPosition> interceptions = new ArrayList<>();
        ChessPosition interception;
        int row;
        int tempRow;
        int col;
        int tempCol;
        int bumpVal;
        int bumpVal2;
        int kingRow;
        int kingCol;
        ChessPiece attacker = board.getPiece(attackerPos);
        PieceType attackerType = attacker.getPieceType();
        // add attacker position to list of possible interceptions
        interceptions.add(attackerPos);
        if (attackerType == QUEEN || attackerType == ROOK) {
            row = kingPosition.getRow();
            col = kingPosition.getColumn();
            if (attackerPos.getRow() == kingPosition.getRow()) {
                tempCol = attackerPos.getColumn();
                bumpVal = ((col - tempCol) * -1);
                bumpVal = (bumpVal > 0) ? -1 : 1;
                while (tempCol+1 != col) {
                    tempCol+= bumpVal;
                    interception = new ChessPosition(row, tempCol);
                    interceptions.add(interception);
                }
            }
            if (attackerPos.getColumn() == kingPosition.getColumn()) {
                tempRow = attackerPos.getRow();
                bumpVal = ((row - tempRow) * -1);
                bumpVal = (bumpVal > 0) ? -1 : 1;
                while (tempRow+1 != row) {
                    tempRow+= bumpVal;
                    interception = new ChessPosition(tempRow, col);
                    interceptions.add(interception);
                }
            }
        }
        if (attackerType == QUEEN || attackerType == BISHOP) {
            row = attackerPos.getRow();
            col = attackerPos.getColumn();
            kingRow = kingPosition.getRow();
            kingCol = kingPosition.getColumn();
            int drow = (row - kingRow);
            int dcol = (col - kingCol);
            if (abs(dcol) == abs(drow)) {
                bumpVal = Integer.signum(drow) * -1;
                bumpVal2 = Integer.signum(dcol) * -1;

                while(row != kingRow) {
                    row += bumpVal;
                    col += bumpVal;
                    interception = new ChessPosition(row, col);
                    interceptions.add(interception);
                }
            }
        }

        //Check my Moves
        Collection<ChessMove> moveSet;
        for (HashMap.Entry<ChessPosition, ChessPiece> entry : myPieces.entrySet()) {
            ChessPosition start = entry.getKey();
            ChessPiece piece = entry.getValue();
            moveSet = piece.pieceMoves(board, start);
            for (ChessMove move : moveSet) {
                if (interceptions.contains(move.getEndPosition())) {
                    if (testMove(move, teamColor)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if (isInCheck(teamColor)) {
            return false;
        }
        HashMap<ChessPosition, ChessPiece> myPieces;
        Collection<ChessMove> moveSet;
        myPieces = board.getPieces(teamColor);
        for (HashMap.Entry<ChessPosition, ChessPiece> entry : myPieces.entrySet()) {
            ChessPosition start = entry.getKey();
            ChessPiece piece = entry.getValue();
            moveSet = validMoves(start);
            if (moveSet.size() != 0) {
                return false;
            }
        }
        return true;
    }

    public boolean isTurn(TeamColor color) {
        if (color == getTeamTurn()) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board.setBoard(board);
    }

    public void swapTeamTurn() {
        if (turn == TeamColor.WHITE) {
            turn = TeamColor.BLACK;
            resetPawns(TeamColor.BLACK);
        }
        else {
            turn = TeamColor.WHITE;
            resetPawns(TeamColor.WHITE);
        }
    }

    public void resetPawns(TeamColor color) {
        HashMap<ChessPosition, ChessPiece> myPieces;
        myPieces = board.getPieces(color);
        for (HashMap.Entry<ChessPosition, ChessPiece> entry : myPieces.entrySet()) {
            ChessPiece piece = entry.getValue();
            if (piece.getPieceType() == PAWN) {
                piece.resetEnPassant();
            }
        }
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return Objects.equals(board, chessGame.board) && turn == chessGame.turn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, turn);
    }
}
