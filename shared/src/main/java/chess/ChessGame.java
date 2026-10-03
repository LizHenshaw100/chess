package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

/**
 * A class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    TeamColor turn;
    ChessBoard board;

    public ChessGame() {
        turn = TeamColor.WHITE;
        board = new ChessBoard();
        board.resetBoard();
    }

    public ChessGame(ChessGame oldGame) {
        turn = oldGame.getTeamTurn();
        board = new ChessBoard(oldGame.getBoard());
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return turn;
    }

    public void swapTeamTurn() {
        if (getTeamTurn() == TeamColor.WHITE) {
            setTeamTurn(TeamColor.BLACK);
        }
        else {
            setTeamTurn(TeamColor.WHITE);
        }
    }

    /**
     * Sets which teams turn it is
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
     * Gets all valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece piece = board.getPiece(startPosition);
        if (piece == null) {
            return null;
        }
        Collection<ChessMove> valid = new ArrayList<>();
        for (ChessMove move : piece.pieceMoves(board, startPosition)) {
            if (testMove(move, piece.getTeamColor())) {
                valid.add(move);
            }
        }
        return valid;
    }

    /**
     * Makes a move in the chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition start = move.getStartPosition();
        ChessPiece piece = board.getPiece(start);
        if (piece == null) {
            throw new InvalidMoveException();
        }
        TeamColor color = piece.getTeamColor();
        if (getTeamTurn() != color) {
            throw new InvalidMoveException();
        }
        Collection<ChessMove> valid = validMoves(start);
        if (!valid.contains(move)) {
            throw new InvalidMoveException();
        }
        makeTestMove(move);
        swapTeamTurn();
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
        board.removePiece(start);
        if (board.getPiece(end) != null) {
            board.removePiece(end);
        }
        board.addPiece(end, piece);
    }

    public boolean testMove(ChessMove move, TeamColor color) {
        ChessGame testGame = new ChessGame(this);
        testGame.makeTestMove(move);
        return !testGame.isInCheck(color);
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        return myIsInCheck(teamColor) != null;
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

    public ChessPosition findKingPosition(HashMap<ChessPosition, ChessPiece> myPieces) {
        for (HashMap.Entry<ChessPosition, ChessPiece> entry : myPieces.entrySet()) {
            ChessPosition start = entry.getKey();
            ChessPiece piece = entry.getValue();
            if (piece.getPieceType().equals(ChessPiece.PieceType.KING)) {
                return start;
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
    public boolean isInCheckmate(TeamColor teamColor) throws InvalidMoveException {
        return isInCheck(teamColor) && !hasValidMoves(teamColor);
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        return !isInCheck(teamColor) && !hasValidMoves(teamColor);
    }

    boolean hasValidMoves(TeamColor teamColor) {
        for (ChessPosition pos : board.getPieces(teamColor).keySet()) {
            if (!validMoves(pos).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sets this game's chessboard to a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
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
        return turn == chessGame.turn && Objects.equals(getBoard(), chessGame.getBoard());
    }

    @Override
    public int hashCode() {
        return Objects.hash(turn, getBoard());
    }
}
