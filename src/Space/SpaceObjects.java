package Space;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpaceObjects {

    // default
    public int x, y;
    public int width, height = 64;
    double xVelocity, yVelocity;
    public int health;
    public int damage;
    public int speed;
    int radius = 64;
    boolean isDead = false; // remove the object from screen if false
    boolean isExploded = false; // show explosion animation if true

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
    BufferedImage bombUp, bombDown, bombLeft, bombRight, bombUpLeft,
            bombUpRight, bombDownLeft, bombDownRight,
            deadImg1, deadImg2, deadImg3;

    // UFO
    BufferedImage ufo1, ufo2;

    // asteroid
    BufferedImage asteroidImg1, asteroidImg2, asteroidImg3,
                  small1, small2, small3;

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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getXVelocity() {
        return xVelocity;
    }

    public void setXVelocity(int xVelocity) {
        this.xVelocity = xVelocity;
    }

    public double getYVelocity() {
        return yVelocity;
    }

    public void setYVelocity(int yVelocity) {
        this.yVelocity = yVelocity;
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

    public double getRadius() {
        //radius = Math.max(width, height) / 2;
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
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

    public boolean isExploded() {
        return isExploded;
    }

    public void setExploded(boolean exploded) {
        isExploded = exploded;
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
