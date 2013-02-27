
package cardreader.ui;

import cardreader.datahandling.DataParser;
import cardreader.datahandling.DataProcessingWorker;
import cardreader.datahandling.EventTypes;
import cardreader.dialog.FilePathDialog;
import cardreader.dialog.ReportDialog;
import commonutilities.swing.ComponentPosition;
import java.io.File;
import java.io.IOException;
import java.util.List;
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
        if (JOptionPane.showConfirmDialog(null, "Are you sure you want to erase the attendance record? This can not be undone!", "Erase", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
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
    }

    public void setFileLocation() {
        FilePathDialog dialog = new FilePathDialog(null,true,mModel.getPreferences(), false);
        ComponentPosition.centerFrame(dialog);
        dialog.setVisible(true);
    }

    public void fileSaveAs(String eventName) {
//        JOptionPane.showMessageDialog(null, "Replace XXXXX in file name with appropriate event (PT, LLAB, AS100, AS200, AS300 or AS400)", "Update Name", JOptionPane.PLAIN_MESSAGE);
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "Text Files", "txt");
        chooser.setFileFilter(filter);
        chooser.setSelectedFile(new File(mParser.gregorianDateTime() + "_" + eventName + "_Attendance.txt"));
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
            try {
                FileUtils.copyFile(new File(mModel.getPreferences().getFileLocation() + "/attendanceData.txt"), chooser.getSelectedFile());
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void runReport(EventTypes type) {
        ReportDialog dialog = new ReportDialog(null,true,type, mModel);
        ComponentPosition.centerFrame(dialog);
        dialog.setVisible(true);
    }

    public void updateDataTable(String eventName) {
        mModel.setSelectedEvent(eventName);
        EventTypes eventType = null;
        if (eventName.equals("PT")){
            eventType = EventTypes.PT;
        } else if (eventName.equals("LLAB")){
            eventType = EventTypes.LLAB;            
        } else if (eventName.equals("AS100")){
            eventType = EventTypes.AS100;            
        } else if (eventName.equals("AS200")){
            eventType = EventTypes.AS200;            
        } else if (eventName.equals("AS300")){
            eventType = EventTypes.AS300;            
        } else if (eventName.equals("AS400")){
            eventType = EventTypes.AS400;            
        }
        List<Integer> expectedIDs = null;
        if (eventType != EventTypes.PT && eventType != EventTypes.LLAB){
            expectedIDs = mModel.getPersonnelData().getIDsForAsLevel(eventType);
        } else {
            expectedIDs = mModel.getPersonnelData().getAllIDs();
        }
        List<Integer> idsInDataFile = mModel.getPersonnelData().getIDsInDataFile();

        mModel.getDataTableModel().clearData();
        for (int expected : expectedIDs) {
            if (!idsInDataFile.contains(expected)){
                mModel.getDataTableModel().addName(mModel.getPersonnelData().getName(expected));
            }
        }
        mModel.forceUpdate();
//        mModel.getDataTableModel().fireTableDataChanged();
    }

}
