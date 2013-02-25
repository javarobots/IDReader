
package cardreader.ui;

import cardreader.datahandling.IdData;
import cardreader.preferences.ReaderPreferences;
import java.util.Observable;
import java.util.Observer;

/**
 * Model class for the CardReader
 * @author javarobots
 */
public class Model extends Observable {

    private String mStatusText = "Waiting";
    private String mGreeting = "";
    private String mName = "";
    private boolean mClearData = false;
    private boolean mEnableDataTextField = true;
    private IdData mPersonnelData;
    private ReaderPreferences mPreferences;

    public Model(){
        mPreferences = new ReaderPreferences();
        initModel();
    }

    @Override
    public void addObserver(Observer o) {
        super.addObserver(o);
        setChanged();
    }

    private void initModel() {
        mPersonnelData = new IdData(mPreferences);
        mPersonnelData.processInputDataFile();
        if (!mPreferences.isFileLocationSet()){
            setStatusText("Not Ready, Set Data Location");
            setEnableDataTextField(false);
        }
    }

    public void setStatusText(String text){
        mStatusText = text;
        setChanged();
    }

    public void clearData(boolean b){
        mClearData = b;
        setChanged();
    }

    public String getStatusText() {
        return mStatusText;
    }

    public boolean isClearData() {
        return mClearData;
    }

    public boolean isEnableDataTextField() {
        return mEnableDataTextField;
    }

    public void setEnableDataTextField(boolean b) {
        this.mEnableDataTextField = b;
        setChanged();
    }

    public String getGreeting() {
        return mGreeting;
    }

    public void setGreeting(String mGreeting) {
        this.mGreeting = mGreeting;
        setChanged();
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
        setChanged();
    }

    public IdData getPersonnelData() {
        return mPersonnelData;
    }

    public void clearGreetAndName() {
        mGreeting = "";
        mName = "";
        setChanged();
    }
    
    public ReaderPreferences getPreferences() {
        return mPreferences;
    }



}
