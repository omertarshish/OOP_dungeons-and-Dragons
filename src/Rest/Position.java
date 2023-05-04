package Rest;
public class Position
{
    private int xPosition;
    private int yPosition;

    public Position( int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
    }

    public static Position at(int xPosition, int yPosition){
        return new Position(xPosition,yPosition);
    }
    public int getxPosition() {
        return this.xPosition;
    }

    public int getyPosition() {
        return this.yPosition;
    }



    @Override
    public boolean equals(Object otherPosition) {
        if (! (otherPosition instanceof Position))
            return false;
        return (this.xPosition ==  ((Position) otherPosition).getxPosition() && this.yPosition == ((Position) otherPosition).getyPosition());
    }

    public Position moveUp(){return new Position(this.xPosition, this.yPosition - 1);}
    public Position moveDown(){return new Position(this.xPosition, this.yPosition  + 1);}
    public Position moveLeft(){return new Position(this.xPosition - 1, this.yPosition);}
    public Position moveRight(){return new Position(this.xPosition + 1, this.yPosition);}


    public void setPosition(final int x, final int y) {
        this.xPosition = x;
        this.yPosition = y;
    }
}