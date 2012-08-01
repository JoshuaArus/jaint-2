package Jaint;

import JaintListener.EcouteurLargeurHauteur;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * JDialog permettant de créer un nouveau fichier
 * @author Jeff
 */
public class NouveauFichier extends JDialog
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6550863654539037235L;
	private IHM ihm;
    JTextField txtHauteur = new JTextField(3);
    JTextField txtLargeur = new JTextField(3);
    private BufferedImage img;
    private boolean ok;
    /**
     * Pas joli, mais plus le temps de modifier :)
     */
    public JButton btnCreer;

    /**
     * Construit le JDialog
     * @param i
     */
    public NouveauFichier(IHM i)
    {
        super(i, "Nouveau Fichier", true);
        ihm = i;

        JLabel lblHauteur = new JLabel(new ImageIcon("./lib/Icons/Plugins/hauteur.png"));
        JLabel lblLargeur = new JLabel(new ImageIcon("./lib/Icons/Plugins/largeur.png"));
        

        btnCreer = new JButton(new ImageIcon("./lib/Icons/Plugins/apply2.png"));
        JButton btnAnnuler = new JButton(new ImageIcon("./lib/Icons/Plugins/annuler.png"));

        JPanel jpHauteur = new JPanel();
        jpHauteur.add(lblHauteur);
        jpHauteur.add(txtHauteur);

        JPanel jpLargeur = new JPanel();
        jpLargeur.add(lblLargeur);
        jpLargeur.add(txtLargeur);

        JPanel jpTxt = new JPanel(new BorderLayout());
        jpTxt.add(jpHauteur, BorderLayout.NORTH);
        jpTxt.add(jpLargeur, BorderLayout.SOUTH);

        JPanel jpBtn = new JPanel();
        jpBtn.add(btnCreer);
        jpBtn.add(btnAnnuler);

        this.add(jpTxt, BorderLayout.NORTH);
        this.add(jpBtn, BorderLayout.SOUTH);

        btnCreer.setEnabled(false);

        Dimension dimFenetre = ihm.getSize();
        Dimension d = this.getPreferredSize();

        this.setLocation((int)(dimFenetre.getWidth() / 2 - d.getWidth() / 2), (int)(dimFenetre.getHeight() / 2 - d.getHeight() / 2));


        btnAnnuler.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                cancel();
            }
        });

        btnCreer.addActionListener(new ActionListener()
        {
           public void actionPerformed(ActionEvent ae)
           {
               create();
           }
        });


        this.txtLargeur.setDocument(new EcouteurLargeurHauteur(this));
        this.txtHauteur.setDocument(new EcouteurLargeurHauteur(this));
        this.txtHauteur.getDocument().addDocumentListener(new EcouteurLargeurHauteur(this));
        this.txtLargeur.getDocument().addDocumentListener(new EcouteurLargeurHauteur(this));
        

        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);


    }
    private void cancel()
    {
        this.dispose();
    }

    private void create()
    {
        String l = txtLargeur.getText();
        String h = txtHauteur.getText();

        if(!l.equals("") && !h.equals(""))
        {
            int largeur = Integer.parseInt(l);
            int hauteur = Integer.parseInt(h);

            img = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_INT_RGB);
            Graphics g = img.getGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, largeur, hauteur);

            ok = true;
            this.dispose();
        }

    }

    /**
     * Renvoie l'image créé
     * @return L'image crée
     */
    public BufferedImage getBufferedImage()
    {
        return img;
    }

    /**
     * Teste si on a cliqué sur OK
     * @return Résultat du test
     */
    public boolean getOk()
    {
        return ok;
    }

    /**
     * Renvoie la largeur entrée
     * @return La largeur
     */
    public String getLargeur()
    {
        return txtLargeur.getText();
    }

    /**
     * Renvoie la hauteur entrée
     * @return La hauteur
     */
    public String getHauteur()
    {
        return txtHauteur.getText();
    }



}
