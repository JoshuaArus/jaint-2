package Jaint;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Observable;
import java.awt.Point;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Cursor;
import javax.swing.JOptionPane;




/**
 * Classe principale du logiciel
 * @author Joshua & Jonas
 */

public class Jaint extends Observable
{

    /**
     * Liste contenant des liste de <code>Pictimage</code>
     */
    public ArrayList<ArrayList<Pictimage>> lst_pict = new ArrayList<ArrayList<Pictimage>>();
    /**
     * Liste contenant pour chaque onglet la position de l'image courante dans <code>lst_pict</code> (utilisée pour le undo/redo)
     */
    public ArrayList<Integer> lst_pos = new ArrayList<Integer>();
    /**
     * Calque contenant l'image stockée temporairement suite à l'action "couper"
     */
    public Calque cut = new Calque(null, new Point(0,0));
    /**
     * Calque contenant l'image stockée temporairement suite à l'action "copier"
     */
    public Calque copy = new Calque(null, new Point(0,0));
    /**
     * Entier permettant de savoir dans quel mode on se trouve (couper/copier/coller/rogner)
     */
    public int mode = -1;
    PluginsLoader pl;
   

    
    /**
     * Instance de l'IHM pour acceder au JTabbedPane et au reste des composants
     */
    public IHM ihm;
    JFileChooser JFC_chooser = new JFileChooser();
    FileNameExtensionFilter filter_all = new FileNameExtensionFilter("All files (JPG, JPEG, BMP, PNG & GIF)", "jpg", "jpeg", "bmp", "png", "gif");
    FileNameExtensionFilter filter_jpg = new FileNameExtensionFilter("JPG files", "jpg", "jpeg");
    FileNameExtensionFilter filter_bmp = new FileNameExtensionFilter("BMP files", "bmp");
    FileNameExtensionFilter filter_png = new FileNameExtensionFilter("PNG files", "png");
    FileNameExtensionFilter filter_gif = new FileNameExtensionFilter("GIF files", "gif");


    /**
     *
     */
    public Jaint()
    {

        JFC_chooser.addChoosableFileFilter(filter_jpg);
        JFC_chooser.addChoosableFileFilter(filter_png);
        JFC_chooser.addChoosableFileFilter(filter_bmp);
        JFC_chooser.addChoosableFileFilter(filter_gif);
        JFC_chooser.addChoosableFileFilter(filter_all);

        this.pl = new PluginsLoader("./plugins");
        try
        {
                this.pl.loadPlugins();
        }
        catch(WrongPluginException we)
        {
                System.err.println("Erreur le plugin "+we.getPlugin()+" n'est pas valide");
        }


        ihm = new IHM(this, pl);
    }

    /**
     * Simple point d'entrée du logiciel, appel imédiat au constructeur <code>Jaint</code>
     * @param args
     */
    public static void main(String[] args)
    {
    	new Jaint();
        //test
    }


    /**
     * Ajoute un plugin a la liste des plugins.
     *
     * @param	plug	<code>File</code> du fichier classe du plugin.
     * @throws	WrongPluginException	si le plugin est invalide.
     */
    public void addPlugin(File plug) throws WrongPluginException
    {
        try
        {
             this.pl.addPlugin(plug);
        }
        catch(WrongPluginException we)
        {
                System.err.println("ERROR : WrongPluginException : The plugin "+we.getPlugin()+" is not valid");
                throw new WrongPluginException(we.getPlugin());
        }
    }

    /**
     * Supprime le plugin avec son nom de classe.
     *
     * @param	classname	Nom de la classe a supprimer.
     */
    public void removePlugin(String classname)
    {
            this.pl.removePlugin(classname);
    }

    /**
     *Renvoie une copie du PluginsLoader courant
     * @return pl PluginsLoader courant
     */
    public PluginsLoader getPluginsLoader()
    {
        return((PluginsLoader)this.pl.clone());
    }

