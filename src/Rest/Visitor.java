package Rest;

import Tile.Enemies.Enemy;
import Tile.Players.Player;

public interface Visitor {
    void visit(Player p);
    void visit(Enemy p);
}

