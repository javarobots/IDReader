
package cardreader.datahandling;

import cardreader.dialog.FilePathDialog;
import cardreader.preferences.ReaderPreferences;
import commonutilities.swing.ComponentPosition;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author javarobots
 */
public class IdData {

    private ReaderPreferences mPreferences;
    private Map<Integer,String> mIDToNameMap;
    private Map<Integer,Integer> mIDToAslevelMap;

    public IdData(ReaderPreferences prefs) {
        this.mPreferences = prefs;
    }

    /**
     * Read the data file to populate the class maps
     */
    public void processInputDataFile(){
        BufferedReader br = null;
        try {
            mIDToNameMap = new HashMap<>();
            mIDToAslevelMap = new HashMap<>();
            if (mPreferences.isFileLocationSet()){
                br = new BufferedReader(new FileReader(mPreferences.getFileLocation() + "/personnel.txt"));
                String strLine;
                while((strLine = br.readLine()) != null) {
                    //Only process lines that do not contain the # comment
                    if (!strLine.contains("#")){
                        String[] splitString = strLine.split(";");
                        String id = splitString[2];
                        String asLevel = splitString[1];
                        String name = splitString[0];
                        mIDToAslevelMap.put(Integer.parseInt(id), Integer.parseInt(asLevel));
                        mIDToNameMap.put(Integer.parseInt(id), name);
                    }
                }
                br.close();
            } else {
                FilePathDialog dialog = new FilePathDialog(null, true, mPreferences);
                ComponentPosition.centerFrame(dialog);
                dialog.setVisible(true);
            }

        } catch (IOException ex) {
            Logger.getLogger(IdData.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (br != null){
                    br.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(IdData.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Determine if an ID number exists
     * @param idNumber number to check
     * @return true when exists
     */
    public boolean idExists(int idNumber) {
        return mIDToNameMap.containsKey(idNumber);
    }

    /**
     * Get name for ID number
     * @param idNumber the number for the name
     * @return name corresponding to given ID number
     */
    public String getName(int idNumber) {
        return mIDToNameMap.get(idNumber);
    }

    /**
     * Get the AS level of the ID number
     * @param idNumber the number for the AS level
     * @return AS level corresponding to given ID number
     */
    public int getAsLevel(int idNumber) {
        return mIDToAslevelMap.get(idNumber);
    }

    /**
     * Get a list of ID numbers for a given Event Type
     * @param type the event (e.g. LLAB, PT)
     * @return a list of ID numbers
     */
    public List getIDsForAsLevel(EventTypes type) {
        List<Integer> idNumbers = new ArrayList<>();
        for (int id : mIDToAslevelMap.keySet()) {
            if (mIDToAslevelMap.get(id).equals(type.getKey())){
                idNumbers.add(id);
            }
        }
        return idNumbers;
    }

    public List getAllIDs() {
        List<Integer> idNumbers = new ArrayList<>();
        for (int id : mIDToNameMap.keySet()){
            idNumbers.add(id);
        }
        return idNumbers;
    }

    public List getIDsInDataFile() {
        DataParser parser = new DataParser();
        List<Integer> ids = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(mPreferences.getFileLocation() + "/attendanceData.txt"));
            String strLine;
            while((strLine = br.readLine()) != null) {
                //Only process lines that contain ; and ?
                if (strLine.contains(";") && strLine.contains("?")){
                    int id = parser.parseID(strLine);
                    if (!ids.contains(id)){
                        ids.add(id);
                    }
                }
            }
            br.close();
        } catch (IOException  ex) {
            Logger.getLogger(IdData.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(IdData.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ids;
    }

}
