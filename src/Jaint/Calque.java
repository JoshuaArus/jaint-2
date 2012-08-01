package Jaint;

import JaintPlug.Fonction;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * Classe servant a stocker temporairement une image. Utilise pour les fonction couper, copier, coller et rogner
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
     * @param picture L'image a stocker temporairement (peut etre null a la creation du composant)
     * @param origine Coordonnees du point 0,0 de l'image temporaire sur l'arriere plan
     */
    public Calque(BufferedImage picture, Point origine)
    {
        this.picture = picture;
        this.origine = origine;
    }

    /**
     * Sert a placer l'origine de l'image temporaire par rapport au fond
     * @param origine Coordonnees du point 0,0 de l'image temporaire sur l'arriere plan
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
     * Recupere les coordonnees de l'origine du calque
     * @return Le point correspondant a l'origine du calque sur l'image de fond
     */
    public Point getOrigine() {
        return new Point(origine);
    }

    /**
     * Retourne l'image stockee
     * @return L'image stockee
     */
    public BufferedImage getBufferedImage()
    {
        return picture;
    }

    /**
     * Retourne la taille de l'image stockee
     * @return Taille de l'image stockee
     */
    public Dimension getSize() {
        return new Dimension(size);
    }

    /**
     * Retourne l'etat du calque (active ou non)
     * @return Booleen de l'etat du calque
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Changer l'etat du calque
     * @param enabled Vrai ou Faux
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
