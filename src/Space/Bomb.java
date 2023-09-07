package Space;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Bomb extends SpaceObjects {

    Ufo ufo;
    BufferedImage[] bombImg;
    BufferedImage[] bombLeftImg, bombRightImg;
    BufferedImage[] bombDead;

    private int sprite = 1;
    private int frameCounter = 0;

    private int explosionSprite = 1;
    private int explosionCounter = 0;

    private double time = 0;
    private int deathCount = 0;

    double initialX;
    double initialY;

    public Bomb(int x, int y, int width, int height, int health, int damage, int speed, Ufo ufo) {

        super(x, y, width, height, health, damage, speed);

        this.ufo = ufo;
        this.initialX = x;
        this.initialY = y;

        // initialize image array before setting the direction to avoid null
        getBombImg();

        // set bombImg based on UFO direction
        switch (ufo.getDirection()) {

            case "left" -> bombImg = bombRightImg;
            case "right" -> bombImg = bombLeftImg;
        }
    }

    public void getBombImg() {

        try{

            // left rotation
            bombUp = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/fire/bombLeft/up.png")));
            bombUpLeft = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/fire/bombLeft/uLeft.png")));

            bombLeft = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/fire/bombLeft/left.png")));
            bombDownLeft = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/fire/bombLeft/dLeft.png")));

            bombDown= ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/fire/bombLeft/down.png")));
            bombDownRight = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/fire/bombLeft/dRight.png")));

            bombRight = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/fire/bombLeft/right.png")));
            bombUpRight = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/fire/bombLeft/uRight.png")));


            bombLeftImg = new BufferedImage[]{bombUp, bombLeft, bombLeft, bombDownLeft, bombDown, bombDownRight, bombRight, bombUpRight};

            // right rotation


            bombRightImg = new BufferedImage[]{bombUp, bombLeft, bombLeft, bombDownLeft, bombDown, bombDownRight, bombRight, bombUpRight};

            // screen collision
            deadImg1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/fire/explode.png")));
            deadImg2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/fire/explode1.png")));
            //deadImg3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/bullets/explode.png")));



            bombDead = new BufferedImage[] {deadImg1, deadImg2, deadImg3};


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void move() {

        if (!isExploded) {

            double gravity = 0.1;
            if (ufo.getDirection().equals("left")) {

                // calculate new position
                double timeStep = 0.1;
                time += timeStep;

                x = (int) (initialX + (xVelocity + 10.0) * time);
                y = (int) (initialY + (yVelocity - 10.0) * time + 0.5 * gravity * time * time);

                yVelocity += gravity * timeStep;
            }
            else if (ufo.getDirection().equals("right")) {

                // calculate new position
                double timeStep = 0.1;
                time += timeStep;

                x = (int) (initialX + (xVelocity - 5.0) * time);
                y = (int) (initialY + (yVelocity - 10.0) * time + 0.5 * gravity * time * time);

                yVelocity += gravity * timeStep;
            }


            frameCounter++;
            if (frameCounter > 1) {
                frameCounter = 0;
                sprite = (sprite % bombImg.length) +1;
            }
        }
        else {
            explosionCounter++;
            if (explosionCounter > 5) {
                explosionCounter = 0;
                explosionSprite = (explosionSprite % bombImg.length) + 1;
            }
        }
    }

    public void checkCollision() {

        if (x <= 0) {
            isDead = true;
        }
        if (x >= ufo.game.screenWidth) {
            isDead = true;
        }
    }

    public void draw(Graphics2D g2D) {

        BufferedImage image = null;

        if (!isExploded) {

            int index = (sprite - 1);
            image = bombImg[index];

            g2D.drawImage(image, x, y, width, height, null);

        } else {

            deathCount++;
            bombImg = bombDead;
            int index = (explosionSprite - 1);
            image = bombImg[index];

            g2D.drawImage(image, x, y, width, height, null);

            // highest frame count x4;
            if (deathCount >= 20){
                isDead = true;
            }
        }
    }
}
