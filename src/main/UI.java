package main;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class UI {
    GameFrame game;
    BufferedImage heart;

    Font customFont;
    Font scoreFont;
    Font messageFont;

    public boolean showMessage = false;
    public String message = "";

    public UI(GameFrame game) {
        this.game = game;

        try {
            // Load the custom font using Font.TRUETYPE_FONT
            InputStream fontPath = getClass().getResourceAsStream("/fonts/LLPIXEL3.ttf");
            assert fontPath != null;
            customFont = Font.createFont(Font.TRUETYPE_FONT, fontPath);

            scoreFont = customFont.deriveFont(game.tileSize / 3f);
            messageFont = customFont.deriveFont(game.tileSize * 10f);

            // load heart icons
            heart = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player sprites/heart2.png")));


        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }


    }
    public void showMessage(String text) {

        message = text;

        showMessage = true;
    }

    public void draw(Graphics2D g2D) {

        g2D.setFont(scoreFont);
        g2D.setColor(Color.WHITE);
        g2D.drawString("Score: ", game.screenWidth - game.tileSize * 3, game.tileSize / 2); // Display the score at coordinates (200, 100)

        g2D.drawString(message, game.screenWidth / 2, game.screenHeight / 2);


        BufferedImage image1 = null;
        BufferedImage image2 = null;
        BufferedImage image3 = null;

        switch (game.ship.playerHealth) {
            case "max" -> {
                image1 = heart;
                image2 = heart;
                image3 = heart;
            }
            case "2Left" -> {
                image1 = heart;
                image2 = heart;
            }
            case "1Left" -> image1 = heart;
        }

            g2D.drawImage(image1, game.initTileSize, game.screenHeight - game.tileSize, game.tileSize / 2, game.tileSize / 2, null);

            g2D.drawImage(image2, game.initTileSize * 3, game.screenHeight - game.tileSize, game.tileSize / 2, game.tileSize / 2, null);

            g2D.drawImage(image3, game.initTileSize * 5, game.screenHeight - game.tileSize, game.tileSize / 2, game.tileSize / 2, null);
    }
}
