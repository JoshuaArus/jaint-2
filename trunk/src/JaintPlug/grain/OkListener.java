package JaintPlug.grain;

/**
 *
 * @author Jonas
 */
import java.awt.event.*;
import JaintPlug.*;

public class OkListener implements ActionListener
{
	private Grain g;

        /**
         *
         * @param g
         */
        public OkListener(Grain g)
	{
		this.g = g;
	}

	public void actionPerformed(ActionEvent e)
	{
		this.g.apply();
	}
}