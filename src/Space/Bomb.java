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
    BufferedImage[] explosion;

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
//            bombUp = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/fire/bombLeft/up.png")));
//            bombUpLeft = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/fire/bombLeft/uLeft.png")));
//
//            bombLeft = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/fire/bombLeft/left.png")));
//            bombDownLeft = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/fire/bombLeft/dLeft.png")));
//
//            bombDown= ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/fire/bombLeft/down.png")));
//            bombDownRight = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/fire/bombLeft/dRight.png")));
//
//            bombRight = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/fire/bombLeft/right.png")));
//            bombUpRight = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/fire/bombLeft/uRight.png")));
//
//            bombLeftImg = new BufferedImage[]{bombUp, bombDown, bombLeft, bombRight, bombUpLeft, bombUpRight, bombDownLeft, bombDownRight};




            bombLeft1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/fire/bombL/left1.png")));
            bombLeft2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/fire/bombL/left2.png")));

            bombLeftImg = new BufferedImage[]{bombLeft1, bombLeft2};

            // right rotation
            bombRight1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/fire/bombR/right1.png")));
            bombRight2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/fire/bombR/right2.png")));

            bombRightImg = new BufferedImage[]{bombRight1, bombRight2};


//            bombRightImg = new BufferedImage[]{bombUp, bombDown, bombLeft, bombRight, bombUpLeft, bombUpRight, bombDownLeft, bombDownRight};

            // explosion animation
            explosion1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/asteroid/hit/hit1.png")));
            explosion2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/asteroid/hit/hit2.png")));
            explosion3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/asteroid/hit/hit3.png")));
            explosion4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/asteroid/hit/hit4.png")));
            explosion5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/asteroid/hit/hit5.png")));
            explosion6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/asteroid/hit/hit6.png")));
            explosion7 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/asteroid/hit/hit7.png")));
            explosion8 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/asteroid/hit/hit8.png")));

            // add hit images to an array
            explosion = new BufferedImage[] {explosion1, explosion2, explosion3, explosion4, explosion5, explosion6, explosion7, explosion8};


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void move() {

        if (!isExploded) {

            double gravity = 0.6; // how fast the bomb will get down
            double timeStep = 0.3; // how fast the bomb will travel
            time += timeStep;

            // check if the x-velocity has already been set
            if (xVelocity == 0.0) {
                // set the initial x-velocity based on the UFO's direction
                if (ufo.getDirection().equals("left")) {
                    xVelocity = 10.0; // UFO is moving right, throw bomb to the right
                } else if (ufo.getDirection().equals("right")) {
                    xVelocity = -10.0; // UFO is moving left, throw bomb to the left
                }
            }

            // calculate the new position
            x = (int) (initialX + xVelocity * time);
            y = (int) (initialY + (yVelocity - 20.0) * time + 0.5 * gravity * time * time);

            yVelocity += gravity * timeStep;

            frameCounter++;
            if (frameCounter > 2) {
                frameCounter = 0;
                sprite = (sprite % bombImg.length) +1;
            }
        }
        else {
            explosionCounter++;
            if (explosionCounter > 5) {
                explosionCounter = 0;
                explosionSprite = (explosionSprite % explosion.length) + 1;
            }
        }
    }

    public void checkCollision() {

        if (x <= -width) {
            isDead = true;
        }
        if (x >= ufo.game.screenWidth + width) {
            isDead = true;
        }
        if (y >= ufo.game.screenHeight - height) {
            isExploded = true;
        }
    }

    public void draw(Graphics2D g2D) {

        BufferedImage image = null;

        if (!isExploded) {

            int index = (sprite - 1);
            image = bombImg[index];



        } else {

            int index = (explosionSprite - 1);
            image = explosion[index];

            deathCount++;
            // highest frame count x4;
            if (deathCount >= 40){
                isDead = true;
            }
        }
        g2D.drawImage(image, x, y, width, height, null);
    }
}
