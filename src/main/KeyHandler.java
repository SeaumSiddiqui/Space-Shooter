package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public String playerDirection;

    boolean rocketFireX = false;
    boolean rocketFiredX = false;

    boolean rocketFireY = false;
    boolean yOnCooldown = false;
    long lastYFireTime = 0;
    long cooldownDuration;

    public void setCooldownDuration(long duration) {
        cooldownDuration = duration;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // do nothing
    }

    @Override
    public void keyPressed(KeyEvent e) {

        // spacecraft
        if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
            playerDirection = "up";
            upPressed = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
            playerDirection = "down";
            downPressed = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
            playerDirection = "left";
            leftPressed = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            playerDirection = "right";
            rightPressed = true;
        }


        // shoot X
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !rocketFireX) {
            rocketFireX = true;
            rocketFiredX = true;
        }
        // shoot Y
        if (e.getKeyCode() == KeyEvent.VK_J) {
            rocketFireY = true;
            lastYFireTime = System.currentTimeMillis();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        // spacecraft
        if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
            playerDirection = "default";
            upPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
            playerDirection = "default";
            downPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
            playerDirection = "default";
            leftPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            playerDirection = "default";
            rightPressed = false;
        }


        // release X
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            rocketFireX = false;
        }
        // release Y
        if (e.getKeyCode() == KeyEvent.VK_J) {
            rocketFireY = false;
        }
    }

    public void resetRocketX() {
        rocketFiredX = false;
    }
}
