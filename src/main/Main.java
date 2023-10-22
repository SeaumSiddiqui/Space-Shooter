package main;

import javax.swing.*;

public class Main {

    public static JFrame window;
    public static void main(String[] args) {

        window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setUndecorated(true);
        window.setTitle("Space Shooter");


        GameFrame gamePanel = new GameFrame();
        window.add(gamePanel);
        gamePanel.config.loadConfig();
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGame();
    }
}