/**
 * @(#)Ghost.java
 *
 *
 * @Braden and Ritvik
 * @version 1.00 2020/2/27
 */

import javax.swing.ImageIcon;
import java.awt.Image;

public class Ghost extends Game
{
	private Image r;
	private Image l;
	private Image fr;
	private Image fl;
	private boolean weak;

	public Ghost()
    {
     	super();
     	this.weak = false;
    }
    public Ghost(int h, int v, int x)
    {
		super(h,v);
		if(x == 1)
		{
			ImageIcon iir = new ImageIcon("Resources/cnnr.png");
        	this.r = iir.getImage();
        	ImageIcon iil = new ImageIcon("Resources/cnnl.png");
        	this.l = iil.getImage();
		}
		else if(x==2)
		{
			ImageIcon iir = new ImageIcon("Resources/nytr.png");
        	this.r = iir.getImage();
        	ImageIcon iil = new ImageIcon("Resources/nytl.png");
        	this.l = iil.getImage();
		}
		else if(x==3)
		{
			ImageIcon iir = new ImageIcon("Resources/wsjr.png");
        	this.r = iir.getImage();
        	ImageIcon iil = new ImageIcon("Resources/wsjl.png");
        	this.l = iil.getImage();
		}
		else if(x==4)
		{
			ImageIcon iir = new ImageIcon("Resources/mexr.png");
        	this.r = iir.getImage();
        	ImageIcon iil = new ImageIcon("Resources/mexl.png");
        	this.l = iil.getImage();
		}
		ImageIcon iifr = new ImageIcon("Resources/fr.png");
        this.fr = iifr.getImage();
        ImageIcon iifl = new ImageIcon("Resources/fl.png");
        this.fl = iifl.getImage();
        this.weak = false;
        setRight(true);

	}
	public Image getImage()
    {
		if(getRight() && weak)
		{
			return this.fr;
		}

		if(getLeft() && weak)
		{
			return this.fl;
		}

    	if(getRight())
    	{
    		return this.r;
    	}

    	if(getLeft())
    	{
    		return this.l;
    	}
    	else {

        	return getRoss();

    	}
    }

	public boolean isWeak() {
		return weak;
	}

	public void setWeak(boolean weak) {
		this.weak = weak;
	}
}