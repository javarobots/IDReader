
package cardreader.ui;

import cardreader.datahandling.DataParser;
import cardreader.datahandling.DataProcessingWorker;
import cardreader.datahandling.EventTypes;
import cardreader.dialog.FilePathDialog;
import cardreader.dialog.ReportDialog;
import commonutilities.swing.ComponentPosition;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author javarobots
 */
public class Controller {

    private Model mModel;
    private DataParser mParser;

    public Controller(Model model){
        this.mModel = model;
        mParser = new DataParser();
    }

    public void readingText(){
        mModel.setStatusText("Reading");
        mModel.notifyObservers();
    }

    public void processText(String dataString) {
        DataProcessingWorker worker = new DataProcessingWorker(mModel, mParser, dataString);
        worker.execute();
    }

    public void clearAttendance() {
        File dataFile = new File(mModel.getPreferences().getFileLocation() + "/attendanceData.txt");
        if (dataFile.exists()){
            try {
                dataFile.delete();
                dataFile.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void setFileLocation() {
        FilePathDialog dialog = new FilePathDialog(null,true,mModel.getPreferences());
        ComponentPosition.centerFrame(dialog);
        dialog.setVisible(true);
    }

    public void fileSaveAs() {
        JOptionPane.showMessageDialog(null, "Replace XXXXX in file name with appropriate event (PT, LLAB, AS100, AS200, AS300 or AS400)", "Update Name", JOptionPane.PLAIN_MESSAGE);
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "Text Files", "txt");
        chooser.setFileFilter(filter);
        chooser.setSelectedFile(new File(mParser.gregorianDateTime() + "_XXXXX_Attendance.txt"));
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
            try {
                FileUtils.copyFile(new File(mModel.getPreferences().getFileLocation() + "/attendanceData.txt"), chooser.getSelectedFile());
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    void runReport(EventTypes type) {
        ReportDialog dialog = new ReportDialog(null,true,type, mModel);
        ComponentPosition.centerFrame(dialog);
        dialog.setVisible(true);
    }

}
