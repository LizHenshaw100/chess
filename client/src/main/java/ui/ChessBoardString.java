package ui;

import static ui.EscapeSequences.*;

public class ChessBoardString {
    String whiteHeader = SET_BG_COLOR_MAGENTA + "    a  b  c  d  e  f  g  h    " + RESET_BG_COLOR;
    String blackHeader = SET_BG_COLOR_MAGENTA + "    h  g  f  e  d  c  b  a    " + RESET_BG_COLOR;
    String whiteAndBlackSpaces = SET_BG_COLOR_WHITE + EMPTY + SET_BG_COLOR_BLACK + EMPTY;
    String blackAndWhiteSpaces = SET_BG_COLOR_BLACK + EMPTY + SET_BG_COLOR_WHITE + EMPTY;
    String middleWhiteRow = whiteAndBlackSpaces.repeat(4);
    String middleBlackRow = blackAndWhiteSpaces.repeat(4);
    String whitePawn = SET_BG_COLOR_WHITE + WHITE_PAWN + SET_BG_COLOR_BLACK + WHITE_PAWN + RESET_BG_COLOR;
    String whitePawnRow = SET_TEXT_COLOR_BLUE + whitePawn.repeat(4) + RESET_TEXT_COLOR;
    String whitePawnB = SET_BG_COLOR_BLACK + WHITE_PAWN + SET_BG_COLOR_WHITE + WHITE_PAWN + RESET_BG_COLOR;
    String whitePawnRowB = SET_TEXT_COLOR_BLUE + whitePawnB.repeat(4) + RESET_TEXT_COLOR;
    String whiteRow = SET_TEXT_COLOR_BLUE + SET_BG_COLOR_BLACK + WHITE_ROOK + SET_BG_COLOR_WHITE + WHITE_KNIGHT + SET_BG_COLOR_BLACK +
            WHITE_BISHOP + SET_BG_COLOR_WHITE + WHITE_QUEEN + SET_BG_COLOR_BLACK + WHITE_KING + SET_BG_COLOR_WHITE +
            WHITE_BISHOP + SET_BG_COLOR_BLACK + WHITE_KNIGHT + SET_BG_COLOR_WHITE + WHITE_ROOK + RESET_BG_COLOR + RESET_TEXT_COLOR;
    String whiteRowB = SET_TEXT_COLOR_BLUE + SET_BG_COLOR_WHITE + WHITE_ROOK + SET_BG_COLOR_BLACK + WHITE_KNIGHT + SET_BG_COLOR_WHITE +
            WHITE_BISHOP + SET_BG_COLOR_BLACK + WHITE_KING + SET_BG_COLOR_WHITE + WHITE_QUEEN + SET_BG_COLOR_BLACK +
            WHITE_BISHOP + SET_BG_COLOR_WHITE + WHITE_KNIGHT + SET_BG_COLOR_BLACK + WHITE_ROOK + RESET_BG_COLOR + RESET_TEXT_COLOR;
    String blackPawn = SET_TEXT_COLOR_RED + SET_BG_COLOR_BLACK + BLACK_PAWN + SET_BG_COLOR_WHITE + BLACK_PAWN + RESET_BG_COLOR + RESET_TEXT_COLOR;
    String blackPawnRow = blackPawn.repeat(4);
    String blackPawnB = SET_TEXT_COLOR_RED + SET_BG_COLOR_WHITE + BLACK_PAWN + SET_BG_COLOR_BLACK + BLACK_PAWN + RESET_BG_COLOR + RESET_TEXT_COLOR;
    String blackPawnRowB = blackPawnB.repeat(4);
    String blackRow = SET_TEXT_COLOR_RED + SET_BG_COLOR_WHITE + BLACK_ROOK + SET_BG_COLOR_BLACK + BLACK_KNIGHT + SET_BG_COLOR_WHITE +
            BLACK_BISHOP + SET_BG_COLOR_BLACK + BLACK_QUEEN + SET_BG_COLOR_WHITE + BLACK_KING + SET_BG_COLOR_BLACK +
            BLACK_BISHOP + SET_BG_COLOR_WHITE + BLACK_KNIGHT + SET_BG_COLOR_BLACK + BLACK_ROOK + RESET_TEXT_COLOR;
    String blackRowB = SET_TEXT_COLOR_RED + SET_BG_COLOR_BLACK + BLACK_ROOK + SET_BG_COLOR_WHITE + BLACK_KNIGHT + SET_BG_COLOR_BLACK +
    BLACK_BISHOP + SET_BG_COLOR_WHITE + BLACK_KING + SET_BG_COLOR_BLACK + BLACK_QUEEN + SET_BG_COLOR_WHITE +
    BLACK_BISHOP + SET_BG_COLOR_BLACK + BLACK_KNIGHT + SET_BG_COLOR_WHITE + BLACK_ROOK + RESET_TEXT_COLOR;

