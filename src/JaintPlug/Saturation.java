package JaintPlug;



import java.awt.image.BufferedImage;
import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.Image;

import JaintPlug.saturation.*;
import Jaint.*;


import Jaint.Plugin;

/**
 *
 * @author Jonas
 */
public class Saturation implements Plugin
{
    private ArrayList<Integer> lstEffet = new ArrayList<Integer>();
    private boolean estActive = true;
    private boolean ok = false;
    private int nbApplyPlug = 10;
    private double scaleValue = 1;


    private BufferedImage biSatMoins;
    private BufferedImage biOri;
    private BufferedImage biSatPlus;
    private JDialog jd;
    private JPanel jp;
    private JPanel jpSatMoins;
    private JPanel jpOri;
    private JPanel jpSatPlus;



    private JButton jbSatMoins;
    private JButton jbOri;
    private JButton jbSatPlus;
    private JLabel jlSatMoins;
    private JLabel jlSatPlus;
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
        return("Saturation");
    }

    public String getDescri()
    {
        return("Permet de regler la saturation de l'image");
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
        if(img.getType() != BufferedImage.TYPE_INT_ARGB)
            {
                BufferedImage src = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = src.createGraphics();
                g.drawRenderedImage(img, null);
                g.dispose();
                img = src;
            }

            this.biOri = img;

            this.showDialog(img);

            BufferedImage bi = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);

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

        this.jd = new JDialog(JFrame.getFrames()[0], "Saturation", true);
        this.jp = new JPanel(new BorderLayout());

        this.jpSatMoins = new JPanel(new BorderLayout());
        this.jpOri = new JPanel(new BorderLayout());
        this.jpSatPlus = new JPanel(new BorderLayout());



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


        mini = img.getScaledInstance((int)(img.getWidth() * this.scaleValue), (int)(img.getHeight() * this.scaleValue),Image.SCALE_DEFAULT);

        miniBi = Pictimage.toBufferedImage(mini);

        //Création de la miniature de l'image flou
        this.biSatMoins = applySatMoins(miniBi);

        //Création de la miniature de l'image d'origine
        this.biOri = miniBi;

        //Création de la miniature de l'image plus nette
        this.biSatPlus = applySatPlus(miniBi);

        //JPanel contenant l'image et le jlabel de flou
        this.jbSatMoins = new JButton(new ImageIcon(this.biSatMoins));
        this.jbSatMoins.addActionListener(new EcouteurSatMoins(this));
        this.jlSatMoins = new JLabel(new ImageIcon("./lib/Icons/Plugins/satMoins.png"));


        this.jpSatMoins.add(this.jbSatMoins,  BorderLayout.CENTER);
        this.jpSatMoins.add(this.jlSatMoins,  BorderLayout.WEST);


        //JPanel contenant l'image et le jlabel de l'origine
        this.jbOri = new JButton(new ImageIcon(this.biOri));
        this.jbOri.addActionListener(new EcouteurOK(this));
        this.jbOri.addMouseListener(new EcouteurBoutonAppliquer(this));

        this.jpOri.add(this.jbOri,  BorderLayout.CENTER);

        //JPanel contenant l'image et le jlabel de netteté
        this.jbSatPlus = new JButton(new ImageIcon(this.biSatPlus));
        this.jbSatPlus.addActionListener(new EcouteurSatPlus(this));
        this.jlSatPlus = new JLabel(new ImageIcon("./lib/Icons/Plugins/satPlus.png"));


        this.jpSatPlus.add(this.jbSatPlus,  BorderLayout.CENTER);
        this.jpSatPlus.add(this.jlSatPlus,  BorderLayout.EAST);

         this.jp.add(jpSatMoins, BorderLayout.WEST);
         this.jp.add(jpOri, BorderLayout.CENTER);
         this.jp.add(jpSatPlus, BorderLayout.EAST);

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

        BufferedImage icon = applySatPlus(img);
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

        for(Integer i : lstEffet)
        {

            if(i == 0)
            {
                bi = applySatPlus(bi);
            }
            if(i == 1)
            {
                bi = applySatMoins(bi);
            }
        }
        lstEffet = new ArrayList<Integer>();
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
     * Diminue la saturation de l'image passée en parametre
     * @param img image à modifier
     * @return img modifiée
     */
    public BufferedImage applySatMoins(BufferedImage img)
    {
        BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		for (int i=0; i<bi.getWidth(); i++)
		{
			for (int j=0; j<bi.getHeight(); j++)
			{
                            int e = img.getRGB(i,j);

                            if (((e >>24 ) & 0xFF) != 0)
                            {
				int rgb = img.getRGB(i, j);
				int r = (rgb & 0xff0000)>>16;
				int g = (rgb & 0xff00)>>8;
				int b = (rgb & 0xff);

				int moy = (r+g+b)/3;

				r = (int) (r - 10/100.*(r-moy));
				g = (int) (g - 10/100.*(g-moy));
				b = (int) (b - 10/100.*(b-moy));

				if (r<0)r = 0; if (r > 255) r = 255;
				if (g<0) g = 0; if (g > 255) g = 255;
				if (b<0) b = 0; if (b > 255) b = 255;

				int newRGB = (0xff<<24)+(r<<16)+(g<<8)+(b);

				bi.setRGB(i, j, newRGB);
                            }
                            else
                            {
                               bi.setRGB(i,j,e);
                            }

                    }
		}

		return bi;

    }

    /**
     * Augmente la saturation de l'image passée en parametre
     * @param img image à modifier
     * @return img modifiée
     */
    public BufferedImage applySatPlus(BufferedImage img)
    {
        BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		for (int i=0; i<bi.getWidth(); i++)
		{
			for (int j=0; j<bi.getHeight(); j++)
			{
                            int e = img.getRGB(i,j);

                            if (((e >>24 ) & 0xFF) != 0)
                            {
				int rgb = img.getRGB(i, j);
				int r = (rgb & 0xff0000)>>16;
				int g = (rgb & 0xff00)>>8;
				int b = (rgb & 0xff);

				int moy = (r+g+b)/3;

				r = (int) (r + 10/100.*(r-moy));
				g = (int) (g + 10/100.*(g-moy));
				b = (int) (b + 10/100.*(b-moy));

				if (r<0)r = 0; if (r > 255) r = 255;
				if (g<0) g = 0; if (g > 255) g = 255;
				if (b<0) b = 0; if (b > 255) b = 255;

				int newRGB = (0xff<<24)+(r<<16)+(g<<8)+(b);

				bi.setRGB(i, j, newRGB);
                            }
                            else
                            {
                                bi.setRGB(i,j,e);
                            }
			}
		}

		return bi;
    }

    /**
     *Présualiser et mise a jour des boutons
     */
    public void previsualiserSatPlus()
    {
        
        lstEffet.add(0);
        BufferedImage bi = new BufferedImage(this.biOri.getWidth(), this.biOri.getHeight(), BufferedImage.TYPE_INT_ARGB);
        bi = applySatPlus(this.biOri);

        this.biOri = bi;
        this.biSatPlus = applySatPlus(this.biOri);
        this.biSatMoins = applySatPlus(this.biOri);

        this.jbSatPlus.setIcon(new ImageIcon(this.biSatPlus));
        this.jbSatMoins.setIcon(new ImageIcon(this.biSatMoins));
        this.jbOri.setIcon(new ImageIcon(this.biOri));

        this.jd.pack();
    }

    /**
     *Présualiser et mise a jour des boutons
     */
    public void previsualiserSatMoins()
    {
        lstEffet.add(1);

        BufferedImage bi = new BufferedImage(this.biOri.getWidth(), this.biOri.getHeight(), BufferedImage.TYPE_INT_ARGB);
        bi = applySatMoins(this.biOri);

        this.biSatPlus = applySatPlus(this.biOri);
        this.biOri = bi;
        this.biSatMoins = applySatMoins(this.biOri);

        this.jbSatPlus.setIcon(new ImageIcon(this.biSatPlus));
        this.jbSatMoins.setIcon(new ImageIcon(this.biSatMoins));
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
