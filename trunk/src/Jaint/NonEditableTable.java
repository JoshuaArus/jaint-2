package Jaint;

/**
 *<code>JTable</code> avec des cellules non editables.
 * @author Jonas
 */
import javax.swing.JTable;
import javax.swing.table.TableModel;


public class NonEditableTable extends JTable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1617746832183058300L;
	/**
     *Constructeur d'une JTable non éditable
     * @param dm TableModel
     */
    public NonEditableTable(TableModel dm)
    {
            super(dm);
    }
    @Override
    public boolean isCellEditable(int row, int col)
    {
            return(false);
    }
}