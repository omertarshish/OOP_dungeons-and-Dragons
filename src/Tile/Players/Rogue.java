package Tile.Players;

import Rest.GameBoard;
import Rest.Position;
import Tile.Enemies.Enemy;
import java.util.ArrayList;
import java.util.List;


public class Rogue extends Player {

    protected int cost;
    protected int currentEnergy;
    protected String abilityName;

    public Rogue(String name, int healtCapacity,  int attack, int defence , int cost){
        super(name,healtCapacity ,attack,defence);
        this.abilityName = "Fan of Knives";
        this.cost = cost;
        this.currentEnergy = 100;
    }




    @Override
    public Position onTick(Player player) {
        currentEnergy = Math.min(currentEnergy+10, 100);
        Position position = super.onTick(player);
        return position;
    }

    public int min (int a, int b){
        if (a < b)
            return a;
        return b;
    }

    @Override
    public void castAbility (GameBoard gameBoard) {
        if (currentEnergy < cost)
            messageCallback.send("Can not cast the " + abilityName + " ability, requires " + (cost-currentEnergy) + " more energy." );
        else{
            messageCallback.send(name + " cast " + abilityName + ".");
            currentEnergy = currentEnergy - cost;
            List<Enemy> enemyToInteract = new ArrayList<>();
            for (Enemy enemy : gameBoard.getEnemyies()){
                if (this.Range(enemy.getPosition()) < 2)
                    enemyToInteract.add(enemy);
            }
           for (Enemy enemy : enemyToInteract)
               specialAbilityInteract(enemy);
            }
        }


    public void specialAbilityInteract(Enemy enemy){
        messageCallback.send(name + " trying to hit " + enemy.getName() + ".");
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
    @Override
    public void levelingUp(){
        super.levelingUp();
        currentEnergy = 100;
        setAttack(attack + 3*level);
        messageCallback.send(describeLevelingUp());
    }


    @Override
    public String describe(){
        return super.describe() + String.format("\t\tcurrent energy: %s\t\tcost: %d", currentEnergy + "/" + "100", cost);
    }
}

