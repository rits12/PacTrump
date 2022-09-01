/**
 * @(#)wall.java
 *
 *
 * @Braden and Ritvik
 * @version 1.00 2020/3/2
 */

import javax.swing.ImageIcon;
import java.awt.Image;
public class Wall extends Item {

    public Wall()
    {
    	super();
    }
    public Wall (int h, int v)
    {

        super(h, v, 0, false);
        ImageIcon iid = new ImageIcon("Resources/wall.png");
        Image I = iid.getImage();
        setImage(I);
    }



}