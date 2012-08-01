package Jaint;

import JaintPlug.Fonction;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * Classe servant à stocker temporairement une image. Utilisé pour les fonction couper, copier, coller et rogner
 * @author Joshua
 */
public class Calque
{
    BufferedImage picture = null;
    Point origine;
    Dimension size;
    boolean enabled;

    /**
     * Renvoi une nouvelle instance de type <code>Calque</code>
     * @param picture L'image à stocker temporairement (peut être null à la création du composant)
     * @param origine Coordonnées du point 0,0 de l'image temporaire sur l'arriere plan
     */
    public Calque(BufferedImage picture, Point origine)
    {
        this.picture = picture;
        this.origine = origine;
    }

    /**
     * Sert à placer l'origine de l'image temporaire par rapport au fond
     * @param origine Coordonnées du point 0,0 de l'image temporaire sur l'arriere plan
     */
    public void setOrigine(Point origine) {
        this.origine = origine;
    }

    /**
     * Specifie l'image stocker
     * @param picture L'image temporaire a stocker
     */
    public void setPicture(BufferedImage picture) {
        if (picture != null)
            this.picture = Fonction.cloneBuff(picture);
        else
            this.picture = null;
    }

    /**
     * Récupere les coordonnées de l'origine du calque
     * @return Le point correspondant à l'origine du calque sur l'image de fond
     */
    public Point getOrigine() {
        return new Point(origine);
    }

    /**
     * Retourne l'image stockée
     * @return L'image stockée
     */
    public BufferedImage getBufferedImage()
    {
        return picture;
    }

    /**
     * Retourne la taille de l'image stockée
     * @return Taille de l'image stockée
     */
    public Dimension getSize() {
        return new Dimension(size);
    }

    /**
     * Retourne l'état du calque (activé ou non)
     * @return Booléen de l'état du calque
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Changer l'état du calque
     * @param enabled Vrai ou Faux
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
