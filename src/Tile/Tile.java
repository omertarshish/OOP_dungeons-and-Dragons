package Tile;

import Rest.Position;
import Tile.Enemies.Enemy;

public abstract class Tile implements Comparable<Tile> {
    protected char tile;
    protected Position position;

    protected Tile(char tile){
        this.tile = tile;
    }
    public void setTile(char t) {this.tile = t;}

    protected void initialize(Position position){
        this.position = position;
    }

    public char getTile() {
        return tile;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public abstract void accept(Unit unit);

    public abstract Enemy enemyCast();

    @Override // need to change!!
    public int compareTo(Tile tile){
        int compare = position.getyPosition() - tile.position.getyPosition();
        if (compare<0)
            return -1;
        else if (compare>0)
            return 1;
        compare = position.getxPosition() - tile.position.getxPosition();
        if (compare<0)
            return -1;
        else if (compare>0)
            return 1;
        else
            return 0;
    }

    public double Range(Position position)
    {
        return Math.sqrt(Math.pow((this.position.getxPosition() - position.getxPosition()),2) + Math.pow((this.position.getyPosition() - position.getyPosition()),2));
    }
    @Override
    public String toString() {
        return String.valueOf(tile);
    }
}