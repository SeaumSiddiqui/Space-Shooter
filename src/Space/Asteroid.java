package Space;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Asteroid extends SpaceObjects{

    BufferedImage asteroid;
    BufferedImage[] hit;

    private int frameCount = 0;
    private int sprite = 1;

    private int deathCount;

    public Asteroid(int x, int y, int width, int height, int health, int damage, int speed) {

        super(x, y, width, height, health, damage, speed);

        // get random asteroid image form asteroidImg array to draw
        asteroid = getAsteroidImg();
    }

    public BufferedImage getAsteroidImg() {

        try {
            // asteroids
            asteroidImg1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/asteroid/asteroid1.png")));
            asteroidImg2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/asteroid/asteroid2.png")));
            asteroidImg3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/asteroid/asteroid3.png")));
            asteroidImg4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/asteroid/asteroid4.png")));
            asteroidImg5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/asteroid/asteroid5.png")));

            // hit animation
            hit1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/asteroid/hit/hit1.png")));
            hit2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/asteroid/hit/hit2.png")));
            hit3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/asteroid/hit/hit3.png")));
            hit4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/asteroid/hit/hit4.png")));
            hit5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/asteroid/hit/hit5.png")));
            hit6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/asteroid/hit/hit6.png")));
            hit7 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/asteroid/hit/hit7.png")));
            hit8 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/asteroid/hit/hit8.png")));

            // add hit image in an array
            hit = new BufferedImage[]{hit1, hit2, hit3, hit4, hit5, hit6, hit7, hit8};

            // add asteroid image in an array
            BufferedImage[] asteroidImg = {asteroidImg1, asteroidImg2, asteroidImg3, asteroidImg4, asteroidImg5};
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
            frameCount++;

            if (frameCount > 5) {
                frameCount = 0;
                sprite = (sprite % hit.length) + 1;
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

            int index = (sprite - 1);
            BufferedImage image = hit[index];
            g2D.drawImage(image, x, y, width, height, null);

            deathCount++;
            // frame count x num of image in hit array (5x8) = 40
            if (deathCount > 40) {
                isDead = true;
            }
        }
    }
}
