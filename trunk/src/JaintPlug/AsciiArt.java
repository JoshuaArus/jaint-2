package JaintPlug;


import JaintPlug.asciiArt.*;
import Jaint.*;

import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.*;


/**
 * Applique un effet d'asciiArt sur l'image
 * @author Jonas
 */

public class AsciiArt implements Plugin
{
    private boolean estActive = true;
    private boolean ok = false;
    private int nbApplyPlug = 1;
    private double scaleValue = 1;
    private int taille;

    private BufferedImage biOri;
    private BufferedImage biModif;

    
    private JButton jbModif;

    private JDialog jd;
    private JPanel jpImage;
    private JPanel jpText;
    private JPanel jpPrincipal;

    private JLabel jlApply;

    private JaintSpinner jsTaille;

    private final int NBCHAR = 200;

    //Tableau des caractères ordonnées suivant leur luminosité.
    private CharValue[] charTable = new CharValue[NBCHAR];


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
        return("Ascii Art");
    }

    public String getDescri()
    {
        return("Des caractères constituent l'image de base en fonction de la luminosité");
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

            BufferedImage bi = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

            if(ok)
            {
                bi = applyEffectPlugin(img);

                return bi;
            }
            else
            {
                return img;
            }
    }

    public BufferedImage applyEffectIcon(BufferedImage img)
    {
        this.taille = 2;
        BufferedImage icon = applyEffectPlugin(img);
        return icon;
    }

    /**
     * Applique l'effet à l'image en parametre et retourne l'image modifiée
     * @param img BufferedImage image courante
     * @return biModif BufferedImage image modifié
     */
    public BufferedImage applyEffectPlugin(BufferedImage img)
    {

        BufferedImage result;
        if(this.taille>img.getWidth() || this.taille>img.getHeight())
        {
            result = img;
        }

        else
        {
            genereTable();
            int width = img.getWidth() - img.getWidth()%this.taille;
            int height = img.getHeight() - img.getHeight()%this.taille;
            result = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
            Graphics graph = result.getGraphics();
            Font font = new Font("ascii",Font.PLAIN,this.taille);
            graph.setFont(font);
            graph.setColor(Color.WHITE);
            graph.fillRect(0, 0, width, height);
            int moyenne, alphaMoy;
            for(int k=0;k<width;k+=this.taille)
            {
                for(int l=0;l<height;l+=this.taille)
                {
                    moyenne=0;
                    alphaMoy=0;
                    for(int i=0;i<this.taille;i++)
                    {
                        for(int j=0;j<this.taille;j++)
                        {
                            Color c = new Color(img.getRGB(i+k, j+l));
                            alphaMoy+=c.getAlpha();
                            moyenne+=(c.getRed()+c.getGreen()+c.getBlue())/3;
                        }
                    }

                    alphaMoy/=this.taille*this.taille;
                    moyenne/=this.taille*this.taille;
                    int i=0;
                    int value = charTable[i].value;
                    while(i<NBCHAR && value<moyenne)
                    {
                        i++;
                        value = charTable[i].value;
                    }
                    Color color = new Color(0,0,0,alphaMoy);
                    graph.setColor(color);
                    graph.drawString(charTable[i].caractere,k,(l+(this.taille*4/5)));
                }
            }
        }

        return result;

    }

 
    public int getNbApplyPlug()
    {
        return nbApplyPlug;
    }

    /**
     * Affiche la fenetre du plugin, en prenant en parametre l'image courante
     * @param img image courante
     */
    public void showDialog(BufferedImage img)
    {
        this.taille = 1;
        Image mini;
        BufferedImage miniBi;

        this.jd = new JDialog(JFrame.getFrames()[0], "Ascii Art", true);
        this.jpPrincipal = new JPanel(new BorderLayout());

        this.jpImage = new JPanel();
        this.jpText = new JPanel();


        this.jsTaille = new JaintSpinner();
        this.jsTaille.getJtf_nombre().getDocument().addDocumentListener(new EcouteurSpinner(this));
        
         

        this.jpText.add(jsTaille);

        

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

        this.biOri = miniBi;
        this.biModif = applyEffectPlugin(miniBi);

        //Creation et ajout des ecouteurs aux deux miniatures
        this.jbModif = new JButton(new ImageIcon(this.biModif));
        this.jbModif.addActionListener(new EcouteurOK(this));
        this.jbModif.addMouseListener(new EcouteurBoutonAppliquer(this));

        //On fixe la taille du bouton, c'est moche je sais ...
        this.jbModif.setPreferredSize(new Dimension((int)(img.getWidth() * this.scaleValue)+20, (int)(img.getHeight() * this.scaleValue)+20));

        
        this.jpImage.add(this.jbModif);

        this.jpPrincipal.add(this.jpImage, BorderLayout.CENTER);
        this.jpPrincipal.add(this.jpText, BorderLayout.SOUTH);


        

        this.jd.add(jpPrincipal);
        this.jd.pack();


        Dimension d = this.jd.getSize();
        Dimension dScreen = this.jd.getToolkit().getScreenSize();
        this.jd.setLocation((int)(dScreen.getWidth()/2-d.getWidth()/2), (int)(dScreen.getHeight()/2-d.getHeight()/2));

        //Icone pour appliquer l'effet
        JLayeredPane jlp  = this.jd.getLayeredPane();
        jlApply = new JLabel (new ImageIcon("./lib/Icons/Plugins/apply.png"));

        Dimension dApply = jlApply.getPreferredSize();
        Dimension dBtnApply = this.jbModif.getPreferredSize();
        jlApply.setBounds((int)(d.getWidth()/2 - dApply.getWidth()/2),(int)(dBtnApply.getHeight()/2 - dApply.getHeight()/2), (int)dApply.getWidth(), (int)dApply.getHeight());

        jlp.add(jlApply);

        this.jd.addWindowListener(new WEcouteurFerme(this));
        this.jd.setResizable(false);
        this.jd.setVisible(true);
        
    }
    
    /**
     * Prévisualiser l'image sur l'apercu
     */
    public void previsualiser()
    {
        this.biModif = applyEffectPlugin(this.biOri);
        this.jbModif.setIcon(new ImageIcon(this.biModif));
    }

    /**
     * Recupere la taille des caractères dans le JaintSpiner
     */
    public void setTaille()
    {
        
        this.taille =  this.jsTaille.getValue();
    }

    

    /**
    * La fonction genereTable permet de generer un tableau de caractères en faisant
    * correspondre le caractère avec la luminosité de ce caractère
    *
    */
    public void genereTable()
    {
        for (int i=0; i<NBCHAR;i++)
        {
            BufferedImage bf = new BufferedImage(10,10,BufferedImage.TYPE_INT_RGB);
            Graphics g = bf.getGraphics();
            Font font = new Font("ascii",Font.PLAIN,10);
            g.setFont(font);
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, 10, 10);
            char[] c = Character.toChars(i+32);
            g.setColor(Color.BLACK);
            g.drawString(c[0]+"", 0, 10*4/5);
            int value=0;
            for(int k=0;k<10;k++)
            {
                for(int l=0;l<10;l++)
                {
                Color col = new Color(bf.getRGB(k, l));
                value += (col.getRed()+col.getGreen()+col.getBlue())/3;
                }
            }
            value/=10*10;
            CharValue cv = new CharValue(c[0]+"",value);
            charTable[i]=cv;
        }
            trieur();
    }

    /**
    *	Trie les caractères et les places de facon ordonnée dans le tableau charTable.
    */
    public void trieur()
    {
        for(int i=0;i<NBCHAR;i++)
        {
            for(int j=i+1;j<NBCHAR;j++)
            {
                // trie le tableau de caractere en fonction de la luminosité de chaque caractère.
                if(charTable[i].value>charTable[j].value)
                {
                    CharValue cv = new CharValue(charTable[i].caractere, charTable[i].value);
                    charTable[i] = charTable[j];
                    charTable[j] = cv;
                }
            }
        }
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