    public String getBoard(String color) throws Exception{
        if (!color.equals("WHITE") && !color.equals("BLACK")) {
            throw new Exception("Not a valid color to get the board String");
        }
        String board = "";
        if (color.equals("WHITE")) {
            board += whiteHeader + "\n";
            board += SET_BG_COLOR_MAGENTA + " 8 " + blackRow + SET_BG_COLOR_MAGENTA + " 8 " + RESET_BG_COLOR + "\n";
            board += SET_BG_COLOR_MAGENTA + " 7 " + blackPawnRow + SET_BG_COLOR_MAGENTA + " 7 " + RESET_BG_COLOR + "\n";
            board += SET_BG_COLOR_MAGENTA + " 6 " + middleWhiteRow + SET_BG_COLOR_MAGENTA + " 6 " + RESET_BG_COLOR + "\n";
            board += SET_BG_COLOR_MAGENTA + " 5 " + middleBlackRow + SET_BG_COLOR_MAGENTA + " 5 " + RESET_BG_COLOR + "\n";
            board += SET_BG_COLOR_MAGENTA + " 4 " + middleWhiteRow + SET_BG_COLOR_MAGENTA + " 4 " + RESET_BG_COLOR + "\n";
            board += SET_BG_COLOR_MAGENTA + " 3 " + middleBlackRow + SET_BG_COLOR_MAGENTA + " 3 " + RESET_BG_COLOR + "\n";
            board += SET_BG_COLOR_MAGENTA + " 2 " + whitePawnRow + SET_BG_COLOR_MAGENTA + " 2 " + RESET_BG_COLOR + "\n";
            board += SET_BG_COLOR_MAGENTA + " 1 " + whiteRow + SET_BG_COLOR_MAGENTA + " 1 " + RESET_BG_COLOR;
            board += "\n" + whiteHeader;
        }
        else {
            board += blackHeader + "\n";
            board += SET_BG_COLOR_MAGENTA + " 1 " + whiteRowB + SET_BG_COLOR_MAGENTA + " 1 " + RESET_BG_COLOR + "\n";
            board += SET_BG_COLOR_MAGENTA + " 2 " + whitePawnRowB + SET_BG_COLOR_MAGENTA + " 2 " + RESET_BG_COLOR + "\n";
            board += SET_BG_COLOR_MAGENTA + " 3 " + middleWhiteRow + SET_BG_COLOR_MAGENTA + " 3 " + RESET_BG_COLOR + "\n";
            board += SET_BG_COLOR_MAGENTA + " 4 " + middleBlackRow + SET_BG_COLOR_MAGENTA + " 4 " + RESET_BG_COLOR + "\n";
            board += SET_BG_COLOR_MAGENTA + " 5 " + middleWhiteRow + SET_BG_COLOR_MAGENTA + " 5 " + RESET_BG_COLOR + "\n";
            board += SET_BG_COLOR_MAGENTA + " 6 " + middleBlackRow + SET_BG_COLOR_MAGENTA + " 6 " + RESET_BG_COLOR + "\n";
            board += SET_BG_COLOR_MAGENTA + " 7 " + blackPawnRowB + SET_BG_COLOR_MAGENTA + " 7 " + RESET_BG_COLOR + "\n";
            board += SET_BG_COLOR_MAGENTA + " 8 " + blackRowB + SET_BG_COLOR_MAGENTA + " 8 " + RESET_BG_COLOR;
            board += "\n" + blackHeader;
        }
        return board;
    }
}
