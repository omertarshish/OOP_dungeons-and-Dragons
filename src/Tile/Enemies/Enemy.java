package Tile.Enemies;

import Rest.EnemyDeathCallback;
import Rest.GameBoard;
import Rest.MessageCallback;
import Rest.Position;
import Tile.Players.Player;
import Tile.Unit;

public abstract class Enemy extends Unit {
    private final int experienceValue; // check abou it
    private EnemyDeathCallback enemyDeathCallback;

    public Enemy(char tile, String name, int healCapacity, int attack, int defense, int experienceValue)
    {
        super(tile, name, healCapacity, attack, defense);
        this.experienceValue = experienceValue;
    }

    public void initialize(Position position, MessageCallback messageCallback, EnemyDeathCallback enemyDeathCallback){
        super.initialize(position,messageCallback);
        this.enemyDeathCallback = enemyDeathCallback;
    }

    public abstract void initializeGameBoard (GameBoard gameBoard);

    public int getExperienceValue() {
        return this.experienceValue;
    }

    @Override
    public Enemy enemyCast() {
        return this;
    }

    @Override
    public boolean isEnemy() {
        return true;
    }

    @Override
    public boolean getIsAlive(){
        return this.isAlive;
    }
    @Override
    public void processStep(Enemy e, boolean bool){
        //just for the abstraction
    }
    @Override
    public void onDeath(){
        if (this.health.getHealthAmount() == 0){
            isAlive = false;
            enemyDeathCallback.call(this); /// need to check
        }
    }


    public void visit(Player p){ //combat against the player
        battle(p);
    }
    public void visit(Enemy e){
        // just for the abstraction
    }

    public void accept(Unit unit){
        unit.visit(this);
    }
}
