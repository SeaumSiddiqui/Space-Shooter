package main;

import space.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class GameFrame extends JPanel implements Runnable {

    // Screen settings
    public final int initTileSize = 16; //16x16 tile
    public final int scale = 4;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 10;
    public final int tileSize = initTileSize * scale; //64x64 tile
    public final int screenWidth = tileSize* maxScreenCol; //1024px
    public final int screenHeight = tileSize * maxScreenRow; //640px
    public final int FPS = 60; //60 frames per second
    public double score = 0;

    // game state
    public int gameState;
    public final int play = 3;
    public final int pause = 2;
    public final int gameOver = 1;
    public final int titleState = 0;

    // background
    BackgroundSlideshow slideshow = new BackgroundSlideshow(this);

    // game sound
    GameSound sound = new GameSound();

    // initialize KeyHandler
    KeyHandler keyH = new KeyHandler(this);

    // UI
    UI ui = new UI(this);

    // spaceship
    public Spaceship  ship = new Spaceship(0, screenHeight / 2 - tileSize, tileSize,
            tileSize, 999, 999, 8, keyH, this);

    // UFO
    public final List<Ufo> ufo = new ArrayList<>();

    // asteroids
    public final List<Asteroid> asteroids = new ArrayList<>();

    // rocket
    public final List<Rocket> rockets = new ArrayList<>();

    // bomb
    public final List<Bomb> bombs = new ArrayList<>();

    Thread gameThread;

    GameFrame() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        gameState = titleState;
    }

    public List<SpaceObjects> getObjects() {

        ArrayList<SpaceObjects> list = new ArrayList<>();
        list.addAll(asteroids);
        list.addAll(rockets);
        list.addAll(bombs);
        list.addAll(ufo);
        return list;
    }

    public void startGame() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {

        if (gameState == play) {
            ship.move();

            for (SpaceObjects object : getObjects()) {
                object.move();
            }
        }
    }

    public void spaceshipSpawn() {

        if (!ship.isDead()) {
            ship = new Spaceship(0, screenHeight / 2 - tileSize, tileSize,
                    tileSize, ship.getHealth(), 999, 8, keyH, this);
        }
    }

    public void objectSpawn() {

        if (gameState ==  play) {
            asteroidSpawn();// spawn asteroid
            ufoSpawn();// spawn ufo's
        }
    }

    public void asteroidSpawn(){

       Random randomAsteroid = new Random();
       int random100 = randomAsteroid.nextInt(250);

       if (random100 == 0) {
           asteroids.add(new Asteroid(screenWidth, randomAsteroid.nextInt(screenHeight - tileSize), tileSize, tileSize, 333, 333, 1, this));
       }
    }

    // change the spawn direction to Y
    // TO-DO
    public void ufoSpawn() {

        if (ufo.size() > 0) return;

        Random randomUfo = new Random();
        double spawnProbability = 0.0050;
        // 0.50% chance of spawn in each iteration
        // 5 spawn in every 1000 iteration
        if (randomUfo.nextDouble() < spawnProbability) {

            ufo.add(new Ufo(screenWidth - tileSize * 2, (int)(Math.random() * screenHeight/2),tileSize, tileSize, 555, 333, 2, this));
            ufo.add(new Ufo(screenWidth - tileSize * 2, (int)(Math.random() * screenHeight/2 + tileSize),tileSize, tileSize, 555, 333, 2, this));
            ufo.add(new Ufo(screenWidth - tileSize * 2, (int)(Math.random() * screenHeight/2 + (tileSize * 4)),tileSize, tileSize, 555, 333, 2, this));
        }
    }

    public void checkRockets() {

        for (Rocket rocket: rockets) {
            // check collision between rocket and asteroid
            for (Asteroid asteroid: asteroids) {

                if (asteroid.intersects(rocket)) {
                    asteroid.takeDamage(rocket.getDamage());
                    rocket.setDead(true);
                    score+= 2;
                }
            }
            // check collision between rocket and UFO
            for (Ufo ufo: ufo) {

                if (ufo.intersects(rocket)) {
                    ufo.takeDamage(rocket.getDamage());
                    rocket.setDead(true);
                    score+= 2;
                }
            }
            // check collision between rocket and bomb
            for (Bomb bomb: bombs) {

                if (bomb.intersects(rocket)) {
                    bomb.takeDamage(rocket.getDamage());
                    rocket.setDead(true);
                    score++;
                }
            }
        }
    }

    public void checkBomb() {
        // check collision between bombs and the spaceship
        for (Bomb bomb: bombs) {
            // check collision between bomb and asteroid
            for (Asteroid asteroid: asteroids) {
                if (asteroid.intersects(bomb)) {
                    asteroid.takeDamage(bomb.getDamage());
                    bomb.takeDamage(asteroid.getDamage());
                }
            }
            // check collision between bomb and spaceship
            if (ship.intersects(bomb)) {
                ship.takeDamage(bomb.getDamage());
                bomb.takeDamage(ship.getDamage());
                spaceshipSpawn();
            }
        }
    }

    public void checkUfo() {

        for (Ufo ufo: ufo) {
            if (ship.intersects(ufo)) {
                ship.takeDamage(ufo.getDamage());
                ufo.takeDamage(ship.getDamage());
                spaceshipSpawn();
            }
        }
    }

    public void checkAsteroid() {
        for(Asteroid asteroid: asteroids) {
            if (ship.intersects(asteroid)) {
                ship.takeDamage(asteroid.getDamage());
                asteroid.takeDamage(ship.getDamage());
                spaceshipSpawn();
            }
        }
    }

    public void borderCollision() {
        // remove offscreen rockets
        for (Rocket rocket: rockets) {
            rocket.checkCollision();
        }// remove offscreen bombs and explode if touches the bottom screen
        for (Bomb bomb: bombs) {
            bomb.checkCollision();
        }// remove offscreen asteroids
        for (Asteroid asteroid: asteroids) {
            asteroid.checkCollision();
        }// bounce off ufo from screen edges
        for (Ufo ufo: ufo) {
            ufo.checkCollision();
        }
        // prevent ship from going offscreen
        ship.checkCollision();
    }

    public void removeDead() {

        rockets.removeIf(SpaceObjects::isDead);
        asteroids.removeIf(SpaceObjects::isDead);
        ufo.removeIf(SpaceObjects::isDead);
        bombs.removeIf(SpaceObjects::isDead);
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2D = ((Graphics2D) g);

            // draw background
            slideshow.draw(g2D);
            // draw spaceship
            ship.drawSpaceship(g2D);
            // draw other game objects
            for (SpaceObjects object : getObjects()) {

                try {
                    object.draw(g2D);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // draw ui
            ui.drawUI(g2D);

        // ensure pending graphics operations are completed
        Toolkit.getDefaultToolkit().sync();
        // dispose of the graphics context after all drawing operations
        g2D.dispose();
    }

    // main game loop
    public void run() {

        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                objectSpawn(); // spawn ufo & asteroid
                checkRockets(); // checks rocket collision with objects
                checkBomb(); // checks boom collision with objects
                checkUfo(); // checks collision between ufo and spaceship
                checkAsteroid(); // checks collision between asteroid and spaceship
                removeDead(); // remove dead objects
                update(); // update position and remove dead objects
                borderCollision(); // checks collision with edge/remove offscreen objects
                repaint(); // repaint the panel
                delta--;
            }
        }
    }

    public void restart() {
        // clear all objects lists
        rockets.clear();
        bombs.clear();
        asteroids.clear();
        ufo.clear();
        for (SpaceObjects object : getObjects()) {
            object.setDead(true);
        }

        // Reset all game-related variables to their initial values
        gameState = play; // set the game state to "play"
        ship.health = 999; // set player health to full
        ship.setDead(false); // mark player as alive
        score = 0;

        // reset the spaceship
        spaceshipSpawn();
    }


    // play background music
    public void playMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    public void stopMusic() {
        sound.stop();
    }
    // play sound effects
    public void playSE(int i) {
        sound.setFile(i);
        sound.play();
    }

    public List<Rocket> getRockets() {
        return rockets;
    }

    public List<Bomb> getBombs() {
        return bombs;
    }
}
