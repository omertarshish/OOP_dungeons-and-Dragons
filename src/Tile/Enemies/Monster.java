package Tile.Enemies;

import Rest.GameBoard;
import Rest.Position;
import Tile.Players.Player;

public class Monster extends Enemy{

    private int visionRange;


    public Monster(char tile,  String name, int  healthCapacity, int attack, int defense, int experienceValue, int visionRange ) {
        super(tile, name, healthCapacity, attack, defense, experienceValue);
        this.visionRange = visionRange;
    }


    @Override
    public Position onTick(Player player){
        if (this.Range(player.getPosition()) <= visionRange){ // if the player in in vision rang
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
    public void initializeGameBoard (GameBoard gameBoard){
        //just for the abstraction
    }


}

