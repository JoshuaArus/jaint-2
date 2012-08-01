package Jaint;

/**
 *
 * @author Jeff
 */

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.awt.print.*;

/**
 * Classe permettant l'impression d'une image.
 */
public class Imprimante implements Printable
{
	private BufferedImage image;
	private IHM ihm;

        /**
         * Construit l'impression
         * @param i
         * @param image
         */
        public Imprimante(IHM i, BufferedImage image)
	{
		this.image = image;
		this.ihm = i;
	}
	public int print(Graphics g, PageFormat pf, int page) throws PrinterException
	{
		if (page > 0)
		{
                    return NO_SUCH_PAGE;
		}

		Graphics2D g2d = (Graphics2D)g;
		g2d.translate(pf.getImageableX(), pf.getImageableY());

		g.drawImage(this.image, 0, 0, this.ihm);

		return PAGE_EXISTS;
	}
        /**
         *
         */
        public void applyPrint()
	{

                PrinterJob printJob = PrinterJob.getPrinterJob();

                PageFormat pageFormat = printJob.defaultPage();



                printJob.setPrintable( this, pageFormat);



                if (printJob.printDialog())
                { // le dialogue d’impression

                      try
                      {
                            printJob.print();
                      }
                      catch (PrinterException ex)
                      {
                            JOptionPane.showMessageDialog(this.ihm, "Printing Error :\n"+ex.getMessage(), "Printing Error", JOptionPane.ERROR_MESSAGE);

                      }

                }
	}

        /**
         * Permet de créer directement une impression
         * @param ihm
         */
        public static void print(IHM ihm)
        {
            Imprimante impr = new Imprimante(ihm, ihm.jaint.getImageCourante());
            impr.applyPrint();
        }
}
