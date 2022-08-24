package battleship;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PalyerBoard {
    private final int BOARD_ROW = 11;
    private final int BOARD_COLUMN = 11;
    private final int USER_Input_Length = 3;
    private String[][] playerBoard = new String[BOARD_ROW][BOARD_COLUMN];
    private String[][] fogOfWarBoard = new String[BOARD_ROW][BOARD_COLUMN];

    private Scanner scanner ;
    private String[]userInput = new String[USER_Input_Length];

    public String[][] getPlayerBoard() {
        return playerBoard;
    }

    public void setPlayerBoard(String[][] playerBoard) {
        this.playerBoard = playerBoard;
    }

    public String[][] getFogOfWarBoard() {
        return fogOfWarBoard;
    }

    public void setFogOfWarBoard(String[][] fogOfWarBoard) {
        this.fogOfWarBoard = fogOfWarBoard;
    }

    protected String[][] generateInitialBoard(String [][] board){
        String[] alphabet = new String[] {"X","A","B","C","D","E","F","G","H","I","J"};
        for (int row = 0; row < 11; row++){
            for (int column = 0; column < 11; column++) {
                if ((row == 0) && (column == 0)) {
                    board[row][column] = "  ";
                } else if (row == 0) {
                    board[row][column] = column + " ";
                } else if (column == 0) {
                    board[row][column] = alphabet[row];
                } else {
                    board[row][column] = " ~";
                }
            }
        }
        return board;
    }


    protected void printBoard(String [][] board) {
        for (String[] row: board){
            for (String position: row) {
                System.out.print(position);
            }
            System.out.println();
        }
    }

    private String[] stringConverter(String input) {
        input = input.trim().toUpperCase();
        String[]rawCoordinates = input.split(" ");
        String[]coordinates = new String[4];

        Pattern lettersPattern = Pattern.compile("[A-J]");
        Pattern numbersPattern = Pattern.compile("\\d\\d?");

        for (int i=0; i<rawCoordinates.length;i++){
            Matcher lettersMatcher = lettersPattern.matcher(rawCoordinates[i]);

            while(lettersMatcher.find()){
                coordinates[2*i]=lettersMatcher.group();
            }
        }

        for (int i=0;i<rawCoordinates.length;i++){
            Matcher numbersMatcher = numbersPattern.matcher(rawCoordinates[i]);

            while(numbersMatcher.find()){
                coordinates[2*i+1]=numbersMatcher.group();
            }
        }

        return coordinates;
    }

    protected void printCoordinates(String[] somthInput) {
        for (String s : somthInput) {
            System.out.println(s);
        }
    }

    private boolean updateBoard(String[] input, Ships ship) {
        char firstLetter = input[0].charAt(0);
        char secondLetter = input[2].charAt(0);
        int firstRow = (int) firstLetter - 64;
        int secondRow = (int) secondLetter - 64;

        int firstColumn = Integer.parseInt(input[1]);
        int secondColumn = Integer.parseInt(input[3]);

        int firstRowCoordinate = Math.min(firstRow, secondRow);
        int secondRowCoordinate = Math.max(firstRow, secondRow);
        int firstColumnCoordinate = Math.min(firstColumn, secondColumn);
        int secondColumnCoordinate = Math.max(firstColumn, secondColumn);

        boolean isAvailable = false;

        while(!isAvailable){
            isAvailable = isLocationAvailable(playerBoard, firstRowCoordinate,secondRowCoordinate, firstColumnCoordinate, secondColumnCoordinate);

            if(!isAvailable){
                return handleShipsTooClose(ship);
            }
            else{
                return insertShip(ship, firstRowCoordinate, secondRowCoordinate, firstColumnCoordinate, secondColumnCoordinate);
            }
        }

        return true;
    }

    private boolean insertShip(Ships ship, int startRow, int finishRow, int startCol,int finishCol){

        if (!isValidLength(ship, startRow, finishRow, startCol, finishCol)) {
            return handleWrongShipLength(ship);
        } else if (!isValidLocation(startRow, finishRow, startCol, finishCol)){
            return handleWrongShipLocation(ship);
        } else {
            for (int row = startRow; row <= finishRow; row++){
                for (int col = startCol; col <= finishCol; col ++){
                    playerBoard[row][col] = " O";
                }
            }
        }
        return true;
    }

    private boolean isValidLength(Ships ship,int startRow, int finishRow, int startCol, int finishCol){
        return ((finishRow - startRow == ship.size -1) ||
                (finishCol - startCol == ship.size -1));
    }

    private boolean isValidLocation(int startRow, int finishRow, int startCol, int finishCol) {
       return startRow == finishRow || startCol == finishCol;
    }
    private boolean handleWrongShipLength(Ships ship) {

        System.out.println("Error! Wrong length of the " + ship.name + "! Try again:");
        System.out.println();

        return false;
    }

    private boolean handleWrongShipLocation(Ships ship) {

        System.out.println("Error! Wrong ship location! Try again:");
        System.out.println();

        return false;
    }

    private boolean handleShipsTooClose(Ships ship) {

        System.out.println("Error! You placed it too close to another one. Try again:");
        System.out.println();

        return false;
    }

    private static boolean isLocationAvailable(String[][] board, int firstRow, int secondRow, int firstCol, int secondCol){
        for (int row=firstRow-1; row<=secondRow + 1;row++) {
            if(row == board.length){
                continue;
            }

            for (int col = firstCol - 1; col <= secondCol + 1; col++) {
                if(col == board.length){
                    continue;
                }

                if (board[row][col].equals(" O")) {
                    return false;
                }
            }
        }

        return true;
    }

    protected boolean isPlayerBoardSetupComplete() {
        generateInitialBoard(playerBoard);
        for (Ships ship : Ships.values()) {
            System.out.println();
            System.out.println("Enter the coordinates of the " + ship.name + " (" + ship.size + " cells):");
            System.out.println();
            boolean isShipSuccessfullyDeployed = false;

            while(!isShipSuccessfullyDeployed) {
                isShipSuccessfullyDeployed = getInputAndUpdate(ship);
            }

            printBoard(playerBoard);

        }
        return true;
    }

    private boolean getInputAndUpdate(Ships ship) {
        scanner = new Scanner(System.in);
        try {
            userInput = stringConverter(scanner.nextLine());
            System.out.println();
        } catch (Exception e) {
            System.out.println("Error in the input string please try again");
            return false;
        }

        return updateBoard(userInput, ship);
    }
}

