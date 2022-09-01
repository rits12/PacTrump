/**
 * @(#)Game.java
 *
 *
 * @braden and ritvik
 * @version 1.00 2020/2/27
 */

import javax.swing.ImageIcon;
import java.awt.*;

public class Game
{

	private int x;
	private int y;
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	private int cache;
	private Image p;
	private int speed;

    public Game()
    {
    	this.x = 0;
    	this.y = 0;
    	this.left = false;
    	this.right = false;
    	this.up = false;
    	this.down = false;
    	ImageIcon iid = new ImageIcon("resources/ross.png");
        this.p = iid.getImage();
        this.cache = 0;
        this.speed = 0;

    }
    public Game(int h, int v)
    {
    	this.x = h;
    	this.y = v;
    	this.left = false;
    	this.right = false;
    	this.up = false;
    	this.down = false;
        ImageIcon iid = new ImageIcon("resources/ross.png");
        this.p = iid.getImage();
        this.cache = 1;
        this.speed = 3;

    }
    public int getX()
    {
    	return this.x;
    }
    public void setX(int h)
    {
    	this.x = h;
    }
    public int getY()
    {
    	return this.y;
    }
    public void setY(int v)
    {
    	this.y = v;
    }
    public boolean getLeft()
    {
        return this.left;
    }
    public void setLeft(boolean l)
    {
        this.left = l;
    }
    public boolean getRight()
    {
        return this.right;
    }
    public void setRight(boolean r)
    {
        this.right = r;
    }
    public boolean getUp()
    {
        return this.up;
    }
    public void setUp(boolean u)
    {
        this.up = u;
    }
    public boolean getDown()
    {
        return this.down;
    }
    public void setDown(boolean d)
    {
        this.down = d;
    }
    public Image getRoss()
    {
        return this.p;
    }
    public int getCache() {
        return cache;
    }
    public void setCache(int cache) {
        this.cache = cache;
    }
    public Image getImage() {return this.p;}
    public void setImage(Image p) {this.p = p;}
    public void move(Dimension x)
    {
        //System.out.println("im at " + getX() + ", " + getY());
        if(getRight())
        {
            if(getX() <= (x.getWidth() - 75)) {
                setX(getX() + speed);
            }
        }
        if(getLeft())
        {
            if(getX() >= speed) {
                setX(getX() - speed);
            }
        }
        if(getUp())
        {
            if(getY() >= speed) {
                setY(getY() - speed);
            }
        }
        if(getDown())
        {
            if(getY() <= (x.getHeight() - 115)) {
                setY(getY() + speed);
            }
        }

    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public int getSpeed() {return this.speed;}
}