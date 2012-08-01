package Jaint;

import JaintListener.EcouteurClavier;
import JaintListener.BtnEDPlugin;
import JaintListener.SaveAsListener;
import JaintListener.EcouteurPot;
import JaintListener.SaveListener;
import JaintListener.EDPluginListener;
import JaintListener.EcouteurPinceau;
import JaintListener.EcouteurPalette;
import JaintListener.JaintWindowListener;
import JaintListener.RemovePluginListener;
import JaintListener.EcouteurColler;
import JaintListener.EcouteurCopier;
import JaintListener.EcouteurCouper;
import JaintListener.EcouteurForm;
import JaintListener.AddPluginListener;
import JaintListener.DropImageListener;
import JaintListener.EcouteurPipette;
import JaintListener.GestPlugListener;
import JaintListener.EcouteurGomme;
import JaintListener.EcouteurSelectionCouleur;
import JaintListener.EcouteurMAJIconPlug;
import JaintListener.BtnPluginListener;
import JaintListener.EcouteurRogner;
import JaintListener.ChooseLangListener;
import JaintListener.TradLangListener;
//import jaint.Webcam;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;

import java.awt.dnd.DropTarget;
import java.util.ArrayList;

import java.util.Vector;
import javax.swing.table.*;
import javax.swing.filechooser.*;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.PropertyResourceBundle;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.UnsupportedEncodingException;

/**
 * Interface principale du logiciel, contient le <code>JTabbedPane</code>
 * 
 * @author Joshua & Jonas & Jeff
 */
