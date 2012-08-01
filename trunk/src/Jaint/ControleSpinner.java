package Jaint;

import java.awt.Color;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Classe controlant les changement de valeur des spinners RVB et appliquant le traitement adéquat
 * @author Jeff
 */
public class ControleSpinner implements ChangeListener
{
    private JaintPalette palette;

    /**
     * Construit le contrôle
     * @param p La palette
     */
    public ControleSpinner(JaintPalette p)
    {
        palette = p;
    }

    public void stateChanged(ChangeEvent e)
    {
        if(!palette.onDegrade() && !palette.onSliderColor() && !palette.isOnMenu())
        {
            JSpinner spinner = (JSpinner) e.getSource();

            int value = (Integer) spinner.getValue();


                if(value > 255)
                    spinner.setValue(255);
                else if (value < 0)
                    spinner.setValue(0);
                else
                {
                   JSpinner[] rvb = palette.getSpinner();

                   Color c = modifyColor(rvb);
                   float[] hsb = replacerSlider(c);
                   replacerPointille(c, hsb, palette);
                   palette.repaint();
                }
        }


    }

    private Color modifyColor(JSpinner[] rvb)
    {
        int rouge = (Integer) rvb[0].getValue();
        int vert = (Integer) rvb[1].getValue();
        int bleu = (Integer) rvb[2].getValue();

        Color res = new Color(rouge, vert, bleu);

        if(palette.getBoutonColor()[0].isChecked())
            palette.setFirstColor(res);
        else
            palette.setSecondColor(res);

        return res;


    }

    private float[] replacerSlider(Color c)
    {
        JSliderColorChooser sliderColor = palette.getSliderColor();
       
       final int FIN_GRIS = JSliderColorChooser.FIN_GRIS;

       int rouge = c.getRed();
       int vert = c.getGreen();
       int bleu = c.getBlue();

       float[] hsb = Color.RGBtoHSB(rouge, vert, bleu, null);
       
            if(estNoir(rouge, vert, bleu) || estBlanc(rouge, vert, bleu) || estGris(rouge, vert, bleu))
            {
                 sliderColor.setSliderX(FIN_GRIS / 2);
            }
            else
            {
                int largeur = sliderColor.getSize().width;
                double pointX = largeur * hsb[0] + FIN_GRIS;

                sliderColor.setSliderX((int)(pointX));
            }



       return hsb;
    }

    private boolean estNoir(int rouge, int vert, int bleu)
    {
        return (rouge == 0 && vert == 0 && bleu == 0);
    }

    private boolean estBlanc(int rouge, int vert, int bleu)
    {
        return (rouge == 255 && vert == 255 && bleu == 255);
    }

    /**
     * Replace la ligne de pointillée dans le sélecteur de couleur
     * @param c Couleur sélectionnée
     * @param hsb Tableau HSB de la couleur
     * @param jp La JaintPalette
     */
    public static void replacerPointille(Color c, float[] hsb, JaintPalette jp)
    {
        int rayonPalette = jp.getPetitRayon();

        if(hsb[1] == 1f && hsb[2] == 1f)
        {
            //couleur pure

            jp.setPosLuminosite(jp.coordX);
        }
        else if(hsb[1] == 1f && hsb[2] != 1f)
        {
            // première partie du degradé

            jp.setPosLuminosite((int) (jp.coordX - rayonPalette + rayonPalette * hsb[2]));
        }
        else if(hsb[1] != 1f && hsb[2] == 1f)
        {
            //deuxième partie du dégradé

            double res = (-hsb[1] * rayonPalette) + rayonPalette;
            jp.setPosLuminosite((int) res  + jp.coordX);
        }


        jp.setChangementSpinner(true);

    }

    private boolean estGris(int rouge, int vert, int bleu)
    {
        return (rouge == vert && vert == bleu);
    }

}
