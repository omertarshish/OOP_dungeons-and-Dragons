package Tile.Players;

import Rest.GameBoard;
import Rest.Position;
import Tile.Enemies.Enemy;

import java.util.List;

public class Mage extends Player{
    protected int manaPool;
    protected int currentMana;
    protected int manaCost;
    protected int spellPower;
    protected int hitsCount;
    protected int abilityRange;
    protected String abilityName;

    public Mage( String name,int healthCapacity, int attack, int defense,  int manaPool, int manaCost, int spellPower, int hitsCount, int abilityRange){
        super(name, healthCapacity ,attack, defense);
        this.abilityName = "Blizzard";
        this.manaCost = manaCost;
        this.manaPool = manaPool;
        this.abilityRange = abilityRange;
        this.currentMana = manaPool / 4;
        this.hitsCount = hitsCount;
        this.spellPower = spellPower;
    }

    public Position onTick (Player player) {
        currentMana = Math.min(manaPool,currentMana + level);
        Position position = super.onTick(player);
        return position;
    }

    @Override
    public void castAbility (GameBoard gameBoard) {
        if (currentMana < manaCost)
            messageCallback.send("Can not cast the " + abilityName + " ability, requires " + (manaCost-currentMana) + " more mana." );
        else{
            messageCallback.send(name + " cast " + abilityName + ".");
            currentMana = currentMana - manaCost;
            List<Enemy> enemiesInRange =gameBoard.findEnemiesInRange(abilityRange);
            int size = enemiesInRange.size();
            int hits = 0;
            while (hits<hitsCount && size>0){
                Enemy randomEnemyInRange = enemiesInRange.get( (int)(Math.random() * size));
                specialAbilityInteract(randomEnemyInRange);
                if (!randomEnemyInRange.getIsAlive())
                    enemiesInRange.remove(randomEnemyInRange); // deleting the enemy from the local list if he died.
                size = enemiesInRange.size();
                hits++;
            }
        }
    }

    public void specialAbilityInteract(Enemy enemy){
        messageCallback.send(name + " trying to hit " + enemy.getName() + ".");
        int defense = enemy.defend();
        messageCallback.send(enemy.getName() +" rolled " + defense+" defense points.");
        int damage = spellPower - defense;
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
        manaPool = manaPool + 25 * level;
        currentMana = Math.min(currentMana+(manaPool/4), manaPool);
        spellPower = spellPower + (10 * level);
        messageCallback.send(describeLevelingUp());
    }

    public String describeLevelingUp(){
        return super.describeLevelingUp() +  "+" + 25*level + " maximum mana, " + "+" + 10*level + " spell power.";
    }
    @Override
    public String describe() {
        return super.describe() + String.format("\t\tMana: %s\t\tSpell Power: %s",   currentMana + "/" + manaPool, spellPower);
    }
}

