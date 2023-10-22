package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class BackgroundSlideshow {

    GameFrame game;
    private BufferedImage[] spaceImage, planetImage, starImage;
    private int currentIndexSpace, currentIndexStars, currentIndexPlanet = 0;
    private int xOffsetSpace, xOffsetStars, xOffsetPlanet = 0;

    public BackgroundSlideshow(GameFrame game) {

        this.game = game;

        getBackgroundImages();
        startSlideshow();
    }

    public void getBackgroundImages() {

        try {

            BufferedImage space1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/space/bg-space1.png")));
            BufferedImage space2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/space/bg-space2.png")));

            spaceImage = new BufferedImage[]{space1, space2};

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {

            BufferedImage planet1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/space/bg-planet1.png")));
            BufferedImage planet2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/space/bg-planet2.png")));

            planetImage = new BufferedImage[]{planet1, planet2};

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {

            BufferedImage star1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/space/bg-stars1.png")));
            BufferedImage star2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/space/bg-stars2.png")));

            starImage = new BufferedImage[]{star1, star2};

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startSlideshow() {

        Timer timer = new Timer();

        // main background
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                xOffsetSpace += 1;
                if (xOffsetSpace >= game.screenWidth) {
                    xOffsetSpace = 0;
                    currentIndexSpace = (currentIndexSpace + 1) % spaceImage.length;
                }
            }
        }, 0, 59); // Adjust the delay to control slideshow speed

        // background stars
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                xOffsetStars += (int) 1.7;
                if (xOffsetStars >= game.screenWidth) {
                    xOffsetStars = 0;
                    currentIndexStars = (currentIndexStars + 1) % starImage.length;
                }
            }
        }, 0, 42); // Adjust the delay to control slideshow speed

        // background planet
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                xOffsetPlanet += (int) 1.9;
                if (xOffsetPlanet >= game.screenWidth) {
                    xOffsetPlanet = 0;
                    currentIndexPlanet = (currentIndexPlanet + 1) % planetImage.length;
                }
            }
        }, 0, 31); // Adjust the delay to control slideshow speed
    }

    public void draw(Graphics2D g2D) {

        drawSpace(g2D);
        drawStars(g2D);
        drawPlanet(g2D);
    }

    public void drawSpace(Graphics2D g2D) {

        BufferedImage currentImage = spaceImage[currentIndexSpace];
        g2D.drawImage(currentImage, -xOffsetSpace, 0, game.screenWidth, game.screenHeight, null);

        int nextImageIndex = (currentIndexSpace + 1) % spaceImage.length;
        BufferedImage nextImage = spaceImage[nextImageIndex];
        g2D.drawImage(nextImage, game.screenWidth - xOffsetSpace, 0, game.screenWidth, game.screenHeight, null);
    }

    public void drawStars(Graphics2D g2D) {

        BufferedImage currentImage = starImage[currentIndexStars];
        g2D.drawImage(currentImage, -xOffsetStars, 0, game.screenWidth, game.screenHeight, null);

        int nextImageIndex = (currentIndexStars + 1) % starImage.length;
        BufferedImage nextImage = starImage[nextImageIndex];
        g2D.drawImage(nextImage, game.screenWidth - xOffsetStars, 0, game.screenWidth, game.screenHeight, null);
    }

    public void drawPlanet(Graphics2D g2D) {

        BufferedImage currentImage = planetImage[currentIndexPlanet];
        g2D.drawImage(currentImage, -xOffsetPlanet, 0, game.screenWidth, game.screenHeight, null);

        int nextImageIndex = (currentIndexPlanet + 1) % planetImage.length;
        BufferedImage nextImage = planetImage[nextImageIndex];
        g2D.drawImage(nextImage, game.screenWidth - xOffsetPlanet, 0, game.screenWidth, game.screenHeight, null);
    }
}
