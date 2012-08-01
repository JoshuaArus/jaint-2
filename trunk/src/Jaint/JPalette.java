package Jaint;

/**
 * @(#)Text1.java
 *
 *
 * @author Jean-François
 * @version 1.00 2009/11/5
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Arc2D;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.*;


/**
 * Classe permettant d'afficher un menu circulaire auquel on peut ajouter plusieurs <code>JPaletteItem</code>
 * @author Jeff
 */
public class JPalette extends JComponent
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7221681135767849610L;
	
	/*attributs*/

	//nombre de bouton
	
	private int nbBtn;
	
	//angle du premier bouton;
	
	private int angleDepart = 0; // compt� � partir de la droite du cercle, dans le sens contraire des aiguilles d'une montre


	//changer couleur du bouton suivant l'action de la souris

	private boolean enfonce = false;
	

	// num�ro du bouton / "sous-bouton" enfonce ...
	
	private int btnEnfonce = 1;
	private int ssBtnEnfonce = 1;
	
	// ... sur lequel la souris est
	
	private int  mouseOnBtn = 0;
	private int mouseOnSubBtn = 0;
	
	// Dimension du SubmenuItemOuvert
	
	private int initDepart = 0;
	private int finMenu = 0;

        //coordonn�es polaires dans le cercle
	
	private double r;
	private double theta;

   	
   	//dimension de la palette
   	
        /**
         * Diamètre de la <code>JPalette</code> sans les sous-menus. Vaut le tiers de la heuteur de l'écran
         */
        public static final int GRAND_DIAM = Toolkit.getDefaultToolkit().getScreenSize().height / 3;
    /**
     * Rayon de la <code>JPalette</code>
     */
    public static final int GRAND_RAYON = GRAND_DIAM / 2;
    
    /**
     * Diamètre de la <code>JPalette</code> sans les cercles de couleur
     */
    public static final int MOYEN_DIAM = GRAND_DIAM - (GRAND_DIAM / 20);
    /**
     * Rayon de la <code>JPalette</code> sans les cercles de couleur
     */
    public static final int MOYEN_RAYON = MOYEN_DIAM / 2;
    
    /**
     * Diamètre du centre de la <code>JPalette</code>
     */
    public static final int PETIT_DIAM = GRAND_DIAM - (GRAND_DIAM / 3);
    /**
     * Rayon du centre de la <code>JPalette</code>
     */
    public static final int PETIT_RAYON = PETIT_DIAM / 2;
    
    /**
     * Diamètre de la <code>JPalette</code> avec un sous menu d'ouvert
     */
    public static final int DIAM_SUBMENU = GRAND_DIAM + (int)(GRAND_DIAM / 2.5);
    /**
     * Rayon de la <code>JPalette</code> avec un sous menu d'ouvert
     */
    public static final int RAYON_SUBMENU = DIAM_SUBMENU / 2;
   	
   	
   	//coordonn�es du centre du cercle
	
    /**
     *  Coordonnée x du centre de la <code>JPalette</code>
     */
    protected int coordX = RAYON_SUBMENU + 1;
    /**
     * Coordonnée y du centre de la <code>JPalette</code>
     */
    protected int coordY = RAYON_SUBMENU + 1;


    //coordonn�se x et y de la souris

    /**
     * Coordonnée x de la souris dans la <code>JPalette</code>
     */
    protected int mouseX = coordX;
    /**
     * Coordonnée y de la souris dans la <code>JPalette</code>
     */
    protected int mouseY = coordY;
    
    
   
    
    //graphisme de la palette
    
    private Graphics2D graph;
    
    //couleur des ronds ext�rieurs
    
    private Color firstColor = Color.BLACK;
    private Color secondColor = Color.WHITE;


    private Color couleurEnfonce;
    private Color couleurPassage;
    private Color btnColor;
    private Color couleurDesactive = Color.GRAY;
    
    
    //liste des boutons
    
    private LinkedList<JPaletteItem> lst_btn = new LinkedList<JPaletteItem>();
    private static boolean anotherDisplayed = false;
    
    private static double degreSsBtn;
    
    private static Dimension dimImg;
    private static int espaceEntreIcone;
    
    
    /*constructeur*/
    
    
    /**
     * Place la <code>JPalette</code> dans la <code>JFrame</code> passée en argument et placera le premier bouton à droite (angle zéro)
     * @param jf La <code>JFrame</code> dans laquelle la <code>JPalette</code> s'affichera
     */
    public JPalette(JFrame jf)
    {
    	this(0, jf);
    }
            
    
    /**
     * Place la <code>JPalette</code> dans la <code>JFrame</code>
     * @param angle Angle du début du premier <code>JPaletteItem</code>
     * @param jf La <code>JFrame</code> dans laquelle la <code>JPalette</code> s'affichera
     */
    public JPalette(int angle, JFrame jf)
    {
    	angle %= 360;
    	
    	angleDepart = angle;
        setVisible(false);


        if(jf != null)
            jf.getLayeredPane().add(this, JLayeredPane.PALETTE_LAYER);

            setBtnColor(new Color(108,140,208));//couleur bleu clair par defaut
           
        int largeurSsMenu = RAYON_SUBMENU - GRAND_RAYON;

        dimImg = pythagore(largeurSsMenu);
        espaceEntreIcone = dimImg.width / 10;
        
                    
        degreSsBtn = getDegreSubmenu();
            

    	
    	this.addMouseMotionListener(new MouseMotionListener()
    	{
			public void mouseDragged(MouseEvent e) 
			{
				enfonce = true;
			    
			    if(changeArea(e))
			    {
			    	mouseX = e.getX();
				    mouseY = e.getY();
				    
				    repaint();
			    }
			    else
			    {
			    	mouseX = e.getX();
				    mouseY = e.getY();
			    }
			    
			}
 
			public void mouseMoved(MouseEvent e) 
			{
                            
				if(changeArea(e))
				{
					mouseX = e.getX();
					mouseY = e.getY();
					
					repaint();
				}
				else
				{
					mouseX = e.getX();
					mouseY = e.getY();
					
				}
				
				
			}
			
		});

    	
    	this.addMouseListener(new MouseAdapter()
    	{
            @Override
    		public void mouseClicked(MouseEvent e)
    		{
    			if(e.getButton() == MouseEvent.BUTTON3)
    			{
    				setVisible(false);
                                setState(false);
    			}
    		}    		
    		
    	});
            	
    	enableEvents(AWTEvent.MOUSE_EVENT_MASK);

        Dimension dim = getPreferredSize();
    	setBounds(0, 0, dim.width, dim.height);
    	
    }
    
    
    /*méthodes*/
    
    
    @Override
    public void paintComponent(Graphics g)
    {
        graph = (Graphics2D) g;
    	graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);//affinage des traits
        
        
    	//dessin des sous menus
    	
    	
    	int depart; // utilisé plusieurs fois dans cette méthode paint
    	
    	   	
    	this.setToolTipText(null);
    	    	
            boolean trouve = false;
            
            degreSsBtn = getDegreSubmenu();

	    for(int i = 0; i < lst_btn.size() && !trouve; i++)
	    {
		    	
		       int nbSubmenuBtn = lst_btn.get(i).nbSubmenuItem();

		    	if(lst_btn.get(i).isOpen() && nbSubmenuBtn > 0)
		    	{
		    		trouve = true;
    		
		    			
                                        double tailleSubmenu = degreSsBtn * nbSubmenuBtn;
                                        

                                        //tailleNormale équivaut à la taille d'un bouton de la palette
		    			boolean tailleNormale = (tailleSubmenu < (360 / nbBtn));
		    			degreSsBtn =  (tailleNormale)? ((360 / nbBtn)/ nbSubmenuBtn) : getDegreSubmenu();
		    			
		    			if(tailleNormale)
		    			{
		    				depart = angleDepart + i * (360 / nbBtn);
		    			}
		    			else
		    			{
		    				depart = (int) (angleDepart + degreSsBtn + i * (360 / nbBtn) - (tailleSubmenu / 2) + espaceEntreIcone + espaceEntreIcone / 2);
		    			}
		    			
		    			if(depart < 0)
		    				depart += 360;
		    			
		    			int depart2 = depart;
		    			
		    			
		    			while(depart2 < 360)
		    			{
		    				depart2 += degreSsBtn;
		    				
		    			}
		    			
		    			depart2 %= 360;
		    			
		    			mouseOnSubBtn = 0;
		    			
		    			if(enfonce)
		    				ssBtnEnfonce = 0;
		    				int tempBtn = 0;
		    			    				
		    			
                                        
                                        graph.setColor(btnColor);


                                        initDepart = depart;
		    			finMenu = depart;
                                        
		    			
		    			for(int x = 0; x < nbSubmenuBtn; x++)
                                        {
                                            finMenu += degreSsBtn;
                                            
                                        }
		    				
		    			
		    			
		    			int taille_Rayon = (GRAND_RAYON + ((RAYON_SUBMENU - GRAND_RAYON) / 2));
		    			
		    			setBasicStroke(RAYON_SUBMENU - GRAND_RAYON);
		    			
		    			graph.draw(new Arc2D.Double(coordX - taille_Rayon, coordY - taille_Rayon, taille_Rayon * 2, taille_Rayon * 2, initDepart, finMenu - initDepart, Arc2D.OPEN));
		    				
		    			
		    			theta = Math.atan2(-mouseY + coordY, -mouseX + coordX) * (-180 / Math.PI);
		    			theta += 180;
		    			
		    			
		    			
		    			depart = initDepart;
		    			
		    			trouve = false;
		    			
		    			graph.setColor(couleurDesactive);
                                        
                                        for(int k = 0; k < nbSubmenuBtn; k++)
                                        {
                                            if(!lst_btn.get(i).getBtn(k).isEnabled())
                                             {
                                                   
                                                   graph.draw(new Arc2D.Double(coordX - taille_Rayon, coordY - taille_Rayon, taille_Rayon * 2, taille_Rayon * 2, depart, degreSsBtn, Arc2D.OPEN));
                                             }

                                            depart += degreSsBtn;
                                        }

                                        depart = initDepart;

                                        int degre = (int) degreSsBtn;
			    			
			    			for(int j = 0; j < nbSubmenuBtn && !trouve; j++)
			    			{
			    				
			    				
			    				
			    				depart %= 360;
			    				
			    				tempBtn++;
			    				
			    				
			    				 if(lst_btn.get(i).getBtn(j).isEnabled())
                                                         {
                                                            
				    				 if(trouve = SubmenuItemFound(depart, degre, depart2) && isOnSubmenu())
				    				 {
                                                                        if(enfonce)
                                                                        {
                                                                            ssBtnEnfonce = tempBtn;
                                                                            graph.setColor(couleurEnfonce);
                                                                        }
                                                                        else
                                                                            graph.setColor(couleurPassage);


				    				 	mouseOnSubBtn = tempBtn;

                                                                        graph.draw(new Arc2D.Double(coordX - taille_Rayon, coordY - taille_Rayon, taille_Rayon * 2, taille_Rayon * 2, depart, degre, Arc2D.OPEN));
                                                                        
                                                                        String text = lst_btn.get(i).getBtn(j).getToolTipText();

                                                                        if(text != null)
                                                                        {
                                                                            this.setToolTipText(text);

                                                                        }

                                                                 }
				    				 
                                                         }
                                                         
				    			depart += degre;
			    				
			    			}


                                        setBasicStroke(2);
                                        graph.setColor(Color.BLACK);
                                        graph.drawArc(coordX - RAYON_SUBMENU, coordY - RAYON_SUBMENU, RAYON_SUBMENU * 2, RAYON_SUBMENU * 2, initDepart, finMenu - initDepart);

			    					    			
		    			
		    			//affichage des images des boutons du sous menu
			    				
			    		depart = initDepart;	

                                        SubmenuItem item;

                                        setBasicStroke(1);
                                        transparenceOff(graph);
				    	for(int j = 0; j < lst_btn.get(i).nbSubmenuItem() ; j++)
				    	{
                                           item = lst_btn.get(i).getBtn(j);

                                           Image img = item.getImage();
                                                				    					
                                           if(img != null)
                                           {
                                               drawImage(img, 0, depart, (int) degreSsBtn, taille_Rayon, 0, dimImg);
                                           }

                                                depart += degreSsBtn;
				    	}
                                        transparenceOn(graph);
		    			
		    			
		    			setBasicStroke(2);
                                        

                                        Point pt1 = polarToDegree(RAYON_SUBMENU, initDepart);
                                        Point pt2 = polarToDegree(GRAND_RAYON, initDepart);
                                        
                                        graph.drawLine(pt1.x, pt1.y, pt2.x, pt2.y);

                                        pt1 = polarToDegree(RAYON_SUBMENU, finMenu);
                                        pt2 = polarToDegree(GRAND_RAYON, finMenu);

                                        graph.drawLine(pt1.x, pt1.y, pt2.x, pt2.y);
	    		}
		    }
            
		//dessin des demi-cercles    	
    	
    	transparenceOff(graph);
    	graph.setColor(firstColor);
    	
    	setBasicStroke((float)(GRAND_RAYON - MOYEN_RAYON));
    	
    	int taille_Rayon = (MOYEN_RAYON + ((GRAND_RAYON - MOYEN_RAYON) / 2));
    	
        graph.draw(new Arc2D.Double(coordX - taille_Rayon, coordY - taille_Rayon, taille_Rayon * 2, taille_Rayon * 2, 0, 180, Arc2D.OPEN));
         
        graph.setColor(secondColor);
       	graph.draw(new Arc2D.Double(coordX - taille_Rayon, coordY - taille_Rayon, taille_Rayon * 2, taille_Rayon * 2, 0, -180, Arc2D.OPEN));
    	transparenceOn(graph);

    	setBasicStroke(2);
    	
    	graph.setColor(Color.BLACK);
    	graph.drawOval(coordX - GRAND_RAYON, coordY - GRAND_RAYON, GRAND_DIAM, GRAND_DIAM);
    	
    	setBasicStroke(1); 		
    		
    	//dessin des boutons
    	
    	if(nbBtn > 0)
    	{
            int constante;
            depart = angleDepart;

            constante = 360 / nbBtn;

             graph.setColor(couleurDesactive);
             setBasicStroke((float) MOYEN_RAYON - PETIT_RAYON);
             taille_Rayon = (PETIT_RAYON + ((MOYEN_RAYON - PETIT_RAYON) / 2));

            for(int i = 0; i < nbBtn; i++)
            {                

                if(!lst_btn.get(i).isEnabled())
                {
                                                              
                     graph.draw(new Arc2D.Double(coordX - taille_Rayon, coordY - taille_Rayon, taille_Rayon * 2, taille_Rayon * 2, (int) depart, (int) constante + 1, Arc2D.OPEN));
                          
                }

                depart += constante;
            }
	    	
		 graph.setColor(btnColor);

	    	graph.drawOval(coordX - taille_Rayon, coordY - taille_Rayon, taille_Rayon * 2, taille_Rayon * 2);
	    	
	    	
	    	if(isOnMenu())
                {
	    	
	    	
                        theta = Math.atan2(-mouseY + coordY, -mouseX + coordX) * (-180 / Math.PI);
                                theta += 180;

                                
		    	trouve = false;
		    	int reste = 360 % nbBtn;
		    	
		    	mouseOnBtn = 0;
		    			
		    	if(enfonce)
                            btnEnfonce = 0;
		    		
		    		
		    	constante = 360 / nbBtn;
	    	
	    		depart = angleDepart;
	    		
	    		setBasicStroke(MOYEN_RAYON - PETIT_RAYON);
		    	
		    	
                        
                            for(int i = 0; i < nbBtn && !trouve; i++)
                            {
                                        

                                                    if(enfonce)
                                                    {
                                                            graph.setColor(couleurEnfonce);
                                                    }
                                                    else
                                                    {
                                                            graph.setColor(couleurPassage);
                                                    }



                                                    if(i < reste)
                                                    {
                                                            if( theta > depart && theta <= depart + constante + 1 || (theta + 360 > depart && theta + 360 <= depart + constante + 1))
                                                            {
                                                                if(lst_btn.get(i).isEnabled())
                                                                {

                                                                            graph.draw(new Arc2D.Double(coordX - taille_Rayon, coordY - taille_Rayon, taille_Rayon * 2, taille_Rayon * 2, (int) depart, (int) constante + 1, Arc2D.OPEN));
                                                                            String text = lst_btn.get(i).getToolTipText();

                                                                            if(text != null)
                                                                            {
                                                                                setToolTipText(text);

                                                                            }
                                                                }

                                                                    trouve = true;

                                                            }

                                                            depart += constante + 1;
                                                    }
                                                    else
                                                    {
                                                            if( theta > depart && theta <= depart + constante || (theta + 360 > depart && theta + 360 <= depart + constante))
                                                            {
                                                                if(lst_btn.get(i).isEnabled())
                                                                {
                                                                    graph.draw(new Arc2D.Double(coordX - taille_Rayon, coordY - taille_Rayon, taille_Rayon * 2, taille_Rayon * 2, (int) depart, (int) constante, Arc2D.OPEN));
                                                                    String text = lst_btn.get(i).getToolTipText();

                                                                    if(text != null)
                                                                    {

                                                                        this.setToolTipText(text);

                                                                    }
                                                                }
                                                                    trouve = true;

                                                            }

                                                            depart += constante;
                                                    }

                                                    


                                                    depart %= 360;


                                                    //permet de savoir quel bouton a �t� press�

                                                    mouseOnBtn++;

                                                    if(enfonce)
                                                            btnEnfonce++;
                              
                            }
                        }
		        	
    	}
    	
    	
       setBasicStroke(2);

    	graph.setColor(Color.BLACK);
    	graph.drawOval(coordX - MOYEN_RAYON, coordY - MOYEN_RAYON, MOYEN_DIAM - 1, MOYEN_DIAM - 1);
        graph.drawLine(coordX - GRAND_RAYON, coordY, coordX - MOYEN_RAYON, coordY);
        graph.drawLine(coordX + GRAND_RAYON, coordY, coordX + MOYEN_RAYON, coordY);

    	setBasicStroke(1);


        graph.drawOval(coordX - PETIT_RAYON, coordY - PETIT_RAYON, PETIT_DIAM, PETIT_DIAM);
                  
               
        //dessin des ic�nes des boutons
        
        depart = angleDepart;
        int espace = MOYEN_RAYON - PETIT_RAYON;

        Dimension dimImage = pythagore(espace);
        
            transparenceOff(graph);
            int angle = 360 / nbBtn;
            int reste = 360 % nbBtn;
	    
	        for(int i = 0; i < lst_btn.size(); i++)
	        {
                    if(i < reste)
                        depart++;

                    Image im = lst_btn.get(i).getImage();

                    if(im != null)
                        drawImage(im, i, depart, angle, (MOYEN_RAYON + PETIT_RAYON) / 2, 360 % nbBtn, dimImage);

                    depart += angle;
	        	
	        }

           
                transparenceOn(graph);
	 	   	
    }
   
    /**
     * Augmente la taille du <code>Graphics</code> de la <code>JPalette</code>
     * @param value Taille
     */
    protected void setBasicStroke(float value)
    {
    	graph.setStroke(new BasicStroke(value, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
    }
        
    /**
     *  Teste si la souris se trouve sur un <code>JPaletteItem</code>
     * @return Vrai si la souris se trouve sur un <code>JPaletteItem</code>
     */
    public boolean isOnMenu()
    {
    	r = (int) getR(mouseX - coordX, mouseY - coordY);
	    		
	   	return (r >= PETIT_RAYON && r < MOYEN_RAYON);
    }
    
    /**
     * Teste si la souris se trouve sur un <code>SubmenuItem</code> ouvert
     * @return Vrai si la souris se trouve sur un <code>SubmenuItem</code>
     */
    public boolean isOnSubmenu()
    {
    	boolean res = false;
    	
    	r = (int) getR(mouseX - coordX, mouseY - coordY);
    	    	
    	boolean ouvert = false;
    	
	    	
	    
	    	for(int i = 0; i < lst_btn.size() && !ouvert; i++)
	    	{
	    		if(lst_btn.get(i).isOpen())
	    		{
	    			ouvert = true;
	    		}
	    	}
	    
	    
    	
    	if(ouvert && r > GRAND_RAYON && r <= RAYON_SUBMENU)
    	{
    		theta = Math.atan2(-mouseY + coordY, -mouseX + coordX) * (-180 / Math.PI);
	    	theta += 180;
    		
    		
    		int depart = initDepart;
    		int fin = finMenu;
    		
    		if(depart < 0)
    			depart += 360;
    			
    		if(fin < 0)
    			fin += 360;
    		
    		
    		depart %= 360;
    		fin %= 360;
    		
    		if(theta > depart && theta < fin)
    		{
    			res = true;
    		}
    		else if(fin < depart)
    		{
    			
    			if(theta >= 0 && theta < fin)
    			{
    				res = true;
    			}
    			else if(theta >= depart && theta < 360 || (theta >= 0 && theta < fin))
    			{
    				res = true;
    			}
    			
    		}
	    	
	    	
    	}
    	
    	
    	return res;
    }

    /**
     * Teste si la souris se trouve sur le centre
     * @return Vrai si sur le centre
     */
    protected boolean onCenter()
    {
        r = (int) getR(mouseX - coordX, mouseY - coordY);

        return (r < PETIT_RAYON);
    }
    
   
    
    /**
     * Fonction permettant de calculer la distance d'un point par rapport au centre de la <code>JPalette</code>
     * @param x coordonnée x
     * @param y coordonnée y
     * @return La distance avec le centre
     */
    public double getR(int x, int y)
    {
    	return (Math.sqrt((Math.pow(x, 2) + Math.pow(y, 2))));
    }
   
    //vérifie si on a changé de zone dans la JPalette
    private boolean changeArea(MouseEvent e)
    {
    	
    	boolean res = false;
    	
    	int depart;
    	int constante;
    	
    	
    	//comme mouseX et mouseY n'ont pas encore �t� mis � jour, on regarde en r�alit� les derni�res coordonn�es
    	if(isOnMenu())
    	{
    		mouseX = e.getX();
    		mouseY = e.getY();
    		
    		//on a mis les coordonn�es � jour
    		
    		if(!isOnMenu())
    		{
    			res = true;
    		}
    		else if(nbBtn > 0) //si on est toujours sur un JPaletteItem, il faut regarder l'angle pour savoir si on a chang� d'item
    		{
    			
    			theta = Math.atan2(-mouseY + coordY, -mouseX + coordX) * (-180 / Math.PI);
	    		theta += 180;
    			
    			constante = (int) 360 / nbBtn;
    			depart = angleDepart + constante * (mouseOnBtn - 1); //mouseOnBtn correspond au dernier btn sur lequel �tait la souris (il est mis � jour dans la m�thode paint)
    			
    			depart %= 360;
    			
    			if(depart < (depart + constante) % 360)
    			{
	    			if(theta < depart || theta > depart + constante)
	    			{
	    				res = true;
	    			}
	    			
    			}
    			else
    			{
    				
    				if(theta > (depart + constante) % 360 && theta < depart)
	    			{
	    				res = true;
	    			}
    			}
    			
   			    			
    			
    		}
    		
    	}
    	else if(isOnSubmenu())
    	{
    		mouseX = e.getX();
    		mouseY = e.getY();
    		
    		if(!isOnSubmenu())
    		{
    			res = true;
    		}
    		else
    		{
    			
    			theta = Math.atan2(-mouseY + coordY, -mouseX + coordX) * (-180 / Math.PI);
	    		theta += 180;
    			
    			int numBtn = 0;
                        int nbSsBtn = lst_btn.get(btnEnfonce - 1).nbSubmenuItem();

                        

	    		if(nbSsBtn > 0)
	    		{
	    			constante = (finMenu - initDepart) / nbSsBtn;
	    			
	    			
	    			boolean trouve = false;
	    			
	    			int debut = initDepart;
	    			int fin = finMenu;
	    			
	    			if(debut < 0)
	    				debut += 360;
	    					
	    			if(fin < 0)
	    				fin += 360;
	    			
		    		fin %= 360;
		    		debut %= 360;	
		    		
		    			
		    		if(debut < fin)
		    		{
		    			for(int i = debut; i < fin && !trouve; i += constante)
		    			{
		    				if(theta > i && theta <= i + constante)
		    				{
		    					trouve = true;
		    				}
		    				
		    				numBtn++;
		    				
		    			}
		    	   			
		    		}
		    		else
		    		{
		    			    			
		    			int i;
		    			
		    			for(i = debut; i < 360 && !trouve; i += constante)
		    			{
		    				if(theta > i && theta <= i + constante)
		    				{
		    					trouve = true;
		    				}
		    				
		    				numBtn++;
		    				
		    			}
		    			
		    			
		    			if(!trouve)
		    			{
		    				if(theta > 0 && theta <= i % 360)
		    				{	
		    					
		    				}
		    				else
		    				{
			    				for(i = i % 360; i < fin && !trouve; i += constante)
				    			{
				    				if(theta > i && theta <= i + constante)
				    				{
				    					trouve = true;
				    				}
				    				
				    				numBtn++;
				    				
				    			}
		    				}
			    			
		    			}
		    			
		    			
		    		}
	    			
	    			if(mouseOnSubBtn != numBtn)
	    				res = true;
	    				
	    		}
	    		
    		}
    		
    	}
    	else
    	{
    		mouseX = e.getX();
    		mouseY = e.getY();
    		
    		if(isOnMenu() || isOnSubmenu())
    		{
    			res = true;
    		}
    		
    		
    	}
    	
    	
    	return res;
    }
    
    
    
    @Override
     public Dimension getPreferredSize()// à redefinir pr afficher le composant
    {
    	return new Dimension(DIAM_SUBMENU + 2, DIAM_SUBMENU + new JLabel("r").getPreferredSize().height + 2);
    }


    /**
     * Renvoie la couleur du cercle supérieur
     * @return Couleur du demi cercle supérieur
     */
    public Color getFirstColor()
     {
          return new Color(firstColor.getRGB());
     }

     /**
      * Renvoie la couleur du cercle inférieur
      * @return Couleur du demi cercle inférieur
      */
     public Color getSecondColor()
     {
         return new Color(secondColor.getRGB());
     }


     /**
      * Couleur qui remplace celle du demi cercle supérieur
      * @param color La couleur
      */
     public void setFirstColor(Color color)
     {
         firstColor = color;
     }

     /**
      * Couleur qui remplace celle du demi cercle supérieur
      * @param color La couleur
      */
     public void setSecondColor(Color color)
     {
         secondColor = color;
     }
    
     /**
      * Permet de récupérer le rayon du centre de la <code>JPalette</code> pour pouvoir dessiner un centre dans une classe fille
      * @return Le rayon
      */
     public int getPetitRayon()
    {
    	return PETIT_RAYON;
    }
    
    /**
     * Permet de récupérer les coordonnées du centre de la <code>JPalette</code> pour pouvoir dessiner un centre dans une classe fille
     * @return Les coordonnées
     */
    public Point getCenterPoint()
    {
    	return new Point(coordX, coordY);
    }


    /**
     * Permet de connaître le numéro du <code>JPaletteItem</code> pressé
     * @return Le numéro
     */
    public int getSelectedItem()
    {
        return btnEnfonce;
    }

    /**
     * Permet de connaître le numéro du <code>SubmenuItem</code> pressé
     * @return
     */
    public int getSelectedSubItem()
    {
        return ssBtnEnfonce;
    }

    /**
     * Teste si la souris se trouve sur les demi cercles de couleur
     * @return Vrai si sur un demi-cercle
     */
    protected boolean onColorCircle()
    {
        r = (int) getR(mouseX - coordX, mouseY - coordY);

	   	return (r >= MOYEN_RAYON && r < GRAND_RAYON);

    }
    
    
    
    @Override
     public void processMouseEvent(MouseEvent e)
    {
			
		if(e.getButton() != MouseEvent.BUTTON3)
		{
			switch(e.getID())
			{
				case MouseEvent.MOUSE_PRESSED :
				    enfonce = true;
				    
				    
				    if(isOnMenu() || isOnSubmenu())
				    {
				    	mouseX = e.getX();
				    	mouseY = e.getY();
				    	
				    	repaint();
				    }
				    else
				    {
				    	mouseX = e.getX();
				    	mouseY = e.getY();
				    }

				    break;
				    
			
				case MouseEvent.MOUSE_RELEASED: 
					
					
				    if(isOnMenu())//renvoie vrai si la souris se trouve sur un bouton (pas un bouton d'un sous menu)
				    {  			    	
					 if(lst_btn.get(btnEnfonce - 1).isEnabled())
                                         {
						    if(!lst_btn.get(btnEnfonce - 1).isOpen()) // si le sous menu n'est pas ouvert
						    {

                                                        int idBtn = 0;

                                                        for(ActionListener al : lst_btn.get(btnEnfonce - 1).getActionListeners())
                                                        {
                                                            al.actionPerformed(new ActionEvent(this, idBtn, "Commande" + btnEnfonce));
                                                            idBtn++;
                                                        }


                                                        /* Si le sous menu enclenché n'a pas de sous menu
                                                         * alors on laisse l'ancien ouvert
                                                         */


                                                        if(lst_btn.get(btnEnfonce - 1).hasSubmenu())
                                                        {
                                                            for(int i = 0; i < nbBtn; i++)
                                                            {
                                                                lst_btn.get(i).setOpen(false);
                                                            }

                                                            lst_btn.get(btnEnfonce - 1).setOpen(true);
                                                        }
                                                        else
                                                        {
                                                            /* si le bouton enclenché n'avait pas de sous menu
                                                             * on va remettre "btnEnfonce" à l'état précédent
                                                             * pour cela, on va rechercher dans la liste
                                                             * quel bouton est "ouvert"
                                                             */
                                                            boolean ok = false;

                                                            for(int i = 0; i < nbBtn && !ok; i++)
                                                            {
                                                                if(lst_btn.get(i).isOpen())
                                                                {
                                                                    btnEnfonce = i + 1;
                                                                    ok = true;
                                                                }
                                                            }
                                                        }
   
						    }
						    else
						    {
						    	lst_btn.get(btnEnfonce - 1).setOpen(false);
						    	
						    	int idBtn = 0;
						    	
						    	
						    	for(ActionListener al : lst_btn.get(btnEnfonce - 1).getActionListeners())
							    {
							    	al.actionPerformed(new ActionEvent(this, idBtn, "Commande" + btnEnfonce));
							    	idBtn++;
							    }
						    }
                                         }
						
						
						
						
						if(enfonce)
                                                {
							enfonce = false;
							repaint();
                                                }
					    
				    	
				    }
				    else if(isOnSubmenu())
				    {
                                           try
                                           {
                                                if(lst_btn.get(btnEnfonce - 1).getBtn(ssBtnEnfonce - 1).isEnabled())
                                               {
                                                    int idBtn = 0;

                                                    for(ActionListener al : lst_btn.get(btnEnfonce - 1).getBtn(ssBtnEnfonce - 1).getActionListeners())
                                                    {
                                                        al.actionPerformed(new ActionEvent(this, idBtn, "Commande" + btnEnfonce + "." + ssBtnEnfonce));
                                                        idBtn++;
                                                    }
                                               }
                                           }
                                           catch(IndexOutOfBoundsException ie)
                                           {
                                               /* Cas où on clique sur le sous bouton désactivé
                                                * Lorsque c'est le cas, la variable ssBtnEnfonce n'a pas pu se mettre à la valeur de ce sous bouton
                                                * Lorsque j'affecte la valeur à ce bouton (cf algo lorsque je dessinne les sous boutons)
                                                * Il faudrait que je teste si ce bouton est désactivé juste avant chaque "drawArc"
                                                * A la place, je le fait une fois pour tout un bloc.
                                                * Du coup, ce try catch est nécessaire
                                                */
                                           }
					    
					    
					    
					    if(enfonce)
					    {
							enfonce = false;
							repaint();
					    }	
					    	
				    }
                                    else if(!onCenter() && !onColorCircle())
                                    {
                                        for(int i = 0; i < nbBtn; i++)
                                        {
                                            lst_btn.get(i).setOpen(false);
                                        }
                                        repaint();
                                    }
				    
				    /* Si on est ni sur un JPaletteItem, ni sur un SubmenuItem, �a ne sert � rien de tout redessiner
				     * On remet juste enfonce � faux
				     **/
				     
				     if(enfonce)
				     	enfonce = false;
				    
				    
				    
				    break;
			
                                    case MouseEvent.MOUSE_ENTERED :
					
					
					
				break;
			
				case MouseEvent.MOUSE_EXITED:

                                    mouseX = 0;
                                    mouseY = 0;
				    repaint();
				    break;
			}
			
		}
		
		       
		super.processMouseEvent(e);

                
	}
	
     /**
      * Ajoute un <code>JPaletteItem</code> à la <code>JPaletteItem</code>
      * @param item Le <code>JPaletteItem</code> à ajouter
      */
     public void add(JPaletteItem item)
    {
    	if(nbBtn + 1 > 12)
    		throw new IndexOutOfBoundsException("Too much JPaletteItem : more than 12");
    	
    	
        lst_btn.add(item);
        nbBtn++;
    }
         

    /**
     * Active la transparence pour le <code>Graphics2D</code> donné
     * @param g Le <code>Graphics2D</code>
     */
    protected void transparenceOn(Graphics2D g)
    {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.65f ));
    }

    /**
     * Désactive la transparence pour le <code>Graphics2D</code> donné
     * @param g Le <code>Graphics2D</code>
     */
    protected void transparenceOff(Graphics2D g)
    {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f ));
    }

    /**
     * Teste si une autre <code>JPalette</code> est déjà affichée dans la <code>JFrame</code>
     * @return Le résultat du test
     */
    public boolean anotherPaletteDisplayed()
    {
        return anotherDisplayed;
    }

    /**
     * A utiliser lorsqu'on rend la <code>JPalette</code> visible ou invisible. Permet d'empêcher que plusieurs <code>JPalette</code> ne s'affichent en même temps dans la même <code>JFrame</code>
     * @param display Vrai si setVisible(true), faux sinon
     */
    public void setState(boolean display)
    {
        anotherDisplayed = display;
    }


    /**
     * Permet de récupérer la couleur des <code>JPaletteItem</code>
     * @return La couleur
     */
    @SuppressWarnings("unused")
	private Color getBtnColor()
    {
        return new Color(btnColor.getRed(), btnColor.getGreen(), btnColor.getBlue());
    }

    /**
     * Permet de changer la couleur des <code>JPaletteItem</code>
     * @param color La couleur
     */
    public void setBtnColor(Color color)
    {
        int rouge = color.getRed();
        int vert = color.getGreen();
        int bleu = color.getBlue();


        btnColor = new Color(rouge, vert, bleu);

        // on modifie la saturation et la luminosté pour avoir une couleur plus claire dans la même teinte
        // C'est la couleur que j'utilise lorsqu'on passe sur un bouton
        float[] hsb = Color.RGBtoHSB(rouge, vert, bleu, null);
        hsb[1] = 1;//modification de la saturation
        hsb[2] = 1;//modification de la luminosité

        // on crée la nouvelle couleur avec un autre taux de HSB et on l'affecte à couleurEnfonce
        couleurPassage = new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));


        //on modifie encore la luminosité pour avoir une couleur plus foncée lorsqu'on clic sur le bouton
        hsb[2] = 50;

        couleurEnfonce = new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));



    }

    /**
     * Calcule la dimension d'un carré en connaissant son hypothénuse
     * @param hypothenuse L'hypothénuse
     * @return La dimension des deux autres côté du carré
     */
    protected Dimension pythagore(int hypothenuse)
    {
        int res = (int) (hypothenuse / Math.sqrt(2));

        return new Dimension(res, res);

    }

    /**
     * Renvoie la largeur d'un <code>SubmenuItem</code>
     * @return La largeur
     */
    public int getSubmenuWidth()
    {
        return RAYON_SUBMENU - GRAND_RAYON;
    }

    /**
     * Renvoie le <code>JPaletteItem</code> à l'index passée en argument (premier indice à 0)
     * @param index Indice du <code>JPaletteItem</code>
     * @return Le <code>JPaletteItem</code>
     */
    public JPaletteItem getBtn(int index)
    {
       return lst_btn.get(index);
    }

    /**
     * Renvoie l'indice du <code>JPaletteItem</code> qui a été pressé
     * @return L'indice
     */
    public int getBtnEnfonce()
    {
        return btnEnfonce;
    }
    

    /**
     * Renvoie la largeur des <code>JPaletteItem</code>
     * @return La largeur
     */
    public int getMenuWidth()
    {
        return MOYEN_RAYON - PETIT_RAYON;
    }


    /**
     * Renvoie la taille du contour d'un sous menu de la <code>JPalette</code>
     * @return La taille en degré
     */
    public static double getDegreSubmenu()
    {
        int largeurSsMenu = RAYON_SUBMENU - GRAND_RAYON;

        int largeurImg = (int) (largeurSsMenu / Math.sqrt(2));
       
        double fraction1 = (largeurImg / 10) + largeurSsMenu / 2;
        double fraction2 = (GRAND_DIAM + largeurSsMenu) / 2;
        double fraction = fraction1 / fraction2;
        
        return Math.toDegrees(2 * Math.asin(fraction));
    }

    
    private Point polarToDegree(int r, int theta)
    {

        int x = (int) (r * Math.cos(theta * (Math.PI / -180)) + coordX);
        int y = (int) (r * Math.sin(theta * (Math.PI / -180)) + coordY);
        

        return new Point(x, y);
    }

    private void drawImage(Image img, int index, int depart, int angle, int distance, int reste, Dimension dimImage)
    { 
	        	if(index < reste)
	        		depart++;


	        	

	        		int t = (depart + angle + depart) / 2;

	        		int centreImageX = (int) (distance * Math.cos(t * (Math.PI / -180))) + coordX;
	        		int centreImageY = (int) (distance * Math.sin(t * (Math.PI / -180))) + coordY;


	        		graph.drawImage(img, (int)(centreImageX - dimImage.width / 2), (int)(centreImageY - dimImage.height / 2), this);
	        	


	        	depart += angle;
    }
    
    
    private boolean SubmenuItemFound(int depart, int degre, int depart2)
    {
        boolean res = false;

        if(theta > depart && theta <= depart + degre)
	{
            res = true;

        }				    				 
	else if(theta >= 0 && theta < depart2 && depart == depart2 - degre + 360)
        {
            res = true;
        }
				    			
        else if(enfonce && (theta >= depart && theta < 360) && depart == depart2 - degre + 360)
        {
            res = true;
        }

        return res;
    }

    /**
     * Renvoie la liste des <code>JToolTipText</code> des <code>JPaletteItem</code>
     * @param p La <code>JPalette</code>
     * @return La liste des  <code>JToolTipText</code>
     */
    public static ArrayList<String> getToolTipText(JPalette p)
    {
        ArrayList<String> res = new ArrayList<String>();

        for(JPaletteItem item : p.lst_btn)
            res.add(item.getToolTipText());

        return res;
    }

    /**
     * Renvoie une liste de tous les <code>JPaletteItem</code>
     * @return La liste
     */
    public ArrayList<JPaletteItem> getAllJPaletteItem()
    {
        ArrayList<JPaletteItem> res = new ArrayList<JPaletteItem>();

        for(JPaletteItem item : lst_btn)
            res.add(item);

        return res;
    }

}