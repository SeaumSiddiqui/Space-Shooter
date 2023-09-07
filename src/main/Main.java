package main;

import javax.swing.*;

public class Main {

    static GameFrame gamePanel;
    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setTitle("Space Shooter");
        window.setResizable(false);

        gamePanel = new GameFrame();
        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGame();
    }
}