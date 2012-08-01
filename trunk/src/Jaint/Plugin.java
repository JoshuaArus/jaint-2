package Jaint;


import java.awt.image.BufferedImage;


/**
 *Interface Plugin
 * @author Jonas
 */
public interface Plugin
{
    /**
     * Retourne le nom du plugin.
     * @return nom du plugin
     */
    public String getName();

    /**
     * Retourne la description du plugin
     * @return description
     */
    public String getDescri();

    /**
     *  Renvoie si un plugin est active ou non
     *
     * @return Vrai si le plugin est active, faux sinon
     */
    public boolean isEnabled();

    /**
     *  Modification du boolean enabled
     *
     * @param b     boolean vrai pour activer le plugin, faux pour le desactiver
     */
    public void setEnabled(boolean b);

    /**
     * Methode pour la creation de l'icone du plugin, avec les parametres souhaite
     * 
     *
     * @param	img	l'image sur laquelle appliquer les modifications
     * @return icon     icone modifiee par l'effet
     */
    public BufferedImage applyEffectIcon(BufferedImage img);

    
    /**
     * Methode generale qui appelle tous les autres methodes quand on applique un plugin
     * 
     * @param img   l'image sur laquelle appliquer l'effet
     * @return imgModif     image modifiee
     */
    public BufferedImage modify(BufferedImage img);



    /**
     * Methode qui renvoie le nombre de fois que l'effet doit etre applique a l'icone afin de voir un resultat
     *
     * @return nbEffect     Nombre d'application de l'effet sur l'icone
     */
    public int getNbApplyPlug();



}
