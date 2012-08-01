package Jaint;


import java.awt.Dimension;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JMenuItem;

/**
 * Classe creant des objets pouvant etre ajoute a une JPalette, et pouvant stocker des SubmenuItem
 * @author Jeff
 */
public class JPaletteItem extends JMenuItem
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4349769701151427152L;
	private Image img;
        private ArrayList<SubmenuItem> lst_Btn = new ArrayList<SubmenuItem>();
        private ArrayList<SubmenuItem> btnVisible = new ArrayList<SubmenuItem>(17);
        private boolean sousMenu = false;
        private boolean ouvert = false;
        private boolean enabled = true;
        private Dimension dimImg;
        private String toolTypeText;

        //permet de gerer le decalage des boutons lorsqu'ils y en a trop dans ce sous menu
        
        
        private SubmenuItem FLECHE_RETOUR;
        private SubmenuItem FLECHE_SUIVANT;

        private int nbMax;
        private int curseur = nbMax - 1;
        private boolean nbMaxTrouve = false;

    /**
     * Construit le JPaletteItem
     */
    public JPaletteItem()
    {
    	super();
        dimImg = pythagore(JPalette.MOYEN_RAYON - JPalette.PETIT_RAYON);
        
        FLECHE_RETOUR = new SubmenuItem("./lib/flecheRetour.png");
        FLECHE_SUIVANT = new SubmenuItem("./lib/flecheSuivant.png");

        initListener();
    }
    
    /**
     * Construit un JPaletteItem avec une image
     * @param image Chemin de l'image
     */
    public JPaletteItem(String image)
    {
    	super();

        dimImg = pythagore(JPalette.MOYEN_RAYON - JPalette.PETIT_RAYON);
        
    	img = new ImageIcon(image).getImage().getScaledInstance(dimImg.width, dimImg.height, Image.SCALE_SMOOTH);

        FLECHE_RETOUR = new SubmenuItem("./lib/flecheRetour.png");
        FLECHE_SUIVANT = new SubmenuItem("./lib/flecheSuivant.png");


        initListener();
    }
    
    /**
     * Change l'image d'un bouton
     * @param image Chemin de la nouvelle image
     */
    public void setImage(String image)
    {
    	img = new ImageIcon(image).getImage().getScaledInstance((int)dimImg.getWidth(),(int) dimImg.getHeight(), Image.SCALE_SMOOTH);
    }
    
    /**
     * Change l'image d'un bouton
     * @param image La nouvelle image
     */
    public void setImage(Image image)
    {
    	img = image.getScaledInstance(dimImg.width, dimImg.height, Image.SCALE_SMOOTH);
    }
    
    /**
     * Retourne l'image du JPaletteItem
     * @return L'image
     */
    public Image getImage()
    {
    	return new ImageIcon(img).getImage();
    }

    /**
     * Retire un SubmenuItem de ce JPaletteItem
     * @param index Indice du SubmenuItem (premier indice vaut 0)
     */

    public void removeButton(int index)
    {
       lst_Btn.remove(index);
       curseur--;
       
       if(index < btnVisible.size())
           btnVisible.remove(index);
    }

    /**
     * Enleve tous les SubmenuItem de ce JPaletteItem
     */
    @Override
    public void removeAll()
    {
        while(!lst_Btn.isEmpty())
            lst_Btn.remove(0);
        
        while(!btnVisible.isEmpty())
            btnVisible.remove(0);

        curseur = nbMax - 1;
    }


    /**
     * Ajoute un SubmenuItem
     * @param item
     */
    public void add(SubmenuItem item)
    {
        if(sousMenu)
            lst_Btn.add(item);
        else
            throw new IndexOutOfBoundsException();

        if(!nbMaxTrouve)
        {
            findnbMax();
        }

        if(btnVisible.size() >= nbMax && nbMaxTrouve)
        {
           if(!btnVisible.contains(FLECHE_RETOUR))
           {
               btnVisible.add(0, FLECHE_RETOUR);
               btnVisible.set(btnVisible.size() - 1, FLECHE_SUIVANT);
               btnVisible.remove(btnVisible.size() - 2);

           }

           curseur = nbMax - 1;
        }
        else
        {
            btnVisible.add(item);
        }
    }
    
    /**
     * Teste si le JPaletteItem est ouvert
     * @return Vrai si ouvert
     */
    public boolean isOpen()
    {
        return ouvert;
    }
    
    /**
     * Permet d'ouvrir ou de fermer le JPaletteItem au prochain rafraichissement de la JPalette
     * @param state Ouvert si vrai, ferme sinon
     */
    public void setOpen(boolean state)
    {
        ouvert = state;
    }

    /**
     * Teste si le JPaletteItem a un sous menu
     * @return Vrai si a un sous menu
     */
    public boolean hasSubmenu()
    {
        return sousMenu;
    }

    /**
     * Permet d'ajouter des SubmenuItem a ce JPaletteItem
     */
    public void addSubmenu()
    {
        sousMenu = true;
    }

    /**
     * Renvoie le SubmenuItem a l'indice voulu
     * @param index L'indice du SubmenuItem
     * @return Le SubmenuItem
     */
    public SubmenuItem getBtn(int index)
    {
        return btnVisible.get(index);
    }

    /**
     * Renvoie le nombre de SubmenuItem
     * @return Le nombre
     */
    public int nbSubmenuItem()
    {
        return btnVisible.size();
    }

    @Override
    public void setEnabled(boolean state)
    {

        enabled = state;
    }

    @Override
    public boolean isEnabled()
    {
        return enabled;
    }

    private Dimension pythagore(int hypothenuse)
    {
        int carre = (int) (Math.pow(hypothenuse, 2));
        int largeur = carre / 2;

        int res = (int) Math.sqrt(largeur);

        return new Dimension(res, res);

    }

    /**
     * Renvoie la reference de la liste comportant les SubmenuItem visible dans la JPalette
     * @return La liste des SubmenuItem
     */
    public ArrayList<SubmenuItem> getLstBtn()
    {
        return btnVisible;
    }

    
    @SuppressWarnings("unused")
	private int getCurseur()
    {
        return curseur;
    }

    /**
     * Incremente le curseur s'il n'est pas en dehors de la liste des SubmenuItem
     * @param list La liste des SubmenuItem visible dans la JPalette
     * @param fullList La liste complete
     */
    public void curseurPlus(ArrayList<SubmenuItem> list, ArrayList<SubmenuItem> fullList)
    {
        int taille = list.size();
        
        if(curseur <= fullList.size() && taille <= nbMax + 1)
        {  
            list.remove(1);
            list.add(taille - 2, fullList.get(curseur - 1));
            curseur++;
        }
    }
    
    /**
     *
     * Decremente le curseur s'il n'est pas en dehors de la liste des SubmenuItem
     * @param list La liste des SubmenuItem visible dans la JPalette
     * @param fullList La liste complete
     */
    public void curseurMoins(ArrayList<SubmenuItem> list, ArrayList<SubmenuItem> fullList)
    {
        if(curseur >= nbMax)
        {
            curseur--;
            
            int taille = btnVisible.size();

            list.remove(taille - 2);
            list.add(1, fullList.get(curseur - nbMax + 1));
        }
    }

    private void initListener()
    {
        FLECHE_RETOUR.addActionListener(new ActionListener()
        {
           public void actionPerformed(ActionEvent ae)
           {
               JPalette p = (JPalette) ae.getSource();
               JPaletteItem btn = p.getBtn(p.getBtnEnfonce() - 1);

               ArrayList<SubmenuItem> lst = btn.getLstBtn();
               ArrayList<SubmenuItem> lstComplete = btn.getFullList();

               curseurMoins(lst, lstComplete);
           }
        });

        FLECHE_SUIVANT.addActionListener(new ActionListener()
        {
           public void actionPerformed(ActionEvent ae)
           {
               JPalette p = (JPalette) ae.getSource();
               JPaletteItem btn = p.getBtn(p.getBtnEnfonce() - 1);
          
               ArrayList<SubmenuItem> lst = btn.getLstBtn();
               ArrayList<SubmenuItem> lstComplete = btn.getFullList();

               curseurPlus(lst, lstComplete);
               //JPaletteItem.this.palette.repaint();
           }
        });
    }

    @SuppressWarnings("unused")
	private ArrayList<SubmenuItem> copy(ArrayList<SubmenuItem> lst)
    {
        ArrayList<SubmenuItem> res = new ArrayList<SubmenuItem>(16);

        for(SubmenuItem item : lst)
        {
            res.add(item);
        }

        return res;
    }

    /**
     * Renvoie la reference de la liste complete des SubmenuItem
     * @return La liste complete
     */
    public ArrayList<SubmenuItem> getFullList()
    {
        return lst_Btn;
    }

    private void findnbMax()
    {

        double constante = 21;
        int taille = nbSubmenuItem() + 1;
   
        if(taille * constante >= 360)
        {
            nbMax = taille - 1;
            nbMaxTrouve = true;
        }

    }


    @Override
    public void setToolTipText(String text)
    {
        toolTypeText = text;
    }

    @Override
    public String getToolTipText()
    {
        return toolTypeText;
    }

    /**
     * Renvoie la liste des infobulles
     * @param p Le JPaletteItem en question
     * @return La liste des infobulles
     */
    public static ArrayList<String> getToolTipText(JPaletteItem p)
    {
        ArrayList<String> res = new ArrayList<String>();

        for(SubmenuItem item : p.lst_Btn)
            res.add(item.getToolTipText());

        return res;
    }

    /**
     * Renvoie la liste de tous les SubmenuItem en cassant la reference
     * @return La liste complete
     */
    public ArrayList<SubmenuItem> getAllSubmenuItem()
    {
        ArrayList<SubmenuItem> res = new ArrayList<SubmenuItem>();

        for(SubmenuItem item : lst_Btn)
            res.add(item);

        return res;
    }
}