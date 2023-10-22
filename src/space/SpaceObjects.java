package space;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpaceObjects {

    // default
    public int x, y;
    public int width, height;
    double xVelocity, yVelocity;
    public int health;
    public int damage;
    public int speed;
    int radius = 64;
    boolean isDead = false; // remove the object from game if true
    boolean isExploded = false; // remove the object & show explosion animation if true

    public SpaceObjects(int x, int y, int width, int height, int health,  int damage, int speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.health = health;
        this.damage = damage;
        this.speed = speed;
    }

    // spaceship
    BufferedImage playerUp, playerDown, playerLeft, playerRight, playerDefault;

    // rocket
    BufferedImage rocketX1, rocketX2;
    BufferedImage rocketY1, rocketY2, rocketY3, rocketY4, rocketY5, rocketY6;

    // bomb
    BufferedImage explosion1, explosion2, explosion3, explosion4,
                  explosion5, explosion6, explosion7, explosion8,
                  bombLeft1, bombLeft2, bombRight1, bombRight2  ;


    // UFO
    BufferedImage ufo;
    BufferedImage spark1, spark2, spark3, spark4;

    // asteroid
    BufferedImage asteroidImg1, asteroidImg2, asteroidImg3, asteroidImg4, 
                  asteroidImg5, asteroidImg6, asteroidImg7, asteroidImg8,
                  hit1, hit2, hit3, hit4, hit5, hit6, hit7, hit8;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void takeDamage(int damage) {

        health -= damage;

        if (health <= 0) {
            isExploded = true;
        }

    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public void move() {
        // nothing here
    }

    public void draw(Graphics2D g2D) {
        // nothing here
    }

    public boolean intersects(SpaceObjects o) {

        double dx = x - o.x;
        double dy = y - o.y;

        double destination = Math.sqrt(dx * dx + dy * dy);
        double destination2 = Math.max(radius, o.radius);

        return destination <= destination2;
    }
}
