package Tile.Players;

import Rest.GameBoard;
import Rest.Position;
import Tile.Enemies.Enemy;

import java.util.List;

public class Hunter extends Player{
    protected int range;
    protected int arrowsCount;
    protected int tickCount;
    protected String abilityName;


    public Hunter(String name, int healthCapacity, int attack, int defense,  int range){
        super(name, healthCapacity ,attack, defense);
        this.range = range;
        this.arrowsCount = 10 * level;
        tickCount = 0;
        abilityName = "shoot";
    }

    public Position onTick (Player player) {
        if (tickCount == 10){
            arrowsCount = arrowsCount + level;
            tickCount =0;
        }
        else
            tickCount++;
        Position position = super.onTick(player);
        return position;
    }

    @Override
    public void castAbility (GameBoard gameBoard) {
        List<Enemy> enemiesInRange = gameBoard.findEnemiesInRangeLessOrEqual(range);
        if (arrowsCount==0)
            messageCallback.send(name + " tried to cast " + abilityName + " but there wasn't any arrow." );
        else if (enemiesInRange.size() == 0)
            messageCallback.send(name + " tried to cast " + abilityName + " but there wasn't any enemy that in range less or equal to " + range + "." );
        else{
            int minRange=-1;
            Enemy enemyToAttack=null;
            for (Enemy enemy : enemiesInRange)
                if(minRange==-1 || Range(enemy.getPosition()) < minRange)
                    enemyToAttack = enemy;
            specialAbilityInteract(enemyToAttack);
            arrowsCount--;
        }
    }


    public void specialAbilityInteract(Enemy enemy){
        messageCallback.send(name + " fired an arrow at " + enemy.getName() + ".");
        int defense = enemy.defend();
        messageCallback.send(enemy.getName() +" rolled " + defense+" defense points.");
        int damage = attack - defense;
        if (damage>0) {
            enemy.getHealth().decreaseHealth(damage);
            enemy.onDeath(); // check if the defender is dead.
            if (!enemy.getIsAlive())
                processStep(enemy, true);
            messageCallback.send(name + " hit " + enemy.getName() + " for " + damage + " ability damage." );
        }
        else
            messageCallback.send(name + " hit " + enemy.getName() + " for 0 ability damage." );
    }

    public void levelingUp(){
        super.levelingUp();
        arrowsCount = arrowsCount + (10*level);
        attack = attack + (2*level);
        defense = defense + level;
        messageCallback.send(describeLevelingUp());
    }

    public String describeLevelingUp(){
        return name + " reached level " + level + ": " + "+" + 10*level + " Health, " + "+" + 6*level + " Attack, " + "+" + 2*level + " Defence.";
    }

    @Override
    public String describe() {
        return super.describe() + String.format("\t\tArrows: %s\t\tRange: %s", arrowsCount, range);
    }
}


