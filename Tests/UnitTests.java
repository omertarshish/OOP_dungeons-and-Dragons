import Rest.MessageCallback;
import Tile.Enemies.Enemy;
import Tile.Enemies.Monster;
import Tile.Enemies.Trap;
import Tile.Players.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;

public class UnitTests {

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
        public  void isAlive() {
        assertEquals(true, enemies.get(1).get().getIsAlive());
        assertEquals(true, players.get(1).get().getIsAlive());
        assertEquals(true, enemies.get(4).get().getIsAlive());
        assertEquals(true, enemies.get(7).get().getIsAlive());
        assertEquals(true, enemies.get(7).get().isEnemy());
        }


        @Test public
        void isEnemy() {

        assertEquals(true, enemies.get(2).get().isEnemy());
        assertEquals(true, enemies.get(3).get().isEnemy());
        assertEquals(false, players.get(4).get().isEnemy());
        assertEquals(false, players.get(3).get().isEnemy());
    }

    @Test
    public  void enemyCast() {
       assertEquals(400, enemies.get(2).get().getHealth().getHealthAmount());
       assertEquals(250, players.get(5).get().getHealth().getHealthAmount());
    }

    @Test
    public  void describeEnemy() { //print a description of each enemy
        MessageCallback messageCallback = s -> System.out.println(s);
        messageCallback.send("Enemies deatails: ");
        for (int i=0; i<enemies.size(); i++)
            messageCallback.send(enemies.get(i).get().describe());
    }

    @Test
    public  void describePlayer() { //print a description of each player
        MessageCallback messageCallback = s -> System.out.println(s);
        messageCallback.send("Player deatails: ");
        for (int i=0; i<players.size(); i++)
            messageCallback.send(players.get(i).get().describe());
    }












}
