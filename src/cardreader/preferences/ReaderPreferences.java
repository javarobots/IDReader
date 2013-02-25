
package cardreader.preferences;

import java.util.prefs.Preferences;

/**
 *
 * @author javarobots
 */
public class ReaderPreferences {

    private Preferences mPreferences;

    /**
     * Inner enumeration class
     */
    public enum PreferenceType {

        LOCATION_SET("ls"),
        FILE_LOCATION("fl");

        private final String mPrefKey;

        PreferenceType(String key){
            this.mPrefKey = key;
        }

        @Override
        public String toString() {
            return this.mPrefKey;
        }
    }

    public ReaderPreferences() {
        mPreferences = Preferences.userRoot().node(this.getClass().getName());
    }

    public boolean isFileLocationSet() {
        return mPreferences.getBoolean(PreferenceType.LOCATION_SET.toString(), false);
    }

    public void setFileLocationSet(boolean b) {
        mPreferences.putBoolean(PreferenceType.LOCATION_SET.toString(), b);
    }

    public String getFileLocation() {
        return mPreferences.get(PreferenceType.FILE_LOCATION.toString(), null);
    }

    public void setFileLocation(String filePath) {
        mPreferences.put(PreferenceType.FILE_LOCATION.toString(), filePath);
    }


}
