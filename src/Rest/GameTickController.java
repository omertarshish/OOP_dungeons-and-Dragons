package Rest;

import Tile.Enemies.Enemy;
import Tile.Players.Player;

public class GameTickController {

    private Player player;
    private GameBoard gameBoard;

    public GameTickController(Player player,  GameBoard gameBoard){
        this.player = player;
        this.gameBoard = gameBoard;
    }

    public void gameTick() {
        try {
            Position playerNextPosition = this.player.onTick(this.player);
            if (playerNextPosition != null) // if it is null, the player cast his special ability
                player.interact(gameBoard.getTile(playerNextPosition)); // interacion with the tile int the positon that the player want to go
            else // need to cast his speciel ability
                player.castAbility(gameBoard);
        }
        catch (Exception e){
            //do nothing, wait for valid input
        }


        for (Enemy enemy : gameBoard.getEnemyies()) { // play the enemies moves
            try {
                Position enemynextPosition = enemy.onTick(this.player);
                if (enemynextPosition != null)
                    enemy.interact(gameBoard.getTile(enemynextPosition));
                else{
                    //do bothing the enemy cast his special ability
                }
            }
            catch (Exception e){
                //do nothing, wait for valid input
            }
        }
    }

}
