package main;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) throws Exception {

        // Copied all this shit; don't know how it works
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // prob make this true at some point
        window.setResizable(false);
        window.setTitle("Deterrence");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}
