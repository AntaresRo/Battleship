package battleship;

import java.util.Scanner;

public class PvPLogic {

    PalyerBoard player1 = new PalyerBoard();
    PalyerBoard player2 = new PalyerBoard();
    GameLogic gameLogicPlayerOne = new GameLogic();
    GameLogic gameLogicPlayerTwo = new GameLogic();
    Scanner scanner = new Scanner(System.in);



    private void nextPlayer() {
        boolean hasEnterBeenPressed = false;
        while (!hasEnterBeenPressed) {
            System.out.println();
            System.out.println("Press Enter and pass the move to another player");
            System.out.println("...");
            String userInput = scanner.nextLine();
            if (userInput.isEmpty()){
                hasEnterBeenPressed = true;
            }
        }
    }

    private void changeTurn() {
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press Enter and pass the move to another player");
        System.out.println("...");
        scanner.nextLine();
    }

    protected void playersSetup() {
        gameLogicPlayerOne.generateFogOfWarBoard(player1);
        gameLogicPlayerTwo.generateFogOfWarBoard(player2);

        System.out.println("Player 1, place your ships on the game field");
        System.out.println();
        player1.printBoard(player1.generateInitialBoard(player1.getPlayerBoard()));
        player1.isPlayerBoardSetupComplete();
        changeTurn();

        System.out.println("Player 2, place your ships on the game field");
        System.out.println();
        player2.printBoard(player2.generateInitialBoard(player2.getPlayerBoard()));
        player2.isPlayerBoardSetupComplete();
        changeTurn();

    }

    protected void playGame() {

        boolean isGameOver = false;

        while (!isGameOver) {
            player2.printBoard(player2.getFogOfWarBoard());
            System.out.println("---------------------");
            player1.printBoard(player1.getPlayerBoard());

            System.out.println();
            System.out.println("Player 1, it's your turn:");
            isGameOver = gameLogicPlayerOne.isTheGameOver(player2);
            if (isGameOver) {
                break;
            }
            changeTurn();

            player1.printBoard(player1.getFogOfWarBoard());
            System.out.println("---------------------");
            player2.printBoard(player2.getPlayerBoard());

            System.out.println();
            System.out.println("Player 2, it's your turn:");
            isGameOver = gameLogicPlayerTwo.isTheGameOver(player1);
            if (isGameOver) {
                break;
            }
            changeTurn();

        }


    }




}
