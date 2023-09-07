package Space;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Asteroid extends SpaceObjects{

    BufferedImage asteroid;

    private int deathCount;

    public Asteroid(int x, int y, int width, int height, int health, int damage, int speed) {

        super(x, y, width, height, health, damage, speed);

        asteroid = getAsteroidImg();
    }


    public BufferedImage getAsteroidImg() {

        try {

            asteroidImg1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/asteroid/asteroid1.png")));
            asteroidImg2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/asteroid/asteroid2.png")));
            asteroidImg3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/asteroid/asteroid3.png")));

            small2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/asteroid/small-2.png")));

            BufferedImage[] asteroidImg = {asteroidImg1, asteroidImg2, asteroidImg3};
            Random random = new Random();
            return asteroidImg[random.nextInt(asteroidImg.length)];

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void move() {

        if (!isExploded) {
            x -= speed;
        } else {
            deathCount++;
            if (deathCount > 30) {
                isDead = true;
            }
        }
    }

    public void checkCollision() {

        if (x <= -width) {
            isDead = true;
        }
    }


    public void draw(Graphics2D g2D) {

        if (!isExploded) {

            BufferedImage image = asteroid;
            g2D.drawImage(image, x, y, width, height, null);
        } else {

            BufferedImage image = small2;
            g2D.drawImage(image, x, y, width / 2, height / 2, null);
        }
    }

}
