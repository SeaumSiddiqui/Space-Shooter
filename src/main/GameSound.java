package main;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class GameSound {

    Clip clip;
    URL[] soundUrl = new URL[8];
    FloatControl fc;
    public int volumeScale = 5;
    float volume;

    public GameSound(){

        soundUrl[0] = getClass().getResource("/sound/space-shooter.wav");// background music
        soundUrl[1] = getClass().getResource("/sound/explosion.wav");// explosion sound
        soundUrl[2] = getClass().getResource("/sound/hit.wav");// spark sound
        soundUrl[3] = getClass().getResource("/sound/shoot1.wav");// shoot x
        soundUrl[4] = getClass().getResource("/sound/shoot2.wav");// shoot y
        soundUrl[5] = getClass().getResource("/sound/scroll.wav");// scroll
        soundUrl[6] = getClass().getResource("/sound/volume.wav");// volume level
        soundUrl[7] = getClass().getResource("/sound/click.wav");// click
    }

    public void setFile(int i) {

        try {

            AudioInputStream ais = AudioSystem.getAudioInputStream(soundUrl[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }

    public void checkVolume() {
        switch (volumeScale) {
            case 0 -> volume = -80f;
            case 1 -> volume = -57f;
            case 2 -> volume = -46f;
            case 3 -> volume = -36f;
            case 4 -> volume = -27f;
            case 5 -> volume = -19f;
            case 6 -> volume = -12f;
            case 7 -> volume = -6f;
            case 8 -> volume = -1f;
            case 9 -> volume = 3f;
            case 10 -> volume = 6f;
        }
        fc.setValue(volume);
    }
}





