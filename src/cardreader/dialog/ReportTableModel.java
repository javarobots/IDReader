
package cardreader.dialog;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author javarobots
 */
public class ReportTableModel extends AbstractTableModel {

    private String[] columnHeading = {"Name"};
    private List<String> names = new ArrayList<>();

    @Override
    public int getRowCount() {
        return names.size();
    }

    @Override
    public int getColumnCount() {
        return columnHeading.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return names.get(rowIndex);
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnHeading[columnIndex];
    }

    public void addName(String name){
        names.add(name);
        fireTableDataChanged();
    }
    
    public void clearData() {
        names.clear();
    }

}
