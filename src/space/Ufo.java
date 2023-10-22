package space;

import main.GameFrame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Ufo extends SpaceObjects{

    GameFrame game;
    Random random;
    BufferedImage[] spark;
    private Timer bombDropTimer;

    private int frameCount = 0;
    private int sprite = 1;
    private int deathCount;

    public Ufo(int x, int y, int width, int height, int health, int damage, int speed, GameFrame game) {

        super(x, y, width, height, health, damage, speed);

        this.game = game;

        random = new Random();

        // ufo x direction
        int randomXDirection = random.nextInt(2);

        if (randomXDirection == 0)
            randomXDirection--;
        setXDirection(randomXDirection * speed);

        // ufo y direction
        int randomYDirection = random.nextInt(2);

        if (randomYDirection == 0)
            randomYDirection--;
        setYDirection(randomYDirection * speed);

        getBomb();
        getUfoImg();
    }

    public void getUfoImg() {

        try{
            // UFO
            ufo = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ufo/ufo.png")));

            // hit animation
            spark1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ufo/spark/spark1.png")));
            spark2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ufo/spark/spark2.png")));
            spark3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ufo/spark/spark3.png")));
            spark4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ufo/spark/spark4.png")));

            // add hit images to an array
            spark = new BufferedImage[]{spark1, spark2, spark3, spark4};



        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getDirection() {

        if (xVelocity < 0) {
            return "left";
        }
        if (xVelocity > 0) {
            return "right";
        }
        return null;
    }

    private void setXDirection(int randomXDirection) {
        xVelocity = randomXDirection;
    }

    private void setYDirection(int randomYDirection) {
        yVelocity = randomYDirection;
    }


    public void move() {

        if (!isExploded) {

            x += (int) xVelocity;
            y += (int) yVelocity;
        } else {
            frameCount++;

            if (frameCount >= 3) {
                frameCount = 0;
                sprite = (sprite % spark.length) + 1;
            }
        }
    }

    public void getBomb() {

        if (!isDead && game.gameState == game.play) {

            // drop bomb in every 10 second
            bombDropTimer = new Timer();
            bombDropTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    dropBomb();
                }
            }, 0, 10000); // 10,000 milliseconds = 10 seconds
        }
    }

    public void cancelBombTimer() {
        if (bombDropTimer != null) {
            bombDropTimer.cancel();
            bombDropTimer.purge();
        }
    }

    public void checkCollision() {

        // bounce UFO off top and bottom edges
        if (y <= 0 || y >= game.screenHeight - width){
            setYDirection((int) -yVelocity);
        }
        // bounce UFO off left and right edges
        if (x <= 0 || x >= game.screenWidth - height){
            setXDirection((int) -xVelocity);
        }
    }

    public void dropBomb() {

        if (game.gameState == game.play && !isDead) {
            game.getBombs().add(new Bomb(getX(), getY(), width, height, 111, 333, 0, this));
        }
    }

    public void draw(Graphics2D g2D) {

        BufferedImage image;

        if (!isExploded) {

            image = ufo;
        } else {

            //game.playSE(2);

            int index = (sprite - 1);
            image = spark[index];
            deathCount++;

            if (deathCount >= 12) {
                cancelBombTimer();
                isDead = true;
            }
        }

        g2D.drawImage(image, x, y, width, height / 2, null);
    }
}
