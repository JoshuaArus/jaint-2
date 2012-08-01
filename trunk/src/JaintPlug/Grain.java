package JaintPlug;


import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;



import Jaint.*;
import Jaint.Plugin;
import JaintPlug.grain.*;
/**
 *
 * @author Jonas
 */
public class Grain implements Plugin
{
    private JDialog jd;
    private JSlider js;
    private int value;
    private double scaleValue = 1;
    private boolean ok = false;

    private BufferedImage biFirst;
    private BufferedImage biNew;

    private JPanel jpimage;
    private JPanel jpInfo;
    private JPanel jp;
    
    private JButton jbModif;
    private JLabel jlApply;



    private CompteARebourds car;

    private boolean estActive = true;
    private int nbApplyPlug = 1;


    public String getName()
    {
            return("Grain");
    }

    public String getDescri()
    {
            return("Permet d'appliquer du bruit a l'image ");
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
     * Prend l'image courante en parametre et retourne l'image modifie ou non
     * @param img image courante
     * @return imgModif image modifie ou non
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

            this.biFirst = img;

            this.showDialog(img);

            BufferedImage bi = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);

            if (ok)
            {
                    bi = applyEffectPlugin(img);

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
        this.value = 0;

        this.jd = new JDialog(JFrame.getFrames()[0], "Grain", true);



        this.jp = new JPanel(new BorderLayout());

        this.js = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        this.js.setPreferredSize(new Dimension(300, 50));
        this.js.addChangeListener(new EcouteurSlider(this, car));

        this.jpInfo = new JPanel(new BorderLayout());
        jpInfo.add(new JLabel(new ImageIcon("./lib/Icons/Plugins/pingouin.png")), BorderLayout.WEST);
        jpInfo.add(this.js , BorderLayout.CENTER);
        jpInfo.add(new JLabel(new ImageIcon("./lib/Icons/Plugins/grain.png")), BorderLayout.EAST);

        

        /*Ajout de l'image reduite dans le panel nord*/
        int width = img.getWidth();
        int height = img.getHeight();

        this.jpimage = new JPanel(new BorderLayout());
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

        mini = img.getScaledInstance((int)(img.getWidth() * this.scaleValue), (int)(img.getHeight() * this.scaleValue),Image.SCALE_SMOOTH);

        miniBi = Pictimage.toBufferedImage(mini);

        this.biFirst = miniBi;
        this.biNew = applyEffectPlugin(miniBi);

        //Creation et ajout des ecouteurs aux deux miniatures
        this.jbModif = new JButton(new ImageIcon(this.biNew));
        this.jbModif.addActionListener(new OkListener(this));
        this.jbModif.addMouseListener(new EcouteurBoutonAppliquer(this));

        //On fixe la taille du bouton, c'est moche je sais ...
        this.jbModif.setPreferredSize(new Dimension((int)(img.getWidth() * this.scaleValue)+20, (int)(img.getHeight() * this.scaleValue)+20));


        this.jpimage.add(this.jbModif);

        this.jp.add(this.jpimage, BorderLayout.CENTER);
        this.jp.add(this.jpInfo, BorderLayout.SOUTH);

        this.jd.add(jp);
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

    /**
     *
     */
    public void setJsValue()
    {
            this.value = this.js.getValue();
    }



    /**
     *
     */
    public void previsualiser()
    {

        int temp = this.value;
        this.value = this.value / 10;
        this.biNew = applyEffectPlugin(this.biFirst);
        this.jbModif.setIcon(new ImageIcon(this.biNew));
        this.value = temp;
    }


    public BufferedImage applyEffectIcon(BufferedImage img)
    {
        this.value = 1;
        BufferedImage icon = applyEffectPlugin(img);
        return icon;
    }

    /**
     * Applique l'effet a l'image en parametre et retourne l'image modifiee
     * @param img BufferedImage image courante
     * @return biModif BufferedImage image modifie
     */
    public BufferedImage applyEffectPlugin(BufferedImage img)
    {

        
            BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

            Graphics2D g = bi.createGraphics();
            g.drawRenderedImage(img, null);
            g.dispose();

            int height = img.getHeight();
            int width = img.getWidth();

            for (int v = 0; v < this.value*10000; v++)
            {
                    double randomx = Math.random();
                    double randomy = Math.random();

                    int x = (int) (randomx*height);
                    int y = (int) (randomy*width);

                    int rgb = img.getRGB(y,x);

                    if (((rgb >>24 ) & 0xFF) != 0)
                    {

                        int cPix = img.getRGB(y, x);

                        double randomi = Math.random();
                        double randomj = Math.random();

                        int i = (int) (randomi*height);
                        int j = (int) (randomj*width);

                        bi.setRGB(j, i, cPix);
                    }
                    else
                    {
                         bi.setRGB(y,x,rgb);
                    }
            }

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
     * Affiche l'icone valide sur le bouton valider si l'utilisateur se trouve dessu
     * @param entre boolean
     */
    public void modifBtnApply(boolean entre)
    {
      jlApply.setVisible(entre);
    }

    
}

