package Tile;

import Rest.Position;
import Tile.Enemies.Enemy;

public class Wall extends Tile {
    private static final char wall = '#';

    public Wall(Position position){
        super(wall);
        initialize(position);
    }

    public void accept(Unit unit){
        //can't accept so do nothing
    }

    @Override
    public Enemy enemyCast() {// just for the abstraction
        return null;
    }

}


