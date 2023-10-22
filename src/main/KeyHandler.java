package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GameFrame game;
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public String playerDirection;

    public boolean rocketFireX = false;
    public boolean rocketFiredX = false;

    public boolean rocketFireY = false;
    public boolean yOnCooldown = false;

    public long lastYFireTime = 0;
    public long cooldownDuration;

    public int commandNum = 0;

    public KeyHandler(GameFrame game) {

        this.game = game;
    }

    public void setCooldownDuration(long duration) {
        cooldownDuration = duration;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // do nothing
    }

    @Override
    public void keyPressed(KeyEvent e) {

        // title state
        if (game.gameState == game.titleState) {

            // move the cursor around
            if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {

                game.playSE(5);
                commandNum--;
                if (commandNum < 0) {
                    commandNum = 2;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {

                game.playSE(5);
                commandNum++;
                if (commandNum > 2) {
                    commandNum = 0;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (commandNum == 0) {
                    game.playSE(7);
                    game.playMusic(0);
                    game.gameState = game.play;
                }
                if (commandNum == 1) {
                    game.playSE(7);
                }
                if (commandNum == 2) {
                    game.playSE(7);
                    System.exit(0);
                }
            }
        }

        // option state
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

            if (game.gameState == game.play) {
                game.playSE(7);
                game.gameState = game.option;
            }
            else if (game.gameState == game.option) {
                game.playSE(7);
                game.gameState = game.play;
            }
        }

        if (game.gameState == game.option) {
            // move the cursor around
            if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {

                game.playSE(5);
                commandNum--;
                if (commandNum < 0) {
                    commandNum = 3;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {

                game.playSE(5);
                commandNum++;
                if (commandNum > 3) {
                    commandNum = 0;
                }
            }

            // increase volume
            if (e.getKeyCode() == KeyEvent.VK_PAGE_UP) {
                // music volume
                if (commandNum == 0 && game.music.volumeScale < 10) {
                    game.playSE(6);
                    game.music.volumeScale++;
                    game.music.checkVolume();
                }// effect volume
                if (commandNum == 1 && game.effect.volumeScale < 10) {
                    game.playSE(6);
                    game.effect.volumeScale++;
                }
            }

            // decrease volume
            if (e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
                // music volume
                if (commandNum == 0 && game.music.volumeScale > 0) {
                    game.playSE(6);
                    game.music.volumeScale--;
                    game.music.checkVolume();
                }// effect volume
                if (commandNum == 1 && game.effect.volumeScale > 0) {
                    game.playSE(6);
                    game.effect.volumeScale--;
                }
            }


            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                // main menu
                if (commandNum == 2) {
                    game.playSE(7);
                    game.music.stop();
                    game.gameState = game.titleState;
                }// quit game
                if (commandNum == 3) {
                    game.playSE(7);
                    System.exit(0);
                }
            }
        }

        // pause state
        if (e.getKeyCode() == KeyEvent.VK_P) {

            if (game.gameState == game.play) {
                game.playSE(7);
                game.stopMusic();
                game.gameState = game.pause;
            }
            else if (game.gameState == game.pause) {
                game.playSE(7);
                game.playMusic(0);
                game.gameState = game.play;
            }
        }

        // game over state
        if (game.gameState == game.gameOver) {

            game.stopMusic();
            // restart game
            if (e.getKeyCode() == KeyEvent.VK_R) {
                game.playSE(7);
                game.restart();
                game.gameState = game.play;
            }
        }

        // spacecraft control
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
