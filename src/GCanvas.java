import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class GCanvas extends JComponent
{

    private static final Dimension PREFFERED_SIZE = new Dimension(1200, 800);
    private boolean dead;


    public void paintComponent(Graphics g)
    {
        g.setColor(Color.black);
        g.fillRect(0, 0 , getWidth(), getHeight());

        Border compound;
        Border raisedbevel = BorderFactory.createRaisedBevelBorder();
        Border loweredbevel = BorderFactory.createLoweredBevelBorder();

        Border redline = BorderFactory.createLineBorder(Color.blue);

        compound = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);
        this.setBorder(compound);

        compound = BorderFactory.createCompoundBorder(redline, compound);
        this.setBorder(compound);


        for(int x = 0; x < GFrame.getMueller().size(); x++) {
            g.drawImage(GFrame.getMueller().get(x).getImage(), GFrame.getMueller().get(x).getX(), GFrame.getMueller().get(x).getY(), this);

        }

        if(dead)
        {
            String F = "T R U M P  2 0 2 0";
            Font donald = new Font("FF Meta Bold", Font.BOLD, 69);
            FontMetrics metr = getFontMetrics(donald);

            g.setColor(Color.red);
            g.fillRect(0, 0 , getWidth(), getHeight());

            g.setColor(Color.white);
            this.setFont(donald);
            g.drawString(F, getWidth()/2 - (metr.stringWidth(F)/2), getHeight()/2);
        }
    }

    public Dimension getPreferredSize()
    {
        return PREFFERED_SIZE;
    }


    public void setDead(boolean dead) {
        this.dead = dead;
    }


}
