
package cardreader.datahandling;

/**
 *
 * @author javarobots
 */
public enum EventTypes {

    PT(0),
    LLAB(0),
    AS100(100),
    AS200(200),
    AS300(300),
    AS400(400);

    private final int mKeyValue;

    EventTypes(int key){
        this.mKeyValue = key;
    }

    public int getKey() {
        return mKeyValue;
    }

}
