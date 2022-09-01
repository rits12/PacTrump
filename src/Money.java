/**
 * @(#)Money.java
 *
 *
 * @author
 * @version 1.00 2020/2/27
 */
import javax.swing.ImageIcon;
import java.awt.Image;

public class Money extends Item{


    public Money() {
    	super();

    }
    public Money(int h, int v)
    {

        super(h, v, 10, true);
        ImageIcon iid = new ImageIcon("Resources/money.png");
        Image I = iid.getImage();
        setImage(I);
    }


}