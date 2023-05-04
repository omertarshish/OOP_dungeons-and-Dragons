package Tile.Players;

import Rest.GameBoard;
import Rest.MessageCallback;
import Rest.Position;
import Tile.Enemies.Enemy;
import Tile.Unit;

import java.util.Scanner;

public abstract class Player extends Unit implements HeroicUnit {
    protected int experience;
    protected int level;

    public Player(String name, int healCapacity, int attack, int defense)
    {
        super('@' , name, healCapacity, attack, defense);
        this.experience = 0;
        this.level = 1;
        this.isAlive = true;
    }

    public abstract void castAbility(GameBoard gameBoard);

    @Override
    public Position onTick(Player player) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (input.length() ==1){
           char moveType = input.charAt(0);
            if (moveType == 'w')
                return  player.getPosition().moveUp();
            else if (moveType == 's')
                return  player.getPosition().moveDown();
            else if (moveType == 'a')
                return  player.getPosition().moveLeft();
            else if ( moveType == 'd')
                return  player.getPosition().moveRight();
            else if (moveType == 'e')
                return null;
        }
        throw new IllegalArgumentException();
    }

    public boolean getIsAlive(){
        return isAlive;
    }

    @Override
    public void onDeath(){
        if (this.health.getHealthAmount() == 0){
            isAlive = false;
            this.setTile('X');
        }

    }
    public void levelingUp(){
        this.experience -= 50 * level;
        level++;
        health.HealthLevelUp(level);
        attack += 4 * level;
        defense += level;
    }


    public void processStep(Enemy e, boolean isSpecialInteract){

        if (! e.getIsAlive()) //check if need to gain points, if the enemy died
            this.experience = this.experience + e.getExperienceValue();

        if (experience >= 50 * level)
            levelingUp();

        if (!isSpecialInteract){
            Position temp = this.getPosition();
            this.setPosition(e.getPosition()); // get the position of the enemy
            e.setPosition(temp);
        }
    }

    @Override
    public void accept(Unit unit){
        unit.visit(this);
    }

    @Override
    public Enemy enemyCast() {//just for the abstraction
        return null;
    }

    @Override
    public boolean isEnemy() {
        return false;
    }

    public void visit(Enemy e){
        battle(e);
        processStep(e, true);
    }

    public void visit(Player p){
        // just for the abstraction
    }

    public void initialize(Position position, MessageCallback messageCallback){
        super.initialize(position,messageCallback);
    }

    public String describeLevelingUp(){
        return name + " reached level " + level + ": " + "+" + 10*level + " Health, " + "+" + 4*level + " Attack, " + "+" + level + " Defence.";
    }
@Override
    public String describe() {
        return super.describe() + String.format("\t\tExperience: %s\t\tLevel: %s", experience + "/" + 50*level, level);
    }
}
