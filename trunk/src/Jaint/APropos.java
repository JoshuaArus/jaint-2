package Jaint;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

/**
 * Fenetre contenant la licence d'utilisation et les informations sur le logiciel
 * @author Joshua
 */
public class APropos extends JDialog
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7889478324716643749L;

	/**
     * Renvoi une nouvelle instance de la fenetre <code>APropos</code>
     * @param ihm Le composant parent. Dans le cas de Jaint c'est l'IHM
     */
    public APropos(IHM ihm)
    {
        super(ihm,"About",true);

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        JPanel nord = new JPanel();
        nord.add(new JLabel(new ImageIcon(new ImageIcon("./lib/Icons/jaint2.jpg").getImage().getScaledInstance((int)(d.getHeight()/6), (int)(d.getHeight()/6), Image.SCALE_SMOOTH))));

        JPanel centre = new JPanel();

        JTabbedPane jtp = new JTabbedPane();
        centre.add(jtp);

        JPanel jpLicense = new JPanel();

        String license = "Copyright (C) 2010 ARUS Joshua, LEIS Jonas, SPATZ Jean-Francois\n\n";
        license += "This program is free software; you can redistribute it and/or modify\n";
        license += "it under the terms of the GNU General Public License as published by\n";
        license += "the Free Software Foundation; either version 2 of the License, or\n";
        license += "(at your option) any later version.\n";
        license += "\n";
        license += "This program is distributed in the hope that it will be useful,\n";
        license += "but WITHOUT ANY WARRANTY; without even the implied warranty of\n";
        license += "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the\n";
        license += "GNU General Public License for more details.\n";
        license += "\n";
        license += "You should have received a copy of the GNU General Public License along\n";
        license += "ith this program; if not, write to the Free Software Foundation, Inc.,\n";
        license += "51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.";

        jpLicense.add(new JTextArea(license));

        JPanel jp_info = new JPanel(new BorderLayout());

        jp_info.add(new JLabel("<html><font size=+2>Jaint 1.0</font></html>", JLabel.CENTER), BorderLayout.NORTH);

        JPanel jp_info_center = new JPanel(new BorderLayout());

        jp_info_center.add(new JLabel("Image Processing Software", JLabel.CENTER), BorderLayout.NORTH);

        String text = "<html><strong>Developed and documented by :</strong>";
        text += "<br /><ul>";
        text += "<li>Joshua ARUS</li>";
        text += "<li>Jonas LEIS</li>";
        text += "<li>Jean-Francois SPATZ</li>";
        text += "</ul></html>";
        jp_info_center.add(new JLabel(text, JLabel.CENTER), BorderLayout.CENTER);

        jp_info.add(jp_info_center, BorderLayout.CENTER);

        jp_info.add(new JLabel("<html>L'urgent est fait, l'impossible est en cours, pour les miracles, prevoir un delai...</html>", JLabel.CENTER), BorderLayout.SOUTH);

        jtp.addTab("Info", jp_info);
        jtp.addTab("License", jpLicense);

        this.add(nord,BorderLayout.NORTH);
        this.add(centre,BorderLayout.CENTER);

        this.pack();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setIconImage(Toolkit.getDefaultToolkit().createImage("./lib/Icons/jaint.png"));//modification de l'icone sous windows
        this.setLocation((d.width-this.getWidth())/2, (d.height-this.getHeight())/2);
        this.setVisible(true);
        this.setResizable(false);
    }
}
