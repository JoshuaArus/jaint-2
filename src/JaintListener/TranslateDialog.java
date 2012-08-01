package JaintListener;


import java.util.ArrayList;
import java.util.Locale;
import java.util.Collections;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.BorderFactory;

import java.awt.BorderLayout;
import java.awt.Dimension;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;

/**
 * <code>JDialog</code> permettant la traduction du logiciel.
 *
 * @author Jeff
 * @version 0.1
 * @since JDK1.6
 */
public class TranslateDialog extends JDialog
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2218235903166499840L;

	private JPanel jp_general1, jp_general2, jp_devices1, jp_outil, jp_edition;

	private JLabel jl_new, jl_newimage, jl_open, jl_close, jl_save, jl_saveAs, jl_quit, jl_undo, jl_redo;
	private JLabel jl_plugins, jl_oplugins, jl_languages, jl_changeLang, jl_newLang, jl_help, jl_tuto, jl_faq, jl_about;
        private JLabel jl_dessin, jl_pinceau, jl_gomme, jl_pdp, jl_forme, jl_outil, jl_selection, jl_pipette, jl_couleur;
        private JLabel jl_edition, jl_couper, jl_copier, jl_coller, jl_rogner, jl_agRed;
	private JLabel jl_print, jl_webcam, jl_screenshot;

	private JTextField jtf_new, jtf_newimage, jtf_open, jtf_close, jtf_save, jtf_saveAs, jtf_quit, jtf_undo, jtf_redo;
	private JTextField jtf_plugins, jtf_oplugins, jtf_languages, jtf_changeLang, jtf_newLang, jtf_help, jtf_tuto, jtf_faq, jtf_about;
        private JTextField jtf_dessin, jtf_pinceau, jtf_gomme, jtf_pdp, jtf_forme, jtf_outil, jtf_selection, jtf_pipette, jtf_couleur;
        private JTextField jtf_edition, jtf_couper, jtf_copier, jtf_coller, jtf_rogner, jtf_agRed;
	private JTextField jtf_print, jtf_webcam, jtf_screenshot;

	private JPanel jp_languages;
	private JComboBox jcb_languages;
	private JPanel jp_flag;
	private JLabel jl_flag;

	private JPanel jp_buttons;
	private JButton jb_ok, jb_previous, jb_next;

	private String[] languages;
	private String[] languagesISO;
	private ArrayList<Locale> loc;
	private Locale[] sortedLocales;

	int pageNumber = 1;

        private String cheminFlag = "./lib/Icons/Palette/Option/flags/";
        private String cheminLang = "./lib/lang/";

	/**
	*Cree un <code>TranslateDialog</code>
	*@param parent La <code>Frame</code> qui affiche ce <code>JDialog</code>
	*@param title Le titre du <code>JDialog</code>
	*@param modal vrai pour bloquer la <code>JFrame</code> parent, faux sinon
	*/
	public TranslateDialog(JFrame parent, String title, boolean modal)
	{
		super(parent, title);
		this.setSize(540, 600);
		this.setLocationRelativeTo(null);
		this.setResizable(false);


		// LANGUAGES SELECTION

		this.jp_languages = new JPanel(new BorderLayout());
		this.jp_languages.setPreferredSize(new Dimension(300, 600));
		this.jp_languages.setBorder(BorderFactory.createTitledBorder("Select a language"));
		this.jl_flag = new JLabel(new ImageIcon(cheminFlag + "Flag_Albanian.png"));
		this.jp_flag = new JPanel(new BorderLayout());
		this.jp_flag.add(new JLabel("<html><body><br /></body></html>"), BorderLayout.NORTH);
		this.jp_flag.add(this.jl_flag);
		JTextArea jta = new JTextArea(20, 5);
		jta.setLineWrap(true);
		jta.setWrapStyleWord(true);

		jta.setText("\nTo add a new language, chose the one you want in the list, then just fill those text areas with the words or expressions you want.\n\nIf you don't know how to translate one of the terms proposed, you can let the text area empty and it will be automatically set to the english translation.\n\nIf you want to create the translation file manually :\n\n- Create a file named\n      \"<language>.lang\"\n- Fill it using this model, for each term :\n     Close (tab) your term\n     Blur (tab) your term\n- Move this file in the \"lang\" directory\n\nPlease check out the documentation for further information.");
		jta.setEditable(false);
		this.jp_flag.add(jta, BorderLayout.SOUTH);
		this.jp_languages.add(this.jp_flag);


		// LOCALES LIST

		Locale.setDefault(Locale.UK);
		loc = new ArrayList<Locale>();

		for (Locale l : Locale.getAvailableLocales())
		{
			boolean estTrouve = false;

			if (!l.getCountry().equals(""))
			{
				for (int i = 0; i < loc.size() && !estTrouve; i++)
				{
					estTrouve = loc.get(i).getDisplayLanguage().equals(l.getDisplayLanguage());
				}
				if (!estTrouve)
				{
					loc.add(l);
				}
			}
		}

		languages = new String[loc.size()];

		for (int k = 0; k < loc.size(); k++)
		{
			languages[k] = loc.get(k).getDisplayLanguage().substring(0, 1).toUpperCase() +
						loc.get(k).getDisplayLanguage().substring(1).toLowerCase();
		}

		languagesISO = new String[loc.size()];

		for (int j = 0; j < loc.size(); j++)
		{
			languagesISO[j] = loc.get(j).getLanguage();
		}

		ArrayList<String> lstSortedLanguages = new ArrayList<String>();
		for (int l = 0; l < loc.size(); l++)
		{
			lstSortedLanguages.add(languages[l]);
		}

		Collections.sort(lstSortedLanguages);

		String[] sortedLanguages = new String[loc.size()];
		for (int m = 0; m < sortedLanguages.length; m++)
		{
			sortedLanguages[m] = lstSortedLanguages.get(m);
		}

		this.sortedLocales = new Locale[loc.size()];
		for (int n = 0; n < sortedLocales.length; n++)
		{
			this.sortedLocales[n] = new Locale(sortedLanguages[n]);
		}

		this.jcb_languages = new JComboBox(sortedLanguages);
		this.jcb_languages.addActionListener(new TradListListener(this));
		this.jp_languages.add(this.jcb_languages, BorderLayout.NORTH);

		// GENERAL TERMS - PAGE 1

		this.jp_general1 = new JPanel();
		this.jp_general1.setBorder(BorderFactory.createTitledBorder("General Terms - Page 1/2"));

		
		this.jtf_new = new JTextField(20);
		this.jl_new = new JLabel("New");
		this.jp_general1.add(this.jl_new);
		this.jp_general1.add(this.jtf_new);

		this.jtf_newimage = new JTextField(20);
		this.jl_newimage = new JLabel("New image");
		this.jp_general1.add(this.jl_newimage);
		this.jp_general1.add(this.jtf_newimage);

		this.jtf_open = new JTextField(20);
		this.jl_open = new JLabel("Open");
		this.jp_general1.add(this.jl_open);
		this.jp_general1.add(this.jtf_open);

		this.jtf_close = new JTextField(20);
		this.jl_close = new JLabel("Close tab");
		this.jp_general1.add(this.jl_close);
		this.jp_general1.add(this.jtf_close);

		this.jtf_save = new JTextField(20);
		this.jl_save = new JLabel("Save");
		this.jp_general1.add(this.jl_save);
		this.jp_general1.add(this.jtf_save);

		this.jtf_saveAs = new JTextField(20);
		this.jl_saveAs = new JLabel("Save as");
		this.jp_general1.add(this.jl_saveAs);
		this.jp_general1.add(this.jtf_saveAs);

		this.jtf_quit = new JTextField(20);
		this.jl_quit = new JLabel("Quit");
		this.jp_general1.add(this.jl_quit);
		this.jp_general1.add(this.jtf_quit);
		
		this.jtf_undo = new JTextField(20);
		this.jl_undo = new JLabel("Undo");
		this.jp_general1.add(this.jl_undo);
		this.jp_general1.add(this.jtf_undo);

		this.jtf_redo = new JTextField(20);
		this.jl_redo = new JLabel("Redo");
		this.jp_general1.add(this.jl_redo);
		this.jp_general1.add(this.jtf_redo);

		// GENERAL TERMS - PAGE 2

		this.jp_general2 = new JPanel();
		this.jp_general2.setBorder(BorderFactory.createTitledBorder("General Terms - Page 2/2"));

		this.jtf_plugins = new JTextField(20);
		this.jl_plugins = new JLabel("Plugins");
		this.jp_general2.add(this.jl_plugins);
		this.jp_general2.add(this.jtf_plugins);

		this.jtf_oplugins = new JTextField(20);
		this.jl_oplugins = new JLabel("Organize plugins");
		this.jp_general2.add(this.jl_oplugins);
		this.jp_general2.add(this.jtf_oplugins);

		this.jtf_languages = new JTextField(20);
		this.jl_languages = new JLabel("Language");
		this.jp_general2.add(this.jl_languages);
		this.jp_general2.add(this.jtf_languages);

		this.jtf_changeLang = new JTextField(20);
		this.jl_changeLang = new JLabel("Choose language");
		this.jp_general2.add(this.jl_changeLang);
		this.jp_general2.add(this.jtf_changeLang);

		this.jtf_newLang = new JTextField(20);
		this.jl_newLang = new JLabel("Add language");
		this.jp_general2.add(this.jl_newLang);
		this.jp_general2.add(this.jtf_newLang);

		this.jtf_help = new JTextField(20);
		this.jl_help = new JLabel("Help");
		this.jp_general2.add(this.jl_help);
		this.jp_general2.add(this.jtf_help);

                this.jtf_tuto = new JTextField(20);
		this.jl_tuto = new JLabel("Tutorial");
		this.jp_general2.add(this.jl_tuto);
		this.jp_general2.add(this.jtf_tuto);

                this.jtf_faq = new JTextField(20);
		this.jl_faq = new JLabel("FAQ");
		this.jp_general2.add(this.jl_faq);
		this.jp_general2.add(this.jtf_faq);

		this.jtf_about = new JTextField(20);
		this.jl_about = new JLabel("About");
		this.jp_general2.add(this.jl_about);
		this.jp_general2.add(this.jtf_about);

                //DESSIN

                this.jp_outil = new JPanel();
                this.jp_outil.setBorder(BorderFactory.createTitledBorder("Tool"));

                this.jtf_dessin = new JTextField(20);
		this.jl_dessin = new JLabel("Drawing");
		this.jp_outil.add(this.jl_dessin);
		this.jp_outil.add(this.jtf_dessin);

                this.jtf_pinceau = new JTextField(20);
		this.jl_pinceau = new JLabel("Pen");
		this.jp_outil.add(this.jl_pinceau);
		this.jp_outil.add(this.jtf_pinceau);

                this.jtf_gomme = new JTextField(20);
		this.jl_gomme = new JLabel("Eraser");
		this.jp_outil.add(this.jl_gomme);
		this.jp_outil.add(this.jtf_gomme);

                this.jtf_pdp = new JTextField(20);
		this.jl_pdp = new JLabel("Tin of paint");
		this.jp_outil.add(this.jl_pdp);
		this.jp_outil.add(this.jtf_pdp);

                this.jtf_forme = new JTextField(20);
		this.jl_forme = new JLabel("Shape");
		this.jp_outil.add(this.jl_forme);
		this.jp_outil.add(this.jtf_forme);

                this.jtf_outil = new JTextField(20);
		this.jl_outil = new JLabel("Tool");
		this.jp_outil.add(this.jl_outil);
		this.jp_outil.add(this.jtf_outil);

                this.jtf_selection = new JTextField(20);
		this.jl_selection = new JLabel("Selection");
		this.jp_outil.add(this.jl_selection);
		this.jp_outil.add(this.jtf_selection);

                this.jtf_pipette = new JTextField(20);
		this.jl_pipette = new JLabel("Pipette");
		this.jp_outil.add(this.jl_pipette);
		this.jp_outil.add(this.jtf_pipette);

                this.jtf_couleur = new JTextField(20);
		this.jl_couleur = new JLabel("Color");
		this.jp_outil.add(this.jl_couleur);
		this.jp_outil.add(this.jtf_couleur);


                //EDITION

                this.jp_edition = new JPanel();
		this.jp_edition.setBorder(BorderFactory.createTitledBorder("Edition"));


                this.jtf_edition = new JTextField(20);
		this.jl_edition = new JLabel("Edit");
		this.jp_edition.add(this.jl_edition);
		this.jp_edition.add(this.jtf_edition);

                this.jtf_couper = new JTextField(20);
		this.jl_couper = new JLabel("Cut");
		this.jp_edition.add(this.jl_couper);
		this.jp_edition.add(this.jtf_couper);

                this.jtf_copier = new JTextField(20);
		this.jl_copier = new JLabel("Copy");
		this.jp_edition.add(this.jl_copier);
		this.jp_edition.add(this.jtf_copier);

                this.jtf_coller = new JTextField(20);
		this.jl_coller = new JLabel("Paste");
		this.jp_edition.add(this.jl_coller);
		this.jp_edition.add(this.jtf_coller);

                this.jtf_rogner = new JTextField(20);
		this.jl_rogner = new JLabel("Trim");
		this.jp_edition.add(this.jl_rogner);
		this.jp_edition.add(this.jtf_rogner);

                this.jtf_agRed = new JTextField(20);
		this.jl_agRed = new JLabel("Fullscreen");
		this.jp_edition.add(this.jl_agRed);
		this.jp_edition.add(this.jtf_agRed);

		//DEVICES - PAGE 1

		this.jp_devices1 = new JPanel();
		this.jp_devices1.setBorder(BorderFactory.createTitledBorder("Devices"));

		this.jtf_print = new JTextField(20);
		this.jl_print = new JLabel("Print");
		this.jp_devices1.add(this.jl_print);
		this.jp_devices1.add(this.jtf_print);

		this.jtf_webcam = new JTextField(20);
		this.jl_webcam = new JLabel("Webcam");
		this.jp_devices1.add(this.jl_webcam);
		this.jp_devices1.add(this.jtf_webcam);

		this.jtf_screenshot = new JTextField(20);
		this.jl_screenshot = new JLabel("Screenshot");
		this.jp_devices1.add(this.jl_screenshot);
		this.jp_devices1.add(this.jtf_screenshot);

		this.jp_general1.setVisible(true);
		this.jp_general2.setVisible(false);
		this.jp_devices1.setVisible(false);

		this.add(this.jp_general1);
		this.add(this.jp_general2);
		this.add(this.jp_devices1);

		//BUTTONS

		this.jp_buttons = new JPanel();

		this.jb_previous = new JButton("Previous");
		this.jb_previous.addActionListener(new TradPrevListener(this));
		this.jb_previous.setVisible(true);
		this.jb_previous.setEnabled(false);

		this.jb_next = new JButton("Next");
		this.jb_next.addActionListener(new TradNextListener(this));

		this.jb_ok = new JButton("Ok");
		this.jb_ok.addActionListener(new TradOkListener(this));
		this.jb_ok.setVisible(true);
		this.jb_ok.setEnabled(false);

		this.jp_buttons.add(this.jb_previous);
		this.jp_buttons.add(this.jb_next);
		this.jp_buttons.add(this.jb_ok);


		this.add(this.jp_languages, BorderLayout.WEST);
		this.add(this.jp_general1);

		this.add(this.jp_buttons, BorderLayout.SOUTH);
		try
		{
		this.savedLanguage();
		}
		catch(Exception e)
		{
		}
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	/**
	* Renvoie un tableau de <code>String</code> correspondant au code de recherche dans un fichier .lang
	*<p>
	*N.B. Si le JTextField est vide, le <code>String</code> du tableau sera remplace par le texte du label.
	*@return Le tableau <code>String</code>
	*/
	public String[] getComponentsStrings()
	{

		JLabel[] jl = {jl_new, jl_newimage, jl_open, jl_close, jl_save, jl_saveAs, jl_quit, jl_undo, jl_redo, jl_plugins, jl_oplugins, jl_languages, jl_changeLang, jl_newLang, jl_help, jl_tuto, jl_faq, jl_about, jl_dessin, jl_pinceau, jl_gomme, jl_pdp, jl_forme, jl_outil, jl_selection, jl_pipette, jl_couleur, jl_edition, jl_couper, jl_copier, jl_coller, jl_rogner, jl_agRed, jl_print, jl_webcam, jl_screenshot};
		JTextField[] jtf = {jtf_new, jtf_newimage, jtf_open, jtf_close, jtf_save, jtf_saveAs, jtf_quit, jtf_undo, jtf_redo, jtf_plugins, jtf_oplugins, jtf_languages, jtf_changeLang, jtf_newLang, jtf_help, jtf_tuto, jtf_faq, jtf_about, jtf_dessin, jtf_pinceau, jtf_gomme, jtf_pdp, jtf_forme, jtf_outil, jtf_selection, jtf_pipette, jtf_couleur, jtf_edition, jtf_couper, jtf_copier, jtf_coller, jtf_rogner, jtf_agRed, jtf_print, jtf_webcam, jtf_screenshot};

		String[] s = new String[jl.length];

		for (int i = 0; i < s.length; i++)
		{
			if (jtf[i].getText().equals(""))
			{
				s[i] = jl[i].getText().replaceAll(" ", "") + "\t" + jl[i].getText() + "\n";
			}
			else
			{
				s[i] = jl[i].getText().replaceAll(" ", "") + "\t" + jtf[i].getText() + "\n";
			}
		}

		return s;
	}

	/**
	*Cree la langue avec la traduction entree
	*<p>
	*N.B. Le fichier sera cree dans le repertoire "lang"
         *@param s Tableau de <code>String</code> containing les codes de traduction
         * @throws java.io.IOException
	*/
	public void ok(String[] s) throws java.io.IOException
	{
		int i = this.jcb_languages.getSelectedIndex();

		String fileName = cheminLang+this.sortedLocales[i].getLanguage()+".lang";
		File file = new File(fileName);
		file.createNewFile();

		BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));

		for (int j = 0; j < s.length; j++)
		{
			bw.write(s[j]);
			bw.flush();
		}
		bw.close();
		this.dispose();
	}

	/**
	* Remplie les JTextFields si la langue a deja ete creee.
         *
         * @throws java.io.IOException
         */
	public void savedLanguage() throws java.io.IOException
	{
		File directory = new File(cheminLang);
		File[] files = directory.listFiles();

		JTextField[] jtf = {jtf_new, jtf_newimage, jtf_open, jtf_close, jtf_save, jtf_saveAs, jtf_quit, jtf_undo, jtf_redo, jtf_plugins, jtf_oplugins, jtf_languages, jtf_changeLang, jtf_newLang, jtf_help, jtf_tuto, jtf_faq, jtf_about, jtf_dessin, jtf_pinceau, jtf_gomme, jtf_pdp, jtf_forme, jtf_outil, jtf_selection, jtf_pipette, jtf_couleur, jtf_edition, jtf_couper, jtf_copier, jtf_coller, jtf_rogner, jtf_agRed, jtf_print, jtf_webcam, jtf_screenshot};

		String lang = this.jcb_languages.getSelectedItem().toString().toLowerCase() + ".lang";
		ArrayList<String> trad;
		String[] elem;
		String line;

		boolean found = false;

		int i = 0;

		while (i < files.length && !found)
		{
			trad = new ArrayList<String>();
			if (files[i].getName().equals(lang))
			{

				BufferedReader br = new BufferedReader(new FileReader(files[i]));

					while((line = br.readLine()) != null)
					{
						elem = line.split("\t");
						trad.add(elem[1]);
					}

				br.close();

				for (int k = 0; k < trad.size(); k++)
				{
					jtf[k].setText(trad.get(k));
				}
				found = true;
			}
			else
			{
				for (int l = 0; l < jtf.length; l++)
				{
					jtf[l].setText("");
				}
			}
			i++;
		}

	}

	/**
	*change l'icone du drapeau en fonction de la langue selectionnee
	*/
	public void flagChange()
	{
		String imgName = cheminFlag+ "Flag_" + (String)this.jcb_languages.getSelectedItem() + ".png";
		this.jl_flag.setIcon(new ImageIcon(imgName));
	}

	/**
	* change la liste de termes a traduire avec la precedente
	*/
	public void previous()
	{
		this.pageNumber--;

		if (this.pageNumber == 1)
		{
			this.jb_previous.setEnabled(false);
			this.jb_next.setEnabled(true);
			this.jb_ok.setEnabled(false);
			this.jp_general1.setVisible(true);
			this.jp_general2.setVisible(false);
			this.jcb_languages.setEnabled(true);
		}
		else if (this.pageNumber == 2)
		{
			this.jb_previous.setEnabled(true);
			this.jb_next.setEnabled(true);
			this.jb_ok.setEnabled(false);
                        this.jp_outil.setVisible(false);
			this.jp_general2.setVisible(true);
			this.jcb_languages.setEnabled(false);
		}
		else if (this.pageNumber == 3)
		{
			this.jb_previous.setEnabled(true);
			this.jb_next.setEnabled(true);
			this.jb_ok.setEnabled(false);
			this.jcb_languages.setEnabled(false);
                        this.jp_edition.setVisible(false);
                        this.jp_outil.setVisible(true);
		}
		else if (this.pageNumber == 4)
		{
			this.jb_previous.setEnabled(true);
			this.jb_ok.setEnabled(false);
			this.jp_devices1.setVisible(false);
			this.jcb_languages.setEnabled(false);
                        this.jp_edition.setVisible(true);
		}
	}

	/**
	*change la liste de termes a traduire avec la suivante
	*/
	public void next()
	{
		this.pageNumber++;

		if (this.pageNumber == 7)
		{
			this.jb_next.setEnabled(false);
		}

		if (this.pageNumber == 2)
		{
			this.jb_previous.setEnabled(true);
			this.jb_ok.setEnabled(false);
			this.jp_general1.setVisible(false);
			this.add(this.jp_general2);
			this.jp_general2.setVisible(true);
			this.jcb_languages.setEnabled(false);
		}
		else if (this.pageNumber == 3)
		{
			this.jb_previous.setEnabled(true);
			this.jb_ok.setEnabled(false);
			this.jp_general2.setVisible(false);
			this.jcb_languages.setEnabled(false);
                        this.add(this.jp_outil);
                        this.jp_outil.setVisible(true);
		}
		else if (this.pageNumber == 4)
		{
			this.jb_previous.setEnabled(true);
			this.jb_ok.setEnabled(false);
			this.jcb_languages.setEnabled(false);
                        this.jp_outil.setVisible(false);
                        this.add(this.jp_edition);
                        this.jp_edition.setVisible(true);

		}
		else if (this.pageNumber == 5)
		{
			this.jb_previous.setEnabled(true);
			this.jb_next.setEnabled(false);
			this.jb_ok.setEnabled(true);
			this.add(this.jp_devices1);
			this.jp_devices1.setVisible(true);
			this.jcb_languages.setEnabled(false);
                        this.jp_edition.setVisible(false);
		}
	}
}
