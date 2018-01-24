package mx.com.atmen.drunkapp;

public class Enemy {

    protected int id;
    protected int x;
    protected int y;

    Enemy(int id, int x, int y){
        this.id = id;
        this.x = x;
        this.y = y;
    }

    protected int getId(){
        return id;
    }

    protected int getX(){
        return x;
    }

    protected int getY(){
        return y;
    }

}
