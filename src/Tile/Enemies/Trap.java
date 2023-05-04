package Tile.Enemies;


import Rest.GameBoard;
import Rest.Position;
import Tile.Players.Player;

public class Trap extends Enemy {

    private int visibilityTime;
    private int inVisibilityTime;
    private int ticksCount;
    private boolean visible;
    private char originalTile;

    public Trap (char tile,  String name, int healCapacity, int AttackPoints, int DefensePoints, int experienceValue, int visibilityTime, int inVisibilityTime) {
        super(tile,  name, healCapacity, AttackPoints, DefensePoints, experienceValue);
        this.visibilityTime = visibilityTime;
        this.inVisibilityTime = inVisibilityTime;
        this.ticksCount = 0;
        visible = true;
        originalTile = tile;
    }

    @Override
    public Position onTick(Player player){
        visible = visibilityTime<ticksCount;
        if (visible)
            setTile('.');
        else
            setTile(originalTile);
        if (ticksCount == (visibilityTime+inVisibilityTime))
            ticksCount=0;
        else
            ticksCount++;
        if(this.Range(player.getPosition()) < 2) //if player close enough to attack-> attack
            battle(player);
        return  this.position;
    }

    @Override
    public String describe(){
        return super.describe() + String.format("\t\tVisibility time: %s\t\tInvisibility Time: %s", visibilityTime , inVisibilityTime);
    }

    @Override
    public void initializeGameBoard (GameBoard gameBoard){
        //just for the abstraction
    }



}

