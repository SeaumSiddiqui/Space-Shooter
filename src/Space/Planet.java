package Space;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Planet extends SpaceObjects{

    BufferedImage planet1, planet2;

    public Planet(int x, int y, int width, int height, int health, int damage, int speed) {

        super(x, y, width, height, health, damage, speed);

        getPlanetImg();
    }

    public void getPlanetImg() {
        try{
            
            planet1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/space/planet1.png")));
            planet2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/space/planet2.png")));
            
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void move() {

        x --;
    }

    public void checkCollision() {

        if (x <= -width) {
            isDead = true;
        }
    }
    
    public void draw(Graphics2D g2D) {

        if (!isDead) {

            g2D.drawImage(planet1, x, y, width, height, null);

        } else {

            g2D.drawImage(planet2, x, y, width, height, null);

        }
    }

}
