package Space;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Rocket extends SpaceObjects {

    Spaceship spaceship;
    BufferedImage[] rocketY;
    private int frameCount = 0;
    private int sprite = 1;

    public Rocket(int x, int y, int width, int height, int health, int damage, int rocketSpeed, Spaceship spaceship) {

        super(x, y, width, height, health, damage, rocketSpeed);

        this.spaceship = spaceship;

        getRocketImg();

    }

    public void getRocketImg() {

        try{

            // rocket 1
            rocketX1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/fire/rocket1/shoot1.png")));
            rocketX2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/fire/rocket1/shoot2.png")));

            // rocket 2
            rocketY1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/fire/rocket2/charged1.png")));
            rocketY2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/fire/rocket2/charged2.png")));
            rocketY3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/fire/rocket2/charged3.png")));
            rocketY4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/fire/rocket2/charged4.png")));
            rocketY5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/fire/rocket2/charged5.png")));
            rocketY6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/fire/rocket2/charged6.png")));

            rocketY = new BufferedImage[]{rocketY1, rocketY2, rocketY3, rocketY4, rocketY5};

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void move() {

        if(!isDead) {

            x += speed;
            y++;
            // normal rocket
            if (spaceship.rocketType.equals("x")) {

                frameCount++;

                if (frameCount > 10) {

                    if (sprite == 1) {
                        sprite = 2;
                    } else if (sprite == 2) {
                        sprite = 1;
                    }
                    frameCount = 0;
                }
            } else {
                // ultimate rocket
                damage = 333;
                frameCount++;

                if (frameCount > 10) {
                    frameCount = 0;
                    sprite = (sprite % rocketY.length) + 1;
                }
            }
        }
    }

    public void checkCollision() {
        // check for right side of the screen only
        if (x >= spaceship.game.screenWidth + width) {
            isDead = true;
        }
    }

    public void draw(Graphics2D g2D) {

        BufferedImage image = null;

        if (!isDead) {
            switch (spaceship.rocketType) {

                case "x" -> {
                    if (sprite == 1) {
                        image = rocketX1;
                    }
                    if (sprite == 2) {
                        image = rocketX2;
                    }
                }
                case "y" -> {
                    int index = (sprite - 1);
                    image = rocketY[index];
                }
            }

            g2D.drawImage(image, x, y, width, height, null);
        }
    }
}