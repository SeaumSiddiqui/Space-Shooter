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

    Font LLPIXEL3, MaruMonica;
    Font scoreFont, messageFont, titleFont, menuFont;
    //public int commandNum = 0;

    public boolean showMessage = false;
    public String message = "";

    public UI(GameFrame game) {
        this.game = game;

        try {
            // Load the custom font using Font.TRUETYPE_FONT
            InputStream fontPath1 = getClass().getResourceAsStream("/fonts/LLPIXEL3.ttf");
            assert fontPath1 != null;
            LLPIXEL3 = Font.createFont(Font.TRUETYPE_FONT, fontPath1);

            scoreFont = LLPIXEL3.deriveFont(game.tileSize / 3f);
            messageFont = LLPIXEL3.deriveFont(game.tileSize * 10f);


            InputStream fontPath2 = getClass().getResourceAsStream("/fonts/MaruMonica.ttf");
            assert fontPath2 != null;
            MaruMonica = Font.createFont(Font.TRUETYPE_FONT, fontPath2);

            titleFont = MaruMonica.deriveFont(Font.BOLD, game.tileSize * 2f);
            menuFont = MaruMonica.deriveFont(Font.BOLD, game.tileSize / 2f);


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

    public void drawGameUI(Graphics2D g2D) {

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

    public void drawTitleUI(Graphics2D g2D) {

        // game title
        String text = "Space Shooter";
        int x = game.screenWidth / 6;
        int y = game.tileSize * 3;

        g2D.setFont(titleFont);
        // text shadow
        g2D.setColor(Color.DARK_GRAY);
        g2D.drawString(text, x+5, y+5);
        // main text
        g2D.setColor(Color.WHITE);
        g2D.drawString(text, x, y);

        // menu
        g2D.setFont(menuFont);

        text = "Start Game";
        x = game.screenWidth/2 - game.tileSize;
        y = game.tileSize * 5;
        g2D.drawString(text, x, y);

        if (game.keyH.commandNum == 0) {
            g2D.drawString(">", x - game.tileSize / 2, y);
        }


        text = "Load Game";
        y = game.tileSize * 6;
        g2D.drawString(text, x, y);

        if (game.keyH.commandNum == 1) {
            g2D.drawString(">", x - game.tileSize / 2, y);
        }


        text = "Quit";
        x = (int) (game.screenWidth/2 - game.tileSize /2.5);
        y = game.tileSize * 7;
        g2D.drawString(text, x, y);

        if (game.keyH.commandNum == 2) {
            g2D.drawString(">", x - game.tileSize / 2, y);
        }
    }
}
