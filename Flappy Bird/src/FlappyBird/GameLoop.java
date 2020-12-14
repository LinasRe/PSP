package FlappyBird;


import java.awt.event.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class GameLoop implements ActionListener, KeyListener {

    private int score = 0, highscore = 0, windowWidth, windowHeight, groundHeight = 150;;
    private Random random;
    private boolean gameOver, isStarted;
    private ArrayList<Rectangle> pipes;
    private Bird bird;
    private Renderer renderer;


    GameLoop(int winWidth, int winHeight, Renderer renderer) {
        pipes = new ArrayList<>();
        bird = new Bird(winWidth, winHeight);
        random = new Random();
        this.windowWidth = winWidth;
        this.windowHeight = winHeight;
        this.renderer = renderer;

    }

    public void actionPerformed(ActionEvent e) {
        if (isStarted) {
            movePipes();
            accelerateBirdsY();
            checkGroundCollision();
            checkForPipes();

            for (Rectangle pipe : pipes) {
                scoreAdding(pipe);
                pipeCollision(pipe);
            }
        }
        updateHighscore();
        renderer.updateRendererValues(groundHeight, score, highscore, pipes, bird, isStarted, gameOver);
        renderer.repaint();
    }

    private void movePipes() {
        int pipeSpeed = 10;
        for (Rectangle pipe : pipes) {
            pipe.x -= pipeSpeed;
        }
    }

    private void accelerateBirdsY() {

        int maxFallingSpeed = 15;
        if (bird.getYMotion() < maxFallingSpeed) {
            bird.addToYMotion(1);
        }
        bird.moveYCoord();
    }

    private void checkGroundCollision() {
        if (bird.getY() > windowHeight - groundHeight - bird.getHeight() || bird.getY() < 0) {
            gameOver = true;
        }
        if (bird.getY() + bird.getYMotion() >= windowHeight - groundHeight) {
            bird.setY(windowHeight - groundHeight - bird.height);
        }
    }

    private void checkForPipes(){
        for (int i = 0; i < pipes.size(); i++) {
            if (pipes.get(i).x + pipes.get(i).width < 0) {
                pipes.remove(pipes.get(i));
                if (pipes.get(i).y == 0) {
                    addPipe();
                }
            }
        }
    }

    private void scoreAdding(Rectangle pipe) {

        if (pipe.x + pipe.width == bird.getX() && pipe.y < bird.getY() && !gameOver) {
            score++;
        }
    }

    private void pipeCollision(Rectangle pipe){
        if (pipe.intersects(bird)) {
            gameOver = true;
            if (bird.getX() <= pipe.x) {
                bird.setX((int) (pipe.x - bird.getWidth()));
            } else {
                if (pipe.y != 0) {
                    bird.setY((int) pipe.getY() - bird.height);
                } else if (bird.getY() < pipe.height) {
                    bird.setY(pipe.height);
                }
            }
        }
    }

    private void updateHighscore() {
        if(score > highscore) {
            highscore = score;
        }
    }

    private void addPipe() {
        int space = 300;
        int width = 100;

        if(pipes.size() < 3){
            for(int i = 0; i < 3; i++){
                int height = 50 + random.nextInt(space);
                pipes.add(new Rectangle(windowWidth + width + pipes.size() * 300, windowHeight - height - groundHeight, width, height)); //Apatinis
                pipes.add(new Rectangle(windowWidth + width + (pipes.size() - 1 ) * 300,0 , width, windowHeight - height - space)); //Viršutinis
            }
        }
        else{
            int height = 50 + random.nextInt(space);
            pipes.add(new Rectangle(pipes.get(pipes.size() - 1).x + 600, windowHeight - height - groundHeight, width, height)); //Apatinis
            pipes.add(new Rectangle(pipes.get(pipes.size() - 1).x, 0, width, windowHeight - height - space)); //Viršutinis
        }
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
                addPipe();
            }

            else if(!gameOver) {
                if(bird.getYMotion() > 0) { bird.setYMotion(0); }
                bird.setYMotion(bird.getYMotion() - 10);
            }

            if(gameOver) {
                bird = new Bird(windowWidth, windowHeight);
                pipes.clear();
                score = 0;
                bird.setYMotion(0);
                gameOver = false;
                isStarted = false;
            }
        }
    }
}
