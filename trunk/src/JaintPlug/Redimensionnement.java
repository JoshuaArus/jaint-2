package JaintPlug;

/**
 *
 * @author jonas
 */

import Jaint.*;
import JaintPlug.redim.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


/**
 *
 * @author Jonas & Joshua
 */
public class Redimensionnement implements Plugin
{
    private int nbApplyPlug = 1;
    private boolean ok = false;
    private boolean estActive = true;

    private int hauteurOrignine = 1;

    private int largeurOrigine = 1;

    private BufferedImage biModif;

    private JDialog jd;
    private JTextField jtfHauteur;
    private JTextField jtfLargeur;
    private JButton jbValider;
    private JCheckBox jcbRatio;

    private JPanel jpPrincipal;
    private JPanel jpHauteur;
    private JPanel jpLargeur;
    private JLabel jlHauteur;
    private JLabel jlLargeur;
    private JPanel jpTaille;
    private JPanel jpRatio;
    private JLabel jlRatio;
    

    public String getName()
    {
            return("Redimensionnement");
    }

    public String getDescri()
    {
            return("Permet de redimensionner l'image");
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
     *
     * @return
     */
    public int getNbApplyPlug()
    {
        return nbApplyPlug;
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

        this.hauteurOrignine = img.getHeight();
        this.largeurOrigine = img.getWidth();

        this.showDialog(img);

         

        if (ok)
        {

                BufferedImage bi = applyEffect(img);

                return bi;
        }
        else
        {
                return null;
        }
    }

    /**
     * Affiche la fenetre du plugin, en prenant en parametre l'image courante
     * @param img image courante
     */
    public void showDialog(BufferedImage img)
    {
        this.jd = new JDialog(JFrame.getFrames()[0], "Redimensionnement", true);
        this.jpPrincipal = new JPanel(new BorderLayout());
        this.jpHauteur = new JPanel();
        this.jpLargeur = new JPanel();
        this.jpRatio = new JPanel();
        this.jpTaille = new JPanel(new BorderLayout());
        this.jlHauteur = new JLabel(new ImageIcon("./lib/Icons/Plugins/hauteur.png"));
        this.jlLargeur = new JLabel(new ImageIcon("./lib/Icons/Plugins/largeur.png"));
        this.jlRatio = new JLabel (new ImageIcon("./lib/Icons/Plugins/ratio.png"));
        this.jtfHauteur = new JTextField();
        this.jtfLargeur = new JTextField();
        this.jbValider = new JButton(new ImageIcon("./lib/Icons/Plugins/apply2.png"));
        this.jbValider.addActionListener(new EcouteurOK(this));
        this.jcbRatio = new JCheckBox();
        

       this.jtfHauteur.setPreferredSize(new Dimension(100,30));
       this.jtfHauteur.setHorizontalAlignment(JTextField.RIGHT);
       this.jtfLargeur.setHorizontalAlignment(JTextField.RIGHT);
       this.jtfLargeur.setPreferredSize(new Dimension(100,30));


        this.jpHauteur.add(this.jlHauteur);
        this.jpHauteur.add(this.jtfHauteur);

        this.jpLargeur.add(this.jlLargeur);
        this.jpLargeur.add(this.jtfLargeur);

        this.jpRatio.add(this.jlRatio);
        this.jpRatio.add(this.jcbRatio);

        this.jpTaille.add(this.jpHauteur, BorderLayout.NORTH);
        this.jpTaille.add(this.jpLargeur, BorderLayout.CENTER);
        this.jpTaille.add(this.jpRatio, BorderLayout.SOUTH);
        this.jpPrincipal.add(this.jpTaille, BorderLayout.CENTER);
        this.jpPrincipal.add(this.jbValider, BorderLayout.SOUTH);




        this.jd.add(this.jpPrincipal);
        this.jd.pack();



        Dimension d = this.jd.getSize();
        Dimension dScreen = this.jd.getToolkit().getScreenSize();
        this.jd.setLocation((int)(dScreen.getWidth()/2-d.getWidth()/2), (int)(dScreen.getHeight()/2-d.getHeight()/2));

        this.jtfLargeur.setDocument(new DocumentLH(this,this.jtfLargeur,this.jtfHauteur));
        this.jtfHauteur.setDocument(new DocumentLH(this,this.jtfHauteur,this.jtfLargeur));
        //this.jtfHauteur.getDocument().addDocumentListener(new DocumentLH(this,this.jtfLargeur));
        //this.jtfLargeur.getDocument().addDocumentListener(new DocumentLH(this,this.jtfHauteur));

        this.jtfLargeur.setText(this.largeurOrigine+"");
        this.jtfHauteur.setText(this.hauteurOrignine+"");

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

    public BufferedImage applyEffectIcon(BufferedImage img)
    {
        BufferedImage icon = null;
        try {
            icon = ImageIO.read(new File("./lib/Icons/Plugins/ratio.png"));
        } catch (IOException ex) {
            icon = new BufferedImage(5,5,BufferedImage.TYPE_INT_ARGB);
        }
       
        return icon;

    }

    /**
     * Applique l'effet a l'image en parametre et retourne l'image modifiee
     * @param img BufferedImage image courante
     * @return biModif BufferedImage image modifie
     */
    public BufferedImage applyEffect(BufferedImage img)
    {
        Image imgModif;
        int hauteur = Integer.parseInt(this.jtfHauteur.getText());
        int largeur = Integer.parseInt(this.jtfLargeur.getText());

        imgModif = img.getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH);

        this.biModif = Pictimage.toBufferedImage(imgModif);
        return biModif;
    }

    /**
     *Retourne le bouton valider
     * @return jbValider
     */
    public JButton getJbValider()
    {
        return jbValider;
    }

    /**
     *Retourne jtfHauteur
     * @return jtfHauteur
     */
    public JTextField getJtfHauteur()
    {
        return jtfHauteur;
    }

    /**
     *Retourne jtfLargeur
     * @return jtfLargeur
     */
    public JTextField getJtfLargeur()
    {
        return jtfLargeur;
    }

    /**
     *Retourne jcbRatio
     * @return jcbRatio
     */
    public JCheckBox getJcbRatio()
    {
        return jcbRatio;
    }

    /**
     *Retourne hauteurOrignine
     * @return hauteurOrignine
     */
    public int getHauteurOrigine() {
        return hauteurOrignine;
    }

    /**
     *Retourne largeurOrigine
     * @return largeurOrigine
     */
    public int getLargeurOrigine() {
        return largeurOrigine;
    }

}
