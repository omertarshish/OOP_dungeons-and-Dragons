import Rest.Position;
import Tile.Enemies.Enemy;
import Tile.Enemies.Monster;
import Tile.Enemies.Trap;
import Tile.Players.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;

public class OnTickTests {

    List<Supplier<Enemy>> enemies;
    List<Supplier<Player>> players;
    @Before
    public void setUp() throws Exception {
        enemies = Arrays.asList(
                () -> new Monster('s', "Lannister Solider", 80, 8, 3, 25, 3),
                () -> new Monster('k', "Lannister Knight", 200, 14, 8, 50, 4),
                () -> new Monster('q', "Queen's Guard", 400, 20, 15, 100, 5),
                () -> new Monster('z', "Wright", 600, 30, 15, 100, 3),
                () -> new Monster('b', "Bear-Wright", 1000, 75, 30, 250, 4),
                () -> new Monster('g', "Giant-Wright", 1500, 100, 40, 500, 5),
                () -> new Monster('w', "White Walker", 2000, 150, 50, 1000, 6),
                () -> new Monster('M', "The Mountain", 1000, 60, 25, 500, 6),
                () -> new Monster('C', "Queen Cersei", 100, 10, 10, 1000, 1),
                () -> new Monster('K', "Night's King", 5000, 300, 150, 5000, 8),
                () -> new Trap('B', "Bonus Trap", 1, 1, 1, 250, 1, 5),
                () -> new Trap('Q', "Queen's Trap", 250, 50, 10, 100, 3, 7),
                () -> new Trap('D', "Death Trap", 500, 100, 20, 250, 1, 10)
        );


        players  = Arrays.asList(
                () -> new Warrior("Jon Snow", 300, 30, 4, 3),
                () -> new Warrior("The Hound", 400, 20, 6, 5),
                () -> new Mage("Melisandre", 100, 5, 1, 300, 30, 15, 5, 6),
                () -> new Mage("Thoros of Myr", 250, 25, 4, 150, 20, 20, 3, 4),
                () -> new Rogue("Arya Stark", 150, 40, 2, 20),
                () -> new Rogue("Bronn", 250, 35, 3, 50),
                () -> new Hunter("Ygritte", 220, 30, 2, 6)
        );
    }

    @Test
    public  void move() {// check move method
        Position position = new Position(5,5);

        assertEquals(4, position.moveUp().getyPosition());
        assertEquals(6, position.moveDown().getyPosition());
        assertEquals(6, position.moveRight().getxPosition());
        assertEquals(4, position.moveLeft().getxPosition());
    }

    @Test
    public  void onTickMonsterLeft() {// check when need to move left
        Enemy enemy = enemies.get(7).get();
        Position positionEnemy = new Position(10,10);
        enemy.setPosition(positionEnemy);
        Player player = players.get(0).get();
        Position positionPlayer = new Position(5,7);
        player.setPosition(positionPlayer);
        assertEquals(9, enemy.onTick(player).getxPosition());
    }

    @Test
    public  void onTickMonsterRight() {// check when need to move right
        Enemy enemy = enemies.get(7).get();
        Position positionEnemy = new Position(10,10);
        enemy.setPosition(positionEnemy);
        Player player = players.get(0).get();
        Position positionPlayer = new Position(15,7);
        player.setPosition(positionPlayer);
        assertEquals(11, enemy.onTick(player).getxPosition());
    }


    @Test
    public  void onTickMonsterUp() {// check when need to move Up
        Enemy enemy = enemies.get(7).get();
        Position positionEnemy = new Position(10,10);
        enemy.setPosition(positionEnemy);
        Player player = players.get(0).get();
        Position positionPlayer = new Position(10,5);
        player.setPosition(positionPlayer);
        assertEquals(9, enemy.onTick(player).getyPosition());
    }

    @Test
    public  void onTickMonsterDown() {// check when need to move down
        Enemy enemy = enemies.get(7).get();
        Position positionEnemy = new Position(10,10);
        enemy.setPosition(positionEnemy);
        Player player = players.get(0).get();
        Position positionPlayer = new Position(10,15);
        player.setPosition(positionPlayer);
        assertEquals(11, enemy.onTick(player).getyPosition());
    }

    @Test
    public  void onTickTrap() {// check when need to move down
        Enemy enemy = enemies.get(10).get();
        Position positionEnemy = new Position(10,10);
        enemy.setPosition(positionEnemy);
        Player player = players.get(0).get();
        Position positionPlayer = new Position(1000,200);
        player.setPosition(positionPlayer);
        assertEquals(10, enemy.onTick(player).getyPosition());
        assertEquals(10, enemy.onTick(player).getxPosition());
    }
}
