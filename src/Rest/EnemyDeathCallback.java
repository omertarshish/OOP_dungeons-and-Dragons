package Rest;

import Tile.Enemies.Enemy;

public interface EnemyDeathCallback{
    void call(Enemy e);
}