
package cardreader.datahandling;

import cardreader.ui.Model;
import cardreader.wav.WavWorker;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import org.joda.time.DateTime;

/**
 *
 * @author javarobots
 */
public class DataProcessingWorker extends SwingWorker<Boolean, Object> {

    private Model mModel;
    private String mText;
    private DataParser mParser;

    public DataProcessingWorker(Model m, DataParser parser, String text) {
        this.mModel = m;
        this.mText = text;
        this.mParser = parser;
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        mModel.setStatusText("Processing");
        mModel.notifyObservers();
        IdData personnelData = mModel.getPersonnelData();
        int idNumber = mParser.parseID(mText);
        if (personnelData.idExists(idNumber)){
            DateTime now = new DateTime(System.currentTimeMillis());
            mModel.setGreeting(mParser.properGreeting(now.getHourOfDay()));
            mModel.setName(personnelData.getName(idNumber));
            mModel.notifyObservers();
            saveCardSwipe();
            WavWorker player = new WavWorker(mModel,"/wavs/valid_mary.wav");
            player.execute();
        } else {
            mModel.setGreeting("Invalid ID Number");
            mModel.setName("See Cadre!");
            mModel.notifyObservers();
            WavWorker player = new WavWorker(mModel,"/wavs/invalid_mary.wav");
            player.execute();
        }
        return true;
    }

    private void saveCardSwipe() {
        BufferedWriter bw = null;
        try {
            File outFile = new File(mModel.getPreferences().getFileLocation() + "/attendanceData.txt");
            if (!outFile.exists()) {
                outFile.createNewFile();
            }
            bw = new BufferedWriter(new FileWriter(outFile, true));
            bw.append(mParser.recordDateTime() + "\n");
            bw.append(mText + "\n\n");
            bw.flush();
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(DataProcessingWorker.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (bw != null){
                    bw.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(DataProcessingWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    public void done() {
        mModel.getController().updateDataTable(mModel.getSelectedEvent());
    }

}
