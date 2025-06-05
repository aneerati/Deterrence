package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

    // Tile Defaults
    final int originalTileSize = 16; // 16x16
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;

    // Screen Defaults
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    // DEVELOPER MODE
    public long drawStart = 0;

    // Default FPS
    int FPS = 60;

    // GAME STATE
    public int gameState;
    public static final int TITLE_STATE = 0;
    public static final int PLAY_STATE = 1;
    public static final int PAUSE_STATE = 2;
    public static final int WIN_STATE = 3;
    public static final int DIALOGUE_STATE = 4;

    // SYSTEM CLASSES
    KeyHandler keyH = new KeyHandler(this);
    TileManager tileM = new TileManager(this);
    public AssetSetter aSetter = new AssetSetter(this);
    Sound music = new Sound();
    Sound soundEffect = new Sound();
    public UI ui = new UI(this);
    Thread gameThread;

    // ENTITY and OBJECT CLASSES
    public Player player = new Player(this, keyH);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public SuperObject obj[] = new SuperObject[10];
    public Entity npc[] = new Entity[10];

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setObject();
        aSetter.setNPC();
        // playMusic(0);
        gameState = TITLE_STATE;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        // time stuff for FPS
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime = 0;

        // Game loop does 2 things:
        // 1. Update info in game (e.g. player position)
        // 2. Draw screen with updated info
        while (gameThread != null) {

            // Game Loop with FPS handling
            currentTime = System.nanoTime();
            delta = delta + (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }

        }
    }

    // Calls update methods of other classes
    public void update() {
        if (gameState == PLAY_STATE) {
            player.update();
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].update();
                }
            }
        }

        if (gameState == PAUSE_STATE) {

        }

    }

    // Calls draw methods of other classes
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (gameState == TITLE_STATE) {
            ui.draw(g2);
        } else {
            // DEVELOPER MODE
            if (keyH.developerMode == true) {
                drawStart = System.nanoTime();
            }

            // TILE DRAWING
            tileM.draw(g2);

            // OBJECT DRAWING
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    obj[i].draw(g2, this);
                }
            }

            // NPC
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].draw(g2);
                }
            }

            // PLAYER DRAWING
            player.draw(g2);

            // UI DRAWING
            ui.draw(g2);

            if (keyH.developerMode == true) {
                long passed = System.nanoTime() - drawStart;
                g2.setColor(Color.WHITE);
                g2.drawString("Render Time: " + passed, 10, 400);
            }

            g2.dispose();
        }
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSoundEffect(int i) {
        soundEffect.setFile(i);
        soundEffect.play();
    }
}
