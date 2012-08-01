package Jaint;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;

/**
 *
 * @author Joshua
 */
public class AideEnLigne extends JDialog implements ActionListener, HyperlinkListener
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6589019419499256124L;

	public class clavListener implements KeyListener
    {
        AideEnLigne fenetre;

        private clavListener(AideEnLigne aThis) {
            fenetre = aThis;
        }

        public void keyTyped(KeyEvent e) {
            ;
        }

        public void keyPressed(KeyEvent e) {
            ;
        }

        public void keyReleased(KeyEvent e) {
            String touche = KeyEvent.getKeyText(e.getKeyCode());
            if (touche.equals("F1") || touche.equals("Esc"))
                fenetre.dispose();
        }

    }
    public class previousListener implements ActionListener
    {
        ArrayList<URL> page = new ArrayList<URL>();
        AideEnLigne ael = null;

        private previousListener(AideEnLigne aThis) {
            ael = aThis;
        }
        public void actionPerformed(ActionEvent arg0) {
            if(page.size() > 0)
            {
                try {
                    ael.nL.setPage(ael.getPage());
                    ael.setPage(page.get(page.size()-1));
                    page.remove(page.size()-1);
                    if (page.size() == 0)
                            ael.previous.setEnabled(false);
                } catch (Exception ex) {
                    Logger.getLogger(AideEnLigne.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        private void setPage(URL page) {
            this.page.add(page);
            ael.previous.setEnabled(true);
        }


    }

    public class nextListener implements ActionListener
    {
        ArrayList<URL> page = new ArrayList<URL>();
        AideEnLigne ael = null;

        private nextListener(AideEnLigne aThis) {
            ael = aThis;
        }
        public void actionPerformed(ActionEvent arg0) {
            if(page.size() > 0)
            {
                try {
                    ael.pL.setPage(ael.getPage());
                        ael.setPage(page.get(page.size()-1));
                        page.remove(page.size()-1);
                        if (page.size() == 0)
                            ael.next.setEnabled(false);
                } catch (Exception ex) {
                    Logger.getLogger(AideEnLigne.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        private void setPage(URL page) {
            this.page.add(page);
            ael.next.setEnabled(true);
        }

        private void clear() {
             page = new ArrayList<URL>();
        }

    }
    private JEditorPane editorPane;
    private JPanel top = new JPanel(new BorderLayout());
    private int dimension = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 50;
    private JButton next = new JButton(new ImageIcon(new ImageIcon("./lib/flecheRetour.png").getImage().getScaledInstance(dimension,dimension,Image.SCALE_SMOOTH)));
    private JButton previous = new JButton(new ImageIcon(new ImageIcon("./lib/flecheSuivant.png").getImage().getScaledInstance(dimension,dimension,Image.SCALE_SMOOTH)));
    private previousListener pL = new previousListener(this);
    private nextListener nL = new nextListener(this);
    /**
     * Renvoi une nouvelle instance de l'aide en ligne
     * @param ihm Le composant parent. Dans le cas de Jaint c'est l'IHM
     */
    public AideEnLigne(IHM ihm)
    {
        super(ihm, "Aide en ligne");

        
        top.add(previous,BorderLayout.WEST);
        top.add(next,BorderLayout.EAST);

        previous.setEnabled(false);
        next.setEnabled(false);
        previous.addActionListener(pL);
        next.addActionListener(nL);
        this.add(top,BorderLayout.NORTH);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

        try
        {
            editorPane = new JEditorPane();
            editorPane.setEditable(false);
            editorPane.addHyperlinkListener(this);
            editorPane.setPage(new URL("file:./lib/Help/index.html"));
            JScrollPane jsp = new JScrollPane(editorPane);
            this.add(jsp);
        }
        catch (IOException ex)
        {
        }

        editorPane.setPreferredSize(new Dimension((int)(d.width*0.7),(int)(d.height*0.7)));
        this.editorPane.addKeyListener(new clavListener(this));
        this.pack();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setIconImage(Toolkit.getDefaultToolkit().createImage("./lib/Icons/jaint.png"));//modification de l'icone sous windows
        this.setLocation((d.width-this.getWidth())/2, (d.height-this.getHeight())/2);
        //this.setAlwaysOnTop(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        this.setVisible(true);
    }

    public void hyperlinkUpdate(HyperlinkEvent event)
    {
        if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
        {
                if (event instanceof HTMLFrameHyperlinkEvent)
                {
                        HTMLFrameHyperlinkEvent  evt = (HTMLFrameHyperlinkEvent)event;
                        HTMLDocument doc = (HTMLDocument)editorPane.getDocument();
                        doc.processHTMLFrameHyperlinkEvent(evt);
                }
                else
                {
                        try 
                        {
                            pL.setPage(editorPane.getPage());
                            editorPane.setPage(event.getURL());
                            next.setEnabled(false);
                            nL.clear();
                        }
                        catch (IOException exc)
                        {
                            JOptionPane.showMessageDialog(this, "Page introuvable !");
                        }
                }
        }

    }
    private void setPage(URL uRL) throws IOException {
            editorPane.setPage(uRL);
    }

    private URL getPage()
    {
        return editorPane.getPage();
    }

}
