package JaintListener;

import Jaint.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.dnd.InvalidDnDOperationException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;

/**
 * Classe réalisant le drag and drop d'image et de plugin
 * @author Jonas
 */
public class DropImageListener implements DropTargetListener
{
	private IHM ihm;
        private Jaint jaint;


    /**
     *Constructeur
     * @param i ihm courante
     * @param j Jaint
     */
    public DropImageListener(IHM i, Jaint j)
	{
		ihm = i;
                jaint = j;
	}

	public void dragEnter(DropTargetDragEvent dtde) {}

	public void dragExit(DropTargetEvent dte) {}

	public void dragOver(DropTargetDragEvent dtde) {}


	public void drop(DropTargetDropEvent dtde)
	{

		Transferable t = dtde.getTransferable();


		/*
		*Code windows
		*/

		if(t.isDataFlavorSupported(DataFlavor.javaFileListFlavor))
		{


			dtde.acceptDrop(dtde.getDropAction());

			try
			{
				@SuppressWarnings("rawtypes")
				List lst_file = (List) t.getTransferData(DataFlavor.javaFileListFlavor);
				for(Object o : lst_file)
				{
                                        //Si c'est une image
                                        if(((File)o).toString().endsWith(".jpg") || ((File)o).toString().endsWith(".jpeg") || ((File)o).toString().endsWith(".JPG") || ((File)o).toString().endsWith(".png") ||((File)o).toString().endsWith(".bmp"))
                                        {
                                           ihm.addTab(((File)o).toString());
                                        }
                                        //Si c'est un plugin
                                        else if(((File)o).toString().endsWith(".class"))
                                        {
                                           
                                           this.jaint.addPlugin(((File)o));
                                           this.ihm.initPluginsMenu();
                                           

                                        }
                                        else
                                        {
                                            System.err.println("Fichier invalide");
                                        }
				}
			}
			catch (UnsupportedFlavorException e)
			{
				System.err.println("Ce type de fichier n'est pas reconnu.");
				return;
			}
			catch (IOException e)
			{
				System.err.println("Erreur d'entrée-sortie");
			}
		}
		else //Code pour unix et mac
		{
			
			DataFlavor df[] = t.getTransferDataFlavors();
			boolean importer = false;

			for(int i=0; i<df.length; i++)
			{
				if(df[i].isRepresentationClassReader())
				{

					dtde.acceptDrop(dtde.getDropAction());
					Reader r;
					try
					{
						r = df[i].getReaderForText(t);
						BufferedReader bf = new BufferedReader(r);
						String ligne;
						File f;

						while((ligne = bf.readLine()) != null)
						{
							f = new File(new URI(ligne));
                                                       

                                                        if(f.toString().endsWith(".jpg") || f.toString().endsWith(".jpeg") || f.toString().endsWith(".JPG")|| f.toString().endsWith(".png") ||f.toString().endsWith(".bmp"))
                                                        {
                                                            ihm.addTab(f.toString());
                                                            importer = true;

                                                        }
                                                        else  if(f.toString().endsWith(".class")) //Si le fichier est plugins
                                                        {
                                                           
                                                           this.jaint.addPlugin(f);
                                                           this.ihm.initPluginsMenu();
                                                           importer = true;
                                                        }
                                                        else
                                                        {
                                                        }
							

						}
					}
					catch (UnsupportedFlavorException e)
					{
					}
					catch (IOException e)
					{
						System.err.println("Erreur d'entrée-sortie");
					}
					catch (URISyntaxException e)
					{
						System.err.println("Adresse du fichier incorrecte");
					}
					catch (InvalidDnDOperationException e)
					{
						//On ignore
					}

					dtde.getDropTargetContext().dropComplete(importer);
				}
			}

			if(!importer)
			{
				System.err.println("Ce fichier n'a pu être importé");
			}
		}
	}

	public void dropActionChanged(DropTargetDragEvent dtde) {}

}


