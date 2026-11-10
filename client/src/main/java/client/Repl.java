package client;

import java.util.Scanner;

import static java.awt.Color.BLUE;
import static java.awt.Color.RED;

public class Repl {
    private final ChessClient client;

    public Repl(String serverUrl) throws Exception {
        client = new ChessClient(serverUrl, this);
    }

    public void run() {
        System.out.println("Welcome to Chess");
        System.out.println(client.help());

        Scanner scanner = new Scanner(System.in);
        printPrompt();
        var result = scanner.nextLine();
        while (!result.equals("quit") && !result.equals("q")) {
            try {
                result = client.eval(result);
                System.out.print(BLUE + result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
            printPrompt();
            result = scanner.nextLine();
        }
        System.out.println();
    }

    private void printPrompt() {
        System.out.print("\n" + ">>> ");
    }

}
