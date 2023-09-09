package Space;

import main.GameFrame;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import javax.imageio.ImageIO;

public class Spaceship extends SpaceObjects {

    GameFrame game;
    KeyHandler keyH;
    public String rocketType;

    public Spaceship(int x, int y, int width, int height, int health, int damage, int speed, KeyHandler keyH, GameFrame game) {

        super(x, y, width, height, health, damage, speed);

        this.keyH = keyH;
        this.game = game;

        keyH.playerDirection = "default";

        getSpaceshipImg();
    }

    private void getSpaceshipImg() {

        try{

            playerUp = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player sprites/up.png")));
            playerDown = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player sprites/down.png")));
            playerLeft = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player sprites/static.png")));
            playerRight = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player sprites/static.png")));

            playerDefault = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player sprites/static.png")));

        } catch (Exception e) {
            System.out.println("Error:" + e);
        }
    }

    public void move () {

        if (!isDead) {

            if (keyH.upPressed) {
                y -= speed;
            }
            if (keyH.downPressed) {
                y += speed;
            }
            if (keyH.leftPressed) {
                x -= speed;
            }
            if (keyH.rightPressed) {
                x += speed;
            }
        }
    }

    public void checkCollision() {

        // stop spaceship at the top and bottom screen
        if (y <= 0) {
            y = 0;
        }
        if (y >= game.screenHeight - height) {
            y = game.screenHeight - height;
        }
        if (x <= 0) {
            x = 0;
        }
        if (x >= game.screenWidth - width) {
            x = game.screenWidth - width;
        }
    }

    public void fire() {

        game.getRockets().add(new Rocket(x, y + (game.tileSize / 2), game.tileSize / 4, game.tileSize / 6,0, 111, 8, this));

    }


    public void drawSpaceship(Graphics g2D) {

        BufferedImage image = null;

        if (!isExploded) {

            switch (keyH.playerDirection) {

                case "up" -> image = playerUp;
                case "down" -> image = playerDown;
                case "left" -> image = playerLeft;
                case "right" -> image = playerRight;
                case "default" -> image = playerDefault;
            }

            g2D.drawImage(image, x, y, width, height, null);
        }
    }
}
