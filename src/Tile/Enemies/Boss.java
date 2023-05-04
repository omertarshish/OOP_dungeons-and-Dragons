package Tile.Enemies;

import Rest.GameBoard;
import Rest.Position;
import Tile.Players.HeroicUnit;
import Tile.Players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Boss extends Enemy implements HeroicUnit {

    private  int visionRange;
    private int abilityFrequency;
    private int combatTicks;
    private GameBoard gameBoard;
    private String abilityName;

    public Boss(char tile, String name,  int healthCapacity, int attack, int defense, int experienceValue, int visionRange , int abilityFrequency) {
        super(tile, name, healthCapacity, attack, defense, experienceValue);
        this.visionRange = visionRange;
        this.abilityFrequency = abilityFrequency;
        combatTicks = 0;
        abilityName = "shooting";
    }
    @Override
    public void initializeGameBoard (GameBoard gameBoard){
        this.gameBoard = gameBoard;
    }

    @Override
    public Position onTick(Player player){
        if (this.Range(player.getPosition()) <= visionRange){ // if the player in in vision rang
            if (combatTicks == abilityFrequency){
                combatTicks = 0;
                castAbility(gameBoard);
                return null;
            }
            else{
                combatTicks++;
                int dX = this.position.getxPosition() - player.getPosition().getxPosition();
                int dY = this.position.getyPosition() - player.getPosition().getyPosition();
                int absValueDX = Math.abs(dX);
                int absValueDY = Math.abs(dY);
                if (absValueDX > absValueDY){
                    if (dX > 0)
                        return this.position.moveLeft();
                    else
                        return this.position.moveRight();
                }
                else{
                    if (dY > 0)
                        return this.position.moveUp();
                    else
                        return this.position.moveDown();
                }
            }
            }

        else{ //choose the next position randomly
            int randomMove = (int)(Math.random() * 5);
            if (randomMove == 0)
                return this.position; // // for 0, stay in the same position
            else if (randomMove ==1)
                return this.position.moveLeft();
            else if (randomMove ==2)
                return this.position.moveRight();
            else if (randomMove ==3)
                return this.position.moveDown();
            else //randomMove=4
                return this.position.moveUp();
        }
    }


    @Override
    public void castAbility (GameBoard gameBoard) {
            messageCallback.send(name + " cast " + abilityName + ".");
            specialAbilityInteract(gameBoard.getPlayer());
    }


    public void specialAbilityInteract(Player player){
        messageCallback.send(name + " trying to hit " + player.getName() + ".");
        int defense = player.defend();
        messageCallback.send(player.getName() +" rolled " + defense+" defense points.");
        int damage = attack - defense;
        if (damage>0) {
            player.getHealth().decreaseHealth(damage);
            player.onDeath(); // check if the defender is dead.
            messageCallback.send(name + " hit " + player.getName() + " for " + damage + " ability damage." );
        }
        else
            messageCallback.send(name + " hit " + player.getName() + " for 0 ability damage." );
    }



    @Override
    public String describe(){
        return super.describe() + String.format("\t\tVision Range: %s\t\tReamaining to cast ability: %s", visionRange , combatTicks + "/" + abilityFrequency);
    }

}
