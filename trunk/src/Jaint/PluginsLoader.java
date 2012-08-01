package Jaint;

/**
 *
 * @author jonas
 */

import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;



public class PluginsLoader
{
    private ArrayList<String> listClass;
    private ArrayList<Plugin> listPlugins;
    public ArrayList<Plugin> listPluginsActif;
    private String path;

    /**
     * Construit un nouveau <code>PluginsLoader</code> avec une <code>String</code> specifiant le repertoire pour les plugins.
     *
     * @param	path	repertoire source des plugins
     */
    public PluginsLoader(String path)
    {
            this.path = path;
            this.listClass = new ArrayList<String>();
            this.listPlugins = new ArrayList<Plugin>();
            this.listPluginsActif = new ArrayList<Plugin>();
    }
    /**
     * Construit un nouveau <code>PluginsLoader</code> avec <code>PluginsLoader</code> courant
     *
     * @param	pluginsLoader	PluginLoader courant
     */
    private PluginsLoader(PluginsLoader pl)
    {
            this.path = pl.getPath();
            this.listClass = pl.getClassNames();
            this.listPlugins = pl.getPlugins();
            this.listPluginsActif = pl.getPluginsActif();
    }

  
    /**
     * Ajoute le plugin plug à la liste listPluginActif
     * @param plug Plugin à ajouter à la liste
     */
    public void addListPluginActif(Plugin plug)
    {

        this.listPluginsActif.add(plug);
    }


    @Override
    /**
     * Creee et retourne une copie de cet objet.
     */
    public Object clone()
    {
            return(new PluginsLoader(this));
    }

 
    /**
     * Methode qui copie le fichier du plugins vers le dossier ./plugins du logiciel et dans le dossier contenant les .class
     * @param plug  Plugin à ajouter
     * @throws WrongPluginException
     */
    public void addPlugin(File plug) throws WrongPluginException
    {
        boolean estPresent = false;
        int i = 0;
        FileChannel source = null; // canal d'entrée
        FileChannel destination = null; // canal de sortie
        FileChannel destination2 = null; //canal de sortie2
        String absolute_path = plug.getAbsolutePath(); // On récupère le chemin du plugin
        String pathPlug = System.getProperty("user.dir" ); // On récupère le chemin du dossier du projet

        try
        {

            if(plug.getName().length() > 6 && plug.getName().endsWith(".class"))
            {
               //On parcourt la liste des plugins
                while(i < this.listPlugins.size() && !estPresent)
                {
                
                    
                    //si le plugin que l'on importe dans jaint est déja dans la liste
                    if(plug.getName().equals(this.listClass.get(i)+".class"))
                    {
                        //On affiche un méssage d'information
                        estPresent = true;
                        JOptionPane.showMessageDialog(null, "Le plugin "+plug.getName()+" est déjà dans Jaint",plug.getName(), JOptionPane.INFORMATION_MESSAGE,new ImageIcon("./lib/Icons/Plugins/plugin.png"));

                    }

                    i++;
                }

                //On ajoute dans les dossier contenant les .class seulement si le plugin n'est pas encore présent
                if(!estPresent)
                {
                    
                    source = new FileInputStream(absolute_path).getChannel();
                    destination = new FileOutputStream(this.path+"/"+plug.getName()).getChannel();
                    
                    destination2 = new FileOutputStream(pathPlug+"/build/classes/JaintPlug/"+plug.getName()).getChannel();

                    // Copie de plugin.class vers ./plugins
                    source.transferTo(0, source.size(), destination);
                    // Copie de plugin.class vers ./build/classes/JaintPlug
                    source.transferTo(0, source.size(), destination2);


                }

                String name = plug.getName().substring(0,plug.getName().length()-6);

                if(this.listClass.indexOf(name) == -1)
                {
                        this.newPlugin(name);
                }
            }
        }
        catch (Exception e)
        {
                e.printStackTrace(); // n'importe quelle exception
        }
        finally
        { // finalement on ferme
            if(source != null)
            {
                    try
                    {
                            source.close();
                    }
                    catch (IOException e) {}
            }
            if(destination != null)
            {
                    try
                    {
                            destination.close();
                    }
                    catch (IOException e) {}
            }
        }
    }

