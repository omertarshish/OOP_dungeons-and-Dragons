package Rest;


import Tile.Players.Player;

public class GameBoardManager {
    private GameBoard gameBoard;
    private Player player;
    private MessageCallback messageCallback;


    public GameBoardManager(GameBoard gameBoard, MessageCallback messageCallback, Player player){
        this.gameBoard = gameBoard;
        this.messageCallback = messageCallback;
        this.player = player;
    }

    public void run(){
        GameTickController gameTickController = new GameTickController(player, gameBoard);
        while((!(gameBoard.getEnemyies().isEmpty())) && player.getIsAlive()){
            printData();
            gameTickController.gameTick();
        }
        if (! player.getIsAlive())
            messageCallback.send("your player has died, the game is over :(");
    }

    public void printData (){
        messageCallback.send(gameBoard.toString());
        messageCallback.send(player.describe());
    }



}
