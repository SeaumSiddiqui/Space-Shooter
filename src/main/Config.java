package main;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Config {

    GameFrame game;
    private final String configFileName = "config.txt";

    Config(GameFrame game) {
        this.game = game;
    }

    public void saveConfig() {

        try {
            Path configFilePath = Paths.get(System.getProperty("user.home"), configFileName);
            BufferedWriter bw = new BufferedWriter(new FileWriter(configFilePath.toString()));


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
            Path configFilePath = Paths.get(System.getProperty("user.home"), configFileName);

            if (Files.exists(configFilePath)) {
                BufferedReader br = new BufferedReader(new FileReader(configFilePath.toString()));

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
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
