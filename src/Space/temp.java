/*package main;

import Space.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class GameFrame extends JPanel implements Runnable {

    // Screen settings
    private final int initTileSize = 16; //16x16 tile
    private final int scale = 4;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 10;
    public final int tileSize = initTileSize * scale; //64x64 tile
    public final int screenWidth = tileSize* maxScreenCol; //1024px
    public final int screenHeight = tileSize * maxScreenRow; //640px
    public final int FPS = 60; //60 frames per second

    BufferedImage backgroundImg;

    // initialize KeyHandler
    KeyHandler keyH = new KeyHandler();

    // spaceship
    public Spaceship ship = new Spaceship(0, screenHeight / 2 - tileSize,
            tileSize * 2, tileSize * 2, 999, 999, 10, keyH, this);

    // UFO
    public final List<Ufo> ufo = new ArrayList<Ufo>();

    // asteroids
    public final List<Asteroid> asteroids = new ArrayList<Asteroid>();

    // rocket
    public final List<Rocket> rockets = new ArrayList<Rocket>();

    // bomb
    public final List<Bomb> bombs = new ArrayList<Bomb>();


    Thread gameThread;

    GameFrame() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        try {

            backgroundImg = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/space/download.png")));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void startGame() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    public void asteroidSpawn(){

        Random randomAsteroid = new Random();
        int random100 = randomAsteroid.nextInt(250);

        if (random100 == 0) {
            asteroids.add(new Asteroid(screenWidth, randomAsteroid.nextInt(screenHeight - tileSize), tileSize, tileSize, 333, 333, 1));
        }
    }

    // change the spawn direction to Y
    // TO-DO
    public void ufoSpawn() {

        if (ufo.size() > 0) return;

        Random randomUfo = new Random();
        double dropProbability = 0.0050;
        // 0.50% chance of spawn in each iteration
        // 5 spawn in every 1000 iteration
        if (randomUfo.nextDouble() < dropProbability) {

            ufo.add(new Ufo(screenWidth - tileSize * 2, (int)(Math.random() * screenHeight/2),tileSize, tileSize, 555, 333, 2, this));
            ufo.add(new Ufo(screenWidth - tileSize * 2, (int)(Math.random() * screenHeight/2 + tileSize),tileSize, tileSize, 555, 333, 2, this));
            ufo.add(new Ufo(screenWidth - tileSize * 2, (int)(Math.random() * screenHeight/2 + (tileSize * 4)),tileSize, tileSize, 555, 333, 2, this));
        }
    }

    public void rocketSpawn() {

        // rocket X
        if (keyH.rocketFireX && keyH.rocketFiredX) {
            ship.fire();
            keyH.resetRocketX();
        }

        // rocket Y
        keyH.setCooldownDuration(1000);
        long currentTime = System.currentTimeMillis();

        if (keyH.rocketFireY) {
            // check if rocketY is not on cooldown or enough time has passed
            if (!keyH.yOnCooldown) {
                ship.fire();
                keyH.yOnCooldown = true;
                keyH.lastYFireTime = currentTime;
            }
            // Optional: add an else condition to provide feedback that rocketY is on cooldown.
            // will add cooldown animation later
        }
        // reset cooldown flag
        if (keyH.yOnCooldown && currentTime - keyH.lastYFireTime >= keyH.cooldownDuration) {
            keyH.yOnCooldown = false;
        }
    }

    public void checkRockets() {

        for (Rocket rocket: rockets) {
            // check collision between rocket and asteroid
            for (Asteroid asteroid: asteroids) {

                if (asteroid.intersects(rocket)) {
                    asteroid.takeDamage(rocket.getDamage());
                    rocket.takeDamage(asteroid.getDamage());
                }
            }
            // check collision between rocket and UFO
            for (Ufo ufo: ufo) {

                if (ufo.intersects(rocket)) {
                    ufo.takeDamage(rocket.getDamage());
                    rocket.takeDamage(ufo.getDamage());
                }
            }
            // check collision between rocket and bomb
            for (Bomb bomb: bombs) {

                if (bomb.intersects(rocket)) {
                    bomb.takeDamage(rocket.getDamage());
                    rocket.takeDamage(bomb.getDamage());
                }
            }
        }
    }

    public void checkBomb() {
        // check collision between bombs and the spaceship
        for (Bomb bomb: bombs) {

            if (ship.intersects(bomb)) {
                ship.takeDamage(bomb.getDamage());
                bomb.takeDamage(ship.getDamage());
            }
        }
    }

    public void update() {

        ship.move(); // update spaceship
        updateAsteroid(); // update asteroid
        updateRocket(); // update rocket
        updateUfo(); // update UFO
        updateBomb(); // update bomb
    }

    public void updateAsteroid() {

        Iterator<Asteroid> asteroidIterator = asteroids.iterator();

        while (asteroidIterator.hasNext()) {
            Asteroid asteroid = asteroidIterator.next();
            // update asteroid
            asteroid.move();
            // remove dead asteroid
            // remove asteroid after leaving the screen
            if (asteroid.getX() <= -tileSize){
                asteroidIterator.remove();
            }
            if (asteroid.isDead()) {
                asteroidIterator.remove();
            }
        }
    }

    public void updateUfo() {

        Iterator<Ufo> ufoIterator = ufo.iterator();

        while (ufoIterator.hasNext()) {
            Ufo ufo = ufoIterator.next();
            // update UFO
            ufo.move();
            // remove dead UFO
            if (ufo.isDead()) {
                ufoIterator.remove();
            }
        }
    }

    public void updateRocket() {

        Iterator<Rocket> rocketIterator = rockets.iterator();

        while (rocketIterator.hasNext()) {
            Rocket rocket = rocketIterator.next();
            // update rocket
            rocket.move();
            // remove rocket after leaving the screen
            // remove dead rocket
            if (rocket.getX() >= screenWidth + tileSize || rocket.isDead()) {
                rocketIterator.remove();
            }
        }
    }

    public void updateBomb() {

        Iterator<Bomb> bombIterator = bombs.iterator();

        while (bombIterator.hasNext()) {
            Bomb bomb = bombIterator.next();
            // update bomb
            bomb.move();
            // remove bomb after leaving the screen
            if ((bomb.getX() >= screenWidth + tileSize || bomb.getX() <= - tileSize)) {
                bombIterator.remove();
            }
        }
    }

    public void borderCollision() {

        // prevent ship from going offscreen
        ship.checkCollision();

        // bounce off ufo from the screen edges
        for (Ufo ufo: ufo) {
            ufo.checkCollision();
        }

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2D = ((Graphics2D) g);

        // Background Image
        g2D.drawImage(backgroundImg, 0, 0, this);

        synchronized (ufo) {
            for (Ufo ufo : ufo) {
                ufo.draw(g2D);
            }
        }

        synchronized (asteroids) {
            for (Asteroid asteroid: asteroids) {
                asteroid.draw(g2D);
            }
        }

        ship.draw(g2D);

        synchronized (rockets) {
            for (Rocket rocket : rockets) {
                rocket.draw(g2D);
            }
        }

        synchronized (bombs) {
            for (Bomb bomb : bombs) {
                bomb.draw(g2D);
            }
        }

        // ensure pending graphics operations are completed
        Toolkit.getDefaultToolkit().sync();
        // dispose of the graphics context after all drawing operations
        g2D.dispose();
    }

    // main game loop
    @Override
    public void run() {

        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {

                rocketSpawn();// spawn rockets on key press
                checkRockets();// checks rocket collision
                checkBomb();// checks boom collision
                update();// update position and remove dead objects
                asteroidSpawn();// spawn asteroid
                ufoSpawn();// spawn ufo's
                borderCollision();// checks screen collision
                repaint();// repaint the panel
                delta--;
            }
        }
    }

    public List<Asteroid> getAsteroids() {
        return asteroids;
    }

    public List<Rocket> getRockets() {
        return rockets;
    }

    public List<Bomb> getBombs() {
        return bombs;
    }
}**/