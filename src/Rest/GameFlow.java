package Rest;

import Tile.Enemies.Enemy;
import Tile.Players.Player;
import Tile.Tile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class GameFlow {
    private GameBoard gameBoard;
    String path;
    List<File> listOfLevels;
    private final TileFactory TF;
    private MessageCallback messageCallback;
    Player player;
    private EnemyDeathCallback enemyDeathCallback;


    public GameFlow(String path, MessageCallback messageCallback){
        this.path = path;
        this.listOfLevels = loadLevels(path);
        TF = new TileFactory();
        this.messageCallback = messageCallback;
        this.player = null;
    }

    public void run(){
        for (File level : listOfLevels) {
            GameBoard gameBoard = new GameBoard(); // make
            this.enemyDeathCallback = e -> gameBoard.remove(e);
            Tile[][] tiles = makeBoardArray(level); //making an array of tile of the game
            gameBoard.initializeBoard(tiles);
            gameBoard.initializePlayer(player);// initialize list of tile and enemies
            GameBoardManager gameBoardManager = new GameBoardManager(gameBoard, messageCallback, player);
            initializeGameBoardOnEnemtBoss(gameBoard);
            gameBoardManager.run();
        }
        if (player.getIsAlive())
            messageCallback.send("you won the game, well done!!");
    }


    public void initializeGameBoardOnEnemtBoss (GameBoard gameBoard){
        for (Enemy enemy : gameBoard.getEnemyies()){
            char type = enemy.getTile();
            if ( type == 'M' || type == 'C' || type == 'K') //need to initialize gameBoard to the boss enemy
                enemy.initializeGameBoard(gameBoard);
        }
    }



    private Tile[][] makeBoardArray(File level) {
        List<String> linesOfString = new ArrayList<>();
        try { // the level by lines string
            linesOfString = Files.readAllLines(Paths.get(path + "\\" + level.getName()));
        } catch (IOException e) {// exeption
            messageCallback.send("Technical problem while reading the files");
        }
        Tile[][] tiles = new Tile[linesOfString.size()][linesOfString.get(0).length()];
        int count = 0;
        for (String line : linesOfString) {
            for (int i = 0; i < line.length(); i++) {
                char type = line.charAt(i);
                Position position = Position.at(i, count);
                if (type == '#') //produce wall
                    tiles[count][i] = TF.produceWall(position);
                else if (type == '.') //produce tile
                    tiles[count][i] = TF.produceEmpty(position);
                else if (type == '@') { // -  init the playerPosition
                    if (player == null) {
                        player = TF.producePlayer((choosePlayer() - 1), position, messageCallback); // just on the first level
                        messageCallback.send("You have selected: \n" + this.player.getName());
                    } else
                        player.initialize(position, messageCallback);
                    tiles[count][i] = player;
                }
                else {   //enemy
                    tiles[count][i] = TF.produceEnemy(type, position, messageCallback, enemyDeathCallback);
                }
            }
            count++;
        }
        return tiles;
    }

    public int choosePlayer() {
        List<Player> players = TF.listPlayers();
        int count = 1;
        messageCallback.send("Select player:");
        for (Player p: players) {
           messageCallback.send(count + ". " + p.describe() + "\n");
            count++;
        }
        String playerChoiseString="";
        int playerChoiseInt=-1;
        Scanner scanner = new Scanner(System.in);;
        boolean isValid = false;
        while (!isValid){
            try{
                playerChoiseString = scanner.next();
                playerChoiseInt = Integer.parseInt(playerChoiseString); //it will throw exeption if it will be string.
                if (playerChoiseInt>7 || playerChoiseInt < 1)
                    throw new NoSuchElementException();// not a valid input
                isValid = true;
            }
            catch (Exception e){
                count = 1;
                System.out.println(e);
                System.out.println(e.getCause());
                messageCallback.send("please choose a valid Input in order to start the game");
                for (Player player : players) {
                    messageCallback.send(count + ". " + player.describe() + "\n");
                    count++;
                }
            }

        }
        return playerChoiseInt;
    }

    private List<File> loadLevels(String path){
        File folerOffiles = new File(path);
        File[] listOfFiles = folerOffiles.listFiles();
        return List.of(listOfFiles);
    }

}
