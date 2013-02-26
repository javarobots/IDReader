
package cardreader.wav;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author javarobots
 */
public class WavPlayer {

    public void play(File file) {
        try {
            try (Clip clip = AudioSystem.getClip()) {
                clip.open(AudioSystem.getAudioInputStream(file));
                clip.start();
                while (!clip.isRunning()) {
                    Thread.sleep(10);
                }
                while (clip.isRunning()) {
                    Thread.sleep(10);
                }
            }
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException | InterruptedException exc) {
            exc.printStackTrace(System.out);
        }
    }
}
