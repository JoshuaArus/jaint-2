package Jaint;

/**
 * @(#)JaintPalette.java
 *
 * Classe permettant de redessiner une JPalette avec un centre personnalisé
 *
 * @author Jean-François
 * @version 1.00 2009/11/30
 */

import JaintListener.EcouteurFormeBas;
import JaintListener.EcouteurSelectionForm;
import JaintListener.EcouteurChangementCouleur;
import JaintListener.EcouteurBtnCouleur;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.border.Border;

public class JaintPalette extends JPalette
{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 7938539416577924695L;
	/**
     * le carré
     */
    public static final int CARRE = 0;
    /**
     * la ligne
     */

    public static final int LIGNE = 1;
    /**
     * le rond
     */
    public static final int ROND = 2;

    /**
     * La première figure sélectionnable dans le choix des formes
     */
    public static final int FIGURE1 = 0;
    /**
     * La deuxième figure sélectionnable dans le choix des formes
     */
    public static final int FIGURE2 = 1;
    /**
     * La troisème figure sélectionnable dans le choix des formes
     */
    public static final int FIGURE3 = 2;


    /**
     * Choix par défaut (permet de dessiner le centre de départ)
     */
    public static final int CHOIX_DEFAUT = 0;
	
    /**
     * Choix du pinceau
     */
    public static final int CHOIX_PINCEAU = 1;
    /**
     * Choix des formes
     */
    public static final int CHOIX_FORME = 2;
    /**
     * Choix des couleurs
     */
    public static final int CHOIX_COULEUR = 3;

    /**
     * Choix du gomme
     */
    public static final int CHOIX_GOMME = 4;

    /**
     *Choix du pot de peinture
     */
    public static final int CHOIX_POT_PEINTURE = 5;

    /**
     * Choix de la pipette
     */
    public static final int CHOIX_PIPETTE = 6;

    /**
     * Choix du carré vide
     */
    public static final int CARRE1 = 7;

    /**
     * Choix du carré à deux couleurs
     */
    public static final int CARRE2 = 8;

    /**
     * Choix du carré rempli à une couleur
     */
    public static final int CARRE3 = 9;

    /**
     * Choix du rond vide
     */
    public static final int ROND1 = 10;

    /**
     * Choix du rond rempli à deux couleurs
     */
    public static final int ROND2 = 11;

    /**
     * Choix du rond rempli à une couleur
     */
    public static final int ROND3 = 12;


        private int rayon;
	private int indexCenter = 0;
        private int figureBas = FIGURE1;
        private int selectedForm = CARRE;
        private int selectedForm2 = CARRE1;

	private Graphics2D graph;
        private JSlider slider;
        private JSlider sliderPot;
        private JSliderColorChooser sliderColor;
        private JSpinner[] rvb = new JSpinner[3];
        private JBouton[] btnColor = new JBouton[2];
        private JBouton[] btnForme = new JBouton[3];
        private JBouton[] btnFormeBas = new JBouton[3];
        private Border borderBtn;

        private BufferedImage img_Grand_Degrade;

	private Image IMG_CARRE;
        private Image IMG_ROND;
        private Image IMG_LIGNE;

        private Image IMG_CHOIX_CARRE1;
        private Image IMG_CHOIX_CARRE2;
        private Image IMG_CHOIX_CARRE3;
        
        private Image IMG_CHOIX_ROND1;
        private Image IMG_CHOIX_ROND2;
        private Image IMG_CHOIX_ROND3;

       


        private int hauteur;
        private int largeur;
        private boolean changementSpinner = false;
        private int posLuminosite;
        private final Image defaultImage;
        private boolean surSliderColor = false;

        private IHM ihm;
       


