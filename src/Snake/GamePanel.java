package Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 60;
    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(new Color(255,221,221));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame(){
        newApple();
        running=true;
        timer=new Timer(DELAY, this);
        timer.start();
    }

    public void newApple(){
        appleX= random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY= random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){

        if(running){

            for (int i = 0; i < (SCREEN_HEIGHT/UNIT_SIZE); i++) {
                //g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,SCREEN_HEIGHT);
                //g.drawLine(0,i*UNIT_SIZE,SCREEN_WIDTH,i*UNIT_SIZE);
                //g.setColor(Color.darkGray);
            }
            g.setColor(new Color(217, 255, 223));
            g.fillOval(appleX,appleY,UNIT_SIZE,UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if(i==0){
                    g.setColor(new Color(217, 255, 255));
                    g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
                }
                else {
                    g.setColor(new Color(135, 206, 235));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);

                }
            }
            drawScore(g);
        }
        else{
            gameOver(g);
        }
    }


    public void move(){
        for (int i = bodyParts; i > 0; i--) {
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        switch (direction){
            case 'U':
                y[0]=y[0]-UNIT_SIZE;
                break;
            case 'D':
                y[0]=y[0]+UNIT_SIZE;
                break;
            case 'L':
                x[0]=x[0]-UNIT_SIZE;
                break;
            case 'R':
                x[0]=x[0]+UNIT_SIZE;
                break;

        }
    }


    public void checkApple(){
        if (x[0]==appleX && y[0]==appleY){
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    public void checkCollisions(){
        //checks for head collision with body
        for (int i = bodyParts; 0 < i; i--) {
            if ((x[0]) == x[i] && y[0]==y[i]){
                running=false;
            }
        }
        if (x[0] <0){
            running = false;
        }
        else if(x[0]>SCREEN_WIDTH){
            running = false;
        }
        else if(y[0]<0){
            running=false;
        }
        else if(y[0]>SCREEN_HEIGHT){
            running=false;
        }

        if(running==false){
            timer.stop();
        }
    }

    public void drawScore(Graphics g){
        g.setColor(new Color(255, 154, 162));
        g.setFont(new Font("AvenirNext",Font.PLAIN,40));
        FontMetrics fontMetrics1 = getFontMetrics(g.getFont());
        g.drawString("Score "+ applesEaten, (SCREEN_WIDTH-fontMetrics1.stringWidth("Score " + applesEaten))/2,g.getFont().getSize());
    }

    public void gameOver(Graphics g){
        drawScore(g);

        g.setColor(new Color(255, 154, 162));
        g.setFont(new Font("AvenirNext",Font.BOLD,75));
        FontMetrics fontMetrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH-fontMetrics.stringWidth("Game Over"))/2,(SCREEN_HEIGHT/2));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            int keyCode = e.getKeyCode();

            if (keyCode==KeyEvent.VK_RIGHT && direction!='L')
                direction = 'R';
            else if (keyCode==KeyEvent.VK_LEFT && direction!='R')
                direction = 'L';
            else if (keyCode==KeyEvent.VK_DOWN && direction!='U')
                direction = 'D';
            else if (keyCode==KeyEvent.VK_UP && direction!='D')
                direction = 'U';
            }
        }
    }

