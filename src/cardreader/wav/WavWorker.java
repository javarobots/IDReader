
package cardreader.wav;

import cardreader.ui.Model;
import java.io.File;
import javax.swing.SwingWorker;

/**
 *
 * @author javarobots
 */
public class WavWorker extends SwingWorker<Boolean,Boolean> {

    private Model mModel;
    private String mFilePath;
    private boolean mResetToWaiting = true;

    public WavWorker(Model model, String filePath) {
        this.mModel = model;
        this.mFilePath = filePath;
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        WavPlayer player = new WavPlayer();
        File f = new File(mModel.getPreferences().getFileLocation() + mFilePath);
        player.play(f);
        return true;
    }

    @Override
    public void done() {
        if (mResetToWaiting){
            mModel.clearData(true);
            mModel.setStatusText("Waiting");
            mModel.clearGreetAndName();
            mModel.notifyObservers();
            mModel.clearData(false);
        }
    }

    public void setResetToWaiting(boolean reset) {
        this.mResetToWaiting = reset;
    }



}
