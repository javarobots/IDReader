
package cardreader.datahandling;

import cardreader.ui.Controller;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextField;

/**
 *
 * @author javarobots
 */
public class ReaderKeyListener implements KeyListener {

    private Controller mReaderController;

    public ReaderKeyListener(Controller controller){
        this.mReaderController = controller;
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        //printIt("Pressed", keyEvent);
    }

        @Override
    public void keyReleased(KeyEvent keyEvent) {
        if (KeyEvent.getKeyText(keyEvent.getKeyCode()).equalsIgnoreCase("enter")){
            mReaderController.processText(((JTextField)keyEvent.getComponent()).getText());
        } else {
            mReaderController.readingText();
        }

    }

        @Override
    public void keyTyped(KeyEvent keyEvent) {
        //printIt("Typed", keyEvent);
    }

//    Used for development purposes
//    private void printIt(String title, KeyEvent keyEvent) {
//        int keyCode = keyEvent.getKeyCode();
//        String keyText = KeyEvent.getKeyText(keyCode);
//        System.out.println(title + " : " + keyText + " / " + keyEvent.getKeyChar());
//    }

}
