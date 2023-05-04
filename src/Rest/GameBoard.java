package Rest;

import Tile.Enemies.Enemy;
import Tile.Players.Player;
import Tile.Tile;
import Tile.Empty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GameBoard {
    private List<Tile> tiles;
    private List<Enemy> enemyies;
    private Player player;

    public GameBoard(){
    }

    public void initializeBoard(Tile[][] board){
        tiles = new ArrayList<>();
        for(Tile[] line : board){
            tiles.addAll(Arrays.asList(line));
        }
        makeListOfEnmey(); // make a list of enemies
    }
    public void remove(Enemy e) {
        Position position = e.getPosition();
        tiles.remove(e);
        enemyies.remove(e);
        tiles.add(new Empty(position));
    }

    public void initializePlayer(Player player){// set player
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Enemy> getEnemyies(){
        return this.enemyies;
    }

    public void makeListOfEnmey() {
        this.enemyies = new ArrayList<>();
        for (Tile t : tiles){
            char type = t.getTile();
            if (type!= '.' && type!='@' && type!= '#')
                enemyies.add(t.enemyCast());
        }
    }

    public List<Enemy> findEnemiesInRange(int range){ //return enemy in range that smaller then rang
        List<Enemy> enemiesInRange = new ArrayList<>();
        for(Enemy enemy : enemyies){
            if (enemy.Range(this.player.getPosition()) < range)
                enemiesInRange.add(enemy);
        }
        return enemiesInRange;
    }



    public List<Enemy> findEnemiesInRangeLessOrEqual(int range){//return enemy in range that smaller or equal then range
        List<Enemy> enemiesInRange = new ArrayList<>();
        for(Enemy enemy : enemyies){
            if (enemy.Range(this.player.getPosition()) <= range)
                enemiesInRange.add(enemy);
        }
        return enemiesInRange;
    }

    public Tile getTile(Position pos) {
        for (Tile t: tiles)
            if (t.getPosition().equals(pos))
                return t;
        return null;
    }



    @Override
    public String toString() {
        tiles = tiles.stream().sorted(Tile::compareTo).collect(Collectors.toList());
        String str = "";
        for(Tile t : tiles){
            if(t.getPosition().getxPosition() == 0){
                str = str + ("\n");
            }
            str = str + t;
        }
        return str;
    }
}