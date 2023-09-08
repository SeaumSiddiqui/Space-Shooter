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
    private BufferedImage[] backgroundImage;
    private int currentImageIndex = 0;
    private int xOffset = 0;

    public BackgroundSlideshow(GameFrame game) {

        this.game = game;
        getBackgroundImage();
        startSlideshow();
    }

    public void getBackgroundImage() {

        try {

            BufferedImage image1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/space/image1.png")));
            BufferedImage image2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/space/image2.png")));
            BufferedImage image3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/space/image3.png")));
            BufferedImage image4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/space/image4.png")));
            BufferedImage image5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/space/image5.png")));
            BufferedImage image6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/space/image6.png")));

            backgroundImage = new BufferedImage[]{image1, image2, image3, image4, image5, image6};

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startSlideshow() {

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                xOffset += (double) 1.2;
                if (xOffset >= game.screenWidth) {
                    xOffset = 0;
                    currentImageIndex = (currentImageIndex + 1) % backgroundImage.length;
                }
            }
        }, 0, 70); // Adjust the delay to control slideshow speed
    }

    public void draw(Graphics2D g2D) {

        BufferedImage currentImage = backgroundImage[currentImageIndex];
        g2D.drawImage(currentImage, -xOffset, 0, game.screenWidth, game.screenHeight, null);

        int nextImageIndex = (currentImageIndex + 1) % backgroundImage.length;
        BufferedImage nextImage = backgroundImage[nextImageIndex];
        g2D.drawImage(nextImage, game.screenWidth - xOffset, 0, game.screenWidth, game.screenHeight, null);
    }
}
