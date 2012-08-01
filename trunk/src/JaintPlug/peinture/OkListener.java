package JaintPlug.peinture;

/**
 *
 * @author Jonas
 */
import java.awt.event.*;
import JaintPlug.*;

public class OkListener implements ActionListener
{
	private PeintureHuile ph;

        /**
         *
         * @param ph
         */
        public OkListener(PeintureHuile ph)
	{
		this.ph = ph;
	}

	public void actionPerformed(ActionEvent e)
	{
		this.ph.apply();
	}
}