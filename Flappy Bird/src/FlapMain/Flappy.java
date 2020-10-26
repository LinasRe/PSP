package FlapMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class Flappy extends JPanel implements ActionListener, KeyListener {

    public static Flappy flappy;
    public int yMotion, score = 0, groundHeight = 150, birdSize = 20, highscore = 0;
    public int windowWidth = 1200, windowHeight = 800;
    public Rectangle bird;
    public ArrayList<Rectangle> pipes;
    public Random random;
    boolean gameOver, isStarted;

    public Flappy() {

        JFrame jFrame = new JFrame();
        jFrame.setSize(windowWidth, windowHeight);
        jFrame.add(this);
        jFrame.addKeyListener(this);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setTitle("Flappy Rectangle");
        jFrame.setResizable(true);

        bird = new Rectangle(windowWidth / 2, windowHeight / 2, birdSize, birdSize);
        pipes = new ArrayList<>();
        random = new Random();
        Timer timer = new Timer(20, this);

        timer.start();

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        int pipeSpeed = 10;

        if(isStarted) {
            for (Rectangle pipe: pipes) { pipe.x -= pipeSpeed; }

            if (yMotion < 15) { yMotion ++; }

            bird.y += yMotion;

            for (int i = 0; i < pipes.size(); i++) {
                if (pipes.get(i).x + pipes.get(i).width < 0) {
                    pipes.remove(pipes.get(i));
                    if (pipes.get(i).y == 0) { addPipe(false); }
                }
            }

            for (Rectangle pipe : pipes) {
                if(pipe.x + pipe.width == bird.x && pipe.y < bird.y && !gameOver) {
                    score++;
                }
                if (pipe.intersects(bird)) {
                    gameOver = true;
                    if(bird.x <=  pipe.x) {
                        bird.x = pipe.x - bird.width;
                    }
                    else {
                        if(pipe.y != 0) {
                            bird.y = pipe.y - bird.height;
                        }
                        else if(bird.y < pipe.height) {
                            bird.y = pipe.height;
                        }
                    }
                }
            }
            if (bird.y > windowHeight - groundHeight - bird.height || bird.y < 0) {
                gameOver = true;
            }
            if(bird.y + yMotion >= windowHeight - groundHeight) {
                bird.y = windowHeight - groundHeight - bird.height;
            }
        }

        if(score > highscore) {
            highscore = score;
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.cyan);
        g.fillRect(0, 0, windowWidth, windowHeight);
        g.setColor(Color.green.darker());
        g.fillRect(0, windowHeight - groundHeight, windowWidth, groundHeight);
        g.setColor(Color.orange);
        g.fillRect(bird.x, bird.y, bird.width, bird.height);

        for(Rectangle pipe : pipes) {
            g.setColor(Color.green.darker());
            g.fillRect(pipe.x, pipe.y, pipe.width, pipe.height);
        }
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN , 50));

        if(!isStarted) {
            g.drawString("Press space to start", windowWidth / 4 + 70, windowHeight / 2 - 50);
        }
        if(gameOver) {
            g.drawString("Score: " + score + " Highscore: " + highscore, windowWidth / 4 + 30, windowHeight / 2 - 40);
            g.drawString("Press space to continue", windowWidth / 4 + 10, windowHeight / 2 + 10);
        }
        if(!gameOver && isStarted) {
            g.setFont(new Font("Arial", Font.PLAIN , 100));
            g.drawString(String.valueOf(score), windowWidth / 2 - 25,100);
        }
    }

    public void addPipe(boolean isFirstPipe) {
        int space = 300;
        int width = 100;
        int height = 50 + random.nextInt(300);

        if(pipes.size() < 3){
            for(int i = 0; i < 3; i++){
                pipes.add(new Rectangle(windowWidth + width + pipes.size() * 300, windowHeight - height - groundHeight, width, height)); //Apatinis
                pipes.add(new Rectangle(windowWidth + width + (pipes.size() - 1 ) * 300,0 , width, windowHeight - height - space)); //Apatinis
            }
        }
        else{
            pipes.add(new Rectangle(pipes.get(pipes.size() - 1).x + 600, windowHeight - height - groundHeight, width, height)); //Apatinis
            pipes.add(new Rectangle(pipes.get(pipes.size() - 1).x, 0, width, windowHeight - height - space)); //Apatinis
        }
    }

    public static void main(String[] args) {
        flappy = new Flappy();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_SPACE) {

            if(!isStarted) {
                isStarted = true;
                addPipe(true);
            }

            else if(!gameOver) {
                if(yMotion > 0) { yMotion = 0; }
                yMotion -= 10;
            }

            if(gameOver) {
                bird = new Rectangle(windowWidth / 2, windowHeight / 2, birdSize, birdSize);
                pipes.clear();
                score = 0;
                yMotion = 0;
                gameOver = false;
                isStarted = false;
            }
        }
    }
}
