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
    Font scoreFont, optionFont, titleFont, menuFont;
    //public int commandNum = 0;

    public boolean showMessage = false;
    public String message = "";
    private Graphics2D g2D;

    public UI(GameFrame game) {
        this.game = game;

        try {
            // Load the custom font using Font.TRUETYPE_FONT
            InputStream fontPath1 = getClass().getResourceAsStream("/fonts/LLPIXEL3.ttf");
            assert fontPath1 != null;
            LLPIXEL3 = Font.createFont(Font.TRUETYPE_FONT, fontPath1);

            scoreFont = LLPIXEL3.deriveFont(Font.BOLD, game.tileSize / 3f);


            InputStream fontPath2 = getClass().getResourceAsStream("/fonts/MaruMonica.ttf");
            assert fontPath2 != null;
            MaruMonica = Font.createFont(Font.TRUETYPE_FONT, fontPath2);

            titleFont = MaruMonica.deriveFont(Font.BOLD, game.tileSize * 2f);
            menuFont = MaruMonica.deriveFont(Font.BOLD, game.tileSize / 2f);
            optionFont = MaruMonica.deriveFont(Font.BOLD, game.tileSize);


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

    public void drawUI(Graphics2D g2D) {

        this.g2D = g2D;

        if (game.gameState == game.play) {
            drawGameUI();
        }
        if (game.gameState == game.pause) {
            drawPauseUI();
        }
        if (game.gameState == game.option) {
            drawOptionUI();
        }
        if (game.gameState == game.titleState) {
            drawTitleUI();
        }
        if (game.gameState == game.gameOver) {
            drawEndUI();
        }
    }

    public void drawGameUI() {

        int x = game.initTileSize * 2;
        int y = game.screenHeight - game.initTileSize * 2;

        g2D.setFont(scoreFont);
        g2D.setColor(Color.WHITE);
        g2D.drawString(String.format("SCORE : %.2f", game.score), x, y);// display score on game screen

        y = game.initTileSize;
        int width = game.tileSize / 2;
        int height = game.tileSize / 2;

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

        g2D.drawImage(image3, game.screenWidth - width * 4, y, width, height, null);
        g2D.drawImage(image2, game.screenWidth - width * 3, y, width, height, null);
        g2D.drawImage(image1, game.screenWidth - width * 2, y, width, height, null);
    }

    public void drawPauseUI () {

        int x = -1;
        int y = game.tileSize * 4;

        g2D.setColor(Color.WHITE);
        g2D.drawRect(x, y,game.screenWidth+1, game.screenHeight / 3);

        String text = "Paused";
        g2D.setFont(titleFont);

        x = getXonCenter(text);
        y = game.tileSize * 6;

        // text shadow
        g2D.setColor(Color.DARK_GRAY);
        g2D.drawString(text, x+5, y+5);
        // main text
        g2D.setColor(Color.WHITE);
        g2D.drawString(text, x, y);


        showMessage("press Esc to resume");
        g2D.setFont(menuFont);

        x = getXonCenter(message);
        y = (int) (game.tileSize * 6.9);

        g2D.setColor(Color.DARK_GRAY);
        g2D.drawString(message, x, y);

    }

    public void drawOptionUI() {

        // sub window
        int frameWidth = game.tileSize * game.maxScreenCol;
        int frameHeight = game.tileSize * game.maxScreenRow;
        int frameX = game.screenWidth/2 - frameWidth/2;
        int frameY = game.screenHeight/2 - frameHeight/2;
        int edge = 25;

        g2D.setColor(new Color(0, 0, 0, 80));
        g2D.fillRoundRect(frameX, frameY, frameWidth, frameHeight, edge , edge);

        // top text
        String text = "Options";
        g2D.setFont(optionFont);

        int x = getXonCenter(text);
        int y = frameY + game.tileSize;

        g2D.setColor(Color.white);
        g2D.drawString(text, x, y);


        // options text
        x = frameX + game.tileSize;
        g2D.setFont(menuFont);

        // draw music
        y = frameY + game.tileSize * 3;
        g2D.drawString("Music Volume ", x, y);

        if (game.keyH.commandNum == 0) {
            g2D.drawString(">", x - game.tileSize / 2, y);
        }


        // draw sound effect
        y = frameY + game.tileSize * 4;
        g2D.drawString("Sound Effect ", x, y);

        if (game.keyH.commandNum == 1) {

            g2D.drawString(">", x - game.tileSize / 2, y);
        }

        y = frameY + game.tileSize * 5;
        g2D.drawString("Main Menu ", x, y);

        if (game.keyH.commandNum == 2) {

            g2D.drawString(">", x - game.tileSize / 2, y);
        }


        // draw quit game
        y = frameY + game.tileSize * 7;
        g2D.drawString("Quit ", x, y);

        if (game.keyH.commandNum == 3) {
            g2D.drawString(">", x - game.tileSize / 2, y);
        }

        // draw slider
        x *= 2;
        y = frameY + game.tileSize * 3;
        // music slider shadow
        int musicWidth = 100 * game.music.volumeScale;
        g2D.setColor(new Color(0x6161C4));
        g2D.fillRoundRect(x, y - 20, musicWidth, 20, 25, 25);
        //music slider
        g2D.drawRoundRect(x, y - 20, 500, 20, 25, 25); // 500/5 = 100

        // effect slider shadow
        y = frameY + game.tileSize * 4;
        int effectWidth = 100 * game.effect.volumeScale;
        g2D.setColor(new Color(0x6161C4));
        g2D.fillRoundRect(x, y - 20, effectWidth, 20, 25, 25);
        // effect slider
        g2D.drawRoundRect(x, y - 20, 500, 20, 25, 25);
    }

    public void drawTitleUI() {

        // game title
        String text = "Space Shooter";
        g2D.setFont(titleFont);

        int x = getXonCenter(text);
        int y = (game.tileSize * 4);

        // text shadow
        g2D.setColor(Color.DARK_GRAY);
        g2D.drawString(text, x+5, y+5);
        // main text
        g2D.setColor(Color.WHITE);
        g2D.drawString(text, x, y);

        // menu
        g2D.setFont(menuFont);

        text = "Start Game";
        x = getXonCenter(text);
        y = game.tileSize * 7;
        g2D.drawString(text, x, y);

        if (game.keyH.commandNum == 0) {
            g2D.drawString(">", x - game.tileSize / 2, y);
        }


        text = "Load Game";
        x = getXonCenter(text);
        y = game.tileSize * 8;
        g2D.drawString(text, x, y);

        if (game.keyH.commandNum == 1) {
            g2D.drawString(">", x - game.tileSize / 2, y);
        }


        text = "Quit";
        x = getXonCenter(text);
        y = game.tileSize * 9;
        g2D.drawString(text, x, y);

        if (game.keyH.commandNum == 2) {
            g2D.drawString(">", x - game.tileSize / 2, y);
        }
    }

    public void drawEndUI () {

        int x = -1;
        int y = game.tileSize * 4;

        g2D.setColor(Color.DARK_GRAY);
        g2D.drawRect(x, y,game.screenWidth+1, game.screenHeight / 3);

        String text = "Game Over";
        g2D.setFont(titleFont);

        x = getXonCenter(text);
        y = game.tileSize * 6;

        // text shadow
        g2D.setColor(Color.DARK_GRAY);
        g2D.drawString(text, x+5, y+5);
        // main text
        g2D.setColor(new Color(0xC71111));
        g2D.drawString(text, x, y);

        // show message
        showMessage("press R to restart");
        g2D.setFont(menuFont);

        x = getXonCenter(message);
        y = (int) (game.tileSize * 6.9);

        g2D.setColor(Color.white);
        g2D.drawString(message, x, y);

        // show score
        String scoreText = String.format("Your Score: %.2f", game.score);
        g2D.setFont(menuFont);

        x = getXonCenter(scoreText);
        y = (game.tileSize * 9);

        g2D.setColor(Color.LIGHT_GRAY);
        g2D.drawString(scoreText, x, y);// display final score
    }

    public int getXonCenter(String text) {

        int length = (int) g2D.getFontMetrics().getStringBounds(text, g2D).getWidth();
        return game.screenWidth/2 - length/2;
    }
}