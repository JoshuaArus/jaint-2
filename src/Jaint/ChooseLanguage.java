package Jaint;

/**
 *
 * @author Jeff
 */


import JaintListener.ChooseLangCancelListener;
import JaintListener.ChooseLangOkListener;
import java.io.IOException;
import java.util.Locale;

import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.BorderLayout;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;


/**
 * <code>JDialog</code> permettant le choix de la langue parmi celles existantes.
 */
public class ChooseLanguage extends JDialog
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3902852276888425002L;
	private JPanel jp;
	private JPanel jp_button;
	private JLabel jl;
	private JComboBox jcb;
	private JButton jb_ok;
	private JButton jb_cancel;
	private String[] languagesISO;
	private IHM ihm;
        private String cheminLang = "./lib/lang/";

        /**
         * Construit le selecteur de langue
         * @param ih
         */
        public ChooseLanguage(IHM ih)
	{
		super(ih, "Langue", true);
		this.ihm = ih;
		this.jp = new JPanel(new BorderLayout());
		this.jp_button = new JPanel();
		this.jl = new JLabel("Choisissez une langue : ");
		this.jb_ok = new JButton("Ok");
		this.jb_ok.addActionListener(new ChooseLangOkListener(this));
		this.jb_cancel = new JButton("Cancel");
		this.jb_cancel.addActionListener(new ChooseLangCancelListener(this));

		File directory = new File(cheminLang);
		File[] subfiles = directory.listFiles();

		int j = 0;

		for(int i = 0; i < subfiles.length; i++)
		{
			if (subfiles[i].getName().endsWith(".lang"))
			{
				j++;
			}
		}

		int k = 0;
		String[] propertiesFiles = new String[j];

		for (int i = 0; i < subfiles.length; i++)
		{
			if (subfiles[i].getName().endsWith(".lang"))
			{
				propertiesFiles[k] = subfiles[i].getName();
				k++;
			}
		}

		languagesISO = new String[propertiesFiles.length];
		Locale[] locales = new Locale[propertiesFiles.length];

		for (int i = 0; i < propertiesFiles.length; i++)
		{
				languagesISO[i] = propertiesFiles[i].substring(0, propertiesFiles[i].length() - 5);
				locales[i] = new Locale(languagesISO[i]);
		}

		String[] languages = new String[locales.length];

		for (int i = 0; i < locales.length; i++)
		{
				languages[i] = locales[i].getDisplayLanguage().substring(0, 1).toUpperCase() + locales[i].getDisplayLanguage().substring(1).toLowerCase();
		}

		this.jcb = new JComboBox(languages);

		this.jcb.add(this.jl);
		this.jp.add(this.jcb);
		this.jp_button.add(this.jb_ok);
		this.jp_button.add(this.jb_cancel);
		this.jp.add(this.jp_button, BorderLayout.SOUTH);
		this.add(this.jp);
		this.pack();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screen.width - this.getSize().width)/2, (screen.height - this.getSize().height)/2);
		this.setResizable(false);
		this.setVisible(true);
	}

        /**
         * Change la langue par defaut du logiciel
         */
        public void languageOk()
	{
		if (this.jcb.getSelectedItem() != null)
		{
			String loc = this.languagesISO[this.jcb.getSelectedIndex()];
			this.ihm.changeLang(loc);

                        try
                        {
                            BufferedWriter bw = new BufferedWriter(new FileWriter("./lib/lang.config", false));

                            bw.write(loc);
                            bw.close();
                        }
                        catch (IOException ex)
                        {

                        }


		}
	}
}

