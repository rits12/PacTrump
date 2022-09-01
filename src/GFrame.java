import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.lang.Math;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Random;

public class GFrame extends JFrame implements KeyListener {

    private static Trump t;
    private static Ghost c;
    private static Ghost n;
    private static Ghost w;
    private static Ghost m;
    private static ArrayList<Game> mueller;


    private static GCanvas biden;
    private JLabel stats;
    private boolean inGame;
    private int moneyLeft;
    private int level;

    public GFrame() {
        makeAmericaGreatAgain();
        pack();
        mueller = new ArrayList<Game>();
        t = new Trump(0, 0);
        mueller.add(t);
        c = new Ghost(570, 263, 1);
        mueller.add(c);
        n = new Ghost(570, 317, 2);
        mueller.add(n);
        w = new Ghost(570, 372, 3);
        mueller.add(w);
        m = new Ghost(570, 425, 4);
        mueller.add(m);
        spawnObjects();


        addKeyListener(this);
    }

    public void makeAmericaGreatAgain() {
        setTitle("Pac-Trump");
        setLayout(new BorderLayout());

        ImageIcon title = new ImageIcon("Resources/Title Screen.gif");
        JButton st = new JButton(title);
        add(st, BorderLayout.CENTER);
        requestFocus();
        setResizable(false);

        st.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                remove(st);
                start();
            }
        });
    }

    public void start() {
        biden = new GCanvas();
        add(biden, BorderLayout.CENTER);
        setSize(biden.getPreferredSize());
        setResizable(false);
        setLocationRelativeTo(null);
        requestFocus();

        this.level = 1;
        stats = new JLabel("");
        update();
        add(stats, BorderLayout.PAGE_END);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        biden.setDead(false);
        this.inGame = true;


    }

    @Override
    public void keyReleased(final KeyEvent keyEvent) {

        boolean call = checkWall(t);
        t.setLeft(false);
        t.setUp(false);
        t.setDown(false);
        t.setRight(false);
        randomMove();


    }

    @Override
    public void keyTyped(final KeyEvent keyEvent) {
    }

    public void keyPressed(final KeyEvent keyEvent) {


        if ((keyEvent.getKeyCode() == KeyEvent.VK_LEFT)) {
            if ((!checkGhost()) && (inGame) && checkWall(t)) {
                    checkItem();
                    t.setLeft(true);
                    t.move(biden.getPreferredSize());
                    randomMove();
                    biden.repaint();
                    t.setCache(2);

            }
        }

        if ((keyEvent.getKeyCode() == KeyEvent.VK_RIGHT)) {
            if ((!checkGhost()) && (inGame) && checkWall(t)) {
                checkItem();
                t.setRight(true);
                t.move(biden.getPreferredSize());
                randomMove();
                biden.repaint();
                t.setCache(1);
            }
        }

        if ((keyEvent.getKeyCode() == KeyEvent.VK_UP)) {
            if ((!checkGhost()) && (inGame) && checkWall(t)) {
                checkItem();
                t.setUp(true);
                t.move(biden.getPreferredSize());
                randomMove();
                biden.repaint();
                t.setCache(3);
            }
        }

        if ((keyEvent.getKeyCode() == KeyEvent.VK_DOWN)) {
            if ((!checkGhost()) && (inGame) && checkWall(t)) {
                checkItem();
                t.setDown(true);
                t.move(biden.getPreferredSize());
                randomMove();
                biden.repaint();
                t.setCache(4);
            }
        }

    }

    public boolean checkGhost() {
        if (collision(c, t)) {
            if (inGame && !c.isWeak()) {
                t.setLives((t.getLives() - 1));
                update();
                end();

                return true;
            }
            if(inGame && c.isWeak())
            {
                t.setScore((t.getScore()) + 100);
                c.setX(570);
                c.setY(263);
                c.setWeak(false);
            }
        }
        if (collision(n, t)) {
            if (inGame && !n.isWeak()) {
                t.setLives((t.getLives() - 1));
                update();
                end();
                return true;
            }
            if(inGame && n.isWeak())
            {
                t.setScore((t.getScore()) + 100);
                n.setX(570);
                n.setY(317);
                n.setWeak(false);

            }
        }
        if (collision(w, t)) {
            if (inGame && !w.isWeak()) {
                t.setLives((t.getLives() - 1));
                update();
                end();
                return true;
            }
            if(inGame && w.isWeak())
            {
                t.setScore((t.getScore()) + 100);
                w.setX(570);
                w.setY(372);
                w.setWeak(false);
            }
        }
        if (collision(m, t)) {
            if (inGame && !m.isWeak()) {
                t.setLives((t.getLives() - 1));
                update();
                end();
                return true;
            }
            if(inGame && m.isWeak())
            {
                t.setScore((t.getScore()) + 100);
                m.setX(570);
                m.setY(425);
                m.setWeak(false);
            }
        }
        return false;

    }

    public void checkItem() {
        for(int x = 0; x < mueller.size(); x++) {
            if (collision(mueller.get(x), t)) {
                if (mueller.get(x) instanceof Money) {
                    t.setScore(((Money) mueller.get(x)).getVal() + t.getScore());
                    mueller.remove(mueller.get(x));
                    moneyLeft--;
                    update();
                }

                if(mueller.get(x) instanceof Maga)
                {
                    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
                    mueller.remove(mueller.get(x));
                    frighten();
                    executorService.schedule(this::revert, 10, TimeUnit.SECONDS);


                }
                if(mueller.get(x) instanceof Golf)
                {
                    t.setScore(((Golf) mueller.get(x)).getVal() + t.getScore());
                    mueller.remove(mueller.get(x));
                    update();
                }
            }
        }

        if(moneyLeft <= 0)
        {
            advance();
        }
    }

    public boolean checkWall(Game q)
    {
        for(int x = 0; x < mueller.size(); x++) {
            if (collision(mueller.get(x), q)) {
                if (mueller.get(x) instanceof Wall) {

                        if(q.getRight())
                        {
                            q.setX(q.getX()-5);
                            if(q instanceof Ghost || q instanceof  Golf)
                            {
                                q.setRight(false);
                                q.setLeft(true);

                                if(q instanceof Ghost && ((Ghost) q).isWeak()){
                                    q.setCache(1);
                                }
                                else {
                                    q.setCache(3);
                                }
                            }

                            return false;
                        }
                        if(q.getLeft())
                        {
                            q.setX(q.getX()+5);
                            if(q instanceof Ghost || q instanceof  Golf)
                            {
                                q.setLeft(false);
                                q.setRight(true);

                                if(q instanceof Ghost && ((Ghost) q).isWeak()){
                                    q.setCache(2);
                                }
                                else {
                                    q.setCache(4);
                                }

                            }
                            return false;
                        }
                        if(q.getUp())
                        {
                            q.setY(q.getY() + 5);
                            if(q instanceof Ghost || q instanceof  Golf)
                            {
                                q.setUp(false);
                                q.setDown(true);

                                if(q instanceof Ghost && ((Ghost) q).isWeak()){
                                    q.setCache(1);
                                }
                                else {
                                    q.setCache(3);
                                }
                            }
                            return false;
                        }
                        if(q.getDown())
                        {
                            q.setY(q.getY() - 5);
                            if(q instanceof Ghost || q instanceof  Golf)
                            {
                                q.setDown(false);
                                q.setUp(true);

                                if(q instanceof Ghost && ((Ghost) q).isWeak()){
                                    q.setCache(2);
                                }
                                else {
                                    q.setCache(4);
                                }
                            }
                            return false;
                        }

                }
            }
            }
        return true;
    }
    
    public boolean collision(Game z, Game q) {
        ArrayList<Dimension> tBox = hitBox(q);
        ArrayList<Dimension> objectBox = hitBox(z);

        for (int x = 0; x < objectBox.size(); x++) {
            for (int y = 0; y < tBox.size(); y++) {
                if (tBox.get(y).equals(objectBox.get(x))) {
                    return true;
                }
            }
        }

        return false;

    }

    public ArrayList<Dimension> hitBox(Game z) {
        ArrayList<Dimension> bernieBox = new ArrayList<Dimension>();

        //top row of objects's image
        for (int x = -24; x <= 24; x++) {
            bernieBox.add(new Dimension(z.getX() + x, z.getY() + 24));
        }

        //first column of objects's image
        for (int y = -24; y <= 24; y++) {
            bernieBox.add(new Dimension(z.getX() - 24, z.getY() + y));
        }

        //bottom row of objects's image
        for (int x = -24; x <= 24; x++) {
            bernieBox.add(new Dimension(z.getX() + x, z.getY() - 24));
        }

        //last column of objects's image
        for (int y = -24; y <= 24; y++) {
            bernieBox.add(new Dimension(z.getX() + 24, z.getY() + y));
        }

        return bernieBox;
    }


    public void end() {
        if (t.getLives() > 0) {
            t.setX(0);
            t.setY(0);
        } else {
            biden.setDead(true);
            inGame = false;

        }
    }

    public void advance()
    {
        level++;
        int score = t.getScore();
        for(int x = 0; mueller.size() > 0; )
        {
            mueller.remove(mueller.get(x));
        }
        t = new Trump(0, 0);
        mueller.add(t);
        c = new Ghost(570, 263, 1);
        mueller.add(c);
        n = new Ghost(570, 317, 2);
        mueller.add(n);
        w = new Ghost(570, 372, 3);
        mueller.add(w);
        m = new Ghost(570, 425, 4);
        mueller.add(m);
        spawnObjects();

        t.setScore(score);

    }

    public void revert()
    {

        c.setWeak(false);
        n.setWeak(false);
        w.setWeak(false);
        m.setWeak(false);
    }

    public void frighten()
    {
        c.setWeak(true);
        n.setWeak(true);
        w.setWeak(true);
        m.setWeak(true);
    }

    public void update() {
        stats.setText("Lives: " + t.getLives() + "       Score: " + t.getScore() + "       Level: " + level);

    }

    public void spawnObjects() {

        //  //  //  //  //  //  //  //  //  //make ghost house box thing i don't know//  //  //  //  //  //  //  //  //  //
        mueller.add(new Wall(570, 209));
        mueller.add(new Wall(570, 479));

        mueller.add(new Golf(570, 155));


        //  //  //  //  //  //  //  //  //  //spawn some hats//  //  //  //  //  //  //  //  //  //

        int hat1 = (int)(Math.random()*2)+1;
        int hat2 = (int)(Math.random()*2)+1;

        if(hat1 == 1)
        {
            mueller.add(new Maga(565, 54));
        }
        if(hat1 == 2)
        {
            mueller.add(new Maga(565, 636));
        }
        if(hat2 == 1)
        {
            mueller.add(new Maga( 0, 370));
        }
        if(hat2 == 2)
        {
            mueller.add(new Maga( 1128, 370));
        }




        //  //  //  //  //  //  //  //  //  //generate second quadrant//  //  //  //  //  //  //  //  //  //

        Dimension q2 = new Dimension(500, 400);
        int topLeft = (int)(Math.random()*3)+1;


        //  //  //  //  //  //  //  //  //  //second quadrant prefab one//  //  //  //  //  //  //  //  //  //

        if (topLeft == 1) {
            for (int x = 55; x < q2.getWidth(); x += 54) {
                if (x != 109 && x != 271) {
                    mueller.add(new Wall(x, 54));
                }
            }
            for (int y = 55; y < q2.getHeight() * .8; y += 54) {
                if (y != 217) {
                    mueller.add(new Wall(55, y));
                }
            }
            for (int x = 165; x < q2.getWidth() - 54; x += 54) {
                if (x != 381) {
                    mueller.add(new Wall(x, (int) (q2.getHeight() / 2.45)));
                }
            }
            for (int x = 165; x < q2.getWidth() - 54; x += 54) {
                if (x != 273) {
                    mueller.add(new Wall(x, (int) (q2.getHeight() / 1.48)));
                }
            }
            for(int x = 54; x < q2.getWidth(); x += 55) {
                mueller.add(new Money(x, 00));
                moneyLeft ++;

            }

            for(int y = 54; y < q2.getHeight() -55; y += 55)
            {
                mueller.add(new Money(0, y));
                moneyLeft ++;
            }

            for(int x = 109; x < q2.getWidth(); x += 55) {
                mueller.add(new Money(x, 109));
                moneyLeft ++;

            }
            for(int x = 55; x < q2.getWidth(); x += 55)
            {
                mueller.add(new Money(x, 219));
                moneyLeft ++;
            }
            for(int x = 55; x < q2.getWidth(); x += 55)
            {
                mueller.add(new Money(x, 329));
                moneyLeft ++;
            }
            for(int y = 55; y < q2.getHeight()-55; y += 110) {
                mueller.add(new Money(109, y));
                moneyLeft++;
            }
            for(int y = 55; y < q2.getHeight()-55; y += 220) {
                mueller.add(new Money(274, y));
                moneyLeft++;
            }
            for(int y = 165; y < q2.getHeight()-55; y += 110) {
                mueller.add(new Money(q2.width - 7, y));
                moneyLeft++;
            }
            mueller.add(new Money( 384, 165));
            moneyLeft++;


        }
        //  //  //  //  //  //  //  //  //  //second quadrant prefab two//  //  //  //  //  //  //  //  //  //

        if (topLeft == 2) {
            for (int x = 55; x < q2.getWidth(); x += 54) {
                if (x != 217 && x != 379) {
                    mueller.add(new Wall(x, 54));
                }
            }
            for (int y = 54; y < q2.getHeight() * .8; y += 54) {
                if (y != 162) {
                    mueller.add(new Wall(55, y));
                }
            }
            for (int x = 165; x < q2.getWidth() - 54; x += 54) {
                if (x != 327 && x != 219) {
                    mueller.add(new Wall(x, (int) (q2.getHeight() / 2.45)));
                }
            }
            for (int x = 110; x < q2.getWidth() - 54; x += 54) {
                mueller.add(new Wall(x, (int) (q2.getHeight() / 1.48)));

            }

            for(int x = 54; x < q2.getWidth(); x += 55) {
                mueller.add(new Money(x, 00));
                moneyLeft ++;

            }

            for(int y = 54; y < q2.getHeight() -55; y += 55)
            {
                mueller.add(new Money(0, y));
                moneyLeft ++;
            }
            for(int x = 55; x < q2.getWidth(); x += 55)
            {
                mueller.add(new Money(x, 329));
                moneyLeft ++;
            }
            for(int x = 109; x < q2.getWidth(); x += 55) {
                mueller.add(new Money(x, 110));
                moneyLeft ++;

            }
            for(int x = 109; x < q2.getWidth(); x += 55) {
                mueller.add(new Money(x, 220));
                moneyLeft ++;

            }
            for(int y = 165; y < q2.getHeight()-55; y += 110) {
                mueller.add(new Money(q2.width - 7, y));
                moneyLeft++;
            }
            for(int y = 55; y < q2.getHeight() -165; y += 110)
            {
                mueller.add(new Money(220, y));
                moneyLeft ++;
            }
            for(int x = 55; x < 165; x += 55)
            {
                mueller.add(new Money(x, 165));
                moneyLeft ++;
            }
            mueller.add(new Money(330, 165));
            moneyLeft ++;
            mueller.add(new Money(384, 54));



        }
        //  //  //  //  //  //  //  //  //  //second quadrant prefab three//  //  //  //  //  //  //  //  //  //

        if (topLeft == 3) {
            for (int x = 55; x < q2.getWidth(); x += 54) {
                if (x != 109 && x != 271) {
                    mueller.add(new Wall(x, 55));
                }
            }
            for (int y = 55; y < q2.getHeight() * .8; y += 54) {
                if (y != 217 && y != 109) {
                    mueller.add(new Wall(57, y));
                }
            }
            mueller.add(new Wall(273, 217));
            mueller.add(new Wall(163, 109));
            for (int x = 111; x < q2.getWidth() - 54; x += 54) {
                if (x != 381) {
                    mueller.add(new Wall(x, (int) (q2.getHeight() / 2.45)));
                }
            }
            for (int x = 111; x < q2.getWidth() - 54; x += 54) {
                if (x != 219) {
                    mueller.add(new Wall(x, (int) (q2.getHeight() / 1.48)));
                }
            }

            for(int x = 54; x < q2.getWidth(); x += 55) {
                mueller.add(new Money(x, 00));
                moneyLeft ++;

            }

            for(int y = 54; y < q2.getHeight() -55; y += 55)
            {
                mueller.add(new Money(0, y));
                moneyLeft ++;
            }
            for(int x = 55; x < q2.getWidth(); x += 55)
            {
                mueller.add(new Money(x, 329));
                moneyLeft ++;
            }
            for(int x = 55; x < q2.getWidth(); x += 55)
            {
                if(x!= 275) {
                    mueller.add(new Money(x, 219));
                    moneyLeft++;
                }
            }
            for(int x = 55; x < q2.getWidth(); x += 55)
            {
                if(x!= 165) {
                    mueller.add(new Money(x, 110));
                    moneyLeft++;
                }
            }
            for(int y = 165; y < q2.getHeight()-55; y += 110) {
                mueller.add(new Money(q2.width - 6, y));
                moneyLeft++;
            }
            mueller.add(new Money(110, 54));
            moneyLeft ++;
            mueller.add(new Money(274, 54));
            moneyLeft ++;
            mueller.add(new Money(384, 165));
            moneyLeft ++;
            mueller.add(new Money(220, 274));
            moneyLeft ++;


        }

        //  //  //  //  //  //  //  //  //  //generate first quadrant//  //  //  //  //  //  //  //  //  //

        Dimension q1 = new Dimension(1128, 400);
        int topRight = (int)(Math.random()*3)+1;

        //  //  //  //  //  //  //  //  //  //first quadrant prefab one//  //  //  //  //  //  //  //  //  //

        if (topRight == 1) {
            for (int x = 642; x < q1.getWidth() - 55; x += 54) {
                if (x != 1020 && x != 858)
                    mueller.add(new Wall(x, 54));
            }
            for (int y = 55; y < q1.getHeight() * .8; y += 54) {
                if (y != 217)
                    mueller.add(new Wall(1073, y));
            }
            for (int x = 696; x < q1.getWidth() - 110; x += 54) {
                if (x != 750)
                    mueller.add(new Wall(x, (int) (q1.getHeight() / 2.45)));
            }
            for (int x = 696; x < q1.getWidth() - 110; x += 54) {
                if (x != 858)
                    mueller.add(new Wall(x, (int) (q1.getHeight() / 1.48)));
            }

            for(int x = 642; x < q1.getWidth() + 54; x += 54) {
                mueller.add(new Money(x, 00));
                moneyLeft ++;

            }

            for(int y = 54; y < q1.getHeight() -55; y += 55)
            {
                mueller.add(new Money(q1.width, y));
                moneyLeft ++;
            }

            for(int x = 642; x < q1.getWidth() -54; x += 54) {
                mueller.add(new Money(x, 109));
                moneyLeft ++;

            }
            for(int x = 642; x < q1.getWidth(); x += 54)
            {
                mueller.add(new Money(x, 329));
                moneyLeft ++;
            }
            for(int x = 642; x < q1.getWidth(); x += 54)
            {
                mueller.add(new Money(x, 219));
                moneyLeft ++;
            }
            for(int y = 54; y < q1.getHeight() -55; y += 110)
            {
                mueller.add(new Money(q1.width-109, y));
                moneyLeft ++;
            }
            mueller.add(new Money(858, 54));
            moneyLeft++;
            mueller.add(new Money(858, 270));
            moneyLeft++;
            mueller.add(new Money(750, 165));
            moneyLeft ++;
            mueller.add(new Money(642, 165));
            moneyLeft ++;
            mueller.add(new Money(642, 275));
            moneyLeft ++;



        }
        //  //  //  //  //  //  //  //  //  //first quadrant prefab two//  //  //  //  //  //  //  //  //  //

        if (topRight == 2) {
            for (int x = 642; x < q1.getWidth() - 55; x += 54) {
                if (x != 912 && x != 750)
                    mueller.add(new Wall(x, 54));
            }
            for (int y = 54; y < q1.getHeight() * .8; y += 54) {
                if (y != 163)
                    mueller.add(new Wall(1073, y));
            }
            for (int x = 696; x < q1.getWidth() - 110; x += 54) {
                if (x != 804 && x != 912)
                    mueller.add(new Wall(x, (int) (q1.getHeight() / 2.45)));
            }
            for (int x = 696; x < q1.getWidth() - 54; x += 54) {
                mueller.add(new Wall(x, (int) (q1.getHeight() / 1.48)));
            }

            for(int x = 642; x < q1.getWidth() + 54; x += 54) {
                mueller.add(new Money(x, 00));
                moneyLeft ++;

            }

            for(int y = 54; y < q1.getHeight() -55; y += 55)
            {
                mueller.add(new Money(q1.width, y));
                moneyLeft ++;
            }

            for(int x = 642; x < q1.getWidth() -54; x += 54) {
                mueller.add(new Money(x, 109));
                moneyLeft ++;

            }
            for(int x = 642; x < q1.getWidth(); x += 54)
            {
                mueller.add(new Money(x, 329));
                moneyLeft ++;
            }
            for(int x = 642; x < q1.getWidth()-55; x += 54)
            {
                mueller.add(new Money(x, 219));
                moneyLeft ++;
            }
            mueller.add(new Money(913, 54));
            moneyLeft++;
            mueller.add(new Money(804, 165));
            moneyLeft++;
            mueller.add(new Money(750, 55));
            moneyLeft ++;
            mueller.add(new Money(642, 165));
            moneyLeft ++;
            mueller.add(new Money(642, 275));
            moneyLeft ++;
            mueller.add(new Money(913, 165));
            moneyLeft++;
            mueller.add(new Money(1021, 165));
            moneyLeft++;


        }
        //  //  //  //  //  //  //  //  //  //first quadrant prefab three//  //  //  //  //  //  //  //  //  //

        if (topRight == 3) {
            for (int x = 642; x < q1.getWidth() - 55; x += 54) {
                if (x != 1020 && x != 858)
                    mueller.add(new Wall(x, 55));
            }
            for (int y = 55; y < q1.getHeight() * .8; y += 54) {
                if (y != 217 && y != 109)
                    mueller.add(new Wall(1073, y));
            }
            mueller.add(new Wall(858, 217));
            mueller.add(new Wall(966, 109));
            for (int x = 696; x < q1.getWidth() - 55; x += 54) {
                if (x != 750)
                    mueller.add(new Wall(x, (int) (q1.getHeight() / 2.45)));
            }
            for (int x = 696; x < q1.getWidth() - 55; x += 54) {
                if (x != 912)
                    mueller.add(new Wall(x, (int) (q1.getHeight() / 1.48)));
            }

            for(int x = 642; x < q1.getWidth() + 54; x += 54) {
                mueller.add(new Money(x, 00));
                moneyLeft ++;

            }

            for(int y = 54; y < q1.getHeight() -55; y += 55)
            {
                mueller.add(new Money(q1.width, y));
                moneyLeft ++;
            }

            for(int x = 642; x < q1.getWidth() ; x += 54) {
                if(x != 966) {
                    mueller.add(new Money(x, 109));
                    moneyLeft++;
                }
            }
            for(int x = 642; x < q1.getWidth(); x += 54)
            {
                mueller.add(new Money(x, 329));
                moneyLeft ++;
            }
            for(int x = 642; x < q1.getWidth(); x += 54)
            {
                if(x!= 858) {
                    mueller.add(new Money(x, 219));
                    moneyLeft++;
                }
            }
            mueller.add(new Money(859, 54));
            moneyLeft++;
            mueller.add(new Money(750, 165));
            moneyLeft ++;
            mueller.add(new Money(642, 165));
            moneyLeft ++;
            mueller.add(new Money(642, 275));
            moneyLeft ++;
            mueller.add(new Money(913, 275));
            moneyLeft++;
            mueller.add(new Money(1021, 54));
            moneyLeft++;

        }


        //  //  //  //  //  //  //  //  //  //generate third quadrant//  //  //  //  //  //  //  //  //  //

        Dimension q3 = new Dimension(500, 700);
        int bottomLeft = (int)(Math.random()*3)+1;

        //  //  //  //  //  //  //  //  //  //third quadrant prefab one//  //  //  //  //  //  //  //  //  //

        if (bottomLeft == 1) {
            for (int x = 55; x < q3.getWidth(); x += 54) {
                if (x != 109 && x != 271)
                    mueller.add(new Wall(x, 636));
            }
            for (int y = 636; y > 400; y -= 54) {
                if (y != 474)
                    mueller.add(new Wall(55, y));
            }
            for (int x = 165; x < q3.getWidth() - 54; x += 54) {
                if (x != 381)
                    mueller.add(new Wall(x, (int) (q3.getHeight() * .75)));
            }
            for (int x = 165; x < q3.getWidth() - 54; x += 54) {
                if (x != 273) {
                    mueller.add(new Wall(x, (int) (q3.getHeight() / 1.67)));
                }
            }

            for(int x = 54; x < q3.getWidth(); x += 55) {
                mueller.add(new Money(x, q3.height - 15));
                moneyLeft ++;

            }

            for(int y = q3.height-15 ; y > 400; y -= 53)
            {
                mueller.add(new Money(0, y));
                moneyLeft ++;
            }

            for(int x = 55; x < q3.getWidth(); x += 55)
            {
                mueller.add(new Money(x, 473));
                moneyLeft ++;
            }
            for(int x = 110; x < q3.getWidth(); x += 55)
            {
                    mueller.add(new Money(x, 579));
                    moneyLeft++;

            }
            for(int y = 632; y >400; y -= 108) {
                mueller.add(new Money(109, y));
                moneyLeft++;
            }
            for(int y = 632; y > 400; y -= 218) {
                mueller.add(new Money(274, y));
                moneyLeft++;
            }
            for(int y = 632; y > 400; y -= 110) {
                if (y!= 632) {
                    mueller.add(new Money(q3.width - 7, y));
                    moneyLeft++;
                }
            }
            mueller.add(new Money( 384, 524));
            moneyLeft++;


        }
        //  //  //  //  //  //  //  //  //  //third quadrant prefab two//  //  //  //  //  //  //  //  //  //

        if (bottomLeft == 2) {
            for (int x = 55; x < q3.getWidth(); x += 54) {
                if (x != 217 && x != 379)
                    mueller.add(new Wall(x, 636));
            }
            for (int y = 636; y > 400; y -= 54) {
                if (y != 528)
                    mueller.add(new Wall(55, y));
            }
            for (int x = 165; x < q3.getWidth() - 54; x += 54) {
                if (x != 327 && x != 219)
                    mueller.add(new Wall(x, (int) (q3.getHeight() * .75)));
            }
            for (int x = 110; x < q2.getWidth() - 54; x += 54) {
                mueller.add(new Wall(x, (int) (q3.getHeight() / 1.67)));

            }

            for(int x = 54; x < q3.getWidth(); x += 55) {
                mueller.add(new Money(x, q3.height - 15));
                moneyLeft ++;

            }

            for(int y = q3.height-15 ; y > 400; y -= 53)
            {
                mueller.add(new Money(0, y));
                moneyLeft ++;
            }

            for(int x = 110; x < q3.getWidth(); x += 55)
            {
                mueller.add(new Money(x, 473));
                moneyLeft ++;
            }
            for(int x = 110; x < q3.getWidth(); x += 55)
            {
                mueller.add(new Money(x, 579));
                moneyLeft++;

            }

            for(int y = 632; y > 454; y -= 110) {
                mueller.add(new Money(220, y));
                moneyLeft++;
            }
            for(int y = 632; y > 400; y -= 110) {
                if (y!= 632) {
                    mueller.add(new Money(q3.width - 7, y));
                    moneyLeft++;
                }
            }
            mueller.add(new Money( 330, 524));
            moneyLeft++;
            mueller.add(new Money( 55, 524));
            moneyLeft++;
            mueller.add(new Money( 110, 524));
            moneyLeft++;
            mueller.add(new Money( 384, 632));
            moneyLeft++;

        }
        //  //  //  //  //  //  //  //  //  //third quadrant prefab three//  //  //  //  //  //  //  //  //  //

        if (bottomLeft == 3) {
            for (int x = 55; x < q3.getWidth(); x += 54) {
                if (x != 109 && x != 271)
                    mueller.add(new Wall(x, 636));
            }
            mueller.add(new Wall(163, 582));
            for (int y = 636; y > 400; y -= 54) {
                if (y != 474 && y != 582)
                    mueller.add(new Wall(57, y));
            }
            mueller.add(new Wall(273, 473));
            for (int x = 111; x < q3.getWidth() - 54; x += 54) {
                if (x != 381)
                    mueller.add(new Wall(x, (int) (q3.getHeight() * .755)));
            }
            for (int x = 111; x < q2.getWidth() - 54; x += 54) {
                if (x != 219) {
                    mueller.add(new Wall(x, (int) (q3.getHeight() / 1.665)));
                }
            }

            for(int x = 54; x < q3.getWidth(); x += 55) {
                mueller.add(new Money(x, q3.height - 15));
                moneyLeft ++;

            }

            for(int y = q3.height-15 ; y > 400; y -= 53)
            {
                mueller.add(new Money(0, y));
                moneyLeft ++;
            }

            for(int x = 55; x < q3.getWidth(); x += 55)
            {
                if(x!= 275) {
                    mueller.add(new Money(x, 473));
                    moneyLeft++;
                }
            }
            for(int x = 55; x < q3.getWidth(); x += 55)
            {
                if(x!= 165) {
                    mueller.add(new Money(x, 579));
                    moneyLeft++;
                }
            }

            mueller.add(new Money( 384, 524));
            moneyLeft++;
            mueller.add(new Money( 220, 418));
            moneyLeft++;
            mueller.add(new Money( 273, 632));
            moneyLeft++;
            mueller.add(new Money( 110, 632));
            moneyLeft++;
            mueller.add(new Money( q3.width- 7, 418 ));
            moneyLeft++;
            mueller.add(new Money( q3.width- 7, 524 ));
            moneyLeft++;


        }


        //  //  //  //  //  //  //  //  //  //generate fourth quadrant//  //  //  //  //  //  //  //  //  //

        Dimension q4 = new Dimension(1128, 700);
        int bottomRight = (int)(Math.random()*3)+1;


        //  //  //  //  //  //  //  //  //  //fourth quadrant prefab one//  //  //  //  //  //  //  //  //  //

        if (bottomRight == 1) {
            for (int x = 642; x < q4.getWidth(); x += 54) {
                if (x != 1020 && x != 858)
                    mueller.add(new Wall(x, 636));
            }
            for (int y = 636; y > 400; y -= 54) {
                if (y != 474)
                    mueller.add(new Wall((int) q4.getWidth() - 54, y));
            }
            for (int x = 696; x < q4.getWidth() - 110; x += 54) {
                if (x != 750)
                    mueller.add(new Wall(x, (int) (q4.getHeight() * .75)));
            }
            for (int x = 696; x < q4.getWidth() - 110; x += 54) {
                if (x != 858)
                    mueller.add(new Wall(x, (int) (q4.getHeight() / 1.67)));
            }

            for(int x = 642; x < q4.getWidth() + 54; x += 54) {
                mueller.add(new Money(x, q4.height - 15));
                moneyLeft ++;

            }

            for(int y = 636; y > 400; y -= 54)
            {
                mueller.add(new Money(q4.width, y));
                moneyLeft ++;
            }

            for(int x = 642; x < q4.getWidth() -54; x += 54) {
                mueller.add(new Money(x, 582));
                moneyLeft ++;

            }
            for(int x = 642; x < q4.getWidth(); x += 54)
            {
                mueller.add(new Money(x, 329));
                moneyLeft ++;
            }
            for(int x = 642; x < q4.getWidth(); x += 54)
            {
                mueller.add(new Money(x, 474));
                moneyLeft ++;
            }
            for(int y = 636; y >400 ; y -= 110)
            {
                mueller.add(new Money(q4.width-109, y));
                moneyLeft ++;
            }
            mueller.add(new Money(858, 420));
            moneyLeft++;
            mueller.add(new Money(858, 636));
            moneyLeft++;
            mueller.add(new Money(750, 528));
            moneyLeft ++;
            mueller.add(new Money(642, 420));
            moneyLeft ++;
            mueller.add(new Money(642, 528));
            moneyLeft ++;


        }
        //  //  //  //  //  //  //  //  //  //fourth quadrant prefab two//  //  //  //  //  //  //  //  //  //

        if (bottomRight == 2) {
            for (int x = 642; x < q4.getWidth(); x += 54) {
                if (x != 912 && x != 750)
                    mueller.add(new Wall(x, 636));
            }
            for (int y = 636; y > 400; y -= 54) {
                if (y != 528)
                    mueller.add(new Wall((int) q4.getWidth() - 54, y));
            }
            for (int x = 696; x < q4.getWidth() - 110; x += 54) {
                if (x != 804 && x != 912)
                    mueller.add(new Wall(x, (int) (q4.getHeight() * .75)));
            }
            for (int x = 696; x < q1.getWidth() - 54; x += 54) {
                mueller.add(new Wall(x, (int) (q4.getHeight() / 1.67)));
            }

            for(int x = 642; x < q4.getWidth() + 54; x += 54) {
                mueller.add(new Money(x, q4.height - 15));
                moneyLeft ++;

            }

            for(int y = 636; y > 400; y -= 54)
            {
                mueller.add(new Money(q4.width, y));
                moneyLeft ++;
            }

            for(int x = 642; x < q4.getWidth() -54; x += 54) {
                mueller.add(new Money(x, 582));
                moneyLeft ++;

            }

            for(int x = 642; x < q4.getWidth()-54; x += 54)
            {
                mueller.add(new Money(x, 474));
                moneyLeft ++;
            }
            mueller.add(new Money(912, 528));
            moneyLeft++;
            mueller.add(new Money(912, 636));
            moneyLeft++;
            mueller.add(new Money(750, 636));
            moneyLeft ++;
            mueller.add(new Money(642, 420));
            moneyLeft ++;
            mueller.add(new Money(642, 528));
            moneyLeft ++;
            mueller.add(new Money(804, 528));
            moneyLeft ++;
            mueller.add(new Money(q4.width -54, 528));
            moneyLeft ++;
            mueller.add(new Money(q4.width -108, 528));
            moneyLeft ++;

        }
        //  //  //  //  //  //  //  //  //  //fourth quadrant prefab three//  //  //  //  //  //  //  //  //  //

        if (bottomRight == 3) {
            for (int x = 642; x < q4.getWidth(); x += 54) {
                if (x != 1020 && x != 858)
                    mueller.add(new Wall(x, 635));
            }
            for (int y = 635; y > 400; y -= 54) {
                if (y != 473 && y != 581)
                    mueller.add(new Wall((int) q4.getWidth() - 54, y));
            }
            for (int x = 696; x < q4.getWidth() - 55; x += 54) {
                if (x != 750)
                    mueller.add(new Wall(x, (int) (q4.getHeight() * .753)));
            }
            mueller.add(new Wall(858, 473));
            mueller.add(new Wall(966, 581));
            for (int x = 696; x < q1.getWidth() - 55; x += 54) {
                if (x != 912)
                    mueller.add(new Wall(x, (int) (q4.getHeight() / 1.67)));
            }

            for(int x = 642; x < q4.getWidth() + 54; x += 54) {
                mueller.add(new Money(x, q4.height - 15));
                moneyLeft ++;

            }

            for(int y = 636; y > 400; y -= 54)
            {
                mueller.add(new Money(q4.width, y));
                moneyLeft ++;
            }

            for(int x = 642; x < q4.getWidth() -54; x += 54) {
                if(x!= 966) {
                    mueller.add(new Money(x, 582));
                    moneyLeft++;
                }

            }

            for(int x = 642; x < q4.getWidth(); x += 54)
            {
                if(x!= 858) {
                    mueller.add(new Money(x, 474));
                    moneyLeft++;
                }
            }
            mueller.add(new Money(912, 420));
            moneyLeft++;
            mueller.add(new Money(858, 636));
            moneyLeft++;
            mueller.add(new Money(750, 528));
            moneyLeft ++;
            mueller.add(new Money(642, 420));
            moneyLeft ++;
            mueller.add(new Money(642, 528));
            moneyLeft ++;
            mueller.add(new Money(q4.width -54, 582));
            moneyLeft ++;
            mueller.add(new Money(q4.width -108, 636));
            moneyLeft ++;

        }


    }

    public static ArrayList<Game> getMueller ()
        {
            return mueller;
        }

    public void randomMove()
    {
            for (int x = 0; x < mueller.size(); x++) {
                if ((mueller.get(x) instanceof Ghost) || (mueller.get(x) instanceof Golf)) {

                    mueller.get(x).move(biden.getPreferredSize());
                    if(mueller.get(x).getX() >= (biden.getPreferredSize().getWidth() - 75) )
                    {
                        mueller.get(x).setRight(false);
                        mueller.get(x).setX(mueller.get(x).getX() - 3);
                        int flip = new Random().nextInt(3);
                        if(flip == 1)
                        {
                            mueller.get(x).setLeft(true);
                        }
                        if(flip == 2)
                        {
                            mueller.get(x).setUp(true);
                        }
                        if(flip == 3)
                        {
                            mueller.get(x).setDown(true);
                        }

                    }
                    if(mueller.get(x).getX() <= 0)
                    {

                        mueller.get(x).setLeft(false);
                        mueller.get(x).setX(mueller.get(x).getX() + 3);
                        int flip = new Random().nextInt(3);
                        if(flip == 1)
                        {
                            mueller.get(x).setRight(true);
                        }
                        if(flip == 2)
                        {
                            mueller.get(x).setUp(true);
                        }
                        if(flip == 3)
                        {
                            mueller.get(x).setDown(true);
                        }

                    }
                    if(mueller.get(x).getY() <= 0)
                    {
                        mueller.get(x).setUp(false);
                        mueller.get(x).setY(mueller.get(x).getY() + 3);
                        int flip = new Random().nextInt(3);
                        if(flip == 1)
                        {
                            mueller.get(x).setRight(true);
                        }
                        if(flip == 2)
                        {
                            mueller.get(x).setLeft(true);
                        }
                        if(flip == 3)
                        {
                            mueller.get(x).setDown(true);
                        }

                    }
                    if(mueller.get(x).getY() <= (biden.getPreferredSize().getHeight() - 115))
                    {
                        mueller.get(x).setDown(false);
                        mueller.get(x).setY(mueller.get(x).getY() - 3);
                        int flip = new Random().nextInt(3);
                        if(flip == 1)
                        {
                            mueller.get(x).setRight(true);
                        }
                        if(flip == 2)
                        {
                            mueller.get(x).setUp(true);
                        }
                        if(flip == 3)
                        {
                            mueller.get(x).setLeft(true);
                        }

                    }

                }
            }


    }


    }



