package ui;

import java.util.Scanner;

public class ChessBoardTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ChessBoardString board = new ChessBoardString();
        System.out.println("What color?");
        var color = scanner.nextLine();
        try {
            System.out.println(board.getBoard(color));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
