package cardreader.datahandling;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author javarobots <javarobots74@gmail.com>
 */
public class DataParserTest {

    public DataParserTest() {
    }

    @Test
    public void testparseID() {
        DataParser parser = new DataParser();

        //A UWF ID String
        String testID = ";20497004218016?";
        int expectedResult = 970042180;
        int result = parser.parseID(testID);
        assertEquals("parseID test failed", expectedResult, result);

        //A USA ID String
        testID = ";20444933416?";
        expectedResult = 449334;
        result = parser.parseID(testID);
        assertEquals("parseID test failed", expectedResult, result);
    }

    @Test
    public void testProperGreeting(){
        DataParser parser = new DataParser();
        String expectedResponse = "Good Morning";
        String response = parser.properGreeting(9);
        assertEquals("Good Morning failed!", expectedResponse, response);
        expectedResponse = "Good Afternoon";
        response = parser.properGreeting(12);
        assertEquals("Good Afternoon failed!", expectedResponse, response);
        expectedResponse = "Good Evening";
        response = parser.properGreeting(17);
        assertEquals("Good Evening failed!", expectedResponse, response);
    }

}