    /**
     * Stock l'image passée en paramètre dans l'historique (<code>lst_pict</code>) et met à jour la miniature de l'onglet, les miniatures du sous menu des plugin et l'image de la zone principale
     * @param bi Image à stocker
     */
    public void applyEffect(BufferedImage bi)
    {
        int index = ihm.jtp.getSelectedIndex();
        int currentPos = lst_pos.get(index);
        ArrayList<Pictimage> lst = lst_pict.get(index);
        
        try
        {
            if (lst.get(currentPos+1) == null)
            {
                //on essaye de faire un set (changer), si une image existait elle est remplacée, sinon, IndexOutOfBoundsException, donc on va dans le catch
            }
            else
            {
                lst.set(currentPos + 1, new Pictimage(bi,lst.get(currentPos).getPath()));

                int nouveau = currentPos + 1;//on récupere l'emplacement de l'image suivante (si elle existe)

                while(lst.size() > nouveau)
                {
                    lst.remove(nouveau+1);//on met a null toutes les images qui suivent l'image actuelle (histoire de bien gérer l'historique)
                }
            }
        }
        catch(IndexOutOfBoundsException ex)
        {
            //y'a pas encore d'image au rang currentPos+1 donc on ajoute au lieu de set
            lst.add(new Pictimage(bi,lst.get(currentPos).getPath()));
        }
        
        ihm.jaint.lst_pos.set(index, currentPos+1);
        
        MainComponent m = (MainComponent) ihm.jtp.getComponentAt(index);
        TabComponent t = (TabComponent) ihm.jtp.getTabComponentAt(index);
        
        m.update(null, null);
        t.update(null, null);

        ihm.activate(ihm.palette.getBtn(2));
    }

