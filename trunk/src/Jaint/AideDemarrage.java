package Jaint;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Le JDialog qui permet le tutoriel d'aide
 * @author Jeff
 */
public class AideDemarrage extends JDialog implements ActionListener
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7258899602421400987L;

	private IHM ihm;

    private String[] path =
    {
        "./lib/Icons/jaint2.jpg",
        "./lib/Icons/trois_palette.png",
        "./lib/Help/Images/dessin.png",
        "./lib/Help/Images/gestion.png",
        "./lib/Help/Images/option2.png",
        "./lib/Icons/jaint2.jpg",
        "./lib/Icons/rotation1.png",
        "./lib/Help/Images/pluginTuto1.png",
        "./lib/Help/Images/finTuto.png"
    };

    private ImageIcon pluginApply;

    private final int NB_PANEL = path.length;

    private ImageIcon[] tab_img = new ImageIcon[NB_PANEL];
    private ImageIcon rot2 = new ImageIcon("./lib/Icons/rotation1_2.png");
    
    private Dimension dimImg;

    private int pos = 0;

    private JCheckBox chb;
    private JButton suivant;
    private JButton precedent;
    private JButton fermer;

    private JPanel jp_btn = new JPanel();
    private JPanel jp_bas = new JPanel(new BorderLayout());
    private JPanel jp_chb = new JPanel(new BorderLayout());
    private JPanel jp_haut = new JPanel(new BorderLayout());
    private JPanel jp_labelGen = new JPanel(new BorderLayout());
    private JPanel jp_lbl1 = new JPanel();
    private JPanel jp_lbl2 = new JPanel();
    private JPanel jp_lbl3 = new JPanel();
    private JPanel jp_lbl1N2 = new JPanel(new BorderLayout());

    private JLabel lblImgShift = new JLabel();
    private JLabel lblFinShift = new JLabel();
    private ImageIcon imgShift = new ImageIcon(new ImageIcon("./lib/Icons/shift.png").getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));

    private String[] tab_lbl1 =
    {
        "Bienvenue dans Jaint 1.0",
        "Les palettes de menu",
        "La palette de dessin",
        "La palette de gestion",
        "La palette d'option",
        "Le système de glisser-déposer",
        "La rotation libre",
        "Les plugins",
        "Ce tutoriel est terminé"
    };

    private String[] tab_lbl2 =
    {
        "Ce mini tutoriel va vous apprendre les bases du logiciel",
        "Jaint ne possède pas de barre de menu mais des menus circulaires apparaissant grâce au clic de la souris",
        "Elle vous permet d'utiliser les outils de dessin et de modification de l'image",
        "Elle vous permet de créer de nouveaux fichiers, d'en ouvrir, d'en sauvegarder" ,
        "Elle vous permet de configurer Jaint comme vous le souhaitez",
        "Au lieu d'ouvrir une image avec la palette de gestion, vous pouvez utiliser le système de glisser-déposer",
        "Vous devrez cliquer sur un des boutons de rotation se trouvant aux quatre coins de Jaint",
        "Dans la plupart des plugins, vous devrez cliquer sur l'image pour valider l'effet",
        "Vous avez maintenant les bases pour utiliser Jaint"
    };

    private String reussi = "( Passez maintenant à l'étape suivante )";
    private String fin = "( Après avoir fermer ce tutoriel, vous pourrez presser F1 pour toute aide supplémentaire )";

    private JLabel lbl1 = new JLabel(tab_lbl1[0]);
    private JLabel lbl2 = new JLabel(tab_lbl2[0]);
    private JLabel lbl3 = new JLabel(" ");
    private JLabel lblImg;

    //panel qui va remplacer le bouton "suivant" lorsqu'on désactive ce bouton
    private JPanel blanc = new JPanel();
    //idem pour l'image (avec une image transparente à la place du panel)
    private ImageIcon imgBlanc = new ImageIcon("./lib/Icons/vide.png");
    private boolean level4OK = false;
    private boolean level5OK = false;
    
    /**
     * Construit le tutoriel
     * @param i
     */
    public AideDemarrage(IHM i)
    {
        super(i, "Tutoriel", true);
        ihm = i;

        jp_lbl1.add(lbl1);
        jp_lbl2.add(lbl2);
        jp_lbl3.add(lbl3);
        jp_lbl3.add(lblImgShift);
        jp_lbl3.add(lblFinShift);

        tab_img[1] = new ImageIcon("./lib/Icons/palette1.png");

        Image temp = tab_img[1].getImage();
        dimImg = new Dimension(temp.getWidth(this), temp.getHeight(this));
        imgBlanc = new ImageIcon(imgBlanc.getImage().getScaledInstance(dimImg.width, dimImg.height, Image.SCALE_FAST));
        rot2 = new ImageIcon(rot2.getImage().getScaledInstance(dimImg.width, dimImg.height, Image.SCALE_FAST));
        pluginApply = new ImageIcon(new ImageIcon("./lib/Help/Images/pluginTuto.png").getImage().getScaledInstance(dimImg.width, dimImg.height, Image.SCALE_FAST));
        
        for(int j = 0; j < NB_PANEL; j++)
        {
            initImageSize(j);
        }

        initPanelGnl(0, false);

        chb = new JCheckBox("Ne plus afficher");
        suivant = new JButton("Suivant");
        precedent = new JButton("Précédent");
        fermer = new JButton("Fermer");

        if(!ihm.isHelpSelected())
            chb.setSelected(true);

        blanc.setPreferredSize(suivant.getPreferredSize());
        precedent.setVisible(false);

        jp_btn.add(precedent);
        jp_btn.add(suivant);
        jp_btn.add(fermer);

        
        jp_haut.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), ""));


        jp_chb.add(chb, BorderLayout.WEST);
        jp_bas.add(jp_chb, BorderLayout.SOUTH);


        jp_haut.add(jp_labelGen, BorderLayout.NORTH);
        jp_haut.add(jp_btn, BorderLayout.EAST);


        fermer.addActionListener(this);
        precedent.addActionListener(this);
        suivant.addActionListener(this);
  
        jp_labelGen.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                if(pos != 0)
                {
                    switch(pos)
                {
                    case 1 : break;
                    
                    case 2 :
                        if(e.getButton() == MouseEvent.BUTTON3 && !e.isShiftDown() && !e.isControlDown())
                        {
                            jp_labelGen.removeAll();
                            initPanelGnl(pos, true);
                            jp_labelGen.revalidate();
                            suivant.setEnabled(true);
                        }
                    break;

                    case 3 :
                        if(e.getButton() == MouseEvent.BUTTON3 && e.isShiftDown() && !e.isControlDown())
                        {
                            jp_labelGen.removeAll();
                            initPanelGnl(pos, true);
                            jp_labelGen.revalidate();
                            suivant.setEnabled(true);
                        }
                    break;

                    case 4 :
                        if(e.getButton() == MouseEvent.BUTTON3 && e.isControlDown() && !e.isShiftDown())
                        {
                            jp_labelGen.removeAll();
                            initPanelGnl(pos, true);
                            jp_labelGen.revalidate();
                            suivant.setEnabled(true);
                        }
                    break;

                        case 5 : break;

                        default :
                            jp_labelGen.removeAll();
                            initPanelGnl(pos, true);
                            jp_labelGen.revalidate();
                    break;
                }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                if(pos == 6)//35054
                {
                    level4OK = true;
                    jp_labelGen.removeAll();
                    initPanelGnl(pos, false);
                    jp_labelGen.revalidate();
                    lbl3.setText(reussi);                    
                }
                else if(pos == 7)
                {
                    level5OK = true;
                    jp_labelGen.removeAll();
                    initPanelGnl(pos, false);
                    jp_labelGen.revalidate();
                    lbl3.setText(reussi);                    
                }
                

                if(pos < NB_PANEL - 1 && pos > 4)
                {
                    suivant.setEnabled(true);
                }
            }
        });

        chb.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    DataOutputStream ds = new DataOutputStream(new FileOutputStream("./lib/tuto.config", false));
                    
                    boolean result = chb.isSelected();
                    ds.writeBoolean(!result);
                    ihm.setHelpState(!result);
                    ds.close();                    
                }
                catch (IOException ex)
                {

                }
            }

        });


        this.add(jp_haut, BorderLayout.NORTH);
        this.add(jp_bas, BorderLayout.SOUTH);

        Dimension dimIhm;

        if(!ihm.isBeginningRead())
           dimIhm = ihm.getSize();
        else
            dimIhm = Toolkit.getDefaultToolkit().getScreenSize();
            
       
        this.pack();
        this.setSize((int)(dimIhm.width / 1.75), this.getSize().height);
        ihm.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public void actionPerformed(ActionEvent e) 
    {
        JButton btn = (JButton) e.getSource();
        
        if(btn == precedent)
        {
            level4OK = false;
            level5OK = false;

            if(pos == NB_PANEL - 1)
            {
                jp_btn.removeAll();

                jp_btn.add(precedent);
                jp_btn.add(suivant);
                jp_btn.add(fermer);
                blanc.setVisible(false);
                suivant.setVisible(true);

                jp_btn.revalidate();
                jp_btn.repaint();
            }
            else if(pos != 0 || pos != 1)
                suivant.setEnabled(false);

            if(pos > 0)
            {
                jp_labelGen.removeAll();
                pos--;

                initPanelGnl(pos, false);
                jp_labelGen.revalidate();

                if(pos == 0 || pos == 1)
                {
                    if(pos ==0)
                        precedent.setVisible(false);
                    suivant.setEnabled(true);
                }
                    
            }
        }
        else if(btn == suivant)
        {
            if(!precedent.isVisible())
                precedent.setVisible(true);

            if(pos < NB_PANEL - 1)
            {
                jp_labelGen.removeAll();
                pos++;

                initPanelGnl(pos, false);
                
                jp_labelGen.revalidate();
                
                if(pos != 1)
                    suivant.setEnabled(false);
            }

            if(pos == NB_PANEL - 1)
            {
                jp_btn.removeAll();
                
                jp_btn.add(precedent);
                jp_btn.add(blanc);
                jp_btn.add(fermer);
                suivant.setVisible(false);
                blanc.setVisible(true);
                jp_btn.revalidate();
            }
        }
        else
        {
            dispose();
        }

    }

    private void initPanelGnl(int index, boolean clic)
    {
        
        lbl1.setText(this.tab_lbl1[index]);
        lbl2.setText(tab_lbl2[index]);

        if(index != 3 || clic)
        {
            lblImgShift.setIcon(null);
            lblFinShift.setText("");
        }

        if(clic && index != NB_PANEL - 1)
        {
            if(index == 6)
                lbl2.setText("En maintenant ce bouton enfoncé et en déplaçant votre souris, l'image pivotera. Relâcher le bouton pour valider");
            lbl3.setText(reussi);
        }       
        else
        {
            switch(index)
            {
                case 0 :
                    lbl3.setText(" ");
                break;

                case 1 :
                    lbl3.setText("Vous allez maintenant apprendre à ouvrir ces palettes");
                break;

                case 2 :
                    lbl3.setText("Cliquez sur le bouton droit dans la zone ci-dessus pour afficher la palette de dessin");
                break;

                case 3 :
                    lbl3.setText("Maintenez le bouton SHIFT (");
                    lblFinShift.setText(" Majuscule ) enfoncé et cliquez avec le bouton droit dans la zone ci-dessus");
                    lblImgShift.setIcon(imgShift);
                break;

                case 4 :
                    lbl3.setText("Maintenez le bouton CTRL enfoncé et cliquez avec le bouton droit dans la zone ci-dessus");
                break;

                case 5 :
                    lbl3.setText("Déplacer une image de votre ordinateur directement dans Jaint ( Pas encore disponible dans ce tuto )");
                break;

                case 6 :
                    if(!level4OK)
                        lbl3.setText("Cliquez sur l'image et restez appuyé");
                break;

                case 7 :
                    if(!level5OK)
                        lbl3.setText("Cliquez sur l'image");
                break;

                case 8 :
                    lbl3.setText(fin);
                break;             
                    
            }
        }
            

        jp_lbl1N2.add(jp_lbl1, BorderLayout.NORTH);
        jp_lbl1N2.add(jp_lbl2, BorderLayout.SOUTH);

        jp_labelGen.add(jp_lbl1N2, BorderLayout.NORTH);
        
        if(index == 0 || clic)
        {
            if(index == 6)
            {
                lblImg = new JLabel(rot2);
            }
            else
            {
                lblImg = new JLabel(tab_img[index]);
            }

            jp_labelGen.add(lblImg, BorderLayout.CENTER);
        }
        else
        {
            if(index == 6 || index == 7 || index == 8 || index == 1)
            {
                lblImg = new JLabel(tab_img[index]);
                jp_labelGen.add(lblImg, BorderLayout.CENTER);
            }
            else
            {
                lblImg.setVisible(false);
                jp_labelGen.add(new JLabel(imgBlanc), BorderLayout.CENTER);
            }
        }


        if(index == 7)
        {
                lblImg.addMouseMotionListener(new MouseAdapter()
                {
                    @Override
                    public void mouseMoved(MouseEvent me)
                    {
                        int largeurImg = tab_img[7].getIconWidth();
                        int hauteurImg = tab_img[7].getIconHeight();

                        int coinGaucheImg = (getSize().width - largeurImg) / 2;
                       
                        Point p = me.getPoint();

                        if(p.x > coinGaucheImg && p.x < coinGaucheImg + largeurImg && p.y > 0 && p.y < hauteurImg)
                            lblImg.setIcon(pluginApply);
                        else
                            lblImg.setIcon(tab_img[7]);
                    }

                });

                lblImg.addMouseListener(new MouseAdapter()
                {
                    @Override
                    public void mousePressed(MouseEvent me)
                    {
                        suivant.setEnabled(true);
                    }

                });
        }

        jp_labelGen.add(jp_lbl3, BorderLayout.SOUTH);
        
    }
    
    private void initImageSize(int index)
    {
        tab_img[index] = new ImageIcon(new ImageIcon(path[index]).getImage().getScaledInstance(dimImg.width, dimImg.height, Image.SCALE_SMOOTH));
    }

    
    /**
     * Paramètre l'affichage du JDialog
     */
    public void initPos()
    {
        Dimension dimIhm = ihm.getSize();
        Dimension dimAide = getSize();
        this.setLocation(dimIhm.width / 2 - dimAide.width / 2, dimIhm.height / 2 - dimAide.height / 2);
        this.setResizable(false);
        this.setVisible(true);
    }
}
