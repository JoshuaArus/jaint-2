package JaintPlug;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.awt.Graphics2D;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import javax.swing.ImageIcon;

/**
*
*	Classe regroupant une partie des fonctions static utilisable par toutes les autres classes
*/
public class Fonction
{
	/**
	*	Modifie le type d'une BufferedImage passee en parametre
	*
         *	@param img
	*	@param	type		Type voulu pour la nouvelle image
	*	@return	Image identique a la premiere mais avec un type different
	*/
	public static BufferedImage modif_type( BufferedImage img, int type )
	{
		// creation de la nouvelle image au meme dimension avec le nouveau type
		BufferedImage img_new = new BufferedImage( img.getWidth(), img.getHeight(), type );
	
		// recup du graphics de la nouvelle image
		Graphics g = img_new.getGraphics();
	
		// dessin de l'ancienne image sur le graphics
		g.drawImage( img, 0, 0, null );
	
	
		return img_new;
	}

	/**
	* fusion de deux images
	* @param img_sous image de base
	* @param img_sur image a copier par dessus 
	* @return image fusioner
	*/
	
	public static BufferedImage fusion( BufferedImage img_sous, BufferedImage img_sur )
	{
		int largeur, longueur;    // |
	
		if ( img_sous.getWidth() > img_sur.getWidth() )    //
		{
			largeur = img_sous.getWidth();                 // Necessaire pour
		}
		else                                               // obtenir la taille
		{
			largeur = img_sur.getWidth();                  // de l'image que
		}
	
		// l'ont obtiendra
		if ( img_sous.getHeight() > img_sur.getHeight() )    // au final
		{
			longueur = img_sous.getHeight();                 //
		}
		else                                                 //
		{
			longueur = img_sur.getHeight();                  //
		}
	
		// creation de la nouvelle image
		BufferedImage img_fusion = new BufferedImage( largeur, longueur, BufferedImage.TYPE_4BYTE_ABGR );
	
		// recuperation du graphics
		Graphics g_fusion = img_fusion.getGraphics();
	
		// collage des deux images sur l'image final avec un fond transparent
		g_fusion.drawImage( img_sous, 0, 0, new Color( 255, 255, 255, 0 ), null );
		g_fusion.drawImage( img_sur, 0, 0, new Color( 255, 255, 255, 0 ), null );
	
	
		return img_fusion;
	}
	/**
	*	Methode convertissant une Image en BufferedImage
	*
         *	@param img
         * @return	BufferedImage identique a l'image de base
	*/
	public static BufferedImage buffToImage(Image img) 
	{
		BufferedImage image = new BufferedImage(img.getWidth(null),img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		image.getGraphics().drawImage(img, 0, 0, null);
		return image;
	}
	
	/**
	*	Fonction remplassant les %20 d'une chaine de caracteres et l'ecrivant dans un fichier dont le nom est passe en parametre
	*
	*	@param	s	Nom du fichier dans lequel il faut placer le fichier
	*	@param	text	Texte a placer dans le fichier	
	*	@return	Vrai si le texte a ete correctement place
	*/
	public static boolean ecrireTout( String s , String text)
	{
		s = s.replaceAll("%20", " ");
		return Fonction.ecrireTout(new File(s), text);
	}
	/**
	*	Fonction ecrivant une chaine dans un fichier passe en parametre
	*
         *	@param f
	*	@param	text		Texte a placer dans le fichier	
	*	@return	Vrai si le texte a ete correctement place
	*/
	public static boolean ecrireTout( File f , String text)
	{
		PrintWriter ecrivain = null;
		boolean res = true;
		try 
		{
			ecrivain = new PrintWriter( new BufferedWriter( new OutputStreamWriter( new FileOutputStream( f,false ), "UTF8") ) );
			ecrivain.println(text);
		}
		catch (IOException ex)
		{
			
			res = false;
		}
		finally
		{
			ecrivain.close();
		}
		return res;
	}
	/**
	*	Methode permettant de deplacer un fichier vers un emplacement donne
	*
         *	@param dir
         * @param	emplacement	Emplacement dans lequel on veut mettre le fichier
	*	@return	Vrai si le fichier a ete correctement deplace
	*/
	public static boolean deplacement(File dir, String emplacement)
	{
		boolean depla = false;
		if(copie(dir, emplacement) && del(dir))
			depla = true;

		return depla;
	}
	/**
	*	Methode copiant un fichier d'un endroit vers un autre
	*	
         *	@param ficLu
         * @param	emplacement	Emplacement vers lequel il faut copier le fichier
	*	@return	Vrai si le fichier a ete correctement copie
	*/
	public static boolean copie(File ficLu, String emplacement)
	{
		boolean copieOk = true ;
		if(ficLu.isDirectory())
		{
			
			new File(emplacement +"/"+ ficLu.getName()).mkdir();
			File[] children = ficLu.listFiles();
			for( File f : children )
				copieOk = (Fonction.copie(f,emplacement +"/"+ ficLu.getName()) && copieOk );
		}
		else
		{
			File ficEcrit;
			if( new File(emplacement).isDirectory() )
				ficEcrit = new File(emplacement+"/"+ficLu.getName());
			else
				ficEcrit = new File(emplacement);
			
			try
			{
				FileInputStream read = new FileInputStream(ficLu);
				try
				{
					FileOutputStream write = new FileOutputStream(ficEcrit);
					try
					{
						byte[] tabLu = new byte[1024];
						int nbLu;
						while((nbLu = read.read(tabLu)) > 0)
						{
							write.write(tabLu,0,nbLu);
						}
					}
					catch(Exception ioe)
					{
						
						copieOk = false;
					}
					finally
					{
						try
						{
							write.close();
						}
						catch(IOException ioe)
						{
							
							copieOk = false;
						}
					}
				}
				catch(Exception ioe)
				{
					
					copieOk = false;
				}
				finally
				{
					try 
					{
						read.close();
					}
					catch(IOException ioe)
					{
						
						copieOk = false;
					}
				}
			}
			catch(FileNotFoundException fnfe)
			{
				
				copieOk = false;
			}
		}
	return copieOk;
	}
	/**
	*	Methode comparant deux couleurs avec une tolerance pour decider s'ils sont identique ou non
	*
	*	@param	pixel1		Premier pixel a comparer
	*	@param	pixel2		Deuxieme pixel a comparer
	*	@param	tolerance		Tolerance pour la comparaison
	*	@return	Vrai s'ils sont identiques, faux sinon.
	*/
	public static boolean couleurIdentique(int pixel1,int pixel2, int tolerance)
	{
		boolean estIdentique = false;
	
		Color couleur1 = new Color(pixel1);
		Color couleur2 = new Color(pixel2);
	
		if(!(Math.abs(couleur1.getRed()- couleur2.getRed()) > tolerance))
			if(!(Math.abs(couleur1.getBlue()- couleur2.getBlue()) > tolerance))
				if(!(Math.abs(couleur1.getGreen()- couleur2.getGreen()) > tolerance))
					estIdentique = true;

	return estIdentique;
	}
	/**
	*
	*	Methode permettant de supprimer un fichier
	*
         *	@param dir
         * @return	Vrai si le fichier a ete correctemetn supprime, faux sinon.
	*/
	public static boolean del(File dir)
	{
		boolean success = true;
		if (dir.isDirectory()) 
		{
			File[] children = dir.listFiles();
			for( File f : children )
				success = (Fonction.del(f) && success );
			
			success = (dir.delete() && success );
		}
		else
			success = dir.delete();
		
	return success;
	}
	/**
	*
	*	Methode permettant de cloner une BufferedImage
	*
         *	@param bufImg
         * @return	Image clone de l'image passee en parametre
	*/
	public static BufferedImage cloneBuff(BufferedImage bufImg)
	{
		BufferedImage newBufferImg ;
		if( bufImg != null)
		{
			int type = bufImg.getType();
	
			if ( type == BufferedImage.TYPE_CUSTOM )
			{
				type = bufImg.getColorModel().hasAlpha()? BufferedImage.TYPE_INT_ARGB: BufferedImage.TYPE_INT_RGB;
			}
		
			newBufferImg = new BufferedImage( bufImg.getWidth(), bufImg.getHeight(), type );
			Graphics2D    g            = newBufferImg.createGraphics();
		
			g.drawRenderedImage( bufImg, null );
			g.dispose();
		}
		else 
			newBufferImg = null;
			
	return newBufferImg;	
	}
	/**
	*	Test si la chaine passee en parametre est un entier positif
	*	
	*	@param	test	Chaine de caractere a tester
	*	@return Vrai si la chaine est un entier positif,sinon faux
	*/
	public static boolean estUnEntierPositif(String test)
	{
		return test.matches( "[0-9]+" );
	}
	/**
	*	Test si la chaine passee en parametre est un entier relatif, soit positif ou negatif
	*	
	*	@param	test	Chaine de caractere a tester
	*	@return Vrai si la chaine est un entier relatif,sinon faux
	*/
	public static boolean estUnEntierRelatif(String test)
	{
		return test.matches( "-?[0-9]+" );
	}
	/**
	*	Test si la chaine passee en parametre est un decimal positif
	*	
	*	@param	test	Chaine de caractere a tester
	*	@return Vrai si la chaine est un decimal positif,sinon faux
	*/
	public static boolean estUnDecimalPositif(String test)
	{
		return test.matches( "[0-9]+([.,][0-9]*)?" );
	}
	/**
	*	Test si la chaine passee en parametre est un decimal relatif, soit positif ou negatif
	*	
	*	@param	test	Chaine de caractere a tester
	*	@return Vrai si la chaine est un decimal relatif,sinon faux
	*/
	public static boolean estUnDecimalRelatif(String test)
	{
		return test.matches( "-?[0-9]+([.,][0-9]*)?" );
	}
	
	/**
	*	Transforme une chaine en Integer ou en Double suivant la valeur contenue dedans
	*
	*	@param	test		Chaine dont il faut extraire la valeur
	*	@return	Valeur contenue dans la chaine
	*/
	/*public static double getValue(String test)
	{
		double valeur = 0;
		if(test.indexOf(',') != -1)
			test = test.replace(',','.');
	
		if(test.charAt(test.length()-1) == ',' || test.charAt(test.length()-1) == '.')
			test += '0';
	
		if(estUnEntierRelatif(test))
			valeur = Integer.valueOf( test );
		else if(estUnDecimalRelatif(test))
			valeur = Double.valueOf(test);
	
		return valeur;
	}*/
	/**
	*	Methode comparant deux BufferedImages et renvoyant la difference sous forme d'entier
	*
	*	@param	img1	Premiere BufferedImage a comparer
	*	@param	img2	Deuxieme BufferedImage a comparer
	*	@return	Valeur de la difference entre les BufferedImages. 0 si elles sont identiques, -1 si elles sont de tailles differentes ou n avec n egale au nombre de pixel different
	*/
	public static int compareImage(BufferedImage img1, BufferedImage img2)
	{
		int nbErreur = 0;
		if(img1!= null && img2 != null && img1.getWidth() == img2.getWidth() && img1.getHeight() == img2.getHeight())
		{
			for(int i = 0; i < img2.getHeight();i++)
				for(int j = 0; j < img2.getWidth();j++) 
					if( img1.getRGB(j,i) != img2.getRGB(j,i) )
					nbErreur++;
		}
		else 
			nbErreur = -1;
	
	return nbErreur;    
	}
	/**
	*	Methode comparant deux Images et renvoyant la difference sous forme d'entier
	*
	*	@param	img1	Premiere Image a comparer
	*	@param	img2	Deuxieme Image a comparer
	*	@return	Valeur de la difference entre les Images. 0 si elles sont identiques, -1 si elles sont de tailles differentes ou n avec n egale au nombre de pixel different
	*/
	public static int compareImage(Image img1, Image img2)
	{
		return (img1== null || img2 == null )?-1:compareImage(buffToImage(img1),buffToImage(img2));
	}
	/**
	*	Methode comparant deux ImageIcon et renvoyant la difference sous forme d'entier
	*
	*	@param	img1	Premiere ImageIcon a comparer
	*	@param	img2	Deuxieme ImageIcon a comparer
	*	@return	Valeur de la difference entre les ImageIcons. 0 si elles sont identiques, -1 si elles sont de tailles differentes ou n avec n egale au nombre de pixel different
	*/
	public static int compareImage(ImageIcon img1 ,ImageIcon img2)
	{
		return (img1== null || img2 == null )?-1:compareImage(img1.getImage(), img2.getImage());
	}
}