public class IHM extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -987614679699885631L;

	/**
	 * Element principal de l'IHM, contient les onglets et la zone principale
	 */
	public JTabbedPane jtp = new JTabbedPane(JTabbedPane.RIGHT,
			JTabbedPane.SCROLL_TAB_LAYOUT);

	/**
	 * Référence vers l'instance de <code>Jaint</code> contenant certaines
	 * données
	 */
	public Jaint jaint;
	/**
	 * Palette de dessin
	 */
	public JaintPalette palette = new JaintPalette(30, "./lib/Icons/jaint2.jpg", this);

	/**
	 * Palette d'option
	 */
	public JaintPalette2 paletteOption = new JaintPalette2(30, "./lib/Icons/jaint2.jpg", "./lib/Icons/versPaletteGestion.png", this, JaintPalette2.OPTION);
	/**
	 * Palette de gestion de fichier
	 */
	public JaintPalette2 paletteGestion = new JaintPalette2(30, "./lib/Icons/jaint2.jpg", "./lib/Icons/versPaletteOption.png", this, JaintPalette2.GESTION);
	EcouteurPalette ep;
	EcouteurPalette epG;
	EcouteurPalette epO;
	PluginsLoader pl;
	/**
	 * Instance contenant les fonctions de dessin pour dessiner sur l'image
	 */
	public FonctionDessin fd;

	// DND
	DropTarget target;

	// JDialog gestionnaire de plugin
	private JDialog jd_listPlugins;
	private JScrollPane jsp_listPlugins;
	private NonEditableTable jt_listPlugins;
	private Vector<String> pluginsColumn;
	private DefaultTableModel tb_listPlugins;
	private JPanel jp_butPlugins;
	private JButton jb_addPlugin;
	private JButton jb_removePlugin;
	private JButton jb_edPlugin;
	private JFileChooser jfc = new JFileChooser();

	private int nbScreenshot = 0;
	private AideDemarrage aideDebut;
	private JWindow splashScreen;
	private JProgressBar jpb = new JProgressBar();

	// vaut vrai si on a lancé le tutoriel
	private boolean lu = false;

	private JPaletteItem outil = new JPaletteItem(
			"./lib/Icons/Dessin/outil.png");
	private JPaletteItem dessin = new JPaletteItem(
			"./lib/Icons/Palette/Dessin/dessin.png");
	private JPaletteItem undo = new JPaletteItem("./lib/Icons/undo.png");
	private JPaletteItem couperCopierColler = new JPaletteItem(
			"./lib/Icons/Palette/Dessin/outil.png");
	private JPaletteItem btnPlug = new JPaletteItem(
			"./lib/Icons/Plugins/plugin.png");
	private JPaletteItem redo = new JPaletteItem("./lib/Icons/redo.png");

	private JPaletteItem btnOuvrir = new JPaletteItem("./lib/Icons/open.png");
	private JPaletteItem btnNouveau = new JPaletteItem(
			"./lib/Icons/Palette/Gestion/nouveau.png");
	private JPaletteItem btnSaveAs = new JPaletteItem("./lib/Icons/saveas.png");
	private JPaletteItem btnFermer = new JPaletteItem(
			"./lib/Icons/Palette/Gestion/fermer.png");
	private JPaletteItem btnQuitter = new JPaletteItem(
			"./lib/Icons/Palette/Gestion/quitter.png");
	private JPaletteItem btnSave = new JPaletteItem("./lib/Icons/save.png");

	private JPaletteItem gestionPlugin = new JPaletteItem(
			"./lib/Icons/Palette/Option/gestionnairePlugin.png");
	private JPaletteItem gestionLangue = new JPaletteItem(
			"./lib/Icons/Palette/Option/flags/Flag_French.png");
	private JPaletteItem pleinEcran = new JPaletteItem(
			"./lib/Icons/Palette/Option/agrandir.png");
	private JPaletteItem aide = new JPaletteItem(
			"./lib/Icons/Palette/Option/aide.png");
	private JPaletteItem apropos = new JPaletteItem(
			"./lib/Icons/Palette/Option/aPropos.png");
	private JPaletteItem print = new JPaletteItem(
			"./lib/Icons/Palette/Option/imprimante.png");

	private SubmenuItem couleur = new SubmenuItem(
			"./lib/Icons/Palette/Dessin/couleur.png");
	private SubmenuItem pipette = new SubmenuItem(
			"./lib/Icons/Palette/Dessin/pipette.png");
	private SubmenuItem fleche = new SubmenuItem(
			"./lib/Icons/Palette/Dessin/souris.png");

	private SubmenuItem forme = new SubmenuItem(
			"./lib/Icons/Dessin/sousMenu/forme.png");
	private SubmenuItem potPeinture = new SubmenuItem(
			"./lib/Icons/Palette/Dessin/pot.png");
	private SubmenuItem gomme = new SubmenuItem(
			"./lib/Icons/Palette/Dessin/gomme.png");
	private SubmenuItem pinceau = new SubmenuItem(
			"./lib/Icons/Palette/Dessin/pinceau.png");

	private SubmenuItem couper = new SubmenuItem(
			"./lib/Icons/Palette/Dessin/couper.png");
	private SubmenuItem copier = new SubmenuItem(
			"./lib/Icons/Palette/Dessin/copier.png");
	private SubmenuItem coller = new SubmenuItem(
			"./lib/Icons/Palette/Dessin/coller.png");
	private SubmenuItem rogner = new SubmenuItem(
			"./lib/Icons/Palette/Dessin/rogner.png");

	private SubmenuItem nouveauFichier = new SubmenuItem(
			"./lib/Icons/Palette/Gestion/fichier.png");
	private SubmenuItem capture = new SubmenuItem(
			"./lib/Icons/Palette/Gestion/capture.png");
	private SubmenuItem webcam = new SubmenuItem(
			"./lib/Icons/Palette/Gestion/webcam.png");
	private SubmenuItem tuto = new SubmenuItem(
			"./lib/Icons/Palette/Option/tuto.png");
	private SubmenuItem questions = new SubmenuItem(
			"./lib/Icons/Palette/Option/faq.png");
	private SubmenuItem ajoutLangue = new SubmenuItem(
			"./lib/Icons/Palette/Option/ajoutLangue.png");
	private SubmenuItem choixLangue = new SubmenuItem(
			"./lib/Icons/Palette/Option/changeLangue.png");
	private SubmenuItem btnApplyPlug;

	private boolean aideLuDebut = false;

	/**
	 * Renvoi l'instance de l'IHM crée
	 * 
	 * @param j
	 *            Instance de Jaint contenant le reste des données
	 * @param p
	 *            Le pluginsLoader permettant de charger les plugins dans leur
	 *            dossier dès le démarrage
	 */
	public IHM(Jaint j, PluginsLoader p) {
		super("Jaint 1.0");

		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

		// SplashScreen
		jpb.setValue(0);
		jpb.setStringPainted(true);
		jpb.setString("Chargement du logiciel - 0%");
		splashScreen = new JWindow(this);
		splashScreen.getContentPane().add(
				new JLabel(new ImageIcon("./lib/SplashScreen.png")),
				BorderLayout.CENTER);
		splashScreen.getContentPane().add(jpb, BorderLayout.SOUTH);
		splashScreen.pack();
		int x = (int) (d.getWidth() - splashScreen.getWidth()) / 2;
		int y = (int) (d.getHeight() - splashScreen.getHeight()) / 2;
		splashScreen.setLocation(x, y);
		splashScreen.setVisible(true);

		sleep(100);

		try {
			DataInputStream dis = new DataInputStream(new FileInputStream(
					"./lib/tuto.config"));
			try {
				if (dis.readBoolean()) {
					aideLuDebut = true;
					lu = true;
					aideDebut = new AideDemarrage(this);
				}

				dis.close();
			} catch (IOException ex) {
				try {
					DataOutputStream ds = new DataOutputStream(
							new FileOutputStream("./lib/tuto.config", false));
					ds.writeBoolean(true);
					ds.close();
				} catch (IOException ex2) {

				}
			}
		} catch (FileNotFoundException ex) {

		}

		jpb.setValue(5);
		jpb.setString("Chargement des fonctions de dessin - 5%");
		sleep(100);

		this.jaint = j;
		this.pl = p;

		fd = new FonctionDessin(this);

		jpb.setValue(10);
		jpb.setString("Chargement des éléments - 10%");
		sleep(100);

		ep = new EcouteurPalette(palette, this);
		epG = new EcouteurPalette(paletteGestion, this);
		epO = new EcouteurPalette(paletteOption, this);

		jpb.setValue(15);
		jpb.setString("Chargement des éléments - 15%");
		sleep(100);

		paletteGestion.addPalette(paletteOption);

		jpb.setValue(20);
		jpb.setString("Chargement des éléments - 20%");
		sleep(100);

		paletteOption.addPalette(paletteGestion);

		jpb.setValue(25);
		jpb.setString("Chargement de l'interface principale - 25%");
		sleep(100);

		jtp.addChangeListener(new EcouteurMAJIconPlug(this));

		jpb.setValue(30);
		jpb.setString("Chargement des éléments connexes - 30%");
		sleep(100);

		// Filtres de files pour le JFileChooser
		jfc.setAcceptAllFileFilterUsed(false);
		jfc.addChoosableFileFilter(new FileNameExtensionFilter(
				"Class Files (.class)", "class"));

		// Paramètrages divers
		jfc.setMultiSelectionEnabled(false);
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);

		// DND
		this.target = new DropTarget(this, new DropImageListener(this, j));
		this.target.setActive(true);
		this.add(jtp);

		jpb.setValue(40);
		jpb.setString("Chargement de la palette principale - 40%");
		sleep(400);

		this.addWindowListener(new JaintWindowListener(this));// ecouteur qui
																// gère la
																// fermeture du
																// logiciel

		initPaletteDessin();

		jpb.setValue(70);
		jpb.setString("Chargement de la palette de gestion - 70%");
		sleep(200);

		initPaletteGestion();

		jpb.setValue(80);
		jpb.setString("Chargement de la palette d'option - 80%");
		sleep(200);

		initPaletteOption();

		jpb.setValue(90);
		jpb.setString("Chargement des plugins - 90%");
		sleep(100);

		// mise en place de la langue

		initLanguage();

		// Creation de la fenetre du gestionnaire de plugins
		this.initPluginDialog();

		jpb.setValue(100);
		jpb.setString("Chargement de la fenêtre principale - 100%");

		// configuration de la fenetre
		setPreferredSize(new Dimension((int) d.getWidth() / 2,
				(int) d.getHeight() / 2));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().createImage(
				"./lib/Icons/jaint.png"));// modification de l'icone sous
											// windows
		pack();// calcul de la position des éléments
		setExtendedState(JFrame.MAXIMIZED_BOTH);// maximisation de la fenetre
		splashScreen.dispose();
		setVisible(true);

		// affichage de l'aide de démarrage si la case du checkbox était
		// cochée
		if (lu) {
			aideDebut.initPos();
		}

	}

	/**
	 * Charge un plugin et l'ajoute dans la liste des plugins
	 */
	public void addPlugin() {
		int returnVal = jfc.showOpenDialog(null);
		File fich = jfc.getSelectedFile();

		if (returnVal == JFileChooser.APPROVE_OPTION) {

			if (fich.getName().toLowerCase().endsWith(".class")) {
				try {
					this.jaint.addPlugin(fich);
					this.initPluginsMenu();
				} catch (WrongPluginException we) {
					JOptionPane
							.showMessageDialog(
									this,
									"Le plugin "
											+ we.getPlugin()
											+ " est invalide. Il ne peut pas être importé dans Jaint",
									"Plugin invalide",
									JOptionPane.ERROR_MESSAGE);
				}
			} else {
				addPlugin(); // On réouvre le fileChooser
			}
		}
	}

	/**
	 * Ajout un nouvel onglet à partir du menu de la palette ou du drag and
	 * drop
	 * 
	 * @param s
	 *            Le chemin d'accès de l'image à ouvrir
	 */
	public void addTab(String s) {

		this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		try
		{
			Pictimage picture = new Pictimage(s);
			jaint.lst_pos.add(0);
			
			ArrayList<Pictimage> lst = new ArrayList<Pictimage>();
			lst.add(new Pictimage(picture.getBufferedImage(), picture.getPath()));
	
			jaint.lst_pict.add(lst);
	
			MainComponent main = new MainComponent(picture, this);// création du
																	// composant
																	// principal =
																	// l'image
																	// centrale (pas
																	// onglet)
			TabComponent tab = new TabComponent(picture, trunk(name(s)), this);// création
																				// de
																				// l'onglet
	
			jtp.add(main);// on l'ajoute a un onglet vide
			jtp.setSelectedIndex(jtp.getTabCount() - 1);// on se place sur cet
														// onglet (le dernier
														// rajouté)
	
			jtp.setTabComponentAt(jtp.getSelectedIndex(), tab);// on affecte a
																// l'onglet le
																// composant privé
																// qui permet
																// d'afficher ce
																// qu'on veut
			jtp.setToolTipTextAt(jtp.getTabCount() - 1, name(s));// on met un petit
																	// tooltipText
																	// joli pour les
																	// noms trop
																	// longs :)
	
			main.picture.addMouseListener(ep);
			main.picture.addMouseListener(epG);
			main.picture.addMouseListener(epO);
	
			this.initPluginsMenu();
			jtp.removeMouseListener(ep);
			jtp.removeMouseListener(epG);
			jtp.removeMouseListener(epO);
			main.initListener();
	
			this.setVisible(true);
		}
		catch(Exception e)
		{
		}
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * Ajoute un nouvel onglet à partir d'une image pré-existante
	 * 
	 * @param img
	 *            L'image à rajouter
	 * @param type
	 *            Le type d'image rajouté (1 pour "Screenshot", 2 pour
	 *            "Image vide", 3 pour la "webcam", 4 pour la fonction "rogner)
	 */
	public void addTab(BufferedImage img, int type) {
		this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		jaint.lst_pos.add(0);

		Pictimage picture = new Pictimage(img, "");
		ArrayList<Pictimage> lst = new ArrayList<Pictimage>();

		lst.add(new Pictimage(picture.getBufferedImage(), picture.getPath()));

		jaint.lst_pict.add(lst);

		MainComponent main = new MainComponent(picture, this);// création du
																// composant
																// principal =
																// l'image
																// centrale (pas
																// onglet)
		TabComponent tab;
		String chaine;
		if (type == 1)// si c'est un screenshot
		{
			nbScreenshot++;
			tab = new TabComponent(picture, "Screenshot_" + nbScreenshot
					+ "  *", this);// création de l'onglet

			chaine = "Screenshot_" + nbScreenshot;
		} else if (type == 2)// si c'est un nouveau fichier vide
		{
			nbScreenshot++;
			tab = new TabComponent(picture,
					"New_Blank_" + nbScreenshot + "  *", this);// création de
																// l'onglet

			chaine = "New_Blank_" + nbScreenshot;
		} else if (type == 3)// si c'est une photo prise avec la webcam
		{
			nbScreenshot++;
			tab = new TabComponent(picture, "New_Webcam_" + nbScreenshot
					+ "  *", this);// création de l'onglet

			chaine = "New_Webcam_" + nbScreenshot;
		} else if (type == 4) {
			nbScreenshot++;
			String previous = jtp.getToolTipTextAt(jtp.getSelectedIndex());
			if (previous.endsWith("  *"))
				previous = previous.substring(0, previous.length() - 4);

			previous = previous + "_" + nbScreenshot + "  *";
			tab = new TabComponent(picture, previous, this);// création de
															// l'onglet

			chaine = jtp.getToolTipTextAt(jtp.getSelectedIndex())
					+ nbScreenshot;
		} else {
			nbScreenshot++;
			String nom = jtp.getToolTipTextAt(jtp.getSelectedIndex());
			tab = new TabComponent(picture, "New " + trunk(nom) + "  *", this);// création
																				// de
																				// l'onglet

			chaine = "New " + nom;
		}

		jtp.add(main);// on l'ajoute a un onglet vide
		jtp.setSelectedIndex(jtp.getTabCount() - 1);// on se place sur cet
													// onglet (le dernier
													// rajouté)
		jtp.setTabComponentAt(jtp.getSelectedIndex(), tab);// on affecte a
															// l'onglet le
															// composant privé
															// qui permet
															// d'afficher ce
															// qu'on veut
		jtp.setToolTipTextAt(jtp.getTabCount() - 1, chaine);// on met un petit
															// tooltipText joli
															// pour les noms
															// trop longs :)
		tab.setPreferredSize(tab.getPreferredSize());

		main.picture.addMouseListener(ep);
		main.picture.addMouseListener(epG);
		main.picture.addMouseListener(epO);

		this.initPluginsMenu();
		jtp.removeMouseListener(ep);
		jtp.removeMouseListener(epG);
		jtp.removeMouseListener(epO);
		main.initListener();

		this.setVisible(false);
		this.setVisible(true);
		try {
			Thread.sleep(200);
		} catch (InterruptedException ex) {
			Logger.getLogger(IHM.class.getName()).log(Level.SEVERE, null, ex);
		}
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * Fermer l'onglet au rang donné
	 * 
	 * @param i
	 *            Rang de l'onglet à fermer (commence à 0)
	 */
	public void close(int i) {
		JOptionPane jop = new JOptionPane();

		@SuppressWarnings("static-access")
		int res = jop.showConfirmDialog(this,
				"Voulez vous fermer cet onglet ?", "Message de confirmation",
				JOptionPane.YES_NO_OPTION);

		if (res == JOptionPane.YES_OPTION) {
			String s = ((TabComponent) jtp.getTabComponentAt(i)).getLabel();

			if (s.endsWith(" *")) {
				@SuppressWarnings("static-access")
				int res2 = jop.showConfirmDialog(this,
						"Voulez vous enregistrer votre travail ?",
						"Message de confirmation",
						JOptionPane.YES_NO_CANCEL_OPTION);

				if (res2 == JOptionPane.YES_OPTION) {
					ArrayList<Pictimage> lst = jaint.lst_pict.get(i);
					String path = lst.get(lst.size() - 1).getPath();

					if (path == null)
						jaint.enregistrerSous(i);
					else
						jaint.enregistrer(i, new File(path));
				}
				if (res2 != JOptionPane.CANCEL_OPTION) {
					jaint.lst_pict.remove(i);
					jaint.lst_pos.remove(i);
					jtp.remove(i);
				}
			} else {
				jaint.lst_pict.remove(i);
				jaint.lst_pos.remove(i);
				jtp.remove(i);
			}
		}

		if (jtp.getSelectedIndex() == -1) {
			jtp.addMouseListener(ep);
			jtp.addMouseListener(epG);
			jtp.addMouseListener(epO);
		}

	}

	/**
	 * Si le plugin est activé, on le désactive, si il est désactivé on
	 * l'active
	 */
	public void edPlugin() {
		int nbRows = this.jaint.getPluginsLoader().getNbPlugins();
		Plugin plug;

		for (int i = 0; i < nbRows; i++) {
			if (this.jt_listPlugins.isRowSelected(i)) {
				// Si le plugin est activé on le désactive, et on le retire de
				// la liste
				if (this.jaint.getPluginsLoader().getPlugins().get(i)
						.isEnabled()) {
					// On modifie l'attribue estActivé des plugins estActive :
					// false
					this.jaint.getPluginsLoader().getPlugins().get(i)
							.setEnabled(false);

					// Le plugin est automatiquement retiré de la liste car
					// lors de l'apelle des initPlugsMenu()
					// on ajoute à la liste uniquement les plugins qui activé
					// (estAcrive : true)

				} else if (!this.jaint.getPluginsLoader().getPlugins().get(i)
						.isEnabled())// Si le plugin est désactivé
				{
					// On modifie l'attribue estActive
					this.jaint.getPluginsLoader().getPlugins().get(i)
							.setEnabled(true);

				}

			}
		}

		// On efface tous les plugins de la liste des plugins actifs
		pl.removeAllListPluginActif();

		// On ajoute à cette liste uniquement les plugins qui sont activés
		for (int i = 0; i < this.jaint.getPluginsLoader().getPlugins().size(); i++) {
			plug = this.jaint.getPluginsLoader().getPlugins().get(i);
			if (plug.isEnabled()) {
				pl.addListPluginActif(plug);
			}

		}

		this.initPluginsMenu();

	}

	/**
     *
     */
	public void gestPlugins() {
		this.jd_listPlugins.pack();
		this.jd_listPlugins.setVisible(true);
	}

	private void initPaletteDessin() {
		palette.add(outil);
		palette.add(dessin);
		palette.add(undo);
		undo.setEnabled(false);
		palette.add(couperCopierColler);
		palette.add(btnPlug);
		palette.add(redo);
		redo.setEnabled(false);

		couleur.addActionListener(new EcouteurSelectionCouleur(palette));

		outil.addSubmenu();

		outil.add(couleur);
		outil.add(pipette);
		outil.add(fleche);

		dessin.addSubmenu();

		dessin.add(forme);
		dessin.add(potPeinture);
		dessin.add(gomme);
		dessin.add(pinceau);

		forme.addActionListener(new EcouteurForm(palette));
		potPeinture.addActionListener(new EcouteurPot(palette));
		gomme.addActionListener(new EcouteurGomme(palette));
		pinceau.addActionListener(new EcouteurPinceau(palette));

		pipette.addActionListener(new EcouteurPipette(this));

		fleche.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				palette.setCenter(JaintPalette.CHOIX_DEFAUT);
			}

		});

		undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jaint.undo();
			}
		});

		btnPlug.addSubmenu();

		redo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jaint.redo();
			}
		});

		couperCopierColler.addSubmenu();

		couper.addActionListener(new EcouteurCouper(this));
		copier.addActionListener(new EcouteurCopier(this));
		coller.addActionListener(new EcouteurColler(this));
		rogner.addActionListener(new EcouteurRogner(this));

		couperCopierColler.add(couper);
		couperCopierColler.add(copier);
		couperCopierColler.add(coller);
		couperCopierColler.add(rogner);

		jtp.addKeyListener(new EcouteurClavier(this));

		jtp.addMouseListener(ep);

	}

	private void initPaletteGestion() {
		paletteGestion.setBtnColor(new Color(165, 255, 114));

		paletteGestion.add(btnOuvrir);
		paletteGestion.add(btnNouveau);
		paletteGestion.add(btnSaveAs);
		paletteGestion.add(btnFermer);
		paletteGestion.add(btnQuitter);
		paletteGestion.add(btnSave);

		btnNouveau.addSubmenu();

		btnNouveau.add(webcam);
		btnNouveau.add(capture);
		btnNouveau.add(nouveauFichier);

		btnOuvrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jaint.ouvrir();
			}
		});

		btnSave.addActionListener(new SaveListener(this));
		btnSaveAs.addActionListener(new SaveAsListener(this));

		btnQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				jaint.quitter();
			}

		});

		btnFermer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (jtp.getTabRunCount() > 0)
					close(jtp.getSelectedIndex());
			}
		});

		capture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				screenshot();
			}

		});

		// webcam.addActionListener(new ActionListener()
		// {
		// public void actionPerformed(ActionEvent ae)
		// {
		// new Webcam(IHM.this);
		// }
		//
		// });

		nouveauFichier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				nouveauFichier();
			}

		});

		jtp.addMouseListener(epG);

	}

	private void initPaletteOption() {
		paletteOption.setBtnColor(new Color(255, 76, 116));

		paletteOption.add(gestionPlugin);
		paletteOption.add(gestionLangue);
		paletteOption.add(pleinEcran);
		paletteOption.add(aide);
		paletteOption.add(apropos);
		paletteOption.add(print);

		pleinEcran.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				pleinEcran();
			}

		});

		gestionPlugin.addActionListener(new GestPlugListener(this));

		print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Imprimante impr = new Imprimante(IHM.this, jaint
						.getImageCourante());
				impr.applyPrint();
			}

		});

		apropos.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				new APropos(IHM.this);
			}
		});

		aide.addSubmenu();
		aide.add(tuto);
		aide.add(questions);

		tuto.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				aideDebut = new AideDemarrage(IHM.this);
				aideDebut.initPos();
			}

		});

		questions.addActionListener(new AideEnLigne(this));

		gestionLangue.addSubmenu();
		gestionLangue.add(ajoutLangue);
		gestionLangue.add(choixLangue);
		ajoutLangue.addActionListener(new TradLangListener(this));
		choixLangue.addActionListener(new ChooseLangListener(this));

		jtp.addMouseListener(epO);
	}

	/**
	 * Methode affichant une fenêtre pour specifier la taille du fichier blanc
	 * a créer et rajoute automatiquement un onglet contenant la nouvelle image
	 */
	public void nouveauFichier() {

		NouveauFichier nouveau = new NouveauFichier(this);

		if (nouveau.getOk())
			addTab(nouveau.getBufferedImage(), 2);
	}

	/**
	 * Fonction permettant de prendre un screenshot (affiche un dialogue pour
	 * les parametres puis rajoute l'onglet contenant la nouvelle image)
	 */
	public void screenshot() {
		Screenshot sc = new Screenshot(this);
		if (sc.getOk()) {
			if (sc.getMinimize()) {
				this.setExtendedState(Frame.ICONIFIED);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ie) {
				}
			}
			if (sc.getTime() > 0 && sc.getTime() < 30) {
				try {
					Thread.sleep(sc.getTime() * 1000);
				} catch (InterruptedException ie) {
				}
			}

			addTab(getScreenshot(new Rectangle(Toolkit.getDefaultToolkit()
					.getScreenSize())), 1);
			this.setExtendedState(Frame.MAXIMIZED_BOTH);
		}
	}

	/**
	 * Fonction permettant de prendre une impression d'écran sur une zone
	 * donnée
	 * 
	 * @param rec
	 *            Zone à copier dans la BufferedImage
	 * @return L'image contenant la zone séléctionnée
	 */
	public static BufferedImage getScreenshot(Rectangle rec) {
		BufferedImage image = null;
		try {
			Robot robot = new Robot();
			image = robot.createScreenCapture(rec);
		} catch (AWTException awte) {
		}

		return (image);
	}

	/**
	 * Création de la fenetre de gestionnaire de plugins
	 */
	public void initPluginDialog() {

		this.jd_listPlugins = new JDialog(this, "Gestionnaire de Plugins");

		this.pluginsColumn = new Vector<String>();
		this.pluginsColumn.add("Nom");
		this.pluginsColumn.add("Description");
		this.pluginsColumn.add("Etat");
		Vector<Vector<String>> rowData = new Vector<Vector<String>>();
		this.tb_listPlugins = new DefaultTableModel(rowData, pluginsColumn);
		this.jt_listPlugins = new NonEditableTable(tb_listPlugins);
		this.jt_listPlugins.setColumnSelectionAllowed(false);
		this.jsp_listPlugins = new JScrollPane(this.jt_listPlugins);

		this.jd_listPlugins.add(this.jsp_listPlugins);

		this.jp_butPlugins = new JPanel(new GridLayout(1, 2));
		this.jb_addPlugin = new JButton("Ajouter");
		this.jb_removePlugin = new JButton("Supprimer");
		this.jb_edPlugin = new JButton("Activer / Désactiver");
		this.jb_addPlugin.addActionListener(new AddPluginListener(this));
		this.jb_removePlugin.addActionListener(new RemovePluginListener(this));
		this.jb_edPlugin.addActionListener(new EDPluginListener(this));

		ListSelectionModel lsm_list = jt_listPlugins.getSelectionModel();

		lsm_list.addListSelectionListener(new BtnEDPlugin(this));

		this.jp_butPlugins.add(this.jb_addPlugin);
		this.jp_butPlugins.add(this.jb_removePlugin);
		this.jp_butPlugins.add(this.jb_edPlugin);

		this.jd_listPlugins.add(this.jp_butPlugins, BorderLayout.SOUTH);
		this.jd_listPlugins.pack();

		Dimension d = this.jd_listPlugins.getSize();
		Dimension dScreen = this.jd_listPlugins.getToolkit().getScreenSize();
		this.jd_listPlugins.setLocation(
				(int) (dScreen.getWidth() / 2 - d.getWidth() / 2),
				(int) (dScreen.getHeight() / 2 - d.getHeight() / 2));

		this.jd_listPlugins.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.jd_listPlugins.setVisible(false);
	}

	/**
	 * Met a jour les miniatures du sous menu des plugins
	 */
	public void initPluginsMenu() {

		// Bouton du sous menu Plugin

		Vector<Vector<String>> rowData = new Vector<Vector<String>>();

		BufferedImage icon;

		int j = 0;

		// On supprime tous les boutons du sous menu
		btnPlug.removeAll();

		/*
		 * for(int i = 0; i < 3; i++) { btnPlug.add(new
		 * SubmenuItem("./lib/Icons/Palette/Option/imprimante.png")); }
		 */

		Dimension dimImg = palette.pythagore(JPalette.RAYON_SUBMENU
				- JPalette.GRAND_RAYON);

		int index = jtp.getSelectedIndex();
		Pictimage pict = jaint.lst_pict.get(index)
				.get(jaint.lst_pos.get(index));
		Image temp = pict.getBufferedImage().getScaledInstance(dimImg.width,
				dimImg.height, Image.SCALE_SMOOTH);
		// miniature de base terminée au format Image

		for (int i = 0; i < this.jaint.getPluginsLoader().getNbPlugins(); i++) {

			Vector<String> row = new Vector<String>(3);
			row.add(this.jaint.getPluginsLoader().getPlugins().get(i).getName());
			row.add(this.jaint.getPluginsLoader().getPlugins().get(i)
					.getDescri());

			// On vérifie si le plugin est activé ou non
			if (this.jaint.getPluginsLoader().getPlugins().get(i).isEnabled()) {
				row.add("Activé");
				if (jtp.getSelectedIndex() != -1) {
					// Seulement si le plugin est activé on le rajoute dans la
					// liste des plugins

					BufferedImage mini = new BufferedImage(dimImg.width,
							dimImg.height, BufferedImage.TYPE_INT_RGB);
					Graphics g = mini.getGraphics();
					g.drawImage(temp, 0, 0, this);

					icon = jaint.createIconPlug(j, mini);

					// Bouton du sous menu Plugin
					btnApplyPlug = new SubmenuItem(icon);
					btnApplyPlug.addActionListener(new BtnPluginListener(j,
							IHM.this));
					btnPlug.add(btnApplyPlug);
					String nom = jaint.getPluginsLoader().getPlugins().get(i)
							.getName();
					btnApplyPlug.setToolTipText(nom);
					j++;
				}

			} else {
				row.add("Désactivé");

			}

			rowData.add(row);
		}

		this.tb_listPlugins.setDataVector(rowData, this.pluginsColumn);
	}

	/**
	 * Applique le plugin selectionné à l'image courante
	 * 
	 * @param plug
	 */
	public void modify(int plug) {

		this.jaint.applyPlugin(plug);

	}

	/**
	 * Fonction qui change le text du bouton Desactiver/Activer du gestionnaire
	 * de plugin en fonction du plugin sélectionné
	 */
	public void modifyBtnED() {
		int nbRows = this.jaint.getPluginsLoader().getNbPlugins();
		String res; // Text du bouton
		boolean act = false;
		boolean desact = false;
		for (int i = 0; i < nbRows; i++) {
			if (this.jt_listPlugins.isRowSelected(i)) {
				// Si le plugin est activé on le désactive, et on le retire de
				// la liste
				if (this.jaint.getPluginsLoader().getPlugins().get(i)
						.isEnabled()) {
					act = true;
				} else {
					desact = true;
				}

			}
		}

		if (desact && !act) {
			res = "Activer";
		} else if (!desact && act) {
			res = "Désactiver";
		} else {
			res = "Activer / Désactiver";
		}

		jb_edPlugin.setText(res);
	}

	/**
	 * Fonction renvoyant le nom d'un fichier selon le chemin d'accès passé en
	 * paramètre
	 * 
	 * @param s
	 *            Chaine à découper
	 * @return Le nom du fichier avec l'extension
	 */
	public String name(String s)// extract the name file of the path
	{
		String s2 = "";

		if (s.length() > 0) {
			int i = s.length() - 1;// on commence a la fin de la chaine car le
									// parametre est du type
									// C:\mon\dossier\mon\fichier.txt sur
									// windows

			while (s.charAt(i) != '\\' && s.charAt(i) != '/') {
				i--;
			}
			s2 = s.substring(i + 1);// on obtient donc juste fichier.txt
		}

		return s2;
	}

	/**
	 * Suppression d'un plugin, suppression des deux fichiers .class
	 */
	public void removePlugin() {
		int nbRows = this.jaint.getPluginsLoader().getNbPlugins();
		Object[] options = { "Oui", "Non" };
		int over = JOptionPane
				.showOptionDialog(
						null,
						"Voules vous vraiment supprimer le(s) plugin(s) selectionné(s) ?",
						"Supprimer plugins", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		int nb_suppr = 0;
		if (over == JOptionPane.YES_OPTION) {
			for (int i = 0; i < nbRows; i++) {
				if (this.jt_listPlugins.isRowSelected(i)) {
					this.jaint.removePlugin(this.jaint.getPluginsLoader()
							.getClassNames().get(i - nb_suppr));
					nb_suppr++;
				}
			}
			this.initPluginsMenu();
		}
	}

	/**
	 * Fonction permettant de tronquer une chaine à 8 caractères et de
	 * rajouter "..." si besoin
	 * 
	 * @param s
	 *            La chaine à tronquer
	 * @return La chaine tronquée à 8 caractères
	 */
	public String trunk(String s) {
		if (s.length() > 8)
			return s.substring(0, 8) + "...";

		return s;
	}

	/**
	 * 
	 * @param bi
	 */
	public void applyWebcam(BufferedImage bi) {
		addTab(bi, 3);
	}

	/**
	 * Fonction permettant de passer d'un mode fenêtré au mode plein écran et
	 * vice versa.
	 */
	public void pleinEcran() {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice();

		if (gd.isFullScreenSupported()) {
			if (!this.isUndecorated()) {
				this.dispose();
				this.setUndecorated(true);
				this.setVisible(true);
				gd.setFullScreenWindow(this);
			} else {
				this.dispose();
				this.setUndecorated(false);
				this.setVisible(true);
				gd.setFullScreenWindow(null);
			}

		}
	}

	/**
	 * 
	 * @return
	 */
	public boolean isHelpSelected() {
		return lu;
	}

	/**
	 * 
	 * @param state
	 */
	public void setHelpState(boolean state) {
		lu = state;
	}

	/**
	 * Saves the language chosen by user in a file
	 * 
	 * @param loc
	 *            Language chosen by user
	 */
	public void changeLang(String loc) {
		Locale l = new Locale(loc);

		try {
			PropertyResourceBundle messages = new PropertyResourceBundle(
					new InputStreamReader(new FileInputStream("./lib/lang/"
							+ l.getLanguage() + ".lang"), "utf8"));

			this.paintLabelWithLanguage(messages);

			String filePath = "./usr_params.properties";
			File newf = new File("newParams.properties");
			try {
				BufferedReader buff = new BufferedReader(new FileReader(
						filePath));
				BufferedWriter buffW = new BufferedWriter(new FileWriter(newf,
						true));
				try {
					String line;
					while ((line = buff.readLine()) != null) {
						if (line.startsWith("lang")) {
							String newLine = "lang\t" + loc + "\n";
							buffW.write(newLine);
						} else {
							buffW.write(line + "\n");
						}
						buffW.flush();
					}
				} finally {
					buff.close();
					buffW.close();
				}
			} catch (IOException ioe) {
			}
			newf.renameTo(new File("usr_params.properties"));
		} catch (FileNotFoundException fnfe) {
			JOptionPane.showMessageDialog(this,
					"La langue choisie est introuvable", "Langue introuvable",
					JOptionPane.ERROR_MESSAGE);
		} catch (UnsupportedEncodingException uee) {
		} catch (IOException ioe) {
		}
	}

	/**
	 * Traduction des termes de l'ihm
	 * 
	 * @param messages
	 *            Object qui contient la traduction des termes
	 */
	public void paintLabelWithLanguage(PropertyResourceBundle messages) {
		// File
		nouveauFichier.setToolTipText(messages.getString("New"));
		btnNouveau.setToolTipText(messages.getString("Newimage"));
		capture.setToolTipText(messages.getString("Screenshot"));
		webcam.setToolTipText(messages.getString("Webcam"));
		btnOuvrir.setToolTipText(messages.getString("Open"));
		btnSave.setToolTipText(messages.getString("Save"));
		btnSaveAs.setToolTipText(messages.getString("Saveas"));
		print.setToolTipText(messages.getString("Print"));
		btnFermer.setToolTipText(messages.getString("Closetab"));
		btnQuitter.setToolTipText(messages.getString("Quit"));

		// Edit
		undo.setToolTipText(messages.getString("Undo"));
		redo.setToolTipText(messages.getString("Redo"));
		gestionLangue.setToolTipText(messages.getString("Language"));
		choixLangue.setToolTipText(messages.getString("Chooselanguage"));
		ajoutLangue.setToolTipText(messages.getString("Addlanguage"));

		// Plugins
		btnPlug.setToolTipText(messages.getString("Plugins"));
		gestionPlugin.setToolTipText(messages.getString("Organizeplugins"));

		// Help
		aide.setToolTipText(messages.getString("Help"));
		questions.setToolTipText(messages.getString("FAQ"));
		tuto.setToolTipText(messages.getString("Tutorial"));
		apropos.setToolTipText(messages.getString("About"));

		// outil

		dessin.setToolTipText(messages.getString("Drawing"));
		pinceau.setToolTipText(messages.getString("Pen"));
		gomme.setToolTipText(messages.getString("Eraser"));
		potPeinture.setToolTipText(messages.getString("Tinofpaint"));
		forme.setToolTipText(messages.getString("Shape"));
		outil.setToolTipText(messages.getString("Tool"));
		fleche.setToolTipText(messages.getString("Selection"));
		pipette.setToolTipText(messages.getString("Pipette"));
		couleur.setToolTipText(messages.getString("Color"));

		// Edition

		couperCopierColler.setToolTipText(messages.getString("Edit"));
		couper.setToolTipText(messages.getString("Cut"));
		copier.setToolTipText(messages.getString("Copy"));
		coller.setToolTipText(messages.getString("Paste"));
		rogner.setToolTipText(messages.getString("Trim"));
		pleinEcran.setToolTipText(messages.getString("Fullscreen"));
	}

	private void sleep(int i) {
		try {
			Thread.sleep(i);
		} catch (Exception e) {

		}
	}

	private void initLanguage() {
		try {
			BufferedReader bf = new BufferedReader(new FileReader(
					"./lib/lang.config"));

			try {
				String loc = bf.readLine();
				bf.close();
				changeLang(loc);
			} catch (IOException ex) {
				changeLang("english");
			}

		} catch (FileNotFoundException ex) {
			changeLang("english");
		}
	}

	public boolean isBeginningRead() {
		return aideLuDebut;
	}

	void disable(JPaletteItem item) {
		item.setEnabled(false);
	}

	void activate(JPaletteItem item) {
		item.setEnabled(true);
	}

	public void hidePalettes() {
		palette.setVisible(false);
        palette.setState(false);
        paletteOption.setVisible(false);
        paletteOption.setState(false);
        paletteGestion.setVisible(false);
        paletteGestion.setState(false);
	}

}