package Tile;

import Rest.*;
import Tile.Enemies.Enemy;
import Tile.Players.Player;

public abstract class Unit extends Tile implements Visitor {
	protected MessageCallback messageCallback;
    protected String name;
    protected int attack;
    protected int defense;
    protected Health health;
    protected boolean isAlive;

    protected Unit(char tile, String name, int healthCapacity, int attack, int defense) {
        super(tile);
        this.health = new Health(healthCapacity);
        this.name = name;
        this.attack = attack;
        this.defense = defense;
        isAlive = true;
    }

    public Health getHealth(){
        return  health;
    }
    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public abstract boolean isEnemy();

    public void initialize(Position position, MessageCallback messageCallback){
       super.initialize(position);
       this.messageCallback = messageCallback;
    }
    public int getDefense() {
        return this.defense;
    }

    protected int attack(){
        return (int)(Math.random() * attack);
    }

    public int defend(){
       return  (int)(Math.random() * defense);
    }


    // Should be automatically called once the unit finishes its turn
    public abstract void processStep(Enemy e, boolean bool);

    // What happens when the unit dies
    public abstract void onDeath();
    //is the unit alive
    public abstract boolean getIsAlive();

    // This unit attempts to interact with another tile.
    public void interact(Tile tile){
		tile.accept(this);
    }
    public void visit(Empty empty){
        Position temp = empty.getPosition();
        empty.setPosition(this.getPosition());
        this.setPosition(temp);
    }



    public abstract void visit(Player p);
    public abstract void visit(Enemy e);
    public abstract Position onTick(Player position); // find the next desired position/game move for the player/enemy

    // Combat against another unit.
    protected void battle(Unit u){
        messageCallback.send(describe());
        messageCallback.send(u.describe());
        messageCallback.send(name + " engaged in combat with " + u.getName() + ".");
        int attackDamage = attack();
        messageCallback.send(this.getName() +" rolled " + attackDamage+" attack points."); ; //Might move to callback in unit
        int defense = u.defend();
        messageCallback.send(u.getName() +" rolled " + defense+" defense points.");
        int damageDealt = attackDamage - defense;
        if (damageDealt>0) {
            messageCallback.send(name + " dealt " + damageDealt + " damage to " + u.getName() + ".");
            u.health.decreaseHealth(damageDealt);
            u.onDeath(); // check if the defender is dead.
            if (!u.isAlive && u.isEnemy())
                messageCallback.send(u.getName() + " died. " + name + " gained " + u.enemyCast().getExperienceValue() + " experience");
        }
        else
            messageCallback.send(name + " dealt 0 damage to "  + u.getName() + ".");
    }


    public String getName() {
        return this.name;
    }

    public String describe() {
        return String.format("%s\t\tHealth: %s\t\tAttack: %d\t\tDefense: %d", getName(), health, attack, getDefense());
    }
}