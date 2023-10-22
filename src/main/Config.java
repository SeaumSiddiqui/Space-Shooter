package main;

import java.io.*;

public class Config {

    GameFrame game;

    Config(GameFrame game) {
        this.game = game;
    }

    public void saveConfig() {

        try {

            BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));

            // highest score
            bw.write(String.valueOf(game.highestScore));
            bw.newLine();

            // music volume
            bw.write(String.valueOf(game.music.volumeScale));
            bw.newLine();

            // effect volume
            bw.write(String.valueOf(game.effect.volumeScale));
            bw.newLine();

            bw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadConfig() {

        try {

            BufferedReader br = new BufferedReader(new FileReader("config.txt"));

            // highest score
            String s = br.readLine();
            game.highestScore = Double.parseDouble(s);

            // music volume
            s = br.readLine();
            game.music.volumeScale = Integer.parseInt(s);

            // effect volume
            s = br.readLine();
            game.effect.volumeScale = Integer.parseInt(s);

            br.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
