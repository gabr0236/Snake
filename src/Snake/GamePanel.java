package Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    private static final int UNIT_SIZE = 25;
    private static final int GAME_UNITS = UNIT_SIZE*UNIT_SIZE;
    private static final int DELAY = 60;
    private final int[] x = new int[GAME_UNITS];
    private final int[] y = new int[GAME_UNITS];
    private int bodyParts = 6;
    private int applesEaten;
    private int appleX;
    private int appleY;
    private char direction = 'R';
    private boolean running;
    private Timer timer;

    GamePanel() {
        this.setPreferredSize(new Dimension(GAME_UNITS, GAME_UNITS));
        this.setBackground(new Color(255, 221, 221));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();

    }

    public void newApple() {
        appleX = ((int)(Math.random()*UNIT_SIZE)*UNIT_SIZE);
        appleY = ((int)(Math.random()*UNIT_SIZE)*UNIT_SIZE);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {

        if (running) {
            g.setColor(new Color(217, 255, 223));
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(new Color(217, 255, 255));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(135, 206, 235));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);

                }
            }
            drawScore(g);
        } else {
            gameOver(g);
        }
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (direction == 'R' && direction != 'L')
            x[0]=x[0]+UNIT_SIZE;
        else if (direction == 'L' && direction != 'R')
            x[0]=x[0]-UNIT_SIZE;
        else if (direction == 'D' && direction != 'U')
            y[0]=y[0]+UNIT_SIZE;
        else if (direction == 'U' && direction != 'D')
            y[0]=y[0]-UNIT_SIZE;
    }


    public void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    public void checkCollisions() {
        //checks for head collision with body
        for (int i = bodyParts; 0 < i; i--) {
            if ((x[0]) == x[i] && y[0] == y[i]) {
                running = false;
                System.out.println("collisio x: " + x[i] +"  collisio y: " +y[i]);
            }
        }
        if (x[0] < 0) {
            running = false;
        } else if (x[0] == GAME_UNITS) {
            running = false;
        } else if (y[0] < 0) {
            running = false;
        } else if (y[0] == GAME_UNITS) {
            running = false;
        }

        if (!running) {
            timer.stop();
        }
    }

    public void drawScore(Graphics g) {
        g.setColor(new Color(255, 154, 162));
        g.setFont(new Font("Balker", Font.PLAIN, 40));
        FontMetrics fontMetrics1 = getFontMetrics(g.getFont());
        g.drawString("Score " + applesEaten, (GAME_UNITS - fontMetrics1.stringWidth("Score " + applesEaten)) / 2, g.getFont().getSize());
    }

    public void gameOver(Graphics g) {
        drawScore(g);

        g.setColor(new Color(255, 154, 162));
        g.setFont(new Font("Arial Black", Font.BOLD, 75));
        FontMetrics fontMetrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (GAME_UNITS - fontMetrics.stringWidth("Game Over")) / 2, (GAME_UNITS / 2));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();

            if (keyCode == KeyEvent.VK_RIGHT && direction != 'L')
                direction = 'R';
            else if (keyCode == KeyEvent.VK_LEFT && direction != 'R')
                direction = 'L';
            else if (keyCode == KeyEvent.VK_DOWN && direction != 'U')
                direction = 'D';
            else if (keyCode == KeyEvent.VK_UP && direction != 'D')
                direction = 'U';
        }

        @Override
        public void keyReleased(KeyEvent e) {
            super.keyReleased(e);
        }
    }
}

