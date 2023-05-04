import Rest.MessageCallback;
import Rest.Position;
import Tile.Empty;
import Tile.Enemies.Enemy;
import Tile.Enemies.Monster;
import Tile.Enemies.Trap;
import Tile.Players.*;
import Tile.Wall;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;

public class VisitTests {
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
    public  void PlayerVisitEnemy() { // need to print combat data between player and enemy
        Enemy enemy = enemies.get(7).get();
        Position positionEnemy = new Position(10,10);
        MessageCallback messageCallback = s -> System.out.println(s);
        enemy.initialize(positionEnemy, messageCallback);
        Player player = players.get(0).get();
        Position positionPlayer = new Position(10,10);
        player.initialize(positionPlayer, messageCallback);
        player.interact(enemy);
    }

    @Test
    public  void EnemyVisitPlayer() { // need to print combat data between player and enemy
        Enemy enemy = enemies.get(7).get();
        Position positionEnemy = new Position(10,9);
        MessageCallback messageCallback = s -> System.out.println(s);
        enemy.initialize(positionEnemy, messageCallback);
        Player player = players.get(0).get();
        Position positionPlayer = new Position(10,10);
        player.initialize(positionPlayer, messageCallback);
        enemy.interact(player);
    }

    @Test
    public  void EnemyVisitEmpty() { // enemy visit empty, need to change position
        Enemy enemy = enemies.get(7).get();
        Position positionEnemy = new Position(10,9);
        MessageCallback messageCallback = s -> System.out.println(s);
        enemy.initialize(positionEnemy, messageCallback);
        Position positionEmpty = new Position(10,10);
        Empty empty = new Empty(positionEmpty);
        enemy.interact(empty);
        assertEquals(10, enemy.getPosition().getxPosition());
        assertEquals(10, enemy.getPosition().getyPosition());
        assertEquals(10, empty.getPosition().getxPosition());
        assertEquals(9, empty.getPosition().getyPosition());
    }

    @Test
    public  void PlayerVisitEmpty() { // player visit empty, need to change position
        Player player = players.get(3).get();
        Position positionPlayer = new Position(5,5);
        MessageCallback messageCallback = s -> System.out.println(s);
        player.initialize(positionPlayer, messageCallback);
        Position positionEmpty = new Position(4,5);
        Empty empty = new Empty(positionEmpty);
        player.interact(empty);
        assertEquals(4, player.getPosition().getxPosition());
        assertEquals(5, player.getPosition().getyPosition());
        assertEquals(5, empty.getPosition().getxPosition());
        assertEquals(5, empty.getPosition().getyPosition());
    }

    @Test
    public  void EnemyVisitEall() { // enemy visit wall, do nothing
        Enemy enemy = enemies.get(7).get();
        Position positionEnemy = new Position(10,9);
        MessageCallback messageCallback = s -> System.out.println(s);
        enemy.initialize(positionEnemy, messageCallback);
        Position positionWall = new Position(10,10);
        Wall wall = new Wall(positionWall);
        enemy.interact(wall);
        assertEquals(10, enemy.getPosition().getxPosition());
        assertEquals(9, enemy.getPosition().getyPosition());
        assertEquals(10, wall.getPosition().getxPosition());
        assertEquals(10, wall.getPosition().getyPosition());
    }

    @Test
    public  void PlayerVisitWall() { // player visit wall, do nothing
        Player player = players.get(3).get();
        Position positionPlayer = new Position(5,5);
        MessageCallback messageCallback = s -> System.out.println(s);
        player.initialize(positionPlayer, messageCallback);
        Position positionWall = new Position(6,5);
        Wall wall = new Wall(positionWall);
        player.interact(wall);
        assertEquals(5, player.getPosition().getxPosition());
        assertEquals(5, player.getPosition().getyPosition());
        assertEquals(6, wall.getPosition().getxPosition());
        assertEquals(5, wall.getPosition().getyPosition());
    }

}
