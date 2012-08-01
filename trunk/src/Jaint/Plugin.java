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
     *  Renvoie si un plugin est activé ou non
     *
     * @return Vrai si le plugin est activé, faux sinon
     */
    public boolean isEnabled();

    /**
     *  Modification du boolean enabled
     *
     * @param b     boolean vrai pour activer le plugin, faux pour le desactiver
     */
    public void setEnabled(boolean b);

    /**
     * Méthode pour la création de l'icone du plugin, avec les paramètres souhaité
     * 
     *
     * @param	img	l'image sur laquelle appliquer les modifications
     * @return icon     icone modifiée par l'effet
     */
    public BufferedImage applyEffectIcon(BufferedImage img);

    
    /**
     * Méthode générale qui appelle tous les autres méthodes quand on applique un plugin
     * 
     * @param img   l'image sur laquelle appliquer l'effet
     * @return imgModif     image modifiée
     */
    public BufferedImage modify(BufferedImage img);



    /**
     * Méthode qui renvoie le nombre de fois que l'effet doit être appliqué à l'icone afin de voir un résultat
     *
     * @return nbEffect     Nombre d'application de l'effet sur l'icone
     */
    public int getNbApplyPlug();



}
