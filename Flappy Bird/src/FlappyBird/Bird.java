package FlappyBird;

import java.awt.*;

public class Bird extends Rectangle {

    private int yMotion;
    Bird(int winWidth, int winHeight)
    {
        this.height = 20;
        this.width = 20;
        this.x = winWidth / 2;
        this.y = winHeight / 2;

    }

    public int getYMotion()
    {
        return this.yMotion;
    }
    public void addToYMotion(int addValue)
    {
        yMotion = yMotion + addValue;
    }
    public void setYMotion(int newYMotion)
    {
        this.yMotion = newYMotion;
    }
    public void moveYCoord()
    {
        this.y += yMotion;
    }
    public void setX(int newX)
    {
        this.x = newX;
    }
    public void setY(int newY)
    {
        this.y = newY;
    }

}