     /**
     * Construit la palette
     * @param angle Angle du début du premier bouton
     * @param centerImage Chemin de l'image du centre par défaut
     * @param i L'ihm principale
     */
    public JaintPalette(int angle, String centerImage, IHM i)
    {
    	super(angle, i);

        ihm = i;
       
    	rayon = super.getPetitRayon();
        defaultImage = new ImageIcon(centerImage).getImage().getScaledInstance(rayon * 2, rayon * 2, Image.SCALE_SMOOTH);

    	
    	Point p = super.getCenterPoint();
    	
    	coordX = p.x;
    	coordY = p.y;

        img_Grand_Degrade = new BufferedImage(rayon * 2, 1, BufferedImage.TYPE_INT_RGB);

        slider = new JSlider(1, 50, 25);
        sliderPot = new JSlider(0, 255, 25);
        this.add(slider);
        this.add(sliderPot);
        int largeurSlider = (coordX + rayon - rayon / 4) - (coordX - rayon + rayon / 4);
        slider.setBounds(coordX - rayon + rayon / 4, coordY, largeurSlider, slider.getPreferredSize().height);
        sliderPot.setBounds(coordX - rayon + rayon / 4, coordY, largeurSlider, slider.getPreferredSize().height);

        slider.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(e.getButton() == MouseEvent.BUTTON3)
                {
                    JaintPalette.this.setState(false);
                    JaintPalette.this.setVisible(false);

                }
            }

        });


        sliderColor = new JSliderColorChooser(rayon * 2, new JSlider().getPreferredSize().height, this);
        this.add(sliderColor);
        Dimension dimSliderColor = sliderColor.getPreferredSize();
        sliderColor.setBounds(coordX - rayon, coordY, dimSliderColor.width, dimSliderColor.height);
        sliderColor.setOpaque(false);


        sliderColor.addMouseListener(new MouseAdapter()
            {
                @Override
               public void mouseEntered(MouseEvent e)
               {
                    setSliderColorState(true);
               }

                @Override
               public void mouseExited(MouseEvent e)
               {
                   setSliderColorState(false);
               }
            });

        rvb[0] = new JSpinner();
        rvb[1] = new JSpinner();
        rvb[2] = new JSpinner();

        int hauteurSpinner = dimSliderColor.height / 2;
        int departSpinner = pythagore(rayon, dimSliderColor.height + hauteurSpinner);
        
        int largeurSpinner = (int) ((departSpinner * 2) / 3);

        Dimension dimSpinner = new Dimension(largeurSpinner, hauteurSpinner);

        int j = 0;
        

        for(JSpinner elem : rvb)
        {
            elem.setVisible(false);
            this.add(elem);
            elem.setPreferredSize(dimSpinner);
            elem.setBounds(coordX - departSpinner + j * dimSpinner.width, (coordY + dimSliderColor.height), dimSpinner.width, dimSpinner.height);
            j++;

            elem.addChangeListener(new ControleSpinner(this));
            
        }

        rvb[0].setBorder(BorderFactory.createEtchedBorder(Color.RED, Color.RED));
        rvb[1].setBorder(BorderFactory.createEtchedBorder(Color.GREEN, Color.GREEN));
        rvb[2].setBorder(BorderFactory.createEtchedBorder(Color.BLUE, Color.BLUE));

        

        int espace = dimSpinner.height / 2;
        int hauteurBtn = (int) (dimSpinner.height * 1.25);
        int largeurBtn = pythagore(rayon, dimSliderColor.height + dimSpinner.height + hauteurBtn + espace);
       
            btnColor[0] = new JBouton();
            this.add(btnColor[0]);
            btnColor[0].setBounds(coordX - largeurBtn, coordY + dimSliderColor.height + dimSpinner.height + espace, largeurBtn, hauteurBtn);

            btnColor[0].check();
           

            btnColor[1] = new JBouton();
            this.add(btnColor[1]);
            btnColor[1].setBounds(coordX, coordY + dimSliderColor.height + dimSpinner.height + espace, largeurBtn, hauteurBtn);

            btnColor[0].setBorder(BorderFactory.createEtchedBorder(Color.ORANGE, Color.ORANGE));
            
          
        for(JButton btn : btnColor)
        {
            btn.setSize(largeurBtn, hauteurBtn);
            btn.setVisible(false);
            btn.addActionListener(new EcouteurBtnCouleur(this));
        }

        JBouton.setColorIcon(btnColor);

        hauteur = (int) (rayon / 1.75);
        largeur = (int) (rayon / 3);

        hauteur *= 0.9;

        String cheminDossier = "./lib/Icons/Dessin/formeGeo/";

        IMG_CARRE = new ImageIcon(cheminDossier + "carre.png").getImage().getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH);
        IMG_ROND = new ImageIcon(cheminDossier + "rond.png").getImage().getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH);
        IMG_LIGNE = new ImageIcon(cheminDossier + "ligne.png").getImage().getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH);

        IMG_CHOIX_CARRE1 = new ImageIcon(cheminDossier + "selectionCarre1.png").getImage().getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH);
        IMG_CHOIX_CARRE2 = new ImageIcon(cheminDossier + "selectionCarre2.png").getImage().getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH);
        IMG_CHOIX_CARRE3 = new ImageIcon(cheminDossier + "selectionCarre3.png").getImage().getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH);

        IMG_CHOIX_ROND1 = new ImageIcon(cheminDossier + "selectionRond1.png").getImage().getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH);
        IMG_CHOIX_ROND2 = new ImageIcon(cheminDossier + "selectionRond2.png").getImage().getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH);
        IMG_CHOIX_ROND3 = new ImageIcon(cheminDossier + "selectionRond3.png").getImage().getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH);
        
        btnForme[CARRE] = new JBouton(new ImageIcon(IMG_CARRE));
        btnForme[LIGNE] = new JBouton(new ImageIcon(IMG_LIGNE));
        btnForme[ROND] = new JBouton(new ImageIcon(IMG_ROND));

        btnFormeBas[FIGURE1] = new JBouton();
        btnFormeBas[FIGURE2] = new JBouton();
        btnFormeBas[FIGURE3] = new JBouton();

        borderBtn = btnFormeBas[CARRE].getBorder();
        btnFormeBas[FIGURE1].setBorder(BorderFactory.createEtchedBorder(Color.ORANGE, Color.ORANGE));

        for(JBouton btn : btnForme)
        {
            this.add(btn);
            btn.setVisible(false);
        }

        p = getPoint(135, rayon);
       
        btnForme[CARRE].setBounds(p.x, p.y, largeur, hauteur);

        int saveX = p.x;

        p = getPoint(45, rayon);
        btnForme[ROND].setBounds(p.x - largeur, p.y, largeur, hauteur);

        int distance = p.x - saveX;

        p.x = saveX + (distance - largeur) / 2;

        btnForme[LIGNE].setBounds(p.x, p.y, largeur, hauteur);

        for(JBouton btn : btnFormeBas)
        {
            this.add(btn);
            btn.addActionListener(new EcouteurFormeBas(this));
        }
        
        p = getPoint(225, rayon);
        btnFormeBas[FIGURE1].setBounds(p.x, p.y - hauteur, largeur, hauteur);

        saveX = p.x;

        p = getPoint(315, rayon);
        btnFormeBas[FIGURE2].setBounds(p.x - largeur, p.y - hauteur, largeur, hauteur);

        distance = p.x - saveX;
        p.x = saveX + (distance - largeur) / 2;

        btnFormeBas[FIGURE3].setBounds(p.x, p.y - hauteur, largeur, hauteur);

       
        slider.setVisible(false);
        sliderPot.setVisible(false);
        sliderColor.setVisible(false);


        for(JBouton btn : btnForme)
            btn.addActionListener(new EcouteurSelectionForm(this));




        this.addMouseMotionListener(new MouseMotionListener()
        {

            public void mouseDragged(MouseEvent e)
            {

            }

            public void mouseMoved(MouseEvent e)
            {
                if(indexCenter == JaintPalette.CHOIX_COULEUR || indexCenter == JaintPalette.CHOIX_PIPETTE || indexCenter == JaintPalette.CHOIX_POT_PEINTURE)
                {
                    mouseX = e.getX();
                    mouseY = e.getY();

                    if(onDegrade())
                    {
                        int lgDebutToCentre = (getPreferredSize().width / 2 - rayon);
                        Color color = new Color(img_Grand_Degrade.getRGB(mouseX - lgDebutToCentre, 0));

                        majSpinner(color);

                        repaint();
                    }
                }
            }

        });

        this.addMouseListener(new EcouteurChangementCouleur(this));

      
          setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    
    
    @Override
    public void paintComponent(Graphics g)
    {
        graph = (Graphics2D ) g;
        
        transparenceOn(graph);
        super.paintComponent(graph);
        transparenceOff(graph);
    	
    	
    	
    	graph.setClip(new Ellipse2D.Double(coordX - rayon, coordY - rayon, rayon * 2, rayon * 2));


        if(defaultImage != null && indexCenter == CHOIX_DEFAUT)
    	{
    		graph.drawImage(defaultImage, coordX - rayon, coordY - rayon, this);
    	}

        graph.setColor(Color.BLACK);
        graph.drawOval(coordX - rayon, coordY - rayon, rayon * 2, rayon * 2);
        
    	switch(indexCenter)
    	{

            case CHOIX_PINCEAU :

                sliderColor.setVisible(false);
                sliderPot.setVisible(false);

                for(JSpinner elem : rvb)
                    elem.setVisible(false);
                for(JButton btn : btnColor)
                    btn.setVisible(false);
                for(JBouton btn : btnForme)
                    btn.setVisible(false);
                for(JBouton btn : btnFormeBas)
                    btn.setVisible(false);
                initCenter();
                drawCircleSize();
                drawSlider();
                finishCenter();

                ihm.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                
            break;
    		  		
    		
    	
            case CHOIX_FORME :

                sliderColor.setVisible(false);
                sliderPot.setVisible(false);

                ihm.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                for(JSpinner elem : rvb)
                    elem.setVisible(false);
                for(JButton btn : btnColor)
                    btn.setVisible(false);
                initCenter();
                drawFormeHaut();
                drawFormeBas();
                drawCircleSize();
                drawSlider();
                finishCenter();
        
            break;

            case CHOIX_GOMME :

                sliderColor.setVisible(false);
                sliderPot.setVisible(false);
               
                for(JSpinner elem : rvb)
                    elem.setVisible(false);
                for(JButton btn : btnColor)
                    btn.setVisible(false);
                for(JBouton btn : btnForme)
                    btn.setVisible(false);
                for(JBouton btn : btnFormeBas)
                    btn.setVisible(false);
                initCenter();
                drawCircleSize();
                drawSlider();
                finishCenter();

                ihm.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                
            break;

            case CHOIX_POT_PEINTURE :

                sliderColor.setVisible(false);
                slider.setVisible(false);

                for(JSpinner elem : rvb)
                    elem.setVisible(false);
                for(JButton btn : btnColor)
                    btn.setVisible(false);
                for(JBouton btn : btnForme)
                    btn.setVisible(false);
                for(JBouton btn : btnFormeBas)
                    btn.setVisible(false);
                initCenter();
                sliderPot.setVisible(true);
                sliderPot.setOpaque(false);
                finishCenter();
                ihm.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
            break;

            case CHOIX_COULEUR : case CHOIX_PIPETTE :

                slider.setVisible(false);
                sliderPot.setVisible(false);
                
                sliderColor.setVisible(true);
                for(JSpinner elem : rvb)
                    elem.setVisible(true);
                for(JButton btn : btnColor)
                    btn.setVisible(true);
                for(JBouton btn : btnForme)
                    btn.setVisible(false);
                for(JBouton btn : btnFormeBas)
                    btn.setVisible(false);

                initCenter();
                drawDegrade();
                finishCenter();

                if(indexCenter == CHOIX_PIPETTE)
                {
                    Image img_cursor = new ImageIcon("./lib/Icons/Palette/Dessin/pipette_curseur.png").getImage();
                    Cursor c = getCursor(0, null, img_cursor, new Point(0, 0));
                    ihm.setCursor(c);
                }
                else
                {
                    ihm.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }

                                                
            break;

            
            default :

                slider.setVisible(false);
                sliderColor.setVisible(false);
                sliderPot.setVisible(false);
                
                for(JSpinner elem : rvb)
                    elem.setVisible(false);
                for(JButton btn : btnColor)
                    btn.setVisible(false);
                for(JBouton btn : btnForme)
                    btn.setVisible(false);
                for(JBouton btn : btnFormeBas)
                    btn.setVisible(false);

                ihm.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

            break;

        }
    }

    private void drawDegrade()
    {
        
        Color c = sliderColor.getColor();
        float hue = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null)[0];

        Dimension dim = sliderColor.getPreferredSize();
        Graphics graphDeg = img_Grand_Degrade.getGraphics();
        
         if(c.equals(new Color(127, 127, 127)))
         {
            for(int i = 0; i < dim.width; i++)
            {
                Color nouvelle = Color.getHSBColor(hue, 0f, (float) i / (dim.width));
                graph.setColor(nouvelle);
                graph.drawLine(coordX - rayon + i, coordY - rayon, coordX - rayon + i, coordY);
                graphDeg.setColor(nouvelle);
                graphDeg.drawLine(i, 0, i, 0);
            }
         }
         else
         {

            for(int i = 0; i < dim.width / 2; i++)
            {
                Color nouvelle = Color.getHSBColor(hue, 1f, (float) i / (dim.width / 2));
                graph.setColor(nouvelle);
                graph.drawLine(coordX - rayon + i, coordY - rayon, coordX - rayon + i, coordY);
                graphDeg.setColor(nouvelle);
                graphDeg.drawLine(i, 0, i, 0);
            }


            for(int i = 0; i < dim.width / 2; i++)
            {
                Color nouvelle = Color.getHSBColor(hue, 1f - (float) i / (dim.width / 2), 1f);
                graph.setColor(nouvelle);
                graph.drawLine(coordX + i, coordY - rayon, coordX + i, coordY);
                graphDeg.setColor(nouvelle);
                graphDeg.drawLine(i + dim.width / 2, 0, i + dim.width / 2, 0);
            }
         }

                if(onDegrade())
                {
                    drawLine(mouseX);
                }   
                else if(changementSpinner)
                {
                    drawLine(posLuminosite);
                    changementSpinner = false;
                }
                else
                {
                    //remet les coordonnées des spinners à jour car elles ont été modifiées si la souris était sur le dégradé.
                     Color color;

                    if(btnColor[0].isChecked())
                    {
                       color = getFirstColor();
                    }
                    else
                    {
                        color = getSecondColor();
                    }

                    majSpinner(color);
                }

    }
    
    /**
     * Efface le dernier centre en mettant une couleur blanche
     */
    public void initCenter()
    {
        graph.setColor(Color.WHITE);
        graph.fillOval(coordX - rayon, coordY - rayon, rayon * 2, rayon * 2);
    }


    /**
     * Dessine le slider de sélection de la taille au centre
     */
    public void drawSlider()
    {
            slider.setVisible(true);
            slider.setOpaque(false);          
    }

    /**
     * Dessine un cercle noir pour affiner les traits du contour du centre de la palette
     */
    public void finishCenter()
    {
        graph.setColor(Color.BLACK);
        graph.drawOval(coordX - rayon, coordY - rayon, rayon * 2, rayon * 2);
    }

    /**
     * Dessine les boutons avec les images du carré, ligne et rond
     */
    public void drawFormeHaut()
    {
        for(JBouton btn : btnForme)
        {
            btn.setVisible(true);
            btn.setBorder(borderBtn);
        }

        if(selectedForm == CARRE)
            btnForme[CARRE].setBorder(BorderFactory.createEtchedBorder(Color.ORANGE, Color.ORANGE));
        else if(selectedForm == ROND)
            btnForme[ROND].setBorder(BorderFactory.createEtchedBorder(Color.ORANGE, Color.ORANGE));
        else if(selectedForm == LIGNE)
            btnForme[LIGNE].setBorder(BorderFactory.createEtchedBorder(Color.ORANGE, Color.ORANGE));

    }

    /**
     * Renvoie le code du centre (à comparer avec un des fields
     * @return Le code du centre
     */
    public int getCenter()
    {
        return indexCenter;
    }

    /**
     * Donne un code au centre (à choisir parmi les fields de la classe
     * @param numCenter Le code du centre
     */
    public void setCenter(int numCenter)
    {
        indexCenter = numCenter;
    }
    
    
    /**
     * Permet de changer la forme géométrique sélectionnée
     * @param forme Code de la forme
     */
    public void setForm(int forme) { selectedForm = forme;}
    /**
     * Renvoie Le code de la forme géométrique sélectionnée
     * @return Le code
     */
    public int getForm(){ return selectedForm;}

    /**
     * Permet de changer les figures géométriques dessinées dans la partie du bas du centre
     * @param numFigure Le code de la figure du bas
     */
    public void setFigureBas(int numFigure){ figureBas = numFigure;}
    /**
     * renvoie le code de la figure géométriques dessinées dans la partie du bas du centre
     * @return Le code de la figure du bas
     */
    public int getFigureBas(){ return figureBas;}

    /**
     * Met à jour les coordonnées de la souris
     * @param x Coordonnée x de la souris
     * @param y Coordonnée y de la souris
     */
    public void setMouse(int x, int y)
    {
        mouseX = x;
        mouseY = y;
    }

    /**
     * Renvoie le tableau des deux boutons de sélection de la couleur
     * @return Le tableaude boutons
     */
    public JBouton[] getBoutonColor()
    {
        return btnColor;
    }

    /**
     * Renvoie le tableau de bouton des formes géométriques
     * @return Le tableau de bouton
     */
    public JBouton[] getBoutonForme()
    {
        return btnForme;
    }

    /**
     * Renvoie le tableau contenant les trois JSpinners rouge vert bleu dans le sélecteur de couleur
     * @return Le tableau
     */
    public JSpinner[] getSpinner()
    {
        return rvb;
    }


    private void drawFormeBas()
    {

        for(JBouton btn : btnFormeBas)
        {
            btn.setVisible(true);
           
        }
       
        switch(selectedForm)
        {

            case CARRE :

                btnFormeBas[FIGURE1].setIcon(new ImageIcon(IMG_CHOIX_CARRE1));
                btnFormeBas[FIGURE1].setName("carre1");
                btnFormeBas[FIGURE3].setIcon(new ImageIcon(IMG_CHOIX_CARRE2));
                btnFormeBas[FIGURE2].setName("carre2");
                btnFormeBas[FIGURE2].setIcon(new ImageIcon(IMG_CHOIX_CARRE3));
                btnFormeBas[FIGURE3].setName("carre3");

            break;

            case ROND :

                btnFormeBas[FIGURE1].setIcon(new ImageIcon(IMG_CHOIX_ROND1));
                btnFormeBas[FIGURE1].setName("rond1");
                btnFormeBas[FIGURE3].setIcon(new ImageIcon(IMG_CHOIX_ROND2));
                btnFormeBas[FIGURE2].setName("rond2");
                btnFormeBas[FIGURE2].setIcon(new ImageIcon(IMG_CHOIX_ROND3));
                btnFormeBas[FIGURE3].setName("rond3");
                
            break;

            case LIGNE :

                for(JBouton btn : btnFormeBas)
                {
                    btn.setVisible(false);
                }

            break;
        }
    }

    private void drawLine(int x)
    {
            float[] style = {10, 5};
            this.graph.setStroke( new BasicStroke( 1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, style, 0));
            this.graph.setColor(Color.BLACK);

            this.graph.drawLine(x, coordY, x, coordY - rayon);

            graph.setStroke(new BasicStroke(1));
    }

    /**
     * Teste si la souris se trouve à l'emplacement du dégradé
     * @return Le résultat du test
     */
    public boolean onDegrade()
    {

        return (onCenter() && mouseY < coordY);
    }

    /**
     * Renvoie l'image contenant le dégradé avec la luminosité et la saturation d'une teinte sélectionnée
     * @return L'image
     */
    public BufferedImage getDegrade()
    {
        return img_Grand_Degrade;
    }

    private void majSpinner(Color c)
    {
        rvb[0].setValue(c.getRed());
        rvb[1].setValue(c.getGreen());
        rvb[2].setValue(c.getBlue());
    }


    private int pythagore(int hypothenuse, int cote)
    {
        double sommeCarre = Math.pow(hypothenuse, 2) - Math.pow(cote, 2);

        return (int) (Math.sqrt(sommeCarre));
    }

    /**
     * Appel à la fonction pythagore de la <code>JPalette</code>
     * @param hypothenuse
     * @return
     */
    @Override
    public Dimension pythagore(int hypothenuse)
    {
        return super.pythagore(hypothenuse);
    }

    private Point getPoint(int angle, int distance)
    {
        int x = (int) (distance * Math.cos(angle * (Math.PI / -180))) + coordX;
	int y = (int) (distance * Math.sin(angle * (Math.PI / -180))) + coordY;

        return new Point(x,y);
    }

    /**
     * Renvoie le tableau des boutons des formes du bas dans la sélection des formes géométriques
     * @return Le tableau de boutons
     */
    public JBouton[] getBoutonBas()
    {
        return btnFormeBas;
    }

    /**
     * Renvoie le sélecteur de teinte
     * @return Le sélecteur de teinte
     */
    public JSliderColorChooser getSliderColor()
    {
        return sliderColor;
    }

    void setPosLuminosite(int pos)
    {
        posLuminosite = pos;
    }

    void setChangementSpinner(boolean change)
    {
        changementSpinner = change;
    }

    /**
     * Renvoie le JSlider permettant de changer la taille du tracé du dessin
     * @return Le JSlider
     */
    public JSlider getSliderSize()
    {
        return slider;
    }

    /**
     * Teste si la souris se trouve sur le sélecteur de couleur
     * @return Le résultat du test
     */
    public boolean onSliderColor()
    {
        return surSliderColor;
    }

    /**
     * Met à jour la valeur du boolean permettant de savoir si la souris se trouve sur le sélecteur de couleur ou non
     * @param on Vrai si sur le sélecteur, faux sinon
     */
    public void setSliderColorState(boolean on)
    {
        surSliderColor = on;
    }


    private void drawCircleSize()
    {
        Dimension dimSlider = slider.getPreferredSize();
        
        graph.setColor(Color.BLACK);
        graph.drawOval(coordX - rayon + 2, coordY - dimSlider.height / 10, rayon / 10, rayon / 10);
        
        
        graph.drawOval(coordX + rayon - rayon / 4, coordY - dimSlider.height / 4, rayon / 4, rayon / 4);
        
    }

    /**
     *
     * Permet de créer un nouveau curseur. Si <code>img_cursor</code> vaut null, une image sera crée en fonction de <code>penSize</code> et <code>couleur</code>
     * @param penSize Taille du curseur
     * @param couleur Couleur du curseur
     * @param img_cursor Image du curseur
     * @param hotspot Point du cursueur
     * @return Le curseur crée
     */
    public static Cursor getCursor(int penSize, Color couleur, Image img_cursor, Point hotspot)
    {
        Toolkit tk = Toolkit.getDefaultToolkit();

        if(img_cursor == null)
        {
            img_cursor = new BufferedImage(36, 36,BufferedImage.TYPE_INT_ARGB);
            Graphics cursorGraph = img_cursor.getGraphics();

            cursorGraph.setColor(couleur);

            cursorGraph.drawOval(0, 0, penSize*2, penSize*2);
        }

        return tk.createCustomCursor(img_cursor, hotspot, "TEST");
    }

    /**
     * Renvoie le code de la forme du haut sélectionnée
     * @return Le code de la forme
     */
    public int getSelectedForm()
    {
        return selectedForm;
    }

    /**
     * Renvoie le code de la forme du bas sélectionnée
     * @return
     */
    public int getSelectedForm2() {
        return selectedForm2;
    }

    /**
     * Change la forme du bas sélectionnée
     * @param i Le code de la forme
     */
    public void setSelectedForm2(int i)
    {
        selectedForm2 = i;
    }

    /**
     * Renvoie la valeur du slider du pot de peinture
     * @return
     */
    public int getSliderPotValue() {
        return sliderPot.getValue();
    }

}