package Jaint;

import JaintListener.EcouteurDessin;
import JaintListener.EcouteurCCC;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.ArrayList;

/**
 * Classe contenant une image à afficher
 * @author Joshua
 */
public class Pictimage extends JPanel implements Cloneable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3566561126327499216L;
	
	/**
     * Image à afficher
     */
    protected ArrayList<BufferedImage> calques = new ArrayList<BufferedImage>();
	//protected BufferedImage picture;
    
    /**
     * Dimension de l'image
     */
    protected Dimension dim;

    String path = null;
    int type = 1;
    private int margeH;
    private int margeW;
    private EcouteurDessin ed = null;
    private EcouteurCCC eCCC = null;

    /**
     *
     * @param img Image à afficher
     * @param path Chemin d'accès du fichier
     */

    public Pictimage(Image img,String path)
    {
        super();
        this.path = path;
        findType(path);
        dim = new Dimension(img.getWidth(this),img.getHeight(this));
        calques.add(new BufferedImage((int)dim.getWidth(),(int)dim.getHeight(),type));
        setBounds(0,0,(int)dim.getWidth(),(int)dim.getHeight());
        setVisible(true);
        setPreferredSize(dim);
        Graphics g = calques.get(0).getGraphics();
        g.drawImage(img, 0, 0, this);
    }

    /**
     *
     * @param path Chemin d'accès du fichier
     * @throws IOException 
     */
    public Pictimage(String path) throws IOException
    {
    	this(ImageIO.read(new File(path)),path);
    }

    /**
     * Renvoi la taille de l'image affichée
     * @return Dimension de l'image
     */
    @Override
    public Dimension getPreferredSize()
    {
    	return new Dimension(dim);
    }

    /**
     * Renvoi le chemin d'accès du fichier correspondant à cette image (null si il n'est pas spécifié)
     * @return Chemin d'accès
     */
    public String getPath()
    {
        if (this.path == null)
            return null;
        else
            return new String(this.path);
    }

    /**
     * Définir le chemin d'accès de l'image
     * @param path Chemin d'accès
     */
    public void setPath(String path)
    {
        this.path = path;
    }

    /**
     * Renvoi une copie de l'image affichée
     * @return Nouvelle BufferedImage
     */
    public BufferedImage getBufferedImage()
    {
        BufferedImage res = null;
        
        res = new BufferedImage((int)dim.getWidth(),(int)dim.getHeight(),type);
        Graphics g = res.getGraphics();
        for(BufferedImage picture : calques)
        	g.drawImage(picture, 0, 0, this);
       
        return res;
    }


    /**
     * Spécifier l'image à afficher
     * @param bi Image à afficher
     */
    public void setBufferedImage(BufferedImage bi)
    {
    	if (bi != null)
        {
    		setBufferedImage(bi,bi.getType());
        }

    	repaint();
    }

    /**
     * Specifier l'image à afficher et son type
     * @param bi Image à afficher
     * @param typeImage Type de l'image (cf types de BufferedImage)
     */
    public void setBufferedImage(Image bi, int typeImage)
    {
    	if (bi != null)
        {
    		calques = new ArrayList<BufferedImage>();
            calques.add(new BufferedImage(bi.getWidth(this),bi.getHeight(this),typeImage));
            Graphics g = calques.get(0).getGraphics();
            g.drawImage(bi, 0, 0, this);
            dim = new Dimension(calques.get(0).getWidth(),calques.get(0).getHeight());
            type = typeImage;
        }

    	repaint();
    }

    /**
     * Definit automatiquement le type de l'image selon son extension
     * @param path Chemin d'accès ou nom du fichier
     */
    public void findType(String path)
    {
        if(!path.equals(""))
        {
            String ext = path.substring(path.length() - 3);

            if (ext.equals("png"))
            {
                type = BufferedImage.TYPE_INT_ARGB;
            }
            else if (ext.equals("gif"))
            {
                type = BufferedImage.TYPE_4BYTE_ABGR;
            }
            else
            {
                type = BufferedImage.TYPE_INT_RGB;
            }
        }
        else
        {
            type = BufferedImage.TYPE_INT_RGB;
        }
    }

    /**
     * Renvoi le type de l'image courante
     * @return Type d'image (cf BufferedImage)
     */
    public int getType()
    {
        return type;
    }

    @Override
    public void paintComponent(Graphics g)
    {
            super.paintComponent(g);
            
            if(ed != null && eCCC != null)
            {
                margeW = (getWidth() - dim.width)/2;
                margeH = (getHeight() - dim.height)/2;
                ed.setMarges(margeW,margeH);
                eCCC.setMarges(margeW, margeH);
            }
            else
            {
                setSize(new Dimension(getWidth()-margeW,getHeight()-margeH));
                margeH = 0;
                margeW = 0;
            }
            
            for(BufferedImage picture : calques)
            	g.drawImage(picture, margeW, margeH, dim.width, dim.height, this);
    }

    @Override
    public String toString()
    {
        return calques.get(0).toString();
    }

    /**
     * Convertit une <code>Image</code> en <code>BufferedImage</code>
     * @param image Image à convertir
     * @return Nouvelle BufferedImage correspondant à l'image passée en paramètre
     */
    public static BufferedImage toBufferedImage(Image image)
    {
        /** On test si l'image n'est pas déja une instance de BufferedImage */
        if( image instanceof BufferedImage )
        {
                return( (BufferedImage)image );
        }
        else
        {
                /** On s'assure que l'image est complètement chargée */
                image = new ImageIcon(image).getImage();

                /** On crée la nouvelle image */
                BufferedImage bufferedImage = new BufferedImage(
                            image.getWidth(null),
                            image.getHeight(null),
                            BufferedImage.TYPE_INT_ARGB );

                Graphics g = bufferedImage.createGraphics();
                g.drawImage(image,0,0,null);
                g.dispose();

                return( bufferedImage );
        }
    }

    void setDessinListener(EcouteurDessin ED) {
        ed = ED;
    }

    void setCCCListener(EcouteurCCC ECCC) {
        eCCC = ECCC;
    }

    @Override
    public Dimension getSize()
    {
    	if(calques.size() == 0)
    		return null;
        return new Dimension(calques.get(0).getWidth(),calques.get(0).getHeight());
    }

}