package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed;
    public boolean downPressed;
    public boolean leftPressed;
    public boolean rightPressed;
    public boolean developerMode = false;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    // VS code tweaked without this
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        // TITLE STATE
        if (gp.gameState == GamePanel.TITLE_STATE) {
            if ((code == KeyEvent.VK_W) || (code == KeyEvent.VK_UP)) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                }
            }
            if ((code == KeyEvent.VK_S) || (code == KeyEvent.VK_DOWN)) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
            }
        }

        // PLAY STATE
        if (gp.gameState == GamePanel.PLAY_STATE) {
            if ((code == KeyEvent.VK_W) || (code == KeyEvent.VK_UP)) {
                upPressed = true;
            }
            if ((code == KeyEvent.VK_A) || (code == KeyEvent.VK_LEFT)) {
                leftPressed = true;
            }
            if ((code == KeyEvent.VK_S) || (code == KeyEvent.VK_DOWN)) {
                downPressed = true;
            }
            if ((code == KeyEvent.VK_D) || (code == KeyEvent.VK_RIGHT)) {
                rightPressed = true;
            }

            // PAUSE STATE
            if (code == KeyEvent.VK_ESCAPE) {
                if (gp.gameState == GamePanel.PLAY_STATE) {
                    gp.gameState = GamePanel.PAUSE_STATE;
                } else if (gp.gameState == GamePanel.PAUSE_STATE) {
                    gp.gameState = GamePanel.PLAY_STATE;
                }
            }

            // DEVELOPER MODE
            if (code == KeyEvent.VK_P) {
                developerMode = !developerMode;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if ((code == KeyEvent.VK_W) || (code == KeyEvent.VK_UP)) {
            upPressed = false;
        }
        if ((code == KeyEvent.VK_A) || (code == KeyEvent.VK_LEFT)) {
            leftPressed = false;
        }
        if ((code == KeyEvent.VK_S) || (code == KeyEvent.VK_DOWN)) {
            downPressed = false;
        }
        if ((code == KeyEvent.VK_D) || (code == KeyEvent.VK_RIGHT)) {
            rightPressed = false;
        }
    }

}