    /**
     * Declare et instancie un nouveau <code>Plugin</code> a partir de son nom.
     *
     * @param	className	nom du <code>Plugin</code> a instancier
     * @throws	WrongPluginException	si le plugin a charger est invalide
     */
    public void newPlugin(String className) throws WrongPluginException
    {
         

            try
            {
                    @SuppressWarnings("rawtypes")
					Class c = Class.forName("JaintPlug."+className);
                    Object classTmp = c.newInstance();

                  
                    if(classTmp instanceof Plugin)
                    {
                            int i = 0;
                            while(i < this.listClass.size() && className.compareTo(this.listClass.get(i)) > 0)
                            {
                                    i++;
                            }
                            this.listClass.add(i, className);
                            this.listPlugins.add(i, (Plugin)classTmp);
                            this.listPluginsActif.add(i, (Plugin) classTmp);
                    }
                    else
                    {
                            throw new WrongPluginException(className);
                    }
            }
            catch(NoClassDefFoundError n)
            {

                    throw new WrongPluginException(className);
            }
            catch(InstantiationException ie)
            {
             
                    throw new WrongPluginException(className);
            }
            catch(IllegalAccessException iae)
            {

                    throw new WrongPluginException(className);
            }
            catch(ClassNotFoundException ce)
            {

                    throw new WrongPluginException(className);
            }
    }

    /**
     * Supprime un plugin en supprimant le fichier .class correspondant.
     *
     * @param	className	nom de la classe du plugin a supprimer
     */
    public void removePlugin(String className)
    {
            int index;
            String path2 = System.getProperty("user.dir" );
            if((index = this.listClass.indexOf(className)) != -1)
            {
                    
                    File plug1 = new File(this.path+"/"+className+".class");
                    File plug2= new File(path2+"/build/classes/JaintPlug/"+className+".class");
                    plug1.delete();
                    plug2.delete();
                    this.listClass.remove(index);
                    this.listPlugins.remove(index);
            }
    }

    
    /**
     *Vide la liste de plugins actifs
     */
    public void removeAllListPluginActif()
    {
        this.listPluginsActif.clear();
    }

    /**
     * Retourne un <code>ArrayList</code> de <code>Plugin</code> contenant toutes les instances des plugins.
     * @return lstPlug  Liste des plugins
     */
    @SuppressWarnings("unchecked")
    public ArrayList<Plugin> getPlugins()
    {
            return((ArrayList<Plugin>)(this.listPlugins.clone()));
    }

    /**
     * Retourne un <code>ArrayList</code> de <code>Plugin</code> contenant toutes les instances des plugins actifs.
     * @return lstPlugActif Liste des plugins actifs
     */
    @SuppressWarnings("unchecked")
    public ArrayList<Plugin> getPluginsActif()
    {
       return((ArrayList<Plugin>)(this.listPluginsActif.clone()));
    }

    /**
     * Retourne un <code>ArrayList</code> de <code>String</code> contenant le nom de toutes les classes <code>Plugin</code>.
     * @return lstPlugClasse Liste des plugins actifs
     */
    @SuppressWarnings("unchecked")
    public ArrayList<String> getClassNames()
    {
            return((ArrayList<String>)(this.listClass.clone()));
    }

    /**
     * Retourne le repertoire ou se trouvent les plugins.
     * @return path Chemin du repertoire contenant les plugins
     */
    public String getPath()
    {
            return(this.path);
    }
    /**
     * Retourne le nombre de plugins charges.
     * @return nbPlug Nombre de plugin dans la liste des plugins
     */
    public int getNbPlugins()
    {
            return(this.listPlugins.size());
    }


    /**
     * Initialise les listes des classes et des instances de <code>Plugin</code>.
     *
     * @throws	NullPointerException	si le chemin est <code>null</code>
     * @throws	WrongPluginException	<code>Plugin</code> invalide
     */
    public void loadPlugins()
    {
            File repPlugin = new File(this.path);
            if(repPlugin.exists())
            {
                    String[] nameFiles = repPlugin.list();
                    for(int i = 0; i < nameFiles.length; i++)
                    {
                            if(nameFiles[i].length() > 6 && nameFiles[i].substring(nameFiles[i].length()-6).compareTo(".class") == 0)
                            {
                                    String tmp = nameFiles[i].substring(0,nameFiles[i].length()-6);
                                    this.newPlugin(tmp);
                            }
                    }
            }
            else
            {
                    File create_dir = new File(this.path);
                    create_dir.mkdir();
            }
    }
}
