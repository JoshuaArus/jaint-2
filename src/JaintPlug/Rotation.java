package JaintPlug;


import java.awt.image.BufferedImage;
import java.awt.*;
import javax.swing.*;

import JaintPlug.rotation.*;
import Jaint.*;


import Jaint.Plugin;

/**
 *
 * @author Jonas
 */
public class Rotation implements Plugin
{

    private boolean estActive = true;
    private boolean ok = false;
    private int nbApplyPlug = 1;
    private double scaleValue = 1;
    private int effect = 0;

    private BufferedImage biRotaAH;
    private BufferedImage biOri;
    private BufferedImage biRotaH;
    private JDialog jd;
    private JPanel jp;
    private JPanel jpRotaAH;
    private JPanel jpOri;
    private JPanel jpRotaH;

    private JButton jbRotaAH;
    private JButton jbOri;
    private JButton jbRotaH;

    private JLabel jlApply;





    /**
     *Annule l'effet et femer la fenetre du plugin
     */
    public void annuler()
    {
        this.ok = false;
        this.jd.dispose();
    }

   /**
    *Applique l'effet et femer la fenetre du plugin
    */
   public void apply()
    {
        this.ok = true;
        this.jd.dispose();
    }

    public String getName()
    {
        return("Rotation 90°");
    }

    public String getDescri()
    {
        return("Permet d'effectuer une rotation de 90°");
    }

    public boolean isEnabled()
    {
        return estActive;
    }

    public void setEnabled(boolean b)
    {
        estActive = b;
    }

    /**
     * Prend l'image courante en parametre et retourne l'image modifié ou non
     * @param img image courante
     * @return imgModif image modifié ou non
     */
    public BufferedImage modify(BufferedImage img)
    {
        if(img.getType() == BufferedImage.TYPE_3BYTE_BGR || img.getType() == 0)
            {
                BufferedImage src = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D g = src.createGraphics();
                g.drawRenderedImage(img, null);
                g.dispose();
                img = src;
            }

            this.biOri = img;

            this.showDialog(img);

            BufferedImage bi;

            if(ok)
            {
                bi = applyEffect(img);

                return bi;
            }
            else
            {
                return img;
            }
    }

    /**
     * Affiche la fenetre du plugin, en prenant en parametre l'image courante
     * @param img image courante
     */
    public void showDialog(BufferedImage img)
    {

        Image mini;
        BufferedImage miniBi;

        this.jd = new JDialog(JFrame.getFrames()[0], "Rotation 90°", true);
        this.jp = new JPanel(new BorderLayout());

        this.jpRotaAH = new JPanel(new BorderLayout());
        this.jpOri = new JPanel(new BorderLayout());
        this.jpRotaH = new JPanel(new BorderLayout());



        int width = img.getWidth();
        int height = img.getHeight();

        if (width > 1600 || height > 1600)
        {
            this.scaleValue = 0.125;
        }
        else if (width > 800 || height > 800)
        {
            this.scaleValue = 0.25;
        }
        else if(width > 400 || height > 400)
        {
            this.scaleValue = 0.5;
        }
        else
        {
            this.scaleValue = 1;
        }


        mini = img.getScaledInstance((int)(img.getWidth() * this.scaleValue), (int)(img.getHeight() * this.scaleValue),Image.SCALE_SMOOTH);

        miniBi = Pictimage.toBufferedImage(mini);

        //Création de la miniature de l'image selon l'axe vertical
        this.biRotaAH = applyRotation(miniBi,true);

        //Création de la miniature de l'image d'origine
        this.biOri = miniBi;

        //Création de la miniature de l'image selon l'axe horizontal
        this.biRotaH = applyRotation(miniBi,false);

        //JPanel contenant l'image et le jlabel de l'image verticale
        this.jbRotaAH = new JButton(new ImageIcon(this.biRotaAH));
        this.jbRotaAH.addActionListener(new EcouteurRotationAHH(this,true));

        this.jpRotaAH.add(this.jbRotaAH,  BorderLayout.CENTER);



        //JPanel contenant l'image et le jlabel de l'origine
        this.jbOri = new JButton(new ImageIcon(this.biOri));
        this.jbOri.addActionListener(new EcouteurOK(this));
        this.jbOri.addMouseListener(new EcouteurBoutonAppliquer(this));

        this.jpOri.add(this.jbOri,  BorderLayout.CENTER);



        //JPanel contenant l'image et le jlabel de l'image horizontale
        this.jbRotaH = new JButton(new ImageIcon(this.biRotaH));
        this.jbRotaH.addActionListener(new EcouteurRotationAHH(this,false));

        this.jpRotaH.add(this.jbRotaH,  BorderLayout.CENTER);

         this.jp.add(jpRotaAH, BorderLayout.WEST);
         this.jp.add(jpOri, BorderLayout.CENTER);
         this.jp.add(jpRotaH, BorderLayout.EAST);

         this.jd.add(jp);
         this.jd.pack();


         Dimension d = this.jd.getSize();
         Dimension dScreen = this.jd.getToolkit().getScreenSize();
         this.jd.setLocation((int)(dScreen.getWidth()/2-d.getWidth()/2), (int)(dScreen.getHeight()/2-d.getHeight()/2));

         //Icone pour appliquer l'effet
        JLayeredPane jlp  = this.jd.getLayeredPane();
        jlApply = new JLabel (new ImageIcon("./lib/Icons/Plugins/apply.png"));

        Dimension dApply = jlApply.getPreferredSize();
        jlApply.setBounds((int)(d.getWidth()/2 - dApply.getWidth()/2),(int)(d.getHeight()/2 - dApply.getHeight()/2), (int)dApply.getWidth(), (int)dApply.getHeight());

        jlp.add(jlApply);

        this.jd.addWindowListener(new WEcouteurFerme(this));
         this.jd.setResizable(false);
         this.jd.setVisible(true);
    }


