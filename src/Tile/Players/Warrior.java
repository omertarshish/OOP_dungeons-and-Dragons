package Tile.Players;

import Rest.GameBoard;
import Rest.Position;
import Tile.Enemies.Enemy;

import java.util.List;

public class Warrior extends Player {


    private int coolDown;
    private int remainingCoolDown;
    protected String abilityName;


    public Warrior (String name, int healtCapacity, int attack, int defence, int coolDown ) {
        super(name, healtCapacity, attack, defence);
        abilityName = "Avenger's shield";
        this.coolDown = coolDown;
        this.remainingCoolDown = 0;
    }

    @Override
    public Position onTick (Player player) {
        if (remainingCoolDown > 0)
            remainingCoolDown--;
        Position position = super.onTick(player);
        return position;
    }

    @Override
    public void castAbility (GameBoard gameBoard) {
        if (remainingCoolDown>0)
            messageCallback.send("Can not cast the " + abilityName + " ability, requires " + remainingCoolDown + " game tick to be available" );
        else{
            remainingCoolDown = coolDown;
            int newHealth = Math.min(health.getHealthAmount() + (10 * defense), health.getHealthPool());
            health.setHealthAmount(newHealth);
            messageCallback.send(name + " used " + abilityName + ", healing for " + newHealth + ".");
            List<Enemy> enemiesInRange =gameBoard.findEnemiesInRange(3);
            int size = enemiesInRange.size();
            if (size > 0){
                Enemy randomEnemyInRange = enemiesInRange.get( (int)(Math.random() * size));
                specialAbilityInteract(randomEnemyInRange); // Randomly hits one enemy within range < 3 for an amount equals to 10% of the warrior’s.
            }
        }
    }

    public void specialAbilityInteract(Enemy enemy){
        messageCallback.send(name + " trying to hit " + enemy.getName() + ".");
        int defense = enemy.defend();
        messageCallback.send(enemy.getName() +" rolled " + defense+" defense points.");
        int damage = (int) (health.getHealthPool() * 0.1  -  defense);
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
        remainingCoolDown = 0;
        health.increaseHealthPool(5 * level);
        setAttack(attack + 2 * level);//new attack point is attack + 5*level
        setDefense(defense + level);//new defense points is defence + level
        messageCallback.send(describeLevelingUp());
    }


    @Override
    public String describe(){
        return super.describe() + String.format("\t\tcooldown: %s", remainingCoolDown + "/" + coolDown);
    }

}
