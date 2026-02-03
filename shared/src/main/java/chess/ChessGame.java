package chess;

import java.util.*;

import chess.ChessPiece.PieceType;


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
        Collection<ChessMove> validMoves = null;
        ChessGame testGame;
        for (ChessMove move : moves) {
            testGame = new ChessGame(this);
            testGame.makeTestMove(move);
            if (!testGame.isInCheck(team)) {
                validMoves.add(move);
            }
        }
        return validMoves;
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

        Iterator<ChessMove> it = moveList.iterator();
        while (it.hasNext()) {
            ChessMove currentMove = it.next();
            if (currentMove.getEndPosition().equals(end)) {
                ChessGame testGame = new ChessGame(this);
                testGame.makeTestMove(move);
                if (testGame.isInCheck(color)) {
                    throw new InvalidMoveException();
                }

                if (board.getPiece(end) != null) {
                    board.removePiece(end);
                }
                PieceType promoPieceType = move.getPromotionPiece();
                if (promoPieceType!= null) {
                    ChessPiece promoPiece = new ChessPiece(color, promoPieceType);
                    board.addPiece(end, promoPiece);
                    board.removePiece(start);
                }
                else {
                    TeamColor team = piece.getTeamColor();
                    board.addPiece(end, piece);
                    board.removePiece(start);
                }
                swapTeamTurn();
                return;
            }
        }
        throw new InvalidMoveException();
    }

    public void makeTestMove(ChessMove move) {
        ChessPosition start = move.getStartPosition();
        ChessPosition end = move.getEndPosition();
        ChessPiece piece = board.getPiece(start);
        board.removePiece(start);
        board.addPiece(end, piece);
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        Collection<ChessMove> moveSet;
        ChessPosition kingPosition = null;
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

        for (HashMap.Entry<ChessPosition, ChessPiece> entry : myPieces.entrySet()) {
            ChessPosition start = entry.getKey();
            ChessPiece piece = entry.getValue();
            if (piece.getPieceType().equals(PieceType.KING)) {
                kingPosition = start;
                break;
            }
        }
        for (HashMap.Entry<ChessPosition, ChessPiece> entry : opponentPieces.entrySet()) {
            ChessPosition start = entry.getKey();
            ChessPiece piece = entry.getValue();
            moveSet = piece.pieceMoves(board, start);
            for (ChessMove move : moveSet) {
                if (move.getEndPosition().equals(kingPosition)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
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
        }
        else {
            turn = TeamColor.WHITE;
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
