/**
 * @(#)Item.java
 *
 *
 * @Braden and Ritvik
 * @version 1.00 2020/2/27
 */

import javax.swing.ImageIcon;
import java.awt.Image;
public class Item extends Game
{
	private Image i;
	private int val;
	private boolean canPickUp;

    public Item()
    {
    	super();
    	this.val = 0;
    	this.canPickUp = false;
    }
    public Item(int h, int v, int x, boolean c)
    {
    	super(h, v);
        this.val = x;
        this.canPickUp = c;
    }
    public Image getImage()
    {
    	return this.i;
    }
    public void setImage(Image x) {this. i =x; }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }
}