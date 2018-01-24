package mx.com.atmen.drunkapp;

public class Player {

    protected int x;
    protected int y;

    Player(int x, int y){
        this.x = x;
        this.y = y;
    }

    protected int getX(){
        return x;
    }

    protected int getY(){
        return y;
    }

    protected void updateCoords(int newX, int newY){
        x = newX;
        y = newY;
    }

}
