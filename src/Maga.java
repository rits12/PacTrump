/**
 * @(#)Maga.java
 *
 *
 * @Braden and Ritvik
 * @version 1.00 2020/2/27
 */
import javax.swing.ImageIcon;
import java.awt.Image;

public class Maga extends Item {


    public Maga()
    {
    	super();

    }
    public Maga(int h, int v)
    {
        super(h, v, 0, true);
        ImageIcon iid = new ImageIcon("Resources/maga.png");
        Image I = iid.getImage();
        setImage(I);

    }


}