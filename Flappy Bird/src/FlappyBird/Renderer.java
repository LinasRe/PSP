package FlappyBird;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Renderer extends JPanel {

    private int windowWidth, windowHeight, groundHeight, score, highscore;
    private ArrayList<Rectangle> pipes;
    private Bird bird;
    private boolean isStarted, gameOver;

    Renderer(int winWidth, int winHeight)
    {
        this.windowWidth = winWidth;
        this.windowHeight = winHeight;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        paintBackground(g);
        paintPipes(g);
        setTextFontColor(g);
        paintStartText(g);
        paintGameOverText(g);
        paintScore(g);

    }
    private void paintBackground(Graphics g){
        g.setColor(Color.cyan);
        g.fillRect(0, 0, windowWidth, windowHeight);
        g.setColor(Color.green.darker());
        g.fillRect(0, windowHeight - groundHeight, windowWidth, groundHeight);
        g.setColor(Color.orange);
        g.fillRect((int)bird.getX(), (int)bird.getY(), (int)bird.getWidth(), (int)bird.getHeight());
    }

    private void paintPipes(Graphics g){
        for(Rectangle pipe : pipes) {
            g.setColor(Color.green.darker());
            g.fillRect(pipe.x, pipe.y, pipe.width, pipe.height);
        }
    }
    private void setTextFontColor(Graphics g){
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN , 50));
    }
    private void paintStartText(Graphics g){
        if(!isStarted) {
            g.drawString("Press space to start", windowWidth / 4 + 70, windowHeight / 2 - 50);
        }
    }
    private void paintGameOverText(Graphics g){
        if(gameOver) {
            g.drawString("Score: " + score + " Highscore: " + highscore, windowWidth / 4 + 30, windowHeight / 2 - 40);
            g.drawString("Press space to continue", windowWidth / 4 + 10, windowHeight / 2 + 10);
        }
    }
    private void paintScore(Graphics g){
        if(!gameOver && isStarted) {
            g.setFont(new Font("Arial", Font.PLAIN , 100));
            g.drawString(String.valueOf(score), windowWidth / 2 - 25,100);
        }
    }
    
    public void updateRendererValues(int groundHeight, int score, int highscore, ArrayList<Rectangle>pipes, Bird bird, Boolean isStarted, Boolean gameOver)
    {
        this.groundHeight = groundHeight;
        this.score = score;
        this.highscore = highscore;
        this.pipes = pipes;
        this.bird = bird;
        this.isStarted = isStarted;
        this.gameOver = gameOver;
    }

}
