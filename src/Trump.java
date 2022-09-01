/**
 * @(#)Trump.java
 *
 *
 * @Braden and Ritvik
 * @version 1.00 2020/2/27
 */

import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Trump extends Game
{
	private Image r;
	private Image l;
	private Image u;
	private Image d;
	private int lives;
	private int score;

    public Trump()
    {
    	super();
    }
    public Trump(int h, int v)
    {
    	super(h, v);
    	ImageIcon iir = new ImageIcon("Resources/trumpr.gif");
        	this.r = iir.getImage();
        ImageIcon iil = new ImageIcon("Resources/trumpl.gif");
        	this.l = iil.getImage();
        ImageIcon iiu = new ImageIcon("Resources/trumpu.gif");
        	this.u = iiu.getImage();
        ImageIcon iid = new ImageIcon("Resources/trumpd.gif");
        	this.d = iid.getImage();
        setSpeed(5);
        this.lives = 3;
        this.score = 0;
    }
	public Trump(int h, int v, int b)
	{

		super(h, v);
		ImageIcon iid = new ImageIcon("Resources/bars.png");
		Image I = iid.getImage();
		setImage(I);
	}


    public Image getImage()
    {
    	if(this.getRight())
    	{
    		return this.r;
    	}
		if(getCache() == 1)
		{
			return this.r;
		}
    	if(this.getLeft())
    	{
    		return this.l;
    	}
    	if(getCache() == 2)
		{
			return this.l;
		}
    	if(this.getUp())
    	{
    		return this.u;
    	}
    	if(getCache() == 3)
		{
			return this.u;
		}
    	if(this.getDown())
    	{
    		return this.d;
    	}
    	if(getCache() == 4)
		{
			return this.d;
		}
    	else {
        	return getImage();
    	}
    }

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}


}