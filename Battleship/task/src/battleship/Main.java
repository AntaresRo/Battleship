package battleship;

public class Main {


    public static void main(String[] args) {

        /*PalyerBoard player1 = new PalyerBoard();
        GameLogic gameLogic = new GameLogic();
        player1.isPlayerBoardSetupComplete();
        gameLogic.gameStartingAnnouncement(player1);
        System.out.println();
        System.out.println("Take a shot!");
        gameLogic.isTheGameOver(player1);*/
        PvPLogic pvPLogic = new PvPLogic();
        pvPLogic.playersSetup();
        pvPLogic.playGame();



    }


}
