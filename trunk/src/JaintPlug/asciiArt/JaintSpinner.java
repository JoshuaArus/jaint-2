package JaintPlug.asciiArt;

/**
 *
 * @author Jonas
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JaintSpinner extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2152245494333611123L;

	//Ecouteur
    /**
     *
     */
    public class JBEcouteur_plus implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
                    if(!jtf_nombre.getText().equals(""))
                    {
                        jtf_nombre.setText("" + (Integer.parseInt(jtf_nombre.getText()) + 1));
                    }
                    else
                    {
                        jtf_nombre.setText("1");
                    }
			

		}
	}

        /**
         *
         */
        public class JBEcouteur_moins implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
                    if(!jtf_nombre.getText().equals(""))
                    {
                        jtf_nombre.setText("" + (Integer.parseInt(jtf_nombre.getText()) - 1));
                    }
                    else
                    {
                        jtf_nombre.setText("1");
                    }
			

		}
	}

	//Attributs
        /**
         *
         */
        protected JTextField jtf_nombre;
        /**
         *
         */
        protected JButton jb_plus; //source
        /**
         *
         */
        protected JButton jb_moins; //source
        /**
         *
         */
        protected ActionListener al_plus; //ecouteur
        /**
         *
         */
        protected ActionListener al_moins; //ecouteur
        /**
         *
         */
        protected ImageIcon iconPlus;
        /**
         *
         */
        protected ImageIcon iconMoins;
        /**
         *
         */
        protected Image tmp;


	//Constructeurs
        /**
         *
         */
        public JaintSpinner()
	{
		super();

		//Constructeurs des elements
                
                this.tmp = new ImageIcon("./lib/Icons/Plugins/PolicePlus.png").getImage().getScaledInstance(45,30,Image.SCALE_DEFAULT);
                this.iconPlus = new ImageIcon(this.tmp);

                this.tmp = new ImageIcon("./lib/Icons/Plugins/PoliceMoins.png").getImage().getScaledInstance(45,30,Image.SCALE_DEFAULT);
                this.iconMoins = new ImageIcon(this.tmp);
                

                
		this.jb_plus = new JButton();
		this.jb_moins = new JButton();
		this.jtf_nombre = new JTextField("1");
                this.al_plus = new JBEcouteur_plus();
		this.al_moins = new JBEcouteur_moins();

                
                              
                this.jb_moins.setIcon(this.iconMoins);
                this.jb_plus.setIcon(this.iconPlus);

                //Parametrage de la taille des elements
                this.jtf_nombre.setPreferredSize( new Dimension(90,40));
                this.jb_plus.setPreferredSize( new Dimension(40,40));
                this.jb_moins.setPreferredSize( new Dimension(40,40));

                
                
		//Parametrage des elements
		this.jtf_nombre.setEditable(true);
		this.jtf_nombre.setHorizontalAlignment(JTextField.RIGHT);

		//Parametrage des evenements
		this.jb_plus.addActionListener(al_plus);
		this.jb_moins.addActionListener(al_moins);

		//Saisie controlee
                
		jtf_nombre.setDocument(new NumberDoc(this));
                jtf_nombre.setText("1");
                

                
		//Parametrage du JPanel
		this.setLayout(new BorderLayout());

		//Ajout des elements a un JPanel
		this.add(jb_plus,BorderLayout.EAST);
		this.add(jtf_nombre,BorderLayout.CENTER);
		this.add(jb_moins,BorderLayout.WEST);


	}//Constructeur

        /**
         *
         * @return
         */
        public int getValue()
	{
            String res = this.jtf_nombre.getText();
            return (res.equals("")) ? -1 : Integer.valueOf(res);
	}

        /**
         *
         * @param v
         */
        public void setValue(int v)
        {
            this.jtf_nombre.setText(String.valueOf(v));
        }

        /**
         *
         * @return
         */
        public JTextField getJtf_nombre()
    {
        return jtf_nombre;
    }



       

}
