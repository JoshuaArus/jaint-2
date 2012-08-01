//package jaint;
//
//import Jaint.IHM;
//import java.awt.BorderLayout;
//import java.awt.Component;
//import java.awt.Dimension;
//import java.awt.Graphics;
//import java.awt.Image;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.WindowEvent;
//import java.awt.event.WindowListener;
//import java.awt.image.BufferedImage;
//
//import javax.media.Buffer;
//import javax.media.CaptureDeviceInfo;
//import javax.media.CaptureDeviceManager;
//import javax.media.Manager;
//import javax.media.MediaLocator;
//import javax.media.Player;
//import javax.media.control.FrameGrabbingControl;
//import javax.media.format.VideoFormat;
//import javax.media.util.BufferToImage;
//import javax.swing.JButton;
//import javax.swing.JDialog;
//import javax.swing.JPanel;
//import javax.swing.JOptionPane;
//
//
//
///**
// * <code>JDialog</code> permettant la prise d'une capture d'image de la webcam.
// */
//public class Webcam extends JDialog implements ActionListener
//{
//	private static Player player;
//	private CaptureDeviceInfo di;
//	private MediaLocator ml;
//	private JButton capture;
//	private Buffer buf;
//	private Image img;
//	private VideoFormat vf;
//	private BufferToImage btoi;
//
//	private IHM ihm;
//
//	private JPanel jp;
//
//        /**
//         * Construit le JDialog
//         * @param i
//         */
//        @SuppressWarnings("static-access")
//	public Webcam(IHM i)
//	{
//		super(i, "Webcam", true);
//		this.ihm = i;
//		this.jp = new JPanel(new BorderLayout());
//
//		this.capture = new JButton("Capture");
//		this.capture.addActionListener(this);
//
//                this.addWindowListener(new WindowListener()
//                {
//
//                    public void windowOpened(WindowEvent e)
//                    {
//
//                    }
//
//                    public void windowClosing(WindowEvent e)
//                    {
//
//                    }
//
//                    public void windowClosed(WindowEvent e) {
//
//                    }
//
//                    public void windowIconified(WindowEvent e) {
//
//                    }
//
//                    public void windowDeiconified(WindowEvent e) {
//
//                    }
//
//                    public void windowActivated(WindowEvent e) {
//
//                    }
//
//                    public void windowDeactivated(WindowEvent e) {
//
//                    }
//
//                });
//
//		try
//		{
//			String str1 = "vfw:Logitech USB Video Camera:0";
//			String str2 = "vfw:Microsoft WDM Image Capture (Win32):0";
//			this.di = CaptureDeviceManager.getDevice(str1);
//			this.ml = new MediaLocator("vfw://0");
//
//			this.player = Manager.createRealizedPlayer(this.ml);
//			this.player.start();
//			Component comp;
//
//			if ((comp = player.getVisualComponent()) != null)
//			{
//				this.jp.add(comp,BorderLayout.NORTH);
//			}
//			this.jp.add(this.capture);
//
//			this.add(this.jp);
//			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			this.pack();
//
//			Dimension d = this.getSize();
//			Dimension dScreen = this.getToolkit().getScreenSize();
//			this.setLocation((int)(dScreen.getWidth()/2 - d.getWidth()/2), (int)(dScreen.getHeight()/2 - d.getHeight()/2));
//
//			this.setVisible(true);
//		}
//		catch (Exception e)
//		{
//			JOptionPane.showMessageDialog(this, "            Webcam not found.\nPlease install Java Media Framework !", "Webcam error", JOptionPane.ERROR_MESSAGE);
//			this.dispose();
//		}
//	}
//
//
//	public void actionPerformed(ActionEvent e)
//	{
//		// Grab a frame
//		FrameGrabbingControl fgc = (FrameGrabbingControl) player.getControl("javax.media.control.FrameGrabbingControl");
//		buf = fgc.grabFrame();
//
//		// Convert it to an image
//		btoi = new BufferToImage((VideoFormat)buf.getFormat());
//
//		img = btoi.createImage(buf);
//		BufferedImage bi = new BufferedImage(
//				img.getWidth(null),
//				img.getHeight(null),
//				BufferedImage.TYPE_INT_RGB );
//
//		Graphics g = bi.createGraphics();
//		g.drawImage(img,0,0,null);
//		g.dispose();
//
//		this.ihm.applyWebcam(bi);
//
//		player.close();
//		player.deallocate();
//		this.dispose();
//	}
//}
