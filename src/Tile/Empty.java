package Tile;

import Rest.Position;
import Tile.Enemies.Enemy;

public class Empty extends Tile {


    private static final char dot = '.';

    public Empty(Position position){
        super(dot);
        initialize(position);
    }

    public void accept(Unit unit){
        unit.visit(this);
    }

    @Override
    public Enemy enemyCast() {//just for the abstraction
        return null;
    }


}