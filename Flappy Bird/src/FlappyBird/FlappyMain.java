package FlappyBird;

import javax.swing.*;


public class FlappyMain extends JPanel {

    public static FlappyMain flappyMain;
    private int windowWidth = 1200, windowHeight = 800;
    private Renderer renderer;

    public FlappyMain()
    {

        JFrame jFrame = new JFrame();
        jFrame.setSize(windowWidth, windowHeight);

        renderer = new Renderer(windowWidth, windowHeight);
        GameLoop gameLoop = new GameLoop(windowWidth, windowHeight,renderer);

        jFrame.add(renderer);
        jFrame.addKeyListener(gameLoop);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setTitle("Flappy Rectangle");
        jFrame.setResizable(true);

        Timer timer = new Timer(20, gameLoop);
        timer.start();
    }


    public static void main(String[] args)
    {
        flappyMain = new FlappyMain();
    }


}
