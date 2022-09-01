/**
 * @(#)Fries.java
 *
 *
 * @Braden and Ritvik
 * @version 1.00 2020/2/27
 */
import javax.swing.ImageIcon;
import java.awt.Image;

public class Golf extends Item {

    private Image r;
    private Image l;

    public Golf()
    {
    	super();

    }
    public Golf(int h, int v)
    {

        super(h, v, 100, true);
        ImageIcon iid = new ImageIcon("Resources/golfr.png");
        Image I = iid.getImage();
        this.r = I;

        ImageIcon iil = new ImageIcon("Resources/golfl.png");
        Image L = iil.getImage();
        this.l = L;

    }
    public Image getImage()
    {
        if(getRight())
        {
            return this.r;
        }
        else{
            return this.l;
        }
    }


}