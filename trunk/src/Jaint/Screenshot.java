package Jaint;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComponent;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * <code>JDialog</code> permettant de definir quelques options pour la prise d'un screenshot.
 */
public class Screenshot extends JDialog
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5550649759839243766L;
	private JTextField jtftimer;
	private JCheckBox checkbox;

	private boolean ok = false;
	private int time = 0;

        /**
         *
         * @param ihm
         */
        public Screenshot(IHM ihm)
	{
		super(ihm, "Screenshot", true);
		showDialog();
	}

        /**
         * Permet d'afficher le JDialog
         */
        public void showDialog()
	{
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		JPanel jp = new JPanel(new BorderLayout());

		JPanel jptimer = new JPanel();
		jptimer.add(new JLabel("Compte a rebours : "));
		this.jtftimer = new JTextField("0", 3);
		this.jtftimer.setHorizontalAlignment((int)JComponent.CENTER_ALIGNMENT);
		jptimer.add(this.jtftimer);

		JPanel jpcheck = new JPanel();
		this.checkbox = new JCheckBox("Reduire la fenetre", true);
		jpcheck.add(this.checkbox);

		JPanel jpboutons = new JPanel();
		JButton jbok = new JButton("Capture");
		jbok.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				apply();
			}
		});
		jpboutons.add(jbok);
		JButton jbcancel = new JButton("Annuler");
		jbcancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cancel();
			}
		});
		jpboutons.add(jbcancel);

		jp.add(jptimer, BorderLayout.NORTH);
		jp.add(jpcheck, BorderLayout.CENTER);
		jp.add(jpboutons, BorderLayout.SOUTH);

		this.add(jp);
		this.pack();

		Dimension d = this.getSize();
		Dimension dScreen = this.getToolkit().getScreenSize();
		this.setLocation((int)(dScreen.getWidth()/2-d.getWidth()/2), (int)(dScreen.getHeight()/2-d.getHeight()/2));

		this.setVisible(true);
	}

        /**
         * Applique la capture d'ecran
         */
        public void apply()
	{
		try
		{
			this.time = Integer.parseInt(this.jtftimer.getText());
			this.ok = true;
			this.dispose();
		}
		catch(NumberFormatException nfe)
		{
			JOptionPane.showMessageDialog(null, "Entrez des valeurs numeriques", "Erreur", JOptionPane.WARNING_MESSAGE);
		}
	}

        /**
         * Ferme le JDialog
         */
        public void cancel()
	{
		this.dispose();
	}
        /**
         * Renvoie le temps du minuteur entre par l'utilisateur
         * @return Le temps
         */
        public int getTime()
	{
		return(this.time);
	}
        /**
         * Peremt de savoir si l'utilisateur a clique sur OK
         * @return
         */
        public boolean getOk()
	{
		return(this.ok);
	}
        /**
         * Permet de savoir si l'utilisateur a souhaite reduite la fenetre
         * @return
         */
        public boolean getMinimize()
	{
		return(this.checkbox.isSelected());
	}
}
