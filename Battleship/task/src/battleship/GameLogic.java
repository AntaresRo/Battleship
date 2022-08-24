package battleship;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameLogic {

    private Scanner scanner = new Scanner(System.in);
    private int[] VALIDATED_COORDINATES = new int[2];
    private boolean SHOT_HIT;
    private int numberOfShipsSunken = 0;
    protected void generateFogOfWarBoard(PalyerBoard board) {
        board.generateInitialBoard(board.getFogOfWarBoard());
        board.getFogOfWarBoard();
    }
    protected void gameStartingAnnouncement(PalyerBoard board) {
        generateFogOfWarBoard(board);
        System.out.println();
        System.out.println("The game starts!");
        System.out.println();
        board.printBoard(board.getFogOfWarBoard());
    }

    private int [] stringConverter(String userInput) {
        userInput = userInput.trim().toUpperCase();
        int[]coordinates = new int[2];

        Pattern letterPattern = Pattern.compile("[A-Z]");
        Pattern numbersPattern = Pattern.compile("\\d\\d?");
        Matcher letterMatcher = letterPattern.matcher(userInput);
        Matcher numbersMatcher = numbersPattern.matcher(userInput);

        while (letterMatcher.find()){
            coordinates[0] = Integer.parseInt(String.valueOf(letterMatcher.group().charAt(0) - 64));
        }
        while (numbersMatcher.find()) {
            coordinates[1] = Integer.parseInt(String.valueOf(numbersMatcher.group()));
        }
        return coordinates;
    }

    private boolean areCoordinateValid(int[] userInput) {

        int row = userInput[0];
        int column = userInput[1];

        if (row > 10 || column > 10) {
            System.out.println();
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            System.out.println();
            return false;
        }
        return true;
    }

    private int[] validateShotTaken() {
        boolean isShotTaken = false;
        int[] userCoordinates = new int[2];



        while (!isShotTaken) {
            userCoordinates = stringConverter(scanner.nextLine());
            isShotTaken = areCoordinateValid(userCoordinates);
        }
        return userCoordinates;
    }

    private boolean evaluateShotTaken(PalyerBoard board) {

        String[][] playerBoard = board.getPlayerBoard();
        String[][] fogOfWarBoard = board.getFogOfWarBoard();


        VALIDATED_COORDINATES = validateShotTaken();
        int row = VALIDATED_COORDINATES[0];
        int column = VALIDATED_COORDINATES[1];

        if (playerBoard[row][column].equals(" X")) {
            /*System.out.println();
            System.out.println("You hit a ship!");*/
            SHOT_HIT = false;
            return false;
        } else if (playerBoard[row][column].equals(" M")) {
            /*System.out.println();
            System.out.println("You missed!");*/
            SHOT_HIT = false;
            return false;
        }

        if (playerBoard[row][column].equals(" O")) {
            fogOfWarBoard[row][column] = " X";
            playerBoard[row][column] = " X";
            SHOT_HIT = true;

        } else {
            fogOfWarBoard[row][column] = " M";
            playerBoard[row][column] = " M";
            SHOT_HIT = false;
        }
        board.setFogOfWarBoard(fogOfWarBoard);
        board.setPlayerBoard(playerBoard);

        return SHOT_HIT;
    }

    private boolean evaluateShipSunken(PalyerBoard board){
        String[][] playerBoard = board.getPlayerBoard();
        boolean isShipSunken = false;
        evaluateShotTaken(board);

        if (SHOT_HIT) {
            int row = VALIDATED_COORDINATES[0];
            int column = VALIDATED_COORDINATES[1];

            for (int i = row - 1; i <= row +1; i++){
                if (i == playerBoard.length) {
                    continue;
                }
                for (int y = column -1; y <= column + 1; y++){
                    if (y == playerBoard.length){
                        continue;
                    }
                    if (playerBoard[i][y].equals(" O")) {
                        isShipSunken = false;
                        return false;
                    } else {
                        isShipSunken = true;
                    }
                }
            }
        }
        return isShipSunken;
    }

    protected boolean isTheGameOver(PalyerBoard board){

        boolean isGameOver= false;

        System.out.println();

            if (evaluateShipSunken(board)) {
                numberOfShipsSunken++;
                if (numberOfShipsSunken == Ships.values().length){
                    System.out.println();
                    /*board.printBoard(board.getFogOfWarBoard());
                    System.out.println();*/
                    System.out.println("You sank the last ship. You won. Congratulations!");
                    isGameOver = true;
                } else {
                    System.out.println();
                    /*board.printBoard(board.getFogOfWarBoard());
                    System.out.println();*/
                    System.out.println("You sank a ship!");
                }
            } else if (SHOT_HIT) {
                System.out.println();
                /*board.printBoard(board.getFogOfWarBoard());
                System.out.println();*/
                System.out.println("You hit a ship!");

            } else {
                System.out.println();
                /*board.printBoard(board.getFogOfWarBoard());
                System.out.println();*/
                System.out.println("You missed!");

            }

        return isGameOver;
    }
}
