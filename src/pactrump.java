/**
 * @(#)pactrump.java
 *
 *
 * @author
 * @version 1.00 2020/3/6
 */


import java.awt.EventQueue;
import javax.swing.JFrame;

public final class pactrump{

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                final GFrame hillary = new GFrame();
                hillary.setVisible(true);
            }


        });
    }





}