    public BufferedImage applyEffectIcon(BufferedImage img)
    {

        BufferedImage icon = applyRotation(img,false);
        return icon;
    }


    /**
     * Applique les différents effets à l'image courante et renvoie l'image modifié
     * @param img image courante
     * @return imgModif image modifiée
     */
    public BufferedImage applyEffect(BufferedImage img)
    {
         BufferedImage bi = img;
         effect = ((effect % 4) + 4 ) % 4;

         if(effect == 1)
         {
             bi = applyRotation(img, false);
         }
         else if(effect == 2)
         {
             bi = applyRotation(applyRotation(img,false),false);
         }
         else if(effect == 3)
         {
             bi = applyRotation(img,true);
         }

         effect = 0;
        return bi;
    }

    /**
     *
     * @return
     */
    public int getNbApplyPlug()
    {
        return nbApplyPlug;
    }


    /**
     * Applique une rotation sur l'image entrée en parametre selon le sens horaire ou anti horaire
     * @param img image courante
     * @param ah vrai si rotation anti horaire, faux sinon
     * @return
     */
    public BufferedImage applyRotation(BufferedImage img, Boolean ah)
    {
        BufferedImage imgNew = new BufferedImage( img.getHeight(), img.getWidth(), img.getType() );

            for ( int i = 0; i < img.getHeight(); i++ )
            {
                for ( int j = 0; j < img.getWidth(); j++ )
                {
                    int pixel = img.getRGB( j, i );

                    if ( ah ) //Si rotation dans le sens anti horaire
                    {
                        imgNew.setRGB( img.getHeight() - i - 1, j, pixel );
                    }
                    else
                    {
                        imgNew.setRGB( i, img.getWidth() - j - 1, pixel );
                    }
                }
            }
            return imgNew;
    }
    



    /**
     * prévisualisation sens anti horaire
     */
    public void previsualiserRotationAH()
    {
        this.effect = effect - 1;
       
        BufferedImage bi = new BufferedImage(this.biOri.getWidth(), this.biOri.getHeight(), biOri.getType());
        bi = applyRotation(this.biOri,true);

        this.biOri = bi;

        this.biRotaH = applyRotation(this.biOri,false);
        this.biRotaAH = applyRotation(this.biOri,true);
        this.jbRotaAH.setIcon(new ImageIcon(this.biRotaAH));
        this.jbRotaH.setIcon(new ImageIcon(this.biRotaH));
        this.jbOri.setIcon(new ImageIcon(this.biOri));

        this.jd.pack();
    }

    /**
     *Prévisualisation rotation sens horaire
     */
    public void previsualiserRotationH()
    {
        this.effect = effect + 1;

        BufferedImage bi = new BufferedImage(this.biOri.getWidth(), this.biOri.getHeight(), BufferedImage.TYPE_INT_RGB);
        bi = applyRotation(this.biOri,false);

        this.biOri = bi;

        this.biRotaAH = applyRotation(this.biOri,true);
        this.biRotaH = applyRotation(this.biOri,false);

        this.jbRotaH.setIcon(new ImageIcon(this.biRotaH));
        this.jbRotaAH.setIcon(new ImageIcon(this.biRotaAH));
        this.jbOri.setIcon(new ImageIcon(this.biOri));

        this.jd.pack();
    }

    /**
     * Affiche l'icone validé sur le bouton valider si l'utilisateur se trouve dessu
     * @param entre boolean
     */
    public void modifBtnApply(boolean entre)
    {
      jlApply.setVisible(entre);
    }
}