     /**
     * Permet d'ouvrir un gestionnaire de fichier pour ouvrir une ou plusieurs images et rajoute les éléments séléctionnés à l'IHM
     */
    public void ouvrir()
    {
        JFC_chooser.setMultiSelectionEnabled(true);

        int res = JFC_chooser.showOpenDialog(ihm);//on stock dans res le "code retour" du filechooser

		ihm.setCursor(new Cursor(Cursor.WAIT_CURSOR ));

        File[] files = JFC_chooser.getSelectedFiles();

        for (File file : files)
        {
            if (res == JFileChooser.APPROVE_OPTION && JFC_chooser.accept(file))//si l'utilisateur a bien cliqué sur enregistrer et qu'il est de la bonne extension
            {    
				String myFile = new String(file.toString());// Récupérer le nom du fichier qu’il a spécifié
				ihm.addTab(myFile);
            }
        }

        ihm.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    /**
     * Permet de choisir le fichier de destination de l'image courante et de l'enregistrer
     */
    public void enregistrerSous()
    {
        int ind = ihm.jtp.getSelectedIndex();
        if (ind != -1)
            enregistrerSous(ind);
    }

    /**
     * Permet d'enregistrer l'image courante. Si aucun fichier de destination n'est spécifié, on effectue l'appel à la fonction enregistrer()
     */
    public void enregistrer()
    {
        int index = ihm.jtp.getSelectedIndex();
        if (index != -1)
        {
            String s = lst_pict.get(index).get(lst_pos.get(index)).getPath();

            if (!s.equals(""))
                enregistrer(index,new File(lst_pict.get(index).get(lst_pos.get(index)).getPath()));
            else
                enregistrerSous(index);
        }
            
    }

    /**
     * Permet de faire la même chose que enregistrerSous() mais en spécifiant l'onglet à considérer
     * @param index Index de l'onglet a enregistrer
     */
    public void enregistrerSous(int index)
    {
        int res = JFC_chooser.showSaveDialog(ihm);

        if (res == JFileChooser.APPROVE_OPTION)
        {
            File file = JFC_chooser.getSelectedFile();
            enregistrer(index,file);
        }
    }

    /**
     * Permet d'enregistrer un onglet en particulier en spécifiant le fichier de destination
     * @param index Index de l'onglet a sauvegarder
     * @param file Fichier de destination
     */
    public void enregistrer(int index, File file)
    {
         ArrayList<Pictimage> lst = lst_pict.get(index);
            BufferedImage bf_img = lst.get(lst.size() - 1).getBufferedImage();
		String path = file.getAbsolutePath();
		String name = file.getName();
		String ext = path.substring(path.length()-3);
		if(!(ext.equals("png") || ext.equals("jpg") || ext.equals("gif") || ext.equals("bmp")))
		{
			if(bf_img.getType() == BufferedImage.TYPE_4BYTE_ABGR ||
				bf_img.getType() == BufferedImage.TYPE_4BYTE_ABGR_PRE ||
				bf_img.getType() == BufferedImage.TYPE_BYTE_BINARY ||
				bf_img.getType() == BufferedImage.TYPE_BYTE_GRAY ||
				bf_img.getType() == BufferedImage.TYPE_BYTE_INDEXED)
			{
				ext = "gif";
			}
			else if(bf_img.getType() == BufferedImage.TYPE_CUSTOM ||
				bf_img.getType() == BufferedImage.TYPE_INT_ARGB ||
				bf_img.getType() == BufferedImage.TYPE_INT_ARGB_PRE)
			{
				ext = "png";
			}
			else
			{
				ext = "jpg";
			}
			file = new File(path+"."+ext);
			name = name + "." + ext;
		}
            try {
                ImageIO.write(bf_img, ext, file);
            } catch (IOException ex) {
            }

            TabComponent tab = (TabComponent) ihm.jtp.getTabComponentAt(index);

            for (int i = 0; i < lst.size(); i++)
            {
                lst.get(i).setPath(path);
            }

            tab.setLabel(ihm.trunk(name));

            ihm.jtp.setToolTipTextAt(index,name);
    }

    /**
     * Annule une action réalisée
     */
    public void undo()
    {
       int index = ihm.jtp.getSelectedIndex();

       if (index >= 0)
       {
           int currentPos = lst_pos.get(index);
           currentPos--;
           if(currentPos > -1)
           {
               lst_pos.set(index, currentPos);

               MainComponent m = (MainComponent) ihm.jtp.getComponentAt(index);
               TabComponent t = (TabComponent) ihm.jtp.getTabComponentAt(index);
               
               m.update(null, null);
               t.update(null, null);

               if (currentPos == 0)
               {
                   ((TabComponent)ihm.jtp.getTabComponentAt(index)).setLabel(ihm.trunk(ihm.jtp.getToolTipTextAt(index)));
                   ihm.disable(ihm.palette.getBtn(2));
               }

               //On met a jour les icone du menu des plugin
           ihm.initPluginsMenu();
           }
           ihm.activate(ihm.palette.getBtn(5));
       }
    }

    /**
     * Réapplique une action déjà annulée
     */
    public void redo()
    {
       int index = ihm.jtp.getSelectedIndex();

       if(index>=0)
       {
           int currentPos = lst_pos.get(index);
           currentPos++;
           if(currentPos < lst_pict.get(index).size())
           {
               lst_pos.set(index, currentPos);

               MainComponent m = (MainComponent) ihm.jtp.getComponentAt(index);
               TabComponent t = (TabComponent) ihm.jtp.getTabComponentAt(index);
               
               m.update(null, null);
               t.update(null, null);

               //On met a jour les icone du menu des plugin
                ihm.initPluginsMenu();

                if (currentPos+1 == lst_pict.get(index).size())
                    ihm.disable(ihm.palette.getBtn(5));
           }
           ihm.activate(ihm.palette.getBtn(2));
       }
    }
    /**
     * Applique le plugin definie par <code>index</code> sur l'image courante.
     *
     * @param	index	Position du plugin a appliquer dans la liste des plugins.
     */
    public void applyPlugin(int index)
    {
        if(ihm.jtp.getSelectedIndex() != -1)// Si une image est ouverte
        {
            Plugin p = this.pl.getPluginsActif().get(index);

            ihm.setCursor(new Cursor(Cursor.WAIT_CURSOR ));
            Pictimage OrigBf = lst_pict.get(ihm.jtp.getSelectedIndex()).get(lst_pos.get(ihm.jtp.getSelectedIndex()));


            BufferedImage ModifBf = p.modify(OrigBf.getBufferedImage());

            if(p.getName().equals("Redimensionnement") && ModifBf != null)//Lorsque l'on redimensionne on cree un nouvel onglet
            {
               ihm.addTab(ModifBf,4);
            }

            if(ModifBf != null)
            {
                 this.applyEffect(ModifBf);
            }
           

            ihm.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            //On met a jour les icone du menu des plugin
            ihm.initPluginsMenu();
            
        }

    }

    

    

    /**
     *Methode de creation des icones des plugins
     * @param index index du plugin dans la liste des plugins
     * @param icon miniature de l'image courante m
     * @return icon miniature de l'image courante modifiée par l'effet du plugin
     */
    public BufferedImage createIconPlug(int index, BufferedImage icon)
    {
        Plugin plug = this.pl.getPluginsActif().get(index);
        

        int nbIteration = plug.getNbApplyPlug();


        for( int i = 0 ; i < nbIteration ; i++)
        {
            icon = plug.applyEffectIcon(icon);
        }
        return icon;
    }

    /**
     * Retourne l'image courante sous forme de BufferedImage
     * @return L'image courante
     */
    public BufferedImage getImageCourante()
    {
        return (lst_pict.get(ihm.jtp.getSelectedIndex()).get(lst_pos.get(ihm.jtp.getSelectedIndex()))).getBufferedImage();

    }

    /**
     * Fonction appelée lorsqu'on ferme la fenêtre. Elle verifie si il y a des onglets non enregistrer et si tel est le cas, propose de les enregistrer
     */
    public void quitter()
    {
        int total = ihm.jtp.getTabCount();
        int i = 0;
        int count = 0;

        for (i = 0; i < total; i++)
        {
            if (((TabComponent) ihm.jtp.getTabComponentAt(i)).getLabel().endsWith(" *"))
                count++;
        }

        for (i = 0; i < total; i++)
        {
                String name = ((TabComponent) ihm.jtp.getTabComponentAt(i)).getLabel();
                String tooltip = ihm.jtp.getToolTipTextAt(i);

                if (name.endsWith(" *"))
                {
                    ihm.jtp.setSelectedIndex(i);
                    Object[] options;
                    if (count > 1)
                    {
                        options = new Object[5];
                        options[0] = "Oui";
                        options[1] = "Non";
                        options[2] = "Annuler";
                        options[3] = "Oui pour tous";
                        options[4] = "Non pour tous";
                        
                    }
                    else
                    {
                        options = new Object[3];
                        options[0] = "Oui";
                        options[1] = "Non";
                        options[2] = "Annuler";
                    }

                    Object initial = "Annuler";
                    
                    int res = JOptionPane.showOptionDialog(null,"Voulez vous enregistrer "+tooltip+" ?","Message de confirmation",JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,null,options,initial);
                    
                    if (res == 0)
                    {
                        ArrayList<Pictimage> lst = ihm.jaint.lst_pict.get(i);
                        String path = lst.get(lst.size() - 1).getPath();

                        if (path.equals(""))
                            ihm.jaint.enregistrerSous(i);
                        else
                            ihm.jaint.enregistrer(i,new File(path));
                    }
                    else if (res == 3)
                    {
                        while (i < total)
                        {
                            ArrayList<Pictimage> lst = ihm.jaint.lst_pict.get(i);
                            String path = lst.get(lst.size() - 1).getPath();

                            if (path.equals(""))
                                ihm.jaint.enregistrerSous(i);
                            else
                                ihm.jaint.enregistrer(i,new File(path));

                            i++;
                        }
                    }
                    else if (res == 4)
                    {
                        i = total - 1;
                    }
                    else if (res == 2)
                    {
                        i += total;
                    }
                }
        }

        if (i == (total))
        {
                System.exit(0);
        }
    }

}