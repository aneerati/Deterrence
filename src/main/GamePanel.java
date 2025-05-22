package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

    // Tile Defaults
    final int originalTileSize = 16; // 16x16
    final int scale = 3;
    final int tileSize = originalTileSize * scale;

    // Screen Defaults
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    // Default FPS
    int FPS = 60;

    Thread gameThread;
    KeyHandler keyH = new KeyHandler();

    // Player's Defaults
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        // time stuff for FPS
        double drawInterval = 1000000000 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        // Game loop does 2 things:
        // 1. Update info in game (e.g. player position)
        // 2. Draw screen with updated info
        while (gameThread != null) {
            update();
            repaint();

            try {
                // FPS Handling
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);

                nextDrawTime = nextDrawTime + drawInterval;
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }
    }

    public void update() {
        if (keyH.upPressed == true) {
            playerY = playerY - playerSpeed;
        } else if (keyH.downPressed == true) {
            playerY = playerY + playerSpeed;
        } else if (keyH.leftPressed == true) {
            playerX = playerX - playerSpeed;
        } else if (keyH.rightPressed == true) {
            playerX = playerX + playerSpeed;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.WHITE);

        // player character for now
        g2.fillRect(playerX, playerY, tileSize, tileSize);
        g2.dispose();
    }
}
